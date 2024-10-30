package az.ingress.ms_order.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {
    ADDRESS_NOT_FOUND("ADDRESS_NOT_FOUND", "Address not found."),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Order not found."),
    ORDER_ITEM_NOT_FOUND("ORDER_ITEM_NOT_FOUND", "Order item not found."),
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred."),
    HTTP_METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "Method not allowed."),;

    private final String code;
    private final String message;
}
