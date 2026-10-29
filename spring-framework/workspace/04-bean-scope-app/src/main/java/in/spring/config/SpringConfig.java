package in.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import in.spring.bean.Payment;

@Configuration
@ComponentScan("in.spring")
//@Scope("prototype")
public class SpringConfig {

	@Bean("payment")
//	@Scope("singleton")
	public Payment payment() {

		Payment payment = new Payment();
		payment.setRefId("REF786");
		return payment;

	}
}
