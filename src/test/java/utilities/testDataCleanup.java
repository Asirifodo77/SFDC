package utilities;

import cucumber.api.Scenario;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class testDataCleanup {
   // public String cardNumber ="520000561203";     // Original = 520000561215  // Don't ever use this *** 520000561201
    public String environment =System.getProperty("Environment");
    public String accessToken;
    public String memberID;
    public String JobID;
    public List<String> transactionID = new ArrayList<>();
    public List<String> transactionLineItems = new ArrayList<>();
    public List<String> transactionNumber = new ArrayList<>();
    public List<String> requestID = new ArrayList<>();


    public void cleanUpData(Scenario _scenario) throws Exception {
        //getting the access token.
        DataCleanup dc = new DataCleanup(_scenario);

        try {
            accessToken = dc.getSFaccessToken().trim();
         } catch (ConnectException e) {
            System.out.println("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
        } catch (Exception e){
            System.out.println("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
        }

        //printing out the accessToken
        System.out.println("Access Token :" + "\n" + accessToken);

        //reading the test data file
        readDataForDataCleanup data = new readDataForDataCleanup();
        List<String> cardNumbers;
        cardNumbers = data.getCardNumbersToBeDeleted();

        for (int i = 0; i < cardNumbers.size(); i++) {
            System.out.println("##########################  RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i) + " ########################");

            _scenario.write(" RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i));
            //getting member ID by passing the card number and access token
            memberID = dc.getMemberID(cardNumbers.get(i), accessToken);

            // get Transaction Number from salesforce by passing on the MemberId (ContactId).
            transactionNumber = dc.getTransactionNumber(memberID, accessToken);

            //printing out the memberID
            System.out.println(memberID);

            //get Transaction ID by passing the Member ID(contactId) to SF
            transactionID = dc.getTransactionID(memberID, accessToken);

            // get "Transaction Line Item Id" by passing "Transaction ID" in a loop from SF.
            transactionLineItems = dc.getTransactionLineItemID(transactionID, accessToken);

            //requestId (Get this)
            requestID = dc.getRequestID(cardNumbers.get(i),accessToken);


            //---------------deleting in SF-------------------

            //Delete "Request" record from SF
            dc.deleteRequest(requestID,accessToken);

            //Delete "transaction Line Item" record in SF
            dc.deleteTransactionLineItem(transactionLineItems, accessToken);

            //Delete "Transaction item" record from SF
            dc.deleteTransactionItem(transactionID, accessToken);

            //Delete "Member" record from SF
            dc.deleteMember(memberID, accessToken);


            //----------------deleting in staging ------------

            //check and delete the "Sales Line" record in staging
            dc.deleteSalesLineInStaging(transactionNumber, environment);

            //check and Delete "Sales Header" record in staging
            dc.deleteSalesHeaderInStaging(transactionNumber, environment);

            //check and Delete "Sales Payment" record in staging
            dc.deleteSalesPaymentInStaging(transactionNumber, environment);

            // ---------------deleting in main table (using stored procedure) ----------
            dc.deleteDataInMatrix(cardNumbers.get(i), environment);
        }
    }

    public void cleanUpOneMemberCardData(Scenario _scenario, String cardNum) throws Exception {
        //getting the access token.
        DataCleanup dc = new DataCleanup(_scenario);
        try {
            accessToken = dc.getSFaccessToken().trim();
        } catch (ConnectException e) {
            System.out.println("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! unable to generate access token due connection error. Actual message - "+e.getMessage());
        } catch (Exception e){
            System.out.println("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! unable to generate access token due an exception. Actual message - "+e.getMessage());
        }
        //printing out the accessToken
        System.out.println("Access Token :" + "\n" + accessToken);
        List<String> cardNumbers = new ArrayList<>();
        cardNumbers.add(cardNum);

        for (int i = 0; i < cardNumbers.size(); i++) {
            System.out.println("##########################  RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i) + " ########################");

            _scenario.write(" RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i));
            //getting member ID by passing the card number and access token
            memberID = dc.getMemberID(cardNumbers.get(i), accessToken);

            // get Transaction Number from salesforce by passing on the MemberId (ContactId).
            transactionNumber = dc.getTransactionNumber(memberID, accessToken);

            //printing out the memberID
            System.out.println(memberID);

            //get Transaction ID by passing the Member ID(contactId) to SF
            transactionID = dc.getTransactionID(memberID, accessToken);

            // get "Transaction Line Item Id" by passing "Transaction ID" in a loop from SF.
            transactionLineItems = dc.getTransactionLineItemID(transactionID, accessToken);

            //requestId (Get this)
            requestID = dc.getRequestID(cardNumbers.get(i),accessToken);

            //---------------deleting in SF-------------------
            //Delete "Request" record from SF
            dc.deleteRequest(requestID,accessToken);

            //Delete "transaction Line Item" record in SF
            dc.deleteTransactionLineItem(transactionLineItems, accessToken);

            //Delete "Transaction item" record from SF
            dc.deleteTransactionItem(transactionID, accessToken);

            //Delete "Member" record from SF
            dc.deleteMember(memberID, accessToken);

            //----------------deleting in staging ------------

            //check and delete the "Sales Line" record in staging
            dc.deleteSalesLineInStaging(transactionNumber, environment);

            //check and Delete "Sales Header" record in staging
            dc.deleteSalesHeaderInStaging(transactionNumber, environment);

            //check and Delete "Sales Payment" record in staging
            dc.deleteSalesPaymentInStaging(transactionNumber, environment);

            // ---------------deleting in main table (using stored procedure) ----------
            dc.deleteDataInMatrix(cardNumbers.get(i), environment);
        }
    }

    public void cleanupCobrandMemberData(Scenario _scenario, String cardNum, String FileRef) throws Exception {
        //getting the access token.
        DataCleanup dc = new DataCleanup(_scenario);
        accessToken = dc.getSFaccessToken().trim();
        //printing out the accessToken
        System.out.println("Access Token :" + "\n" + accessToken);
        List<String> cardNumbers = new ArrayList<>();
        cardNumbers.add(cardNum);

        for (int i = 0; i < cardNumbers.size(); i++) {
            System.out.println("##########################  RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i) + " ########################");

            _scenario.write(" RUNNING THE DATA CLEANUP FOR CARD NUMBER - " + cardNumbers.get(i));
            //getting member ID by passing the card number and access token
            memberID = dc.getMemberID(cardNumbers.get(i), accessToken);

            // get Transaction Number from salesforce by passing on the MemberId (ContactId).
            transactionNumber = dc.getTransactionNumber(memberID, accessToken);

            //printing out the memberID
            System.out.println(memberID);

            //get Transaction ID by passing the Member ID(contactId) to SF
            transactionID = dc.getTransactionID(memberID, accessToken);

            // get "Transaction Line Item Id" by passing "Transaction ID" in a loop from SF.
            transactionLineItems = dc.getTransactionLineItemID(transactionID, accessToken);

            //requestId (Get this)
            requestID = dc.getRequestID(cardNumbers.get(i), accessToken);

            //get "JobID" from Job object by passing the 'FileRef' inside the Cobrand create member Json
            JobID = dc.getJobID(FileRef,accessToken);

            //---------------deleting in SF-------------------
            //Delete "Request" record from SF
            dc.deleteRequest(requestID, accessToken);

            //Delete "transaction Line Item" record in SF
            dc.deleteTransactionLineItem(transactionLineItems, accessToken);

            //Delete "Transaction item" record from SF
            dc.deleteTransactionItem(transactionID, accessToken);

            //Delete "Member" record from SF
            dc.deleteMember(memberID, accessToken);

            //Delete "Job" record from SF
            dc.deleteJob(JobID,accessToken);
            //----------------deleting in staging ------------

            //check and delete the "Sales Line" record in staging
            dc.deleteSalesLineInStaging(transactionNumber, environment);

            //check and Delete "Sales Header" record in staging
            dc.deleteSalesHeaderInStaging(transactionNumber, environment);

            //check and Delete "Sales Payment" record in staging
            dc.deleteSalesPaymentInStaging(transactionNumber, environment);

            // ---------------deleting in main table (using stored procedure) ----------
            dc.deleteDataInMatrix(cardNumbers.get(i), environment);
        }
    }

}
