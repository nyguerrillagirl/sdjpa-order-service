package guru.springframework.orderservice.bootstrap;

import guru.springframework.orderservice.domain.Category;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        OrderHeader orderHeader = orderHeaderRepository.findById(1L).get();

        orderHeader.getOrderLines().forEach(ol -> {
            System.out.println(ol.getProduct().getDescription());

            ol.getProduct().getCategories().forEach(cat -> {
                System.out.println("HERE!");
                System.out.println(cat.getDescription());
            });
        });
    }
}
