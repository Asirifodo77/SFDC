Feature: Get Member POS Using Ruby With Active Membership Cycle
  ## ================= Data setup : create a member and updrade to ruby with atp =============
  @Scenario_Get_Member_POS_Using_Ruby_With_Active_Membership_Cycle
  Scenario: Validation of member created in SF and validating ATP
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                      | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | ATP_New_Customer_Sale_Member_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                       | <TotalRecords>   | <ATPStartTimestamp> | <ATPEndTimestamp> |
      | POST           | ATP_New_Customer_Sale_Member_Number | ATP_TotalRecords | ATP_Start_timestamp | ATP_End_timestamp |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                       |
      | ATP_New_Customer_Sale_Member_Number |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_Get_Member_POS_Using_Ruby_With_Active_Membership_Cycle
  Scenario: Validation of created member in SF  and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    And I query Matrix DB Main table for point information
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    And I validate expected point information with Matrix DB

    # ======= End of data setup: member is upgraded to ruby ==========

  @Scenario_Get_Member_POS_Using_Ruby_With_Active_Membership_Cycle
  Scenario: I get member using POS get member API call
    Given I send a POST request to POS get member for card number "ATP_New_Customer_Sale_Member_Number"
    Then  I read the details in POS get member response for card number "ATP_New_Customer_Sale_Member_Number"
    Then I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                         |
      | ATP_New_Customer_Sale_Member_Number   |
    Then I read POS member values from Salesfroce for card number "ATP_New_Customer_Sale_Member_Number"
    Then I validate POS get Member values with Salesforce

