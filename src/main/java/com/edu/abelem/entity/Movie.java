package com.edu.abelem.entity;

public class Movie {

	private String name;
	private Integer stock;
	private Double rentalPrice;  
	
	public Movie() {}
	
	public Movie(String name, Integer stock, Double rentalPrice) {
		this.name = name;
		this.stock = stock;
		this.rentalPrice = rentalPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(Double rentalPrice) {
		this.rentalPrice = rentalPrice;
	}
	
	
	
}