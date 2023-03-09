package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepo productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productCode) {
        return productRepository.findById(productCode).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productCode, Product product) {
        Product existingProduct = getProductById(productCode);
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setQuantityInStock(product.getQuantityInStock());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Integer productCode) {
        productRepository.deleteById(productCode);
    }

    public Product incrementDecrement(Integer productCode, Double amount) {
        Product existingProduct = getProductById(productCode);
        if (existingProduct != null) {
            Double newPrice = existingProduct.getPrice() + amount;
            existingProduct.setPrice(newPrice);
            return productRepository.save(existingProduct);
        }
        return null;
    }
}

