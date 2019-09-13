Feature: Flow 9 - In grace period - posting transaction that meets his renewal criteria
  @Scenario_member_renewal_flow_9
  Scenario: In grace period - posting transaction that meets his renewal criteria.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>            |
      | MemberRenewalCardFlow9   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberRenewalCardFlow9 | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I Associate transaction with Card number
      | <Transaction_Number>                  | <Division>                                             | <Transaction_Date>                      |
      | MemberRenewalCardFlow9_TransactionID1 | Member_Transaction_Association_Disassociation_Division | MemberRenewalCardFlow9_TransactionDate1 |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow9   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>            |
      | MemberRenewalCardFlow9   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "true"
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>              | <TotalRecords>                       | <ATPStartTimestamp>                     | <ATPEndTimestamp>                     |
      | POST           | MemberRenewalCardFlow9     | MemberRenewalCardFlow9_TotalRecords1 | MemberRenewalCardFlow9_Start_timestamp1 | MemberRenewalCardFlow9_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>               |
      | MemberRenewalCardFlow9      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request

  @Scenario_member_renewal_flow_9
  Scenario: In grace period - posting transaction that meets his renewal criteria.
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    And I checked "Cycle End Date" status is "today"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>   |
      | Posting_Renewal_Flow |
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | MemberRenewalCardFlow9  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew" in Matrix DB
    And I validate expected point information with Matrix DB


  @Scenario_member_renewal_flow_9_All
  Scenario Outline: In grace period - posting transaction that meets his renewal criteria.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>              |
      | <MemberRenewalCardFlow9>   |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberRenewalCardFlow9> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I Associate transaction with Card number
      | <Transaction_Number>                    | <Division>                                             | <Transaction_Date>                        |
      | <MemberRenewalCardFlow9_TransactionID1> | Member_Transaction_Association_Disassociation_Division | <MemberRenewalCardFlow9_TransactionDate1> |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow9>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I run renewal job on SF
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>              |
      | <MemberRenewalCardFlow9>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "true"
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                | <TotalRecords>                         | <ATPStartTimestamp>                       | <ATPEndTimestamp>                       |
      | POST           | <MemberRenewalCardFlow9>     | <MemberRenewalCardFlow9_TotalRecords1> | <MemberRenewalCardFlow9_Start_timestamp1> | <MemberRenewalCardFlow9_End_timestamp1> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                 |
      | <MemberRenewalCardFlow9>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "3" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I read the membership Information from Salesforce UI
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I read the "first" membership cycle in the SF
    And I checked "Grace Period Flag" status is "false"
    And I checked "Active Flag" status is "true"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I read the "second" membership cycle in the SF
    And I checked "Grace Period Flag" status is "true"
    And I checked "Active Flag" status is "false"
    And I checked "Cycle End Date" status is "today"
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I read the membership Information after renewal job in salesforce
      | <Renewal_Criteria>   |
      | Posting_Renewal_Flow |
    And I query Matrix DB Main table for membership information
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>             |
      | <MemberRenewalCardFlow9>  |
    And I validate "Movement Type" is "RENEW" in Matrix DB
    And I validate "Remarks" is "Renew" in Matrix DB
    And I validate expected point information with Matrix DB

    Examples:
      | MemberRenewalCardFlow9          | MemberRenewalCardFlow9_TransactionID1         | MemberRenewalCardFlow9_TransactionDate1         | MemberRenewalCardFlow9_TotalRecords1          | MemberRenewalCardFlow9_Start_timestamp1         | MemberRenewalCardFlow9_End_timestamp1          |
      | MemberRenewalCardFlow9_LoyalT   | MemberRenewalCardFlow9_TransactionID1_LoyalT  | MemberRenewalCardFlow9_TransactionDate1_LoyalT  | MemberRenewalCardFlow9_TotalRecords1_LoyalT   | MemberRenewalCardFlow9_Start_timestamp1_LoyalT  | MemberRenewalCardFlow9_End_timestamp1_LoyalT   |
      | MemberRenewalCardFlow9_Jade     | MemberRenewalCardFlow9_TransactionID1_Jade    | MemberRenewalCardFlow9_TransactionDate1_Jade    | MemberRenewalCardFlow9_TotalRecords1_Jade     | MemberRenewalCardFlow9_Start_timestamp1_Jade    | MemberRenewalCardFlow9_End_timestamp1_Jade     |
      | MemberRenewalCardFlow9_Ruby     | MemberRenewalCardFlow9_TransactionID1_Ruby    | MemberRenewalCardFlow9_TransactionDate1_Ruby    | MemberRenewalCardFlow9_TotalRecords1_Ruby     | MemberRenewalCardFlow9_Start_timestamp1_Ruby    | MemberRenewalCardFlow9_End_timestamp1_Ruby     |
      | MemberRenewalCardFlow9_Diamond  | MemberRenewalCardFlow9_TransactionID1_Diamond | MemberRenewalCardFlow9_TransactionDate1_Diamond | MemberRenewalCardFlow9_TotalRecords1_Diamond  | MemberRenewalCardFlow9_Start_timestamp1_Diamond | MemberRenewalCardFlow9_End_timestamp1_Diamond  |

