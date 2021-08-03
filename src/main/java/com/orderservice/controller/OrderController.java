package com.orderservice.controller;


import com.orderservice.exception.ApplicationException;
import com.orderservice.model.Order;
import com.orderservice.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/order-service/")
public class OrderController {

    private OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderServiceImpl){
        this.orderService = orderServiceImpl;
    }


    @PostMapping("/order-create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){

        if (order.getProducts().size()<=0){
            throw new ApplicationException("Please Order something!");
        }

        if (order.getCustomer().getCustomerId().isEmpty() || order.getCustomer().getCustomerId() == null){
            throw new ApplicationException("Customer Id can't be null");
        }

        System.out.println(order.getCustomer().getMobileNumber());
        if (order.getCustomer().getMobileNumber().equals("-1")){
            throw new ApplicationException("Please provide a valid Mobile Number with 11 digits!");
        }

        if (order.getCustomer().getAddress().size() ==0){
            throw  new ApplicationException("Please add shipping address!");
        }

        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/get/orders")
    public ResponseEntity<List<Order>> getOrders(){
        return new ResponseEntity<>(orderService.getOrders(),HttpStatus.OK);
    }


}
