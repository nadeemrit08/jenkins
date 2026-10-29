package in.amazon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import in.amazon.beans.Payment;

@Configuration
@ComponentScan(basePackages = "in.amazon")
public class SpringConfig {

	@Bean("upi")
	@Primary
	public Payment upi() {
		return new Payment("REF-UPI-1234");
	}
	
	@Bean("card")
	public Payment cart() {
		return new Payment("REF-CARD-1234");
	}
	
}
