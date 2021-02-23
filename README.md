# Bitvavo-trading

Java application to fetch details from Bitvavo trading system

## Prerequisites

```
Java
Docker
Maven
```

### Building packages from scratch

If you want to make source code changes then follow the below prerequisites

1. Git clone the bitvavo java library from https://github.com/bitvavo/java-bitvavo-api
2. cd `java-bitvavo-api`
3. Run `mvn clean install` so that jars are pushed to local mvn repo
4. Signup with bitvavo and get api/secret key

## Running the project

1. Add the bitvavo api key, secret key in `application.properties` file
2. Start the project using `mvn spring:boot-run` or build jar file using `mvn clean packages -DskipTests`
3. Run the jar using `java -jar target/bitvavo-trading-0.0.1.jar`
4. Hit the end points `localhost:8080/api/assets` or `localhost:8080/api/ticker/BTC-EUR`

## Displaying BTC price in real time

To see the bitcoin price in real time, navigate to

```html
http://localhost:8080
```

You can change the source code to monitor for other cryptocurrencies as well

## Running in Docker

Build the docker image using

```
./mvnw spring-boot:build-image -DskipTests
```

Start the container

```
docker run -it -d -p 8080:8080 -p 8026:8026 --rm --name bitvavo-trading  bitvavo-trading:0.0.1
```
