package com.example.service;

import com.example.dto.OrderDetailsDto;
import com.example.dto.OrderDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderDetailsRepo;
import com.example.repository.OrderRepo;
import com.example.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl  {

    private final OrderRepo iOrderRepo;
    private final OrderDetailsRepo iOrderDetailsRepo;
    private final CustomerRepo iCustomerRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepo iOrderRepo, OrderDetailsRepo iOrderDetailsRepo, CustomerRepo iCustomerRepo, ProductRepo productRepo, ModelMapper modelMapper) {
        this.iOrderRepo = iOrderRepo;
        this.iOrderDetailsRepo = iOrderDetailsRepo;
        this.iCustomerRepo = iCustomerRepo;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<OrderDto> createOrder(@Valid OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setComments(orderDto.getComments());
        Customer customer = iCustomerRepo.findById(orderDto.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        //order.setCustomerNumber(customer.getCustomerNumber());
        Order savedOrder = iOrderRepo.save(order);

        List<OrderDetailsDto> orderDetailsDtoList = orderDto.getOrderDetails();
        List<OrderDetails> orderDetailsList = orderDetailsDtoList.stream()
                .map(orderDetailsDto -> {
                    Product product = productRepo.findById(orderDetailsDto.getProductCode())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                        throw new IllegalArgumentException("Product is out of stock");
                    }
                    product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDto.getQuantityOrdered());
                    productRepo.save(product);
                    OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
                    orderDetails.setOrderNumber(product.getProductCode());
                    orderDetails.setProductCode(savedOrder.getOrderNumber());
                    orderDetails.setOrder(savedOrder);
                    orderDetails.setProduct(product);
                    orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
                    orderDetails.setPriceEach(product.getPrice());
                    return iOrderDetailsRepo.save(orderDetails);
                })
                .collect(Collectors.toList());

        OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
        savedOrderDto.setOrderDetails(orderDetailsList.stream()
                .map(orderDetails -> modelMapper.map(orderDetails, OrderDetailsDto.class))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(savedOrderDto);
    }
}
