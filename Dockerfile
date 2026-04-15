# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/course-registration-1.0.0.jar app.jar

EXPOSE 8080

# These should be overridden via environment variables or docker-compose
ENV SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle-db:1521/XEPDB1
ENV SPRING_DATASOURCE_USERNAME=COURSE_REG
ENV SPRING_DATASOURCE_PASSWORD=BRUH

ENTRYPOINT ["java", "-jar", "app.jar"]
