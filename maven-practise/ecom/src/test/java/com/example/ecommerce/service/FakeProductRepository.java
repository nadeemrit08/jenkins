package com.example.ecommerce.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.ProductImportRow;
import com.example.ecommerce.repository.ProductRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class FakeProductRepository implements ProductRepository {
    private final Map<Long, Product> products;
    int lockedReads;
    int stockUpdates;
    int batchUpserts;

    FakeProductRepository(Map<Long, Product> products) {
        this.products = products;
    }

    @Override
    public List<Product> findAll(Connection connection) {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(Connection connection, long productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public Optional<Product> findByIdForUpdate(Connection connection, long productId) {
        lockedReads++;
        return findById(connection, productId);
    }

    @Override
    public void updateStock(Connection connection, long productId, int newStockQuantity) {
        Product product = products.get(productId);
        products.put(productId, new Product(
                product.id(),
                product.name(),
                product.description(),
                product.price(),
                newStockQuantity
        ));
        stockUpdates++;
    }

    @Override
    public void upsertProductsBatch(Connection connection, List<ProductImportRow> importedProducts) {
        batchUpserts++;
        long nextId = products.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
        for (ProductImportRow importedProduct : importedProducts) {
            Product existingProduct = products.values().stream()
                    .filter(product -> product.name().equals(importedProduct.name()))
                    .findFirst()
                    .orElse(null);
            long id = existingProduct == null ? nextId++ : existingProduct.id();
            products.put(id, new Product(
                    id,
                    importedProduct.name(),
                    importedProduct.description(),
                    importedProduct.price(),
                    importedProduct.stockQuantity()
            ));
        }
    }
}
