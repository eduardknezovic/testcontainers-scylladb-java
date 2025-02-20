import org.testcontainers.containers.ScyllaDBContainer;

public class FlashSaleTest {
    static ScyllaDBContainer scylladb = new ScyllaDBContainer("scylladb/scylla:6.2");

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
