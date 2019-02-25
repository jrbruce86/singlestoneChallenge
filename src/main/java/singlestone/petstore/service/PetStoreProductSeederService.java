package singlestone.petstore.service;

import org.springframework.stereotype.Service;
import singlestone.petstore.model.PetStoreProduct;
import singlestone.petstore.persist.PetStoreProductRepository;

import javax.annotation.PostConstruct;

@Service
public class PetStoreProductSeederService {

    private final PetStoreProductRepository petStoreProductRepository;

    PetStoreProductSeederService(final PetStoreProductRepository petStoreProductRepository) {
        this.petStoreProductRepository = petStoreProductRepository;
    }

    @PostConstruct
    public void seedPetStoreProducts() {
        petStoreProductRepository.save(new PetStoreProduct().setPetStoreProductId("8ed0e6f7").setDescription("dog food").setCostInDollars(5f));
        petStoreProductRepository.save(new PetStoreProduct().setPetStoreProductId("c0258525").setDescription("dead parrot").setCostInDollars(6.5f));
        petStoreProductRepository.save(new PetStoreProduct().setPetStoreProductId("0a207870").setDescription("cat nip").setCostInDollars(2f));
    }

}
