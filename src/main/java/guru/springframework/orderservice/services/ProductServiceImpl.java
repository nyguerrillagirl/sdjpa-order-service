package guru.springframework.orderservice.services;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private final ProductRepository productRepository;



    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product updateQOH(Long id, Integer quantityOnHand) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        product.setQuantityOnHand(quantityOnHand);
        return productRepository.saveAndFlush(product);
    }
}
