package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import com.example.customvalidation.PostalCode;
import lombok.Data;

@Entity
@Table(name = "customer_tbl")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;

    @NotBlank(message = "First name cannot be blank")
    private String customerFirstName;

    @NotBlank(message = "Last name cannot be blank")
    private String customerLastName;

   // @Pattern(regexp="(^$|[0-9]{10})")
    private Long phone;

    @NotBlank(message = "Address line 1 cannot be blank")
    private String addressLine1;

    @Size(max = 50, message = "Address line 2 cannot be more than 50 characters")
    private String addressLine2;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @PostalCode
    private Integer postalCode;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL)
    private List<Order> order;

}
