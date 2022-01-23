package com.orderservice.repository;


import com.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order,Integer> {
    List<Order> findAllByStatus(String str);
    Optional<Order> findById(String id);
    List<Order> findAllByUsername(String username);
}
