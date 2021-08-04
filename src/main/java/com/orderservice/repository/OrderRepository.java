package com.orderservice.repository;


import com.orderservice.dto.OrderDto;
import com.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findAllByCustomerCustomerId(String id);
    List<Order> findAllByCustomerMobileNumber(String mobileNumber);
}
