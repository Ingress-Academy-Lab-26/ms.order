package az.ingress.ms_order.service.concrete;

import az.ingress.ms_order.aop.annotation.Log;
import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.dao.repository.OrderRepository;
import az.ingress.ms_order.exception.NotFoundException;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;
import az.ingress.ms_order.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static az.ingress.ms_order.mapper.AddressMapper.toAddressEntity;
import static az.ingress.ms_order.mapper.OrderItemMapper.toOrderItemsEntities;
import static az.ingress.ms_order.mapper.OrderMapper.toOrderEntity;
import static az.ingress.ms_order.mapper.OrderMapper.toOrderResponse;
import static az.ingress.ms_order.model.enums.ExceptionMessages.ORDER_NOT_FOUND;
import static az.ingress.ms_order.model.enums.OrderStatus.DELETED;

@Log
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceHandler implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse getOrderById(Long id) {
        return toOrderResponse(fetchOrderIfExists(id));
    }

    @Override
    public void createOrder(OrderRequest orderRequest) {
        var order = toOrderEntity(orderRequest);
        order.setAmount(BigDecimal.ONE);

        setOrderItemsToOrder(order, orderRequest);
        setAddressToOrder(order, orderRequest);

        orderRepository.save(order);
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

    private void setOrderItemsToOrder(OrderEntity order, OrderRequest orderRequest) {
        if (orderRequest.getOrderItems() != null) {
            var orderItemEntities = toOrderItemsEntities(order, orderRequest.getOrderItems());
            order.setOrderItems(orderItemEntities);
        }
    }

    private void setAddressToOrder(OrderEntity order, OrderRequest orderRequest) {
        if (orderRequest.getAddressRequest() != null) {
            var address = toAddressEntity(orderRequest.getAddressRequest());
            order.setAddress(address);
        }
    }

}
