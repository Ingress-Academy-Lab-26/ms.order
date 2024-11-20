package az.ingress.ms_order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name= "ms-auth", url = "${client.ms-auth.url}", path = "/internal/v1/auth")
public interface AuthClient {

    @PostMapping("/verify")
    void verify(@RequestHeader(AUTHORIZATION) String accessToken);
}
