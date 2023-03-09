package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderDetails_tbl")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderNumber;

    private Integer productCode;

    private Integer quantityOrdered;

    private Float priceEach;

    public Integer getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
    public Integer getProductCode() {
        return productCode;
    }
    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }
    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }
    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
    public Float getPriceEach() {
        return priceEach;
    }
    public void setPriceEach(Float priceEach) {
        this.priceEach = priceEach;
    }

}