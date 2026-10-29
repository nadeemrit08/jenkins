package com.example.ecommerce.service;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.StockUnavailableException;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;

public final class CartService {
    private final TransactionExecutor transactionExecutor;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(
            TransactionExecutor transactionExecutor,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            CartRepository cartRepository
    ) {
        this.transactionExecutor = transactionExecutor;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public void addItem(long customerId, long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        transactionExecutor.execute(connection -> {
            customerRepository.findById(connection, customerId)
                    .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
            Product product = productRepository.findById(connection, productId)
                    .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
            if (product.stockQuantity() <= 0) {
                throw new StockUnavailableException("Product is out of stock: " + product.name());
            }
            cartRepository.addItem(connection, customerId, productId, quantity);
            return null;
        });
    }

    public void removeItem(long customerId, long productId) {
        transactionExecutor.execute(connection -> {
            cartRepository.removeItem(connection, customerId, productId);
            return null;
        });
    }

    public List<CartItem> viewCart(long customerId) {
        return transactionExecutor.execute(connection -> cartRepository.findByCustomerId(connection, customerId));
    }
}
