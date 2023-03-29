package com.example.service;

import com.example.dto.OrderDetailsDto;
import com.example.dto.OrderDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderDetailsRepo;
import com.example.repository.OrderRepo;
import com.example.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl  {

    private final OrderRepo orderRepo;
    private final OrderDetailsRepo OrderDetailsRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CustomerServiceImpl customerService;

    public List<Order> listOfOrders(){
        return orderRepo.findAll();
    }
    public OrderServiceImpl(OrderRepo orderRepo, OrderDetailsRepo OrderDetailsRepo, CustomerRepo customerRepo, ProductRepo productRepo, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.OrderDetailsRepo = OrderDetailsRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<OrderDto> createOrder(@Valid OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setComments(orderDto.getComments());
        Customer customer = customerRepo.findById(orderDto.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        //order.setCustomerNumber(customer.getCustomerNumber());
        Order savedOrder = orderRepo.save(order);

        List<OrderDetailsDto> OrderDetailsDtoList = orderDto.getOrderDetails();
        List<OrderDetails> OrderDetailsList = OrderDetailsDtoList.stream()
                .map(OrderDetailsDto -> {
                    Product product = productRepo.findById(OrderDetailsDto.getProductCode())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    if (product.getQuantityInStock() < OrderDetailsDto.getQuantityOrdered()) {
                        throw new IllegalArgumentException("Product is out of stock");
                    }
                    product.setQuantityInStock(product.getQuantityInStock() - OrderDetailsDto.getQuantityOrdered());
                    productRepo.save(product);
                    OrderDetails OrderDetails = modelMapper.map(OrderDetailsDto, OrderDetails.class);
                    OrderDetails.setOrderNumber(product.getProductCode());
                    OrderDetails.setProductCode(savedOrder.getOrderNumber());
                    OrderDetails.setOrder(savedOrder);
                    OrderDetails.setProduct(product);
                    OrderDetails.setQuantityOrdered(OrderDetailsDto.getQuantityOrdered());
                    OrderDetails.setPriceEach(product.getPrice());
                    return OrderDetailsRepo.save(OrderDetails);
                })
                .collect(Collectors.toList());

        OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
        savedOrderDto.setOrderDetails(OrderDetailsList.stream()
                .map(OrderDetails -> modelMapper.map(OrderDetails, OrderDetailsDto.class))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(savedOrderDto);
    }

    public Order createOrder2(Order order) {
        List<OrderDetails> OrderDetails = order.getOrderDetails();
        Order newOrder = orderRepo.save(order);
        List<OrderDetails> orderDetails1 = new ArrayList<>();
        try {
            for (OrderDetails orderDetails : OrderDetails) {
                Product product = productRepo.findById(orderDetails.getProductCode()).orElseThrow();
                int quantityOrdered = orderDetails.getQuantityOrdered();
                if (quantityOrdered <= product.getQuantityInStock()) {
                    product.setQuantityInStock(product.getQuantityInStock() - quantityOrdered);
                    productRepo.save(product);
                    OrderDetails newOrderDetails = new OrderDetails();
                    newOrderDetails.setOrderNumber(newOrder.getOrderNumber());
                    newOrderDetails.setProductCode(product.getProductCode());
                    newOrderDetails.setQuantityOrdered(quantityOrdered);
                    newOrderDetails.setPriceEach(product.getPrice());
                    orderDetails1.add(newOrderDetails);
                } else {
                    throw new ResourceNotFoundException("OUt of stock");
                }
            }
            OrderDetailsRepo.saveAll(orderDetails1);
            return newOrder;
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("no resource");
        } catch (Exception e) {
            throw new ResourceNotFoundException("no resource");
        }
    }


    public Order addOrder3(Order order) {
        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        // add the order to the customer's list of orders
        order.setCustomer(customer);
        order.setCustomerNumber(customer.getCustomerNumber());
        Order order1 = orderRepo.save(order);
        // Eager fetch the customer entity to avoid an additional database call
        customer.getCustomerNumber();
        // Join fetch the products for the OrderDetails entity
        List<OrderDetails> orderDetailsList = order.getOrderDetails().stream()
                .peek(orderDetails -> orderDetails.setProduct(productService.getProductById(orderDetails.getProductCode())))
                .filter(orderDetails -> orderDetails.getProduct().getQuantityInStock() >= orderDetails.getQuantityOrdered())
                .peek(orderDetails -> {
                    Product product = orderDetails.getProduct();
                    product.setQuantityInStock(product.getQuantityInStock() - orderDetails.getQuantityOrdered());
                    productService.updateProduct(orderDetails.getProductCode(), product);
                    orderDetails.setOrder(order1);
                    orderDetails.setOrderNumber(order1.getOrderNumber());
                    orderDetails.setProductCode(product.getProductCode());
                    orderDetails.setPriceEach(product.getPrice());
                })
                .collect(Collectors.toList());
        // Use batch processing to save the OrderDetails entities in a single batch
        OrderDetailsRepo.saveAll(orderDetailsList);
        return orderRepo.save(order1);
    }

    public Order createOrder4(Order orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setComments(orderDto.getComments());
        Customer customer = customerService.getCustomerById(orderDto.getCustomerNumber());
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        Order savedOrder = orderRepo.save(order);

        List<OrderDetails> orderDetailsDtoList = orderDto.getOrderDetails();
        List<OrderDetails> orderDetailsList = orderDetailsDtoList.stream().map(orderDetailsDto -> {
            Product product = productService.getProductById(orderDetailsDto.getProductCode());
            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                throw new IllegalArgumentException("Product is out of stock");
            }
            product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDto.getQuantityOrdered());
            OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            orderDetails.setOrderNumber(product.getProductCode());
            orderDetails.setProductCode(savedOrder.getOrderNumber());
            orderDetails.setOrder(savedOrder);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            productService.updateProduct(product.getProductCode(), product);
            return OrderDetailsRepo.save(orderDetails);
        }).collect(Collectors.toList());
        orderRepo.save(savedOrder).setOrderDetails(orderDetailsList.stream().collect(Collectors.toList()));
        return savedOrder;
    }


    @Transactional
    public Order addOrder5(Order order) {
        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        order.setCustomer(customer);
        order.setCustomerNumber(customer.getCustomerNumber());
        Order order1 = orderRepo.save(order);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        // List<OrderDetails> orderDetailsDTOList = order.getOrderDetails();
        // Product product = products.stream().filter(p ->
        // p.getProductCode().equals(order.getProductCode())).findFirst().orElseThrow(()
        // -> new IllegalArgumentException("Product not found"));
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            Product product = productService.getProductById(orderDetails.getProductCode());
            if (product.getQuantityInStock() >= orderDetails.getQuantityOrdered()) {
                Product product1 = productService.updateProductQuantityInStock(product.getProductCode(),
                        orderDetails.getQuantityOrdered());
                orderDetails.setOrder(order1);
                orderDetails.setProduct(product);
                orderDetails.setOrderNumber(order1.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetails.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetailsList.add(orderDetails);
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        OrderDetailsRepo.saveAll(orderDetailsList);
        return orderRepo.save(order1);
    }


    @Transactional
    public Order addOrder6(Order order) {
        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        order.setCustomer(customer);
        order.setCustomerNumber(customer.getCustomerNumber());
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            Product product = productService.getProductById(orderDetails.getProductCode());
            if (product.getQuantityInStock() >= orderDetails.getQuantityOrdered()) {
                Product product1 = productService.updateProductQuantityInStock(product.getProductCode(),
                        orderDetails.getQuantityOrdered());
                orderDetails.setOrder(order);
                orderDetails.setProduct(product);
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetails.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetailsList.add(orderDetails);
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        OrderDetailsRepo.saveAll(orderDetailsList);
        return orderRepo.save(order);
    }


   /* public Order createOrder3(Order orderReq) {
            List<String> productIds = new ArrayList<>();

            // Gathering product ids
            for (OrderDetails detail : orderReq.getOrderDetails()) {
                productIds.add(detail.getProductCode());
            }

            // Create order
            Order order = null;
            try {
                order = orderRepo.save(orderReq.toOrder());
            } catch (Exception e) {
                throw e;
            }

            if (order != null) {
                // Get product details from database
                List<Product> products = null;
                try {
                    products = productRepo.findProductByIdInListWithStock(productIds);
                } catch (Exception e) {
                    throw e;
                }

                // If all required products found
                if (products.size() == productIds.size()) {
                    List<OrderDetails> orderDetails = new ArrayList<>();

                    // Create order details (No Network hit)
                    for (OrderDetails detail : orderReq.getOrderDetails()) {
                        // TODO: check how details are created after removing from Model
                        List<Product> product = products.stream()
                                .filter(p -> p.getProductCode().equals(detail.getProductCode()))
                                .collect(Collectors.toList());
                        if (product.get(0).getQuantityInStock() >= detail.getQuantityOrdered()) {
                            OrderDetails orderDetail = new OrderDetails();
                            orderDetail.setOrderNumber(order.getOrderNumber());
                            orderDetail.setProductCode(detail.getProductCode());
                            orderDetail.setQuantityOrdered(detail.getQuantityOrdered());
                            orderDetail.setPriceEach(product.get(0).getPrice());
                            orderDetails.add(orderDetail);
                        } else {
                            // TODO: Throw exception insufficientStockException
                            throw new ResourceNotFoundException(" Insufficient Stock Exception");
                        }
                    }
                } else {
                    // TODO: create custom error ProductOutOfStock
                    throw new ResourceNotFoundException(" Product Out Of Stock Exception");
                }

                // Save bulk order details into database
                try {
                    OrderDetailsRepo.saveAll(orderDetails);
                } catch (Exception e) {
                    throw e;
                }

                // Update product quantity in Stock (in Database)
                try {
                    productRepo.bookGivenQtyOfAProductForAnOrder(orderDetails);
                } catch (ResourceNotFoundException e) {
                    throw e;
                }

                return order;
            }
            return null;
        }
    }*/



}
