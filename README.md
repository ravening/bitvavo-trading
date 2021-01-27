# Bitvavo-trading

Java application to fetch details from Bitvavo trading system

## Prerequisites

1. Git clone the bitvavo jaa library from https://github.com/bitvavo/java-bitvavo-api
2. Run `mvn clean install` so that jars are pushed to local mvn repo
3. Signup with bitvavo and get api/secret key

## Running the project

1. Add the bitvavo api key, secret key in `application.properties` file
2. Start the project using `mvn spring:boot-run`
3. Hit the end points `localhost:8080/api/assets` or `localhost:8080/api/ticker/BTC-EUR`

## Displaying BTC price in real time

Open the file `index.html` present in the root of the project. This will initiate a websocket\
connection with the server and the BTC price will be displayed in real time.
