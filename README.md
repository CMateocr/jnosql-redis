# jnosql-redis
A Java application demonstrating the use of [Eclipse JNoSQL](https://github.com/eclipse/jnosql) with **Redis** as a key-value store, built with **Jakarta EE CDI**, **Bean Validation**, and **Gradle**.
## Description
This project implements a simplified **bookstore management system** that covers the full purchase flow:
- **Catalog management** – register and manage authors and books.
- **Inventory management** – track stock levels (supplied and sold units) for each book.
- **Customer management** – register and retrieve customer information.
- **Purchase order processing** – place orders, validate stock, calculate totals, and record order status.
All domain data is persisted in Redis using a key-value strategy, with well-defined key prefixes that group each entity type.

