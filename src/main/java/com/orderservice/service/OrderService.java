package com.orderservice.service;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order saveOrder(OrderRequestDto order);
    Optional<Order> getOrderById(String id);
    List<Order> getOrders();
    List<Order> getOrdersByCustomerId(int id);
    List<Order> getCanceledOrders();
    List<Order> getCompletedOrders();
    List<Order> getProcessingOrders();
    List<Order> getOrdersByStatus(String status);
}
