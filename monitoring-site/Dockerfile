FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/monitoring-site.jar
COPY ${JAR_FILE} monitoring-site.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitoring-site.jar"]
EXPOSE 8082