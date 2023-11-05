package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.*;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

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
        Customer aCustomer = new Customer();
        aCustomer.setCustomerName("Samantha Neill");
        Customer savedCustomer = customerRepository.save(aCustomer);

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(savedCustomer);


        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        //OrderApproval savedApproval = orderApprovalRepository.save(orderApproval);

        orderHeader.setOrderApproval(orderApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        // test it was saved
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        // Let's fetch the saved order
        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());
        // Let's check that order_line exists
        assertTrue(fetchedOrder.getOrderLines().size() > 0);
    }
    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        Customer aCustomer = new Customer();
        aCustomer.setCustomerName("Samantha Neill");
        Customer savedCustomer = customerRepository.save(aCustomer);

        orderHeader.setCustomer(savedCustomer);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        // test it was saved
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        // check by fetching
        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());
        assertNotNull(fetchedOrder);
        assertEquals("Samantha Neill", fetchedOrder.getCustomer().getCustomerName());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }
}
