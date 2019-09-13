Feature: POS Search Member
  @POS_Search_Member
  Scenario: Validate POS search member data in SF and MatrixDB
    Given I read POS search member request body from the Json file
    Given I send the POST request to search member in POS
    Then I validate if POS search member response is valid
    Then I read member details in the response
    Then I read JoinDate value from salesforce
    Then I validate if POS search member is Dummy
    Then I login to SF to search POS member
    Then I search the member using card number
      | <Card_Number>|
      | Card_Number  |
    Then I read the member details in SF
    Then I validate POS search member details with SF
