Feature: User service - create user

  Scenario: Create new user
    Given Api user without id
    When I send post request
    Then I validate that response code is 200
    Then I validate that response body contains api user with generated id

  Scenario Outline: Create new user
    Given Api user with overridden field
      | fieldName  | <fieldName>  |
      | fieldValue | <fieldValue> |
    When I send post request
    Then I validate that response code is 200
    Then I validate that response body contains api user with generated id

    Examples:
      | fieldName                    | fieldValue |
      | driverLicense                | [null]     |
      | addresses[0].zip             | 12345-6789 |
      | addresses[0].apartmentNumber | [null]     |
      | addresses[0].apartmentNumber | [empty]    |
      | addresses[0].apartmentNumber | [blank]    |
