DROP DATABASE IF EXISTS ecommerce_jdbc;
CREATE DATABASE ecommerce_jdbc;
USE ecommerce_jdbc;

CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    phone VARCHAR(30),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    customer_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_customer FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE,
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'CUSTOMER')),
    CONSTRAINT chk_users_customer_role CHECK (
        (role = 'CUSTOMER' AND customer_id IS NOT NULL)
        OR (role = 'ADMIN' AND customer_id IS NULL)
    )
) ENGINE=InnoDB;

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(160) NOT NULL UNIQUE,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_products_price CHECK (price >= 0),
    CONSTRAINT chk_products_stock CHECK (stock_quantity >= 0)
) ENGINE=InnoDB;

CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cart_product FOREIGN KEY (product_id)
        REFERENCES products(id),
    CONSTRAINT uq_cart_customer_product UNIQUE (customer_id, product_id),
    CONSTRAINT chk_cart_quantity CHECK (quantity > 0)
) ENGINE=InnoDB;

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'CONFIRMED',
    ordered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id)
        REFERENCES customers(id),
    CONSTRAINT chk_orders_total CHECK (total_amount >= 0)
) ENGINE=InnoDB;

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    line_total DECIMAL(10,2) AS (quantity * unit_price) STORED,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id)
        REFERENCES products(id),
    CONSTRAINT chk_order_items_quantity CHECK (quantity > 0),
    CONSTRAINT chk_order_items_price CHECK (unit_price >= 0)
) ENGINE=InnoDB;

CREATE INDEX idx_products_stock ON products(stock_quantity);
CREATE INDEX idx_users_customer ON users(customer_id);
CREATE INDEX idx_cart_customer ON cart(customer_id);
CREATE INDEX idx_orders_customer ON orders(customer_id, ordered_at);
CREATE INDEX idx_order_items_order ON order_items(order_id);

INSERT INTO customers (name, email, phone) VALUES
('Aisha Khan', 'aisha@example.com', '9000000001'),
('Rahul Sharma', 'rahul@example.com', '9000000002');

INSERT INTO users (username, password_hash, role, customer_id) VALUES
('admin', 'pbkdf2$65536$ZGVtb19hZG1pbl9zYWx0$uUGWEP6jm2WoDSddiw7KOK1TPBWnrWkASdEk9GD49dY=', 'ADMIN', NULL),
('aisha', 'pbkdf2$65536$ZGVtb19haXNoYV9zYWx0$K6RDHJ+ZfbT/ty/qqMyX4P2ab1ZBEx3VDc+BrTqaPf4=', 'CUSTOMER', 1),
('rahul', 'pbkdf2$65536$ZGVtb19yYWh1bF9zYWx0$oRr5pzWKIhZZTuqZH0dDXCrmz+jBi/iu0/2snvtrdF0=', 'CUSTOMER', 2);

INSERT INTO products (name, description, price, stock_quantity) VALUES
('Mechanical Keyboard', 'Compact wired keyboard with brown switches', 3499.00, 20),
('Wireless Mouse', 'Ergonomic 2.4GHz wireless mouse', 899.00, 40),
('USB-C Hub', '6-in-1 aluminum USB-C hub', 2199.00, 15),
('Laptop Stand', 'Adjustable aluminum stand', 1599.00, 25),
('Noise Cancelling Headphones', 'Over-ear Bluetooth headphones', 5999.00, 10);
