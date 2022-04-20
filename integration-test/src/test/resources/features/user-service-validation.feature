Feature: User service validation

  Scenario Outline: Post user with incorrect field
    Given Api user with overridden field
      | fieldName  | <fieldName>  |
      | fieldValue | <fieldValue> |
    When I send post request with incorrect user entity
    Then I validate that response code is 400
    Then I validate that response contains violation <fieldName> with <violationMessage>

    Examples:
      | fieldName                  | fieldValue       | violationMessage                          |
      | firstName                  | [null]           | must not be blank                         |
      | firstName                  | [empty]          | must not be blank                         |
      | firstName                  | [blank]          | must not be blank                         |

      | secondName                 | [null]           | must not be blank                         |
      | secondName                 | [empty]          | must not be blank                         |
      | secondName                 | [blank]          | must not be blank                         |

      | dateOfBirth                | [empty]          | must not be null                          |
      | dateOfBirth                | [blank]          | must not be null                          |
      | dateOfBirth                | [null]           | must not be null                          |
      | dateOfBirth                | January 17, 2000 | must be well-formed                       |
      | dateOfBirth                | 2000/1/17        | must be well-formed                       |
      | dateOfBirth                | 17-01-2000       | must be well-formed                       |
      | dateOfBirth                | 2000-12-32       | must be well-formed                       |
      | dateOfBirth                | 2000-13-01       | must be well-formed                       |
      | dateOfBirth                | 2222-01-17       | must be a past date                       |

      | email                      | [null]           | must not be empty                         |
      | email                      | [empty]          | must not be empty                         |
      | email                      | [blank]          | must be a well-formed email address       |
      | email                      | incorrect_email  | must be a well-formed email address       |

      | driverLicense.number       | [null]           | must not be blank                         |
      | driverLicense.number       | [empty]          | must not be blank                         |
      | driverLicense.number       | [blank]          | must not be blank                         |
      | driverLicense.active       | [null]           | must not be null                          |

      | addresses                  | [null]           | must not be empty                         |
      | addresses                  | [empty_list]     | must not be empty                         |

      | addresses[0].streetAddress | [null]           | must not be blank                         |
      | addresses[0].streetAddress | [empty]          | must not be blank                         |
      | addresses[0].streetAddress | [blank]          | must not be blank                         |
      | addresses[0].city          | [null]           | must not be blank                         |
      | addresses[0].city          | [empty]          | must not be blank                         |
      | addresses[0].city          | [blank]          | must not be blank                         |
      | addresses[0].state         | [null]           | must not be blank                         |
      | addresses[0].state         | [empty]          | must not be blank                         |
      | addresses[0].state         | [blank]          | must not be blank                         |

      | addresses[0].zip           | [null]           | must not be null                          |
      | addresses[0].zip           | [empty]          | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | [blank]          | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 1                | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 1234             | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 123456           | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | a                | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 1234a            | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | a1234            | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 123456789        | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 12345-678        | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 1234a-5678       | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 12345-a678       | must match format 'xxxxx' or 'xxxxx-xxxx' |
      | addresses[0].zip           | 12345-6789a      | must match format 'xxxxx' or 'xxxxx-xxxx' |
