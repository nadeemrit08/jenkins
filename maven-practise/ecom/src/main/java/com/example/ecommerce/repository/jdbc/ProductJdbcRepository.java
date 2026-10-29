package com.example.ecommerce.repository.jdbc;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.ProductImportRow;
import com.example.ecommerce.repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ProductJdbcRepository implements ProductRepository {
    @Override
    public List<Product> findAll(Connection connection) throws SQLException {
        String sql = """
                SELECT id, name, description, price, stock_quantity
                FROM products
                ORDER BY name
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(map(resultSet));
            }
            return products;
        }
    }

    @Override
    public Optional<Product> findById(Connection connection, long productId) throws SQLException {
        String sql = """
                SELECT id, name, description, price, stock_quantity
                FROM products
                WHERE id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    @Override
    public Optional<Product> findByIdForUpdate(Connection connection, long productId) throws SQLException {
        String sql = """
                SELECT id, name, description, price, stock_quantity
                FROM products
                WHERE id = ?
                FOR UPDATE
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    @Override
    public void updateStock(Connection connection, long productId, int newStockQuantity) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newStockQuantity);
            statement.setLong(2, productId);
            statement.executeUpdate();
        }
    }

    @Override
    public void upsertProductsBatch(Connection connection, List<ProductImportRow> products) throws SQLException {
        String sql = """
                INSERT INTO products (name, description, price, stock_quantity)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    description = VALUES(description),
                    price = VALUES(price),
                    stock_quantity = VALUES(stock_quantity)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (ProductImportRow product : products) {
                statement.setString(1, product.name());
                statement.setString(2, product.description());
                statement.setBigDecimal(3, product.price());
                statement.setInt(4, product.stockQuantity());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private Product map(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("stock_quantity")
        );
    }
}
