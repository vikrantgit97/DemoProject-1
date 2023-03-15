package com.example.elasticdb;

import com.example.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@Repository
//@EnableJpaRepositories
public interface ProductRepoElastic {
        /*extends ElasticsearchRepository<Product, Integer> {
    Optional<Product> findById(Integer productCode);

    Iterable<Product> findAll();*/

}
