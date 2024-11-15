package az.ingress.ms_order.model.client.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private List<ProductDto> orderProducts;
}
