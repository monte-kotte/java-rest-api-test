# Java REST API Testing: Component & Integration Tests

## **Description**
REST application has the following parts:
- **user-service** (main)
- **audit-service** (additional, is being used for local run and integration testing)
- **mongo-db**

## Component tests
The main goal is to test user-service in the isolated environment:
- **audit-service** replaced by wiremock
- **mongo-db** spin up via test-containers

Run component tests (build the project & run only component tests):
```bash
mvn clean install
```

## **Integration test**
The main goal is to test user-service in a real environment with real dependencies
- **audit-service** needs to be executed from project
- **mongo-db** spin up via docker

In order to do so, please follow next steps:
```bash
# 0. Build the project (by default skip integration tests)
mvn clean install
# 1. Spin up mongoDB
docker-compose -f docker-compose.yml up -d
# 2. Run audit service (terminal 1)
mvn spring-boot:run -pl service-audit
# 3. Run user service (terminal 2)
mvn spring-boot:run -pl service-user "-Dspring-boot.run.profiles=local"
# 4. Run integration tests (terminal 3)
mvn clean test -Pintegration-tests -Denv=local
```

## **Reporting**
After running integration tests, an HTML Cucumber report will be generated at the following location:

📄 `integration-test/target/cucumber-reports/cucumber-reports.html`
