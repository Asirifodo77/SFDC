Feature: Transaction Disassociation of Earn only Supplementary card transaction in Salesforce.
  @Scenario_supplementaryCard_transactions_association_disassociation
  Scenario: Transaction Disassociation of Earn only Supplementary card transaction in Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I Associate transaction with Card number
      | <Transaction_Number>                                        | <Division>                                             |<Transaction_Date>                                                        |
      | supplementaryCard_transactions_association_TransactionID1   | Member_Transaction_Association_Disassociation_Division | supplementaryCard_transactions_association_Supplementary_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | supplementaryCard_transactions_association_card   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_supplementaryCard_transactions_association_disassociation
  Scenario: Transaction Disassociation of Earn only Supplementary card transaction in Salesforce posting transaction to the supplementary card
    Given I login to Salesforce
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                                                 | <TotalRecords>                                                       | <ATPStartTimestamp>                                                     | <ATPEndTimestamp>                                                     |
      | POST           | supplementaryCard_transactions_association_supplementary_card | supplementaryCard_transactions_association_SupplementaryTotalRecords | supplementaryCard_transactions_association_SupplementaryStart_timestamp | supplementaryCard_transactions_association_SupplementaryEnd_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                                      |
      | supplementaryCard_transactions_association_supplementary_card      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request

  @Scenario_supplementaryCard_transactions_association_disassociation
  Scenario: Transaction Disassociation of Earn only Supplementary card transaction in Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I Disassociate transaction with Card number
      | <Transaction_Number>                                          | <Division>                                            |<Transaction_Date>                                                        |
      | supplementaryCard_transactions_association_TransactionID1     | Member_Transaction_Association_Disassociation_Division| supplementaryCard_transactions_association_Supplementary_TransactionDate2|
    And Transaction DisAssociation message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                     |
      | supplementaryCard_transactions_association_card   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request

  @Scenario_supplementaryCard_transactions_association_disassociation
  Scenario: Transaction Disassociation of Earn only Supplementary card transaction in Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                                    |
      | supplementaryCard_transactions_association_card  |
    And I validate expected point information with Matrix DB