# JDBC E-Commerce Console Project

This is a mini e-commerce order system built with Java, JDBC, MySQL, and a console UI. It supports product browsing, cart management, order placement, inventory updates, and customer order history.

## Features

- Login with role-based access control.
- Customer self-registration from the console.
- Admin-only product CSV import.
- View products with details, price, and available stock.
- Add and remove cart items per customer.
- Place an order from the current cart.
- Validate stock before order confirmation.
- Insert multiple order items using JDBC batch processing.
- Update product inventory after successful order placement.
- Roll back the entire checkout if any step fails.
- View previous orders with purchased items.

## Architecture

The project uses a layered architecture:

- `ui`: Console input/output only.
- `service`: Business rules and transaction orchestration.
- `repository`: Persistence contracts.
- `repository.jdbc`: JDBC implementations using `PreparedStatement`.
- `domain`: Immutable records used across the app.
- `security`: Password verification and role authorization.
- `config`: Database configuration and connection creation.

Checkout flow is handled by `OrderService.placeOrder`:

1. Validate customer.
2. Load cart items.
3. Lock each product row with `SELECT ... FOR UPDATE`.
4. Validate stock availability.
5. Create an order.
6. Insert order items with `addBatch()` and `executeBatch()`.
7. Reduce product stock.
8. Clear the cart.
9. Commit the transaction.

If any step fails, `TransactionManager` rolls back the connection.

## SOLID Notes

- Single Responsibility: UI, services, repositories, configuration, and transaction handling are separated.
- Open/Closed: Service classes depend on repository interfaces, so JDBC implementations can be replaced without changing business logic.
- Liskov Substitution: Repository implementations follow their interfaces directly.
- Interface Segregation: Repositories expose focused methods for their aggregate.
- Dependency Inversion: High-level services depend on repository abstractions and `ConnectionFactory`.

## Database Design

Tables:

- `users`
- `customers`
- `products`
- `cart`
- `orders`
- `order_items`

ER diagram: [docs/database-design.svg](docs/database-design.svg)

SQL files:

- [db/schema.sql](db/schema.sql)
- [db/seed.sql](db/seed.sql)
- [db/init.sql](db/init.sql)
- [db/migration-auth-product-import.sql](db/migration-auth-product-import.sql)

For a fresh database, run:

```bash
mysql -u root -p < db/init.sql
```

For an existing database created before authentication was added, run:

```bash
mysql -u root -p < db/migration-auth-product-import.sql
```

## Setup

Prerequisites:

- Java 17+
- Maven 3.9+
- MySQL 8+

Create the database and tables:

```bash
mysql -u root -p < db/schema.sql
mysql -u root -p < db/seed.sql
```

Create your local database config:

```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

Edit `src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/ecommerce_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=your_password
```

You can also use environment variables:

```bash
export DB_URL='jdbc:mysql://localhost:3306/ecommerce_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='your_password'
```

## Run

```bash
mvn compile
mvn exec:java
```

Demo users:

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `admin123` | `ADMIN` |
| `aisha` | `aisha123` | `CUSTOMER` |
| `rahul` | `rahul123` | `CUSTOMER` |

New customers can also register from the startup menu. Registration creates both:

- A row in `customers`.
- A linked `CUSTOMER` row in `users` with a PBKDF2 password hash.

## Product CSV Import

Product import is available only from the admin menu.

CSV format:

```csv
name,description,price,stock_quantity
Webcam,Full HD USB webcam,2499.00,12
Desk Mat,"Large anti-slip desk mat, black",799.00,30
```

Sample file:

[samples/products.csv](samples/products.csv)

Products are imported with batch processing. If a product with the same name already exists, its description, price, and stock are updated.

## Test

Run the unit tests:

```bash
mvn test
```

Current tests cover:

- Customer registration.
- Seeded admin login.
- Successful order placement.
- Admin product CSV import.
- Customer rejection from admin product import.
- Batch order item handoff from the service layer.
- Inventory reduction after checkout.
- Cart clearing after checkout.
- Empty cart rejection.
- Insufficient stock rejection before order creation.
- Cart quantity validation.
- Out-of-stock cart rejection.

## Important JDBC Requirements Covered

- All SQL execution uses `PreparedStatement`.
- Order placement uses explicit `commit` and `rollback`.
- Order item insertion uses JDBC batch processing.
- Admin product import uses JDBC batch processing.
- Product stock rows are locked during checkout to avoid overselling.

## Package Structure

```text
src/main/java/com/example/ecommerce
├── App.java
├── config
├── domain
├── exception
├── repository
│   └── jdbc
├── service
└── ui
```
