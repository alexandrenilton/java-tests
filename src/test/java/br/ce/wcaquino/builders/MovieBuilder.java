package br.ce.wcaquino.builders;

import com.edu.abelem.entity.Movie;

public class MovieBuilder {
	private Movie movie;
	
	private MovieBuilder() {
		
	}
	
	public static MovieBuilder oneMovie() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setEstoque(2);
		builder.movie.setNome("Nome 1");
		builder.movie.setPrecoLocacao(4.0);
		
		return builder;
	}
	
	public static MovieBuilder oneMovieOutstock() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setEstoque(0);
		builder.movie.setNome("Nome 2");
		builder.movie.setPrecoLocacao(4.0);
		
		return builder;
	}

	
	public MovieBuilder outstock() {
		movie.setEstoque(0);
		return this;
	}
	
	public Movie now() {
		return movie;
	}
}