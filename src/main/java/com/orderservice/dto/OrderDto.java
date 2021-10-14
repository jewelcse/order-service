package com.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int customerId;
    private String accountNumber;
    private double amount;
    private String orderId;
    private String status;
}
