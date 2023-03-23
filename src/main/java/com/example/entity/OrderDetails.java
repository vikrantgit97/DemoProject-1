package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orderDetails_tbl")
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    private Integer quantityOrdered;

    private Double priceEach;

    @ManyToOne
    @JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productCode")
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
