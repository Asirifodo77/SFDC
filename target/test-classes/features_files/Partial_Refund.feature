Feature: Existing Customer Partial Refund validation
  @Scenario_member_partial_refund
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for partial refund
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>             | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Partial_Refund_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>              | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | POST           | Partial_Refund_Card_Number | Partial_Refund_TotalRecords1 | Partial_Refund_Start_timestamp1 | Partial_Refund_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_partial_refund
  Scenario: Validation of member Salesforce and Matrix DB then post a partial refund transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>              | <TotalRecords>               | <ATPStartTimestamp>             | <ATPEndTimestamp>             |
      | Refund         | Partial_Refund_Card_Number | Partial_Refund_TotalRecords2 | Partial_Refund_Start_timestamp2 | Partial_Refund_End_timestamp2 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request

  @Scenario_member_partial_refund
  Scenario: Validation of member and partial refunded transaction on Salesforce and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>               |
      | Partial_Refund_Card_Number  |
    And I validate expected point information with Matrix DB


  @Scenario_member_partial_refund_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for partial refund validation for all members.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>               | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Partial_Refund_Card_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>                 | <ATPStartTimestamp>               | <ATPEndTimestamp>               |
      | POST           | <Partial_Refund_Card_Number> | <Partial_Refund_TotalRecords1> | <Partial_Refund_Start_timestamp1> | <Partial_Refund_End_timestamp1> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>                 | <ATPStartTimestamp>               | <ATPEndTimestamp>               |
      | Refund         | <Partial_Refund_Card_Number> | <Partial_Refund_TotalRecords2> | <Partial_Refund_Start_timestamp2> | <Partial_Refund_End_timestamp2> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | <Partial_Refund_Card_Number>  |
    And I validate expected point information with Matrix DB

    Examples:
      | Partial_Refund_Card_Number                 | Partial_Refund_TotalRecords1                 | Partial_Refund_Start_timestamp1                 | Partial_Refund_End_timestamp1                 | Partial_Refund_TotalRecords2                 | Partial_Refund_Start_timestamp2                 | Partial_Refund_End_timestamp2                 |
      | Partial_Refund_Card_Number_DiamondTOJade   | Partial_Refund_TotalRecords1_DiamondTOJade   | Partial_Refund_Start_timestamp1_DiamondTOJade   | Partial_Refund_End_timestamp1_DiamondTOJade   | Partial_Refund_TotalRecords2_DiamondTOJade   | Partial_Refund_Start_timestamp2_DiamondTOJade   | Partial_Refund_End_timestamp2_DiamondTOJade   |
      | Partial_Refund_Card_Number_DiamondTORuby   | Partial_Refund_TotalRecords1_DiamondTORuby   | Partial_Refund_Start_timestamp1_DiamondTORuby   | Partial_Refund_End_timestamp1_DiamondTORuby   | Partial_Refund_TotalRecords2_DiamondTORuby   | Partial_Refund_Start_timestamp2_DiamondTORuby   | Partial_Refund_End_timestamp2_DiamondTORuby   |
      | Partial_Refund_Card_Number_DiamondTOLoyalT | Partial_Refund_TotalRecords1_DiamondTOLoyalT | Partial_Refund_Start_timestamp1_DiamondTOLoyalT | Partial_Refund_End_timestamp1_DiamondTOLoyalT | Partial_Refund_TotalRecords2_DiamondTOLoyalT | Partial_Refund_Start_timestamp2_DiamondTOLoyalT | Partial_Refund_End_timestamp2_DiamondTOLoyalT |
      | Partial_Refund_Card_Number_RubyTOJade      | Partial_Refund_TotalRecords1_RubyTOJade      | Partial_Refund_Start_timestamp1_RubyTOJade      | Partial_Refund_End_timestamp1_RubyTOJade      | Partial_Refund_TotalRecords2_RubyTOJade      | Partial_Refund_Start_timestamp2_RubyTOJade      | Partial_Refund_End_timestamp2_RubyTOJade      |
      | Partial_Refund_Card_Number_RubyTOLoyalT    | Partial_Refund_TotalRecords1_RubyTOLoyalT    | Partial_Refund_Start_timestamp1_RubyTOLoyalT    | Partial_Refund_End_timestamp1_RubyTOLoyalT    | Partial_Refund_TotalRecords2_RubyTOLoyalT    | Partial_Refund_Start_timestamp2_RubyTOLoyalT    | Partial_Refund_End_timestamp2_RubyTOLoyalT    |


