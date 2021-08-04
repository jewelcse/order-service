package com.orderservice.listener;

import com.orderservice.config.OrderConfig;
import com.orderservice.dto.*;
import com.orderservice.exception.ApplicationException;
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
        System.out.println("[Updating Order] "+ orderDto.getOrderId());
        Order order = orderService.getOrderById(orderDto.getOrderId()).get();
        if (order.getId().isEmpty()){
            throw new ApplicationException("Order Not Found for "+ orderDto.getOrderId());
        }
        order.setId(orderDto.getOrderId());
        order.setStatus(orderDto.getStatus());
        orderService.UpdateOrder(order);
    }

}
