package com.zepto.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zepto.invoice.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {

}
