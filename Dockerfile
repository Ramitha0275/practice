FROM eclipse-temurin:17-jdk
WORKDIR /app
RUN mkdir -p /data && chmod 777 /data
# Notice we use target/app.jar now
COPY target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]