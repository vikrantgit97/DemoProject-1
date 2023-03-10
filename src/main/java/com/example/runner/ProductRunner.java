package com.example.runner;

import com.example.entity.Product;
import com.example.repository.ProductRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ProductRunner implements CommandLineRunner {

    @Autowired
    private ProductRepo productRepo;
    @Override
    public void run(String... args) throws Exception {

        Faker faker=new Faker(new Locale("en-IND"));
        for(int i=0;i<100;i++){
            Product product = new Product();
            product.setProductName(faker.commerce().productName());
            product.setProductDescription(faker.lorem().sentence());
            product.setQuantityInStock(faker.number().numberBetween(1, 100));
            product.setPrice(faker.number().randomDouble(2, 100, 1000));
            productRepo.save(product);
        }
    }
}
