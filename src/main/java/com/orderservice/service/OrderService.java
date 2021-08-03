package com.orderservice.service;

import com.orderservice.model.Order;

import java.util.List;

public interface OrderService {

    public Order saveOrder(Order order);

    List<Order> getOrders();
}
