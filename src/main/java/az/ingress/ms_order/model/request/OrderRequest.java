package az.ingress.ms_order.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private AddressRequest addressRequest;
    private List<OrderItemRequest> orderItems;
}
