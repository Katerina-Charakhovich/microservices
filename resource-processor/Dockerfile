FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/proceccor-service-0.0.1-SNAPSHOT.jar proceccor_service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]