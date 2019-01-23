package br.ce.wcaquino.builders;

import static br.ce.wcaquino.builders.UsuarioBuilder.oneUser;
import static br.ce.wcaquino.builders.MovieBuilder.oneMovie;


import java.util.Arrays;
import java.util.Date;

import com.edu.abelem.entity.Movie;
import com.edu.abelem.entity.MovieRental;
import com.edu.abelem.entity.User;
import com.edu.abelem.utils.DataUtils;


public class LocacaoBuilder {
	private MovieRental elemento;
	private LocacaoBuilder(){}

	public static LocacaoBuilder oneRental() {
		LocacaoBuilder builder = new LocacaoBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(LocacaoBuilder builder) {
		builder.elemento = new MovieRental();
		MovieRental elemento = builder.elemento;

		
		elemento.setUsuario(oneUser().now());
		elemento.setFilmes(Arrays.asList(oneMovie().now()));
		elemento.setDataLocacao(new Date());
		elemento.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
		elemento.setValor(4.0);
	}

	public LocacaoBuilder withUser(User param) {
		elemento.setUsuario(param);
		return this;
	}

	public LocacaoBuilder withMovieList(Movie... params) {
		elemento.setFilmes(Arrays.asList(params));
		return this;
	}

	public LocacaoBuilder withDateRental(Date param) {
		elemento.setDataLocacao(param);
		return this;
	}

	public LocacaoBuilder withDateReturn(Date param) {
		elemento.setDataRetorno(param);
		return this;
	}

	public LocacaoBuilder withValue(Double param) {
		elemento.setValor(param);
		return this;
	}

	public MovieRental now() {
		return elemento;
	}
}
