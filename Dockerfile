# Use an official OpenJDK runtime as base
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY pom.xml .
COPY src ./src

COPY .env .env
RUN mvn clean package -DskipTests

# Copy the shaded jar to the container
COPY target/dbot-1.0-SNAPSHOT.jar bot.jar
# Use a minimal JRE for running

# Run the bot
CMD ["java", "-jar", "bot.jar"]