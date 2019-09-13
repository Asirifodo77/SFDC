Feature: Auto Transaction Posting - Transaction with TC Payment
  @Scenario_ATP_TC_Payment
  Scenario: Auto Transaction Posting - Transaction with TC Payment (Any 1 Tier)
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard4   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | AutoTransactionPostingCard4    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>               | <TotalRecords>                            | <ATPStartTimestamp>                          | <ATPEndTimestamp>                          |
      | POST           | AutoTransactionPostingCard4 | AutoTransactionPostingCard4_TotalRecords1 | AutoTransactionPostingCard4_Start_timestamp1 | AutoTransactionPostingCard4_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                    |
      | AutoTransactionPostingCard4      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_TC_Payment
  Scenario: Auto Transaction Posting - Transaction with TC Payment (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard4      |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                |
      | AutoTransactionPostingCard4  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                |
      | AutoTransactionPostingCard4  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | AutoTransactionPostingCard4   |
    And I validate expected point information with Matrix DB

  @Scenario_ATP_TC_Payment
  Scenario: Auto Transaction Posting - Transaction with TC Payment (Any 1 Tier)
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                    |
      | AutoTransactionPostingCard4      |
    And I read the payments details
    And I read the payments details in Matrix DB
      | <Transaction_Number>                            |
      | AutoTransactionPostingCard4_TransactionID1      |
    And I validate "Amount In Foreign Currency" is "9,507,250.80" in SF UI Purchase History
    And I validate "Amount In Local Currency" is "9,507,250.80" in SF UI Purchase History
    And I validate "Code" is "1" in SF UI Purchase History
    And I validate "CreditCardNumber" is "0" in SF UI Purchase History
    And I validate "Description" is "CASH" in SF UI Purchase History
    And I validate "ForeignCurrencyDescription" is "JPY" in SF UI Purchase History
    And I validate "Type" is "T" in SF UI Purchase History
    And I validate transaction "Amount In Foreign Currency" is "9,507,250.80" in Matrix DB
    And I validate transaction "Amount In Local Currency" is "9,507,250.80" in Matrix DB
    And I validate transaction "Code" is "1" in Matrix DB
    And I validate transaction "CreditCardNumber" is "0" in Matrix DB
    And I validate transaction "Description" is "CASH" in Matrix DB
    And I validate transaction "ForeignCurrencyDescription" is "JPY" in Matrix DB
    And I validate transaction "Type" is "T" in Matrix DB

