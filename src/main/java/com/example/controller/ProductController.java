package com.example.controller;

import com.example.entity.Product;
import com.example.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productCode}")
    public Product getProductById(@PathVariable Integer productCode) {
        return productService.getProductById(productCode);
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{productCode}")
    public Product updateProduct(@PathVariable Integer productCode, @RequestBody Product product) {
        return productService.updateProduct(productCode, product);
    }

    @DeleteMapping("/{productCode}")
    public void deleteProduct(@PathVariable Integer productCode) {
        productService.deleteProduct(productCode);
    }

    @PostMapping("/{productCode}/{amount}")
    public Product incrementDecrement(@PathVariable Integer productCode, @PathVariable Double amount) {
        return productService.incrementDecrement(productCode, amount);
    }

    @PostMapping("/price/{productCode}/{amount}")
    public ResponseEntity<Product> priceIncrementDecrement(@PathVariable Integer productCode,@PathVariable Double amount ){
        Product product = productService.incrementDecrement(productCode, amount);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }
}
