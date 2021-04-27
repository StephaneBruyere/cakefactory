package com.factory.cake.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketLineDTO {
	
    private String id;
    private String name;
//    private String descritpion;
    private int quantity;
	private BigDecimal lineTotalPrice;


}
