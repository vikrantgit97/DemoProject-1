package com.example.service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderDetailsRepo;
import com.example.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl {
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderDetailsRepo orderDetailsRepository;

        @Transactional
        public Order placeOrder(Order order, List<OrderDetails> orderDetailsList) {
            // Save the order first to generate its ID
            Order savedOrder = orderRepository.save(order);

            // Set the order number for each order detail
            for (OrderDetails orderDetail : orderDetailsList) {
                orderDetail.setOrderNumber(savedOrder.getOrderNumber());
            }

            // Save the order details
            orderDetailsRepository.saveAll(orderDetailsList);

            // Update the product quantities
            for (OrderDetails orderDetail : orderDetailsList) {
                Integer productCode = orderDetail.getProductCode();
                Integer quantityOrdered = orderDetail.getQuantityOrdered();
                Product product = productRepository.findById(productCode)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product code"));

                Integer quantityInStock = product.getQuantityInStock();
                if (quantityInStock < quantityOrdered) {
                    throw new IllegalArgumentException("Not enough stock for product " + productCode);
                }

                product.setQuantityInStock(quantityInStock - quantityOrdered);
                productRepository.save(product);
            }

            return savedOrder;
        }



    public Order saveOrder(Order order, List<OrderDetails> orderDetailsList) {
        // Save the order first to generate its ID
        Order savedOrder = orderRepository.save(order);

        // Set the order number for each order detail
        for (OrderDetails orderDetail : orderDetailsList) {
            orderDetail.setOrderNumber(savedOrder.getOrderNumber());
        }

        // Save the order details
        orderDetailsRepository.saveAll(orderDetailsList);

        return savedOrder;
    }

    //private CustomerRepo customerRepo;
    public Order createOrdr(Integer customerNumber,Order order,Integer productCode){
        Customer customerById = customerService.getCustomerById(customerNumber);
        order.setComments(order.getComments());
        order.setShippedDate(order.getShippedDate());
        OrderDetails orderDetails=new OrderDetails();

        Product product=productService.getProductById(productCode);

        //order.setOrderDetails(orderDetails.getProduct().getProductCode());
        orderDetails.setProduct(orderDetails.getProduct());
        orderDetails.setQuantityOrdered(orderDetails.getQuantityOrdered());
        orderDetails.setPriceEach(orderDetails.getPriceEach());
        orderDetailsRepository.save(orderDetails);
       return orderRepository.save(order);
    }

    public Order createOrders(Order order, List<OrderDetails> orderDetailsList) {
        // Save the order
        order = orderRepository.save(order);

        // Generate codes for the order details
        Integer maxId = orderDetailsRepository.getMaxId();
        if (maxId == null) {
            maxId = 0;
        }
        for (OrderDetails orderDetails : orderDetailsList) {
            orderDetails.setId(++maxId);
            orderDetails.setOrder(order);
        }

        // Save the order details
        orderDetailsRepository.saveAll(orderDetailsList);

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderNumber) {
        return orderRepository.findById(orderNumber).orElse(null);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public void createOrder(Order order, List<OrderDetails> orderDetails) {
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
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
        //existingOrder.setCustomerNumber(order.getCustomerNumber());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Integer orderNumber) {
        orderRepository.deleteById(orderNumber);
    }


    @Transactional
    public void placeOrder(Order order) {
        //Customer customer = customerService.set(order.getCustomerNumber());
        order.setComments("required trimmer");
        addOrder(order);
    }
}

