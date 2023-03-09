package com.example.service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl {

    @Autowired
    private CustomerRepo customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer customerNumber) {
        return customerRepository.findById(customerNumber).orElse(null);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = getCustomerById(customer.getCustomerNumber());
        if (existingCustomer == null) {
            return null;
        }
        existingCustomer.setCustomerFirstName(customer.getCustomerFirstName());
        existingCustomer.setCustomerLastName(customer.getCustomerLastName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setAddressLine1(customer.getAddressLine1());
        existingCustomer.setAddressLine2(customer.getAddressLine2());
        existingCustomer.setCity(customer.getCity());
        existingCustomer.setState(customer.getState());
        existingCustomer.setPostalCode(customer.getPostalCode());
        existingCustomer.setCountry(customer.getCountry());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Integer customerNumber) {
        customerRepository.deleteById(customerNumber);
    }
}
