package com.orderservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document("orders")
public class Order extends BaseModel{

	private String username;
	private List<Product> products;
    private ShippingAddress shippingAddress;
    private int quantity;
    private double shippingCharge;
    private double subTotal;
    private double totalAmount;
    private OrderPriority priority;
    private String status;
    private DeliveryMan deliveryMan;

}