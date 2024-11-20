package az.ingress.ms_order.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

import static az.ingress.ms_order.model.enums.ExceptionMessages.ACCESS_DENIED;
import static az.ingress.ms_order.model.enums.ExceptionMessages.HTTP_METHOD_NOT_ALLOWED;
import static az.ingress.ms_order.model.enums.ExceptionMessages.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception {}", ex);
        return new ErrorResponse(UNEXPECTED_EXCEPTION.getCode(), UNEXPECTED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException ex) {
        log.error("Exception {}", ex);
        return new ErrorResponse(HTTP_METHOD_NOT_ALLOWED.getCode(), HTTP_METHOD_NOT_ALLOWED.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException ex) {
        log.error("NotFoundException {}", ex);
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ProductQuantityException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ErrorResponse handle(ProductQuantityException ex) {
        log.error("ProductQuantityException {}", ex);
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(PaymentFailedException.class)
    @ResponseStatus(PAYMENT_REQUIRED)
    public ErrorResponse handle(PaymentFailedException ex) {
        log.error("PaymentFailedException {}", ex);
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handle(AccessDeniedException ex) {
        log.error("PaymentFailedException {}", ex);
        return new ErrorResponse(ACCESS_DENIED.getCode(), ACCESS_DENIED.getMessage());
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handle(CustomFeignException ex){
        log.error("CustomFeignException: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .code(ex.getCode())
                        .build()
        );
    }
}
