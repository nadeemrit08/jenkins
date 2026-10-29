package com.zepto.invoice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zepto.invoice.entity.InvoiceEntity;
import com.zepto.invoice.repository.InvoiceRepository;
import com.zepto.invoice.request.InvoiceRequest;
import com.zepto.invoice.response.InvoiceResponse;

@Service
public class InvoiceService {

	private InvoiceRepository invoiceRepository;

	public InvoiceService(InvoiceRepository invoiceRepository) {
		super();
		this.invoiceRepository = invoiceRepository;
	}

	public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {

		InvoiceEntity entity = new InvoiceEntity();

		entity.setCustomerName(invoiceRequest.getCustomerName());
		entity.setGst(invoiceRequest.getGst());
		entity.setStatus("PAID");
		entity.setInvId("INV1234");

		entity = invoiceRepository.save(entity);

		InvoiceResponse invoiceResponse = new InvoiceResponse();

		if (entity.getId() > 0) {
			invoiceResponse.setCustomerName(entity.getCustomerName());
			invoiceResponse.setGst(entity.getGst());
			invoiceResponse.setStatus(entity.getStatus());
			invoiceResponse.setInvId(entity.getInvId());
			invoiceResponse.setId(entity.getId());
		}

		return invoiceResponse;

	}

	public List<InvoiceResponse> getInvoices(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<InvoiceEntity> result = invoiceRepository.findAll(pageable);

		List<InvoiceResponse> responses = new ArrayList<InvoiceResponse>();

		for (InvoiceEntity invoiceEntity : result) {

			InvoiceResponse invoiceResponse = new InvoiceResponse();

			invoiceResponse.setInvId(invoiceEntity.getInvId());
			invoiceResponse.setId(invoiceEntity.getId());
			invoiceResponse.setCustomerName(invoiceEntity.getCustomerName());
			invoiceResponse.setGst(invoiceEntity.getGst());
			invoiceResponse.setStatus(invoiceEntity.getStatus());

			responses.add(invoiceResponse);

		}

		return responses;
	}

}
