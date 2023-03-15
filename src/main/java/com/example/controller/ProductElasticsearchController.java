package com.example.controller;

import com.example.entity.Product;
import com.example.service.ProductServiceElastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductElasticsearchController {
    /*@Autowired
    private ProductServiceElastic productService;


    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getOne/{productCode}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productCode) {
        Optional<Product> product = productService.getProductById(productCode);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

}
