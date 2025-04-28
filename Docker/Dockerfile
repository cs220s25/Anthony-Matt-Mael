FROM amazonlinux

RUN yum install -y maven-amazon-corretto21
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "app.jar"]