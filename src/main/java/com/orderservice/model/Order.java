package com.orderservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document("orders")
public class Order {


    @Id
    private String id;
	private Customer customer;
	private List<Product> products;
    private int quantity;
    private double totalAmount;
    private String status;


}