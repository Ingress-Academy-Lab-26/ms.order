package az.ingress.ms_order.exception;

import lombok.Getter;

@Getter
public class ProductQuantityException extends RuntimeException{

    private String code;

    public ProductQuantityException(String message, String code) {
        super(message);
        this.code = code;
    }
}
