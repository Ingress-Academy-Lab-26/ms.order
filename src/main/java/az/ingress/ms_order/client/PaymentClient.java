package az.ingress.ms_order.client;

import az.ingress.ms_order.client.decoder.CustomErrorDecoder;
import az.ingress.ms_order.model.client.payment.PaymentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "ms-payment",
                url = "${client.ms-payment.url}",
                configuration = CustomErrorDecoder.class,
                path = "/internal/v1/payments")
public interface PaymentClient {
    @PostMapping
    void createPayment(@RequestBody PaymentRequestDto paymentRequestDto);
}
