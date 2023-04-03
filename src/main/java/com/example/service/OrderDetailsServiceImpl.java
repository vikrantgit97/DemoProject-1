package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.OrderDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class OrderDetailsServiceImpl {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ProductServiceImpl productService;

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepo.findAll();
    }

    @Transactional
    @Bean
    public void createOrderDetails(List<OrderDetails> orderDetailsList) throws Exception {

        Job job = jobBuilderFactory.get("createOrderDetailsJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1(orderDetailsList))
                .end()
                .build();

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        log.info("Job Execution Status : " + jobExecution.getStatus());
    }

    @Bean
    private Step step1(List<OrderDetails> orderDetailsList) {
        return stepBuilderFactory.get("createOrderDetailsStep")
                .<OrderDetails, OrderDetails>chunk(100)
                .reader(new ListItemReader<>(orderDetailsList))
                .writer(new OrderDetailsWriter(orderDetailsRepo))
                .build();
    }

    private static class OrderDetailsWriter implements ItemWriter<OrderDetails> {

        private final OrderDetailsRepo orderDetailsRepo;

        public OrderDetailsWriter(OrderDetailsRepo orderDetailsRepo) {
            this.orderDetailsRepo = orderDetailsRepo;
        }


        @Bean
        public void write(List<? extends OrderDetails> orderDetailsList) throws Exception {
            orderDetailsRepo.saveAll(orderDetailsList);
        }
    }


    @Bean
    public Job importOrderDetailsJob() {
        return jobBuilderFactory.get("importOrderDetailsJob")
                .incrementer(new RunIdIncrementer())
                .start(importOrderDetailsStep())
                .build();
    }

    @Bean
    public Step importOrderDetailsStep() {
        return stepBuilderFactory.get("importOrderDetailsStep")
                .<OrderDetails, OrderDetails>chunk(10)
                .reader(orderDetailsReader())
                .processor(orderDetailsProcessor())
                .writer(orderDetailsWriter())
                .build();
    }

    @Bean
    public ItemReader<OrderDetails> orderDetailsReader() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetails orderDetailsDTO : orderDTO.getOrderDetails()) {
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if (product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDTO.getQuantityOrdered());
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(orderDTO);
                orderDetails.setProduct(product);
                orderDetails.setOrderNumber(orderDTO.getOrderNumber());
                orderDetails.setProductCode (product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetailsList.add(orderDetails);
                log.info("orderDetails entity : " + orderDetails.toString());
            } else {
                throw new IllegalArgumentException("Product is out of stock");
            }
        }
        return new ListItemReader<>(orderDetailsList);
    }

    @Bean
    public ItemProcessor<OrderDetails, OrderDetails> orderDetailsProcessor() {
        return orderDetails -> orderDetails;
    }

    @Bean
    public ItemWriter<OrderDetails> orderDetailsWriter() {
        return orderDetails -> orderDetailsRepo.saveAllAndFlush(orderDetails);
    }

}
//    public OrderDetails getOrderDetailsByOrderNumber(Integer orderNumber) {
//        return orderDetailsRepository.findById(orderNumber).orElse(null);
//    }
//
//    public Order createOrder(Order orderReq) throws Exception {
//        List<String> productIds = new ArrayList<>();
//
//        // Gathering product ids
//        for (OrderDetails detail : orderReq.getOrderDetails()) {
//            productIds.add(detail.getProductCode());
//        }
//
//        // Create order
//        Order order = null;
//        try {
//            order = orderRepository.save(orderReq);
//        } catch (Exception e) {
//            throw e;
//        }
//
//        if (order != null) {
//            // Get product details from database
//            List<Product> products = null;
//            try {
//                products = productRepository.findProductByIdInListWithStock(productIds);
//            } catch (Exception e) {
//                throw e;
//            }
//
//            // If all required products found
//            if (products.size() == productIds.size()) {
//                List<OrderDetail> orderDetails = new ArrayList<>();
//
//                // Create order details (No Network hit)
//                for (OrderDetail detail : orderReq.getOrderDetail()) {
//                    Product product = products.stream()
//                            .filter(p -> p.getProductCode().equals(detail.getProductCode()))
//                            .findFirst()
//                            .orElse(null);
//                    if (product != null && product.getQuantityInStock() >= detail.getQuantityOrdered()) {
//                        OrderDetail orderDetail = new OrderDetail();
//                        orderDetail.setOrderNumber(order.getOrderNumber());
//                        orderDetail.setProductCode(detail.getProductCode());
//                        orderDetail.setQuantityOrdered(detail.getQuantityOrdered());
//                        orderDetail.setPriceEach(product.getPrice());
//                        orderDetails.add(orderDetail);
//                    } else {
//                        // TODO: Throw exception insufficientStockException
//                        throw new InsufficientStockException();
//                    }
//                }
//
//                // Save bulk order details into database
//                try {
//                    orderDetailRepository.createOrderDetail(orderDetails);
//                } catch (Exception e) {
//                    throw e;
//                }
//
//                // Update product quantity in Stock (in Database)
//                try {
//                    productRepository.bookGivenQtyOfAProductForAnOrder(orderDetails);
//                } catch (InsufficientStockException e) {
//                    throw e;
//                }
//
//                return order;
//            } else {
//                // TODO: create custom error ProductOutOfStock
//                throw new ProductOutOfStockException();
//            }
//        }
//        return order;
//    }
//
//    public void createOrderDetail(List<OrderDetail> orderDetails) {
//        List<OrderDetailEntity> entities = new ArrayList<>();
//        for (OrderDetail detail : orderDetails) {
//            entities.add(new OrderDetailEntity(detail.getOrderNumber(), detail.getProductCode(),
//                    detail.getQuantityOrdered(), detail.getPriceEach()));
//        }
//        orderDetailRepository.saveAll(entities);
//    }
    

/*    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        Product product = productService.getProductById();
        if (product != null && product.getQuantityInStock() >= orderDetails.getQuantityOrdered()) {
            product.setQuantityInStock(product.getQuantityInStock() - orderDetails.getQuantityOrdered());
            productService.updateProduct(product.getProductCode(), product);
            return orderDetailsRepository.save(orderDetails);
        }
        return null;
    }
}*/
   /* public OrderDetails updateOrderDetails(Integer orderNumber, OrderDetails orderDetails) {
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
}*/









































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
