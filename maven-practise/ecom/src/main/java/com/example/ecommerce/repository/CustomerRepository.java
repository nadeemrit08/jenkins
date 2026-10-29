package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> findAll(Connection connection) throws SQLException;

    Optional<Customer> findById(Connection connection, long customerId) throws SQLException;

    long create(Connection connection, String name, String email, String phone) throws SQLException;
}
