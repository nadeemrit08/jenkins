package com.example.ecommerce.repository.jdbc;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.domain.OrderSummary;
import com.example.ecommerce.repository.OrderRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class OrderJdbcRepository implements OrderRepository {
    @Override
    public long createOrder(Connection connection, long customerId, BigDecimal totalAmount) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, total_amount, status) VALUES (?, ?, 'CONFIRMED')";
        try (PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"})) {
            statement.setLong(1, customerId);
            statement.setBigDecimal(2, totalAmount);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("Creating order failed, no generated key returned");
            }
        }
    }

    @Override
    public void insertOrderItemsBatch(Connection connection, long orderId, List<CartItem> cartItems) throws SQLException {
        String sql = """
                INSERT INTO order_items (order_id, product_id, quantity, unit_price)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (CartItem item : cartItems) {
                statement.setLong(1, orderId);
                statement.setLong(2, item.productId());
                statement.setInt(3, item.quantity());
                statement.setBigDecimal(4, item.unitPrice());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public List<OrderSummary> findOrdersByCustomerId(Connection connection, long customerId) throws SQLException {
        String sql = """
                SELECT id, customer_id, total_amount, status, ordered_at
                FROM orders
                WHERE customer_id = ?
                ORDER BY ordered_at DESC, id DESC
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<OrderSummary> orders = new ArrayList<>();
                while (resultSet.next()) {
                    long orderId = resultSet.getLong("id");
                    orders.add(new OrderSummary(
                            orderId,
                            resultSet.getLong("customer_id"),
                            resultSet.getBigDecimal("total_amount"),
                            resultSet.getString("status"),
                            toLocalDateTime(resultSet.getTimestamp("ordered_at")),
                            findItemsByOrderId(connection, orderId)
                    ));
                }
                return orders;
            }
        }
    }

    @Override
    public List<OrderItem> findItemsByOrderId(Connection connection, long orderId) throws SQLException {
        String sql = """
                SELECT oi.product_id, p.name AS product_name, oi.quantity, oi.unit_price, oi.line_total
                FROM order_items oi
                JOIN products p ON p.id = oi.product_id
                WHERE oi.order_id = ?
                ORDER BY oi.id
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<OrderItem> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(new OrderItem(
                            resultSet.getLong("product_id"),
                            resultSet.getString("product_name"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("unit_price"),
                            resultSet.getBigDecimal("line_total")
                    ));
                }
                return items;
            }
        }
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
