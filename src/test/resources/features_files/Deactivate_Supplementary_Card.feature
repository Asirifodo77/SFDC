Feature: Deactivate Supplementary Card
  @Deactivate_Supplementary_Card
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                            |
      | Deactivate_Supplementary_Card_CardNumber |
    And I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                           | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Deactivate_Supplementary_Card_CardNumber | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    Then I validate memberID in Salesforce and Matrix
      | <Card_Number>                            |
      | Deactivate_Supplementary_Card_CardNumber |
    And I Login to workbench to perform Auto Transaction posting
      | <Notification> | <Card_Number>                            | <TotalRecords>                             | <ATPStartTimestamp>                           | <ATPEndTimestamp>                           |
      | POST           | Deactivate_Supplementary_Card_CardNumber | Deactivate_Supplementary_Card_TotalRecords | Deactivate_Supplementary_Card_Start_timestamp | Deactivate_Supplementary_Card_End_timestamp |
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                            |
      | Deactivate_Supplementary_Card_CardNumber |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    #==================== End of association ====================================
  @Deactivate_Supplementary_Card
  Scenario: Validate supplementary card and deactivating it
    Given I have a valid member with associated transaction and with a supplementary card
    Then I login to SF to deactivate the supplementary card
    Then I search member using card number to deactivate the supplementary card
      | <Card_Number>|
      | Card_Number  |
    Then I navigate to memership card tab
    Given I validate if supplementary card is present
    Then  I navigate to cancel card section
    Then I deactivate the "Supplementary Card" with reason "Others" and remark "Cancelled by SFDC Test Automation Script"
    Then I search member using card number to deactivate the supplementary card
      | <Card_Number>|
      | Card_Number  |
    Then I navigate to membership card tab again
    Then I validate the status of Supplementary card to be "Cancelled" in SF
    Then I validate status in Matrix is "Cancelled"



