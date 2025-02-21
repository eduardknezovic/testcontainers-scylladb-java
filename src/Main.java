import org.testcontainers.scylladb.ScyllaDBContainer;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, no nested folders!");

        ScyllaDBContainer scylladb = new ScyllaDBContainer("scylladb/scylla:6.2");

        scylladb.start();

        System.out.println("ScyllaDB started on " + scylladb.getContainerIpAddress());

        // Initialize connection
        Cluster cluster = Cluster.builder()
            .addContactPoint(scylladb.getContainerIpAddress())
            .withPort(scylladb.getMappedPort(9042))
            .build();
        Session session = cluster.connect();

        // Create keyspace and table
        session.execute("CREATE KEYSPACE IF NOT EXISTS test_keyspace WITH replication = "
            + "{'class': 'SimpleStrategy', 'replication_factor': 1}");
        session.execute("USE test_keyspace");
        session.execute("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, "
            + "name text, age int)");

        // Insert data
        UUID userId = UUID.randomUUID();
        session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
            userId, "John Doe", 30);

        // Insert data
        userId = UUID.randomUUID();
        session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
            userId, "Jane Doe", 27);

        // Query data
        ResultSet results = session.execute("SELECT * FROM users");
        for (Row row : results) {
            System.out.println("Retrieved user: " + row.getString("name") 
                + ", Age: " + row.getInt("age"));
        }

        // Clean up
        session.close();
        cluster.close();
        scylladb.stop();

    }
}