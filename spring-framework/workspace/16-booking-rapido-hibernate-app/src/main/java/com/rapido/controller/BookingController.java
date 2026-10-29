package com.rapido.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rapido.request.BookingRequest;
import com.rapido.response.BookingResponse;
import com.rapido.service.BookingService;

@Controller
public class BookingController {

	@Autowired
	BookingService bookingService;

	@GetMapping("/showBookingPage")
	public String showBookingPage() {

		return "booking-page";

	}

	@PostMapping("/bookRide")
	public String acceptBooking(@ModelAttribute BookingRequest bookingRequest, Model model) {

		System.out.println("========= Ride Booking Request =========");
		System.out.println("Mobile      : " + bookingRequest.getMobile());
		System.out.println("Source      : " + bookingRequest.getSource());
		System.out.println("Destination : " + bookingRequest.getDestination());
		System.out.println("Ride Type   : " + bookingRequest.getRideType());
		System.out.println("Amount      : " + bookingRequest.getAmount());
		System.out.println("========================================");

		BookingResponse response = bookingService.bookRide(bookingRequest);

		model.addAttribute("bookingResponse", response);

		return "booking-confirmation";
	}
}
