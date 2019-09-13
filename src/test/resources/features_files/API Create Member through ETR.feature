Feature: Create Member Using API - ETR
  @Scenario_Validate_Member_Create_ETR
  Scenario: Validate Created Member In Sales Force and Matrix

    Given I Send POST Request and Receive Response
    Given I login to SFDC

    And I search for the member through Card Number
      | <Card_Number>|
      | API_ETR_Create_Member_Card_Number  |
    And I read the Profile Information from Sales force UI
    And Read Json file
    And I query
    And I Validate Request Payload with Member Profile Information
#    And I Validate Request Payload with Matrix
  # testing dev branch
