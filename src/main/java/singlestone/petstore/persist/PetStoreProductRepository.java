package singlestone.petstore.persist;

import org.springframework.data.repository.CrudRepository;
import singlestone.petstore.model.PetStoreProduct;

public interface PetStoreProductRepository extends CrudRepository<PetStoreProduct, String> {
}
