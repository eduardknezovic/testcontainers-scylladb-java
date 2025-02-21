import org.testcontainers.scylladb.ScyllaDBContainer;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.UUID;

/**
 * This example demonstrates how to use Testcontainers with ScyllaDB for integration testing.
 * It shows basic CRUD operations and connection management using a containerized ScyllaDB instance.
 */
public class Main {
    public static void main(String[] args) {
        // Start ScyllaDB container
        // We're using ScyllaDB 6.2, but you can choose other versions based on your needs
        ScyllaDBContainer scylladb = new ScyllaDBContainer("scylladb/scylla:6.2")
            .withExposedPorts(9042);  // Expose the CQL port
        
        try {
            scylladb.start();
            System.out.println("✅ ScyllaDB container started successfully");
            System.out.println("📦 Container IP: " + scylladb.getContainerIpAddress());
            System.out.println("🔌 CQL Port: " + scylladb.getMappedPort(9042));

            // Establish connection to ScyllaDB
            try (Cluster cluster = Cluster.builder()
                    .addContactPoint(scylladb.getContainerIpAddress())
                    .withPort(scylladb.getMappedPort(9042))
                    .build();
                Session session = cluster.connect()) {

                // Create keyspace with SimpleStrategy for testing
                System.out.println("Creating keyspace and table...");
                session.execute("CREATE KEYSPACE IF NOT EXISTS test_keyspace WITH replication = "
                    + "{'class': 'SimpleStrategy', 'replication_factor': 1}");
                session.execute("USE test_keyspace");
                
                // Create a table to store user information
                session.execute("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, "
                    + "name text, age int)");

                // Demonstrate data insertion
                System.out.println("Inserting sample data...");
                UUID user1Id = UUID.randomUUID();
                UUID user2Id = UUID.randomUUID();
                
                session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
                    user1Id, "John Doe", 30);
                session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
                    user2Id, "Jane Doe", 27);

                // Query and display the inserted data
                System.out.println("\nRetrieving all users:");
                ResultSet results = session.execute("SELECT * FROM users");
                for (Row row : results) {
                    System.out.printf("👤 User: %s (Age: %d)%n", 
                        row.getString("name"), 
                        row.getInt("age"));
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure the container is stopped even if an error occurs
            System.out.println("\nStopping ScyllaDB container...");
            scylladb.stop();
        }
    }
}