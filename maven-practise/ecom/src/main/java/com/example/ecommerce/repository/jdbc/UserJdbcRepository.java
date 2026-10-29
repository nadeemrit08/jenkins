package com.example.ecommerce.repository.jdbc;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.domain.UserRole;
import com.example.ecommerce.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class UserJdbcRepository implements UserRepository {
    @Override
    public Optional<AuthenticatedUser> findByUsername(Connection connection, String username) throws SQLException {
        String sql = """
                SELECT id, username, password_hash, role, customer_id, active
                FROM users
                WHERE username = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Long customerId = resultSet.getObject("customer_id", Long.class);
                return Optional.of(new AuthenticatedUser(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        UserRole.valueOf(resultSet.getString("role")),
                        customerId,
                        resultSet.getBoolean("active")
                ));
            }
        }
    }

    @Override
    public long createCustomerUser(Connection connection, String username, String passwordHash, long customerId) throws SQLException {
        String sql = """
                INSERT INTO users (username, password_hash, role, customer_id)
                VALUES (?, ?, 'CUSTOMER', ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"})) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setLong(3, customerId);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("Creating user failed, no generated key returned");
            }
        }
    }
}
