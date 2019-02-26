package singlestone.petstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import singlestone.petstore.model.*;
import singlestone.petstore.persist.PetStoreOrderRepository;
import singlestone.petstore.service.productService.PetStoreProductService;

import java.util.Objects;
import java.util.Set;

@Service
public class PetStoreOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PetStoreOrderRepository petStoreOrderRepository;
    private final ExceptionHandlingService exceptionHandlingService;
    private final PetStoreProductService petStoreProductService;

    PetStoreOrderService(final PetStoreOrderRepository petStoreOrderRepository,
                                final ExceptionHandlingService exceptionHandlingService,
                                final PetStoreProductService petStoreProductService) {
        this.petStoreOrderRepository = petStoreOrderRepository;
        this.exceptionHandlingService = exceptionHandlingService;
        this.petStoreProductService = petStoreProductService;
    }

    public void storeOrderDetails(final PetStoreOrder petStoreOrder) throws Exception {
        // Make sure all of the products are valid
        final Set<PetStoreItem> items = petStoreOrder.getItems();
        for(PetStoreItem item : items) {
            final PetStoreProduct product = petStoreProductService.findPetStoreProduct(item.getProductId());
            if(Objects.isNull(product)) {
                throw exceptionHandlingService.createClientFriendlyException(
                        String.format("Error, could not find product with id %s. Check the endpoint for the available products", item.getProductId()),
                        ErrorType.NOT_FOUND);
            }
            if(item.getQuantity() < 0) {
                throw exceptionHandlingService.createClientFriendlyException(
                        String.format("Error cannot add item, %s, with negative quantity of %d", product.getDescription(), item.getQuantity()),
                        ErrorType.BAD_REQUEST);
            }
            logger.trace("Successfully found product, {}", product);
        }
        try {
            petStoreOrderRepository.save(petStoreOrder);
        } catch(final Exception e) {
            throw exceptionHandlingService.createClientFriendlyException(String.format("There was an error storing order %s", petStoreOrder), e);
        }
    }

    public PetStoreOrder findOrder(final String id) throws Exception {
        try {
            return petStoreOrderRepository.findById(id).orElse(null);
        } catch(final Exception e) {
            throw exceptionHandlingService.createClientFriendlyException(String.format("There was an error looking up entry with id %s", id), e);
        }
    }

    public PetStoreOrderDetails computeOrderDetails(final String id) throws Exception {
        final PetStoreOrder petStoreOrder = findOrder(id);
        if(Objects.isNull(petStoreOrder)) {
            throw exceptionHandlingService.createClientFriendlyException(
                    String.format("We're sorry, the order with id %s could not be found in our system. Please try again.", id),
                    ErrorType.NOT_FOUND);
        }
        final Set<PetStoreItem> petStoreItems = petStoreOrder.getItems();
        float totalCost = 0f;
        for(final PetStoreItem petStoreItem: petStoreItems) {
            final PetStoreProduct petStoreProduct = petStoreProductService.findPetStoreProduct(petStoreItem.getProductId());
            totalCost += (petStoreProduct.getCostInDollars() * petStoreItem.getQuantity());
        }
        return createOrderDetails()
                .setPetStoreOrder(petStoreOrder)
                .setTotalCost(totalCost);
    }

    private PetStoreOrderDetails createOrderDetails() {
        return new PetStoreOrderDetails();
    }

}
