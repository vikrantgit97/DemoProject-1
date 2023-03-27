package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Product;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    public List<Product> findByProductName(String productName);

    public List<Product> findByProductNameStartingWith(String msg);

    public List<Product> findByProductNameContaining(String prefix);

    public List<Product> findAllByOrderByProductNameAsc();     //findAllSortedByName();

    public List<Product> findAllByOrderByProductNameDesc();

    public List<Product> findByProductNameContainsAllIgnoreCase(String name);
}
