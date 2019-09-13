Feature: Flow 4 - Member renewed due to  Manual Exception Rule Met
  @Scenario_member_renewal_flow_4
  Scenario: Member renewed due to  Manual Exception Rule Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow4   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow4 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow4_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow4_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow4   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    When I manual renewal member from salesforce UI
    Then I validate manual tier "renewal" message
    When I approve all member "Manual Renew Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | MemberRenewalCardFlow4      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I read the "first" membership cycle in the SF
    And I checked "Exception Renewal Flag" status is "true"
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow4   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | Manual_Exception_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow4  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Approval by CRM" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_4_All
  Scenario Outline: Member renewed due to  Manual Exception Rule Met.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow4>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow4> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow4_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow4_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow4>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    When I manual renewal member from salesforce UI
    Then I validate manual tier "renewal" message
    When I approve all member "Manual Renew Request" requests
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                 |
      | <MemberRenewalCardFlow4>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I read the "first" membership cycle in the SF
    And I checked "Exception Renewal Flag" status is "true"
    And I update the Membershipcycle End date in Salesforce UI
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow4>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | Manual_Exception_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow4>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew - Exceptional Approval by CRM" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow4          | MemberRenewalCardFlow4_TransactionID1         | MemberRenewalCardFlow4_TransactionDate1         |
      | MemberRenewalCardFlow4_Jade     | MemberRenewalCardFlow4_TransactionID1_Jade    | MemberRenewalCardFlow4_TransactionDate1_Jade    |
      | MemberRenewalCardFlow4_Ruby     | MemberRenewalCardFlow4_TransactionID1_Ruby    | MemberRenewalCardFlow4_TransactionDate1_Ruby    |
      | MemberRenewalCardFlow4_Diamond  | MemberRenewalCardFlow4_TransactionID1_Diamond | MemberRenewalCardFlow4_TransactionDate1_Diamond |

