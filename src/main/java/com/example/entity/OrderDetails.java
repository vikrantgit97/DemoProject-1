package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orderDetails_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer orderNumber;

    //@NotNull(message = "Product code cannot be null")
    private Integer productCode;

    //@Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @Min(value = 100, message = "Price each must be at least 100.00")
    private Double priceEach;

    @ManyToOne
    //@JoinColumn(name = "fk_orderNumber")
    @JsonBackReference
    private Order order;

    @JsonIgnore
    @ManyToOne(targetEntity = Product.class,cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    //@JoinColumn(name = "fk_productCode")      //default join column product_tbl_product_code
    private Product product;
}


