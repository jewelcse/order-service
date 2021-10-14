package com.orderservice.model;

import java.util.stream.Stream;

public enum Status {
     PROCESSING("PROCESSING"),
     COMPLETED("COMPLETED"),
     PAID("PAID"),
     CANCELED("CANCELED");

     private String type;

     Status(String type){
          this.type = type;
     }

     Status() {

     }

}
