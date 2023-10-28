package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.OrderLine;
import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("test product");
        product = productRepository.saveAndFlush(newProduct);
    }
    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("Lorraine Figueroa");


        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.setOrderLines(Set.of(orderLine));
        orderLine.setOrderHeader(orderHeader);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        // test it was saved
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        // Let's fetch the saved order
        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());
        // Let's check that order_line exists
        assertNotNull(fetchedOrder.getOrderLines().size() > 0);
    }
    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("Lorraine Figueroa");

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        // test it was saved
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        // check by fetching
        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());
        assertNotNull(fetchedOrder);
        assertEquals("Lorraine Figueroa", fetchedOrder.getCustomer());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }
}
