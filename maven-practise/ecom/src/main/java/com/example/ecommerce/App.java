package com.example.ecommerce;

import com.example.ecommerce.config.AppConfig;
import com.example.ecommerce.config.ConnectionFactory;
import com.example.ecommerce.config.DriverManagerConnectionFactory;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.jdbc.CartJdbcRepository;
import com.example.ecommerce.repository.jdbc.CustomerJdbcRepository;
import com.example.ecommerce.repository.jdbc.OrderJdbcRepository;
import com.example.ecommerce.repository.jdbc.ProductJdbcRepository;
import com.example.ecommerce.repository.jdbc.UserJdbcRepository;
import com.example.ecommerce.security.AccessControl;
import com.example.ecommerce.security.PasswordHasher;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductImportService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.TransactionManager;
import com.example.ecommerce.ui.ConsoleApplication;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        AppConfig config = AppConfig.load();
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(config);
        TransactionManager transactionManager = new TransactionManager(connectionFactory);

        CustomerRepository customerRepository = new CustomerJdbcRepository();
        ProductRepository productRepository = new ProductJdbcRepository();
        CartRepository cartRepository = new CartJdbcRepository();
        OrderRepository orderRepository = new OrderJdbcRepository();
        UserRepository userRepository = new UserJdbcRepository();
        AccessControl accessControl = new AccessControl();

        AuthService authService = new AuthService(transactionManager, userRepository, customerRepository, new PasswordHasher());
        CustomerService customerService = new CustomerService(transactionManager, customerRepository);
        ProductService productService = new ProductService(transactionManager, productRepository);
        ProductImportService productImportService = new ProductImportService(transactionManager, productRepository, accessControl);
        CartService cartService = new CartService(transactionManager, customerRepository, productRepository, cartRepository);
        OrderService orderService = new OrderService(
                transactionManager,
                customerRepository,
                productRepository,
                cartRepository,
                orderRepository
        );

        new ConsoleApplication(
                authService,
                customerService,
                productService,
                productImportService,
                cartService,
                orderService,
                accessControl
        ).run();
    }
}
