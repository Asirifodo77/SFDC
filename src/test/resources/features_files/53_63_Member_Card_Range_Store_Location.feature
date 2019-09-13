Feature: Create Member in SF using a Store Location which does not belong to the Card Range - Script no 63
  @Scenario_member_card_range_store_location
  Scenario: Create Member in SF using a Store Location which does not belong to the Card Range.
    Given I login to Salesforce
    When I create a new member on Salesforce to validate the message
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation>            | <StaffNo> |
      | Member_not_in_card_Range_card  | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location_Scottswalk  | Staff_No  |
    Then I validate the member creation message is "Card number is out of the valid card range for selected store location"

  @Scenario_member_card_range_store_location
  Scenario: Create Member in SF using Card Number Range defined with other location
    Given I login to Salesforce
    When I create a new member on Salesforce to validate the message
      | <MemberNumber>                 | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation>            | <StaffNo> |
      | Member_in_card_Range_card      | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location_Changi      | Staff_No  |
    Then I validate the member creation message is "Card number is out of the valid card range for selected store location"