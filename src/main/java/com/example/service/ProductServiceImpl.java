package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public Product partialUpdate(Integer productCode, Map<String, Object> updates) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productCode", productCode));

        // Use ReflectionUtils to update the product fields with the values in the updates Map
        ReflectionUtils.doWithFields(Product.class, field -> {
            String fieldName = field.getName();
            if (updates.containsKey(fieldName)) {
                field.setAccessible(true);
                Object newValue = updates.get(fieldName);
                field.set(product, newValue);
            }
        });
        return productRepository.save(product);
    }

    /*public Product partialUpdate( Integer productCode,  Map<String, Object> product) {
        Optional<Product> existingProduct = productRepository.findById(productCode);
        if (existingProduct.isPresent()) {
            product.forEach((k,v)->{
                Field field = ReflectionUtils.findField(Product.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingProduct.get(),v);
            });
            Product getProduct = getProductById(productCode);
            getProduct.setProductName(product.getProductName());
            getProduct.setProductDescription(product.getProductDescription());
            getProduct.setQuantityInStock(product.getQuantityInStock());
            getProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }*/

    /*@PatchMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer productId,
                                                 @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        if (productDetails.getProductName() != null && !productDetails.getProductName().isEmpty()) {
            product.setProductName(productDetails.getProductName());
        }

        if (productDetails.getProductDescription() != null && !productDetails.getProductDescription().isEmpty()) {
            product.setProductDescription(productDetails.getProductDescription());
        }

        if (productDetails.getQuantityInStock() != null) {
            product.setQuantityInStock(productDetails.getQuantityInStock());
        }

        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }

        final Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }*/
}

