package com.factory.cake.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item  implements Serializable {
	@Id
	private String id;
	private String name;
	private float price;
	private String description;
	private String image;
}
