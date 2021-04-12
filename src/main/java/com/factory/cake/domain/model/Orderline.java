//package com.factory.cake.domain.model;
//
//import java.io.Serializable;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Positive;
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
//public class Orderline implements Serializable {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator="order_line_seq_gen")
//	private Long id;
//	
//	@Positive(message = "invalid Quantity")
//    private int quantity;
//    
//    @ManyToOne
//    @JoinColumn(name ="ITEM_FK", nullable = false)
//    @NotNull(message = "invalid Item")
//    private Item item;
//    
//    @ManyToOne
//    @JoinColumn(name ="ORDER_FK", nullable = false)
//    @NotNull(message = "invalid Order")
//    private Order order;
//
//}
