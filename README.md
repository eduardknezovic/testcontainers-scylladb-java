
# ScyllaDB Quickstart with Testcontainers in Java

## Introduction

Learn how to get started quickly with ScyllaDB using Testcontainers in Java.

This example demonstrates a simple Java application that connects to a ScyllaDB container and performs some basic operations.

### Why use ScyllaDB?

- Your apps run faster than with Cassandra, perfect for real-time data use cases
- You need fewer servers to do the same work
- It works with your Cassandra apps right now - no changes needed
- Built-in auto-tuning capabilities reduce manual configuration and maintenance overhead

### Why use Testcontainers?

- You get a real database on your computer in seconds
- You can try new ideas fast without breaking anything
- New contributors can learn by doing real work, not just reading
- You can test database changes (ie. migrations) on a real database before they go live

## Setup prerequisites

Before we begin, make sure you have:

- Java 17 installed
- Gradle 8.12 for dependency management
- Docker installed and running (required for Testcontainers)

Check with `java -version` and `gradle --version` to make sure you have the correct versions installed.

To verify Docker is running correctly, run:

```bash
docker run hello-world
```

If you see a welcome message, Docker is working properly. If you get an error, make sure Docker is installed and running.

## How to run the project?

Pull the project from the repository, go to the project root folder and run the following command to start the ScyllaDB container:

```bash
gradle run
```

### The expected output

If you've received the output below, you're all set.

```bash
# ...
Retrieving all users:
User: John Doe (Age: 30)
User: Jane Doe (Age: 27)
# ...
```

## Summary

In this example (and in very few lines of code) we have:
- Set up a ScyllaDB container
- Started a ScyllaDB container
- Connected to ScyllaDB
- Created a keyspace and table
- Inserted some data
- Queried the data
- Stopped the ScyllaDB container

Check out the full code example (with comments) here: [Main.java](https://github.com/eduardknezovic/testcontainers-scylladb-java/blob/main/src/Main.java)

## Learn more

Find out more about ScyllaDB [here](https://www.scylladb.com/)   
Find out more about Testcontainers [here](https://www.testcontainers.org/)


