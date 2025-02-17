
### Prerequisites
- Java 11 or higher
- Docker installed and running
- Maven or Gradle build tool

### What the Example Demonstrates
This example shows how to:
1. Set up a ScyllaDB container for testing
2. Create a test database with a simple product inventory table
3. Simulate a flash sale scenario with concurrent purchases
4. Verify data consistency after parallel operations

The test creates a product with 100 items in stock, simulates 10 concurrent purchases, and verifies that the final stock count is correct (90 items remaining).

### Setup 

#### 1. Java Development Kit (JDK) 11 or higher

Download from: Oracle JDK or
Use OpenJDK: sudo apt install openjdk-11-jdk (Ubuntu/Debian) or
Use brew install openjdk@11 (macOS with Homebrew)
Verify installation: java -version

#### 2. Docker 

```bash
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
docker --version # check the version
```

#### 3. Maven or Gradle

```bash
sudo apt-get install maven
mvn --version # check the version
```

### What next? 

How to set up a simple structure for 
this example, with testcontainers and scylladb?
