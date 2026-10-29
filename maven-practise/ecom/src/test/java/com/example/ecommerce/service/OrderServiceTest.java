package com.example.ecommerce.service;

import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.StockUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceTest {
    private static final long CUSTOMER_ID = 1L;

    private FakeProductRepository productRepository;
    private FakeCartRepository cartRepository;
    private FakeOrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        FakeCustomerRepository customerRepository = new FakeCustomerRepository(Map.of(
                CUSTOMER_ID, new Customer(CUSTOMER_ID, "Aisha Khan", "aisha@example.com", "9000000001")
        ));
        productRepository = new FakeProductRepository(new LinkedHashMap<>(Map.of(
                10L, new Product(10L, "Keyboard", "Mechanical keyboard", new BigDecimal("100.00"), 5),
                20L, new Product(20L, "Mouse", "Wireless mouse", new BigDecimal("50.00"), 10)
        )));
        cartRepository = new FakeCartRepository();
        orderRepository = new FakeOrderRepository();
        orderService = new OrderService(
                new ImmediateTransactionExecutor(),
                customerRepository,
                productRepository,
                cartRepository,
                orderRepository
        );
    }

    @Test
    void placeOrderCreatesOrderBatchInsertsItemsUpdatesInventoryAndClearsCart() {
        cartRepository.setCart(CUSTOMER_ID, List.of(
                new CartItem(10L, "Keyboard", new BigDecimal("100.00"), 2, 5),
                new CartItem(20L, "Mouse", new BigDecimal("50.00"), 3, 10)
        ));

        long orderId = orderService.placeOrder(CUSTOMER_ID);

        assertEquals(1001L, orderId);
        assertEquals(1, orderRepository.createCalls);
        assertEquals(CUSTOMER_ID, orderRepository.createdCustomerId);
        assertEquals(0, new BigDecimal("350.00").compareTo(orderRepository.createdTotalAmount));
        assertEquals(1, orderRepository.batchInsertCalls);
        assertEquals(2, orderRepository.insertedItems.size());
        assertEquals(3, productRepository.findById(null, 10L).orElseThrow().stockQuantity());
        assertEquals(7, productRepository.findById(null, 20L).orElseThrow().stockQuantity());
        assertEquals(2, productRepository.lockedReads);
        assertEquals(2, productRepository.stockUpdates);
        assertEquals(1, cartRepository.clearCalls);
        assertTrue(cartRepository.findByCustomerId(null, CUSTOMER_ID).isEmpty());
    }

    @Test
    void placeOrderRejectsEmptyCart() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> orderService.placeOrder(CUSTOMER_ID)
        );

        assertEquals("Cart is empty", exception.getMessage());
        assertEquals(0, orderRepository.createCalls);
        assertEquals(0, orderRepository.batchInsertCalls);
        assertEquals(0, productRepository.stockUpdates);
        assertEquals(0, cartRepository.clearCalls);
    }

    @Test
    void placeOrderRejectsInsufficientStockBeforeCreatingOrder() {
        cartRepository.setCart(CUSTOMER_ID, List.of(
                new CartItem(10L, "Keyboard", new BigDecimal("100.00"), 6, 5)
        ));

        StockUnavailableException exception = assertThrows(
                StockUnavailableException.class,
                () -> orderService.placeOrder(CUSTOMER_ID)
        );

        assertTrue(exception.getMessage().contains("Insufficient stock"));
        assertEquals(0, orderRepository.createCalls);
        assertEquals(0, orderRepository.batchInsertCalls);
        assertEquals(0, productRepository.stockUpdates);
        assertEquals(0, cartRepository.clearCalls);
        assertEquals(5, productRepository.findById(null, 10L).orElseThrow().stockQuantity());
    }
}
