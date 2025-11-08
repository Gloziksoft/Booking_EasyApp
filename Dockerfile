# Použije moderný Java 17 image (Eclipse Temurin)
FROM eclipse-temurin:17-jdk-jammy

# Pracovný adresár vo vnútri kontajnera
WORKDIR /app

# Skopíruje JAR súbor
COPY target/*.jar app.jar

# Spustí aplikáciu
ENTRYPOINT ["java", "-jar", "app.jar"]

