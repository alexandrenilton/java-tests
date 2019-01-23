package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.edu.abelem.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {
	
	private Integer dayWeek;
	
	public DiaSemanaMatcher(Integer dayWeek) {
		this.dayWeek = dayWeek;
	}
	
	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, dayWeek);
		String dataExtenso = data.getDisplayName(
				Calendar.DAY_OF_WEEK, 
				Calendar.LONG, 
				new Locale("fr", "CA") );
		description.appendText(dataExtenso);
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.verificarDiaSemana(data, dayWeek);
	}

}
