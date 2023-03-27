package com.example.service;

import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
@Slf4j
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
        log.info("Product Service Implementation : deleteProduct() method");
        productRepository.deleteById(productCode);
    }

    public Product incrementDecrement(Integer productCode, Double amount) {
        Product existingProduct = getProductById(productCode);
        if (existingProduct != null) {
            Double increaseDecrease = existingProduct.getPrice() + amount;
            //Double newPrice =  amount;        //to set new price
            existingProduct.setPrice(increaseDecrease);
            return productRepository.save(existingProduct);
        }
        return null;
    }


    public Product partialUpdate(Integer productCode, Map<String, Object> updates) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist"));

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

    public List<Product> getByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    public List<Product> getByProductNamePrefix(String prefixn) {
        return productRepository.findByProductNameStartingWith(prefixn);
    }

    public List<Product> getByProductNameWord(String prefix) {
        return productRepository.findByProductNameContaining(prefix);
    }

    public List<Product> sortByProductNameAsc() {
        return productRepository.findAllByOrderByProductNameAsc();
    }

    public List<Product> sortByProductNameDsc() {
        return productRepository.findAllByOrderByProductNameDesc();
    }

    public List<Product> searchBy(String theName) {

        List<Product> results = null;

        if (theName != null && (theName.trim().length() > 0)) {
            results = productRepository.findByProductNameContainsAllIgnoreCase(theName);
        } else {
            results = sortByProductNameAsc();
        }
        return results;
    }


    /*// method to increment quantityInStock by a given amount
    default void incrementQuantityInStock(Integer productCode, Integer quantity) {
        ProductDto product = findById(productCode).orElseThrow(() -> new IllegalArgumentException("Invalid product code"));
        product.setQuantityInStock(product.getQuantityInStock() + quantity);
        save(product);
    }

    // method to decrement quantityInStock by a given amount
    default void decrementQuantityInStock(Integer productCode, Integer quantity) {
        ProductDto product = findById(productCode).orElseThrow(() -> new IllegalArgumentException("Invalid product code"));
        Integer currentQuantity = product.getQuantityInStock();
        if (currentQuantity >= quantity) {
            product.setQuantityInStock(currentQuantity - quantity);
            save(product);
        } else {
            throw new IllegalArgumentException("Insufficient quantity in stock");
        }
    }*/


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

