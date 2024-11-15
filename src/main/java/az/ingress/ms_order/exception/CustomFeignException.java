package az.ingress.ms_order.exception;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private final int status;
    private final String code;

    public CustomFeignException(String message,
                                int status,
                                String code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
