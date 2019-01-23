package com.edu.abelem.dao;

import java.util.List;

import com.edu.abelem.entity.MovieRental;

public interface MovieRentalDAO {
	public void salvar(MovieRental locacao);

	public List<MovieRental> getDelayedRentals();
}
