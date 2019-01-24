package com.edu.abelem.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.edu.abelem.services.CalcRentalValueTest;
import com.edu.abelem.services.MovieRentalServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
	CalcRentalValueTest.class,
	MovieRentalServiceTest.class
})
public class SuiteRun {

}
