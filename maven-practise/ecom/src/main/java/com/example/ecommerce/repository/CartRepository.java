package com.example.ecommerce.repository;

import com.example.ecommerce.domain.CartItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CartRepository {
    void addItem(Connection connection, long customerId, long productId, int quantity) throws SQLException;

    void removeItem(Connection connection, long customerId, long productId) throws SQLException;

    List<CartItem> findByCustomerId(Connection connection, long customerId) throws SQLException;

    void clear(Connection connection, long customerId) throws SQLException;
}
