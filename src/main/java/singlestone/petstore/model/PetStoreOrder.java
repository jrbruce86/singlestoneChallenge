package singlestone.petstore.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PetStoreOrder")
public class PetStoreOrder {

    @Id
    private String customerId;

    @Embedded
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<PetStoreItem> items = new HashSet<>();

    public PetStoreOrder() {
    }

    public PetStoreOrder(String customerId, Set<PetStoreItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<PetStoreItem> getItems() {
        return items;
    }

    public void setItems(HashSet<PetStoreItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PetStoreOrder{" +
                "customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }
}
