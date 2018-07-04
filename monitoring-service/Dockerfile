FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/monitoring-service.jar
COPY ${JAR_FILE} monitoring-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitoring-service.jar"]
