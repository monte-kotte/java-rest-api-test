Feature: User service - update user

  Scenario: Update user
    Given DB user from "TEMPLATE_DB_USER_1"
    And Api user from "TEMPLATE_API_USER_2"
    When I send update user request
    Then I validate that response code is 200
    Then I validate that response body contains api user with generated id
    Then I validate that response user id equals db user id

  Scenario Outline: Update user
    Given DB user from "TEMPLATE_DB_USER_1"
    And Api user from "TEMPLATE_API_USER_2" with overridden field
      | fieldName  | <fieldName>  |
      | fieldValue | <fieldValue> |
    When I send update user request
    Then I validate that response code is 200
    Then I validate that response body contains api user with generated id
    Then I validate that response user id equals db user id

    Examples:
      | fieldName                    | fieldValue |
      | driverLicense                | [null]     |
      | addresses[0].zip             | 12345-6789 |
      | addresses[0].apartmentNumber | [null]     |
      | addresses[0].apartmentNumber | [empty]    |
      | addresses[0].apartmentNumber | [blank]    |
