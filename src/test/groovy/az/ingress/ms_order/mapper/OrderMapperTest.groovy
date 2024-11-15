package az.ingress.ms_order.mapper

import az.ingress.ms_order.dao.entity.OrderEntity
import az.ingress.ms_order.model.request.OrderRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.ingress.ms_order.model.enums.OrderStatus.IN_PROGRESS
import static az.ingress.ms_order.model.enums.PaymentStatus.PROGRESS

class OrderMapperTest extends Specification{
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom();

    def "TestToOrderResponse()"(){
        given:
        def entity = random.nextObject(OrderEntity)

        when:
        def result = OrderMapper.toOrderResponse(entity)

        then:
        result.getId() == entity.getId()
        result.getUserId() == entity.getUserId()
        result.getCardId() == entity.getCardId()
        result.getAmount() == entity.getAmount()
        result.getAddress() == AddressMapper.toAddressResponse(entity.getAddress())
        result.getOrderItems() == OrderItemMapper.toOrderItemsResponses(entity.getOrderItems())
    }

    def "TestToOrderEntity()"(){
        given:
        def request = random.nextObject(OrderRequest)

        when:
        def result = OrderMapper.toOrderEntity(request)

        then:
        result.getUserId() == request.getUserId()
        result.getCardId() == request.getCardId()
        result.getStatus() == IN_PROGRESS
        result.getPaymentStatus() == PROGRESS
    }


    def "TestToPaymentRequestDto()"(){
        given:
        def entity = random.nextObject(OrderEntity)

        when:
        def result = OrderMapper.toPaymentRequestDto(entity)

        then:
        result.getId() == entity.getId()
        result.getUserId() == entity.getUserId()
        result.getCardId() == entity.getCardId()
        result.getAmount() == entity.getAmount()
    }
}
