package com.amazon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

	@GetMapping("/checkStatus")
	public String checkOrderStatus(@RequestParam("orderId") String orderId) {

		System.out.println("Request received in Controller...");

		return "status";
	}
}