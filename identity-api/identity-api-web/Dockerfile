#FROM openjdk:9-jre
#ADD identity-api-web* /identity-api-web.jar
#ENTRYPOINT ["java", "-jar", "/identity-api-web.jar"]

FROM openjdk:9-jre
ARG JAR_FILE
ADD target/identity-api-web.jar /identity-api-web.jar
ENTRYPOINT ["java", "--add-modules", "java.xml.bind", "-jar", "/identity-api-web.jar"]