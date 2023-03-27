package com.example.config;

import com.example.dto.OrderDetailsDto;
import com.example.dto.OrderDto;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    private ModelMapper modelMapper;

    public OrderMapper() {
        modelMapper = new ModelMapper();
        Converter<Order, OrderDto> populateExistingNumbers = new Converter<Order, OrderDto>() {
            @Override
            public OrderDto convert(MappingContext<Order, OrderDto> context) {
                //This custom converter replaces the one automatically created by ModelMapper,So we have to map each of the Order fields as well.
                //context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
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
               // context.getDestination().setCustomerNumber(context.getSource().getCustomerNumber());
                context.getDestination().setComments(context.getSource().getComments());
                context.getDestination().setOrderNumber(context.getSource().getOrderNumber());
                context.getDestination().setOrderDate(context.getSource().getOrderDate());
                context.getDestination().setStatus(context.getSource().getStatus());
                context.getDestination().setShippedDate(context.getSource().getShippedDate());
                List<OrderDetails> orderDetailsList = new ArrayList<>();
                for (OrderDetailsDto orderDetailsDto : context.getSource().getOrderDetails()) {
                    OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
                    orderDetails.setOrderNumber(context.getDestination());
                    orderDetailsList.add(orderDetails);
                }
                context.getDestination().setOrderDetails(orderDetailsList);
                return context.getDestination();
            }
        };
        modelMapper.addConverter(populateExistingNumbers);
        modelMapper.addConverter(handlePhoneNumbersEntered);
    }

    public OrderDto toOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public Order toOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}
