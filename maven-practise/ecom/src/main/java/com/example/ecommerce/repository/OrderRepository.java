package com.example.ecommerce.repository;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.domain.OrderSummary;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {
    long createOrder(Connection connection, long customerId, BigDecimal totalAmount) throws SQLException;

    void insertOrderItemsBatch(Connection connection, long orderId, List<CartItem> cartItems) throws SQLException;

    List<OrderSummary> findOrdersByCustomerId(Connection connection, long customerId) throws SQLException;

    List<OrderItem> findItemsByOrderId(Connection connection, long orderId) throws SQLException;
}
