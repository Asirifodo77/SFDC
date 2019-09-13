Feature: Member Card Management in Salesforce - Replace
  @Scenario_member_Replace_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                            |
      | Member_Card_Management_Replace_NewCard   |
    And I delete the new card number to be replaced from matrix main table
      | <Card_Number>                            |
      | Member_Card_Management_Replace_NewCard   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_Card_Management_Replace_OldCard | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                          | <TotalRecords>       | <ATPStartTimestamp>     | <ATPEndTimestamp>     |
      | POST           | Member_Card_Management_Replace_OldCard | Replace_TotalRecords | Replace_Start_timestamp | Replace_End_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_Replace_card_management
  Scenario: Replace Member card and validate replacement on Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    When I replace the member card with a New Card number
      | <Card_Number_OLD>                        |<Card_Number_NEW>                        |
      | Member_Card_Management_Replace_OldCard   |Member_Card_Management_Replace_NewCard   |
    Then I validate the card "Replace" confirmation message
    When I search for the member with Card Number
      | <Card_Number>                            |
      | Member_Card_Management_Replace_NewCard   |
    Then I validate card status is "Active" for the new card under Membership Cards tab
    When I search for the member with Card Number
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    Then I validate "The membership card is cancelled" warning message
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                          |
      | Member_Card_Management_Replace_OldCard |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Given I login to Salesforce
    When I search for the member with Card Number
      | <Card_Number>                            |
      | Member_Card_Management_Replace_NewCard   |
    And I validate card status is "Cancelled" for the old card under Membership Cards tab
      | <Card_Number>                          |
      | Member_Card_Management_Replace_OldCard |

  @Scenario_member_Replace_card_management
  Scenario: Validate Member replace cancellation on Matrix DB
    Then I validate "MembershipStatusCode" is "RENEWED" in Main database
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    And I validate "RenewedTo" is "Member_Card_Management_Replace_NewCard" in Main database
      | <Card_Number>                            |
      | Member_Card_Management_Replace_OldCard   |
    And I validate "MembershipStatusCode" is "ACTIVE" in Main database
      | <Card_Number>                            |
      | Member_Card_Management_Replace_NewCard   |


  @Scenario_member_Replace_card_management_All
  Scenario Outline: Validate Member replace cancellation on Matrix DB for any Tier
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>       |
      | <Replace_OldCard>   |
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>       |
      | <Replace_NewCard>   |
    And I delete the new card number to be replaced from matrix main table
      | <Card_Number>       |
      | <Replace_NewCard>   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>    | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Replace_OldCard> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>     | <TotalRecords>         | <ATPStartTimestamp>       | <ATPEndTimestamp>       |
      | POST           | <Replace_OldCard> | <Replace_TotalRecords> | <Replace_Start_timestamp> | <Replace_End_timestamp> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>       |
      | <Replace_OldCard>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>       |
      | <Replace_OldCard>   |
    When I replace the member card with a New Card number
      | <Card_Number_OLD>   |<Card_Number_NEW>   |
      | <Replace_OldCard>   |<Replace_NewCard>   |
    Then I validate the card "Replace" confirmation message
    When I search for the member with Card Number
      | <Card_Number>       |
      | <Replace_NewCard>   |
    Then I validate card status is "Active" for the new card under Membership Cards tab
    When I search for the member with Card Number
      | <Card_Number>       |
      | <Replace_OldCard>   |
    Then I validate "The membership card is cancelled" warning message
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>     |
      | <Replace_OldCard> |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    Given I login to Salesforce
    When I search for the member with Card Number
      | <Card_Number>       |
      | <Replace_NewCard>   |
    And I validate card status is "Cancelled" for the old card under Membership Cards tab
      | <Card_Number>     |
      | <Replace_OldCard> |
    Then I validate "MembershipStatusCode" is "RENEWED" in Main database
      | <Card_Number>       |
      | <Replace_OldCard>   |
    And I validate "RenewedTo" is "<Replace_NewCard>" in Main database
      | <Card_Number>       |
      | <Replace_OldCard>   |
    And I validate "MembershipStatusCode" is "ACTIVE" in Main database
      | <Card_Number>       |
      | <Replace_NewCard>   |

    Examples:
      | Replace_OldCard                                | Replace_NewCard                                | Replace_TotalRecords         | Replace_Start_timestamp         | Replace_End_timestamp         |
      | Member_Card_Management_Replace_OldCard_LoyalT  | Member_Card_Management_Replace_NewCard_LoyalT  | Replace_TotalRecords_LoyalT  | Replace_Start_timestamp_LoyalT  | Replace_End_timestamp_LoyalT  |
      | Member_Card_Management_Replace_OldCard_Diamond | Member_Card_Management_Replace_NewCard_Diamond | Replace_TotalRecords_Diamond | Replace_Start_timestamp_Diamond | Replace_End_timestamp_Diamond |