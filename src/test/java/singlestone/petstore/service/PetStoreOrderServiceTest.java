package singlestone.petstore.service;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import singlestone.petstore.exception.ClientFacingException;
import singlestone.petstore.model.*;
import singlestone.petstore.persist.PetStoreOrderRepository;
import singlestone.petstore.persist.PetStoreProductRepository;
import singlestone.petstore.service.productService.PetStoreProductService;

import java.util.Optional;

public class PetStoreOrderServiceTest {

    // The mocks
    private PetStoreOrderRepository petStoreOrderRepositoryMock;
    private ExceptionHandlingService exceptionHandlingServiceMock;
    private PetStoreProductService petStoreProductServiceMock;

    // The object being tested
    private PetStoreOrderService objectUnderTest;

    @Before
    public void createDefaultMocks() {
        petStoreOrderRepositoryMock = Mockito.mock(PetStoreOrderRepository.class);
        exceptionHandlingServiceMock = mockExceptionHandlingService();
        petStoreProductServiceMock = Mockito.mock(PetStoreProductService.class);
        initObjectUnderTest();
    }

    void initObjectUnderTest() {
        objectUnderTest = new PetStoreOrderService(
                petStoreOrderRepositoryMock,
                exceptionHandlingServiceMock,
                petStoreProductServiceMock);

    }

    /**
     * Test to make sure the correct details produced for order of two items
     */
    @Test
    public void computeOrderDetailsSuccess() throws Exception {

        /**
         * Setup
         */
        // Setup the order
        final String customerId = "jake";

        final PetStoreOrder petStoreOrderMock = Mockito.mock(PetStoreOrder.class);
        Mockito.when(petStoreOrderMock.getCustomerId()).thenReturn(customerId);
        final Float item1Cost = 55f;
        final Integer item1Quantity = 3;
        final Float item2Cost = 63f;
        final Integer item2Quantity = 5;
        final PetStoreItem item1Mock = mockPetStoreOrderItem(1L, "Product1", item1Quantity, item1Cost, "Product1Description");
        final PetStoreItem item2Mock = mockPetStoreOrderItem(2L, "Product2", item2Quantity, item2Cost, "Product2Description");
        Mockito.when(petStoreOrderMock.getItems()).thenReturn(Sets.newHashSet(item1Mock, item2Mock));
        Mockito.when(petStoreOrderRepositoryMock.findById(customerId)).thenReturn(Optional.of(petStoreOrderMock));

        /**
         * Exercise
         */
        final PetStoreOrderDetails result = objectUnderTest.computeOrderDetails(customerId);

        /**
         * Verify
         */
        // Verify the computation was correct
        System.out.println("The total cost: " + result.getTotalCost());
        Assert.assertTrue(result.getTotalCost() == (item1Cost * item1Quantity) + (item2Cost * item2Quantity));
    }

    /**
     * Test to make sure successful order storage
     */
    @Test
    public void storeOrderNormalSuccess() throws Exception {

        /**
         * Setup
         */
        // Setup the order
        final String customerId = "jake";

        // Mock the order
        final PetStoreOrder petStoreOrderMock = Mockito.mock(PetStoreOrder.class);
        Mockito.when(petStoreOrderMock.getCustomerId()).thenReturn(customerId);
        final Float item1Cost = -55f;
        final Integer item1Quantity = 3;
        final Float item2Cost = 63f;
        final Integer item2Quantity = 5;
        final PetStoreItem item1Mock = mockPetStoreOrderItem(1L, "Product1", item1Quantity, item1Cost, "Product1Description");
        final PetStoreItem item2Mock = mockPetStoreOrderItem(2L, "Product2", item2Quantity, item2Cost, "Product2Description");
        Mockito.when(petStoreOrderMock.getItems()).thenReturn(Sets.newHashSet(item1Mock, item2Mock));
        Mockito.when(petStoreOrderRepositoryMock.findById(customerId)).thenReturn(Optional.of(petStoreOrderMock));

        // Mock the exception handler service
        final ClientFacingException exceptionMock = Mockito.mock(ClientFacingException.class);
        Mockito.when(exceptionHandlingServiceMock.createClientFriendlyExceptionMain(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(exceptionMock);

        /**
         * Exercise
         */
        objectUnderTest.storeOrderDetails(petStoreOrderMock);

        /**
         * Verify
         */
        // Verify save method invoked on repository once
        Mockito.verify(petStoreOrderRepositoryMock, Mockito.times(1)).save(petStoreOrderMock);
        // Verify no exception thrown by exception handler service
        Mockito.verify(exceptionHandlingServiceMock, Mockito.never()).createClientFriendlyExceptionMain(Mockito.any(), Mockito.any(), Mockito.any());
    }

    /**
     * Test to make sure correct error handling for invalid cost entry attempt
     */
    @Test
    public void storeOrderInvalidCostFail() throws Exception {

        /**
         * Setup
         */
        // Setup the order
        final String customerId = "jake";

        // Mock the order
        final PetStoreOrder petStoreOrderMock = Mockito.mock(PetStoreOrder.class);
        Mockito.when(petStoreOrderMock.getCustomerId()).thenReturn(customerId);
        final Float item1Cost = 55f;
        final Integer item1Quantity = -3;
        final Float item2Cost = 63f;
        final Integer item2Quantity = 5;
        final PetStoreItem item1Mock = mockPetStoreOrderItem(1L, "Product1", item1Quantity, item1Cost, "Product1Description");
        final PetStoreItem item2Mock = mockPetStoreOrderItem(2L, "Product2", item2Quantity, item2Cost, "Product2Description");
        Mockito.when(petStoreOrderMock.getItems()).thenReturn(Sets.newHashSet(item1Mock, item2Mock));
        Mockito.when(petStoreOrderRepositoryMock.findById(customerId)).thenReturn(Optional.of(petStoreOrderMock));

        /**
         * Exercise
         */
        try {
            objectUnderTest.storeOrderDetails(petStoreOrderMock);
        } catch(final Throwable t) {

            /**
             * Verify - verify exception invoked correct number of times
             */
            Mockito.verify(exceptionHandlingServiceMock, Mockito.times(1)).createClientFriendlyExceptionMain(Mockito.any(), Mockito.any(ErrorType.class), Mockito.any(Exception.class));
            return;
        }

        /**
         * Verify
         */
        // Verify the exception was thrown
        Assert.fail("Test failure: Exception was expected to be thrown but was not.");
    }

    /**
     * Helper methods
     */
    private PetStoreItem mockPetStoreOrderItem(final Long itemId,
                                       final String productId,
                                       final Integer quantity,
                                       final Float cost,
                                       final String description) throws Exception {
        final PetStoreProduct petStoreProduct1Mock = createProductMock(productId, description, cost);
        Mockito.when(petStoreProductServiceMock.findPetStoreProduct(productId)).thenReturn(petStoreProduct1Mock);
        return createItemMock(itemId, productId, quantity);
    }

    private PetStoreItem createItemMock(final Long id, final String productId, final Integer quantity) {
        final PetStoreItem mock = Mockito.mock(PetStoreItem.class);
        Mockito.when(mock.getItemId()).thenReturn(id);
        Mockito.when(mock.getProductId()).thenReturn(productId);
        Mockito.when(mock.getQuantity()).thenReturn(quantity);
        return mock;
    }

    private PetStoreProduct createProductMock(final String productId, final String description, final Float cost) {
        final PetStoreProduct mock = Mockito.mock(PetStoreProduct.class);
        Mockito.when(mock.getPetStoreProductId()).thenReturn(productId);
        Mockito.when(mock.getDescription()).thenReturn(description);
        Mockito.when(mock.getCostInDollars()).thenReturn(cost);
        return mock;
    }

    private ExceptionHandlingService mockExceptionHandlingService() {
        final ClientFacingException exceptionMock = Mockito.mock(ClientFacingException.class);
        final ServerResponseFactoryService serverResponseFactoryServiceMock = Mockito.mock(ServerResponseFactoryService.class);
        exceptionHandlingServiceMock =  Mockito.spy(new ExceptionHandlingService(serverResponseFactoryServiceMock));
        Mockito.when(exceptionHandlingServiceMock.createClientFriendlyExceptionMain(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(exceptionMock);
        return exceptionHandlingServiceMock;
    }
}