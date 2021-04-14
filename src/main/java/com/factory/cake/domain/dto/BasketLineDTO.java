package com.factory.cake.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketLineDTO {
	
    private String id;
    private String name;
    private int quantity;
	private float lineTotalPrice;


}
