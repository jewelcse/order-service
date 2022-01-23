package com.orderservice.model;

import java.util.stream.Stream;

public enum Status {
     PROCESSING("PROCESSING"),
     COMPLETED("COMPLETED"),
     CASHONDELIVERY("COD"),
     PAID("PAID"),
     CANCELED("CANCELED");

     private final String type;

     Status(String type){
          this.type = type;
     }


}
