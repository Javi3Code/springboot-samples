# Springboot: Redis-cache and PostgresSQL as BBDD : Rest

## Book Rental Store - Spring Boot Project

This is a sample project that demonstrates the usage of Redis as a cache and PostgreSQL as a database in a Spring Boot application. The
project simulates a book rental store where users can register, browse books, and place orders.

## Prerequisites

To run this project, make sure you have the following software installed on your system:
```
- Java Development Kit (JDK) 17 or later
- Apache Maven
- Spring-boot 3.1
- Docker
- IDE (IntelliJ IDEA recommended)
```

## Getting Started

Follow the instructions below to set up and run the project locally:
The project includes a .run (Intellij Idea) to easily compile, package, create the images, and run all the necessary containers, or you can run the next commands:

```bash
        mvn clean compile package -DskipTests --batch-mode --quiet -Pstandalone &&
        docker build . -t red-pos-sam &&
        docker-compose up
```

## Testing the API

To test the API endpoints, you can use the provided Postman collection. Import the collection that is included in `.postman` directory into
Postman, and you will have a collection with pre-configured requests for the available endpoints.

## Fake Data

During startup, the application adds fake data using the Java Faker library. You can view the generated data in the main class of the
project. This data provides a starting point for testing and interacting with the application.

## Conclusion

Congratulations! You have successfully set up and run the Book Rental Store project. Feel free to explore the codebase, modify it to suit
your needs, and use it as a reference for building similar Spring Boot applications. If you encounter any issues or have any questions,
please refer to the project documentation or reach out to the project maintainers.