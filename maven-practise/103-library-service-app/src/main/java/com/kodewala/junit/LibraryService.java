package com.kodewala.junit;

public class LibraryService {

	public static void main(String[] args) {

		LibraryService service = new LibraryService();

		int fine = service.calculateFine(5);
		System.out.println("Fine for 5 extra days : Rs. " + fine);

		int discount = service.calculateDiscount(1000);
		System.out.println("Discounted Amount : Rs. " + discount);

		int books = service.totalBooks(10, 15, 20);
		System.out.println("Total Books : " + books);

		int points = service.calculatePoints(8);
		System.out.println("Reading Points : " + points);

		int fee = service.monthlyFee(6);
		System.out.println("Monthly Fee for 6 months : Rs. " + fee);
	}

	// Fine = Rs.10 per extra day
	public int calculateFine(int extraDays) {
		return extraDays * 10;
	}

	// Membership Discount
	public int calculateDiscount(int amount) {
		return amount - 100;
	}

	// Total Books
	public int totalBooks(int science, int maths, int english) {
		return science + maths + english;
	}

	// Reading Points
	public int calculatePoints(int booksRead) {
		return booksRead * 5;
	}

	// Monthly Fees
	public int monthlyFee(int months) {
		return months * 500;
	}
}
