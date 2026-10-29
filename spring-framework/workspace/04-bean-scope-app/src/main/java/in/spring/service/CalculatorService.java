package in.spring.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CalculatorService {

	public CalculatorService() {
		System.out.println("CalculatorService.CalculatorService()");
	}

	public int add(int a, int b) {
		return a + b;
	}

}
