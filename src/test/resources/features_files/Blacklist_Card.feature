Feature: Member Card Management in Salesforce – Blacklist Cards
  @Scenario_member_Blacklist_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for blacklist
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                              |
      | Member_Card_Management_Blacklist_Card      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Card_Management_Blacklist_Card    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                             | <TotalRecords>                | <ATPStartTimestamp>              | <ATPEndTimestamp>       |
      | POST           | Member_Card_Management_Blacklist_Card     | Blacklist_TotalRecords        | Blacklist_Start_timestamp        | Blacklist_End_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                              |
      | Member_Card_Management_Blacklist_Card      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @Scenario_member_Blacklist_card_management
  Scenario: Blacklist card on Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                           |
      | Member_Card_Management_Blacklist_Card   |
    When I Blacklist the member card number
      | <Card_Number>                           |
      | Member_Card_Management_Blacklist_Card   |
    Then I validate the card "Blacklist" confirmation message
    And I validate card status is "Blacklisted"
    And I validate card status is "Blacklisted" under Membership Cards tab
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                            |
      | Member_Card_Management_Blacklist_Card    |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Then I validate "MembershipStatusCode" is "BLACKLISTED" in Main database
      | <Card_Number>                              |
      | Member_Card_Management_Blacklist_Card      |
    And I validate that the Suspended status is "NULL" in the matrix member table
      | <Card_Number>                              |
      | Member_Card_Management_Blacklist_Card      |


  @Scenario_member_Blacklist_card_management_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member for blacklist
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>         |
      | <Blacklist_Card>      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>      | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Blacklist_Card>    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>        | <TotalRecords>                | <ATPStartTimestamp>              | <ATPEndTimestamp>         |
      | POST           | <Blacklist_Card>     | <Blacklist_TotalRecords>      | <Blacklist_Start_timestamp>      | <Blacklist_End_timestamp> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>         |
      | <Blacklist_Card>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>      |
      | <Blacklist_Card>   |
    When I Blacklist the member card number
      | <Card_Number>      |
      | <Blacklist_Card>   |
    Then I validate the card "Blacklist" confirmation message
    And I validate card status is "Blacklisted"
    And I validate card status is "Blacklisted" under Membership Cards tab
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>       |
      | <Blacklist_Card>    |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Then I validate "MembershipStatusCode" is "BLACKLISTED" in Main database
      | <Card_Number>         |
      | <Blacklist_Card>      |
    And I validate that the Suspended status is "NULL" in the matrix member table
      | <Card_Number>         |
      | <Blacklist_Card>      |

    Examples:
      | Blacklist_Card                                | Blacklist_TotalRecords         | Blacklist_Start_timestamp         | Blacklist_End_timestamp         |
      |Member_Card_Management_Blacklist_Card_Jade     | Blacklist_TotalRecords_Jade    | Blacklist_Start_timestamp_Jade    | Blacklist_End_timestamp_Jade    |
      |Member_Card_Management_Blacklist_Card_Diamond  | Blacklist_TotalRecords_Diamond | Blacklist_Start_timestamp_Diamond | Blacklist_End_timestamp_Diamond |