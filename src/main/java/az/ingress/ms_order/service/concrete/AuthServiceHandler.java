package az.ingress.ms_order.service.concrete;

import az.ingress.ms_order.aop.annotation.Log;
import az.ingress.ms_order.client.AuthClient;
import az.ingress.ms_order.service.abstraction.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final AuthClient authClient;

    @Override
    public void verify(String accessToken) {
        authClient.verify(accessToken);
    }
}
