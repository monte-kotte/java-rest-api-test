Feature: User service - retrieve user with audit

  Scenario: Retrieve user with audit event
    Given DB user from "TEMPLATE_DB_USER_1"
    Given Api user from "TEMPLATE_API_USER_1"
    Given Audit events from "TEMPLATE_API_AUDIT_1"
    When I send get user with audit request
    Then I validate that response code is 200
    Then I validate that response body equals user with audit

  Scenario: Retrieve user with multiple audit events
    Given DB user from "TEMPLATE_DB_USER_1"
    Given Api user from "TEMPLATE_API_USER_1"
    Given Audit events from "TEMPLATE_API_AUDIT_2"
    When I send get user with audit request
    Then I validate that response code is 200
    Then I validate that response body equals user with audit

  Scenario: Retrieve user with multiple audit events
    Given DB user from "TEMPLATE_DB_USER_1"
    Given Api user from "TEMPLATE_API_USER_1"
    Given Audit events from "TEMPLATE_API_AUDIT_2" with no records for user
    When I send get user with audit request
    Then I validate that response code is 200
    Then I validate that response body equals user with audit
