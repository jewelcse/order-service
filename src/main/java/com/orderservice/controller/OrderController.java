package com.orderservice.controller;


import com.orderservice.dto.OrderRequestDto;
import com.orderservice.exception.ApplicationException;
import com.orderservice.service.OrderService;
import com.orderservice.service.OrderServiceImpl;
import com.orderservice.util.JsonResponseEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/order-service/")
public class OrderController {

    private OrderService orderService;
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

    @GetMapping("/get/orders/customerId/{customerId}")
    public ResponseEntity<JsonResponseEntityModel> getOrdersByCustomerId(@PathVariable int customerId){
        if (customerId<=0) throw new ApplicationException(String.format("Invalid CustomerId %s",customerId));
        responseEntityModel.setSuccess(true);
        responseEntityModel.setData(orderService.getOrdersByCustomerId(customerId));
        responseEntityModel.setStatusCode("200");
        return new ResponseEntity<>(responseEntityModel,HttpStatus.OK);
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

}
