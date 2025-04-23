# Stage 1: Build with Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build the project (adjust this if your jar has a different name)
RUN mvn clean package -DskipTests

# Stage 2: Run with lightweight Java 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy only the final jar file and .env from the builder stage
COPY --from=builder /app/target/dbot-1.0-SNAPSHOT.jar bot.jar

# Run the bot
CMD ["java", "-jar", "bot.jar"]