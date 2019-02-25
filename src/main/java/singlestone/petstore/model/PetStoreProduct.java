package singlestone.petstore.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PetStoreProduct")
public class PetStoreProduct {

    @Id
    private String petStoreProductId;

    private String description;
    private Float costInDollars;

    public PetStoreProduct() {

    }

    public String getPetStoreProductId() {
        return petStoreProductId;
    }

    public PetStoreProduct setPetStoreProductId(String petStoreProductId) {
        this.petStoreProductId = petStoreProductId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PetStoreProduct setDescription(String description) {
        this.description = description;
        return this;
    }

    public Float getCostInDollars() {
        return costInDollars;
    }

    public PetStoreProduct setCostInDollars(Float costInDollars) {
        this.costInDollars = costInDollars;
        return this;
    }

    @Override
    public String toString() {
        return "PetStoreProduct{" +
                "petStoreProductId='" + petStoreProductId + '\'' +
                ", description='" + description + '\'' +
                ", costInDollars=" + costInDollars +
                '}';
    }
}
