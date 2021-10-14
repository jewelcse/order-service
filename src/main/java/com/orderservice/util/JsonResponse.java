package com.orderservice.util;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class JsonResponse {
    private boolean success;
    private String StatusCode;
}
