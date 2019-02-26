package singlestone.petstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import singlestone.petstore.service.productService.ExternalPetStoreProductService;
import singlestone.petstore.service.productService.PetStoreProductService;

/**
 * Spring dependency injection configuration
 */
@Configuration
public class DependencyConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ApplicationContext applicationContext;

    DependencyConfig(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean @Primary
    public PetStoreProductService getPetStoreProductServiceImplementation() {
        return applicationContext.getBean(ExternalPetStoreProductService.class);
    }

}
