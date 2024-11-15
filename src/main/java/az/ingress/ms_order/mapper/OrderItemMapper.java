package az.ingress.ms_order.mapper;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.dao.entity.OrderItemEntity;
import az.ingress.ms_order.model.client.product.UpdateProductStockDto;
import az.ingress.ms_order.model.request.OrderItemRequest;
import az.ingress.ms_order.model.response.OrderItemResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
                .price(orderItemEntity.getPrice())
                .build();
    }

    public static OrderItemEntity toOrderItemEntity(OrderEntity orderEntity, OrderItemRequest orderItemRequest) {
        return  OrderItemEntity.builder()
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .order(orderEntity)
                .status(ACTIVE)
                .build();
    }

    public static List<UpdateProductStockDto> toUpdateProductStockDto(List<OrderItemEntity> orderItemEntity){
        return orderItemEntity.stream()
                .map(oie -> UpdateProductStockDto
                        .builder()
                        .id(oie.getProductId())
                        .quantity(oie.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<OrderItemResponse> toOrderItemsResponses(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities
                .stream()
                .map(OrderItemMapper::toOrderItemResponse)
                .toList();
    }
}
