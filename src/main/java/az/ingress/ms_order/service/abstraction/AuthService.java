package az.ingress.ms_order.service.abstraction;

public interface AuthService {
    void verify(String accessToken);
}
