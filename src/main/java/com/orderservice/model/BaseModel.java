package com.orderservice.model;

import java.time.Instant;


public class BaseModel {
    private long id;
    private Instant createdAt = Instant.now();
}
