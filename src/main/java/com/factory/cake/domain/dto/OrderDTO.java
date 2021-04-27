package com.factory.cake.domain.dto;

import java.math.BigDecimal;
import java.util.Collection;

import com.factory.cake.authentication.domain.dto.AddressDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
	
	private final AddressDTO address;
    private final Collection<BasketLineDTO> basket;
    
    public String getTotal() {
        return this.basket.stream().map(BasketLineDTO::getLineTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2).toPlainString();
    }

}
