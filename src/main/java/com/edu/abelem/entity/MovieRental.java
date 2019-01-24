package com.edu.abelem.entity;

import java.util.Date;
import java.util.List;

public class MovieRental {

	private User user;
	private List<Movie> movies;
	private Date rentalDate;
	private Date rentalReturnDate;
	private Double price;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	public Date getRentalDate() {
		return rentalDate;
	}
	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}
	public Date getRentalReturnDate() {
		return rentalReturnDate;
	}
	public void setRentalReturnDate(Date rentalReturnDate) {
		this.rentalReturnDate = rentalReturnDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	


}