Feature: Auto Transaction Posting - Cancelled Membership Card Number
  @Scenario_ATP_CancelledMembershipCard_Member
  Scenario: Auto Transaction Posting - Cancelled Membership Card Number
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                             |
      | ATP_CancelledMembershipCard_Member_Card   |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                          | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | ATP_CancelledMembershipCard_Member_Card | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                           | <TotalRecords>                                   | <ATPStartTimestamp>                                 | <ATPEndTimestamp>                                 |
      | POST           | ATP_CancelledMembershipCard_Member_Card | ATP_CancelledMembershipCard_Member_TotalRecords1 | ATP_CancelledMembershipCard_Member_Start_timestamp1 | ATP_CancelledMembershipCard_Member_End_timestamp1 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                             |
      | ATP_CancelledMembershipCard_Member_Card   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_ATP_CancelledMembershipCard_Member
  Scenario: Auto Transaction Posting - Cancelled Membership Card Number
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                             |
      | ATP_CancelledMembershipCard_Member_Card   |
    When I cancel the member card
    Then I validate the card "Cancel" confirmation message
    And I validate card status is "Cancelled"
    And I validate card status is "Cancelled" under Membership Cards tab

  @Scenario_ATP_CancelledMembershipCard_Member
  Scenario: Auto Transaction Posting - Cancelled Membership Card Number
    Given I query Matrix Card table with Card Number for MemberID
      | <Card_Number>                           |
      | ATP_CancelledMembershipCard_Member_Card |
    Then I query matrix DB stagging table with MemberID and wait for it to be processed
    And I validate card status is "Cancelled" in Main database
      | <Card_Number>                           |
      | ATP_CancelledMembershipCard_Member_Card |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                           | <TotalRecords>                                   | <ATPStartTimestamp>                                 | <ATPEndTimestamp>                                 |
      | POST           | ATP_CancelledMembershipCard_Member_Card | ATP_CancelledMembershipCard_Member_TotalRecords2 | ATP_CancelledMembershipCard_Member_Start_timestamp2 | ATP_CancelledMembershipCard_Member_End_timestamp2 |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                             |
      | ATP_CancelledMembershipCard_Member_Card   |

  @Scenario_ATP_CancelledMembershipCard_Member
  Scenario: Auto Transaction Posting - Cancelled Membership Card Number
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                             |
      | ATP_CancelledMembershipCard_Member_Card   |
    And I validate transaction count is "1" in the transactions table in SF
    And I validate Custom logs for an error under ATP failure as "No active card" for card number "ATP_CancelledMembershipCard_Member_Card"

