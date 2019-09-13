Feature: Flow 7 - None of the Basic / Exception Rule Met and Member got downgraded\deactivated
  @Scenario_member_renewal_flow_7
  Scenario: None of the Basic / Exception Rule Met and Member got downgraded\deactivated
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow7 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow7_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow7_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the membership Information from Salesforce UI
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | NO_Rule        |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I validate "Movement Type" is "DOWNGRADE" in Matrix DB
    And I validate "Remarks" is "Downgrade" in Matrix DB
    And I validate expected point information with Matrix DB

  @Scenario_member_renewal_flow_7_LoyalT
  Scenario: None of the Basic / Exception Rule Met and Member got downgraded\deactivated for LoyalT Members
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow7 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow7_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow7_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the membership Information from Salesforce UI
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow7   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | NO_Rule        |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow7  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           | <Scenario>           |
      | MemberRenewalCardFlow7  | LoyalT_RenewalFlow_7 |
    And I validate "Movement Type" is "NEW" in Matrix DB
    And I validate "LoyalT_Remarks" is "Direct entry to LOYAL T with a $101.00 purchase; With Grace Period; Deactivate" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_7_All
  Scenario Outline: None of the Basic / Exception Rule Met and Member got downgraded\deactivated
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow7>   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow7> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow7_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow7_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow7>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I read the membership Information from Salesforce UI
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow7>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>    |
      | NO_Rule        |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow7>  |
    And I validate "Movement Type" is "DOWNGRADE" in Matrix DB
    And I validate "Remarks" is "Downgrade" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow7          | MemberRenewalCardFlow7_TransactionID1         | MemberRenewalCardFlow7_TransactionDate1         |
      | MemberRenewalCardFlow7_Jade     | MemberRenewalCardFlow7_TransactionID1_Jade    | MemberRenewalCardFlow7_TransactionDate1_Jade    |
      | MemberRenewalCardFlow7_Ruby     | MemberRenewalCardFlow7_TransactionID1_Ruby    | MemberRenewalCardFlow7_TransactionDate1_Ruby    |
      | MemberRenewalCardFlow7_Diamond  | MemberRenewalCardFlow7_TransactionID1_Diamond | MemberRenewalCardFlow7_TransactionDate1_Diamond |
