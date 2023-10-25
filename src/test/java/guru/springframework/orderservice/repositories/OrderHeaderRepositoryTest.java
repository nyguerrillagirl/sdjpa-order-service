package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.OrderLine;
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

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("Lorraine Figueroa");

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);

        orderHeader.setOrderLines(Set.of(orderLine));
        orderLine.setOrderHeader(savedOrder);

        // test it was saved
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);
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
