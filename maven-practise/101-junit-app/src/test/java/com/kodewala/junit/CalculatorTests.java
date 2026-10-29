package com.kodewala.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTests {

	@Test
	public void testAddTwoNumbers() {
		Calculator calculator = new Calculator();

		int expected = 23; // setting the exception 

		int actual = calculator.calculateSum(10, 13); 

		assertEquals(expected, actual);
	}
	
	@Test
	public void testAddTwoNumbersUsingZero() {
		Calculator calculator = new Calculator();
		
		int expected = 13; // setting the exception 
		
		int actual = calculator.calculateSum(0, 13); 
		
		assertEquals(expected, actual);
	}
	
	
}
