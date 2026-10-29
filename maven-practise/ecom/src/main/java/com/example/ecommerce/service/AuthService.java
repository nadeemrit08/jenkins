package com.example.ecommerce.service;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.exception.AuthenticationException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.PasswordHasher;

import java.util.Arrays;

public final class AuthService {
    private final TransactionExecutor transactionExecutor;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(
            TransactionExecutor transactionExecutor,
            UserRepository userRepository,
            CustomerRepository customerRepository,
            PasswordHasher passwordHasher
    ) {
        this.transactionExecutor = transactionExecutor;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordHasher = passwordHasher;
    }

    public AuthenticatedUser login(String username, char[] password) {
        try {
            AuthenticatedUser user = transactionExecutor.execute(connection -> userRepository.findByUsername(connection, username)
                    .orElseThrow(() -> new AuthenticationException("Invalid username or password")));
            if (!user.active() || !passwordHasher.verify(password, user.passwordHash())) {
                throw new AuthenticationException("Invalid username or password");
            }
            return user;
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    public AuthenticatedUser registerCustomer(
            String username,
            char[] password,
            String name,
            String email,
            String phone
    ) {
        try {
            validateRegistration(username, password, name, email);
            return transactionExecutor.execute(connection -> {
                if (userRepository.findByUsername(connection, username).isPresent()) {
                    throw new AuthenticationException("Username already exists");
                }
                String passwordHash = passwordHasher.hashForStorage(password);
                long customerId = customerRepository.create(connection, name, email, phone);
                userRepository.createCustomerUser(connection, username, passwordHash, customerId);
                return userRepository.findByUsername(connection, username)
                        .orElseThrow(() -> new AuthenticationException("Registration failed"));
            });
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private void validateRegistration(String username, char[] password, String name, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (username.length() < 3 || username.length() > 80) {
            throw new IllegalArgumentException("Username must be 3 to 80 characters");
        }
        if (password == null || password.length < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
    }
}
