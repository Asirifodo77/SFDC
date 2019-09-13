Feature: Dummy member Last Name verification in POS
@Validate_Dummy_Member_LastName_POS
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

@Validate_Dummy_Member_LastName_POS
Scenario: validate dummy members last name in POS
Given I send the POST request to get Member "Dummy_Card_LastNameTest2_LoyalT" in POS
Then I validate if last name of the member is a Blank