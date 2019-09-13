Feature: Create Member in SF using Expired Membership Card Number.
  @Scenario_Create_Member_SF_Expired_Membership_Card_Number
  Scenario: Create Member in SF using Expired Membership Card Number
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                       |
      | Member_SF_Expired_Membership_Card   |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                       | <SalutationEnglish> | <FirstName>               | <LastName>               | <Email>                | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Member_SF_Expired_Membership_Card    | Saluation_English   | Expired_Member_FirstName  | Expired_Member_LastName  | Expired_Member_Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    Given I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                     | <TotalRecords>                                  | <ATPStartTimestamp>                                | <ATPEndTimestamp>                                |
      | POST           | Member_SF_Expired_Membership_Card | Member_SF_Expired_Membership_Card_TotalRecords1 | Member_SF_Expired_Membership_Card_Start_timestamp1 | Member_SF_Expired_Membership_Card_End_timestamp1 |
    And I validate the success message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                     |
      | Member_SF_Expired_Membership_Card |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_Create_Member_SF_Expired_Membership_Card_Number
  Scenario: Validation of member in Salesforce and Matrix DB before running renewal job.
    And I query Salesforce DB to retrieve membership CycleID to run renewal job
      | <Card_Number>                      |
      | Member_SF_Expired_Membership_Card  |
    And I run renewal job on SF
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | Member_SF_Expired_Membership_Card  |
    And I validate the membership status is "Inactive" after running renewal job

  @Scenario_Create_Member_SF_Expired_Membership_Card_Number
  Scenario: Validation of member creation in Salesforce.
    Given I login to Salesforce
    When I create a new member on Salesforce to validate the message
      | <MemberNumber>                     | <SalutationEnglish> | <FirstName>               | <LastName>               | <Email>                | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation>     | <StaffNo> |
      | Member_SF_Expired_Membership_Card  | Saluation_English   | Expired_Member_FirstName  | Expired_Member_LastName  | Expired_Member_Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location      | Staff_No  |
    Then I validate the member creation message is "This membership card number has been registered by another member"

  @Scenario_Create_Member_SF_Expired_Membership_Card_Number
  Scenario: 58_I search member using search member POS for expired membership card
    Given I send a POST request to POS search member with multipal criteria FirstName "unique_FirstName54" LastName "unique_LastName54" EmailAddressText "unique_email54@gmail.com" ContactNumberText ""
    Then I read the details in POS search member response for card number "Member_SF_Expired_Membership_Card"
    Then I validate if POS search response card number is equal to "Member_SF_Expired_Membership_Card"
    Then I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                     |
      | Member_SF_Expired_Membership_Card |
    Then I read POS search member values from Salesfroce for card number "Member_SF_Expired_Membership_Card"
    Then I validate POS search Member values with Salesforce