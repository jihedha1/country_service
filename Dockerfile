FROM openjdk:21-oracle
VOLUME /temp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]