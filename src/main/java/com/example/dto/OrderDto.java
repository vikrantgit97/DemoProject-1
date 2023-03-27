package com.example.dto;

import com.example.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {

    private Integer orderNumber;
    private LocalDateTime orderDate=LocalDateTime.now();
    private LocalDateTime shippedDate;
    private Status status=Status.ORDERED;
    private String comments;
    private Integer customerNumber;
    private List<OrderDetailsDto> orderDetails;



}
