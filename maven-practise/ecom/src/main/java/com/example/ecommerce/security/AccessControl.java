package com.example.ecommerce.security;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.exception.AuthorizationException;

public final class AccessControl {
    public void requireAdmin(AuthenticatedUser user) {
        if (user == null || !user.isAdmin()) {
            throw new AuthorizationException("Admin access required");
        }
    }

    public long requireCustomerId(AuthenticatedUser user) {
        if (user == null || !user.isCustomer() || user.customerId() == null) {
            throw new AuthorizationException("Customer access required");
        }
        return user.customerId();
    }
}
