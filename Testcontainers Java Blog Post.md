# How to Use Java Testcontainers with ScyllaDB

## 

## Introduction: Fast-Track Your ScyllaDB Testing

Why wrestle with all the complexities of DB configuration for each round of integration testing? In this blog post, I will explain how you can use the Testcontainers library to provide lightweight, throwaway instances of ScyllaDB for unit testing. I’ll go through a hands-on example that you can run yourself, which includes creating the database instance and testing against it.   
 

## Testcontainers: a Valuable Tool for ScyllaDB Testing

There are different ways to test your code. Part of that process will include testing different modules like your database. By using Testcontainers, you can define your dependencies in code. When you run your tests, a ScyllaDB container will be created and then deleted. This allows you to test your application against a real instance of the database without having to worry about complex environment configurations. This also ensures that the database setup has no effecton the production environment. 

The Testcontainers ScyllaDB integration I’ll showcase here works with Java. However, you can also use it with [Go](https://testcontainers.com/modules/scylladb/?language=go), [Python](https://testcontainers.com/modules/scylladb/?language=python), and [Node.js](https://testcontainers.com/modules/scylladb/?language=nodejs).

Some of the advantages of using Testcontainer with ScyllaDB:

- **Launches Dockerized DBs on demand** \- So you get a fresh environment for every test run  
- **Isolates tests with throwaway containers** \- So there’s no test interference or state leakage  
- **Pairs perfectly with ScyllaDB's speed** \- The fast startup complements ScyllaDB's performance

## Tutorial: Building a ScyllaDB Test Step-by-Step

### Step 1: Configure Your Project Dependencies

Before we begin, make sure you have:

- [Java 21 or newer](https://www.oracle.com/java/technologies/downloads/#java24) installed  
- [Docker](https://www.docker.com/get-started/) installed and running (required for Testcontainers)  
- [Gradle](https://gradle.org/) or [Maven](https://maven.apache.org/) project configuration

Check with `java -version` to make sure you have the correct version installed.

To verify that Docker is running correctly, run:

```
docker run hello-world
```

In your `build.gradle` file add the following dependencies

```
dependencies {
    testImplementation 'org.testcontainers:scylladb:1.20.5'
    testImplementation 'com.datastax.oss:java-driver-core:4.17.0'

    // and others if you don't have them yet
    implementation 'ch.qos.logback:logback-classic:1.4.11'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.2'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher:1.10.2'
}
```

### Step 2: Launch ScyllaDB in a Container

Create a `ScyllaDBExampleTest.java` file.

You can copy and paste the code provided below.

This code will start a fresh ScyllaDB instance for every test in this file in the `setUp` method.

To ensure the instance gets shut down after every test, we've created the `tearDown` method, too.

```java
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.scylladb.ScyllaDBContainer;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScyllaDBExampleTest {

    private ScyllaDBContainer scylladb;
    private CqlSession session;

    @Before
    public void setUp() {
        scylladb = new ScyllaDBContainer("scylladb/scylla:2025.1")
            .withExposedPorts(9042, 19042);
        scylladb.start();
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
        if (scylladb != null) {
            scylladb.stop();
        }
    }

} 
```

### Step 3: Connect via the Java Driver

We connect to the ScyllaDB container by creating a new session.

To do so, we need to update our `setUp` method:

```java
    @Before
    public void setUp() {
        scylladb = new ScyllaDBContainer("scylladb/scylla:2025.1")
            .withExposedPorts(9042, 19042);
        scylladb.start();

        // Add the following code to create a connection to ScyllaDB:
        session = CqlSession.builder()
            .addContactPoint(new InetSocketAddress(scylladb.getHost(), scylladb.getMappedPort(9042)))
            .withLocalDatacenter("datacenter1")
            .build();
    }
```

### Step 4: Define Your Schema

Once we have our ScyllaDB instance running and our connection set up, we can create a schema for our (currently empty) database.

Let's define the schema for our freshly created ScyllaDB instance:

```java

    @Before
    public void setUp() {
        scylladb = new ScyllaDBContainer("scylladb/scylla:2025.1")
            .withExposedPorts(9042, 19042);
        scylladb.start();

        session = CqlSession.builder()
            .addContactPoint(new InetSocketAddress(scylladb.getHost(), scylladb.getMappedPort(9042)))
            .withLocalDatacenter("datacenter1")
            .build();

        // Add the following code to create an example schema
        session.execute("CREATE KEYSPACE IF NOT EXISTS test_keyspace WITH replication = "
            + "{'class': 'NetworkTopologyStrategy', 'datacenter1': 1}");
        session.execute("USE test_keyspace");
        session.execute("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, name text, age int)");
    }

```

### Step 5: Insert and Query Data

Once we have prepared the ScyllaDB instance, we can run operations on it.

To do so, let's add a new method to our `ScyllaDBExampleTest` class:

```java

    @Test
    public void testScyllaDBOperations() {

        // Insert sample data
        UUID user1Id = UUID.randomUUID();
        UUID user2Id = UUID.randomUUID();

        // Add two new users
        session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)", user1Id, "John Doe", 30);
        session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)", user2Id, "Jane Doe", 27);

        // Retrieve and verify the inserted data
        ResultSet results = session.execute("SELECT * FROM users");
        int count = 0;
        for (Row row : results) {
            assertNotNull(row.getString("name"));
            assertNotNull(row.getInt("age"));
            count++;
        }

        assertEquals(2, count); // Ensure two new users are present 
    }
```

### Step 6: Run and Validate the Test

Your test is now complete and ready to be executed\!

In that example, this command was used to execute the test, but you are free to use whatever you're comfortable with.

```
./gradlew clean test --no-daemon
```

If successfully executed, you will see the container start in the logs, and the test will pass if the assertions hold

### Full code example

The repository of the full code example can be found here: [https://github.com/eduardknezovic/testcontainers-scylladb-java](https://github.com/eduardknezovic/testcontainers-scylladb-java)

## Performance Spotlight: Why This Approach Wins

- **Container startup in seconds** \- ScyllaDB's efficient design means quick test initialization  
- **Real database behavior** \- test against actual CQL responses, not simulated ones  
- **Low-latency queries** \- even in a container, ScyllaDB's shard-per-core architecture delivers microsecond responses  
- **Isolated test environments** \- each test run gets a pristine database state

## Level Up: Extending Your ScyllaDB Tests

Take your testing further:

- **Test schema migrations** \- Verify that your database evolution scripts work correctly  
- **Simulate multi-node clusters** \- Use multiple containers to test distributed scenarios  
- **Benchmark performance** \- Measure ScyllaDB's throughput under various workloads  
- **Test failure scenarios** \- Simulate network partitions or node failures

```java
// Example: Creating a multi-node cluster
Network network = Network.newNetwork();
    scyllaNode1 = new ScyllaDBContainer("scylladb/scylla:2025.1")
    .withExposedPorts(9042, 19042)
    .withNetwork(network)
    .withNetworkAliases("scylla-node1");

scyllaNode2 = new ScyllaDBContainer("scylladb/scylla:2025.1")
    .withExposedPorts(9042, 19042)
    .withNetwork(network)
    .withNetworkAliases("scylla-node2")
    .withCommand("--seeds=scylla-node1");

scyllaNode1.start();
scyllaNode2.start();
```

## Wrap-Up: Master ScyllaDB Testing with Confidence

You've built a fast, real ScyllaDB test in Java that provides genuine database behavior without the overhead of a permanent installation. This approach gives you confidence that your code will work correctly in production.

You can try it with an [example app](https://university.scylladb.com/courses/scylla-essentials-overview/lessons/quick-wins-install-and-run-scylla/topic/building-an-application-lab/) on ScyllaDB University, customize it to your project and specific needs, and share your experience with the [community](http://forum.scylladb.com)\!

## Resources: Dive Deeper

- [ScyllaDB Documentation](https://docs.scylladb.com/)  
- [Testcontainers](https://www.testcontainers.org/)  
- [GitHub Repository with Examples](https://github.com/eduardknezovic/testcontainers-scylladb-java)  
- [ScyllaDB University](https://university.scylladb.com/) \- Free courses to master ScyllaDB

