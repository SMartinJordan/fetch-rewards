# Fetch Rewards Backend Software Engineering

### Prerequisite

- Installation of Java 11 ( https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

### How to run the code

Go to the root of the project

#### Windows

- Open a command prompt
- Run the command below

```
gradlew.bat bootRun
```

#### MacOS

- Open a Terminal
- Run the command below

```
./gradlew bootRun
```

### FETCH REWARDS – BACKEND API

ADD A TRANSACTION

`POST /transactions/new`

```
curl --header "Content-Type: application/json" --request POST --data '{ "payer": "MILLER COORS", "points": 10000, "timestamp": "2020-11-01T14:00:00Z" }' http://localhost:8080/transactions/new
```

SPEND POINTS

`POST user/expense`

```
curl --header "Content-Type: application/json" --request POST --data '{ "points": 5000 }' http://localhost:8080/user/points
```

GET USER'S POINTS BALANCE

`GET /user/balance`

```
curl --header "Content-Type: application/json" --request GET http://localhost:8080/user/balance
```


