package com.example.service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.OrderDetailsRepo;
import com.example.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ModelMapper modelMapper;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    //@Transactional
    public Order createOrder(Order order) {
        order.setComments(order.getComments());
        orderRepo.save(order);
        List<OrderDetails> orderDetailsList1 = new ArrayList<>();
        List<OrderDetails> orderDetailsDTOList = order.getOrderDetails();
        List<Integer> pid = orderDetailsDTOList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        log.info("product entity : "+products.toString());
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        for (int i = 0; i < orderDetailsDTOList.size(); i++) {
            OrderDetails orderDetailsDTO = orderDetailsDTOList.get(i);
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if ((product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered())) {
                productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDTO.getQuantityOrdered());
                OrderDetails orderDetails = modelMapper.map(orderDetailsDTOList, OrderDetails.class);
                orderDetails.setOrderNumber(order.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetails.setProduct(product);
                orderDetailsList1.add(orderDetails);      //orderDetailsRepo.save(orderDetails);
                log.info("orderDetails entity : " + orderDetails.toString());
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        OrderDetailsRepo.saveAll(orderDetailsList1);log.info("order entity : "+order.toString());
        return order;
    }


    @Transactional
    public Order createOrder1(Order order) {
        order.setComments(order.getComments());
        orderRepo.save(order);
        List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();
        List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.findProductByIdInList(pid);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetails orderDetailsDto : orderDetailsDtoList) {
            Product product = productMap.get(orderDetailsDto.getProductCode());
            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                throw new IllegalArgumentException("Product is out of stock");
            }
            productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDto.getQuantityOrdered());
            OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            orderDetails.setProduct(product);
            orderDetails.setOrderNumber(order.getOrderNumber());
            orderDetailsList.add(orderDetails);
        }
        OrderDetailsRepo.saveAll(orderDetailsList);
        orderRepo.save(order);
        return order;
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
            if (product.getQuantityInStock() > orderDetailsDTO.getQuantityOrdered()) {
                OrderDetails orderDetails = new OrderDetails();
                productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDTO.getQuantityOrdered());
                orderDetails.setOrder(orderDTO);
                orderDetails.setProduct(product);
                orderDetails.setOrderNumber(orderDTO.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());

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


    @Transactional
    public Order createOrder8(Order order) {
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
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetails orderDetailsDto : orderDetailsDtoList) {
            Product product = productMap.get(orderDetailsDto.getProductCode());
            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                throw new IllegalArgumentException("Product is out of stock");
            }
            OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDto.getQuantityOrdered());
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            orderDetails.setProductCode(product.getProductCode());
            orderDetails.setProduct(product);
            //orderDetails.setOrder(order);
            orderDetails.setOrderNumber(order.getOrderNumber());
            orderDetailsList.add(orderDetails);
        }
        OrderDetailsRepo.saveAll(orderDetailsList);
        orderRepo.save(order);
        return order;
    }
    public List<Order> listOfOrders() {
        return orderRepo.findAll();
    }



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


    /*@Transactional
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
    }*/

/* @Transactional
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
    }*/

/* @Transactional
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
    }*/