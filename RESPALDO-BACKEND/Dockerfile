FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} PrestaBancoBackend.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar", "PrestaBancoBackend.jar"]