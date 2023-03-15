package com.example.service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl {


    @Autowired
    private CustomerServiceImpl customerRepo;
    @Autowired
    private OrderRepo orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderNumber) {
        return orderRepository.findById(orderNumber).orElse(null);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        Order existingOrder = getOrderById(order.getOrderNumber());
        if (existingOrder == null) {
            return null;
        }
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setShippedDate(order.getShippedDate());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setComments(order.getComments());
        existingOrder.setCustomerNumber(order.getCustomerNumber());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Integer orderNumber) {
        orderRepository.deleteById(orderNumber);
    }


    @Transactional
    public void placeOrder(Order order) {
        Customer customer = customerRepo.getCustomerById(order.getCustomerNumber());
        // Set the customer object to the order
        //order.setCustomer(customer);
        order.setComments("required trimmer");
        addOrder(order);
    }
}

