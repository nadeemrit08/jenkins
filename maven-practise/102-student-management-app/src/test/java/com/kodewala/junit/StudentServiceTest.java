package com.kodewala.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentServiceTest {

	private StudentService service;

	@BeforeEach
	void setup() {
		service = new StudentService();
	}

	@Test
	void shouldCreateStudent() {

		Student student = new Student(1, "Nadeem", 82);

		assertEquals(1, student.getId());
		assertEquals("Nadeem", student.getName());
		assertEquals(82, student.getMarks());
	}

	@Test
	void shouldReturnGradeA() {

		Student student = new Student(1, "Rahul", 95);

		assertEquals("A", service.getGrade(student));
	}

	@Test
	void shouldReturnGradeB() {

		Student student = new Student(2, "Amit", 80);

		assertEquals("B", service.getGrade(student));
	}

	@Test
	void shouldReturnGradeC() {

		Student student = new Student(3, "John", 65);

		assertEquals("C", service.getGrade(student));
	}

	@Test
	void shouldReturnFail() {

		Student student = new Student(4, "David", 30);

		assertEquals("Fail", service.getGrade(student));
	}

	
}
