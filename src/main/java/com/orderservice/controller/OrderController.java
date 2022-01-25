package com.orderservice.controller;


import com.orderservice.dto.CustomPrincipal;
import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderUpdateDeliveryProfileDto;
import com.orderservice.exception.ApplicationException;
import com.orderservice.model.Order;
import com.orderservice.service.OrderService;
import com.orderservice.util.JsonResponseEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/v1/order-service/")
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class OrderController {

    private final OrderService orderService;
    JsonResponseEntityModel responseEntityModel = new JsonResponseEntityModel();


    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @PostMapping("/create-order")
    public ResponseEntity<JsonResponseEntityModel> createOrder(@RequestBody OrderRequestDto order){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.saveOrder(order));
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping("/get/all-orders")
    public ResponseEntity<JsonResponseEntityModel> getOrders(){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getOrders());
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/get/orders/customerId/{username}")
    public ResponseEntity<JsonResponseEntityModel> getOrdersByCustomerId(@PathVariable String username){
        //if (customerId<=0) throw new ApplicationException(String.format("Invalid CustomerId %s",customerId));
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getOrdersByCustomerId(username));
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/get/order-details/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable String orderId){
        return new ResponseEntity<>(orderService.getOrderDetailsByOrderId(orderId),HttpStatus.OK);
    }

    @PostMapping("/order/update/delivery/profile")
    public void updateOrderDeliveryProfile(@RequestBody OrderUpdateDeliveryProfileDto order){
        System.out.println(order.getOrderId());
        System.out.println(order.getDeliveryManUsername());
        orderService.updateOrderDeliveryProfile(order);
    }


    @GetMapping("/get/canceled-orders")
    public ResponseEntity<JsonResponseEntityModel> getCanceledOrders(){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getCanceledOrders());
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/get/completed-orders")
    public ResponseEntity<JsonResponseEntityModel> getCompletedOrders(){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getCompletedOrders());
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/get/processing-orders")
    public ResponseEntity<JsonResponseEntityModel> getProcessingOrders(){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getProcessingOrders());
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/get/orders/status")
    public ResponseEntity<JsonResponseEntityModel> getOrdersByStatus(@RequestParam String str){
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getOrdersByStatus(str));
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
    }

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('role_admin')")
    public String context() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    public String secured(CustomPrincipal principal) {
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/common")
    public String general() {
        return "common api success";
    }


}
