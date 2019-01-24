package com.edu.abelem.dao;

import java.util.List;

import com.edu.abelem.entity.MovieRental;

public interface MovieRentalDAO {
	public void save(MovieRental locacao);

	public List<MovieRental> getDelayedRentals();
}
