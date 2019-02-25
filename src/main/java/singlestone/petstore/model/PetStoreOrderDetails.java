package singlestone.petstore.model;

public class PetStoreOrderDetails {
    private PetStoreOrder petStoreOrder;
    private Float totalCost;

    public PetStoreOrder getPetStoreOrder() {
        return petStoreOrder;
    }

    public PetStoreOrderDetails setPetStoreOrder(PetStoreOrder petStoreOrder) {
        this.petStoreOrder = petStoreOrder;
        return this;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public PetStoreOrderDetails setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    @Override
    public String toString() {
        return "PetStoreOrderDetails{" +
                "petStoreOrder=" + petStoreOrder +
                ", totalCost=" + totalCost +
                '}';
    }
}
