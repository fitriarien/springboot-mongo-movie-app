FROM openjdk:11
ADD target/springboot-mongo-docker-movies.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]