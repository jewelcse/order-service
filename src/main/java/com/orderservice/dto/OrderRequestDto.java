package com.orderservice.dto;

import com.orderservice.model.Customer;
import com.orderservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Customer customer;
    private List<Product> products = new ArrayList<>();

}