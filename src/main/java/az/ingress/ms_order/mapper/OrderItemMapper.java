package az.ingress.ms_order.mapper;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.dao.entity.OrderItemEntity;
import az.ingress.ms_order.model.request.OrderItemRequest;
import az.ingress.ms_order.model.response.OrderItemResponse;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static az.ingress.ms_order.model.enums.Status.ACTIVE;

@Slf4j
public enum OrderItemMapper {
    ORDER_ITEM_MAPPER;

    public static OrderItemResponse toOrderItemResponse(OrderItemEntity orderItemEntity) {
        return OrderItemResponse.builder()
                .id(orderItemEntity.getId())
                .quantity(orderItemEntity.getQuantity())
                .productId(orderItemEntity.getProductId())
                .build();
    }

    public static OrderItemEntity toOrderItemEntity(OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    public static OrderItemEntity toOrderItemEntity(OrderEntity orderEntity, OrderItemRequest orderItemRequest) {
        return  OrderItemEntity.builder()
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .order(orderEntity)
                .price(BigDecimal.TEN)
                .status(ACTIVE)
                .build();
    }

    public static OrderItemEntity updateOrderItemEntity(OrderItemEntity orderItemEntity, OrderItemRequest orderItemRequest) {
        orderItemEntity.setProductId(orderItemRequest.getProductId());
        orderItemEntity.setQuantity(orderItemRequest.getQuantity());
        return orderItemEntity;
    }

    public static List<OrderItemResponse> toOrderItemsResponses(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities
                .stream()
                .map(OrderItemMapper::toOrderItemResponse)
                .toList();
    }

    public static List<OrderItemEntity> toOrderItemsEntities(OrderEntity orderEntity, List<OrderItemRequest> orderItemRequests) {
        return orderItemRequests
                .stream()
                .map(orderItemRequest -> OrderItemMapper.toOrderItemEntity(orderEntity, orderItemRequest))
                .toList();
    }
}
