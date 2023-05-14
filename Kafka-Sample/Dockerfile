FROM amazoncorretto:17-alpine3.14
RUN apk update && apk add maven
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests --batch-mode --quiet
EXPOSE 8080
CMD ["java", "-jar", "target/Kafka-Sample.jar"]