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


    @PostMapping("/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){

        if(order.getProducts().isEmpty() || order.getProducts().size()<=0){
            throw new ApplicationException("You can't make any order with empty items!");
        }

        if (order.getCustomer().getCustomerId().isEmpty() || order.getCustomer().getCustomerId() == null){
            throw new ApplicationException("Customer Id can't be Empty");
        }

        if (order.getCustomer().getCustomerFirstName().isEmpty() || order.getCustomer().getCustomerFirstName() == null){
            throw new ApplicationException("Customer First Name can't be Empty");
        }

        if (order.getCustomer().getCustomerLastName().isEmpty() || order.getCustomer().getCustomerLastName() == null){
            throw new ApplicationException("Customer Last Name can't be Empty");
        }

        if (order.getCustomer().getCustomerEmail().isEmpty() || order.getCustomer().getCustomerEmail() == null){
            throw new ApplicationException("Customer Email can't be Empty");
        }

        if (order.getCustomer().getMobileNumber() == "-1" || order.getCustomer().getMobileNumber() == null){
            throw new ApplicationException("Please provide a valid Mobile Number with 11 digits!");
        }

        if (order.getCustomer().getAddress().size() ==0 || order.getCustomer().getAddress() == null){
            throw  new ApplicationException("Please add shipping address!");
        }

        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/get/all-orders")
    public ResponseEntity<List<Order>> getOrders(){
        return new ResponseEntity<>(orderService.getOrders(),HttpStatus.OK);
    }

    @GetMapping("/get/orders/customerId/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable String customerId){
        return new ResponseEntity<>(orderService.getOrdersByCustomerId(customerId),HttpStatus.OK);
    }

    @GetMapping("/get/orders/customerMobileNumber/{mobileNumber}")
    public ResponseEntity<List<Order>> getOrdersByCustomerMobileNumber(@PathVariable String mobileNumber){
        return new ResponseEntity<>(orderService.getOrdersByCustomerMobileNumber(mobileNumber),HttpStatus.OK);
    }


}
