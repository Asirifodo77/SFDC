Feature: Member cycle Extension
  @Member_Cycle_Extension
  Scenario: Create member and associate a transaction
    #=========Associating the transaction with card number first ================
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                                               |
      | Membership_Cycle_Extension_CardNumber                       |
    Given I login to Salesforce
    When I create a new member on Salesforce
      | <MemberNumber>                                            | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | Membership_Cycle_Extension_CardNumber                     | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                                              |
      | Membership_Cycle_Extension_CardNumber                      |
    And I Associate transaction with Card number
      | <Transaction_Number>                                          | <Division>                                            | <Transaction_Date>                                            |
      | Membership_Cycle_Extension_Transaction                        | Member_Transaction_Association_Disassociation_Division| Membership_Cycle_Extension_Date                              |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                                               |
      | Membership_Cycle_Extension_CardNumber                       |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    #==================== End of association ====================================
    #==================== Start of Member cycle Extension =======================
  @Member_Cycle_Extension
  Scenario: Extend the existing member cycle
    Given I have a valid member with associated transaction
    Then I login to Salesforce to Extend membership cycle
    Then I search member using card number to Extend membership cycle
      | <Card_Number>|
      | Card_Number  |
    Then I navigate to member cycle extension section
    Then I verify if Manual cycle extension popup is loaded successfully
    Then I select value for Cycle end year dropdown
    Then I select value for Cycle end month dropdown
    Then I set value for Remarks textarea
    Then I select value for Reason dropdown
    Then I click Next button
    Then I verify New cycle end date value in the popup before finishing
    Then I verify Reason value in the popup before finishing
    Then I verify Remarks value in the popup before finishing
    Then I click Finish button
    Then I approve all "Manual Extension Request" requests
    Then I search member using card number to Extend membership cycle
      | <Card_Number>|
      | Card_Number  |
    Then I click on Member Cycle tab
    Then I validate if cycle end date value in Saleforce is updated successfully
    Then I validate if cycle end date value in Matrix is updated successfully
    #==================== End of Member cycle Extension =======================