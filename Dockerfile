FROM openjdk:12-jdk

EXPOSE 8080

VOLUME C:/users/momir1/data

COPY /target/FruitApplication-10.0.1.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]