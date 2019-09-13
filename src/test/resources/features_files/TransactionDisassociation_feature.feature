Feature: Disassociate a Transaction to the created member
  @positiveScenario
  Scenario: Transaction Disassociation
    Given I go to the login page
    And I am on SFDC login page
    When I login to SFDC
      | <UserName>   | <Password> |
      | SFDCAdmin_UserName | SFDCAdmin_Pwd    |
    And I search for the transaction to fetch Division and TxnDate
      | <Transaction_Number> |
      | Txn_number           |
    And I search for the member through Card Number
      | <Card_Number>|
      | Card_Number  |
    And I Disassociate transaction with Card number
      |<Transaction_Number>|
      | Txn_number         |

