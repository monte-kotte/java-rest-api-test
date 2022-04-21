Feature: User service - retrieve user

  Scenario: Retrieve all users from DB
    Given DB user
    Given DB size
    Given Api user
    When I send get all users request
    Then I validate that response code is 200
    Then I validate that response body contains user
    Then I validate that response body size equals DB size

  Scenario: Retrieve user by id
    Given DB user
    Given Api user
    When I send get user by id request
    Then I validate that response body equals user
