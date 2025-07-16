# Použije openjdk ako základ
FROM openjdk:17-jdk-slim

# Pracovný adresár vo vnútri kontajnera
WORKDIR /app

# Skopíruje JAR súbor do kontajnera
COPY target/moja-aplikacia.jar app.jar

# Príkaz na spustenie aplikácie
ENTRYPOINT ["java", "-jar", "app.jar"]

