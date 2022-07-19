FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/song-service-0.0.1-SNAPSHOT.jar song_service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/song_service.jar"]