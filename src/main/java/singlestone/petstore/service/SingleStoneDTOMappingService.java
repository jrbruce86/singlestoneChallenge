package singlestone.petstore.service;

import org.springframework.stereotype.Service;
import singlestone.petstore.dto.PetStoreProductDTO;
import singlestone.petstore.model.PetStoreProduct;

/**
 * Service that maps data to/from singlestone data transfer objects
 */
 @Service
public class SingleStoneDTOMappingService {

    /**
     * Converts incoming petstore product transfer object to internal data structure
     * @param petStoreProductDTO the dto
     * @return the internal datastructure
     */
    public PetStoreProduct fromDTO(final PetStoreProductDTO petStoreProductDTO) {
        return new PetStoreProduct()
            .setCostInDollars(petStoreProductDTO.getPrice())
            .setDescription(petStoreProductDTO.getName())
            .setPetStoreProductId(String.valueOf(petStoreProductDTO.getId()));
    }

}
