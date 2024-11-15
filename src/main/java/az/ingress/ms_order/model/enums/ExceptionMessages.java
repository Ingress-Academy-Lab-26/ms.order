package az.ingress.ms_order.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {
    ADDRESS_NOT_FOUND("ADDRESS_NOT_FOUND", "Address not found."),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Order not found."),
    ORDER_ITEM_NOT_FOUND("ORDER_ITEM_NOT_FOUND", "Order item not found."),
    ORDER_ITEM_LESS_QUANTITY("ORDER_ITEM_LESS_QUANTITY", "There is less quantity."),
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred."),
    HTTP_METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "Method not allowed."),
    ACCESS_DENIED("ACCESS_DENIED", "Access denied."),
    CLIENT_ERROR("CLIENT_ERROR", "Exception from Client"),
    PAYMENT_FAILED("PAYMENT_FAILED", "Payment failed"),;
    private final String code;
    private final String message;
}
