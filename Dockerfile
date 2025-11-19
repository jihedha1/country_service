FROM eclipse-temurin:21-jdk-jammy
VOLUME /temp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]