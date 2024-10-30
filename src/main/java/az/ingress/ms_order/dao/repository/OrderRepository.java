package az.ingress.ms_order.dao.repository;


import az.ingress.ms_order.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = {"address", "orderItems"})
    Optional<OrderEntity> findById(Long id);
}
