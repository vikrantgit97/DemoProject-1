package com.example.repository;

import com.example.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    Customer findBycustomerFirstName(String name);

     Customer findBycustomerLastName(String name);
}
