package com.edu.abelem.services;

import static com.edu.abelem.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.abelem.dao.MovieRentalDAO;
import com.edu.abelem.entity.Movie;
import com.edu.abelem.entity.MovieRental;
import com.edu.abelem.entity.User;
import com.edu.abelem.exceptions.MovieOutOfStockException;
import com.edu.abelem.exceptions.MovieRentalException;
import com.edu.abelem.utils.DataUtils;

public class MovieRentalService {
	
	private MovieRentalDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	
	public MovieRental alugarFilme(User usuario, List<Movie> filmes) throws MovieOutOfStockException, MovieRentalException  {
		
		if (filmes == null || filmes.isEmpty() ) {
			throw new MovieRentalException("Filme vazio");
		}
		
		for (Movie filme: filmes) {
			if (filme.getEstoque() == 0 ) {
				throw new MovieOutOfStockException();
			}
		}
		
		if (usuario == null) {
			throw new MovieRentalException("Usuario vazio");
		}
		
		if (spcService.hasNegativeScore(usuario)) {
			throw new MovieRentalException("Usuário Negativado");
		}
		
		MovieRental locacao = new MovieRental();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(getValorTotalFilmes(filmes));
			
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void notifyDelay() {
		List<MovieRental> rents = dao.getDelayedRentals();
		for(MovieRental rent: rents) {
			emailService.notifyDelay(rent.getUsuario());
		}
	}
	
	public void setLocacaoDAO(MovieRentalDAO dao) {
		this.dao = dao;
	}
	
	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	public Double getValorTotalFilmes(List<Movie> filmes) {
		// desconto de 25% no terceiro filme
		Double tot = 0d;
		int count = 1;
		for(Movie filme: filmes) {
			if ( count == 3) {
				tot += filme.getPrecoLocacao() * 0.75;
			} else if ( count == 4 ){
				tot += filme.getPrecoLocacao() * 0.50;
			} else if ( count == 5) {
				tot += filme.getPrecoLocacao() * 0.25;
			} else if ( count == 6) {
				tot += filme.getPrecoLocacao() * 0;
			} else {
				tot += filme.getPrecoLocacao();
			}
			count++;
		}
		return tot;
	}
	
	
	

}