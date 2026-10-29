package com.kodewala.junit;

public class StudentService {

	public String getGrade(Student student) {

		if (student == null) {
			throw new IllegalArgumentException("Student cannot be null");
		}

		int marks = student.getMarks();

		if (marks < 0 || marks > 100) {
			throw new IllegalArgumentException("Invalid Marks");
		}

		if (marks >= 90)
			return "A";

		if (marks >= 75)
			return "B";

		if (marks >= 60)
			return "C";

		return "Fail";
	}

	public boolean isPassed(Student student) {

		if (student == null) {
			throw new IllegalArgumentException("Student cannot be null");
		}

		return student.getMarks() >= 35;
	}

	public double calculatePercentage(Student student) {

		if (student == null) {
			throw new IllegalArgumentException("Student cannot be null");
		}

		return student.getMarks();
	}
}
