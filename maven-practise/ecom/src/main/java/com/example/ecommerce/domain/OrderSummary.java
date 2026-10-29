package com.example.ecommerce.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderSummary(
        long orderId,
        long customerId,
        BigDecimal totalAmount,
        String status,
        LocalDateTime orderedAt,
        List<OrderItem> items
) {
}
