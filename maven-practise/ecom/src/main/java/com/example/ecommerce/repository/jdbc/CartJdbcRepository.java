package com.example.ecommerce.repository.jdbc;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.repository.CartRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CartJdbcRepository implements CartRepository {
    @Override
    public void addItem(Connection connection, long customerId, long productId, int quantity) throws SQLException {
        String sql = """
                INSERT INTO cart (customer_id, product_id, quantity)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customerId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        }
    }

    @Override
    public void removeItem(Connection connection, long customerId, long productId) throws SQLException {
        String sql = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customerId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<CartItem> findByCustomerId(Connection connection, long customerId) throws SQLException {
        String sql = """
                SELECT p.id AS product_id, p.name AS product_name, p.price AS unit_price,
                       c.quantity, p.stock_quantity AS available_stock
                FROM cart c
                JOIN products p ON p.id = c.product_id
                WHERE c.customer_id = ?
                ORDER BY c.added_at
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<CartItem> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(new CartItem(
                            resultSet.getLong("product_id"),
                            resultSet.getString("product_name"),
                            resultSet.getBigDecimal("unit_price"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("available_stock")
                    ));
                }
                return items;
            }
        }
    }

    @Override
    public void clear(Connection connection, long customerId) throws SQLException {
        String sql = "DELETE FROM cart WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customerId);
            statement.executeUpdate();
        }
    }
}
