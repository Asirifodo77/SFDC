Feature: ETR get Member
  @ETR_getMember
  Scenario: get ETR member
    Given I read getMember POST request body from the Json file
    Then I send the POST request to get Member in ETR
    Then I validate if ETR getMember response is valid
    Then I read the details in get member response
    Then I validate if ETR Member is Dummy
    Then I login to SF to get ETR member;
    Then I search ETR get member using card number
      | <Card_Number>|
      | Card_Number  |
    Then I read ETR member values from SF
    Then I validate ETR getMember details with SF
