Feature: New Customer Sale Validation - Dummy Member
  @Scenario_Validate_Dummy_Member_in_Sales_Force
  Scenario: Delete member on Salesforce and Matrix DB before posting transaction notification to create a Dummy Member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>     |
      | Dummy_Card_Number |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>     | <TotalRecords>     | <ATPStartTimestamp>   | <ATPEndTimestamp>   |
      | POST           | Dummy_Card_Number | Dummy_TotalRecords | Dummy_Start_timestamp | Dummy_End_timestamp |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>      |
      | Dummy_Card_Number  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_Validate_Dummy_Member_in_Sales_Force
  Scenario: Validation of member in Salesforce and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>      |
      | Dummy_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>      |
      | Dummy_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>      |
      | Dummy_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>      |
      | Dummy_Card_Number  |
    And I validate expected point information with Matrix DB


  @Scenario_Validate_Dummy_Member_in_Sales_Force_All
  Scenario Outline: Post transaction to create Dummy Member all
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>       | <TotalRecords>       | <ATPStartTimestamp>     | <ATPEndTimestamp>     |
      | POST           | <Dummy_Card_Number> | <Dummy_TotalRecords> | <Dummy_Start_timestamp> | <Dummy_End_timestamp> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>        |
      | <Dummy_Card_Number>  |
    And I validate expected point information with Matrix DB

    Examples:
      | Dummy_Card_Number         | Dummy_TotalRecords         | Dummy_Start_timestamp         | Dummy_End_timestamp         |
      | Dummy_Card_Number_LoyalT  | Dummy_TotalRecords_LoyalT  | Dummy_Start_timestamp_LoyalT  | Dummy_End_timestamp_LoyalT  |
      | Dummy_Card_Number_Jade    | Dummy_TotalRecords_Jade    | Dummy_Start_timestamp_Jade    | Dummy_End_timestamp_Jade    |
      | Dummy_Card_Number_Ruby    | Dummy_TotalRecords_Ruby    | Dummy_Start_timestamp_Ruby    | Dummy_End_timestamp_Ruby    |
      | Dummy_Card_Number_Diamond | Dummy_TotalRecords_Diamond | Dummy_Start_timestamp_Diamond | Dummy_End_timestamp_Diamond |