package com.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    @NotNull(message = "Product code cannot be null")
    private Integer productCode;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @DecimalMin(value = "100.00", message = "Price each must be at least 100.00")
    private Double priceEach;

}