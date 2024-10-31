# Dockerfile
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copie o pom.xml e o diretório src
COPY pom.xml .
COPY src ./src

# Execute o Maven para construir o JAR
RUN mvn clean package -DskipTests -U

# Estágio final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/service-account-1.0-SNAPSHOT.jar service-account.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "service-account.jar"]
