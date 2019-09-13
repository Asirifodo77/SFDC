Feature: Member Card Management in Salesforce – Merge Cards
  @Scenario_member_merge_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member which is in the system.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                      |
      | MemberMergeCardNumberCardKept      |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>                   | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberMergeCardNumberCardKept    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                  |
      | MemberMergeCardNumberCardKept  |
    And I Associate transaction with Card number
      | <Transaction_Number>                         | <Division>                                             | <Transaction_Date>                             |
      | MemberMergeCardNumberCardKept_TransactionID  | Member_Transaction_Association_Disassociation_Division | MemberMergeCardNumberCardKept_TransactionDate  |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                   |
      | MemberMergeCardNumberCardKept   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_merge_card_management
  Scenario: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member which is merged and cancelled from the system.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                        |
      | MemberMergeCardNumberCardCancel      |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>                       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | MemberMergeCardNumberCardCancel      | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                    |
      | MemberMergeCardNumberCardCancel  |
    And I Associate transaction with Card number
      | <Transaction_Number>                           | <Division>                                             | <Transaction_Date>                               |
      | MemberMergeCardNumberCardCancel_TransactionID  | Member_Transaction_Association_Disassociation_Division | MemberMergeCardNumberCardCancel_TransactionDate  |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                     |
      | MemberMergeCardNumberCardCancel   |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed

  @Scenario_member_merge_card_management
  Scenario: Validation of member with merged on Salesforce and Matrix DB.
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | MemberMergeCardNumberCardCancel      |
    And I read the membership points balance
      | <Card_Number>                        |
      | MemberMergeCardNumberCardCancel      |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberMergeCardNumberCardKept      |
    And I read the membership points balance
      | <Card_Number>                      |
      | MemberMergeCardNumberCardKept      |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberMergeCardNumberCardKept      |
    When I merge the member card
      | <Card_Number>                      |
      | MemberMergeCardNumberCardCancel    |
    Then I validate the card "Merge" confirmation message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                        |
      | MemberMergeCardNumberCardKept        |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | MemberMergeCardNumberCardCancel      |
    And I read the membership Information from Salesforce UI for the cancelled card
    And I query Matrix DB Main table for membership information
      | <Card_Number>                    |
      | MemberMergeCardNumberCardCancel  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                    |
      | MemberMergeCardNumberCardCancel  |
    And I query Matrix DB Transact table for transaction information
      | <Card_Number>                    |
      | MemberMergeCardNumberCardCancel  |
    And I validate the "Membership Status" is "Merged"
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                     |
      | MemberMergeCardNumberCardCancel   |
    And I validate expected point information with Matrix DB
    And I validate expected transaction information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | MemberMergeCardNumberCardKept      |
    And I read the membership Information from Salesforce UI
    And I validate the purchase history tab
    And I query Matrix DB Main table for membership information
      | <Card_Number>                  |
      | MemberMergeCardNumberCardKept  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                  |
      | MemberMergeCardNumberCardKept  |
    And I query Matrix DB Transact table for transaction information
      | <Card_Number>                  |
      | MemberMergeCardNumberCardKept  |
    And I validate the "Membership Status" is "Active"
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                   |
      | MemberMergeCardNumberCardKept   |
    And I validate expected point information with Matrix DB after merging
      | <Card_Number_Kept>              | <Card_Number_Cancel>              |
      | MemberMergeCardNumberCardKept   | MemberMergeCardNumberCardCancel   |
    And I validate all transactions added to Matrix DB Transact table
      | <Card_Number>                   |
      | MemberMergeCardNumberCardKept   |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | MemberMergeCardNumberCardCancel      |
    When I click "Merge Card Request" on the Member Request tab and "Merge" the request
    Then I validate the request details in Salesforce UI
      | <Card_Number_Kept>              | <Card_Number_Cancel>              |
      | MemberMergeCardNumberCardKept   | MemberMergeCardNumberCardCancel   |


  @Scenario_member_merge_card_management_All
  Scenario Outline: Delete member on Salesforce and Matrix DB before creating member on Salesforce and posting transaction notification to the member which is in the system.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardKept>    |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>                     | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberMergeCardNumberCardKept>    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                    |
      | <MemberMergeCardNumberCardKept>  |
    And I Associate transaction with Card number
      | <Transaction_Number>   | <Division>                                 | <Transaction_Date> |
      | <TransactionID_Kept>        | <Association_Disassociation_Division> | <TransactionDate_Kept>  |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                   |
      | <MemberMergeCardNumberCardKept> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardCancel>    |
    Given I login to Salesforce with CRM user
    When I create a new member on Salesforce
      | <MemberNumber>                       | <SalutationEnglish> | <FirstName> | <LastName> | <Email> | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> |
      | <MemberMergeCardNumberCardCancel>    | Saluation_English   | First_name  | Last_name  | Email   | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  |
    And I search for the member with Card Number
      | <Card_Number>                    |
      | <MemberMergeCardNumberCardCancel>|
    And I Associate transaction with Card number
      | <Transaction_Number>   | <Division>                                   | <Transaction_Date>        |
      | <TransactionID_Cancel>        | <Association_Disassociation_Division> | <TransactionDate_Cancel>  |
    And Transaction Association message should be "Data was successfully saved"
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                     |
      | <MemberMergeCardNumberCardCancel> |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardCancel>    |
    And I read the membership points balance
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardCancel>    |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardKept>      |
    And I read the membership points balance
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardKept>      |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardKept>      |
    When I merge the member card
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardCancel>  |
    Then I validate the card "Merge" confirmation message
    And I query Salesforce DB to retrieve membership CycleID
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardKept>      |
    And I query Matrix DB Stagging table with CycleID and wait for it to be processed for "2" request
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                          |
      | <MemberMergeCardNumberCardCancel>      |
    And I read the membership Information from Salesforce UI for the cancelled card
    And I query Matrix DB Main table for membership information
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardCancel>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardCancel>  |
    And I query Matrix DB Transact table for transaction information
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardCancel>  |
    And I validate the "Membership Status" is "Merged"
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                     |
      | <MemberMergeCardNumberCardCancel> |
    And I validate expected point information with Matrix DB
    And I validate expected transaction information with Matrix DB
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardKept>    |
    And I read the membership Information from Salesforce UI
    And I validate the purchase history tab
    And I query Matrix DB Main table for membership information
      | <Card_Number>                    |
      | <MemberMergeCardNumberCardKept>  |
    And I query Matrix DB Main table for point information
      | <Card_Number>                    |
      | <MemberMergeCardNumberCardKept>  |
    And I query Matrix DB Transact table for transaction information
      | <Card_Number>                      |
      | <MemberMergeCardNumberCardKept>    |
    And I validate the "Membership Status" is "Active"
    And I validate expected membership information with Salesforce UI
    And I validate expected membership information with Matrix DB
      | <Card_Number>                     |
      | <MemberMergeCardNumberCardKept>   |
    And I validate expected point information with Matrix DB after merging
      | <Card_Number_Kept>                | <Card_Number_Cancel>              |
      | <MemberMergeCardNumberCardKept>   | <MemberMergeCardNumberCardCancel> |
    And I validate all transactions added to Matrix DB Transact table
      | <Card_Number>                     |
      | <MemberMergeCardNumberCardKept>   |
    Given I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                        |
      | <MemberMergeCardNumberCardCancel>    |
    When I click "Merge Card Request" on the Member Request tab and "Merge" the request
    Then I validate the request details in Salesforce UI
      | <Card_Number_Kept>              | <Card_Number_Cancel>              |
      | <MemberMergeCardNumberCardKept> | <MemberMergeCardNumberCardCancel> |

    Examples:
      | MemberMergeCardNumberCardKept         | MemberMergeCardNumberCardCancel         | Association_Disassociation_Division                    | TransactionDate_Kept                                    | TransactionID_Kept                                    |TransactionDate_Cancel                                  | TransactionID_Cancel                                  |
      | MemberMergeCardNumberCardKept_Diamond | MemberMergeCardNumberCardCancel_Diamond | Member_Transaction_Association_Disassociation_Division | MemberMergeCardNumberCardKept_TransactionDate_Diamond   | MemberMergeCardNumberCardKept_TransactionID_Diamond   |MemberMergeCardNumberCardCancel_TransactionDate_Diamond | MemberMergeCardNumberCardCancel_TransactionID_Diamond |
      | MemberMergeCardNumberCardKept_Ruby    | MemberMergeCardNumberCardCancel_Ruby    | Member_Transaction_Association_Disassociation_Division | MemberMergeCardNumberCardKept_TransactionDate_Ruby      | MemberMergeCardNumberCardKept_TransactionID_Ruby      |MemberMergeCardNumberCardCancel_TransactionDate_Ruby    | MemberMergeCardNumberCardCancel_TransactionID_Ruby    |