package az.ingress.ms_order.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperFactory {
    OBJECT_MAPPER;

    public ObjectMapper getInstance() {
        return new ObjectMapper();
}
}
