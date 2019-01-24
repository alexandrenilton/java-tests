package com.edu.abelem.services;

import static com.edu.abelem.utils.DataUtils.addDays;

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
	
	public MovieRental rentMovie(User usuario, List<Movie> movies) throws MovieOutOfStockException, MovieRentalException  {
		
		if (movies == null || movies.isEmpty() ) {
			throw new MovieRentalException("Movie is empty");
		}
		
		for (Movie movie: movies) {
			if (movie.getStock() == 0 ) {
				throw new MovieOutOfStockException();
			}
		}
		
		if (usuario == null) {
			throw new MovieRentalException("User is empty");
		}
		
		if (spcService.hasNegativeScore(usuario)) {
			throw new MovieRentalException("User denied");
		}
		
		MovieRental movieRental = new MovieRental();
		movieRental.setMovies(movies);
		movieRental.setUser(usuario);
		movieRental.setRentalDate(new Date());
		movieRental.setPrice(getTotalPrice(movies));
			
		//Entrega no dia seguinte
		Date dataReturn = new Date();
		dataReturn = addDays(dataReturn, 1);
		
		if(DataUtils.checkDayOfWeek(dataReturn, Calendar.SUNDAY)) {
			dataReturn = addDays(dataReturn, 1);
		}
		
		movieRental.setRentalReturnDate(dataReturn);
		
		//Salvando a locacao...	
		dao.save(movieRental);
		
		return movieRental;
	}
	
	public void notifyDelay() {
		List<MovieRental> rents = dao.getDelayedRentals();
		for(MovieRental rent: rents) {
			emailService.notifyDelay(rent.getUser());
		}
	}
	
	public void setMovieRentalDAO(MovieRentalDAO dao) {
		this.dao = dao;
	}
	
	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	public Double getTotalPrice(List<Movie> movies) {
		// desconto de 25% no terceiro movie
		Double tot = 0d;
		int count = 1;
		for(Movie movie: movies) {
			if ( count == 3) {
				tot += movie.getRentalPrice() * 0.75;
			} else if ( count == 4 ){
				tot += movie.getRentalPrice() * 0.50;
			} else if ( count == 5) {
				tot += movie.getRentalPrice() * 0.25;
			} else if ( count == 6) {
				tot += movie.getRentalPrice() * 0;
			} else {
				tot += movie.getRentalPrice();
			}
			count++;
		}
		return tot;
	}
	
	
	

}