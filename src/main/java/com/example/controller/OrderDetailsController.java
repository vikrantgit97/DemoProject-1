package com.example.controller;

import com.example.entity.OrderDetails;
import com.example.service.OrderDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
public class OrderDetailsController {
/*

    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    @GetMapping
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsByOrderNumber(@PathVariable("id") Integer orderNumber) {
        OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderNumber(orderNumber);
        if (orderDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetails);
    }

    @PostMapping
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails) {
        OrderDetails newOrderDetails = orderDetailsService.createOrderDetails(orderDetails);
        if (newOrderDetails == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetails(@PathVariable("id") Integer orderNumber,
                                                           @RequestBody OrderDetails orderDetails) {
        if (!orderNumber.equals(orderDetails.getOrderNumber())) {
            return ResponseEntity.badRequest().build();
        }
        OrderDetails updatedOrderDetails = orderDetailsService.updateOrderDetails(orderNumber, orderDetails);
        if (updatedOrderDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrderDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable("id") Integer orderNumber) {
        orderDetailsService.deleteOrderDetails(orderNumber);
        return ResponseEntity.noContent().build();
    }

}
*/}
