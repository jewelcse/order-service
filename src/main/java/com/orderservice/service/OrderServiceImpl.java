package com.orderservice.service;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.OrderDto;
import com.orderservice.exception.ApplicationException;
import com.orderservice.model.Order;
import com.orderservice.model.Product;
import com.orderservice.model.Status;
import com.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;
    private OrderDto orderDto;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            RabbitTemplate rabbitTemplate){
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Order saveOrder(Order order) {

        List<Product> products= order.getProducts();
        products.stream().forEach(product -> {

            if (product.getProductId().isEmpty() || product.getProductId() == null){
                throw new ApplicationException("Product's Id can't be Empty");
            }

            if (product.getProductTitle().isEmpty() || product.getProductTitle() == null){
                throw new ApplicationException("Product's Title can't be Empty");
            }

            if (product.getQuantity() <=0){
                throw new ApplicationException("Product's Quantity can't be Empty");
            }
            if (product.getProductPrice() <=0){
                throw new ApplicationException("Product's Price can't be Empty");
            }
        });

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
    public Optional<Order>  getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(String id) {
        return orderRepository.findAllByCustomerCustomerId(id);
    }

    @Override
    public List<Order> getOrdersByCustomerMobileNumber(String mobileNumber) {
        return orderRepository.findAllByCustomerMobileNumber(mobileNumber);
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

    public void UpdateOrder(Order order){
        orderRepository.save(order);
    }
}
