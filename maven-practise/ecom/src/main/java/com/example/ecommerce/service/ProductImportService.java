package com.example.ecommerce.service;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.dto.ProductImportResult;
import com.example.ecommerce.dto.ProductImportRow;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.security.AccessControl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ProductImportService {
    private final TransactionExecutor transactionExecutor;
    private final ProductRepository productRepository;
    private final AccessControl accessControl;

    public ProductImportService(
            TransactionExecutor transactionExecutor,
            ProductRepository productRepository,
            AccessControl accessControl
    ) {
        this.transactionExecutor = transactionExecutor;
        this.productRepository = productRepository;
        this.accessControl = accessControl;
    }

    public ProductImportResult importFromCsv(AuthenticatedUser user, Path csvPath) {
        accessControl.requireAdmin(user);
        List<ProductImportRow> products = readProducts(csvPath);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("CSV file does not contain product rows");
        }
        transactionExecutor.execute(connection -> {
            productRepository.upsertProductsBatch(connection, products);
            return null;
        });
        return new ProductImportResult(products.size());
    }

    private List<ProductImportRow> readProducts(Path csvPath) {
        try {
            List<String> lines = Files.readAllLines(csvPath);
            List<ProductImportRow> products = new ArrayList<>();
            int startIndex = hasHeader(lines) ? 1 : 0;
            for (int i = startIndex; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.isBlank()) {
                    continue;
                }
                products.add(parseProduct(line, i + 1));
            }
            return products;
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read CSV file: " + csvPath, e);
        }
    }

    private boolean hasHeader(List<String> lines) {
        if (lines.isEmpty()) {
            return false;
        }
        String firstLine = lines.get(0).trim().toLowerCase();
        return firstLine.startsWith("name,") || firstLine.startsWith("product_name,");
    }

    private ProductImportRow parseProduct(String line, int lineNumber) {
        List<String> columns = parseCsvLine(line);
        if (columns.size() != 4) {
            throw new IllegalArgumentException("Invalid CSV at line " + lineNumber + ". Expected: name,description,price,stock_quantity");
        }
        String name = columns.get(0).trim();
        String description = columns.get(1).trim();
        if (name.isBlank()) {
            throw new IllegalArgumentException("Product name is required at line " + lineNumber);
        }
        try {
            BigDecimal price = new BigDecimal(columns.get(2).trim());
            int stockQuantity = Integer.parseInt(columns.get(3).trim());
            if (price.compareTo(BigDecimal.ZERO) < 0 || stockQuantity < 0) {
                throw new IllegalArgumentException("Price and stock must be non-negative at line " + lineNumber);
            }
            return new ProductImportRow(name, description, price, stockQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value at line " + lineNumber, e);
        }
    }

    private List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        values.add(current.toString());
        return values;
    }
}
