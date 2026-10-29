package com.example.ecommerce.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;

public final class ProductService {
    private final TransactionExecutor transactionExecutor;
    private final ProductRepository productRepository;

    public ProductService(TransactionExecutor transactionExecutor, ProductRepository productRepository) {
        this.transactionExecutor = transactionExecutor;
        this.productRepository = productRepository;
    }

    public List<Product> listProducts() {
        return transactionExecutor.execute(connection -> productRepository.findAll(connection));
    }

    public Product getProduct(long productId) {
        return transactionExecutor.execute(connection -> productRepository.findById(connection, productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId)));
    }
}
