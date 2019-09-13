Feature: Member manual upgrade in Salesforce
  @Scenario_member_manual_upgrade
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for manual upgrade.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                   | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberManualUpgradeCardNumber    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                     | <TotalRecords>                      | <ATPStartTimestamp>                    | <ATPEndTimestamp>                    |
      | POST           | MemberManualUpgradeCardNumber | Member_manual_Upgrade_TotalRecords1 | Member_manual_Upgrade_Start_timestamp1 | Member_manual_Upgrade_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_manual_upgrade
  Scenario: Validation of member transaction on Salesforce and Matrix DB.
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                  |
      | MemberManualUpgradeCardNumber  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                  |
      | MemberManualUpgradeCardNumber  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | MemberManualUpgradeCardNumber   |
    And I validate expected point information with Matrix DB

  @Scenario_member_manual_upgrade
  Scenario: Validation of member and refunded transaction on Salesforce and Matrix DB then perform a manual upgrade
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    When I manual upgrade member from salesforce UI
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    Then I validate manual tier "upgrade" message
    When I approve all member "Manual Upgrade Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request

  @Scenario_member_manual_upgrade
  Scenario: Validation of member and Matrix DB after the manual upgrade
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I read the membership Information after manual upgrade
      | <Card_Number>                      |
      | MemberManualUpgradeCardNumber      |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                  |
      | MemberManualUpgradeCardNumber  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                  |
      | MemberManualUpgradeCardNumber  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   | <Scenario>        |
      | MemberManualUpgradeCardNumber   | ManualUpgrade     |
    And I validate expected point information with Matrix DB


  @Scenario_member_manual_upgrade_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification for the all the tiers to validate manual upgrade.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                          |
      | <Member_manual_Upgrade_Card_Number>    |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Member_manual_Upgrade_Card_Number>  | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                       | <TotalRecords>                        | <ATPStartTimestamp>                      | <ATPEndTimestamp>                      |
      | POST           | <Member_manual_Upgrade_Card_Number> | <Member_manual_Upgrade_TotalRecords1> | <Member_manual_Upgrade_Start_timestamp1> | <Member_manual_Upgrade_End_timestamp1> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                         |
      | <Member_manual_Upgrade_Card_Number>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                         |
      | <Member_manual_Upgrade_Card_Number>   |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                       |
      | <Member_manual_Upgrade_Card_Number> |
    And I validate expected point information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                       |
      | <Member_manual_Upgrade_Card_Number> |
    When I manual upgrade member from salesforce UI
      | <Card_Number>                       |
      | <Member_manual_Upgrade_Card_Number> |
    Then I validate manual tier "upgrade" message
    When I approve all member "Manual Upgrade Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | <Member_manual_Upgrade_Card_Number> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I read the membership Information after manual upgrade
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                        |
      | <Member_manual_Upgrade_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                       | <Scenario>        |
      | <Member_manual_Upgrade_Card_Number> | ManualUpgrade     |
    And I validate expected point information with Matrix DB

    Examples:
      | Member_manual_Upgrade_Card_Number             | Member_manual_Upgrade_Start_timestamp1                 | Member_manual_Upgrade_End_timestamp1                 | Member_manual_Upgrade_TotalRecords1                 |
      | MemberManualUpgradeCardNumber_LoyalTTOJade    | Member_manual_Upgrade_Start_timestamp1_LoyalTTOJade    | Member_manual_Upgrade_End_timestamp1_LoyalTTOJade    | Member_manual_Upgrade_TotalRecords1_LoyalTTOJade    |
      | MemberManualUpgradeCardNumber_LoyalTTORuby    | Member_manual_Upgrade_Start_timestamp1_LoyalTTORuby    | Member_manual_Upgrade_End_timestamp1_LoyalTTORuby    | Member_manual_Upgrade_TotalRecords1_LoyalTTORuby    |
      | MemberManualUpgradeCardNumber_LoyalTTODiamond | Member_manual_Upgrade_Start_timestamp1_LoyalTTODiamond | Member_manual_Upgrade_End_timestamp1_LoyalTTODiamond | Member_manual_Upgrade_TotalRecords1_LoyalTTODiamond |
      | MemberManualUpgradeCardNumber_JadeTORuby      | Member_manual_Upgrade_Start_timestamp1_JadeTORuby      | Member_manual_Upgrade_End_timestamp1_JadeTORuby      | Member_manual_Upgrade_TotalRecords1_JadeTORuby      |
      | MemberManualUpgradeCardNumber_JadeTODiamond   | Member_manual_Upgrade_Start_timestamp1_JadeTODiamond   | Member_manual_Upgrade_End_timestamp1_JadeTODiamond   | Member_manual_Upgrade_TotalRecords1_JadeTODiamond   |
      | MemberManualUpgradeCardNumber_RubyTODiamond   | Member_manual_Upgrade_Start_timestamp1_RubyTODiamond   | Member_manual_Upgrade_End_timestamp1_RubyTODiamond   | Member_manual_Upgrade_TotalRecords1_RubyTODiamond   |
