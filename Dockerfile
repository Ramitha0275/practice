FROM eclipse-temurin:17-jdk
WORKDIR /app
RUN mkdir -p /data && chmod 777 /data
# Notice we use target/app.jar# Change this line in your Dockerfile:
COPY target/practice-app-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]