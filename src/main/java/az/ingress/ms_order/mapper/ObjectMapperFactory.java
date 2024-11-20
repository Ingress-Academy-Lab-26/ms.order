package az.ingress.ms_order.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public enum ObjectMapperFactory {
    OBJECT_MAPPER;

    public ObjectMapper getInstance() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
}
}
