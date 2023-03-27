package com.example.controller;

import com.example.dto.ErrorResponse;
import com.example.dto.SuccessResponse;
import com.example.entity.Product;
import com.example.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{productCode}")
    public ResponseEntity<?> getProductById(@PathVariable("productCode") Integer productCode) {
        if (productService.getProductById(productCode) != null) {
            return ResponseEntity.ok(productService.getProductById(productCode));
        } else {
            return ErrorResponse.error("Product not found", "404");
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return SuccessResponse.ok("Product created successfully", createdProduct);
    }


    @PutMapping("{productCode}")
    public Product updateProduct(@PathVariable Integer productCode, @RequestBody Product product) {
        return productService.updateProduct(productCode, product);
    }


    @DeleteMapping("/{productCode}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productCode") Integer productCode) {
        if (productService.getProductById(productCode) != null) {
            productService.deleteProduct(productCode);
            return SuccessResponse.ok("Product deleted successfully", null);
        } else {
            return ErrorResponse.error("Product not found", "Error_101");
        }
    }


    @PostMapping("/{productCode}/price/{amount}")
    public ResponseEntity<Product> priceIncrementDecrement(@PathVariable Integer productCode, @PathVariable Double amount) {
        Product product = productService.incrementDecrement(productCode, amount);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<List<Product>> findProductByname(@PathVariable String productName) {
        return new ResponseEntity<>(productService.getByName(productName), HttpStatus.OK);
    }

    @GetMapping("/names/{productName}")
    public ResponseEntity<List<Product>> findProductBynamePrefix(@PathVariable String productNamePrefix) {
        return new ResponseEntity<>(productService.getByProductNamePrefix(productNamePrefix), HttpStatus.OK);
    }

    @GetMapping("/word/{productName}")
    public ResponseEntity<List<Product>> findProductBynameCOntaining(@PathVariable String productNamePrefix) {
        return new ResponseEntity<>(productService.getByProductNameWord(productNamePrefix), HttpStatus.OK);
    }

    @GetMapping("/word")
    public ResponseEntity<List<Product>> findProductBynameCOntaining() {
        return new ResponseEntity<>(productService.sortByProductNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/words")
    public ResponseEntity<List<Product>> findProductBynameDsc() {
        return new ResponseEntity<>(productService.sortByProductNameDsc(), HttpStatus.OK);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<Product>> searchProductBynameContains(@PathVariable String search) {
        return new ResponseEntity<>(productService.searchBy(search), HttpStatus.OK);
    }
}
