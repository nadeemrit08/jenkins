package in.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import in.spring.bean.Employee;
import in.spring.bean.Payment;

@Configuration
//@Scope("prototype")
public class SpringConfig {

	@Bean("payment")
//	@Scope("singleton")
	public Payment payment() {

		Payment payment = new Payment();
		payment.setRefId("REF786");
		return payment;

	}
	
	@Bean("employee")
	public Employee employee() {
		Employee employee = new Employee(1200, "Dileep");
		return employee;
	}
}
