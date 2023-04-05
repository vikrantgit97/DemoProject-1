package com.example.runner;

import com.example.entity.Customer;
import com.example.repository.CustomerRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.IntStream;

@Component
@Order(2)
public class CustomerRunner implements CommandLineRunner {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("en-IND"));
        if (customerRepo.count() == 0) {
            IntStream.range(0, 10).mapToObj(i -> {
                Customer customer = new Customer();
                customer.setCustomerFirstName(faker.name().firstName());
                customer.setCustomerLastName(faker.name().lastName());
                customer.setPhone(faker.number().numberBetween(1000000000L, 9999999999L));
                customer.setAddressLine1(faker.address().streetAddress());
                customer.setAddressLine2(faker.address().secondaryAddress());
                customer.setCity(faker.address().city());
                customer.setState(faker.address().state());
                customer.setPostalCode(faker.number().numberBetween(100000, 999999));
                customer.setCountry(faker.address().country());
                return customer;
            }).forEach(customerRepo::save);
        }
    }
}

