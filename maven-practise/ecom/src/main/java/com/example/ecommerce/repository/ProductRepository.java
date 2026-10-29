package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.ProductImportRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll(Connection connection) throws SQLException;

    Optional<Product> findById(Connection connection, long productId) throws SQLException;

    Optional<Product> findByIdForUpdate(Connection connection, long productId) throws SQLException;

    void updateStock(Connection connection, long productId, int newStockQuantity) throws SQLException;

    void upsertProductsBatch(Connection connection, List<ProductImportRow> products) throws SQLException;
}
