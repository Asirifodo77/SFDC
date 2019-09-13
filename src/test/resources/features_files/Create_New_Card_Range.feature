Feature: Create new card range
  @New_Card_Range
  Scenario: create new card range in Salesforce
    Given If previous test data is deleted from Matrix
    Given I login to Salesforce to delete existing card range
    Given I navigate to create new card range tab
    Then I validate Minimum Value, Maximum value and delete the Existing card range record from Salesforce UI
    Given I navigate to create new card range tab
    Then I select create new card range option
    Given If create new card range popup is loaded correctly
    Then I select Store location
    Then I select Tier
    Then I enable Manually choose range checkbox
    Then I type MinimumValue
    Then I type Maximum value
    Then I type Remark
    Then I click on Next button
    Given If next new card range popup is loaded correctly
    Then I validate Tier label value
    Then I validate Minimum value label value
    Then I validate Maximum value label value
    Then I validate Store Location label value
    Then I validate Remark label value
    Then I click on Finish button
    Then I search record using card range name
    Then I click on card range name value and wait for the popup to be opened
    Then I validate Store Location value in table
    Then I validate Minimum Value in table
    Then I validate Maximum value in table
    Then I validate Tier value in table
    Then I check if Status is Enabled
    Then I validate Created Date value is equal to today
    Then I validate Remark value in table
    Then I query DB staging table and check if there is a record for given card range
    Given If record is processed I query Matrix table and check the card count