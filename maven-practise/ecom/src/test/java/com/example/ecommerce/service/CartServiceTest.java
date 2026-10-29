package com.example.ecommerce.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.StockUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartServiceTest {
    private static final long CUSTOMER_ID = 1L;

    private FakeProductRepository productRepository;
    private FakeCartRepository cartRepository;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        FakeCustomerRepository customerRepository = new FakeCustomerRepository(Map.of(
                CUSTOMER_ID, new Customer(CUSTOMER_ID, "Aisha Khan", "aisha@example.com", "9000000001")
        ));
        productRepository = new FakeProductRepository(new LinkedHashMap<>(Map.of(
                10L, new Product(10L, "Keyboard", "Mechanical keyboard", new BigDecimal("100.00"), 5),
                20L, new Product(20L, "Mouse", "Wireless mouse", new BigDecimal("50.00"), 0)
        )));
        cartRepository = new FakeCartRepository();
        cartService = new CartService(
                new ImmediateTransactionExecutor(),
                customerRepository,
                productRepository,
                cartRepository
        );
    }

    @Test
    void addItemRejectsInvalidQuantityBeforeRepositoryCall() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addItem(CUSTOMER_ID, 10L, 0)
        );

        assertEquals("Quantity must be greater than zero", exception.getMessage());
        assertEquals(0, cartRepository.addCalls);
    }

    @Test
    void addItemRejectsOutOfStockProduct() {
        StockUnavailableException exception = assertThrows(
                StockUnavailableException.class,
                () -> cartService.addItem(CUSTOMER_ID, 20L, 1)
        );

        assertEquals("Product is out of stock: Mouse", exception.getMessage());
        assertEquals(0, cartRepository.addCalls);
    }

    @Test
    void addItemAddsAvailableProductToCart() {
        cartService.addItem(CUSTOMER_ID, 10L, 2);

        assertEquals(1, cartRepository.addCalls);
    }
}
