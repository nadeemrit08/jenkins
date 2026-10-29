package com.zepto.invoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.invoice.request.InvoiceRequest;
import com.zepto.invoice.response.InvoiceResponse;
import com.zepto.invoice.service.InvoiceService;

@RestController
@RequestMapping("invoice")
public class InvoiceController {

	private InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@PostMapping("create")
	public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {

		InvoiceResponse response = invoiceService.createInvoice(invoiceRequest);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

	}

	@GetMapping("listOfInvoices")
	public ResponseEntity<List<InvoiceResponse>> getAllInvoices(@RequestParam int page, @RequestParam int size) {

		List<InvoiceResponse> response = invoiceService.getInvoices(page, size);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}
