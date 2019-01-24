package com.edu.abelem.matchers;

import java.util.Calendar;

public class OwnMatchers {
	public static DayWeekMatcher isOn(Integer diaSemana) {
		return new DayWeekMatcher(diaSemana);
	}
	
	public static DayWeekMatcher isOnMonday() {
		return new DayWeekMatcher(Calendar.MONDAY);
	}
	
	public static DateDiffDaysMatcher isDaysInterval(Integer qtdDays) {
		return new DateDiffDaysMatcher(qtdDays);
	}
	
	public static DateDiffDaysMatcher isToday() {
		return new DateDiffDaysMatcher(0);
	}
}
