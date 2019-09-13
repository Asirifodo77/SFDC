Feature: Cobrand Indicator
  @CB_create_and_validate_indicator
  Scenario: Create Cobrand member and Verify cobrand indicator in SF and Matrix
    Given I read Create Cobrand member request body from json file
    Given I create Cobrand member using API call
    Then I login to Salesforce
    Then I search member using first name and last name
    Then I read Cobrand Indicator status in Salesforce
    Then I read first name and last name in Salesforce
    Then I read card number from Salesfroce
    Then I validate first name and last name with API request
    Then I validate if cobrand indicator checkbox is checked
    Then I validate cobrand indicator value in matrix
    Then I validate Non purchase checkbox value in Dev console
    Then I validate Movement type and Remarks values in Salesforce
    Then I validate Movement type and Remarks values in Matrix
    Then I cleanup data of created Cobrand member from SF and MatrixDB