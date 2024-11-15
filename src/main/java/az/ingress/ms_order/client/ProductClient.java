package az.ingress.ms_order.client;

import az.ingress.ms_order.client.decoder.CustomErrorDecoder;
import az.ingress.ms_order.model.client.product.ProductResponse;
import az.ingress.ms_order.model.client.product.ProductIdsDto;
import az.ingress.ms_order.model.client.product.UpdateProductStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name= "ms-product",
                        url = "${client.ms-product.url}",
                        configuration = CustomErrorDecoder.class,
                        path = "v1/internal")
public interface ProductClient {

    @GetMapping("/get-quantity")
    ProductResponse getOrderProductById(@RequestBody ProductIdsDto id);

    @PostMapping("/update-quantity")
    void updateQuantity(List<UpdateProductStockDto> productResponseDto);
}
