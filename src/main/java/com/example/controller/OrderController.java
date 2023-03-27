package com.example.controller;

import com.example.dto.OrderDetailsDto;
import com.example.dto.OrderDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderRepo;
import com.example.repository.ProductRepo;
import com.example.service.OrderDetailsServiceImpl;
import com.example.service.OrderServiceImpl;
import org.modelmapper.Converter;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private ProductRepo productRepository;

    private ModelMapper modelMapper;

    @Autowired
    public OrderController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addConverter(populateExistingNumbers);
        this.modelMapper.addConverter(handlePhoneNumbersEntered);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDto orderDto) {
        try {
            Order order = modelMapper.map(orderDto, Order.class);           //converting Dto to entity
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(savedOrder);
        } catch (MappingException | DataAccessException ex) {
            throw new RuntimeException( ex.getMessage());
        }
    }


    Converter<Order, OrderDto> populateExistingNumbers = new Converter<Order, OrderDto>() {
        @Override
        public OrderDto convert(MappingContext<Order, OrderDto> context) {
            //This custom converter replaces the one automatically created by ModelMapper,So we have to map each of the Order fields as well.
            context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
            context.getDestination().setComments(context.getSource().getComments());
            context.getDestination().setOrderNumber(context.getSource().getOrderNumber());
            context.getDestination().setOrderDate(context.getDestination().getOrderDate());
            context.getDestination().setStatus(context.getDestination().getStatus());
            context.getDestination().setShippedDate(context.getDestination().getShippedDate());

            List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
            for (OrderDetails orderDetails : context.getSource().getOrderDetails()) {
                orderDetailsDtoList.add(modelMapper.map(orderDetails, OrderDetailsDto.class));
            }
            context.getDestination().setOrderDetails(orderDetailsDtoList);
            return context.getDestination();
        }
    };

    Converter<OrderDto, Order> handlePhoneNumbersEntered = new Converter<OrderDto, Order>() {
        @Override
        public Order convert(MappingContext<OrderDto, Order> context) {
            //This custom converter replaces the one automatically created by ModelMapper, So we have to map each of the Order fields as well.
            context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
            context.getDestination().setComments(context.getSource().getComments());
            context.getDestination().setOrderNumber(context.getSource().getOrderNumber());
            context.getDestination().setOrderDate(context.getDestination().getOrderDate());
            context.getDestination().setStatus(context.getDestination().getStatus());
            context.getDestination().setShippedDate(context.getDestination().getShippedDate());

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            for (OrderDetailsDto orderDetailsDto : context.getSource().getOrderDetails()) {
                OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
                orderDetails.setOrder(context.getDestination());
                orderDetailsList.add(orderDetails);
            }
            context.getDestination().setOrderDetails(orderDetailsList);
            return context.getDestination();
        }
    };



       /*Converter<Order, OrderDto> populateExistingNumbers = new Converter<Order, OrderDto>() {
        @Override
        public OrderDto convert(MappingContext<Order, OrderDto> context) {
            //This custom converter replaces the one automatically created by ModelMapper,So we have to map each of the Order fields as well.
            context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
            context.getDestination().setComments(context.getSource().getComments());
            context.getDestination().setOrderNumber(context.getSource().getOrderNumber());
            context.getDestination().setOrderDate(context.getSource().getOrderDate());
            context.getDestination().setStatus(context.getSource().getStatus());
            context.getDestination().setShippedDate(context.getSource().getShippedDate());
            List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
            for (OrderDetails orderDetails : context.getSource().getOrderDetails()) {
                orderDetailsDtoList.add(modelMapper.map(orderDetails, OrderDetailsDto.class));
            }
            context.getDestination().setOrderDetails(orderDetailsDtoList);
            return context.getDestination();
        }
    };

    Converter<OrderDto, Order> handlePhoneNumbersEntered = new Converter<OrderDto, Order>() {
        @Override
        public Order convert(MappingContext<OrderDto, Order> context) {
            //This custom converter replaces the one automatically created by ModelMapper, So we have to map each of the Order fields as well.
            context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
            context.getDestination().setComments(context.getSource().getComments());
            context.getDestination().setOrderNumber(context.getSource().getOrderNumber());
            context.getDestination().setOrderDate(context.getSource().getOrderDate());
            context.getDestination().setStatus(context.getSource().getStatus());
            context.getDestination().setShippedDate(context.getSource().getShippedDate());
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            for (OrderDetailsDto orderDetailsDto : context.getSource().getOrderDetails()) {
                OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
                orderDetails.setOrder(context.getDestination());
                orderDetailsList.add(orderDetails);
            }
            context.getDestination().setOrderDetails(orderDetailsList);
            return context.getDestination();
        }
    };*/

    /*@PostMapping("/ord")
    @Transactional
    public Order createOrder(@Valid @RequestBody OrderDto orderDTO) {
        Order order = new Order();
        order.setComments(orderDTO.getComments());
        Customer customer = customerRepo.findById(orderDTO.getCustomerNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        order.setCustomerNumber(customer);
        List<OrderDetailsDto> orderDetailsDTOList = orderDTO.getOrderDetails();
        for (OrderDetailsDto orderDetailsDTO : orderDetailsDTOList) {
            Product product = productRepo.findById(orderDetailsDTO.getProductCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            if (product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered()) {
                product.setQuantityInStock(product.getQuantityInStock() - orderDetailsDTO.getQuantityOrdered());
                productRepo.save(product);
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderNumber(order);
                orderDetails.setProductCode(product);
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetailsRepo.save(orderDetails);
            } else {
                throw new ResourceNotFoundException("Product is out of stock");
            }
        }
        return orderRepository.save(order);
    }*/

    @PostMapping
    public ResponseEntity<Order> addOrderDto1(@RequestBody Order order) {
       // Order order = modelMapper.map(orderdto, Order.class);           // convert DTO to entity
        Order order1 = orderRepository.save(order);
        return new ResponseEntity<>(order1, HttpStatus.OK);
    }

    /*@PostMapping("/{customerId}/{productCode}")
    public ResponseEntity<Order>createOrdr(@PathVariable Integer customerId,@RequestBody Order order,@PathVariable Integer productCode){
        return ResponseEntity.ok().body(orderService.createOrdr(customerId,order,productCode));
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Integer orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/save")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
            Customer customer = customerRepository.findById(order.getCustomerNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer "+order.getCustomerNumber()));
            //order.setCustomer(customer);
            order.setOrderDetails(order.getOrderDetails());
            Order savedOrder = orderRepository.save(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        }

    @PutMapping("{id}")
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



    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteOrders(@PathVariable(value = "id") Integer orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping
    public Order addOrder(@Valid @RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Order> updateOrders1(@PathVariable(value = "id") Integer orderNumber,
                                              @Valid @RequestBody Order order) {
        Order existingOrder = orderService.getOrderById(orderNumber);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        Order updatedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("place-order")
    public ResponseEntity<Void> placeOrder(@Valid @RequestBody Order order) {
        Order order1 = orderService.placeOrder(order);
        return ResponseEntity.ok().build();
    }*/
}
