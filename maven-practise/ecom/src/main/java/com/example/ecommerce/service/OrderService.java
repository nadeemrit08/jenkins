package com.example.ecommerce.service;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.OrderSummary;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.StockUnavailableException;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class OrderService {
    private final TransactionExecutor transactionExecutor;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            TransactionExecutor transactionExecutor,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            CartRepository cartRepository,
            OrderRepository orderRepository
    ) {
        this.transactionExecutor = transactionExecutor;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public long placeOrder(long customerId) {
        return transactionExecutor.execute(connection -> {
            customerRepository.findById(connection, customerId)
                    .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));

            List<CartItem> cartItems = cartRepository.findByCustomerId(connection, customerId);
            if (cartItems.isEmpty()) {
                throw new IllegalStateException("Cart is empty");
            }

            List<CartItem> pricedItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (CartItem item : cartItems) {
                Product lockedProduct = productRepository.findByIdForUpdate(connection, item.productId())
                        .orElseThrow(() -> new NotFoundException("Product not found: " + item.productId()));

                if (lockedProduct.stockQuantity() < item.quantity()) {
                    throw new StockUnavailableException(
                            "Insufficient stock for " + lockedProduct.name()
                                    + ". Requested " + item.quantity()
                                    + ", available " + lockedProduct.stockQuantity()
                    );
                }

                CartItem pricedItem = new CartItem(
                        lockedProduct.id(),
                        lockedProduct.name(),
                        lockedProduct.price(),
                        item.quantity(),
                        lockedProduct.stockQuantity()
                );
                pricedItems.add(pricedItem);
                totalAmount = totalAmount.add(pricedItem.lineTotal());
            }

            long orderId = orderRepository.createOrder(connection, customerId, totalAmount);
            orderRepository.insertOrderItemsBatch(connection, orderId, pricedItems);

            for (CartItem item : pricedItems) {
                int newStock = item.availableStock() - item.quantity();
                productRepository.updateStock(connection, item.productId(), newStock);
            }

            cartRepository.clear(connection, customerId);
            return orderId;
        });
    }

    public List<OrderSummary> viewOrderHistory(long customerId) {
        return transactionExecutor.execute(connection -> orderRepository.findOrdersByCustomerId(connection, customerId));
    }
}
