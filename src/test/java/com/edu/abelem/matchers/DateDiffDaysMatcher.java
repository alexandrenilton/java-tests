package com.edu.abelem.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.edu.abelem.utils.DataUtils;

public class DateDiffDaysMatcher extends TypeSafeMatcher<Date> {
	
	private Integer qtdDays;
	
	public DateDiffDaysMatcher(Integer qtdDays) {
		this.qtdDays = qtdDays;
	}
	
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isSameDate(data, DataUtils.obterDataComDiferencaDias(qtdDays));
	}

}
