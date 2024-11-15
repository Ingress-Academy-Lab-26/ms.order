package az.ingress.ms_order.mapper

import az.ingress.ms_order.dao.entity.OrderEntity
import az.ingress.ms_order.dao.entity.OrderItemEntity
import az.ingress.ms_order.model.request.OrderItemRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.ingress.ms_order.model.enums.Status.ACTIVE

class OrderItemMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestToOrderItemResponse()"() {
        given:
        def entity = random.nextObject(OrderItemEntity)

        when:
        def result = OrderItemMapper.toOrderItemResponse(entity)

        then:
        entity.getId() == result.getId()
        entity.getQuantity() == result.getQuantity()
        entity.getProductId() == result.getProductId()
        entity.getPrice() == result.getPrice()
    }

    def "TestToOrderItemEntity()"() {
        given:
        def entity = random.nextObject(OrderEntity)
        def dto = random.nextObject(OrderItemRequest)

        when:
        def result = OrderItemMapper.toOrderItemEntity(entity, dto)

        then:
        result.getProductId() == dto.getProductId()
        result.getQuantity() == dto.getQuantity()
        result.getOrder() == entity
        result.getStatus() == ACTIVE
    }

    def "TestToUpdateProductStockDto()"() {
        given:
        def entities = random.nextObject(List<OrderItemEntity>)

        when:
        def result = OrderItemMapper.toUpdateProductStockDto(entities)

        then:
        result.size() == entities.size()
        for (int i = 0; i < result.size(); i++) {
            result[i].getId() == entities[i].getProductId()
            result[i].getQuantity() == entities[i].getQuantity()
        }
    }

    def "TestToOrderItemsResponses()"() {
        given:
        def entities = random.nextObject(List<OrderItemEntity>)

        when:
        def result = OrderItemMapper.toOrderItemsResponses(entities)

        then:
        result.size() == entities.size()
        for (int i = 0; i < result.size(); i++) {
            result[i].getId() == entities[i].getProductId()
            result[i].getProductId() == entities[i].getProductId()
            result[i].getQuantity() == entities[i].getQuantity()
            result[i].getPrice() == entities[i].getPrice()
        }
    }
}