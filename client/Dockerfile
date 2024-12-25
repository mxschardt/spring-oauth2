FROM eclipse-temurin:23

COPY target/wewe-client.jar wewe-client.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/wewe-client.jar"]