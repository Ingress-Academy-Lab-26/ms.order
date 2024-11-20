package az.ingress.ms_order.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String code, String message) {

}
