package com.example.ecommerce.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.repository.CustomerRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class FakeCustomerRepository implements CustomerRepository {
    private final Map<Long, Customer> customers;

    FakeCustomerRepository(Map<Long, Customer> customers) {
        this.customers = new LinkedHashMap<>(customers);
    }

    @Override
    public List<Customer> findAll(Connection connection) {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<Customer> findById(Connection connection, long customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public long create(Connection connection, String name, String email, String phone) {
        long id = customers.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
        customers.put(id, new Customer(id, name, email, phone));
        return id;
    }
}
