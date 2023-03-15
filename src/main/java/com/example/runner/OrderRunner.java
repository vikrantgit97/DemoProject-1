package com.example.runner;

import com.example.entity.Order;
import com.example.enums.Status;
import com.example.repository.OrderRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
@org.springframework.core.annotation.Order(5)
@Component
public class OrderRunner implements CommandLineRunner {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void run(String... args) throws Exception {
        Faker faker=new Faker(new Locale("en-IND"));

        for (int i=1;i<100;i++){
            Order order = new Order();
            //order.setOrderNumber(faker.number().randomNumber());
            order.setOrderDate(faker.date().past(365, TimeUnit.DAYS));
            order.setShippedDate(faker.date().future(30, TimeUnit.DAYS));
            order.setStatus(Status.ORDERED);
            order.setComments(faker.lorem().sentence());
           // order.setCustomerNumber(faker.number().randomNumber());
            orderRepo.save(order);
        }
    }
}
