Feature: New Customer Sale - Member created in Salesforce
  @Scenario_Create_new_member_in_SF_and_associate_transaction_through_ATP
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

  @Scenario_Create_new_member_in_SF_and_associate_transaction_through_ATP
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

  @Scenario_Create_new_member_in_SF_and_associate_transaction_through_ATP_All
  Scenario Outline: Validation of member deleted on SF and validation created a new member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>          |
      | <Member_Card_Number>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Member_Card_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>         | <TotalRecords>             | <ATPStartTimestamp>          | <ATPEndTimestamp> |
      | POST           | <Member_Card_Number>  | <Member_ATP_TotalRecords>  | <Member_ATP_Start_timestamp> | <Member_ATP_End_timestamp> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                         |
      | <Member_Card_Number> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                           |
      | <Member_Card_Number>   |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>                           |
      | <Member_Card_Number>   |
    And I query Matrix DB Main table for point information
      | <Card_Number>                           |
      | <Member_Card_Number>   |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                           |
      | <Member_Card_Number>   |
    And I validate expected point information with Matrix DB

    Examples:
    | Member_Card_Number                          | Member_ATP_TotalRecords  | Member_ATP_Start_timestamp  | Member_ATP_End_timestamp  |
    | ATP_New_Customer_Sale_Member_Number_LoyalT  | ATP_TotalRecords_LoyalT  | ATP_Start_timestamp_LoyalT  | ATP_End_timestamp_LoyalT  |
    | ATP_New_Customer_Sale_Member_Number_Jade    | ATP_TotalRecords_Jade    | ATP_Start_timestamp_Jade    | ATP_End_timestamp_Jade    |
    | ATP_New_Customer_Sale_Member_Number_Ruby    | ATP_TotalRecords_Ruby    | ATP_Start_timestamp_Ruby    | ATP_End_timestamp_Ruby    |
    | ATP_New_Customer_Sale_Member_Number_Diamond | ATP_TotalRecords_Diamond | ATP_Start_timestamp_Diamond | ATP_End_timestamp_Diamond |

