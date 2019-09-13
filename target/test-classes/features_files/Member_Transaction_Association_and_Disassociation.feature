Feature: Member Transaction Association and Disassociation in Salesforce
  @Scenario_member_transaction_association_and_disassociation
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and then associate a transaction to member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                                            | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Transaction_Association_Disassociation_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I Associate transaction with Card number
      | <Transaction_Number>                                          | <Division>                                            |<Transaction_Date>                                            |
      | Member_Transaction_Association_Disassociation_TransactionID   | Member_Transaction_Association_Disassociation_Division| Member_Transaction_Association_Disassociation_TransactionDate|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I read the membership Information after transaction association
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I validate expected point information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I Disassociate transaction with Card number
      | <Transaction_Number>                                          | <Division>                                            |<Transaction_Date>                                            |
      | Member_Transaction_Association_Disassociation_TransactionID   | Member_Transaction_Association_Disassociation_Division| Member_Transaction_Association_Disassociation_TransactionDate|
    And Transaction DisAssociation message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I read the membership Information after transaction disassociation
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                                              |
      | Member_Transaction_Association_Disassociation_Card_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                                               |
      | Member_Transaction_Association_Disassociation_Card_Number   |
    And I validate expected point information after transaction disAssociation

  @Scenario_member_transaction_association_and_disassociation_All
  Scenario Outline: Member Transaction Association All
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>          |
      | <Member_Card_Number>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Member_Card_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>         |
      | <Member_Card_Number>  |
    And I Associate transaction with Card number
      | <Transaction_Number>     | <Division>                                            |<Transaction_Date>                                            |
      | <Member_TransactionID>   | Member_Transaction_Association_Disassociation_Division| Member_Transaction_Association_Disassociation_TransactionDate|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I read the membership Information after transaction association
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I validate expected point information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I Disassociate transaction with Card number
      | <Transaction_Number>     | <Division>                                            |<Transaction_Date>                                            |
      | <Member_TransactionID>   | Member_Transaction_Association_Disassociation_Division| Member_Transaction_Association_Disassociation_TransactionDate|
    And Transaction DisAssociation message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I read the membership Information after transaction disassociation
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I query Matrix DB Main table for membership information
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                                              |
      | <Member_Card_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                                               |
      | <Member_Card_Number>   |
    And I validate expected point information after transaction disAssociation


  Examples:
    |Member_Card_Number                                               |Member_TransactionID                                               |
    |Member_Transaction_Association_Disassociation_Card_Number_LoyalT |Member_Transaction_Association_Disassociation_TransactionID_LoyalT |
    |Member_Transaction_Association_Disassociation_Card_Number_Jade   |Member_Transaction_Association_Disassociation_TransactionID_Jade   |
    |Member_Transaction_Association_Disassociation_Card_Number_Ruby   |Member_Transaction_Association_Disassociation_TransactionID_Ruby   |
    |Member_Transaction_Association_Disassociation_Card_Number_Diamond|Member_Transaction_Association_Disassociation_TransactionID_Diamond|