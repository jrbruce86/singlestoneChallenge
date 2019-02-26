package singlestone.petstore.service.productService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import singlestone.petstore.dto.PetStoreProductDTO;
import singlestone.petstore.dto.SingleStoneDTO;
import singlestone.petstore.model.ErrorType;
import singlestone.petstore.model.PetStoreProduct;
import singlestone.petstore.service.ExceptionHandlingService;
import singlestone.petstore.service.SingleStoneDTOMappingService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Pet store product service that integrates with the external endpoint defined by singlestone in order to retrieve the data
 */
 @Service
public class ExternalPetStoreProductService implements PetStoreProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String productDetailsUrl = "https://vrwiht4anb.execute-api.us-east-1.amazonaws.com/default/product/%s";
    private static final String allProductsUrl = "https://vrwiht4anb.execute-api.us-east-1.amazonaws.com/default/product";

    private final ObjectMapper objectMapper = new ObjectMapper(); // Note: would rather test this indirectly by spying

    private final SingleStoneDTOMappingService singleStoneDTOMappingService;
    private final ExceptionHandlingService exceptionHandlingService;

    public ExternalPetStoreProductService(final SingleStoneDTOMappingService singleStoneDTOMappingService,
                                          final ExceptionHandlingService exceptionHandlingService) {
        this.singleStoneDTOMappingService = singleStoneDTOMappingService;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @Override
    public Set<PetStoreProduct> getAvailableProducts() throws Exception {
        final RestTemplate restTemplate = new RestTemplate(); // Note: this does not open new connection (ok to call repeatedly)
        final SingleStoneDTO<Set<PetStoreProductDTO>> singleStoneDTO = restTemplate.exchange(allProductsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SingleStoneDTO<Set<PetStoreProductDTO>>>() {
                })
                .getBody();
        if(singleStoneDTO.getStatusCode() != 200) {
            throw exceptionHandlingService.createClientFriendlyException(
                String.format("Error could not retrieve available products from external endpoint, got status code %s", singleStoneDTO.getStatusCode()),
                ErrorType.NOT_FOUND);
        }
        return singleStoneDTO
                .getBody()
                .stream()
                .map(petStoreProductDTO -> singleStoneDTOMappingService.fromDTO(petStoreProductDTO))
                .collect(Collectors.toSet());
    }

    @Override
    public PetStoreProduct findPetStoreProduct(String productId) throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final String detailUrl = String.format(productDetailsUrl, productId);
        final SingleStoneDTO<PetStoreProductDTO> singleStoneDTO = restTemplate.exchange(
                detailUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SingleStoneDTO<PetStoreProductDTO>>() {
                })
                .getBody();
        logger.info("Got the following dto from url, {}: {}", detailUrl, singleStoneDTO);
        if(singleStoneDTO.getStatusCode() != 200) {
            throw exceptionHandlingService.createClientFriendlyException(
                String.format("Error could not retrieve product details for product %s from endpoint %s, got status code %s", productId, detailUrl, singleStoneDTO.getStatusCode()),
                ErrorType.NOT_FOUND);
        }
        return singleStoneDTOMappingService.fromDTO(restTemplate.exchange(
                String.format(productDetailsUrl, productId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SingleStoneDTO<PetStoreProductDTO>>() {
                })
                .getBody()
                .getBody());
    }

}
