package com.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @Id
    private String id;
	private List<Product> products;
	private Customer customer;
    private int quantity;
    private double totalAmount;
    private Status status;


}