package az.ingress.ms_order.exception;

import lombok.Getter;

@Getter
public class PaymentFailedException extends RuntimeException{
    private String code;

    public PaymentFailedException(String message, String code) {
        super(message);
        this.code = code;
    }
}
