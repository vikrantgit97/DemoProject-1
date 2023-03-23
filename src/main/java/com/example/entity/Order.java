package com.example.entity;

import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;
    @NotNull(message = "Order date cannot be null")

    private LocalDateTime orderDate= LocalDateTime.now();

    private LocalDateTime shippedDate;

    @NotNull(message = "Status cannot be null")
    private Status status=Status.ORDERED;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    private String comments;

    //@NotNull(message = "Customer number cannot be null")
    //private Integer customerNumber;

    @ManyToOne
    @JoinColumn(name = "customerNumber", nullable = false)
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();


}

