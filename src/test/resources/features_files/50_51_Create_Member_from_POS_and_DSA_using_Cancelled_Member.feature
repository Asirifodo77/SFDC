Feature: Create Member from using POS and DSA Cancelled Member
  ## ================== Data setup ======================================
  @Scenario_Create_Member_POS_And_DSA_Cancelled
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

  @Scenario_Create_Member_POS_And_DSA_Cancelled
  Scenario: Cancel Member card and validate cancellation on Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | Member_Card_Management_Cancel   |
    When I cancel the member card
    Then I validate the card "Cancel" confirmation message
    And I validate card status is "Cancelled"
    And I validate card status is "Cancelled" under Membership Cards tab

  @Scenario_Create_Member_POS_And_DSA_Cancelled
  Scenario: Validate Member card cancellation on Matrix DB
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                 |
      | Member_Card_Management_Cancel |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    And I validate card status is "Cancelled" in Main database
      | <Card_Number>                 |
      | Member_Card_Management_Cancel |

    ## ============ END OF DATA SETUP =============================
  # ======== scenario 50 ======================
  @Scenario_Create_Member_POS_And_DSA_Cancelled
  Scenario: I create member in POS using the cancelled member card Number
    Given I send a POST request to create POS member with already cancelled card number"Member_Card_Management_Cancel"
    Then I validate the API return message for POS
    Then I validate Custom logs for an error under POS as "Membership card is blocked"

# ======== scenario 51 ======================
  @Scenario_Create_Member_POS_And_DSA_Cancelled
  Scenario: I create member in DSA for card number not in range
    Given I validate if card range with maximum value "DSA_Member_Card_Range_Maximum_Value" and minimum value "DSA_Member_Card_Range_Minimum_Value" and store location "DSA_Member_Card_Range_Store_Location" does not exist
    Given I validate if card number "DSA_Member_Out_Of_Range_Card_Number" does not exist
    Given I send a POST request to create DSA member with out of range card number "DSA_Member_Out_Of_Range_Card_Number"
    Then I validate the API return message for DSA
    Then I validate Custom logs for an error under DSA as "Invalid Card Number Card number is out of range"
