package com.example.controller;

import com.example.entity.Order;
import com.example.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Integer orderNumber) {
        Order order = orderService.getOrderById(orderNumber);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public Order addOrder(@Valid @RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Integer orderNumber,
                                             @Valid @RequestBody Order order) {
        Order existingOrder = orderService.getOrderById(orderNumber);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        Order updatedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Integer orderNumber) {
        Order existingOrder = orderService.getOrderById(orderNumber);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteOrder(orderNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/place-order")
    public ResponseEntity<Void> placeOrder(@Valid @RequestBody Order order) {
        orderService.placeOrder(order);
        return ResponseEntity.ok().build();
    }
}
