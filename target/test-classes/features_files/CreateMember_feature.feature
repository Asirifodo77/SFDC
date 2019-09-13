Feature: Create member
  @positiveScenario
  Scenario: Creation of member

    Given I login to SFDC
      | <UserName>           | <Password>    |
      | SFDCAdmin_UserName   | SFDCAdmin_Pwd |
    And I create a new member
      | <FirstName> | <StaffNo> | <LastName> | <MemberNumber> | <Email> |
      | First_name  | Staff_no  | Last_name  | Member_Number  | Email   |
    Then I search for the member through Card Number
      | <Card_Number>|
      | Card_Number  |



