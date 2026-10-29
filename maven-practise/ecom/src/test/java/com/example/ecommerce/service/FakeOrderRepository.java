package com.example.ecommerce.service;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.domain.OrderSummary;
import com.example.ecommerce.repository.OrderRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

final class FakeOrderRepository implements OrderRepository {
    long nextOrderId = 1001L;
    long createdCustomerId;
    BigDecimal createdTotalAmount;
    List<CartItem> insertedItems = new ArrayList<>();
    int createCalls;
    int batchInsertCalls;

    @Override
    public long createOrder(Connection connection, long customerId, BigDecimal totalAmount) {
        createCalls++;
        createdCustomerId = customerId;
        createdTotalAmount = totalAmount;
        return nextOrderId;
    }

    @Override
    public void insertOrderItemsBatch(Connection connection, long orderId, List<CartItem> cartItems) {
        batchInsertCalls++;
        insertedItems = new ArrayList<>(cartItems);
    }

    @Override
    public List<OrderSummary> findOrdersByCustomerId(Connection connection, long customerId) {
        return List.of();
    }

    @Override
    public List<OrderItem> findItemsByOrderId(Connection connection, long orderId) {
        return List.of();
    }
}
