//package com.factory.cake.domain.model;
//
//import java.io.Serializable;
//
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@SuppressWarnings("serial")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Order implements Serializable {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator="order_seq_gen")
//	private Long id;
//	
//	@Embedded
//	@NotNull(message = "invalid Address")
//	private Address address = new Address();
//}
