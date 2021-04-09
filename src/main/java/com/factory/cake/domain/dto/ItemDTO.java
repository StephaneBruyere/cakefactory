package com.factory.cake.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO  {

	private String id;
	private String name;
	private float price;
	private String description;
	private String image;
}
