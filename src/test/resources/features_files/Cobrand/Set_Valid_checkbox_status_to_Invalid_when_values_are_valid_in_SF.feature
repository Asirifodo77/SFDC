Feature: Set valid checkbox status to invalid while textbox values are still valid in SF
  @Set_Valid_Checkbox_Status_to_Invalid_When_Contact_Details_Are_Valid
  Scenario: Set and validate the status of Valid Checkboxes When Contact Details Are Valid
    Given I delete existing data for "DSA" create member with json request body in "DSA_Member_IsValid_Checkbox_Update_FilePath"
    Then I create "DSA" member with json request body in "DSA_Member_IsValid_Checkbox_Update_FilePath"
    Then I validate the memberID in salesforce and Matrix
    Then I login to Salesforce
    Then I search for DSA member with card number
      | <Card_Number>|
      | Card_Number  |

    #========== Mobile Phone ======================
    Then I validate if Mobile Phone is empty in SF
    Then I validate if Dialing Code-Mobile Phone is empty in SF
    Then I validate if Valid Mobile Phone checkbox is unchecked
    Then I update the Dialing Code-Mobile Phone field to "65 (Singapore)" in SF
    Then I update Mobile Phone field to "87654321" in SF

    #========= Other Phone 1 =============================
    Then I validate if OtherPhone1 is empty in SF
    Then I validate if Dialing Code-Other Phone 1 is empty in SF
    Then I validate if Valid Other Phone 1 checkbox is unchecked
    Then I update Dialing Code-Other Phone 1 field to "65 (Singapore)" in SF
    Then I update the Other Phone 1 field to "12345678" in SF

    #========= Other Phone 2 =============================
    Then I validate if Other Phone 2 is empty in SF
    Then I validate if Dialing Code-Other Phone 2 is empty in SF
    Then I validate if Valid Other Phone 2 checkbox is unchecked
    Then I update if Dialing Code-Other Phone 2 field to "65 (Singapore)" in SF
    Then I update the Other Phone 2 field to "11223344" in SF

    #======== Email Address =====================
    Then I validate if email address is empty in SF
    Then I validate if valid email address checkbox is unchecked
    Then I update the email address field to "updatedemail@gmail.com" in SF

    Then I click save button in SF
    Given I validate if member profile is updated in CMDlogs
    Then I query Matrix

    #======== Email Address =====================
    Then I validate if Email is updated to "updatedemail@gmail.com" in Matrix
    And I validate if IsInvalidEmail is updated to "0" in Matrix

    #========== Mobile Phone ======================
    Then I validate if MobileNo is updated to "87654321" in Matrix
    And I validate if ValidMobileNo1 is updated to "1" in Matrix
    And I validate if MobileAreaCode is updated to "65" in Matrix

    #========= Other Phone 1 =============================
    Then I validate if MobileNo2 is updated to "12345678" in Matrix
    Then I validate if IsValidMobile2 is updated to "1" in Matrix
    Then I validate if MobileNoArea2 is updated to "65" in Matrix

    #========= Other Phone 2 =============================
    Then I validate if HomeNo is updated to "11223344" in Matrix
    Then I validate if ValidHomeNo is updated to "1" in Matrix
    Then I validate if HomePhoneAreaCode is updated to "65" in Matrix

    #================= Scenario  9 =================
    Then I login to Salesforce
    Then I search for DSA member with card number
      | <Card_Number>|
      | Card_Number  |

    # ======= Email Address =================
    Then I validate if Email is updated to "updatedemail@gmail.com" in SF
    Then I validate if Valid Email Address checkbox is checked
    Then I update the email address field to "updatedemail9@gmail.com" in SF
    Then I update Valid Email Address checkbox to "false"

    #====== Mobile Phone ====================
    Then I validate if MobileNo is updated to "87654321" in SF
    Then I validate if Valid Mobile Phone checkbox is checked
    Then I update Mobile Phone field to "99654321" in SF
    Then I update Valid Mobile Phone checkbox to "false"

    #======= Other Phone 1 ===================
    Then I validate if Other Phone 1 is updated to "12345678" in SF
    Then I validate if Valid Other Phone 1 checkbox is checked
    Then I update the Other Phone 1 field to "99345678" in SF
    Then I update Valid Other Phone 1 checkbox to "false"

    #======= Other Phone 2 ===================
    Then I validate if Other Phone 2 is updated to "11223344" in SF
    Then I validate if Valid Other Phone 2 checkbox is checked
    Then I update the Other Phone 2 field to "99223344" in SF
    Then I update Valid Other Phone 2 checkbox to "false"

    Then I click save button in SF
    Given I validate if member profile is updated in CMDlogs
    Then I query Matrix

    #======== Validation in matrix=====================
    # Email Address
    Then I validate if Email is updated to "updatedemail9@gmail.com" in Matrix
    And I validate if IsInvalidEmail is updated to "1" in Matrix

    # Mobile Phone
    Then I validate if MobileNo is updated to "99654321" in Matrix
    And I validate if ValidMobileNo1 is updated to "0" in Matrix

    # Other Phone 1
    Then I validate if MobileNo2 is updated to "99345678" in Matrix
    Then I validate if IsValidMobile2 is updated to "0" in Matrix

    # Other Phone 2
    Then I validate if HomeNo is updated to "99223344" in Matrix
    Then I validate if ValidHomeNo is updated to "0" in Matrix

    #========== End of scenario 9 ==================