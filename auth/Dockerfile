FROM eclipse-temurin:23

COPY target/wewe-auth.jar wewe-auth.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/wewe-auth.jar"]