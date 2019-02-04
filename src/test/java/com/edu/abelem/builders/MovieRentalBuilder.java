package com.edu.abelem.builders;

import static com.edu.abelem.builders.MovieBuilder.oneMovie;
import static com.edu.abelem.builders.UserBuilder.oneUser;

import java.util.Arrays;
import java.util.Date;

import com.edu.abelem.entity.Movie;
import com.edu.abelem.entity.MovieRental;
import com.edu.abelem.entity.User;
import com.edu.abelem.utils.DataUtils;


public class MovieRentalBuilder {
	private MovieRental element;
	private MovieRentalBuilder(){}

	public static MovieRentalBuilder oneRental() {
		MovieRentalBuilder builder = new MovieRentalBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(MovieRentalBuilder builder) {
		builder.element = new MovieRental();
		MovieRental elemento = builder.element;

		
		elemento.setUser(oneUser().now());
		elemento.setMovies(Arrays.asList(oneMovie().now()));
		elemento.setRentalDate(new Date());
		elemento.setRentalReturnDate(DataUtils.obterDataComDiferencaDias(1));
		elemento.setPrice(4.0);
	}

	public MovieRentalBuilder withUser(User param) {
		element.setUser(param);
		return this;
	}

	public MovieRentalBuilder withMovieList(Movie... params) {
		element.setMovies(Arrays.asList(params));
		return this;
	}

	public MovieRentalBuilder withDateRental(Date param) {
		element.setRentalDate(param);
		return this;
	}

	public MovieRentalBuilder withDateReturn(Date param) {
		element.setRentalReturnDate(param);
		return this;
	}
	
	public MovieRentalBuilder delayed() {
		element.setRentalDate(DataUtils.obterDataComDiferencaDias(-4));
		element.setRentalReturnDate(DataUtils.obterDataComDiferencaDias(-2));
		return this;
	}
	

	public MovieRentalBuilder withValue(Double param) {
		element.setPrice(param);
		return this;
	}

	public MovieRental now() {
		return element;
	}
}
