package com.example.entity;

import com.example.customvalidation.PostalCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;
    private String customerFirstName;
    private String customerLastName;
    private Long phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    @PostalCode
    private Integer postalCode;
    private String country;

}
