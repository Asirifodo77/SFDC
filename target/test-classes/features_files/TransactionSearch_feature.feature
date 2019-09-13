Feature: Search for a transaction
  @positiveScenario
  Scenario: Search for a transaction
    Given I go to the login page
    And I am on SFDC login page
    When I login to SFDC
      | <UserName>   | <Password> |
      | SFDCAdmin_UserName | SFDCAdmin_Pwd    |
    And I search for the transaction to fetch Division and TxnDate
      | <Transaction_Number> |
      | Txn_number           |

