package az.ingress.ms_order.model.client.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Long id;
    private Long userId;
    private Long cardId;
    private BigDecimal amount;
}
