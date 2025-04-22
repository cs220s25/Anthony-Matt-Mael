# Use maven to build the project
FROM maven:3.9.6-eclipse-temurin-21 as builder

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