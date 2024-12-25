FROM eclipse-temurin:23

COPY target/wewe-resource.jar wewe-resource.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/wewe-resource.jar"]