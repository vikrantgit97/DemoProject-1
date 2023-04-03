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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDetailsRepo OrderDetailsRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    public List<Order> listOfOrders() {
        return orderRepo.findAll();
    }


    @Transactional
    public Order createOrder7(Order order) {
        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        orderRepo.save(order);
        List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();
        // ... your existing code ...
        List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (int i = 0; i < orderDetailsDtoList.size(); i++) {
            OrderDetails orderDetailsDTO = orderDetailsDtoList.get(i);
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if (product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDTO.getQuantityOrdered());
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(order);
                orderDetails.setProduct(product);
                orderDetails.setOrderNumber(order.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetailsList.add(orderDetails);
                log.info("orderDetails entity : " + orderDetails.toString());
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        try {
            orderDetailsService.createOrderDetails(orderDetailsList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("createOrderDetails");
        }
        // ... your existing code ...
    return orderRepo.save(order);
    }


    @Transactional
    public Order createOrder5(Order orderDTO) {
        orderDTO.setComments(orderDTO.getComments());
        Customer customer = customerService.getCustomerById(orderDTO.getCustomerNumber());
        orderDTO.setCustomer(customer);
        orderDTO.setCustomerNumber(customer.getCustomerNumber());
        orderRepo.save(orderDTO);
        List<OrderDetails> orderDetailsDTOList = orderDTO.getOrderDetails();
        List<Integer> pid = orderDetailsDTOList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        log.info("product entity : " + products.toString());
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        String insertQuery = "INSERT INTO order_details (order_number, product_code, quantity_ordered, price_each) VALUES (:orderNumber, :productCode, :quantityOrdered, :priceEach)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(insertQuery);
        for (int i = 0; i < orderDetailsDTOList.size(); i++) {
            OrderDetails orderDetailsDTO = orderDetailsDTOList.get(i);
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if (product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDTO.getQuantityOrdered());
                query.setParameter("orderNumber", orderDTO.getOrderNumber());
                query.setParameter("productCode", product.getProductCode());
                query.setParameter("quantityOrdered", orderDetailsDTO.getQuantityOrdered());
                query.setParameter("priceEach", product.getPrice());
                query.addQueryHint("o");
                log.info("orderDetails entity : " + orderDetailsDTOList.toString());
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        query.executeUpdate();
        log.info("order entity : " + orderDTO.toString());
        return orderDTO;
    }

    public Order createOrd(Order order) {
        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        orderRepo.save(order);
        List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();
        List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        List<OrderDetails> orderDetailsList = orderDetailsDtoList.stream().map(orderDetailsDto -> {
            Product product = productMap.get(orderDetailsDto.getProductCode());
            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                throw new IllegalArgumentException("Product is out of stock");
            }
            product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDto.getQuantityOrdered());
            // OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            //OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            orderDetails.setProductCode(product.getProductCode());
            //orderDetails.setOrder(order);
            return orderDetails;
        }).collect(Collectors.toList());
        OrderDetailsRepo.saveAll(orderDetailsList);
        orderRepo.save(order);
        return order;
    }


    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setComments(orderDto.getComments());
        Customer customer = customerRepo.findById(orderDto.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        //order.setCustomerNumber(customer.getCustomerNumber());
        orderRepo.save(order);

        List<OrderDetailsDto> OrderDetailsDtoList = orderDto.getOrderDetails();
        List<OrderDetails> orderDetailsList1 = new ArrayList<>();
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
                    OrderDetails.setProductCode(product.getProductCode());
                    OrderDetails.setOrderNumber(order.getOrderNumber());
                    //OrderDetails.setOrder(order);
                    OrderDetails.setProduct(product);
                    OrderDetails.setQuantityOrdered(OrderDetailsDto.getQuantityOrdered());
                    OrderDetails.setPriceEach(product.getPrice());
                    orderDetailsList1.add(OrderDetails);
                    return OrderDetails;
                })
                .collect(Collectors.toList());
        OrderDetailsRepo.saveAll(orderDetailsList1);
        OrderDto savedOrderDto = modelMapper.map(order, OrderDto.class);
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


    @Transactional
    public Order createOrder6(Order orderDTO) {
        orderDTO.setComments(orderDTO.getComments());
        Customer customer = customerService.getCustomerById(orderDTO.getCustomerNumber());
        orderDTO.setCustomer(customer);
        orderDTO.setCustomerNumber(customer.getCustomerNumber());
        orderRepo.save(orderDTO);
        List<OrderDetails> orderDetailsList1 = new ArrayList<>();
        List<OrderDetails> orderDetailsDTOList = orderDTO.getOrderDetails();
        List<Integer> pid = orderDetailsDTOList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        log.info("product entity : " + products.toString());
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        for (int i = 0; i < orderDetailsDTOList.size(); i++) {
            OrderDetails orderDetailsDTO = orderDetailsDTOList.get(i);
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if (product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDTO.getQuantityOrdered());
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(orderDTO);
                orderDetails.setProduct(product);
                orderDetails.setOrderNumber(orderDTO.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                //productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDTO.getQuantityOrdered());
                orderDetailsList1.add(orderDetails);      //orderDetailsRepo.save(orderDetails);
                log.info("orderDetails entity : " + orderDetails.toString());
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        OrderDetailsRepo.saveAllAndFlush(orderDetailsList1);
        log.info("order entity : " + orderDTO.toString());
        return orderDTO;
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


    @Transactional
    public List<OrderDetails> saveOrderDetailsInBatch(List<OrderDetails> orderDetailsList) {
        int batchSize = 50; // set the batch size as per your requirement
        List<OrderDetails> savedOrderDetailsList = new ArrayList<>();
        for (int i = 0; i < orderDetailsList.size(); i++) {
            OrderDetails orderDetails = orderDetailsList.get(i);
            entityManager.persist(orderDetails);
            savedOrderDetailsList.add(orderDetails);
            if (i % batchSize == 0 || i == orderDetailsList.size() - 1) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return savedOrderDetailsList;
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
