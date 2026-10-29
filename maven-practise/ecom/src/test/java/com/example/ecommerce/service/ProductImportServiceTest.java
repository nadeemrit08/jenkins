package com.example.ecommerce.service;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.domain.UserRole;
import com.example.ecommerce.dto.ProductImportResult;
import com.example.ecommerce.exception.AuthorizationException;
import com.example.ecommerce.security.AccessControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductImportServiceTest {
    @TempDir
    Path tempDir;

    private FakeProductRepository productRepository;
    private ProductImportService productImportService;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository(new LinkedHashMap<>());
        productImportService = new ProductImportService(
                new ImmediateTransactionExecutor(),
                productRepository,
                new AccessControl()
        );
    }

    @Test
    void adminCanImportProductsFromCsv() throws Exception {
        Path csv = tempDir.resolve("products.csv");
        Files.writeString(csv, """
                name,description,price,stock_quantity
                Webcam,Full HD USB webcam,2499.00,12
                Desk Mat,"Large anti-slip desk mat, black",799.00,30
                """);

        ProductImportResult result = productImportService.importFromCsv(admin(), csv);

        assertEquals(2, result.importedRows());
        assertEquals(1, productRepository.batchUpserts);
        assertEquals(new BigDecimal("2499.00"), productRepository.findById(null, 1L).orElseThrow().price());
        assertEquals("Large anti-slip desk mat, black", productRepository.findById(null, 2L).orElseThrow().description());
    }

    @Test
    void customerCannotImportProducts() {
        AuthenticatedUser customer = new AuthenticatedUser(
                2L,
                "aisha",
                "hash",
                UserRole.CUSTOMER,
                1L,
                true
        );

        assertThrows(
                AuthorizationException.class,
                () -> productImportService.importFromCsv(customer, tempDir.resolve("products.csv"))
        );
        assertEquals(0, productRepository.batchUpserts);
    }

    private AuthenticatedUser admin() {
        return new AuthenticatedUser(1L, "admin", "hash", UserRole.ADMIN, null, true);
    }
}
