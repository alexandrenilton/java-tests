package com.edu.abelem.services;

import static com.edu.abelem.builders.MovieRentalBuilder.oneRental;
import static com.edu.abelem.builders.MovieBuilder.oneMovie;
import static com.edu.abelem.builders.UserBuilder.oneUser;
import static com.edu.abelem.matchers.OwnMatchers.isDaysInterval;
import static com.edu.abelem.matchers.OwnMatchers.isOnMonday;
import static com.edu.abelem.matchers.OwnMatchers.isToday;
import static com.edu.abelem.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.edu.abelem.builders.MovieBuilder;
import com.edu.abelem.dao.MovieRentalDAO;
import com.edu.abelem.entity.Movie;
import com.edu.abelem.entity.MovieRental;
import com.edu.abelem.entity.User;
import com.edu.abelem.exceptions.MovieOutOfStockException;
import com.edu.abelem.exceptions.MovieRentalException;
import com.edu.abelem.services.EmailService;
import com.edu.abelem.services.MovieRentalService;
import com.edu.abelem.services.SPCService;
import com.edu.abelem.utils.DataUtils;

public class MovieRentalServiceTest {

	private MovieRentalService service;
	private MovieRentalDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setupClass() {
//		System.out.println("Antes da classe se criada");
	}

	@AfterClass
	public static void setupTearDownClass() {
//		System.out.println("Antes da classe ser destruída");
	}

	@Before
	public void setup() {
		service = new MovieRentalService();
		
		// dao = new MovieRentalDAOFake();
		dao = Mockito.mock(MovieRentalDAO.class); 
		service.setMovieRentalDAO(dao);
		
		spcService= Mockito.mock(SPCService.class);
		service.setSPCService(spcService);
		
		emailService = Mockito.mock(EmailService.class);
		service.setEmailService(emailService);
	}

	@After
	public void tearDown() {
//		System.out.println("After");
	}

	@Test
	public void shouldRentMovie() throws Exception {
		
		Assume.assumeFalse(DataUtils.checkDayOfWeek(new Date(), Calendar.SATURDAY));
		
		User usuario = oneUser().now();
		// List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0), new Filme("Filme 2", 2, 5.0));
		 List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now());

		// acao
		MovieRental locacao = service.rentMovie(usuario, filmes);

		error.checkThat(locacao.getPrice(), is(equalTo(8.0)));
		error.checkThat(DataUtils.isSameDate(locacao.getRentalDate(), new Date()), is(true));
		error.checkThat(locacao.getRentalDate(), isToday());
		error.checkThat(DataUtils.isSameDate(locacao.getRentalReturnDate(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getRentalReturnDate(), isDaysInterval(1));
	}
	
	@Test
	public void shouldSendEmailToDelayedRentals() {
		// scene
		User user = oneUser().now();
		User user2 = oneUser().withName("Alexandre").now();
		User user3 = oneUser().withName("Another user").now();
		
		List<MovieRental> rentals = Arrays.asList(
				oneRental().delayed().withUser(user).now(),
				oneRental().withUser(user2).now(),
				oneRental().delayed().withUser(user3).now() );
		
		Mockito.when(dao.getDelayedRentals()).thenReturn(rentals);
		
		// action
		service.notifyDelay();
		
		
		// verificacao se o metodo notifyDelay Passando user foi chamado
		Mockito.verify(emailService, Mockito.times(2)).notifyDelay(Mockito.any(User.class));
		Mockito.verify(emailService).notifyDelay(user);
		Mockito.verify(emailService).notifyDelay(user3);
		Mockito.verify(emailService, Mockito.never()).notifyDelay(user2);
		
		// deve ser enviado pelo menos 2 emails
		Mockito.verify(emailService, Mockito.atLeastOnce()).notifyDelay(user3);
		
		// verifica se houve mais interações alem das que descrevi no código 
		Mockito.verifyNoMoreInteractions(emailService);
		
		// Garantir que o servico SPC nunca será executado nesses cenários de testes
		Mockito.verifyZeroInteractions(spcService);
	}
	
	@Test(expected = MovieOutOfStockException.class)
	public void shouldNotRentOutOfstockMovie() throws Exception {
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(MovieBuilder.oneMovieOutstock().now(), oneMovie().now());

		// acao
		service.rentMovie(usuario, filmes);
		System.out.println("Forma elegante");
	}

	@Test
	public void shouldNotRentForAEmptyUser() throws MovieOutOfStockException {
		User usuario = null;
		List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now());

		try {
			service.rentMovie(usuario, filmes);
			Assert.fail();
		} catch (MovieRentalException locException) {
			Assert.assertThat(locException.getMessage(), is("User is empty"));
		}

//		System.out.println("Forma robusta - Maior controle");
	}

	@Test
	public void shouldNotRentAEmptyMovie() throws MovieOutOfStockException, MovieRentalException {
		User usuario = oneUser().now();
		List<Movie> filmes = null;

		exception.expect(MovieRentalException.class);
		exception.expectMessage("Movie is empty");

		// acao
		service.rentMovie(usuario, filmes);

//		System.out.println("Forma nova");
	}

	@Test
	public void shouldGive25discountOn3Movie() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(),oneMovie().now(),oneMovie().now() );
		// action
		MovieRental result = service.rentMovie(usuario, filmes);
		// evaluation 4+4+3
		assertThat(result.getPrice(), is(11.0));
	}

	@Test
	public void shouldGive50discountOn4Movie() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(),oneMovie().now(),oneMovie().now(),oneMovie().now());
		// action
		MovieRental result = service.rentMovie(usuario, filmes);
		// evaluation 4+4+3+2
		assertThat(result.getPrice(), is(13.0));
	}

	@Test
	public void shouldGive75discountOn5Movie() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now());
		// action
		MovieRental result = service.rentMovie(usuario, filmes);
		// check 4+4+3+2+1
		assertThat(result.getPrice(), is(14.0));
	}

	@Test
	public void shouldGive100discountOn6Movie() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now());
		// action
		MovieRental result = service.rentMovie(usuario, filmes);
		// check 4+4+3+2+1
		assertThat(result.getPrice(), is(14.0));
	}

	@Test
	public void shouldReturnAMovieOnMondayWhenRentInSaturday() throws MovieOutOfStockException, MovieRentalException {
		// vai executar, somente se for sabado..
		Assume.assumeTrue(DataUtils.checkDayOfWeek(new Date(), Calendar.SATURDAY));
		// scene
		User usuario = oneUser().now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now(), oneMovie().now());
		// action
		MovieRental result = service.rentMovie(usuario, filmes);
		// evaluation
		// boolean isMonday = DataUtils.verificarDiaSemana(result.getRentalReturnDate(), Calendar.MONDAY);
		// Assert.assertTrue(isMonday);
		// assertThat(result.getRentalReturnDate(), new DiaSemanaMatcher(Calendar.MONDAY));
		// assertThat(result.getRentalReturnDate(), isOn(Calendar.MONDAY));
		assertThat(result.getRentalReturnDate(), isOnMonday());
	}
	
	@Test
	public void shouldNotRentAMovieToNegativeScore() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User user = oneUser().now();
		User user2 = oneUser().withName("User2").now();
		List<Movie> filmes = Arrays.asList(oneMovie().now(), oneMovie().now());
		
		//Quando o methodo hasNegativeScore, passando user for passado, ele vai retornar TRUE
		Mockito.when(spcService.hasNegativeScore(Mockito.any(User.class)))
				.thenReturn(true);
		
		//action
		try {
			service.rentMovie(user, filmes);
			
		//check
			Assert.fail();
		} catch (MovieRentalException ex) {
			Assert.assertThat(ex.getMessage(), is("User denied"));
		}
		
		//Mockito.verify(spcService).hasNegativeScore(user2);
		Mockito.verify(spcService).hasNegativeScore(user);
	}
}
