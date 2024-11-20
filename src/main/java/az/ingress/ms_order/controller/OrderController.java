package az.ingress.ms_order.controller;

import az.ingress.ms_order.model.criteria.OrderCriteria;
import az.ingress.ms_order.model.criteria.PageCriteria;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;
import az.ingress.ms_order.model.response.PageableResponse;
import az.ingress.ms_order.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    @PreAuthorize("@authService.verify(#accessToken)")
    public PageableResponse getAllOrders(@RequestHeader(AUTHORIZATION) String accessToken,
                                         PageCriteria pageCriteria,
                                         OrderCriteria cardCriteria) {
        return orderService.getAllOrders(pageCriteria, cardCriteria);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authService.verify(#accessToken)")
    public OrderResponse getOrderById(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @PreAuthorize("@authService.verify(#accessToken)")
    @ResponseStatus(CREATED)
    public void createOrder(@RequestHeader(AUTHORIZATION) String accessToken, @RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.verify(#accessToken)")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
