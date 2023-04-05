package com.example.service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl {

    @Autowired
    private  CustomerRepo iCustomerRepo;

    public List<Customer> getCustomerList() {
        return iCustomerRepo.findAll();
    }

    public Customer updateCustomerDetail(Integer customerNumber, Customer c) {

        Optional<Customer> customer1 = iCustomerRepo.findById(customerNumber);

        if (customer1.isPresent()) {
            Customer customer = customer1.get();
            customer.setCustomerFirstName(c.getCustomerFirstName());
            customer.setCustomerLastName(c.getCustomerLastName());
            customer.setAddressLine1(c.getAddressLine1());
            customer.setAddressLine2(c.getAddressLine2());
            customer.setCity(c.getCity());
            customer.setCountry(c.getCountry());
            customer.setPostalCode(c.getPostalCode());
            customer.setState(c.getState());
            customer.setPhone(c.getPhone());
            return iCustomerRepo.save(customer);
        } else {

            System.out.println("Customer Not Found");
        }

        return null;
    }


    public Customer getCustomerById(Integer customerNumber) {

        return iCustomerRepo.findById(customerNumber).orElse(null);

    }


    public Customer registerCustomer(Customer customerNumber) {

        return iCustomerRepo.save(customerNumber);
    }


    public String deleteCustomer(Integer customerNumber) {

        iCustomerRepo.deleteById(customerNumber);

        return "Customer Deleted Successfully";

    }


    public Customer findBycustomerFirstName(String customerFirstName) {

        return iCustomerRepo.findBycustomerFirstName(customerFirstName);
    }


    public Customer findBycustomerLastName(String customerLasstName) {

        return iCustomerRepo.findBycustomerLastName(customerLasstName);
    }

}