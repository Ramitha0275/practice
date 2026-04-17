FROM eclipse-temurin:17-jdk
WORKDIR /app
RUN mkdir -p /data && chmod 777 /data

# This must match your target folder exactly
COPY target/practice-app-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]