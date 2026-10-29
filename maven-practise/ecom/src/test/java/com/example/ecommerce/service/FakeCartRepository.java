package com.example.ecommerce.service;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.repository.CartRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class FakeCartRepository implements CartRepository {
    private final Map<Long, List<CartItem>> carts = new HashMap<>();
    int addCalls;
    int removeCalls;
    int clearCalls;

    void setCart(long customerId, List<CartItem> items) {
        carts.put(customerId, new ArrayList<>(items));
    }

    @Override
    public void addItem(Connection connection, long customerId, long productId, int quantity) {
        addCalls++;
    }

    @Override
    public void removeItem(Connection connection, long customerId, long productId) {
        removeCalls++;
    }

    @Override
    public List<CartItem> findByCustomerId(Connection connection, long customerId) {
        return new ArrayList<>(carts.getOrDefault(customerId, List.of()));
    }

    @Override
    public void clear(Connection connection, long customerId) {
        clearCalls++;
        carts.remove(customerId);
    }
}
