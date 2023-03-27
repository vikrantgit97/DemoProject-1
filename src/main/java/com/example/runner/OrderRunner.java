package com.example.runner;

import com.example.entity.Order;
import com.example.enums.Status;
import com.example.repository.OrderRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
@org.springframework.core.annotation.Order(5)
@Component
public class OrderRunner implements CommandLineRunner {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void run(String... args) throws Exception {

      //  if (orderRepo.count() == 0) {
            for (int i = 1; i < 100; i++) {
                Faker faker = new Faker(new Locale("en-IND"));
                Order order = new Order();
                //order.setOrderDate(faker.date().future(1, TimeUnit.DAYS));
                order.setComments(faker.lorem().sentence());
                //order.setStatus(Status.ORDERED);
                //Customer customer = new Customer();
                //order.setCustomerNumber(faker.number().randomDigit());
                //order.setShippedDate(faker.date().future(3, TimeUnit.DAYS));
                orderRepo.save(order);
            }
       // }
    }
}
