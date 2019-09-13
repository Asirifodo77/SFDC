Feature: Existing Customer Refund validation
  @Scenario_ATP_Refund
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>        |
      | Refund_Member_Number |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Refund_Member_Number | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>        | <TotalRecords>       | <ATPStartTimestamp>     | <ATPEndTimestamp>     |
      | POST           | Refund_Member_Number | Refund_TotalRecords1 | Refund_Start_timestamp1 | Refund_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_Refund
  Scenario: Validation of member Salesforce and Matrix DB then post a refund transaction
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number> |
      | Refund_Member_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>        | <TotalRecords>       | <ATPStartTimestamp>     | <ATPEndTimestamp>     |
      | Refund         | Refund_Member_Number | Refund_TotalRecords2 | Refund_Start_timestamp2 | Refund_End_timestamp2 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request

  @Scenario_ATP_Refund
  Scenario: Validation of member and refunded transaction on Salesforce and Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I query Matrix DB Main table for point information
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>         |
      | Refund_Member_Number  |
    And I validate expected point information with Matrix DB

  @Scenario_ATP_Refund_All
  Scenario Outline: Validation of member created in SF and validating ATP Refund All
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>         | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <Refund_Member_Number> | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>          | <TotalRecords>         | <ATPStartTimestamp>       | <ATPEndTimestamp>       |
      | POST           | <Refund_Member_Number> | <Refund_TotalRecords1> | <Refund_Start_timestamp1> | <Refund_End_timestamp1> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I validate expected point information with Matrix DB
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>          | <TotalRecords>         | <ATPStartTimestamp>       | <ATPEndTimestamp>       |
      | Refund         | <Refund_Member_Number> | <Refund_TotalRecords2> | <Refund_Start_timestamp2> | <Refund_End_timestamp2> |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I read the membership Information from Salesforce UI
    And I query Matrix DB Main table for membership information
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>           |
      | <Refund_Member_Number>  |
    And I validate expected point information with Matrix DB

    Examples:
      | Refund_Member_Number         | Refund_TotalRecords1         | Refund_Start_timestamp1         | Refund_End_timestamp1         | Refund_TotalRecords2         | Refund_Start_timestamp2         | Refund_End_timestamp2         |
      | Refund_Member_Number_LoyalT  | Refund_TotalRecords1_LoyalT  | Refund_Start_timestamp1_LoyalT  | Refund_End_timestamp1_LoyalT  | Refund_TotalRecords2_LoyalT  | Refund_Start_timestamp2_LoyalT  | Refund_End_timestamp2_LoyalT  |
      | Refund_Member_Number_Jade    | Refund_TotalRecords1_Jade    | Refund_Start_timestamp1_Jade    | Refund_End_timestamp1_Jade    | Refund_TotalRecords2_Jade    | Refund_Start_timestamp2_Jade    | Refund_End_timestamp2_Jade    |
      | Refund_Member_Number_Ruby    | Refund_TotalRecords1_Ruby    | Refund_Start_timestamp1_Ruby    | Refund_End_timestamp1_Ruby    | Refund_TotalRecords2_Ruby    | Refund_Start_timestamp2_Ruby    | Refund_End_timestamp2_Ruby    |
      | Refund_Member_Number_Diamond | Refund_TotalRecords1_Diamond | Refund_Start_timestamp1_Diamond | Refund_End_timestamp1_Diamond | Refund_TotalRecords2_Diamond | Refund_Start_timestamp2_Diamond | Refund_End_timestamp2_Diamond |
