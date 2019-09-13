Feature: Auto Transaction Posting - Transaction with Tax Amount
  @Scenario_ATP_Tax_amount
  Scenario: Auto Transaction Posting - Transaction with Tax Amount (Any 1 Tier)
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard2   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | AutoTransactionPostingCard2    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>               | <TotalRecords>                            | <ATPStartTimestamp>                          | <ATPEndTimestamp>                          |
      | POST           | AutoTransactionPostingCard2 | AutoTransactionPostingCard2_TotalRecords1 | AutoTransactionPostingCard2_Start_timestamp1 | AutoTransactionPostingCard2_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | AutoTransactionPostingCard2      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Tax_amount
  Scenario: Auto Transaction Posting - Transaction with Tax Amount (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard2      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                |
      | AutoTransactionPostingCard2  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                |
      | AutoTransactionPostingCard2  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard2   |
    And I validate expected point information with Matrix DB

  @Scenario_ATP_Tax_amount
  Scenario: Auto Transaction Posting - Transaction with Tax Amount (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard2      |
    And I read the Transaction Details
    And I read the Transaction Details in Matrix DB
      | <Transaction_Number>                            |
      | AutoTransactionPostingCard2_TransactionID1      |
    And I validate "TaxAmountLocal" is "1,677,790.00 JPY" in SF UI Purchase History
    And I validate transaction "TaxAmountLocal" is "1677790.00" in Matrix DB

