Feature: Existing Customer Auto Downgrade from Jade Tier
  @Scenario_ATP_Auto_Downgrade
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | ATP_Auto_Downgrade_Card_Number  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | ATP_Auto_Downgrade_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                  | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | POST           | ATP_Auto_Downgrade_Card_Number | Auto_Downgrade_TotalRecords1 | Auto_Downgrade_Start_timestamp1 | Auto_Downgrade_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                  |
      | ATP_Auto_Downgrade_Card_Number |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @Scenario_ATP_Auto_Downgrade
  Scenario: Validation of member on Salesforce and Matrix DB then post a Jade transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                  | <TotalRecords>                | <ATPStartTimestamp>               | <ATPEndTimestamp>             |
      | POST           | ATP_Auto_Downgrade_Card_Number | Auto_Downgrade_TotalRecords2  | Auto_Downgrade_Start_timestamp2   | Auto_Downgrade_End_timestamp2 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | ATP_Auto_Downgrade_Card_Number      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Auto_Downgrade
  Scenario: Validation of member on Salesforce and Matrix DB then refund the Jade transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                  | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | Refund         | ATP_Auto_Downgrade_Card_Number | Auto_Downgrade_TotalRecords3 | Auto_Downgrade_Start_timestamp3 | Auto_Downgrade_End_timestamp3 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | ATP_Auto_Downgrade_Card_Number      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Auto_Downgrade
  Scenario: Validation of member downgrade and refunded Jade transaction on SF and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | ATP_Auto_Downgrade_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                  | <Scenario>     |
      | ATP_Auto_Downgrade_Card_Number | AutoDowngrade  |
    And I validate expected point information with Matrix DB
