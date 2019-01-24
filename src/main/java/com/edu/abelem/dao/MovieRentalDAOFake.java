package com.edu.abelem.dao;

import java.util.List;

import com.edu.abelem.entity.MovieRental;

public class MovieRentalDAOFake implements MovieRentalDAO {

	public void save(MovieRental locacao) {
		System.out.println("MovieRentalDAOFake.save() ok");
	}

	public List<MovieRental> getDelayedRentals() {
		// TODO Auto-generated method stub
		return null;
	}

}
