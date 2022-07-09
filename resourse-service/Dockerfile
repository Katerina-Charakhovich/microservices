FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/resourse-service-0.0.1-SNAPSHOT.jar resourse-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/resourse-service.jar"]