package com.example.dto;

import com.example.entity.Customer;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderDto {

    private LocalDateTime orderDate=LocalDateTime.now();
    private Date shippedDate;
    private String status;
    private String comments;
    private Integer customerNumber;
    private Customer customer;


}
