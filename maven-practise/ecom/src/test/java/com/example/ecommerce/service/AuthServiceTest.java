package com.example.ecommerce.service;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.domain.UserRole;
import com.example.ecommerce.exception.AuthenticationException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.PasswordHasher;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceTest {
    private static final String ADMIN_HASH = "pbkdf2$65536$ZGVtb19hZG1pbl9zYWx0$uUGWEP6jm2WoDSddiw7KOK1TPBWnrWkASdEk9GD49dY=";

    @Test
    void loginAcceptsSeededAdminPassword() {
        AuthService authService = new AuthService(
                new ImmediateTransactionExecutor(),
                new InMemoryUserRepository(Map.of(
                        "admin", new AuthenticatedUser(1L, "admin", ADMIN_HASH, UserRole.ADMIN, null, true)
                )),
                new FakeCustomerRepository(Map.of()),
                new PasswordHasher()
        );

        AuthenticatedUser user = authService.login("admin", "admin123".toCharArray());

        assertEquals(UserRole.ADMIN, user.role());
        assertEquals("admin", user.username());
    }

    @Test
    void loginRejectsInvalidPassword() {
        AuthService authService = new AuthService(
                new ImmediateTransactionExecutor(),
                new InMemoryUserRepository(Map.of(
                        "admin", new AuthenticatedUser(1L, "admin", ADMIN_HASH, UserRole.ADMIN, null, true)
                )),
                new FakeCustomerRepository(Map.of()),
                new PasswordHasher()
        );

        assertThrows(
                AuthenticationException.class,
                () -> authService.login("admin", "wrong".toCharArray())
        );
    }

    @Test
    void registerCustomerCreatesCustomerUserAndReturnsAuthenticatedCustomer() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository(Map.of());
        AuthService authService = new AuthService(
                new ImmediateTransactionExecutor(),
                userRepository,
                new FakeCustomerRepository(Map.of()),
                new PasswordHasher()
        );

        AuthenticatedUser user = authService.registerCustomer(
                "newuser",
                "secret123".toCharArray(),
                "New User",
                "new@example.com",
                "9000000009"
        );

        assertEquals("newuser", user.username());
        assertEquals(UserRole.CUSTOMER, user.role());
        assertEquals(1L, user.customerId());
    }

    private static final class InMemoryUserRepository implements UserRepository {
        private final Map<String, AuthenticatedUser> users;

        private InMemoryUserRepository(Map<String, AuthenticatedUser> users) {
            this.users = new HashMap<>(users);
        }

        @Override
        public Optional<AuthenticatedUser> findByUsername(Connection connection, String username) {
            return Optional.ofNullable(users.get(username));
        }

        @Override
        public long createCustomerUser(Connection connection, String username, String passwordHash, long customerId) {
            long id = users.size() + 1L;
            users.put(username, new AuthenticatedUser(id, username, passwordHash, UserRole.CUSTOMER, customerId, true));
            return id;
        }
    }
}
