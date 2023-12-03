package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import guru.springframework.orderservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {ProductService.class})
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test void testGetCategory() {
        Product product = productRepository.findByDescription("PRODUCT1").get();

        assertNotNull(product);
        assertNotNull(product.getCategories());
    }
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

    @Test
    void testQuanityOnHand() {
        // Create a new product
        Product newProduct = new Product();
        newProduct.setDescription("Udemy course gift card");
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setQuantityOnHand(100);

        Product savedProduct = productService.saveProduct(newProduct);

        Product udemyGiftCards = productRepository.findById(savedProduct.getId()).get();
        assertEquals(100, udemyGiftCards.getQuantityOnHand());

        // Now let's update
        Product updatedPrduct = productService.updateQOH(savedProduct.getId(), 90);

        Product currentUdemyGiftCards = productRepository.findById(updatedPrduct.getId()).get();
        assertEquals(90, currentUdemyGiftCards.getQuantityOnHand());
    }
}
