FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/monitoring-receiver.jar
COPY ${JAR_FILE} monitoring-receiver.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitoring-receiver.jar"]
EXPOSE 5000