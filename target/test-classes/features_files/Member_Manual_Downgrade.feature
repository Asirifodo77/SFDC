Feature: Member Manual Downgrade in Salesforce
  @Scenario_member_manual_downgrade
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for manual downgrade.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                            | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Card_Management_ManualDowngrade    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                          | <TotalRecords>                | <ATPStartTimestamp>              | <ATPEndTimestamp>              |
      | POST           | Member_Card_Management_ManualDowngrade | ManualDowngrade_TotalRecords1 | ManualDowngrade_Start_timestamp1 | ManualDowngrade_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @Scenario_member_manual_downgrade
  Scenario: Validation of member Salesforce and Matrix DB then post a refund transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                            |
      | Member_Card_Management_ManualDowngrade   |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                          | <TotalRecords>                | <ATPStartTimestamp>     | <ATPEndTimestamp>     |
      | Refund         | Member_Card_Management_ManualDowngrade | ManualDowngrade_TotalRecords2 | ManualDowngrade_Start_timestamp2 | ManualDowngrade_End_timestamp2 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request


  @Scenario_member_manual_downgrade
  Scenario: Validation of member and refunded transaction on Salesforce and Matrix DB then perform a manual downgrade
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                            |
      | Member_Card_Management_ManualDowngrade   |
    And I validate expected point information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I manual downgrade member from salesforce UI
    Then I validate the membership tier
    Then I validate manual tier "downgrade" message
    When I approve all member "Manual Downgrade Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @Scenario_member_manual_downgrade
  Scenario: Validation of member and Matrix DB after the manual downgrade
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | Member_Card_Management_ManualDowngrade      |
    And I read the membership Information after manual downgrade from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                           |
      | Member_Card_Management_ManualDowngrade  |
    And I validate expected membership information after manual downgrade with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                            |
      | Member_Card_Management_ManualDowngrade   |
    And I validate expected point information with Matrix DB


  @Scenario_member_manual_downgrade_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for manual downgrade to all listed tiers.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                             | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Member_Card_Management_ManualDowngrade>   | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                            | <TotalRecords>                  | <ATPStartTimestamp>                | <ATPEndTimestamp>                |
      | POST           | <Member_Card_Management_ManualDowngrade> | <ManualDowngrade_TotalRecords1> | <ManualDowngrade_Start_timestamp1> | <ManualDowngrade_End_timestamp1> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                           |
      | <Member_Card_Management_ManualDowngrade>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                           |
      | <Member_Card_Management_ManualDowngrade>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                            |
      | <Member_Card_Management_ManualDowngrade>   |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                            | <TotalRecords>                  | <ATPStartTimestamp>                | <ATPEndTimestamp>                |
      | Refund         | <Member_Card_Management_ManualDowngrade> | <ManualDowngrade_TotalRecords2> | <ManualDowngrade_Start_timestamp2> | <ManualDowngrade_End_timestamp2> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                             |
      | <Member_Card_Management_ManualDowngrade>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                             |
      | <Member_Card_Management_ManualDowngrade>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                            |
      | <Member_Card_Management_ManualDowngrade> |
    And I validate expected point information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I manual downgrade member from salesforce UI
    Then I validate the membership tier
    Then I validate manual tier "downgrade" message
    When I approve all member "Manual Downgrade Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I read the membership Information after manual downgrade from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                             |
      | <Member_Card_Management_ManualDowngrade>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                             |
      | <Member_Card_Management_ManualDowngrade>  |
    And I validate expected membership information after manual downgrade with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                               |
      | <Member_Card_Management_ManualDowngrade>    |
    And I validate expected point information with Matrix DB

  Examples:
      | Member_Card_Management_ManualDowngrade                 | ManualDowngrade_TotalRecords1                 | ManualDowngrade_Start_timestamp1                 | ManualDowngrade_End_timestamp1                 | ManualDowngrade_TotalRecords2                 | ManualDowngrade_Start_timestamp2                 | ManualDowngrade_End_timestamp2                 |
      | Member_Card_Management_ManualDowngrade_DiamondTOJade   | ManualDowngrade_TotalRecords1_DiamondTOJade   | ManualDowngrade_Start_timestamp1_DiamondTOJade   | ManualDowngrade_End_timestamp1_DiamondTOJade   | ManualDowngrade_TotalRecords2_DiamondTOJade   | ManualDowngrade_Start_timestamp2_DiamondTOJade   | ManualDowngrade_End_timestamp2_DiamondTOJade   |
      | Member_Card_Management_ManualDowngrade_DiamondTORuby   | ManualDowngrade_TotalRecords1_DiamondTORuby   | ManualDowngrade_Start_timestamp1_DiamondTORuby   | ManualDowngrade_End_timestamp1_DiamondTORuby   | ManualDowngrade_TotalRecords2_DiamondTORuby   | ManualDowngrade_Start_timestamp2_DiamondTORuby   | ManualDowngrade_End_timestamp2_DiamondTORuby   |
      | Member_Card_Management_ManualDowngrade_RubyTOJade      | ManualDowngrade_TotalRecords1_RubyTOJade      | ManualDowngrade_Start_timestamp1_RubyTOJade      | ManualDowngrade_End_timestamp1_RubyTOJade      | ManualDowngrade_TotalRecords2_RubyTOJade      | ManualDowngrade_Start_timestamp2_RubyTOJade      | ManualDowngrade_End_timestamp2_RubyTOJade      |
      | Member_Card_Management_ManualDowngrade_RubyTOLoyalT    | ManualDowngrade_TotalRecords1_RubyTOLoyalT    | ManualDowngrade_Start_timestamp1_RubyTOLoyalT    | ManualDowngrade_End_timestamp1_RubyTOLoyalT    | ManualDowngrade_TotalRecords2_RubyTOLoyalT    | ManualDowngrade_Start_timestamp2_RubyTOLoyalT    | ManualDowngrade_End_timestamp2_RubyTOLoyalT    |
      | Member_Card_Management_ManualDowngrade_DiamondTOLoyalT | ManualDowngrade_TotalRecords1_DiamondTOLoyalT | ManualDowngrade_Start_timestamp1_DiamondTOLoyalT | ManualDowngrade_End_timestamp1_DiamondTOLoyalT | ManualDowngrade_TotalRecords2_DiamondTOLoyalT | ManualDowngrade_Start_timestamp2_DiamondTOLoyalT | ManualDowngrade_End_timestamp2_DiamondTOLoyalT |
