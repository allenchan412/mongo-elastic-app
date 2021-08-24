FROM openjdk:11
COPY /target/mongo-elastic-0.0.1-SNAPSHOT.jar mongo-elastic.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/mongo-elastic.jar"]

