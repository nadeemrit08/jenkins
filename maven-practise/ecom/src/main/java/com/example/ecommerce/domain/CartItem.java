package com.example.ecommerce.domain;

import java.math.BigDecimal;

public record CartItem(
        long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        int availableStock
) {
    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
