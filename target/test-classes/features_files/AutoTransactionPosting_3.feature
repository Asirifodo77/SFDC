Feature: Auto Transaction Posting - Transaction with Multiple SKUs
  @Scenario_ATP_Multiple_SKUs
  Scenario: Auto Transaction Posting - Transaction with Multiple SKUs (Any 1 Tier)
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard3   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | AutoTransactionPostingCard3    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>               | <TotalRecords>                            | <ATPStartTimestamp>                          | <ATPEndTimestamp>                          |
      | POST           | AutoTransactionPostingCard3 | AutoTransactionPostingCard3_TotalRecords1 | AutoTransactionPostingCard3_Start_timestamp1 | AutoTransactionPostingCard3_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | AutoTransactionPostingCard3      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Multiple_SKUs
  Scenario: Auto Transaction Posting - Transaction with Multiple SKUs (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard3      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                |
      | AutoTransactionPostingCard3  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                |
      | AutoTransactionPostingCard3  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard3   |
    And I validate expected point information with Matrix DB

  @Scenario_ATP_Multiple_SKUs
  Scenario: Auto Transaction Posting - Transaction with Multiple SKUs (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard3      |
    And I read the SKU Details
    And I validate "Product Name" is "1000125" in SF UI Purchase History
    And I validate "Units" is "1" in SF UI Purchase History
    And I validate "Net Amount without Tax (Local)" is "4,753,625.40 JPY" in SF UI Purchase History
    And I validate "Product Name1" is "1000124" in SF UI Purchase History
    And I validate "Units1" is "1" in SF UI Purchase History
    And I validate "Net Amount without Tax (Local)1" is "4,753,625.40 JPY" in SF UI Purchase History
    And I read the SKU Details details in Matrix DB for skuNumber "1000124"
      | <Transaction_Number>                            | <SkuNumber>|
      | AutoTransactionPostingCard3_TransactionID1      | 1000124    |
    And I validate transaction "Product Name" is "1000124" in Matrix DB
    And I validate transaction "Units" is "1" in Matrix DB
    And I validate transaction "Net Amount without Tax (Local)" is "4753625.4000" in Matrix DB
    And I read the SKU Details details in Matrix DB for skuNumber "1000125"
      | <Transaction_Number>                            | <SkuNumber>|
      | AutoTransactionPostingCard3_TransactionID1      | 1000125    |
    And I validate transaction "Product Name" is "1000125" in Matrix DB
    And I validate transaction "Units" is "1" in Matrix DB
    And I validate transaction "Net Amount without Tax (Local)" is "4753625.4000" in Matrix DB