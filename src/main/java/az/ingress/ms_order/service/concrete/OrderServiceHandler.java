package az.ingress.ms_order.service.concrete;

import az.ingress.ms_order.annotation.Log;
import az.ingress.ms_order.client.PaymentClient;
import az.ingress.ms_order.client.ProductClient;
import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.dao.entity.OrderItemEntity;
import az.ingress.ms_order.dao.repository.OrderRepository;
import az.ingress.ms_order.exception.NotFoundException;
import az.ingress.ms_order.exception.PaymentFailedException;
import az.ingress.ms_order.exception.ProductQuantityException;
import az.ingress.ms_order.model.client.product.ProductIdsDto;
import az.ingress.ms_order.model.client.product.ProductResponse;
import az.ingress.ms_order.model.criteria.OrderCriteria;
import az.ingress.ms_order.model.criteria.PageCriteria;
import az.ingress.ms_order.model.queue.NotificationDto;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;
import az.ingress.ms_order.model.response.PageableResponse;
import az.ingress.ms_order.queue.QueueProducer;
import az.ingress.ms_order.service.abstraction.OrderService;
import az.ingress.ms_order.service.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static az.ingress.ms_order.mapper.AddressMapper.toAddressEntity;
import static az.ingress.ms_order.mapper.OrderItemMapper.toOrderItemEntity;
import static az.ingress.ms_order.mapper.OrderItemMapper.toUpdateProductStockDto;
import static az.ingress.ms_order.mapper.OrderMapper.mapToPageableResponse;
import static az.ingress.ms_order.mapper.OrderMapper.toOrderEntity;
import static az.ingress.ms_order.mapper.OrderMapper.toOrderResponse;
import static az.ingress.ms_order.mapper.OrderMapper.toPaymentRequestDto;
import static az.ingress.ms_order.model.enums.ExceptionMessages.ORDER_ITEM_LESS_QUANTITY;
import static az.ingress.ms_order.model.enums.ExceptionMessages.ORDER_ITEM_NOT_FOUND;
import static az.ingress.ms_order.model.enums.ExceptionMessages.ORDER_NOT_FOUND;
import static az.ingress.ms_order.model.enums.ExceptionMessages.PAYMENT_FAILED;
import static az.ingress.ms_order.model.enums.OrderStatus.COMPLETED;
import static az.ingress.ms_order.model.enums.OrderStatus.DELETED;
import static az.ingress.ms_order.model.enums.PaymentStatus.CANCELED;
import static az.ingress.ms_order.model.enums.PaymentStatus.PAID;
import static az.ingress.ms_order.model.enums.QueueName.NOTIFICATION_Q;
import static az.ingress.ms_order.model.enums.QueueName.PRODUCT_Q;
import static az.ingress.ms_order.model.queue.ChannelType.MAIL;
import static az.ingress.ms_order.model.queue.ChannelType.TELEGRAM;
import static az.ingress.ms_order.util.OrderUtil.calculateTotalAmount;
import static az.ingress.ms_order.util.OrderUtil.generatePayload;
import static az.ingress.ms_order.dao.entity.OrderEntity.Fields.id;

@Log
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceHandler implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final QueueProducer queueProducer;

    @Override
    public PageableResponse getAllOrders(PageCriteria pageCriteria, OrderCriteria cardCriteria) {
        Page<OrderEntity> orders = orderRepository.findAll(
                new OrderSpecification(cardCriteria),
                PageRequest.of(pageCriteria.getPage(),
                        pageCriteria.getCount(),
                        Sort.by(id).descending()));

        return mapToPageableResponse(orders);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return toOrderResponse(fetchOrderIfExists(id));
    }

    @Override
    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        var order = toOrderEntity(orderRequest);

        setOrderItemsToOrder(order, orderRequest);
        setAddressToOrder(order, orderRequest);

        calculateTotalAmount(order);
        sendPaymentRequest(order);

        orderRepository.save(order);
        sendProductUpdateQueue(order.getOrderItems());
        sendNotification(order);
    }

    @Override
    public void deleteOrder(Long id) {
        var order = fetchOrderIfExists(id);
        order.setStatus(DELETED);
        orderRepository.save(order);
    }

    private OrderEntity fetchOrderIfExists(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND.getMessage(), ORDER_NOT_FOUND.getCode()));
    }

    private void setAddressToOrder(OrderEntity order, OrderRequest orderRequest) {
        if (orderRequest.getAddressRequest() != null) {
            var address = toAddressEntity(orderRequest.getAddressRequest());
            order.setAddress(address);
        }
    }

    private void setOrderItemsToOrder(OrderEntity order, OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getOrderItems() == null) {
            return;
        }
        List<OrderItemEntity> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> toOrderItemEntity(order, orderItemRequest))
                .collect(Collectors.toList());

        var productDto = productClient.getOrderProductById(ProductIdsDto.builder()
                        .productId(orderItems
                                            .stream()
                                            .map(OrderItemEntity::getProductId)
                                            .collect(Collectors.toList()))
                        .build());

        settingDataToOrderItem(orderItems, productDto);
        order.setOrderItems(orderItems);
    }

    private void settingDataToOrderItem(List<OrderItemEntity> orderItemEntities, ProductResponse productResponse) {
        if (productResponse == null || productResponse.getOrderProducts() == null) {
            throw new NotFoundException(ORDER_ITEM_NOT_FOUND.getMessage(), ORDER_ITEM_NOT_FOUND.getCode());
        }
        orderItemEntities
                .forEach(orderItemEntity -> {
                    var productDto = productResponse
                                            .getOrderProducts()
                                            .stream()
                                            .filter(product -> product.getProductId().equals(orderItemEntity.getProductId()))
                                            .findFirst()
                                            .orElseThrow(() -> new NotFoundException(ORDER_ITEM_NOT_FOUND.getMessage(), ORDER_ITEM_NOT_FOUND.getCode()));

                    if (productDto.getQuantity() < orderItemEntity.getQuantity()) {
                        throw new ProductQuantityException(ORDER_ITEM_LESS_QUANTITY.getMessage(), ORDER_ITEM_LESS_QUANTITY.getCode());
                    }
                    orderItemEntity.setPrice(productDto.getPrice());
                });
    }

    private void sendPaymentRequest(OrderEntity order) {
        var paymentRequest = toPaymentRequestDto(order);
        try {
            paymentClient.createPayment(paymentRequest);
            order.setStatus(COMPLETED);
            order.setPaymentStatus(PAID);
        }catch (Exception e){
            order.setStatus(DELETED);
            order.setPaymentStatus(CANCELED);
            throw new PaymentFailedException(PAYMENT_FAILED.getMessage(), PAYMENT_FAILED.getCode());
        }
    }

    private void sendProductUpdateQueue(List<OrderItemEntity> orderItems) {
        var updateProductStockDto = toUpdateProductStockDto(orderItems);
        queueProducer.sendMessageToQ(PRODUCT_Q.name(), updateProductStockDto);
    }

    private void sendNotification(OrderEntity order) {
        var telegramNotification = NotificationDto
                                            .builder()
                                            .payload(generatePayload(order))
                                            .channelType(TELEGRAM)
                                            .build();

        var mailNotification = NotificationDto
                                            .builder()
                                            .payload(generatePayload(order))
                                            .channelType(MAIL)
                                            .build();
        queueProducer.sendMessageToQ(NOTIFICATION_Q.name(), telegramNotification);
        queueProducer.sendMessageToQ(NOTIFICATION_Q.name(), mailNotification);
    }
}