package com.orderservice.dto;

import com.orderservice.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String customerId;
    private String accountNumber;
    private double amount;
    private String orderId;
    private Status status;
}
