package com.orderservice.listener;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.*;
import com.orderservice.model.*;
import com.orderservice.repository.*;
import com.orderservice.exception.OrderNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderListener {


    private OrderRepository orderRepository;
    public UpdateOrderListener(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    @RabbitListener(queues = OrderConfig.ORDER_BILLED_QUEUE)
    public void updateOrder(OrderDto orderDto){

        System.out.println("[Updating Order] "+ orderDto.getOrderId());

        Order order = orderRepository.findById(orderDto.getOrderId())
                .orElseThrow( () -> new OrderNotFoundException("Order Not Found for "+ orderDto.getOrderId()));

        order.setStatus(orderDto.getStatus());
        orderRepository.save(order);

    }

}
