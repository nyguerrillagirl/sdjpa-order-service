package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        Product newProduct = new Product();
        newProduct.setDescription("Sunglasses");

        Product savedProduct = productRepository.save(newProduct);

        // test it was saved
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        // check by fetching
        Product fetchedProduct = productRepository.getReferenceById(savedProduct.getId());
        assertNotNull(fetchedProduct);
        assertEquals("Sunglasses", fetchedProduct.getDescription());
        assertNotNull(fetchedProduct.getCreatedDate());
        assertNotNull(fetchedProduct.getLastModifiedDate());
    }
}
