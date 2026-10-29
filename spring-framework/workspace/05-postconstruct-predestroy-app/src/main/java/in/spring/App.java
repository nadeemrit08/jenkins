package in.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.spring.bean.Employee;
import in.spring.bean.Payment;
import in.spring.config.SpringConfig;

public class App {
	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		Payment p1 = (Payment) context.getBean("payment");
		Payment p2 = (Payment) context.getBean("payment");

		System.out.println(p1 == p2);
		
		
		Employee employee = (Employee) context.getBean("employee");
		employee.printInfo();
		
	}
}
