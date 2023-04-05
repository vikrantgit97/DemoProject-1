package com.example.model;

import com.example.entity.Customer;
import lombok.Data;

@Data
public class CustomerModel {

    private Integer customerNumber;
    private String customerFirstName;
    private String customerLastName;
    private Long phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer postalCode;
    private String country;

    public static CustomerModel fromEntity(Customer customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerNumber(customer.getCustomerNumber());
        customerModel.setCustomerFirstName(customer.getCustomerFirstName());
        customerModel.setCustomerLastName(customer.getCustomerLastName());
        customerModel.setPhone(customer.getPhone());
        customerModel.setAddressLine1(customer.getAddressLine1());
        customerModel.setAddressLine2(customer.getAddressLine2());
        customerModel.setCity(customer.getCity());
        customerModel.setState(customer.getState());
        customerModel.setPostalCode(customer.getPostalCode());
        customerModel.setCountry(customer.getCountry());
        return customerModel;
    }


    public  Customer toEntity() {
        Customer customer = new Customer();
        customer.setCustomerNumber(this.getCustomerNumber());
        customer.setCustomerFirstName(this.getCustomerFirstName());
        customer.setCustomerLastName(this.getCustomerLastName());
        customer.setPhone(this.getPhone());
        customer.setAddressLine1(this.getAddressLine1());
        customer.setAddressLine2(this.getAddressLine2());
        customer.setCity(this.getCity());
        customer.setState(this.getState());
        customer.setPostalCode(this.getPostalCode());
        customer.setCountry(this.getCountry());
        return customer;
    }
}
