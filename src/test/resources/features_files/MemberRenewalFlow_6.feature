Feature: Flow 6 - All Exception Rule Met and Member got renewed based only on Threshold - to validate preference
  @Scenario_member_renewal_flow_6
  Scenario: All Exception Rule Met and Member got renewed based only on Threshold - to validate preference.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow6   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow6 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | MemberRenewalCardFlow6  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow6_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow6_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow6   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                            | <Transaction_Date>                     |
      | MemberRenewalCardFlow6_TransactionID2   | Member_Transaction_Association_Disassociation_Division| MemberRenewalCardFlow6_TransactionDate2|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow6   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    When I manual renewal member from salesforce UI
    Then I validate manual tier "renewal" message
    When I approve all member "Manual Renew Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | MemberRenewalCardFlow6      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I read the "first" membership cycle in the SF
    And I checked "Exception Renewal Flag" status is "true"
    And I checked "Grace Period Flag" status is "false"
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow6   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria> |
      | All_Exception_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow6  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (threshold)" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_6_All
  Scenario Outline: All Exception Rule Met and Member got renewed based only on Threshold - to validate preference.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow6>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow6> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow6_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow6_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow6>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                      | <Division>                                            | <Transaction_Date>                       |
      | <MemberRenewalCardFlow6_TransactionID2>   | Member_Transaction_Association_Disassociation_Division| <MemberRenewalCardFlow6_TransactionDate2>|
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow6>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    When I manual renewal member from salesforce UI
    Then I validate manual tier "renewal" message
    When I approve all member "Manual Renew Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                 |
      | <MemberRenewalCardFlow6>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I read the "first" membership cycle in the SF
    And I checked "Exception Renewal Flag" status is "true"
    And I checked "Grace Period Flag" status is "false"
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow6>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria> |
      | All_Exception_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow6>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Rule (threshold)" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow6          | MemberRenewalCardFlow6_TransactionID1         | MemberRenewalCardFlow6_TransactionDate1         | MemberRenewalCardFlow6_TransactionID2         | MemberRenewalCardFlow6_TransactionDate2         |
      | MemberRenewalCardFlow6_Jade     | MemberRenewalCardFlow6_TransactionID1_Jade    | MemberRenewalCardFlow6_TransactionDate1_Jade    | MemberRenewalCardFlow6_TransactionID2_Jade    | MemberRenewalCardFlow6_TransactionDate2_Jade    |
      | MemberRenewalCardFlow6_Ruby     | MemberRenewalCardFlow6_TransactionID1_Ruby    | MemberRenewalCardFlow6_TransactionDate1_Ruby    | MemberRenewalCardFlow6_TransactionID2_Ruby    | MemberRenewalCardFlow6_TransactionDate2_Ruby    |
      | MemberRenewalCardFlow6_Diamond  | MemberRenewalCardFlow6_TransactionID1_Diamond | MemberRenewalCardFlow6_TransactionDate1_Diamond | MemberRenewalCardFlow6_TransactionID2_Diamond | MemberRenewalCardFlow6_TransactionDate2_Diamond |

