
# ScyllaDB Quickstart with Testcontainers in Java

## Introduction

This project is a simple example of how to use Testcontainers to start a ScyllaDB container and connect to it in Java.

### Why use ScyllaDB?

- ScyllaDB is a fast and scalable NoSQL database 
- ScyllaDB is Compatible with Apache Cassandra
- ScyllaDB is faster and more scalable
- ScyllaDB has a more modern and easier to use API
- ScyllaDB is more memory efficient

### Why use Testcontainers?

- You get a real database on your computer in seconds
- You can try new ideas fast without breaking anything
- New contributors can learn by doing real work, not just reading
- You can test database changes (ie. migrations) on a real database before they go live

## Setup

### Prerequisites

Before we begin, make sure you have:

- Java 17 installed
- Gradle 8.12 for dependency management

### How to run the project?

Pull the project from the repository, go to the project root folder and run the following command to start the ScyllaDB container:

```bash
gradle run
```

This is it. 

You have now spun a ScyllaDB container, connected to it, created a keyspace and table, inserted some data and queried it.

