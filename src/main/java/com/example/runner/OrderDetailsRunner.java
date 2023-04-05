package com.example.runner;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.repository.CustomerRepo;
import com.example.repository.OrderDetailsRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@org.springframework.core.annotation.Order(6)
//@Component
public class OrderDetailsRunner implements CommandLineRunner {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Override
    public void run(String... args) throws Exception {
        Faker faker=new Faker(new Locale("en-IND"));
        for (int i = 0; i < 100; i++) {

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setQuantityOrdered(faker.number().numberBetween(1, 10));
            orderDetails.setPriceEach(faker.number().randomDouble(2, 101, 10001));

        }
    }
}
