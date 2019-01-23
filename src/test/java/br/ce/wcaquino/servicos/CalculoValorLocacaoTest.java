package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.MovieBuilder.oneMovie;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import com.edu.abelem.dao.MovieRentalDAO;
import com.edu.abelem.entity.Movie;
import com.edu.abelem.entity.MovieRental;
import com.edu.abelem.entity.User;
import com.edu.abelem.exceptions.MovieOutOfStockException;
import com.edu.abelem.exceptions.MovieRentalException;
import com.edu.abelem.services.MovieRentalService;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	private MovieRentalService service; 
	
	@Parameter(value=0)
	public List<Movie> filmes;
	@Parameter(value=1)
	public Double rentValue;
	@Parameter(value=2)
	public String scene;
	
	@Before
	public void setup() {
		service = new MovieRentalService();
		// LocacaoDAO dao = new LocacaoDAOFake();
		MovieRentalDAO dao = Mockito.mock(MovieRentalDAO.class);
		service.setLocacaoDAO(dao);
	}
	
	private static Movie m1 = oneMovie().now();
	private static Movie m2 = oneMovie().now();
	private static Movie m3 = oneMovie().now();
	private static Movie m4 = oneMovie().now();
	private static Movie m5 = oneMovie().now();
	private static Movie m6 = oneMovie().now(); 
	private static Movie m7 = oneMovie().now();
	private static Movie m8 = oneMovie().now();
	
	
//	@Parameters(name="Test {index} using parameters: {0} - {1}")
	@Parameters(name="{2} = {1}")
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{ Arrays.asList(m1), 4.0, "1 movie: no discount"},
			{ Arrays.asList(m1, m2), 8.0, "2 movies: no discount"},
			{ Arrays.asList(m1, m2, m3), 11.0,  "3 movies: 25% discount" },
			{ Arrays.asList(m1, m2, m3, m4), 13.0, "4 movies: 50% discount" },
			{ Arrays.asList(m1, m2, m3, m4, m5), 14.0, "5 movies: 75% discount" },
			{ Arrays.asList(m1, m2, m3, m4, m5, m6), 14.0, "6 movies: 100% discount"},
			{ Arrays.asList(m1, m2, m3, m4, m5, m6, m7), 18.0, "7 movies: Sem desconto"},
			{ Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8), 22.0, "8 movies: Sem desconto"},
			
		});
		
	}
	
	@Test
	public void shouldCalcRentValueWithDiscounts() throws MovieOutOfStockException, MovieRentalException {
		// scene
		User usuario = new User("User 1");
		// action
		MovieRental result = service.alugarFilme(usuario, filmes); // filmes está no @Parameter(value=0) onde o @ParameterS está marcado, logo a 1 posição do array
		// evaluation
		assertThat(result.getValor(), is(rentValue) ); // rentValue está no @Parameter(value=1) onde o @ParameterS está marcado, logo, a 2 posição do array
	}
}
