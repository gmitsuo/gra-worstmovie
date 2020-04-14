# Golden Raspberry Worst Award Movie App 

### Required:
- JDK 11

### How to run:
- Tests:
    - `./mvnw clean test`
- Build:
    - Jar with maven: `./mvnw clean package [-DskipTests]`. Jar available at `./target/worstmovieapp-0.0.1-SNAPSHOT.jar`.
    - Docker image: make sure docker is running and `./mvn clean spring-boot:build-image`
- The app:
    - Maven: `./mvnw spring-boot:run [-Dspring-boot.run.arguments=--server.port=8080]`
    - Jar: `java -jar ./target/worstmovieapp-0.0.1-SNAPSHOT.jar --server.port=9090`
    - Docker container: `docker run --rm -p 8080:8080 worstmovieapp:0.0.1-SNAPSHOT`