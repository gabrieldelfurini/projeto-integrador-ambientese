FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /home/app/target/grupo5-0.0.1-SNAPSHOT.jar /usr/local/lib/grupo5-0.0.1-SNAPSHOT.jar
COPY opentelemetry /usr/local/lib/opentelemetry
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/usr/local/lib/opentelemetry/opentelemetry-javaagent.jar", "-Dotel.service.name=grupo5", "-Dotel.traces.exporter=otlp", "-Dotel.metrics.exporter=otlp", "-Dotel.logs.exporter=none", "-Dotel.exporter.otlp.endpoint=http://collector-api:4318", "-Dotel.exporter.otlp.protocol=http/protobuf", "-jar","/usr/local/lib/grupo5-0.0.1-SNAPSHOT.jar"]