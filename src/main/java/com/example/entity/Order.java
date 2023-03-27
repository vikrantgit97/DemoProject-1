package com.example.entity;

import com.example.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private LocalDate orderDate=LocalDate.now();

    private LocalDate shippedDate;

    @NotNull(message = "Status cannot be null")
    private Status status=Status.ORDERED;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    private String comments;

    private Integer customerNumber;



    @OneToMany(mappedBy="orderNumber", cascade=CascadeType.ALL, orphanRemoval=true)
    //@JoinColumn(name = "order_id",referencedColumnName = "orderNumber")
    private List<OrderDetails> orderDetails;

    public  Order(){
        log.info("info from order entity ");
    }

    public List<OrderDetails> getOrderDetails() {
        if (orderDetails == null) {
            orderDetails = new ArrayList();
        }
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public void addOrderDetails(Integer orderNumber,Integer productCode,Integer quantityOrdered,Double priceEach) {
        OrderDetails newOrderDetails = new OrderDetails();
        newOrderDetails.setOrderNumber(orderNumber);
        newOrderDetails.setProductCode(productCode);
        newOrderDetails.setQuantityOrdered(quantityOrdered);
        newOrderDetails.setPriceEach(priceEach);
        newOrderDetails.setOrder(this);
        if (orderDetails == null) {
            orderDetails = new ArrayList<OrderDetails>();
        }
        orderDetails.add(newOrderDetails);
    }
}


