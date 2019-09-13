Feature: Create Member from using eTR and POS Blacklisted Member
  # ================= Data Setup : Creating member , associate transaction and then blacklist =========================================
  @Scenario_Create_Member_eTR_And_POS_Blacklisted
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


  @Scenario_Create_Member_eTR_And_POS_Blacklisted
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
    # =============== END OF DATA SETUP ============================================

  @Scenario_Create_Member_eTR_And_POS_Blacklisted
  Scenario: I create member in ETR using the blacklisted member card Number
    Given I send a POST request to create ETR member with already blacklisted card number"Member_Card_Management_Blacklist_Card"
    Then I validate the API return message for ETR
    Then I validate Custom logs for an error under ETR as "Membership card is blocked"

  @Scenario_Create_Member_eTR_And_POS_Blacklisted
  Scenario: I create member in POS using the blacklisted member card Number
    Given I send a POST request to create POS member with already blacklisted card number"Member_Card_Management_Blacklist_Card"
    Then I validate the API return message for POS
    Then I validate Custom logs for an error under POS as "Membership card is blocked"