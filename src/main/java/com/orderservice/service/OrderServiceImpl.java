package com.orderservice.service;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.OrderDto;
import com.orderservice.dto.OrderRequestDto;
import com.orderservice.exception.ApplicationException;
import com.orderservice.model.Order;
import com.orderservice.model.Product;
import com.orderservice.model.Status;
import com.orderservice.repository.OrderRepository;
import com.orderservice.util.MethodUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;
    private OrderDto orderDto;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            RabbitTemplate rabbitTemplate){
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Order saveOrder(OrderRequestDto orderRequestDto) {

        List<Product> products= orderRequestDto.getProducts();

        products.forEach(product -> {
            product.setProductFinalPrice(product.getProductOriginalPrice() - ((product.getDiscountPercentage()*product.getProductOriginalPrice())/100));
        });

        double subTotalCost = products.stream().mapToDouble(product ->
                product.getProductFinalPrice() * product.getQuantity()
        ).sum();
        Integer totalQuantity = products.stream().mapToInt( product -> product.getQuantity()).sum();



        double totalCost = subTotalCost + orderRequestDto.getShippingCharge();

        Order order = new Order();
        order.setId(MethodUtils.generateOrderId());
        order.setCustomer(orderRequestDto.getCustomer());
        order.setProducts(orderRequestDto.getProducts());
        order.setStatus(Status.PROCESSING.toString());

        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setPriority(orderRequestDto.getPriority());
        order.setShippingCharge(orderRequestDto.getShippingCharge());
        order.setSubTotal(subTotalCost);
        order.setTotalAmount(totalCost);
        order.setQuantity(totalQuantity);
        publishOrderForPayment(order);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order>  getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(int id) {
        return orderRepository.findAllByCustomer_CustomerId(id);
    }



    @Override
    public List<Order> getCanceledOrders() {
        return orderRepository.findAllByStatus(Status.CANCELED.toString());
    }

    @Override
    public List<Order> getCompletedOrders() {
        return orderRepository.findAllByStatus(Status.COMPLETED.toString());
    }

    @Override
    public List<Order> getProcessingOrders() {
        return orderRepository.findAllByStatus(Status.PROCESSING.toString());
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        checkValidStatus(status);
        return orderRepository.findAllByStatus(status);
    }

    private Status checkValidStatus(String str){

        return Arrays.stream(Status.values())
                .filter(e -> e.toString().equals(str))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(String.format("Unsupported type %s", str)));

    }

    private void publishOrderForPayment(Order order) {
        orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setStatus(Status.PROCESSING.toString());
        orderDto.setCustomerId(order.getCustomer().getCustomerId());
        orderDto.setAmount(order.getTotalAmount());
        rabbitTemplate.convertAndSend(OrderConfig.ORDER_CREATE_QUEUE,orderDto);
    }

    public void UpdateOrder(Order order){
        orderRepository.save(order);
    }
}
