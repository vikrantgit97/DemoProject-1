package com.example.service;

import com.example.entity.OrderDetails;
import com.example.exception.OrderDetailsRepoException;
import com.example.repository.OrderDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailsServiceImpl {

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    public OrderDetails saveOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepo.save(orderDetails);
    }


    public List<OrderDetails> saveAll(List<OrderDetails> users) {
        return orderDetailsRepo.saveAll(users);
    }

    public OrderDetails updateUser(int id, OrderDetails orderDetails) {
        try {
            OrderDetails orderDetails1 = orderDetailsRepo.findById(id).
                    orElseThrow(() -> (new OrderDetailsRepoException("OrderDetails not Exist ")));
            return orderDetailsRepo.save(orderDetails1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public OrderDetails getOne(int id)  {
        OrderDetails orderDetails = null;
        try {
            orderDetails = orderDetailsRepo.findById(id).
                    orElseThrow(()->(new OrderDetailsRepoException("Order Details not Exist "+id)));
        } catch (OrderDetailsRepoException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }


    public List<OrderDetails> getAllUser() {
        return orderDetailsRepo.findAll();
    }
    public void deleteUser(int id) {
        OrderDetails orderDetails = null;
        try {
            orderDetails = orderDetailsRepo.findById(id).
                    orElseThrow(()->(new OrderDetailsRepoException("Order Details not Exist ")));

        } catch (OrderDetailsRepoException e) {
            throw new RuntimeException(e);
        }
        orderDetailsRepo.delete(orderDetails);;
    }
}
