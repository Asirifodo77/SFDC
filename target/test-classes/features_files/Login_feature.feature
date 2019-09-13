Feature: Login
  @positiveScenario
  Scenario: Login to salesforce

    Given I go to the login page
    And I am on SFDC login page
    When I login to SFDC
      | <UserName> | <Password> |
      | UserName   | Pwd        |
