package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto implements Serializable {

    private Integer orderNumber;
    private Integer productCode;
    private Integer quantityOrdered;
    private Double priceEach;
}
