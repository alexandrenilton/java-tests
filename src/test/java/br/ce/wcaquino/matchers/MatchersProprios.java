package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProprios {
	public static DiaSemanaMatcher isOn(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher isOnMonday() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DateDiffDaysMatcher isDaysInterval(Integer qtdDays) {
		return new DateDiffDaysMatcher(qtdDays);
	}
	
	public static DateDiffDaysMatcher isToday() {
		return new DateDiffDaysMatcher(0);
	}
}
