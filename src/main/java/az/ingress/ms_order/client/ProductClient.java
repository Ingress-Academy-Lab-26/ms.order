package az.ingress.ms_order.client;

import az.ingress.ms_order.client.decoder.CustomErrorDecoder;
import az.ingress.ms_order.model.client.product.ProductResponse;
import az.ingress.ms_order.model.client.product.ProductIdsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name= "ms-product",
                        url = "${client.ms-product.url}",
                        configuration = CustomErrorDecoder.class,
                        path = "v1/internal/products")
public interface ProductClient {

    @PostMapping("/getQuantity")
    ProductResponse getOrderProductById(@RequestBody ProductIdsDto id);

}
