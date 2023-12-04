package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void testBadEmail() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("RBG");
        newCustomer.setEmail("nyguerrillagirl");

        assertThrows(
                jakarta.validation.ConstraintViolationException.class,
                () -> {
                    Customer savedCustomer = customerRepository.save(newCustomer);
                }
        );

    }
    @Test
    void testBadPhone() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("RBG");
        newCustomer.setPhone("555-999-5555 BLAH BLAH BLAH BLAH BLAH");

        assertThrows(
                jakarta.validation.ConstraintViolationException.class,
                () -> {
                    Customer savedCustomer = customerRepository.save(newCustomer);
                }
        );

    }


}
