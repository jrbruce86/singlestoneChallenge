package singlestone.petstore.model;

import javax.persistence.*;

@Entity
@Table(name = "PetStoreItem")
@Embeddable
public class PetStoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String productId;
    private Integer quantity;

    public PetStoreItem() {
    }

    public PetStoreItem(Long itemId, String productId, Integer quantity) {
        this.itemId = itemId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public PetStoreItem setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public PetStoreItem setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PetStoreItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "PetStoreItem{" +
                "itemId=" + itemId +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
