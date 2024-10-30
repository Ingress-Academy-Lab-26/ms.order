package az.ingress.ms_order.aop;

import az.ingress.ms_order.service.abstraction.AuthService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthAspect {
    private final AuthService authService;


    @Pointcut("within(@az.ingress.ms_order.controller..)")
    public void authPointCut() {
    }

    @SneakyThrows
    @Before(value = "authPointCut() && args(token,..)")
    public void before(String token) {
        try {
            authService.verify(token);
        }catch (FeignException e){
            if (e.status() == HttpStatus.FORBIDDEN.value()) {
                log.error("Access forbidden: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication service error");
            }
        }
    }
}
