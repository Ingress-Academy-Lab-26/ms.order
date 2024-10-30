package az.ingress.ms_order.mapper;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;

import static az.ingress.ms_order.mapper.AddressMapper.toAddressResponse;
import static az.ingress.ms_order.mapper.OrderItemMapper.toOrderItemsResponses;
import static az.ingress.ms_order.model.enums.OrderStatus.IN_PROGRESS;
import static az.ingress.ms_order.model.enums.PaymentStatus.PROGRESS;

public enum OrderMapper {
    ORDER_MAPPER;

    public static OrderResponse toOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .userId(orderEntity.getUserId())
                .amount(orderEntity.getAmount())
                .address(toAddressResponse(orderEntity.getAddress()))
                .orderItems(toOrderItemsResponses(orderEntity.getOrderItems()))
                .build();
    }

    public static OrderEntity toOrderEntity(OrderRequest orderRequest) {
        return OrderEntity.builder()
                .userId(orderRequest.getUserId())
                .status(IN_PROGRESS)
                .paymentStatus(PROGRESS)
                .build();
    }
}
