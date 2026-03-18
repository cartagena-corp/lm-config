FROM eclipse-temurin:21-jre
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8088

# Ejecutamos la aplicación con optimización de memoria
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]