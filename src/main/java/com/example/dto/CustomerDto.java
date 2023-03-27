package com.example.dto;

import com.example.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    private Integer customerNumber;
    private String customerFirstName;
    private String customerLastName;
    private Long phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer postalCode;
    private String country;

}









