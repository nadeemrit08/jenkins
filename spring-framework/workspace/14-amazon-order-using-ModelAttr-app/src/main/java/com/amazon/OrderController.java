package com.amazon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.amazon.request.OrderRequest;

@Controller
public class OrderController {

	@GetMapping("/orders/{id}")
	public String getOrders(@PathVariable("id") String orderId) {

		System.out.println("OrderController.getOrders() called " + orderId);

		return "order-details"; // view resolver

	}

	@PostMapping("placeOrder")
	public String placeOrder(@ModelAttribute OrderRequest orderRequest) {

		// Print received data
		System.out.println("Item : " + orderRequest.getItem());
		System.out.println("Price : " + orderRequest.getPrice());
		System.out.println("Quantity : " + orderRequest.getQty());
		System.out.println("User ID : " + orderRequest.getUserId());
		System.out.println("Description : " + orderRequest.getDescription());
		System.out.println("Address : " + orderRequest.getAddress());
		System.out.println("Mobile : " + orderRequest.getMobile());
		
		return "order-confirmation";

	}
}
