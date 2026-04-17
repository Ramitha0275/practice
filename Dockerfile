FROM eclipse-temurin:17-jdk
WORKDIR /app
# Create the data directory so the app doesn't crash trying to find it
RUN mkdir -p /data
COPY target/practice-app-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]