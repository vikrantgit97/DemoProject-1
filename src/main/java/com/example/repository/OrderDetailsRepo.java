package com.example.repository;

import com.example.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Integer> {

    @Query(value = "SELECT MAX(id) FROM OrderDetails")
    public Integer getMaxId();
}
