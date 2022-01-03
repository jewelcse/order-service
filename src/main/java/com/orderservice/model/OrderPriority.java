package com.orderservice.model;

public enum OrderPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);



    private final int priority;

    OrderPriority(int p){
        this.priority = p;
    }
}
