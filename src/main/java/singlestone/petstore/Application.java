package singlestone.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import singlestone.petstore.persist.PetStoreOrderRepository;
import singlestone.petstore.persist.PetStoreProductRepository;

@SpringBootApplication
@ComponentScan("singlestone.petstore")
@ComponentScan(basePackages = {"persist"})
@EnableJpaRepositories(basePackages = {"singlestone.petstore.persist"})
@EntityScan("singlestone.petstore.model")
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {PetStoreOrderRepository.class, PetStoreProductRepository.class})
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
