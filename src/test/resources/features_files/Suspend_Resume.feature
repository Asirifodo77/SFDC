Feature: Member Card Management in Salesforce – Suspended and Resumed Cards
  @Scenario_member_Suspended_Resumed_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member Suspended Resume
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                                  |
      | Member_Card_Management_SuspendResume_Card      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                               | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Card_Management_SuspendResume_Card    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                                 | <TotalRecords>                | <ATPStartTimestamp>              | <ATPEndTimestamp>              |
      | POST           | Member_Card_Management_SuspendResume_Card     | SuspendResume_TotalRecords    | SuspendResume_Start_timestamp    | SuspendResume_End_timestamp    |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                  |
      | Member_Card_Management_SuspendResume_Card      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed


  @Scenario_member_Suspended_Resumed_card_management
  Scenario: Validate Member Suspended cancellation on Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                  |
      | Member_Card_Management_SuspendResume_Card      |
    And I read the membership Information from Salesforce UI
    When I Suspend the member card number
    Then I validate the card "Suspend_Blacklist" confirmation message
    And I validate card status is "Suspended - Investigation"
    And I validate card status is "Suspended - suspicious fraud" under Membership Cards tab
    And I validate that the Suspended status is "SUSPENDED" in the matrix member table
      | <Card_Number>                                  |
      | Member_Card_Management_SuspendResume_Card      |


  @Scenario_member_Suspended_Resumed_card_management
  Scenario: Suspended card Member is Resumed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                     |
      | Member_Card_Management_SuspendResume_Card         |
    When I click "Suspend Card Request" on the Member Request tab and "Resume" the request
    Then I validate the card "Resume" confirmation message
    When I search for the member with Card Number
      | <Card_Number>                                     |
      | Member_Card_Management_SuspendResume_Card         |
    Then I validate card status is "Active"
    And I validate card status is "Active" under Membership Cards tab
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                                   |
      | Member_Card_Management_SuspendResume_Card       |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Then I validate "MembershipStatusCode" is "ACTIVE" in Main database
      | <Card_Number>                                     |
      | Member_Card_Management_SuspendResume_Card         |
    And I validate that the Suspended status is "NULL" in the matrix member table
      | <Card_Number>                                     |
      | Member_Card_Management_SuspendResume_Card         |


  @Scenario_member_Suspended_Resumed_card_management_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member Suspended Resume for all two tiers
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>             |
      | <SuspendResume_Card>      |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>          | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <SuspendResume_Card>    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>            | <TotalRecords>                  | <ATPStartTimestamp>                | <ATPEndTimestamp>                |
      | POST           | <SuspendResume_Card>     | <SuspendResume_TotalRecords>    | <SuspendResume_Start_timestamp>    | <SuspendResume_End_timestamp>    |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>             |
      | <SuspendResume_Card>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>             |
      | <SuspendResume_Card>      |
    And I read the membership Information from Salesforce UI
    When I Suspend the member card number
    Then I validate the card "Suspend_Blacklist" confirmation message
    And I validate card status is "Suspended - Investigation"
    And I validate card status is "Suspended - suspicious fraud" under Membership Cards tab
    And I validate that the Suspended status is "SUSPENDED" in the matrix member table
      | <Card_Number>             |
      | <SuspendResume_Card>      |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                |
      | <SuspendResume_Card>         |
    When I click "Suspend Card Request" on the Member Request tab and "Resume" the request
    Then I validate the card "Resume" confirmation message
    When I search for the member with Card Number
      | <Card_Number>                |
      | <SuspendResume_Card>         |
    Then I validate card status is "Active"
    And I validate card status is "Active" under Membership Cards tab
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>              |
      | <SuspendResume_Card>       |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Then I validate "MembershipStatusCode" is "ACTIVE" in Main database
      | <Card_Number>                |
      | <SuspendResume_Card>         |
    And I validate that the Suspended status is "NULL" in the matrix member table
      | <Card_Number>                |
      | <SuspendResume_Card>         |

    Examples:
      | SuspendResume_Card                                | SuspendResume_TotalRecords         | SuspendResume_Start_timestamp         | SuspendResume_End_timestamp         |
      | Member_Card_Management_SuspendResume_Card_Jade    | SuspendResume_TotalRecords_Jade    | SuspendResume_Start_timestamp_Jade    | SuspendResume_End_timestamp_Jade    |
      | Member_Card_Management_SuspendResume_Card_Diamond | SuspendResume_TotalRecords_Diamond | SuspendResume_Start_timestamp_Diamond | SuspendResume_End_timestamp_Diamond |
