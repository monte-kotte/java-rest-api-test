# Java REST api testing example

## **Description**
REST application has the following parts:
- **user-service** (main)
- **audit-service** (additional, is being used for local run and integration testing)
- **mongo-db**

## Component tests
The main goal is to test user-service in the isolated environment:
- **audit-service** replaced by wiremock
- **mongo-db** spin up via test-containers

Run component tests
```bash
mvn clean test
```

## **Integration test**
The main goal is to test user-service in a real environment with real dependencies
- **audit-service** needs to be executed from project
- **mongo-db** spin up via docker

In order to do so, please follow next steps:
```bash
# 1. Spin up mongoDB
docker-compose -f docker-compose.yml up
# 2. Run audit service
mvn spring-boot:run -pl service-audit
# 3. Run user service
mvn spring-boot:run -pl service-user  "-Dspring-boot.run.profiles=local"
# 4. Run integration tests
mvn clean test -Pintegration-tests -Denv=local
```
