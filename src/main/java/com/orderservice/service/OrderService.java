package com.orderservice.service;

import com.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Order saveOrder(Order order);
    public Optional<Order> getOrderById(String id);
    List<Order> getOrders();

    List<Order> getOrdersByCustomerId(String id);

    List<Order> getOrdersByCustomerMobileNumber(String mobileNumber);
}
