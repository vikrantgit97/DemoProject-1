package com.example.runner;

import org.springframework.boot.CommandLineRunner;

import com.example.dto.CustomerDto;
import com.github.javafaker.Faker;



public class MockData implements CommandLineRunner {
    Faker faker = new Faker();
    @Override
    public void run(String... args) throws Exception {
        for (int i=1;i<100;i++){
            CustomerDto customer = new CustomerDto();
            customer.setCustomerFirstName(faker.name().firstName());
            customer.setCustomerLastName(faker.name().lastName());
            customer.setPhone(faker.number().randomNumber());
            customer.setAddressLine1(faker.address().streetAddress());
            customer.setAddressLine2(faker.address().secondaryAddress());
            customer.setCity(faker.address().city());
            customer.setState(faker.address().state());
            customer.setPostalCode(faker.number().numberBetween(100000, 999999));
            customer.setCountry(faker.address().country());
        }
    }
}
