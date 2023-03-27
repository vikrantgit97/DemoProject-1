package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private Integer productCode;
    private String productName;
    private String productDescription;
    private Integer quantityInStock;
    private Float price;

}
