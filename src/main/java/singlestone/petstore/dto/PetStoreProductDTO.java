package singlestone.petstore.dto;

/**
 * DTO expected from SingleStone containing petstore product details
 */
public class PetStoreProductDTO {
    private Long id;
    private String name;
    private Float price;

    public Long getId() {
        return id;
    }

    public PetStoreProductDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PetStoreProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public PetStoreProductDTO setPrice(Float price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "PetStoreProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}