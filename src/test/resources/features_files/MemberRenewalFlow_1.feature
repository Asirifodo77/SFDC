Feature: Flow 1 - Member renewed due to Basic Renewal Criteria Met
  @Scenario_member_renewal_flow_1
  Scenario: Member renewed due to Basic Renewal Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow1   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow1 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | MemberRenewalCardFlow1  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow1_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow1_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow1   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                            | <Transaction_Date>                     |
      | MemberRenewalCardFlow1_TransactionID2   | Member_Transaction_Association_Disassociation_Division| MemberRenewalCardFlow1_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow1   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow1   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>|
      | Basic_Renewal     |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow1  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "RENEW" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_1_All
  Scenario Outline: Member renewed due to Basic Renewal Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow1>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow1> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow1_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow1_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow1>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                      | <Division>                                            | <Transaction_Date>                       |
      | <MemberRenewalCardFlow1_TransactionID2>   | Member_Transaction_Association_Disassociation_Division| <MemberRenewalCardFlow1_TransactionDate2>|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow1>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow1>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>|
      | Basic_Renewal     |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow1>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "RENEW" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow1          | MemberRenewalCardFlow1_TransactionID1         | MemberRenewalCardFlow1_TransactionDate1         | MemberRenewalCardFlow1_TransactionID2         | MemberRenewalCardFlow1_TransactionDate2         |
      | MemberRenewalCardFlow1_LoyalT   | MemberRenewalCardFlow1_TransactionID1_LoyalT  | MemberRenewalCardFlow1_TransactionDate1_LoyalT  | MemberRenewalCardFlow1_TransactionID2_LoyalT  | MemberRenewalCardFlow1_TransactionDate2_LoyalT  |
      | MemberRenewalCardFlow1_Jade     | MemberRenewalCardFlow1_TransactionID1_Jade    | MemberRenewalCardFlow1_TransactionDate1_Jade    | MemberRenewalCardFlow1_TransactionID2_Jade    | MemberRenewalCardFlow1_TransactionDate2_Jade    |
      | MemberRenewalCardFlow1_Ruby     | MemberRenewalCardFlow1_TransactionID1_Ruby    | MemberRenewalCardFlow1_TransactionDate1_Ruby    | MemberRenewalCardFlow1_TransactionID2_Ruby    | MemberRenewalCardFlow1_TransactionDate2_Ruby    |
      | MemberRenewalCardFlow1_Diamond  | MemberRenewalCardFlow1_TransactionID1_Diamond | MemberRenewalCardFlow1_TransactionDate1_Diamond | MemberRenewalCardFlow1_TransactionID2_Diamond | MemberRenewalCardFlow1_TransactionDate2_Diamond |