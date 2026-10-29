package com.kodewala.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LibraryServiceTest {

	static LibraryService service;

	@BeforeAll
	static void setup() {
		service = new LibraryService();
	}

	@Test
	void shouldCalculateFine() {

		int expected = 50;

		int actual = service.calculateFine(5);

		assertEquals(expected, actual);
	}

	@Test
	void shouldCalculateDiscount() {

		int actual = service.calculateDiscount(1000);

		assertEquals(900, actual);
	}

	@Test
	void shouldCalculateTotalBooks() {

		int actual = service.totalBooks(10, 15, 20);

		assertEquals(45, actual);
	}

	@Test
	void shouldCalculateReadingPoints() {

		int actual = service.calculatePoints(8);

		assertEquals(40, actual);
	}

	@Test
	void shouldCalculateMonthlyFee() {

		int actual = service.monthlyFee(6);

		assertEquals(3000, actual);
	}

}