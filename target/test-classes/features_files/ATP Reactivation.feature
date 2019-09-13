Feature: Validate Reactivation of LoyalT Member with new transaction after member cycle ends
  @Scenario_ATP_Reactivation
  Scenario: Delete member on Salesforce and Matrix DB before posting transaction notification to create a Dummy Member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    Given I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>             | <ATPStartTimestamp>           | <ATPEndTimestamp>           |
      | POST           | ATP_Reactivation_Card_Number | Reactivation_TotalRecords1 | Reactivation_Start_timestamp1 | Reactivation_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                |
      | ATP_Reactivation_Card_Number |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Reactivation
  Scenario: Validation of member in Salesforce and Matrix DB before running renewal job and posting a reactivation transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I validate expected point information with Matrix DB
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I run renewal job on SF
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I validate the membership status is "Inactive" after running renewal job
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>             | <ATPStartTimestamp>           | <ATPEndTimestamp>           |
      | POST           | ATP_Reactivation_Card_Number | Reactivation_TotalRecords2 | Reactivation_Start_timestamp2 | Reactivation_End_timestamp2 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                |
      | ATP_Reactivation_Card_Number |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Reactivation
  Scenario: Validation of member reactivation on Salesforce and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I read the membership Information and Purchase history details from UI and validate it against reactivation logic
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | ATP_Reactivation_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 | <Scenario>   |
      | ATP_Reactivation_Card_Number  | Reactivation |
    And I validate expected point information with Matrix DB
