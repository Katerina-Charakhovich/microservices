FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/resource-service-0.0.1-SNAPSHOT.jar resource-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/resource-service.jar"]