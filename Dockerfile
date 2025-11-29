FROM eclipse-temurin:21-jre
WORKDIR /app

# After running `mvn clean package` on the host:
COPY target/abac-prototype-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
