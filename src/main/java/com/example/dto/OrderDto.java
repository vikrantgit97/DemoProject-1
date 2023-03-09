package com.example.dto;

import com.example.entity.Customer;
import com.example.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {

    private LocalDateTime orderDate=LocalDateTime.now();
    private Date shippedDate;
    //private Status status;
    private String comments;
    private Integer customerNumber;
    private Customer customer;


}
