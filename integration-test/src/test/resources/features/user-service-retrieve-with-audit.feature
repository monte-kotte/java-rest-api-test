Feature: User service - retrieve user with audit

  Scenario Outline: Retrieve user with audit events
    Given DB user from "TEMPLATE_DB_USER_1"
    Given Api user from "TEMPLATE_API_USER_1"
    Given Audit events from "<auditTemplate>"
    When I send get user with audit request
    Then I validate that response code is 200
    Then I validate that response body equals user with audit

    Examples:
      | auditTemplate                       |
      | TEMPLATE_API_AUDIT_SINGLE_RECORD    |
      | TEMPLATE_API_AUDIT_MULTIPLE_RECORDS |

  Scenario: Retrieve user with no audit events
    Given DB user from "TEMPLATE_DB_USER_1"
    Given Api user from "TEMPLATE_API_USER_1"
    Given Audit events from "TEMPLATE_API_AUDIT_MULTIPLE_RECORDS" with no records for user
    When I send get user with audit request
    Then I validate that response code is 200
    Then I validate that response body equals user with audit
