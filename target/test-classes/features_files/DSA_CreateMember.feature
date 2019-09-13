Feature: DSA Create Member
  @DSA_Create_Member
  Scenario: Validate the creation of a new member through DSA
    Given I read DSA request body from Json file
    Given I cleanup data for existing DSA member from SF and MatrixDB
    When I send POST request to create new member through DSA
    Then I validate the memberID in salesforce and Matrix for DSA Created member
    And I login to SF
    Then I search for the member using Card Number
      | <Card_Number>|
      | Card_Number  |
    Then I read details from member profile in SF
    Then I search for missing values in database
    Then I read member details from Matrix
    Then I validate DSA Membership Information with SF
    Then I validate DSA Membership Information with matrixDB

