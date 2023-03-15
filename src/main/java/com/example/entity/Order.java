package com.example.entity;

import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "order_tbl")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;
    @NotNull(message = "Order date cannot be null")

    private Date orderDate;

    private Date shippedDate;

    @NotNull(message = "Status cannot be null")
    private Status status;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    private String comments;

    //@NotNull(message = "Customer number cannot be null")
    private Integer customerNumber;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;*/

}

