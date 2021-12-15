package com.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress{

    private String fullName;
    private long phoneNumber;
    private String region;
    private String city;
    private String area;
    private String address;

}
