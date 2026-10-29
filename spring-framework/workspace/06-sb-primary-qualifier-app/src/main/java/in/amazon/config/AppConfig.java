package in.amazon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.amazon.bean.Payment;

@Configuration
public class AppConfig {

	@Bean("upi")
	public Payment upi() {
		return new Payment("REF-UPI-1234");
	}
	
	public Payment card() {
		return new Payment("REF-CARD-1234");
	}
	
}
