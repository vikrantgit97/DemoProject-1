package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private Integer productCode;
    private Integer quantityOrdered;

    @Min(value = 100, message = "Price each must be at least 100.00")
    private Double priceEach;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_orderNumber")
    @JsonBackReference
    private Order order;

    @ManyToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_productCode")
    private Product product;

}

/*@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @Id
    private Integer orderNumber;

    private Integer productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    @ManyToOne
    @JoinColumn(name = "orderNumber", insertable = false, updatable = false)
    private Order order;

}*/





/*@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    private Integer productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    @ManyToOne
    @JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber")
    private Order order;
}*/






/*@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    private Integer quantityOrdered;

    private Double priceEach;
}*/












    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNumber")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productCode")
    private Product product;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @DecimalMin(value = "100.00", message = "Price each must be at least 100.00")
    private Double priceEach;

}*/






















/*
@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    private Integer productCode;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @DecimalMin(value = "100.00", message = "Price each must be at least 100.00")
    private Double priceEach;

}*/
