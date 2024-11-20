package az.ingress.ms_order.dao.repository;

import az.ingress.ms_order.dao.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {
}
