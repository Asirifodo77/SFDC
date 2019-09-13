Feature: POS Create Member
  @POS_Create_Member
  Scenario: Validate the creation of a new member through POS
    Given I read POS request body from Json file
    Given I cleanup data for existing POS member from SF and MatrixDB
    When I send POST request to create new member through POS
    Then I validate the memberID in salesforce and Matrix for POS Created member
    And I login to SF to verify POS member
    Then I search for the member using POS json Card Number
      | <Card_Number>|
      | Card_Number  |
    Then I read details from POS member profile in SF
    Then I search for missing POS member values in database
    Then I read POS member details from Matrix
    Then I validate POS Membership Information with SF
    Then I validate POS Membership Information with matrixDB