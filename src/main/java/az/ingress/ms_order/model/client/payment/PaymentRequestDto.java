package az.ingress.ms_order.model.client.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class PaymentRequestDto {
    private Long id;
    private Long userId;
    private Long cardId;
    private BigDecimal amount;
}
