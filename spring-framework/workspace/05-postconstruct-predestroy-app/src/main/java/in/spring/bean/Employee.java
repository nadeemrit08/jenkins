package in.spring.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Employee {

	private int salary;
	private String name;

	private Address address;

	public Employee(int salary, String name) {
		super();

		System.out.println("1. Employee :: constructor");
		this.salary = salary;
		this.name = name;
	}

	public void setAddress(Address _address) {
		System.out.println("2. Employee Dependency Injection");
		this.address = _address;
	}

	public void printInfo() {
		System.out.println("Employee :: doing work");
		address.displayAddressInfo();
	}

	@PostConstruct
	public void init() {
		System.out.println("I am using @PreConstruct");
	}

	@PreDestroy
	public void cleanUp() {
		System.out.println("I am using @PreDestroy");
	}

}
