package com.example.controller;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderRepo;
import com.example.repository.ProductRepo;
import com.example.service.OrderDetailsServiceImpl;
import com.example.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private ProductRepo productRepository;

    @PostMapping("/{customerId}/{productCode}")
    public ResponseEntity<Order>createOrdr(@PathVariable Integer customerId,@RequestBody Order order,@PathVariable Integer productCode){
        return ResponseEntity.ok().body(orderService.createOrdr(customerId,order,productCode));
    }
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Integer orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
            Customer customer = customerRepository.findById(order.getCustomerNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer "+order.getCustomerNumber()));
            order.setCustomer(customer);
            order.setOrderDetails(order.getOrderDetails());
            Order savedOrder = orderRepository.save(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Integer orderId,
                                             @Valid @RequestBody Order orderDetails) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

        order.setOrderDate(orderDetails.getOrderDate());
        order.setShippedDate(orderDetails.getShippedDate());
        order.setStatus(orderDetails.getStatus());
        order.setComments(orderDetails.getComments());

        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/orders/{id}")
    public Map<String, Boolean> deleteOrders(@PathVariable(value = "id") Integer orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrders();
    }

    @PostMapping("/")
    public void createOrder(@RequestBody Order order, List<OrderDetails> orderDetails) {
        orderService.createOrder(order, orderDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderByIds(@PathVariable(value = "id") Integer orderNumber) {
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
    public ResponseEntity<Order> updateOrders(@PathVariable(value = "id") Integer orderNumber,
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
