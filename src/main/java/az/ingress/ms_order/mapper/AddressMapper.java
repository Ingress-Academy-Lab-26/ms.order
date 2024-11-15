package az.ingress.ms_order.mapper;


import az.ingress.ms_order.dao.entity.AddressEntity;
import az.ingress.ms_order.model.request.AddressRequest;
import az.ingress.ms_order.model.response.AddressResponse;

import java.time.LocalDateTime;

import static az.ingress.ms_order.model.enums.Status.ACTIVE;
import static java.time.LocalDateTime.now;

public enum AddressMapper {
    ADDRESS_MAPPER;

    public static AddressEntity toAddressEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .deliveryLocation(addressRequest.getDeliveryLocation())
                .status(ACTIVE)
                .build();
    }

    public static AddressResponse toAddressResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
                .id(addressEntity.getId())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .deliveryLocation(addressEntity.getDeliveryLocation())
                .build();
    }
}