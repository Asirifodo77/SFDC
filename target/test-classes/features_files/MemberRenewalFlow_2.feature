Feature: Flow 2 - Member renewed due to  Threshold Exception Rule Criteria Met
  @Scenario_member_renewal_flow_2
  Scenario: Member renewed due to  Threshold Exception Rule Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow2   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow2 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | MemberRenewalCardFlow2  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow2_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow2_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow2   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                            | <Transaction_Date>                     |
      | MemberRenewalCardFlow2_TransactionID2   | Member_Transaction_Association_Disassociation_Division| MemberRenewalCardFlow2_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow2   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow2   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>|
      | Threshold_Renewal |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow2  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (threshold)" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_2_All
  Scenario Outline: Member renewed due to  Threshold Exception Rule Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow2>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow2> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow2_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow2_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow2>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                      | <Division>                                            | <Transaction_Date>                        |
      | <MemberRenewalCardFlow2_TransactionID2>   | Member_Transaction_Association_Disassociation_Division| <MemberRenewalCardFlow2_TransactionDate2> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow2>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>              |
      | <MemberRenewalCardFlow2>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow2>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>|
      | Threshold_Renewal |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow2>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (threshold)" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow2          | MemberRenewalCardFlow2_TransactionID1         | MemberRenewalCardFlow2_TransactionDate1         | MemberRenewalCardFlow2_TransactionID2         | MemberRenewalCardFlow2_TransactionDate2         |
      | MemberRenewalCardFlow2_Jade     | MemberRenewalCardFlow2_TransactionID1_Jade    | MemberRenewalCardFlow2_TransactionDate1_Jade    | MemberRenewalCardFlow2_TransactionID2_Jade    | MemberRenewalCardFlow2_TransactionDate2_Jade    |
      | MemberRenewalCardFlow2_Ruby     | MemberRenewalCardFlow2_TransactionID1_Ruby    | MemberRenewalCardFlow2_TransactionDate1_Ruby    | MemberRenewalCardFlow2_TransactionID2_Ruby    | MemberRenewalCardFlow2_TransactionDate2_Ruby    |
      | MemberRenewalCardFlow2_Diamond  | MemberRenewalCardFlow2_TransactionID1_Diamond | MemberRenewalCardFlow2_TransactionDate1_Diamond | MemberRenewalCardFlow2_TransactionID2_Diamond | MemberRenewalCardFlow2_TransactionDate2_Diamond |

