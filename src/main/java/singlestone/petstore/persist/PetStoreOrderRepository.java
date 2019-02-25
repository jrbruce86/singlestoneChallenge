package singlestone.petstore.persist;

import org.springframework.data.repository.CrudRepository;
import singlestone.petstore.model.PetStoreOrder;

public interface PetStoreOrderRepository extends CrudRepository<PetStoreOrder, String> {
}
