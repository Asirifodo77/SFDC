Feature: Flow 3 - Member renewed due to  CF and Status $ Exception Rule Criteria Met
  @Scenario_member_renewal_flow_3
  Scenario: Member renewed due to  CF and Status $ Exception Rule Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow3   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow3 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | MemberRenewalCardFlow3  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow3_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow3_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow3   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                            | <Transaction_Date>                     |
      | MemberRenewalCardFlow3_TransactionID2   | Member_Transaction_Association_Disassociation_Division| MemberRenewalCardFlow3_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow3   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow3   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>  |
      | CFAndStatus_Renewal |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow3  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (CF)" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_3_All
  Scenario Outline: Member renewed due to  CF and Status $ Exception Rule Criteria Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow3>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow3> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow3_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow3_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow3>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                      | <Division>                                            | <Transaction_Date>                       |
      | <MemberRenewalCardFlow3_TransactionID2>   | Member_Transaction_Association_Disassociation_Division| <MemberRenewalCardFlow3_TransactionDate2>|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow3>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow3>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>  |
      | CFAndStatus_Renewal |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow3>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (CF)" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow3          | MemberRenewalCardFlow3_TransactionID1         | MemberRenewalCardFlow3_TransactionDate1         | MemberRenewalCardFlow3_TransactionID2         | MemberRenewalCardFlow3_TransactionDate2         |
      | MemberRenewalCardFlow3_Jade     | MemberRenewalCardFlow3_TransactionID1_Jade    | MemberRenewalCardFlow3_TransactionDate1_Jade    | MemberRenewalCardFlow3_TransactionID2_Jade    | MemberRenewalCardFlow3_TransactionDate2_Jade    |
      | MemberRenewalCardFlow3_Ruby     | MemberRenewalCardFlow3_TransactionID1_Ruby    | MemberRenewalCardFlow3_TransactionDate1_Ruby    | MemberRenewalCardFlow3_TransactionID2_Ruby    | MemberRenewalCardFlow3_TransactionDate2_Ruby    |
      | MemberRenewalCardFlow3_Diamond  | MemberRenewalCardFlow3_TransactionID1_Diamond | MemberRenewalCardFlow3_TransactionDate1_Diamond | MemberRenewalCardFlow3_TransactionID2_Diamond | MemberRenewalCardFlow3_TransactionDate2_Diamond |

