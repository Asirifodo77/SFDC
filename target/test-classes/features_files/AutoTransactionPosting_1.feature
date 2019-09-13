Feature: Auto Transaction Posting - Transaction with Beauty & Non Beauty products
  @Scenario_ATP_Beauty_and_Non_Beauty
  Scenario: Auto Transaction Posting - Transaction with Beauty & Non Beauty products (For All tiers)
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard1   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | AutoTransactionPostingCard1    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>               | <TotalRecords>                            | <ATPStartTimestamp>                          | <ATPEndTimestamp>                          |
      | POST           | AutoTransactionPostingCard1 | AutoTransactionPostingCard1_TotalRecords1 | AutoTransactionPostingCard1_Start_timestamp1 | AutoTransactionPostingCard1_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | AutoTransactionPostingCard1      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Beauty_and_Non_Beauty
  Scenario: Auto Transaction Posting - Transaction with Beauty & Non Beauty products (For All tiers)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard1      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                |
      | AutoTransactionPostingCard1  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                |
      | AutoTransactionPostingCard1  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard1   |
    And I validate expected point information with Matrix DB

  @Scenario_ATP_Beauty_and_Non_Beauty_All
  Scenario Outline: Auto Transaction Posting - Transaction with Beauty & Non Beauty products (For All tiers)
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | <AutoTransactionPostingCard1>   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                   | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <AutoTransactionPostingCard1>    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                 | <TotalRecords>                              | <ATPStartTimestamp>                            | <ATPEndTimestamp>                            |
      | POST           | <AutoTransactionPostingCard1> | <AutoTransactionPostingCard1_TotalRecords1> | <AutoTransactionPostingCard1_Start_timestamp1> | <AutoTransactionPostingCard1_End_timestamp1> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                      |
      | <AutoTransactionPostingCard1>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | <AutoTransactionPostingCard1>      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                  |
      | <AutoTransactionPostingCard1>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                  |
      | <AutoTransactionPostingCard1>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | <AutoTransactionPostingCard1>   |
    And I validate expected point information with Matrix DB

    Examples:
      | AutoTransactionPostingCard1         | AutoTransactionPostingCard1_TotalRecords1         | AutoTransactionPostingCard1_Start_timestamp1         | AutoTransactionPostingCard1_End_timestamp1         |
      | AutoTransactionPostingCard1_LoyalT  | AutoTransactionPostingCard1_TotalRecords1_LoyalT  | AutoTransactionPostingCard1_Start_timestamp1_LoyalT  | AutoTransactionPostingCard1_End_timestamp1_LoyalT  |
      | AutoTransactionPostingCard1_Jade    | AutoTransactionPostingCard1_TotalRecords1_Jade    | AutoTransactionPostingCard1_Start_timestamp1_Jade    | AutoTransactionPostingCard1_End_timestamp1_Jade    |
      | AutoTransactionPostingCard1_Ruby    | AutoTransactionPostingCard1_TotalRecords1_Ruby    | AutoTransactionPostingCard1_Start_timestamp1_Ruby    | AutoTransactionPostingCard1_End_timestamp1_Ruby    |
      | AutoTransactionPostingCard1_Diamond | AutoTransactionPostingCard1_TotalRecords1_Diamond | AutoTransactionPostingCard1_Start_timestamp1_Diamond | AutoTransactionPostingCard1_End_timestamp1_Diamond |