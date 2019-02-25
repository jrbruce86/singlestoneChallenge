package singlestone.petstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import singlestone.petstore.model.PetStoreOrder;
import singlestone.petstore.model.PetStoreOrderDetails;
import singlestone.petstore.model.PetStoreProduct;
import singlestone.petstore.service.PetStoreService;
import singlestone.petstore.service.ServerResponseFactoryService;

import java.util.Set;

@RestController
@RequestMapping("/default/product")
public class PetStoreController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PetStoreService petStoreService;
    private final ServerResponseFactoryService serverResponseFactoryService;

    /**
     * Constructor
     * @param petStoreService
     * @param serverResponseFactoryService
     */
    PetStoreController(final PetStoreService petStoreService,
                       final ServerResponseFactoryService serverResponseFactoryService) {
        this.petStoreService = petStoreService;
        this.serverResponseFactoryService = serverResponseFactoryService;
    }

    @PostMapping
    public ResponseEntity createOrder(final @RequestBody PetStoreOrder petStoreOrder) throws Exception {
        petStoreService.storeOrderDetails(petStoreOrder);
        return serverResponseFactoryService.createServerResponse(
                HttpStatus.ACCEPTED,
                "Order saved successfully",
                petStoreOrder);
    }

    @GetMapping("{id}")
    public ResponseEntity getOrder(final @PathVariable("id") String id) throws Exception {
        final PetStoreOrderDetails petStoreOrderDetails = petStoreService.computeOrderDetails(id);
        return serverResponseFactoryService.createServerPayloadOnlyResponse(
                HttpStatus.OK,
                petStoreOrderDetails);
    }

    @GetMapping
    public ResponseEntity getAvailableProducts() throws Exception {
        final Set<PetStoreProduct> availableProducts = petStoreService.getAvailableProducts();
        return serverResponseFactoryService.createServerPayloadOnlyResponse(
                HttpStatus.OK,
                availableProducts);
    }



    @GetMapping("/test/{id}")
    public ResponseEntity test(@PathVariable("id") String id) {
        return serverResponseFactoryService.createServerResponse(
                HttpStatus.ACCEPTED,
                "hello!");
    }
}
