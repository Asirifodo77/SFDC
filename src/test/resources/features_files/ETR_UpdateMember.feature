Feature: ETR Update Member
  @ETR_Update_Member
  Scenario: Validate member update from ETR
    Given I read ETR request body from Json file
    Given I cleanup data for existing ETR member from SF and MatrixDB
    When I send POST request to create new member through ETR
    Then I validate the memberID in salesforce and Matrix for ETR Created member
    Then I login to SF to search ETR member
    Then I search ETR member using card number
      | <Card_Number>|
      | Card_Number  |
    Then I read Store Location data from SF
    Then I read details from ETR member profile in SF
    Then I read ETR member details from Matrix
    Then I validate ETR Membership Information with SF
    Then I validate ETR Membership Information with matrixDB
    Then I read the Update ETR request body from Json file
    Then I send POST request to update ETR member
    Then I validate the memberID in salesforce and Matrix for ETR Updated member
    Then I login to SF to search ETR member
    Then I search updated ETR member using card number
      | <Card_Number>|
      | Card_Number  |
    Then I read Store Location data from SF
    Then I read details from ETR member profile in SF
    Then I read ETR member details from Matrix
    Then I validate Updated ETR Membership Information with SF
    Then I validate Updated ETR Membership Information with matrixDB