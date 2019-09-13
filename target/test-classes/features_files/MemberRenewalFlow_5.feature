Feature: Flow 5 - Member got Grace period applied since none of the criteria met and has not availed  Grace period before
  @Scenario_member_renewal_flow_5
  Scenario: Member got Grace period applied since none of the criteria met and has not availed  Grace period before.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow5   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow5 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow5_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow5_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow5   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow5   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | Grace_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow5  |
    And I validate "Movement Type" is "NEW" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_5_All
  Scenario Outline: Member got Grace period applied since none of the criteria met and has not availed  Grace period before.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow5>   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow5> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow5_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow5_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow5>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow5>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | Grace_Rule |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow5>  |
    And I validate "Movement Type" is "NEW" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow5          | MemberRenewalCardFlow5_TransactionID1         | MemberRenewalCardFlow5_TransactionDate1         |
      | MemberRenewalCardFlow5_LoyalT   | MemberRenewalCardFlow5_TransactionID1_LoyalT  | MemberRenewalCardFlow5_TransactionDate1_LoyalT  |
      | MemberRenewalCardFlow5_Jade     | MemberRenewalCardFlow5_TransactionID1_Jade    | MemberRenewalCardFlow5_TransactionDate1_Jade    |
      | MemberRenewalCardFlow5_Ruby     | MemberRenewalCardFlow5_TransactionID1_Ruby    | MemberRenewalCardFlow5_TransactionDate1_Ruby    |
      | MemberRenewalCardFlow5_Diamond  | MemberRenewalCardFlow5_TransactionID1_Diamond | MemberRenewalCardFlow5_TransactionDate1_Diamond |