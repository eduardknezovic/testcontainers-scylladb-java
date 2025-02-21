import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.testcontainers.containers.ScyllaDBContainer;

public class FlashSaleTest {
    // static ScyllaDBContainer scylladb = new ScyllaDBContainer("scylladb/scylla:6.2");

    @BeforeAll
    static void beforeAll() {
        System.err.println("Starting test suite");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.err.println("Starting test: " + testInfo.getDisplayName());
    }

    @Test
    public void sampleTest() {
        System.err.println("Running sample test...");
        assertTrue(true, "Basic test to ensure test framework is working");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.err.println("Finished test: " + testInfo.getDisplayName());
    }

    @AfterAll
    static void afterAll() {
        System.err.println("Test suite completed");
    }
}
