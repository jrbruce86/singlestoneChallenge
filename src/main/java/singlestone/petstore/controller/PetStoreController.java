package singlestone.petstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import singlestone.petstore.model.PetStoreOrder;
import singlestone.petstore.model.PetStoreOrderDetails;
import singlestone.petstore.model.PetStoreProduct;
import singlestone.petstore.service.PetStoreOrderService;
import singlestone.petstore.service.ServerResponseFactoryService;
import singlestone.petstore.service.productService.PetStoreProductService;

import java.util.Set;

@RestController
@RequestMapping("/default/product")
public class PetStoreController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PetStoreOrderService petStoreOrderService;
    private final ServerResponseFactoryService serverResponseFactoryService;
    private final PetStoreProductService petStoreProductService;

    /**
     * Constructor
     *
     * @param petStoreOrderService
     * @param serverResponseFactoryService
     * @param petStoreProductService
     */
    PetStoreController(final PetStoreOrderService petStoreOrderService,
                       final ServerResponseFactoryService serverResponseFactoryService,
                       final PetStoreProductService petStoreProductService) {
        this.petStoreOrderService = petStoreOrderService;
        this.serverResponseFactoryService = serverResponseFactoryService;
        this.petStoreProductService = petStoreProductService;
    }

    @PostMapping
    public ResponseEntity createOrder(final @RequestBody PetStoreOrder petStoreOrder) throws Exception {
        petStoreOrderService.storeOrderDetails(petStoreOrder);
        return serverResponseFactoryService.createServerResponse(
                HttpStatus.ACCEPTED,
                "Order saved successfully",
                petStoreOrder);
    }

    @GetMapping("{id}")
    public ResponseEntity getOrder(final @PathVariable("id") String id) throws Exception {
        final PetStoreOrderDetails petStoreOrderDetails = petStoreOrderService.computeOrderDetails(id);
        return serverResponseFactoryService.createServerPayloadOnlyResponse(
                HttpStatus.OK,
                petStoreOrderDetails);
    }

    @GetMapping
    public ResponseEntity getAvailableProducts() throws Exception {
        final Set<PetStoreProduct> availableProducts = petStoreProductService.getAvailableProducts();
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
