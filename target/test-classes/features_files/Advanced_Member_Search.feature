Feature: Advanced member search for existing members.
  @Scenario_advanced_member_search
  Scenario: Delete all test data in Salesforce and Matrix before creating a member on Salesforce.
    Given I delete test data in Salesforce and Matrix DB
      | <Card_Number>                              |
      | Advance_Member_search_Partial_Card_Number  |
    Given I login to Salesforce
    When I create a new member in SFDC
      | <MemberNumber>                            | <SalutationEnglish> | <FirstName>                             | <LastName>                             | <Email>                             | <Residence> | <PreferredLanguage> | <OptInForMarketingCommunication> | <Tier> | <CardPickupMethod>  | <EnrollmentLocation> | <StaffNo> | <FirstName_Native>                            | <LastName_Native>                            | <Mobile_Phone>                       |
      | Advance_Member_search_Partial_Card_Number | Saluation_English   | Advance_Member_search_Partial_FirstName | Advance_Member_search_Partial_LastName | Advance_Member_search_Partial_Email | Residence   | Preferred_Language  | Opt_In_For_Marketing             | Tier   | Card_Pick_up_Method | Enrollment_Location  | Staff_No  | Advance_Member_search_Partial_FirstNameNative | Advance_Member_search_Partial_LastNameNative | Advance_Member_search_Partial_Mobile |

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with First Name
    Given I login to Salesforce
    When I search for the member through "FirstName" as "TestAutomationSearch"
    Then I validate member "FirstName" as "TestAutomationSearch" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with Last Name.
    Given I login to Salesforce
    When I search for the member through "LastName" as "TestingSearch"
    Then I validate member "LastName" as "TestingSearch" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with Email.
    Given I login to Salesforce
    When I search for the member through "Email" as "abcjireow@gmail.com"
    Then I validate member "Email" as "abcjireow@gmail.com" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with FirstNameNative.
    Given I login to Salesforce
    When I search for the member through "FirstNameNative" as "FNNN"
    Then I validate member "FirstNameNative" as "FNNN" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with LastNameNative.
    Given I login to Salesforce
    When I search for the member through "LastNameNative" as "testingSearchNative"
    Then I validate member "LastNameNative" as "testingSearchNative" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with Mobile.
    Given I login to Salesforce
    When I search for the member through "Mobile" as "8423973205"
    Then I validate member "Mobile" as "8423973205" in basic information
    And I logout from Salesforce

  @Scenario_advanced_member_search
  Scenario: Search for existing member on Salesforce with First Name, Last Name, Native First Name, Native Last Name, Email and Mobile
    Given I login to Salesforce
    When I search for the member with all fields
      |<FirstName>|<LastName>|<FirstNameNative>|<LastNameNative>|<Email>|<Mobile>|
      |Advance_Member_search_Partial_FirstName |Advance_Member_search_Partial_LastName| Advance_Member_search_Partial_FirstNameNative|Advance_Member_search_Partial_LastNameNative|Advance_Member_search_Partial_Email| Advance_Member_search_Partial_Mobile|
    Then I validate member "FirstName" as "TestAutomationSearch" in basic information
    And I validate member "LastName" as "TestingSearch" in basic information
    And I validate member "Email" as "abcjireow@gmail.com" in basic information
    And I validate member "FirstNameNative" as "FNNN" in basic information
    And I validate member "LastNameNative" as "testingSearchNative" in basic information
    And I validate member "Mobile" as "8423973205" in basic information
