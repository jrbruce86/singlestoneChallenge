package singlestone.petstore.service.productService;

import singlestone.petstore.model.PetStoreProduct;

import java.util.Set;

/**
 * Interface that defines the service used to retrieve product info
 */
public interface PetStoreProductService {

    /**
     * Retrieves available pet store products
     * @return the pet store products
     * @throws Exception thrown on error
     */
    Set<PetStoreProduct> getAvailableProducts() throws Exception;

    /**
     * Looks up a specific petstore product
     * @param productId the id of the product
     * @return the petstore product
     * @throws Exception thrown on error
     */
    PetStoreProduct findPetStoreProduct(final String productId) throws Exception;

}
