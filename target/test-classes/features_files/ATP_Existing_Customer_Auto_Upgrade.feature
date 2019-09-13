Feature: Existing Customer Auto Upgrade
  @scenario_ATP_Existing_Customer_Auto_Upgrade
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>               | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | ATP_Auto_Upgrade_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>             | <ATPStartTimestamp>           | <ATPEndTimestamp>           |
      | POST           | ATP_Auto_Upgrade_Card_Number | Auto_Upgrade_TotalRecords1 | Auto_Upgrade_Start_timestamp1 | Auto_Upgrade_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                |
      | ATP_Auto_Upgrade_Card_Number |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @scenario_ATP_Existing_Customer_Auto_Upgrade
  Scenario: Validation of member on Salesforce and Matrix DB then POST a transaction to upgrade member
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>             | <ATPStartTimestamp>           | <ATPEndTimestamp>           |
      | POST           | ATP_Auto_Upgrade_Card_Number | Auto_Upgrade_TotalRecords2 | Auto_Upgrade_Start_timestamp2 | Auto_Upgrade_End_timestamp2 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | ATP_Auto_Upgrade_Card_Number        |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @scenario_ATP_Existing_Customer_Auto_Upgrade
  Scenario: Validation of member upgrade on Salesforce and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | ATP_Auto_Upgrade_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 | <Scenario>  |
      | ATP_Auto_Upgrade_Card_Number  | Upgrade     |
    And I validate expected point information with Matrix DB


  @scenario_ATP_Existing_Customer_Auto_Upgrade_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member auto upgrade for all tiers
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <ATP_Auto_Upgrade_Card_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                  | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | POST           | <ATP_Auto_Upgrade_Card_Number> | <Auto_Upgrade_TotalRecords1> | <Auto_Upgrade_Start_timestamp1> | <Auto_Upgrade_End_timestamp1> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                  |
      | <ATP_Auto_Upgrade_Card_Number> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                  | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | POST           | <ATP_Auto_Upgrade_Card_Number> | <Auto_Upgrade_TotalRecords2> | <Auto_Upgrade_Start_timestamp2> | <Auto_Upgrade_End_timestamp2> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | <ATP_Auto_Upgrade_Card_Number>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | <ATP_Auto_Upgrade_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   | <Scenario>  |
      | <ATP_Auto_Upgrade_Card_Number>  | Upgrade     |
    And I validate expected point information with Matrix DB

    Examples:
      | ATP_Auto_Upgrade_Card_Number                 | Auto_Upgrade_TotalRecords1                 | Auto_Upgrade_Start_timestamp1                 | Auto_Upgrade_End_timestamp1                 | Auto_Upgrade_TotalRecords2                 | Auto_Upgrade_Start_timestamp2                 | Auto_Upgrade_End_timestamp2                 |
      | ATP_Auto_Upgrade_Card_Number_LoyalTTOJade    | Auto_Upgrade_TotalRecords1_LoyalTTOJade    | Auto_Upgrade_Start_timestamp1_LoyalTTOJade    | Auto_Upgrade_End_timestamp1_LoyalTTOJade    | Auto_Upgrade_TotalRecords2_LoyalTTOJade    | Auto_Upgrade_Start_timestamp2_LoyalTTOJade    | Auto_Upgrade_End_timestamp2_LoyalTTOJade    |
      | ATP_Auto_Upgrade_Card_Number_LoyalTTORuby    | Auto_Upgrade_TotalRecords1_LoyalTTORuby    | Auto_Upgrade_Start_timestamp1_LoyalTTORuby    | Auto_Upgrade_End_timestamp1_LoyalTTORuby    | Auto_Upgrade_TotalRecords2_LoyalTTORuby    | Auto_Upgrade_Start_timestamp2_LoyalTTORuby    | Auto_Upgrade_End_timestamp2_LoyalTTORuby    |
      | ATP_Auto_Upgrade_Card_Number_LoyalTTODiamond | Auto_Upgrade_TotalRecords1_LoyalTTODiamond | Auto_Upgrade_Start_timestamp1_LoyalTTODiamond | Auto_Upgrade_End_timestamp1_LoyalTTODiamond | Auto_Upgrade_TotalRecords2_LoyalTTODiamond | Auto_Upgrade_Start_timestamp2_LoyalTTODiamond | Auto_Upgrade_End_timestamp2_LoyalTTODiamond |
      | ATP_Auto_Upgrade_Card_Number_JadeTORuby      | Auto_Upgrade_TotalRecords1_JadeTORuby      | Auto_Upgrade_Start_timestamp1_JadeTORuby      | Auto_Upgrade_End_timestamp1_JadeTORuby      | Auto_Upgrade_TotalRecords2_JadeTORuby      | Auto_Upgrade_Start_timestamp2_JadeTORuby      | Auto_Upgrade_End_timestamp2_JadeTORuby      |
      | ATP_Auto_Upgrade_Card_Number_JadeTODiamond   | Auto_Upgrade_TotalRecords1_JadeTODiamond   | Auto_Upgrade_Start_timestamp1_JadeTODiamond   | Auto_Upgrade_End_timestamp1_JadeTODiamond   | Auto_Upgrade_TotalRecords2_JadeTODiamond   | Auto_Upgrade_Start_timestamp2_JadeTODiamond   | Auto_Upgrade_End_timestamp2_JadeTODiamond   |
      | ATP_Auto_Upgrade_Card_Number_RubyTODiamond   | Auto_Upgrade_TotalRecords1_RubyTODiamond   | Auto_Upgrade_Start_timestamp1_RubyTODiamond   | Auto_Upgrade_End_timestamp1_RubyTODiamond   | Auto_Upgrade_TotalRecords2_RubyTODiamond   | Auto_Upgrade_Start_timestamp2_RubyTODiamond   | Auto_Upgrade_End_timestamp2_RubyTODiamond   |