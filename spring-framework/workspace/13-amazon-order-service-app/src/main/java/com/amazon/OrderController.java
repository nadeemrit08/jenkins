package com.amazon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

	@GetMapping("/orders/{id}")
	public String getOrders(@PathVariable("id") String orderId) {
		
		System.out.println("OrderController.getOrders() called " + orderId);
		
		return "order-details"; // view resolver
		
	} 
}
