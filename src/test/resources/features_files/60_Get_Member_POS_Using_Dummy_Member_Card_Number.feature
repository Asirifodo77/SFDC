Feature: Get Member POS using Dummy member card Number
  @Scenario_Get_Member_POS__Dummy_Profile_Membership_Card_Number
  Scenario: posting transaction notification to create a Dummy Member for POS
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | Dummy_Card_LastNameTest2_LoyalT |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                   | <TotalRecords>                               | <ATPStartTimestamp>                             | <ATPEndTimestamp>                             |
      | POST           | Dummy_Card_LastNameTest2_LoyalT | Dummy_Card_LastNameTest2_TotalRecords_LoyalT | Dummy_Card_LastNameTest2_Start_timestamp_LoyalT | Dummy_Card_LastNameTest2_End_timestamp_LoyalT |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | Dummy_Card_LastNameTest2_LoyalT  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_Get_Member_POS__Dummy_Profile_Membership_Card_Number
  Scenario: (60) I get POS get member details using dummy member card number
    Given I send a POST request to POS get member for card number "Dummy_Card_LastNameTest2_LoyalT"
    Then  I read the details in POS get member response for card number "Dummy_Card_LastNameTest2_LoyalT"
    Then I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                         |
      | Dummy_Card_LastNameTest2_LoyalT       |
    Then I read POS member values from Salesfroce for card number "Dummy_Card_LastNameTest2_LoyalT"
    Then I validate POS get Member values with Salesforce