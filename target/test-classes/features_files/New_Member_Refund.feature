Feature: New Member Refund
  @Scenario_new_member_refund
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                   |
      | New_Member_Refund_Card_Number   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | New_Member_Refund_Card_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                 | <TotalRecords>                  | <ATPStartTimestamp>                | <ATPEndTimestamp>                |
      | POST           | New_Member_Refund_Card_Number | New_Member_Refund_TotalRecords1 | New_Member_Refund_Start_timestamp1 | New_Member_Refund_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                   |
      | New_Member_Refund_Card_Number   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_new_member_refund
  Scenario: Validation of member Salesforce and Matrix DB then post a sale and refund transaction notification
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                   |
      | New_Member_Refund_Card_Number   |
    And I read the membership Information for new Member Refund
    And I query Matrix DB Main table for membership information
      | <Card_Number>                   |
      | New_Member_Refund_Card_Number   |
    And I query Matrix DB Main table for point information
      | <Card_Number>                   |
      | New_Member_Refund_Card_Number   |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                 |
      | New_Member_Refund_Card_Number  |
    And I validate expected point information with Matrix DB

  @Scenario_new_member_refund_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member and Matrix DB validations
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                     |
      | <New_Member_Refund_Card_Number>   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                  | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <New_Member_Refund_Card_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                   | <TotalRecords>                    | <ATPStartTimestamp>                  | <ATPEndTimestamp>                  |
      | POST           | <New_Member_Refund_Card_Number> | <New_Member_Refund_TotalRecords1> | <New_Member_Refund_Start_timestamp1> | <New_Member_Refund_End_timestamp1> |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                     |
      | <New_Member_Refund_Card_Number>   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                     |
      | <New_Member_Refund_Card_Number>   |
    And I read the membership Information for new Member Refund
    And I query Matrix DB Main table for membership information
      | <Card_Number>                     |
      | <New_Member_Refund_Card_Number>   |
    And I query Matrix DB Main table for point information
      | <Card_Number>                     |
      | <New_Member_Refund_Card_Number>   |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                    |
      | <New_Member_Refund_Card_Number>  |
    And I validate expected point information with Matrix DB

    Examples:
      | New_Member_Refund_Card_Number         | New_Member_Refund_TotalRecords1         | New_Member_Refund_Start_timestamp1         | New_Member_Refund_End_timestamp1         |
      | New_Member_Refund_Card_Number_LoyalT  | New_Member_Refund_TotalRecords1_LoyalT  | New_Member_Refund_Start_timestamp1_LoyalT  | New_Member_Refund_End_timestamp1_LoyalT  |
      | New_Member_Refund_Card_Number_Jade    | New_Member_Refund_TotalRecords1_Jade    | New_Member_Refund_Start_timestamp1_Jade    | New_Member_Refund_End_timestamp1_Jade    |
      | New_Member_Refund_Card_Number_Ruby    | New_Member_Refund_TotalRecords1_Ruby    | New_Member_Refund_Start_timestamp1_Ruby    | New_Member_Refund_End_timestamp1_Ruby    |
      | New_Member_Refund_Card_Number_Diamond | New_Member_Refund_TotalRecords1_Diamond | New_Member_Refund_Start_timestamp1_Diamond | New_Member_Refund_End_timestamp1_Diamond |