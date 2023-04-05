package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class OrderDetailsDto {
    private Integer orderId;

    private Integer orderNumber;

    private Integer productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    public OrderDetailsDto(Integer orderNumber, Integer productCode, Integer quantityOrdered, Double priceEach) {
        super();
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
    }

    public OrderDetailsDto() {
        // TODO Auto-generated constructor stub
    }
}
