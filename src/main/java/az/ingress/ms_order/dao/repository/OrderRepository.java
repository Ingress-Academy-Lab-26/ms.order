package az.ingress.ms_order.dao.repository;


import az.ingress.ms_order.dao.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

    @EntityGraph(attributePaths = {"address", "orderItems"})
    Page<OrderEntity> findAll(Specification<OrderEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"address", "orderItems"})
    Optional<OrderEntity> findById(Long id);
}
