package singlestone.petstore.service.productService;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import singlestone.petstore.model.PetStoreProduct;
import singlestone.petstore.persist.PetStoreProductRepository;
import singlestone.petstore.service.ExceptionHandlingService;

import java.util.Set;

/**
 * Petstore product service that uses the internal (locally seeded) pet store product database.
 */
 @Service
public class InternalPetStoreProductService implements PetStoreProductService {

    private final PetStoreProductRepository petStoreProductRepository;
    private final ExceptionHandlingService exceptionHandlingService;

    @Autowired
    public InternalPetStoreProductService(final PetStoreProductRepository petStoreProductRepository,
                                          final ExceptionHandlingService exceptionHandlingService) {
        this.petStoreProductRepository = petStoreProductRepository;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @Override
    public Set<PetStoreProduct> getAvailableProducts() throws Exception {
        try {
            return Sets.newHashSet(petStoreProductRepository.findAll());
        } catch(final Exception e) {
            throw exceptionHandlingService.createClientFriendlyException("There was an unknown error while looking up available products.", e);
        }
    }

    @Override
    public PetStoreProduct findPetStoreProduct(String productId) throws Exception {
        try {
            return petStoreProductRepository.findById(productId).orElse(null);
        } catch (final Exception e) {
            throw exceptionHandlingService.createClientFriendlyException(String.format("An unknown error occurred while looking product with id %s", productId), e);
        }
    }

}
