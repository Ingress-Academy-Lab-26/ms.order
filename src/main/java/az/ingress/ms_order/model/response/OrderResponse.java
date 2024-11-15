package az.ingress.ms_order.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long cardId;
    private BigDecimal amount;
    private AddressResponse address;
    private List<OrderItemResponse> orderItems;
}
