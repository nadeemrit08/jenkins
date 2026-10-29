package com.example.ecommerce.dto;

import java.math.BigDecimal;

public record ProductImportRow(
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {
}
