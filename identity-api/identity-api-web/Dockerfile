FROM openjdk:11-jre
ARG JAR_FILE
ADD target/${JAR_FILE} /identity-api-web.jar
ENTRYPOINT ["java", "-jar", "/identity-api-web.jar"]