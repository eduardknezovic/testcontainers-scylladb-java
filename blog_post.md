
# How to Use Java Testcontainers with ScyllaDB

## Introduction: Fast-Track Your ScyllaDB Testing

Why wrestle with slow, clunky database testing? This tutorial gets you up and running with ScyllaDB and Testcontainers in Java—fast.

- **Spin up ScyllaDB in seconds** - No complex installation required
- **Test against real database behavior** - No mocks means no surprises in production
- **Simplify your development workflow** - Consistent environments across your team

## Why ScyllaDB Powers Modern Apps

ScyllaDB isn't just fast—it's built for today's data-hungry apps.

- **Outruns Cassandra with lower latency** - Up to 10x better performance
- **Scales efficiently on fewer nodes** - Reduce infrastructure costs
- **Drop-in CQL compatibility** - Use familiar Cassandra tools and drivers
- **Self-tuning cuts ops overhead** - Focus on development, not database tuning

Perfect for real-time analytics, IoT data processing, and high-throughput applications where milliseconds matter.

## Testcontainers: Your Essential Tool for ScyllaDB Testing

Testcontainers brings real ScyllaDB instances to your Java tests—no mocks, no hassle.

- **Launches Dockerized DBs on demand** - Fresh environment for every test run
- **Isolates tests with throwaway containers** - No test interference or state leakage
- **Pairs perfectly with ScyllaDB's speed** - Fast startup complements ScyllaDB's performance

## Before You Begin: Setup Checklist

- Java 11 or higher installed
- Maven or Gradle project configured
- Docker running on your machine
- Basic familiarity with ScyllaDB concepts

## Tutorial: Building a ScyllaDB Test Step-by-Step

### Step 1: Configure Your Project Dependencies

Before we begin, make sure you have:

- Java 21 installed
- Docker installed and running (required for Testcontainers)

Check with `java -version` to make sure you have the correct version installed.

To verify Docker is running correctly, run:

```bash
docker run hello-world
```

### Step 2: Launch ScyllaDB in a Container

Create a test class that starts a ScyllaDB container:

(...)

### Step 3: Connect via the Java Driver

(...)

### Step 4: Define Your Schema

(...)

### Step 5: Insert and Query Data

(...)

### Step 6: Run and Validate the Test

(...)

## Performance Spotlight: Why This Approach Wins

- **Container startup in seconds** - ScyllaDB's efficient design means quick test initialization
- **Real database behavior** - Test against actual CQL responses, not simulated ones
- **Low-latency queries** - Even in a container, ScyllaDB's shard-per-core architecture delivers microsecond responses
- **Isolated test environments** - Each test run gets a pristine database state

## Level Up: Extending Your ScyllaDB Tests

Take your testing further:

- **Test schema migrations** - Verify your database evolution scripts work correctly
- **Simulate multi-node clusters** - Use multiple containers to test distributed scenarios
- **Benchmark performance** - Measure ScyllaDB's throughput under various workloads
- **Test failure scenarios** - Simulate network partitions or node failures

```java
// Example: Creating a multi-node cluster
GenericContainer<?> scyllaNode1 = new GenericContainer<>(DockerImageName.parse("scylladb/scylla:5.2"))
    .withExposedPorts(9042)
    .withNetwork(network)
    .withNetworkAliases("scylla-node1");

GenericContainer<?> scyllaNode2 = new GenericContainer<>(DockerImageName.parse("scylladb/scylla:5.2"))
    .withExposedPorts(9042)
    .withNetwork(network)
    .withNetworkAliases("scylla-node2")
    .withCommand("--seeds=scylla-node1");
```

## Wrap-Up: Master ScyllaDB Testing with Confidence

You've built a fast, real ScyllaDB test in Java that provides genuine database behavior without the overhead of a permanent installation. This approach gives you confidence that your code will work correctly in production.

Try it in your project, customize it for your specific needs, and share your experience with the community!

## Resources: Dive Deeper

- [ScyllaDB Documentation](https://docs.scylladb.com/)
- [Testcontainers for Java](https://www.testcontainers.org/)
- [GitHub Repository with Examples](https://github.com/scylladb/scylladb-testcontainers-java)
- [ScyllaDB University](https://university.scylladb.com/) - Free courses to master ScyllaDB
