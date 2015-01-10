package com.edd.food.pojo;

import java.io.Serializable;

public class Food implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Long cost;
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Food() {
		
	}
	public Food(String name, String description, Long cost) {
		this.name = name;
		this.description = description;
		this.cost = cost;
	}
}

