Feature: Edit member
  @positiveScenario
  Scenario: Creation of member
    Given I go to the login page
    And I am on SFDC login page
    When I login to SFDC
      | <UserName>   | <Password> |
      | SFDCAdmin_UserName   | SFDCAdmin_Pwd    |
    And Edit a current member
      | <PhoneNumber> | <StaffName> | <DepartmentName> |
      | Phone_num  | Staff_name  | Dept_name |