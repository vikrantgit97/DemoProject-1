package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "product_tbl")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCode;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    //@Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
    @NotBlank(message = "productDescription name cannot be blank")
    private String productDescription;

    @Min(value = 0, message = "Quantity in stock must be at least 0")
    private Integer quantityInStock;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "100.00", message = "Price must be at least 100.00")
    private Double price;

}