package com.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseModel {

    @Id
    private String id;
    private Instant createdAt = Instant.now();
}
