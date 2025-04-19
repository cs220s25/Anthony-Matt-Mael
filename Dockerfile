# Use an official OpenJDK runtime as base
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

COPY .env .env

# Copy the shaded jar to the container
COPY target/dbot-1.0-SNAPSHOT.jar bot.jar

# Run the bot
CMD ["java", "-jar", "bot.jar"]