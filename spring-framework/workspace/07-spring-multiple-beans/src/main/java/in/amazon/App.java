package in.amazon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.amazon.config.SpringConfig;
import in.amazon.service.PaymentService;

public class App {
	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		PaymentService paymentService = context.getBean(PaymentService.class);
		
		paymentService.doPayment();
	}
}
