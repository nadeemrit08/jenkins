USE ecommerce_jdbc;

INSERT INTO customers (name, email, phone) VALUES
('Aisha Khan', 'aisha@example.com', '9000000001'),
('Rahul Sharma', 'rahul@example.com', '9000000002')
ON DUPLICATE KEY UPDATE name = VALUES(name), phone = VALUES(phone);

INSERT INTO users (username, password_hash, role, customer_id) VALUES
('admin', 'pbkdf2$65536$ZGVtb19hZG1pbl9zYWx0$uUGWEP6jm2WoDSddiw7KOK1TPBWnrWkASdEk9GD49dY=', 'ADMIN', NULL),
('aisha', 'pbkdf2$65536$ZGVtb19haXNoYV9zYWx0$K6RDHJ+ZfbT/ty/qqMyX4P2ab1ZBEx3VDc+BrTqaPf4=', 'CUSTOMER', 1),
('rahul', 'pbkdf2$65536$ZGVtb19yYWh1bF9zYWx0$oRr5pzWKIhZZTuqZH0dDXCrmz+jBi/iu0/2snvtrdF0=', 'CUSTOMER', 2)
ON DUPLICATE KEY UPDATE password_hash = VALUES(password_hash), role = VALUES(role), customer_id = VALUES(customer_id);

INSERT INTO products (name, description, price, stock_quantity) VALUES
('Mechanical Keyboard', 'Compact wired keyboard with brown switches', 3499.00, 20),
('Wireless Mouse', 'Ergonomic 2.4GHz wireless mouse', 899.00, 40),
('USB-C Hub', '6-in-1 aluminum USB-C hub', 2199.00, 15),
('Laptop Stand', 'Adjustable aluminum stand', 1599.00, 25),
('Noise Cancelling Headphones', 'Over-ear Bluetooth headphones', 5999.00, 10);
