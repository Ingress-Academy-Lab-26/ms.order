package az.ingress.ms_order.service.abstraction;

import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;

public interface OrderService {
    OrderResponse getOrderById(Long id);
    void createOrder(OrderRequest orderRequest);
    void deleteOrder(Long id);
}
