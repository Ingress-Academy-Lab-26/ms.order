package az.ingress.ms_order.dao.repository;

import az.ingress.ms_order.dao.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
}
