package com.example.ecommerce.repository;

import com.example.ecommerce.domain.AuthenticatedUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    Optional<AuthenticatedUser> findByUsername(Connection connection, String username) throws SQLException;

    long createCustomerUser(Connection connection, String username, String passwordHash, long customerId) throws SQLException;
}
