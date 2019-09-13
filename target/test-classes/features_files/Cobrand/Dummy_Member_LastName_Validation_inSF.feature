Feature: Dummy member Last Name verification in SF
  @Validate_Dummy_Member_LastName_SF
  Scenario: posting transaction notification to create a Dummy Member for SF
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | Dummy_Card_LastNameTest1_LoyalT |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                   | <TotalRecords>                               | <ATPStartTimestamp>                             | <ATPEndTimestamp>                             |
      | POST           | Dummy_Card_LastNameTest1_LoyalT | Dummy_Card_LastNameTest1_TotalRecords_LoyalT | Dummy_Card_LastNameTest1_Start_timestamp_LoyalT | Dummy_Card_LastNameTest1_End_timestamp_LoyalT |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | Dummy_Card_LastNameTest1_LoyalT  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Validate_Dummy_Member_LastName_SF
  Scenario: validate dummy members last name in SF
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | Dummy_Card_LastNameTest1_LoyalT  |
    Then I read the last name of the member in SF
    Then I validate if last name of the member is equal to "*"
