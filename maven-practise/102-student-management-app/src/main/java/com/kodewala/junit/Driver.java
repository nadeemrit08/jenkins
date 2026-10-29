package com.kodewala.junit;

public class Driver {

	public static void main(String[] args) {

		Student student = new Student(1, "Nadeem", 82);

		StudentService service = new StudentService();

		System.out.println("Grade : " + service.getGrade(student));

		System.out.println("Passed : " + service.isPassed(student));

		System.out.println("Percentage : " + service.calculatePercentage(student));
	}
}