package in.spring.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ShoppingCartService {

	
	public ShoppingCartService() {
		System.out.println("ShoppingCartService.ShoppingCartService() : ");
		
	}
}
