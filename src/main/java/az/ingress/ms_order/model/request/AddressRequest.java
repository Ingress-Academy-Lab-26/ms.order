package az.ingress.ms_order.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String country;
    private String city;
    private String street;
    private String deliveryLocation;
}
