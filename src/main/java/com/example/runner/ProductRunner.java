package com.example.runner;

import com.example.entity.Product;
import com.example.repository.ProductRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Order(3)
public class ProductRunner implements CommandLineRunner {

    @Autowired
    private ProductRepo productRepo;
    @Override
    public void run(String... args) throws Exception {

        Faker faker=new Faker(new Locale("en-IND"));
        if (productRepo.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setProductName(faker.commerce().productName());
                product.setProductDescription(faker.lorem().sentence());
                product.setQuantityInStock(faker.number().numberBetween(1, 100));
                product.setPrice(faker.number().randomDouble(2, 100, 1000));
                productRepo.save(product);
            }
        }
    }
}
