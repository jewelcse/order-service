package com.orderservice.service;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.OrderDto;
import com.orderservice.model.Order;
import com.orderservice.model.Product;
import com.orderservice.model.Status;
import com.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;
    private OrderDto orderDto;


    public OrderServiceImpl(OrderRepository orderRepository,
                            RabbitTemplate rabbitTemplate){
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Order saveOrder(Order order) {

        List<Product> products= order.getProducts();
        double totalCost = products.stream().mapToDouble(product -> product.getProductPrice() * product.getQuantity()).sum();
        Integer totalQuantity = products.stream().mapToInt( product -> product.getQuantity()).sum();

        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);

        order.setStatus(Status.PROCESSING);
        order.setTotalAmount(totalCost);
        order.setQuantity(totalQuantity);
        publishOrderForPayment(order);
        System.out.println("[Creating Order] "+ order.getId());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    private void publishOrderForPayment(Order order) {
        orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setCustomerId(order.getCustomer().getCustomerId());
        orderDto.setAccountNumber(order.getCustomer().getAccountNumber());
        orderDto.setAmount(order.getTotalAmount());
        rabbitTemplate.convertAndSend(OrderConfig.ORDER_CREATE_QUEUE,orderDto);
    }
}
