# jnosql-redis

A Java application demonstrating the use of [Eclipse JNoSQL](https://github.com/eclipse/jnosql) with **Redis** as a key-value store, built with **Jakarta EE CDI**, **Bean Validation**, and **Gradle**.

## Description

This project implements a simplified **bookstore management system** that covers the full purchase flow:

- **Catalog management** – register and manage authors and books.
- **Inventory management** – track stock levels (supplied and sold units) for each book.
- **Customer management** – register and retrieve customer information.
- **Purchase order processing** – place orders, validate stock, calculate totals, and record order status.

All domain data is persisted in Redis using a key-value strategy, with well-defined key prefixes that group each entity type.

## Tech Stack

| Technology | Role |
|---|---|
| Java 17+ | Primary language |
| Jakarta EE (CDI) | Dependency injection and application lifecycle |
| Eclipse JNoSQL | NoSQL abstraction layer (key-value API) |
| Redis 7 | Key-value data store |
| Lombok | Boilerplate reduction (builders, getters, setters) |
| Jakarta Bean Validation | Domain model validation |
| MicroProfile Config | Externalised configuration |
| Gradle (Kotlin DSL) | Build tool |
| Docker / Docker Compose | Local infrastructure |

## Project Structure

```
jnosql-redis/
├── docker-compose.yml          # Redis + PostgreSQL services
├── build.gradle.kts            # Root build file
├── settings.gradle.kts
└── grupal/                     # Main subproject
    └── src/main/java/com/programacion/avanzada/
        ├── Main.java                   # Application entry point
        ├── model/                      # Domain entities
        │   ├── Author.java
        │   ├── Book.java
        │   ├── Customer.java
        │   ├── Inventory.java
        │   ├── LineItem.java
        │   ├── PurchaseOrder.java
        │   └── Status.java             # PLACED | SHIPPED | DELIVERED | CANCELED
        ├── repositories/
        │   ├── interfaces/             # Repository contracts
        │   └── impl/                   # JNoSQL KeyValueTemplate implementations
        ├── services/
        │   ├── dtos/                   # LineItemDTO (isbn + quantity)
        │   ├── interfaces/             # Service contracts
        │   └── impl/                   # Business logic
        └── shared/
            └── AppConstants.java       # Redis key prefix helpers
```

## Domain Model

| Entity | Key prefix | Description |
|---|---|---|
| `Author` | `author:` | Author name and version |
| `Book` | `book:` | ISBN, title, price, author references |
| `Inventory` | `inv:` | Units supplied and sold per book |
| `Customer` | `customer:` | Name, email |
| `PurchaseOrder` | `order:` | Customer reference, items, total, status |

## Getting Started

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose

### 1. Start infrastructure

```bash
docker compose up -d
```

This starts:
- **Redis** on `localhost:6379`
- **PostgreSQL** on `localhost:5433` (reserved for future use)

### 2. Build the project

```bash
./gradlew build
```

### 3. Run the application

```bash
./gradlew :grupal:run
```

The `Main` class executes a complete purchase flow:

1. Registers an author (*Andy Hunt*).
2. Registers a book (*The Pragmatic Programmer*, ISBN `978-1`).
3. Adds 50 units to stock.
4. Registers a customer (*Juan Calvache*).
5. Places an order for 2 copies of the book.
6. Prints the order summary and remaining inventory.

### Expected output

```
--- Starting Application Flow ---
Registering Author
Registering jBook
Adding Stock
Registering Customer
Processing Purchase
--- Purchase Successful ---
Order id: order:<uuid>
Status: PLACED
Total: 90.0
Items count: 1
--- Inventory Status ---
Sold: 2
Remaining: 48
```

## Configuration

Configuration is provided via MicroProfile Config in `grupal/src/main/resources/META-INF/microprofile-config.properties`:

```properties
jakarta.nosql.keyvalue.provider=org.eclipse.jnosql.databases.redis.keyvalue.RedisKeyValueConfiguration
jakarta.nosql.keyvalue.host=localhost:6379
jnosql.redis.host=localhost
jnosql.redis.port=6379
jnosql.keyvalue.database=books_db
```

## Key Design Decisions

- **Key prefixes** – Each entity type uses a dedicated prefix (`book:`, `author:`, `inv:`, `customer:`, `order:`) to avoid key collisions in the shared Redis instance.
- **Optimistic versioning** – Entities carry an integer `version` field to support future optimistic-locking patterns.
- **Service layer** – Business logic (stock validation, price calculation) is encapsulated in CDI `@ApplicationScoped` services, keeping repositories focused solely on persistence.
- **Validation** – Jakarta Bean Validation annotations on the domain model enforce data integrity before persistence.

## License

This project is intended for academic and learning purposes.
