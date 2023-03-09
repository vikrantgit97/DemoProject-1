package com.example.service;

import com.example.elasticdb.ProductRepoElastic;
import com.example.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceElastic {

    @Autowired
    private ProductRepoElastic productRepository;

    public Optional<Product> getProductById(Integer productCode) {
        return productRepository.findById(productCode);
    }

    public
    List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }
}

