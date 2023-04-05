package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "orderDetails_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@BatchSize( value = 10)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idGen")		//@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Integer orderId;

    private Integer orderNumber;

    private Integer productCode;
    //@Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    //@Min(value = 100, message = "Price each must be at least 100.00")
    private Double priceEach;

    @ManyToOne
    @JoinColumn(name = "fk_orderNumber")
    @JsonBackReference
    private Order order;

    //@JsonIgnore
    @ManyToOne(targetEntity = Product.class,cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.DETACH})
    @JoinColumn(name = "fk_productCode")      //default join column product_tbl_product_code
    private Product product;
}


