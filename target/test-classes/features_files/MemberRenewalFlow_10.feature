Feature: Flow 10 - In grace period - associating transaction that meets his renewal criteria
  @Scenario_member_renewal_flow_10
  Scenario: In grace period - associating transaction that meets his renewal criteria.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>             |
      | MemberRenewalCardFlow10   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>          | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow10 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I Associate transaction with Card number
      | <Transaction_Number>                   | <Division>                                             | <Transaction_Date>                       |
      | MemberRenewalCardFlow10_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow10_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>             |
      | MemberRenewalCardFlow10   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>             |
      | MemberRenewalCardFlow10   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I Associate transaction with Card number
      | <Transaction_Number>                     | <Division>                                            | <Transaction_Date>                      |
      | MemberRenewalCardFlow10_TransactionID2   | Member_Transaction_Association_Disassociation_Division| MemberRenewalCardFlow10_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>             |
      | MemberRenewalCardFlow10   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    And I checked "Cycle End Date" status is "today"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>   |
      | Posting_Renewal_Flow |
    And I query Matrix DB Main table for membership information
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I query Matrix DB Main table for point information
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow10  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_10_All
  Scenario Outline: In grace period - associating transaction that meets his renewal criteria.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>               |
      | <MemberRenewalCardFlow10>   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>            | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow10> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                     | <Division>                                             | <Transaction_Date>                         |
      | <MemberRenewalCardFlow10_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow10_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | <MemberRenewalCardFlow10>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | <MemberRenewalCardFlow10>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                     | <Division>                                              | <Transaction_Date>                        |
      | <MemberRenewalCardFlow10_TransactionID2>   | Member_Transaction_Association_Disassociation_Division| <MemberRenewalCardFlow10_TransactionDate2>|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | <MemberRenewalCardFlow10>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    And I checked "Cycle End Date" status is "today"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>   |
      | Posting_Renewal_Flow |
    And I query Matrix DB Main table for membership information
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow10>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow10          | MemberRenewalCardFlow10_TransactionID1         | MemberRenewalCardFlow10_TransactionDate1         | MemberRenewalCardFlow10_TransactionID2         | MemberRenewalCardFlow10_TransactionDate2         |
      | MemberRenewalCardFlow10_LoyalT   | MemberRenewalCardFlow10_TransactionID1_LoyalT  | MemberRenewalCardFlow10_TransactionDate1_LoyalT  | MemberRenewalCardFlow10_TransactionID2_LoyalT  | MemberRenewalCardFlow10_TransactionDate2_LoyalT  |
      | MemberRenewalCardFlow10_Jade     | MemberRenewalCardFlow10_TransactionID1_Jade    | MemberRenewalCardFlow10_TransactionDate1_Jade    | MemberRenewalCardFlow10_TransactionID2_Jade    | MemberRenewalCardFlow10_TransactionDate2_Jade    |
      | MemberRenewalCardFlow10_Ruby     | MemberRenewalCardFlow10_TransactionID1_Ruby    | MemberRenewalCardFlow10_TransactionDate1_Ruby    | MemberRenewalCardFlow10_TransactionID2_Ruby    | MemberRenewalCardFlow10_TransactionDate2_Ruby    |
      | MemberRenewalCardFlow10_Diamond  | MemberRenewalCardFlow10_TransactionID1_Diamond | MemberRenewalCardFlow10_TransactionDate1_Diamond | MemberRenewalCardFlow10_TransactionID2_Diamond | MemberRenewalCardFlow10_TransactionDate2_Diamond |