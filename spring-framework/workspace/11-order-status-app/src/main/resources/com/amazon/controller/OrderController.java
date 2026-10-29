package com.amazon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

	@RequestMapping("/checkStatus")
	public String checkOrderStatus() {

		System.out.println("Request received in Controller...");

		return "status";
	}
}