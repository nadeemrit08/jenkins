package com.example.ecommerce.domain;

import java.math.BigDecimal;

public record OrderItem(
        long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
