Feature: Member Card Management in Salesforce
  @Scenario_member_cancel_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | Member_Card_Management_Cancel   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Card_Management_Cancel | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                 | <TotalRecords>      | <ATPStartTimestamp>    | <ATPEndTimestamp>    |
      | POST           | Member_Card_Management_Cancel | Cancel_TotalRecords | Cancel_Start_timestamp | Cancel_End_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                   |
      | Member_Card_Management_Cancel   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_cancel_card_management
  Scenario: Cancel Member card and validate cancellation on Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | Member_Card_Management_Cancel   |
    When I cancel the member card
    Then I validate the card "Cancel" confirmation message
    And I validate card status is "Cancelled"
    And I validate card status is "Cancelled" under Membership Cards tab

  @Scenario_member_cancel_card_management
  Scenario: Validate Member card cancellation on Matrix DB
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                 |
      | Member_Card_Management_Cancel |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    And I validate card status is "Cancelled" in Main database
      | <Card_Number>                 |
      | Member_Card_Management_Cancel |

  @Scenario_member_cancel_card_management_All
  Scenario Outline: Member cancel card management creating a member all
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | <Member_Card_Management_Cancel> |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Member_Card_Management_Cancel> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                   | <TotalRecords>        | <ATPStartTimestamp>      | <ATPEndTimestamp>      |
      | POST           | <Member_Card_Management_Cancel> | <Cancel_TotalRecords> | <Cancel_Start_timestamp> | <Cancel_End_timestamp> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                   |
      | <Member_Card_Management_Cancel> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | <Member_Card_Management_Cancel> |
    When I cancel the member card
    Then I validate the card "Cancel" confirmation message
    And I validate card status is "Cancelled"
    And I validate card status is "Cancelled" under Membership Cards tab
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                   |
      | <Member_Card_Management_Cancel> |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    And I validate card status is "Cancelled" in Main database
      | <Card_Number>                   |
      | <Member_Card_Management_Cancel> |

    Examples:
      | Member_Card_Management_Cancel         | Cancel_TotalRecords         | Cancel_Start_timestamp         | Cancel_End_timestamp         |
      | Member_Card_Management_Cancel_LoyalT  | Cancel_TotalRecords_LoyalT  | Cancel_Start_timestamp_LoyalT  | Cancel_End_timestamp_LoyalT  |
      | Member_Card_Management_Cancel_Diamond | Cancel_TotalRecords_Diamond | Cancel_Start_timestamp_Diamond | Cancel_End_timestamp_Diamond |