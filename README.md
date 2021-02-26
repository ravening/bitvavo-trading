# Bitvavo-trading

Java application to fetch details from Bitvavo trading system

This project involves two types

1. Displaying BTC in real time
2. Step 1 and notifying the change in BTC price using Telegram Bot

Please follow the respective types for the instructions

## Prerequisites

```
Java
Docker
Maven
Python3
Telegram account <Optional>. Needed for type 2 project
```

## Type 1. Displaying BTC price in real time
If you have docker installed then just build docker image and run the container

```
./mvnw spring-boot:build-image -DskipTests
```

Start the container

```
docker run -it -d -p 8080:8080 -p 8026:8026 --rm --name bitvavo-trading  bitvavo-trading:0.0.1
```

Navigate to
```html
http://localhost:8080
```

If you dont have docker installed but want to run the java jar then follow below steps.

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

To see the bitcoin price in real time, navigate to

```html
http://localhost:8080
```

You can change the source code to monitor for other cryptocurrencies as well


## Type 2. Alerting about BTC prices using Telegram bot

This involves some extra steps. So if you are running the docker or jar file already then stop it.

### Step 1. Starting the config server

The aim of type 2 is to send alerts when BTC prices go up or below the configured prices.\
You configure the prices using the below format

```
bitvavo:
  trades:
    - action: sell
      price: 41000
      crypto: BTC
      tradeprice: 41000
      notify: true
    - action: buy
      price: 38200
      crypto: BTC
      tradeprice: 35000
      notify: true
```

This states that you want to notified when the BTC prices goes above €41000 or below €35000

Create a new git repository and create a new file in it called `bitvavo-client.yml` with the above contents.\
It is important that the file name is same as above mentioned

Enter the above created repository name in the file `bitvavo-config/src/main/resources/application.yml`.\
If its a private repo then enter your username and password as well.

1. Now start the config server
```
cd bitvavo-config
mvn clean package -DskipTests
java -jar target/bitvavo-config-server-0.0.1.jar
```

Let it run in the backend

### Step 2. Setting up Telegram bot

Make sure you have a telegram bot. If you dont have you can skip step and continue with step 3.

Install the below packages in your machine

```
apt install python3-pip
pip3 install telegram
pip3 install python-telegram-bot
```

**Create a bot **

Search for the user @BotFather in Telegram.\
Use the command `/newbot` to create a newbot. Enter the username and other required details.\
Once everything is done, it will display the token. Store it in a safe place.\
Lets store this as `TOKEN`

For the bot to send messages to you, first you need to get your chat id. This can be done by\
sending a `/start` message to `https://telegram.me/getidsbot`. Lets store this as `CHAT_ID`

Configure telegram bot program

You need to enter the `CHAT_ID` and `TOKEN` in `src/main/resources/telegram_bot.py`

You can test to see if telegram is sending message to you by running

```
python3 src/main/resources/telegram_bot.py "hello world"
```

### Step 3. Running the bitvavo trading client

Copy the above mentioned `telegram_bot.py` file to any suitable location in your machine. If not copied\
then messaging wont work. Enter the full path of the script in `src/main/resources/application.properties`

```
telegram.bot.path=<your path>
```

Also enter the location of python3 executable under

```
python.path=
```

Once all these steps are configured, build the packages

```
mvn clean package  -DskipTests
```

and start the application using

```
java -jar target/bitvavo-trading-0.0.1.jar
```

Navigate to

```
http://localhost:8080
```

You should receive a telegram notification whenever the BTC value goes below or above the configured value.\

If you want to be notified at a different price, then change the values in `bitvavo-client.properties` and commit the file.\
Push the changes to the repository and run the below command

```
curl -X POST http://localhost:8080/actuator/refresh
```

This will refresh the configured values and will notifiy on new values.\
Whenever you change the values, dont forget to commit and push the chanes. After that issue the post command.
