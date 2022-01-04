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
public class Order extends BaseModel{

	private int customerId;
	private List<Product> products;
    private ShippingAddress shippingAddress;
    private int quantity;
    private double shippingCharge;
    private double subTotal;
    private double totalAmount;
    private OrderPriority priority;
    private String status;

}