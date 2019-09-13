Feature: Search member POS using more than one criteria at a time
  @Scenario_Search_Member_POS_With_Multiple_Criteria
  Scenario: Create a unique member with DSA create member API call
    Given I cleanup data for existing POS member for Card Number "POS_Search_Member_Using_Multipal_Criteria_Card_Number"
    Given I send a POST request to create POS member with TitleCode "Mr." GenderCode "M" EmailAddressText "uniqueemail@gmail.com" ContactNumberText "86707924" Mobile1AreaCode "65" FirstName "UniqueFirstName99" LastName "UniqueLastName99" CardNumberTag "POS_Search_Member_Using_Multipal_Criteria_Card_Number"
    Then I validate if Member created successfully in SF and Matrix for Card Number "POS_Search_Member_Using_Multipal_Criteria_Card_Number"

  @Scenario_Search_Member_POS_With_Multiple_Criteria
  Scenario: I search member using search member POS with multiple criteria
    Given I send a POST request to POS search member with multipal criteria FirstName "UniqueFirstName99" LastName "UniqueLastName99" EmailAddressText "uniqueemail@gmail.com" ContactNumberText "86707924"
    Then I read the details in POS search member response for card number "POS_Search_Member_Using_Multipal_Criteria_Card_Number"
    Then I validate if POS search response card number is equal to "POS_Search_Member_Using_Multipal_Criteria_Card_Number"
    Then I login to Salesforce
    And I search for the member with Card Number
      | <Card_Number>                                           |
      | POS_Search_Member_Using_Multipal_Criteria_Card_Number   |
    Then I read POS search member values from Salesfroce for card number "POS_Search_Member_Using_Multipal_Criteria_Card_Number"
    Then I validate POS search Member values with Salesforce