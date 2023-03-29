package com.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateQuantityRequest {

    @NotNull(message = "Please select number.")
    private Integer quantityInStock;

    @NotBlank(message = "Please select number.")
    @Size(min = 3, max = 10, message = "Please enter 3 to 10 numbers.")
    private String productCode;

    // getter and setter methods

}