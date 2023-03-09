package com.example.dto;

import com.example.customvalidation.PostalCode;
import com.example.entity.Order;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;

public class CustomerDto {

    //private Integer customerNumber;
    @NotNull(message = "customerFirstName cannot null or empty")
    private String customerFirstName;
    @NotNull(message = "customerLastName cannot null or empty")
    private String customerLastName;
    @Pattern(regexp="(^$|[0-9]{10})")
    private Long phone;
    @NotNull(message = "address cannot null or empty")
    private String addressLine1;
    @Column(nullable = true,length = 50)
    private String addressLine2;
    @NotNull(message = "city cannot null or empty")
    private String city;
    @NotNull(message = "state cannot null or empty")
    private String state;
    @PostalCode
    private Integer postalCode;
    @NotNull(message = "country cannot null or empty")
    private String country;
    private List<Order> order;

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
