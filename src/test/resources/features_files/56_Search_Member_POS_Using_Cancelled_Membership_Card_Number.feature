Feature: Search Member POS Using Cancelled Card Number
  ## ================== Data setup ======================================
  @Scenario_Search_Member_POS_Using_Cancelled_Card_Number
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                                   |
      | POS_Search_Member_Using_Cancelled_Card_Number   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                                | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | POS_Search_Member_Using_Cancelled_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                                 | <TotalRecords>                                          | <ATPStartTimestamp>                                    | <ATPEndTimestamp>                                    |
      | POST           | POS_Search_Member_Using_Cancelled_Card_Number | POS_Search_Member_Using_Cancelled_Card_totalRecordCount | POS_Search_Member_Using_Cancelled_Card_Start_timestamp | POS_Search_Member_Using_Cancelled_Card_End_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                   |
      | POS_Search_Member_Using_Cancelled_Card_Number   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_Search_Member_POS_Using_Cancelled_Card_Number
  Scenario: Cancel Member card and validate cancellation on Salesforce
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                   |
      | POS_Search_Member_Using_Cancelled_Card_Number   |
    When I cancel the member card
    Then I validate the card "Cancel" confirmation message
    And I validate card status is "Cancelled"
    And I validate card status is "Cancelled" under Membership Cards tab

  @Scenario_Search_Member_POS_Using_Cancelled_Card_Number
  Scenario: Validate Member card cancellation on Matrix DB
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                                 |
      | POS_Search_Member_Using_Cancelled_Card_Number |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    And I validate card status is "Cancelled" in Main database
      | <Card_Number>                                 |
      | POS_Search_Member_Using_Cancelled_Card_Number |

  @Scenario_Search_Member_POS_Using_Cancelled_Card_Number
  Scenario: I search member using POS search member API call
    Given I send a POST request to POS search member for card number "POS_Search_Member_Using_Cancelled_Card_Number"
    Then  I read the details in POS search member response for card number "POS_Search_Member_Using_Cancelled_Card_Number"
    Then I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                   |
      | POS_Search_Member_Using_Cancelled_Card_Number   |
    Then I read POS search member values from Salesfroce for card number "POS_Search_Member_Using_Cancelled_Card_Number"
    Then I validate POS search Member values with Salesforce