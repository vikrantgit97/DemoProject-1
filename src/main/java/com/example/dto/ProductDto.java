package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductDto {

    private String productName;

    @NotBlank
    @Size(min=10, max = 500, message = "Min size of product description is 10")
    private String productDescription;

    private Integer quantityInStock;

    private Float price;

}
