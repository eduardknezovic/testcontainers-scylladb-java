import org.testcontainers.scylladb.ScyllaDBContainer;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.net.InetSocketAddress;
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
            System.out.println("\nScyllaDB container started successfully\n");
            System.out.println("Container IP: " + scylladb.getHost());
            System.out.println("CQL Port: " + scylladb.getMappedPort(9042));

            // Establish connection to ScyllaDB using the new driver
            try (CqlSession session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress(scylladb.getHost(), scylladb.getMappedPort(9042)))
                    .withLocalDatacenter("datacenter1")
                    .build()) {

                // Create keyspace with NetworkTopologyStrategy instead of SimpleStrategy
                System.out.println("\nCreating keyspace and table...");
                session.execute("CREATE KEYSPACE IF NOT EXISTS test_keyspace WITH replication = "
                    + "{'class': 'NetworkTopologyStrategy', 'datacenter1': 1}");
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
                    System.out.printf("User: %s (Age: %d)%n", 
                        row.getString("name"), 
                        row.getInt("age"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scylladb.stop(); // Ensure the container is stopped even if an error occurs
            System.out.println("\nScyllaDB container stopped\n");
        }
    }
}