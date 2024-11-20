package az.ingress.ms_order.mapper

import az.ingress.ms_order.dao.entity.AddressEntity
import az.ingress.ms_order.model.request.AddressRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.ingress.ms_order.model.enums.Status.ACTIVE

class AddressMapperTest extends Specification{
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestToAddressEntity()"(){
        given:
        def dto =random.nextObject(AddressRequest)

        when:
        def entity = AddressMapper.toAddressEntity(dto)

        then:
        entity.getCountry() == dto.getCountry()
        entity.getCity() == dto.getCity()
        entity.getStreet() == dto.getStreet()
        entity.getDeliveryLocation() == dto.getDeliveryLocation()
        entity.getStatus() == ACTIVE
    }

    def "TestToAddressResponse()"(){
        given:
        def entity =random.nextObject(AddressEntity)

        when:
        def response = AddressMapper.toAddressResponse(entity)

        then:
        entity.getId() == response.getId()
        entity.getCountry() == response.getCountry()
        entity.getCity() == response.getCity()
        entity.getStreet() == response.getStreet()
        entity.getDeliveryLocation() == response.getDeliveryLocation()
    }

}
