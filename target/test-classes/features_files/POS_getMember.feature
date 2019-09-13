Feature: POS get Member
  @POS_getMember
  Scenario: get POS member
    Given I read POS getMember POST request body from the Json file
    Then I send the POST request to get Member in POS
    Then I validate if POS getMember response is valid
    Then I validate if POS member is Dummy
    Then I read the details in POS get member response
    Then I login to SF to get POS member;
    Then I search POS get member using card number
      | <Card_Number>|
      | Card_Number  |
    Then I read POS member values from SF
    Then I validate POS getMember details with SF