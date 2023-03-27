package com.example;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		/*Order order = new Order();
		order.setOrderDate(new Date());
		order.setShippedDate(new Date());
		order.setStatus(Status.SHIPPED);
		order.setComments("Test order");
		order.setCustomerNumber(1);

		OrderDetails orderDetails1 = new OrderDetails();
		orderDetails1.setProductCode(1);
		orderDetails1.setQuantityOrdered(2);
		orderDetails1.setPriceEach(10.0);
		orderDetails1.setOrder(order);

		OrderDetails orderDetails2 = new OrderDetails();
		orderDetails2.setProductCode(2);
		orderDetails2.setQuantityOrdered(1);
		orderDetails2.setPriceEach(20.0);
		orderDetails2.setOrder(order);

		order.getOrderDetails().add(orderDetails1);
		order.getOrderDetails().add(orderDetails2);
		orderRepository.save(order);

		Order order1 = new Order();
		order1.setOrderDate(new Date());
		order1.setStatus(Status.ORDERED);

		OrderDetails details1 = new OrderDetails();
		details1.setProductCode(1);
		details1.setQuantityOrdered(10);
		details1.setPriceEach(20.0);
		details1.setOrder(order);

		OrderDetails details2 = new OrderDetails();
		details2.setProductCode(2);
		details2.setQuantityOrdered(5);
		details2.setPriceEach(30.0);
		details2.setOrder(order);

		order.setOrderDetails(Arrays.asList(details1, details2));
		orderRepository.save(order);*/
	}
}
