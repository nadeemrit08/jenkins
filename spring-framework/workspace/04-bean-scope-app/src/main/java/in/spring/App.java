package in.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.spring.bean.FileUpload;
import in.spring.bean.Payment;
import in.spring.config.SpringConfig;
import in.spring.service.CalculatorService;
import in.spring.service.ShoppingCartService;

public class App {
	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

//		Payment p1 = (Payment) context.getBean("payment");
//		Payment p2 = (Payment) context.getBean("payment");
//
//		System.out.println(p1 == p2);
//		
//		CalculatorService c1 = context.getBean(CalculatorService.class);
//		CalculatorService c2 = context.getBean(CalculatorService.class);
//		
//		System.out.println(c1 == c2);
		
//		ShoppingCartService cart1 = context.getBean(ShoppingCartService.class);
//		ShoppingCartService cart2 = context.getBean(ShoppingCartService.class);
//		
//		System.out.println(cart1 == cart2);
		
		FileUpload file1 = context.getBean(FileUpload.class);
		file1.setFileName("Resume.pdf");

		FileUpload file2 = context.getBean(FileUpload.class);
		file2.setFileName("Photo.jpg");

		file1.display();
		file2.display();
	}
}
