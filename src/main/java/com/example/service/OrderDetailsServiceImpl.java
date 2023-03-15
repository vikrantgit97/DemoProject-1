package com.example.service;

import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.OrderDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl {

    @Autowired
    private OrderDetailsRepo orderDetailsRepository;

    @Autowired
    private ProductServiceImpl productService;

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails getOrderDetailsByOrderNumber(Integer orderNumber) {
        return orderDetailsRepository.findById(orderNumber).orElse(null);
    }

    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        Product product = productService.getProductById(orderDetails.getProductCode());
        if (product != null && product.getQuantityInStock() >= orderDetails.getQuantityOrdered()) {
            product.setQuantityInStock(product.getQuantityInStock() - orderDetails.getQuantityOrdered());
            productService.updateProduct(product.getProductCode(), product);
            return orderDetailsRepository.save(orderDetails);
        }
        return null;
    }

    public OrderDetails updateOrderDetails(Integer orderNumber, OrderDetails orderDetails) {
        OrderDetails existingOrderDetails = getOrderDetailsByOrderNumber(orderNumber);
        if (existingOrderDetails != null) {
            existingOrderDetails.setProductCode(orderDetails.getProductCode());
            existingOrderDetails.setQuantityOrdered(orderDetails.getQuantityOrdered());
            existingOrderDetails.setPriceEach(orderDetails.getPriceEach());

            Product product = productService.getProductById(orderDetails.getProductCode());
            if (product != null && product.getQuantityInStock() >= orderDetails.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() + existingOrderDetails.getQuantityOrdered() - orderDetails.getQuantityOrdered());
                productService.updateProduct(product.getProductCode(), product);
                return orderDetailsRepository.save(existingOrderDetails);
            } else {
                return null;
            }
        }
        return null;
    }

    public void deleteOrderDetails(Integer orderNumber) {
        OrderDetails existingOrderDetails = getOrderDetailsByOrderNumber(orderNumber);
        if (existingOrderDetails != null) {
            Product product = productService.getProductById(existingOrderDetails.getProductCode());
            product.setQuantityInStock(product.getQuantityInStock() + existingOrderDetails.getQuantityOrdered());
            productService.updateProduct(product.getProductCode(), product);
            orderDetailsRepository.deleteById(orderNumber);
        }
    }
}









































/*public class OrderDetailsServiceImpl {

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
}*/
