package com.example.ecommerce.domain;

public record AuthenticatedUser(
        long id,
        String username,
        String passwordHash,
        UserRole role,
        Long customerId,
        boolean active
) {
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isCustomer() {
        return role == UserRole.CUSTOMER;
    }
}
