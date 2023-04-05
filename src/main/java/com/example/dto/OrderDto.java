package com.example.dto;

import com.example.entity.Customer;
import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Integer orderNumber;

    private LocalDate orderDate;

    private Date shippedDate;

    private Status status=Status.ORDERED;

    private String comments;

    @JsonIgnore
    private Customer customer;

    private Integer customerNumber;

    private List<OrderDetailsDto> orderDetails;

    public List<OrderDetailsDto> getOrderDetails() {
        if (orderDetails == null) {
            orderDetails = new ArrayList<OrderDetailsDto>();
        }
        return orderDetails;
    }
}
