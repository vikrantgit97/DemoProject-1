package com.example.entity;

import com.example.entity.Customer;
import com.example.entity.OrderDetails;
import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Data
@AllArgsConstructor
@Slf4j
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    // @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate=LocalDate.now();

    private LocalDate shippedDate;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private Status status=Status.ORDERED;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    private String comments;

    private Integer customerNumber;

    @JsonIgnore
    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.DETACH})
    @JoinColumn(name = "fk_customerNumber")
    private Customer customer;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<OrderDetails> orderDetails;

    public Order(){
        log.info("info from order entity ");
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
    /*public void addOrderDetails(Integer orderNumber,Integer productCode,Integer quantityOrdered,Double priceEach) {
        OrderDetails newOrderDetails = new OrderDetails();
        newOrderDetails.setOrderNumber(orderNumber);
        newOrderDetails.setProductCode(productCode);
        newOrderDetails.setQuantityOrdered(quantityOrdered);
        newOrderDetails.setPriceEach(priceEach);
        newOrderDetails.setOrderNumber(this);
        if (orderDetails == null) {
            orderDetails = new ArrayList<OrderDetails>();
        }
        orderDetails.add(newOrderDetails);
    }*/
}

