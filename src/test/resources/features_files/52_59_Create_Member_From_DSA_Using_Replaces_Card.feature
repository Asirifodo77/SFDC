Feature: Create Member from DSA using an existing replaced card
  ## ================== Data setup ===========================
  @Scenario_Create_Member_DSA_From_Replaced_Card
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

  @Scenario_Create_Member_DSA_From_Replaced_Card
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

  @Scenario_Create_Member_DSA_From_Replaced_Card
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

    ## ========== End of Data setup ====================
  @Scenario_Create_Member_DSA_From_Replaced_Card
  Scenario: (52) I create member in DSA using the Replaced member card Number
    Given I send a POST request to create DSA member with already Replaced card number "DSA_Member_Replaced_Card_Number"
    Then I validate the API return message for DSA
    Then I validate Custom logs for an error under DSA as "Membership card is blocked"

  # ANU CONFIRMED - MUST VALIDATE THE API RESPONSE'S RETURN MESSAGE IS QUAL TO "CANCELLED MEMBER"
  @Scenario_Create_Member_DSA_From_Replaced_Card
  Scenario: (59) I get POS member details using Replaced member card Number
    Given I send a POST request to POS get member for card number "DSA_Member_Replaced_Card_Number"
    Then I read the details in POS get member response for card number "DSA_Member_Replaced_Card_Number"
    Then I validate if POS get member return message is "CANCELLED MEMBER"
    #Then I login to Salesforce
    #And I search for the member with Card Number
    #  | <Card_Number>                         |
    #  | DSA_Member_Replaced_Card_Number       |
    #Then I read POS member values from Salesfroce for card number "DSA_Member_Replaced_Card_Number"
    #Then I validate POS get Member values with Salesforce