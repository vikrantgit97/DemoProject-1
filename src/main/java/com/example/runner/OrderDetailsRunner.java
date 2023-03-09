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

@Component
public class OrderDetailsRunner implements CommandLineRunner {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Override
    public void run(String... args) throws Exception {
        Faker faker=new Faker(new Locale("en-IND"));

        for (int i = 0; i < 100; i++) {

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
            customerRepo.save(customer);
            OrderDetails orderDetails = new OrderDetails();
            //orderDetails.setProductCode(faker.number().randomNumber());
            Product product = new Product();
            orderDetails.setProductCode(product.getProductCode());

            orderDetails.setQuantityOrdered(faker.number().numberBetween(1, 10));
            orderDetails.setPriceEach(faker.number().randomDouble(2, 10, 100));
            Order order = new Order();
            orderDetails.setOrderNumber(order.getOrderNumber());
            orderDetailsRepo.save(orderDetails);

        }
    }
}
