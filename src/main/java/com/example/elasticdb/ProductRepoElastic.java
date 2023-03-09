package com.example.elasticdb;

import com.example.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ProductRepoElastic extends ElasticsearchRepository<Product, Integer> {
    Optional<Product> findById(Integer productCode);

    Iterable<Product> findAll();

}
