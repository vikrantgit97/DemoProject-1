package com.example.controller;

import com.example.dto.OrderDto;
import com.example.entity.Order;
import com.example.service.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order1) {
        Order order = orderService.createOrder8(order1);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/o")
    public ResponseEntity<OrderDto> addOrder2(@RequestBody OrderDto orderDto) {
        Order order1 = modelMapper.map(orderDto, Order.class);
        Order order = orderService.createOrder8(order1);
        OrderDto order2 = modelMapper.map(order, OrderDto.class);
        return new ResponseEntity<>(order2, HttpStatus.CREATED);
    }

    @PostMapping("o1")
    public ResponseEntity<Order> addOrder3(@RequestBody Order order1) {
        Order order = orderService.createOrder6(order1);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/o2")
    public ResponseEntity<OrderDto> addOrder4(@RequestBody OrderDto orderDto) {
        Order order1 = modelMapper.map(orderDto, Order.class);
        Order order = orderService.createOrder8(order1);
        OrderDto order2 = modelMapper.map(order, OrderDto.class);
        return new ResponseEntity<>(order2, HttpStatus.CREATED);
    }

    @PostMapping("o3")
    public ResponseEntity<Order> addOrder5(@RequestBody Order order1) {
        Order order = orderService.createOrder(order1);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/o4")
    public ResponseEntity<OrderDto> addOrder6(@RequestBody OrderDto orderDto) {
        Order order1 = modelMapper.map(orderDto, Order.class);
        Order order = orderService.createOrder(order1);
        OrderDto order2 = modelMapper.map(order, OrderDto.class);
        return new ResponseEntity<>(order2, HttpStatus.CREATED);
    }

    @PostMapping("o5")
    public ResponseEntity<Order> addOrder7(@RequestBody Order order1) {
        Order order = orderService.createOrder1(order1);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/o6")
    public ResponseEntity<OrderDto> addOrder8(@RequestBody OrderDto orderDto) {
        Order order1 = modelMapper.map(orderDto, Order.class);
        Order order = orderService.createOrder1(order1);
        OrderDto order2 = modelMapper.map(order, OrderDto.class);
        return new ResponseEntity<>(order2, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> addOrder() {
        return new ResponseEntity<>(orderService.listOfOrders(), HttpStatus.OK);
    }
}



   /* @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Integer orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
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
    }*/













    /*Converter<Order, OrderDto> populateExistingNumbers = new Converter<Order, OrderDto>() {
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
    };*/



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



