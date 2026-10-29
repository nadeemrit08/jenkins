package com.example.ecommerce.ui;

import com.example.ecommerce.domain.AuthenticatedUser;
import com.example.ecommerce.domain.CartItem;
import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.domain.OrderSummary;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.exception.AuthenticationException;
import com.example.ecommerce.exception.AuthorizationException;
import com.example.ecommerce.exception.DataAccessException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.StockUnavailableException;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductImportService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.security.AccessControl;

import java.io.Console;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public final class ConsoleApplication {
    private final AuthService authService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductImportService productImportService;
    private final CartService cartService;
    private final OrderService orderService;
    private final AccessControl accessControl;
    private final Scanner scanner = new Scanner(System.in);

    private AuthenticatedUser currentUser;

    public ConsoleApplication(
            AuthService authService,
            CustomerService customerService,
            ProductService productService,
            ProductImportService productImportService,
            CartService cartService,
            OrderService orderService,
            AccessControl accessControl
    ) {
        this.authService = authService;
        this.customerService = customerService;
        this.productService = productService;
        this.productImportService = productImportService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.accessControl = accessControl;
    }

    public void run() {
        System.out.println("JDBC E-Commerce Order System");
        try {
            currentUser = authenticate();
            if (currentUser == null) {
                System.out.println("Goodbye");
                return;
            }
        } catch (DataAccessException e) {
            System.out.println("Database error: " + e.getMessage());
            return;
        } catch (AuthenticationException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (currentUser.isAdmin()) {
            runAdminMenu();
        } else {
            runCustomerMenu();
        }
        System.out.println("Goodbye");
    }

    private AuthenticatedUser authenticate() {
        while (true) {
            System.out.println();
            System.out.println("1. Login");
            System.out.println("2. Register as customer");
            System.out.println("0. Exit");
            int choice = readInt("Choose option: ");
            try {
                if (choice == 1) {
                    return login();
                }
                if (choice == 2) {
                    return registerCustomer();
                }
                if (choice == 0) {
                    return null;
                }
                System.out.println("Invalid option");
            } catch (AuthenticationException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private AuthenticatedUser login() {
        System.out.println();
        System.out.println("Login");
        String username = readText("Username: ");
        char[] password = readPassword("Password: ");
        AuthenticatedUser user = authService.login(username, password);
        System.out.println("Logged in as " + user.username() + " (" + user.role() + ")");
        return user;
    }

    private AuthenticatedUser registerCustomer() {
        System.out.println();
        System.out.println("Customer Registration");
        String username = readText("Username: ");
        char[] password = readPassword("Password: ");
        String name = readText("Full name: ");
        String email = readText("Email: ");
        String phone = readText("Phone: ");
        AuthenticatedUser user = authService.registerCustomer(username, password, name, email, phone);
        System.out.println("Registration successful. Logged in as " + user.username());
        return user;
    }

    private void runAdminMenu() {
        boolean running = true;
        while (running) {
            printAdminMenu();
            int choice = readInt("Choose option: ");
            try {
                switch (choice) {
                    case 1 -> listProducts();
                    case 2 -> showProductDetails();
                    case 3 -> importProductsFromCsv();
                    case 4 -> listCustomers();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (NotFoundException | AuthorizationException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DataAccessException e) {
                System.out.println("Database error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void runCustomerMenu() {
        boolean running = true;
        while (running) {
            printCustomerMenu();
            int choice = readInt("Choose option: ");
            try {
                switch (choice) {
                    case 1 -> listProducts();
                    case 2 -> showProductDetails();
                    case 3 -> addToCart();
                    case 4 -> removeFromCart();
                    case 5 -> viewCart();
                    case 6 -> placeOrder();
                    case 7 -> viewOrderHistory();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (NotFoundException | StockUnavailableException | AuthorizationException
                     | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DataAccessException e) {
                System.out.println("Database error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("1. View available products");
        System.out.println("2. Display product details");
        System.out.println("3. Import products from CSV");
        System.out.println("4. View customers");
        System.out.println("0. Exit");
    }

    private void printCustomerMenu() {
        System.out.println("Customer Menu");
        System.out.println("1. View available products");
        System.out.println("2. Display product details");
        System.out.println("3. Add product to cart");
        System.out.println("4. Remove product from cart");
        System.out.println("5. View my cart");
        System.out.println("6. Place my order");
        System.out.println("7. View my order history");
        System.out.println("0. Exit");
    }

    private void listCustomers() {
        customerService.listCustomers().forEach(customer ->
                System.out.printf("%d. %s <%s>%n", customer.id(), customer.name(), customer.email()));
    }

    private void listProducts() {
        List<Product> products = productService.listProducts();
        System.out.printf("%-5s %-32s %-12s %-8s%n", "ID", "Product", "Price", "Stock");
        for (Product product : products) {
            System.out.printf("%-5d %-32s %-12s %-8d%n",
                    product.id(),
                    truncate(product.name(), 32),
                    money(product.price()),
                    product.stockQuantity());
        }
    }

    private void showProductDetails() {
        long productId = readLong("Product id: ");
        Product product = productService.getProduct(productId);
        System.out.println("ID: " + product.id());
        System.out.println("Name: " + product.name());
        System.out.println("Description: " + product.description());
        System.out.println("Price: " + money(product.price()));
        System.out.println("Available stock: " + product.stockQuantity());
    }

    private void addToCart() {
        long customerId = accessControl.requireCustomerId(currentUser);
        long productId = readLong("Product id: ");
        int quantity = readInt("Quantity: ");
        cartService.addItem(customerId, productId, quantity);
        System.out.println("Item added to cart");
    }

    private void removeFromCart() {
        long customerId = accessControl.requireCustomerId(currentUser);
        long productId = readLong("Product id to remove: ");
        cartService.removeItem(customerId, productId);
        System.out.println("Item removed from cart");
    }

    private void viewCart() {
        long customerId = accessControl.requireCustomerId(currentUser);
        List<CartItem> items = cartService.viewCart(customerId);
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        BigDecimal total = BigDecimal.ZERO;
        System.out.printf("%-5s %-32s %-10s %-8s %-12s %-8s%n", "ID", "Product", "Qty", "Stock", "Unit", "Total");
        for (CartItem item : items) {
            total = total.add(item.lineTotal());
            System.out.printf("%-5d %-32s %-10d %-8d %-12s %-8s%n",
                    item.productId(),
                    truncate(item.productName(), 32),
                    item.quantity(),
                    item.availableStock(),
                    money(item.unitPrice()),
                    money(item.lineTotal()));
        }
        System.out.println("Cart total: " + money(total));
    }

    private void placeOrder() {
        long customerId = accessControl.requireCustomerId(currentUser);
        long orderId = orderService.placeOrder(customerId);
        System.out.println("Order placed successfully. Order id: " + orderId);
    }

    private void viewOrderHistory() {
        long customerId = accessControl.requireCustomerId(currentUser);
        List<OrderSummary> orders = orderService.viewOrderHistory(customerId);
        if (orders.isEmpty()) {
            System.out.println("No previous orders");
            return;
        }
        for (OrderSummary order : orders) {
            System.out.printf("Order #%d | %s | %s | %s%n",
                    order.orderId(),
                    order.status(),
                    order.orderedAt(),
                    money(order.totalAmount()));
            for (OrderItem item : order.items()) {
                System.out.printf("  - %-32s qty=%d unit=%s total=%s%n",
                        truncate(item.productName(), 32),
                        item.quantity(),
                        money(item.unitPrice()),
                        money(item.lineTotal()));
            }
        }
    }

    private void importProductsFromCsv() {
        accessControl.requireAdmin(currentUser);
        String path = readText("CSV path: ");
        var result = productImportService.importFromCsv(currentUser, Path.of(path));
        System.out.println("Imported products: " + result.importedRows());
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Long.parseLong(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number");
            }
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private char[] readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readPassword(prompt);
        }
        System.out.print(prompt);
        return scanner.nextLine().toCharArray();
    }

    private String money(BigDecimal amount) {
        return "Rs " + amount;
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }
}
