USE ecommerce_jdbc;

ALTER TABLE products ADD CONSTRAINT uq_products_name UNIQUE (name);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    customer_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'CUSTOMER')),
    CONSTRAINT chk_users_customer_role CHECK (
        (role = 'CUSTOMER' AND customer_id IS NOT NULL)
        OR (role = 'ADMIN' AND customer_id IS NULL)
    )
) ENGINE=InnoDB;

CREATE INDEX idx_users_customer ON users(customer_id);

INSERT INTO users (username, password_hash, role, customer_id) VALUES
('admin', 'pbkdf2$65536$ZGVtb19hZG1pbl9zYWx0$uUGWEP6jm2WoDSddiw7KOK1TPBWnrWkASdEk9GD49dY=', 'ADMIN', NULL),
('aisha', 'pbkdf2$65536$ZGVtb19haXNoYV9zYWx0$K6RDHJ+ZfbT/ty/qqMyX4P2ab1ZBEx3VDc+BrTqaPf4=', 'CUSTOMER', 1),
('rahul', 'pbkdf2$65536$ZGVtb19yYWh1bF9zYWx0$oRr5pzWKIhZZTuqZH0dDXCrmz+jBi/iu0/2snvtrdF0=', 'CUSTOMER', 2)
ON DUPLICATE KEY UPDATE password_hash = VALUES(password_hash), role = VALUES(role), customer_id = VALUES(customer_id);
