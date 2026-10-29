package com.example.ecommerce.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.exception.DataAccessException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;

import java.util.List;

public final class CustomerService {
    private final TransactionExecutor transactionExecutor;
    private final CustomerRepository customerRepository;

    public CustomerService(TransactionExecutor transactionExecutor, CustomerRepository customerRepository) {
        this.transactionExecutor = transactionExecutor;
        this.customerRepository = customerRepository;
    }

    public List<Customer> listCustomers() {
        return transactionExecutor.execute(connection -> customerRepository.findAll(connection));
    }

    public Customer requireCustomer(long customerId) {
        try {
            return transactionExecutor.execute(connection -> customerRepository.findById(connection, customerId)
                    .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId)));
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
