package com.orderservice.util;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class JsonResponseEntityModel extends JsonResponse{
	private Object data;
}
