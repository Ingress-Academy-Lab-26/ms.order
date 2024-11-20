package az.ingress.ms_order.service.specification;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.model.criteria.OrderCriteria;
import az.ingress.ms_order.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static az.ingress.ms_order.dao.entity.OrderEntity.Fields.*;

@AllArgsConstructor
public class OrderSpecification implements Specification<OrderEntity> {
    private OrderCriteria orderCriteria;

    @Override
    public Predicate toPredicate(Root<OrderEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(orderCriteria.getAmountFrom(),
                        quantityFrom -> cb.greaterThanOrEqualTo(root.get(amount), quantityFrom))
                .addNullSafety(orderCriteria.getAmountTo(),
                        quantityTo -> cb.lessThanOrEqualTo(root.get(amount), quantityTo))
                .addNullSafety(toDateTime(orderCriteria.getCreatedAtFrom()),
                        createdAtFrom -> cb.greaterThanOrEqualTo(root.get(createdAt), createdAtFrom))
                .addNullSafety(toDateTime(orderCriteria.getCreatedAtTo()),
                        createdAtTo -> cb.lessThanOrEqualTo(root.get(createdAt), createdAtTo))
                .build();

        return cb.and(predicates);
    }

    private LocalDateTime toDateTime(LocalDate date) {
        return Optional.ofNullable(date)
                       .map(LocalDate::atStartOfDay)
                       .orElse(null);
    }
}
