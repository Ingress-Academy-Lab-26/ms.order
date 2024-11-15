package az.ingress.ms_order.mapper;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.model.client.payment.PaymentRequestDto;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;
import az.ingress.ms_order.model.response.PageableResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;

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
                .cardId(orderEntity.getCardId())
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
                .cardId(orderRequest.getCardId())
                .build();
    }

    public static PageableResponse mapToPageableResponse(Page<OrderEntity> entities) {
        return PageableResponse
                .builder()
                .content(Collections.singletonList(entities.map(OrderMapper::toOrderResponse).stream().toList()))
                .totalElements(entities.getTotalElements())
                .hasNextPage(entities.hasNext())
                .lastPageNumber(entities.getTotalPages())
                .build();
    }

    public static PaymentRequestDto toPaymentRequestDto(OrderEntity orderEntity) {
        return PaymentRequestDto.builder()
                .id(orderEntity.getId())
                .userId(orderEntity.getUserId())
                .cardId(orderEntity.getCardId())
                .amount(orderEntity.getAmount())
                .build();
    }
}
