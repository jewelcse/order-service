package com.orderservice.listener;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.*;
import com.orderservice.model.*;
import com.orderservice.service.OrderServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UpdateOrderListener {

    private OrderServiceImpl orderService;

    @Autowired
    public UpdateOrderListener(OrderServiceImpl orderService){
        this.orderService = orderService;
    }

    @RabbitListener(queues = OrderConfig.ORDER_BILLED_QUEUE)
    public void updateOrder(OrderDto orderDto){
        Order order = orderService.getOrderById(orderDto.getOrderId()).get();
        order.setId(orderDto.getOrderId());
        order.setStatus(orderDto.getStatus());
        orderService.UpdateOrder(order);
    }

}
