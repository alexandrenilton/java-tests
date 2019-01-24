package com.edu.abelem.builders;

import com.edu.abelem.entity.Movie;

public class MovieBuilder {
	private Movie movie;
	
	private MovieBuilder() {
		
	}
	
	public static MovieBuilder oneMovie() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setStock(2);
		builder.movie.setName("Nome 1");
		builder.movie.setRentalPrice(4.0);
		
		return builder;
	}
	
	public static MovieBuilder oneMovieOutstock() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setStock(0);
		builder.movie.setName("Nome 2");
		builder.movie.setRentalPrice(4.0);
		
		return builder;
	}

	
	public MovieBuilder outstock() {
		movie.setStock(0);
		return this;
	}
	
	public Movie now() {
		return movie;
	}
}