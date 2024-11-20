package az.ingress.ms_order.service.abstraction;

import az.ingress.ms_order.model.criteria.OrderCriteria;
import az.ingress.ms_order.model.criteria.PageCriteria;
import az.ingress.ms_order.model.request.OrderRequest;
import az.ingress.ms_order.model.response.OrderResponse;
import az.ingress.ms_order.model.response.PageableResponse;

public interface OrderService {
    PageableResponse getAllOrders(PageCriteria pageCriteria, OrderCriteria cardCriteria);
    OrderResponse getOrderById(Long id);
    void createOrder(OrderRequest orderRequest);
    void deleteOrder(Long id);
}
