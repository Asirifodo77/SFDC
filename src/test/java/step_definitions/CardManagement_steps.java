package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import page_objects.CardValidation_pageobjects;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.MemberValidation_pageobjects;
import property.Property;
import utilities.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManagement_steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    private String validateMessage_Text;
    private Map<String, String> cancelDataMap;
    public DSAcreateMember_pageObjects matrix;
    private String environment;
    public HashMap<String, String> matrixData;
    public String memberID;
    public Map<String, String> replaceCardDataMap;
    public int supplementary = 0;
    public String supplementary_Card = null;
    public Map<String, String> suspended_BlackListCardDataMap;
    public String blackListOrResumeMessage;
    public Map<String, String> blackListCardDataMap;
    private Map<String, String> mergeCardDataMap;
    public String cardNumber;
    public DSAcreateMember_pageObjects etr;
    public String RequestBody;
    public validateAPIreturnMessage API;
    public String APIReturnMessage;
    public getDataFromSF sfdata;
    public getSFaccessToken accessToken;
    public DSAcreateMember_pageObjects pos;
    public MemberValidation_pageobjects sf;
    public TakeScreenshot screenshot;
    public SoftAssert softAssert;
    public validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;
    public testDataCleanup dataCleanup;

    public CardManagement_steps() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        etr = new DSAcreateMember_pageObjects(_scenario);
        API = new validateAPIreturnMessage();
        environment = System.getProperty("Environment");
        sfdata = new getDataFromSF();
        accessToken = new getSFaccessToken();
        pos = new DSAcreateMember_pageObjects(_scenario);
        sf = new MemberValidation_pageobjects(driver, _scenario);
        screenshot = new TakeScreenshot(driver, _scenario);
        softAssert = new SoftAssert();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);
        dataCleanup = new testDataCleanup();
    }

    @When("^I cancel the member card number$")
    public void iCancelTheMemberCardNumber() throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        cancelDataMap = cardValidation_pageobjects.cancelCardManagement();
    }

    @Then("^I validate card status is \"(.*)\"$")
    public void iValidateCardStatusIs(String expectedStatus) throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        String actualCardStatus = cardValidation_pageobjects.getCardStatus(expectedStatus);
        Assert.assertEquals(expectedStatus, actualCardStatus);
    }

    @Then("^I validate the card \"([^\"]*)\" confirmation message$")
    public void iValidateTheCardConfirmationMessage(String key) throws Throwable {
        if (key.equalsIgnoreCase("Cancel")) {
            String expectedOutput = "Membership Card " + cancelDataMap.get("MembershipCardNumber") + " was cancelled successfully.";
            Assert.assertEquals(expectedOutput, cancelDataMap.get("CancelMessage"));
        } else if (key.equalsIgnoreCase("Replace")) {
            String expectedOutput = "Membership Card " + replaceCardDataMap.get("ReplaceOLD_Card") + " was replaced on " + replaceCardDataMap.get("ReplaceNEW_Card") + " successfully.";
            Assert.assertEquals(expectedOutput, replaceCardDataMap.get("ReplaceMessage"));
        } else if (key.equalsIgnoreCase("Suspend_Blacklist")) {
            String expectedOutput = "Request for suspend membership card was created successfully.";
            Assert.assertEquals(expectedOutput, suspended_BlackListCardDataMap.get("SuspendMessage"));
        } else if (key.equalsIgnoreCase("Blacklisted")) {
            String expectedOutput = "Member was blacklisted successfully.";
            Assert.assertEquals(expectedOutput, blackListOrResumeMessage);
        } else if (key.equalsIgnoreCase("Resume")) {
            String expectedOutput = "Member was resumed successfully.";
            Assert.assertEquals(expectedOutput, blackListOrResumeMessage);
        } else if (key.equalsIgnoreCase("Blacklist")) {
            String expectedOutput = "Membership Card " + blackListCardDataMap.get("MembershipCardNumber") + " was blacklisted successfully.";
            Assert.assertEquals(expectedOutput, blackListCardDataMap.get("BlacklistMessage"));
        } else if (key.equalsIgnoreCase("Merge")) {
            String expectedOutput = "Membership Card was merged successfully.";
            Assert.assertEquals(expectedOutput, mergeCardDataMap.get("MergeMessage"));
        }
    }

    @And("^I validate card status is \"(.*)\" in the membership cards table$")
    public void iValidateCardStatusIsInTheMembershipCardsTable(String expectedOutput) throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        List<String> memberCardStatusList = cardValidation_pageobjects.getMembershipCardTableStatus();
        for (String cardStatus : memberCardStatusList) {
            Assert.assertEquals("Cancelled", cardStatus);
        }
    }

    @Then("^I validate card status is \"(.*)\" in Staging database$")
    public void iValidateCardStatusIsInStagingDatabase(String expectedOutput, DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            //validating staging table IsProcessed value
            String memberIDF = cardValidation_pageobjects.gettingMembershipID(cardNumber);
            cardValidation_pageobjects.checkingIsProcessedStatus(memberIDF);
            //validating staging table Card status
            /*HashMap<String, String> cardStatusCodeMap = cardValidation_pageobjects.getStagingTableRecords(cardNumber);
            System.out.println("isProcessed Value" + cardStatusCodeMap.get("isProcessed"));
            System.out.println("CardStatusCode Value" + cardStatusCodeMap.get("CardStatusCode"));
            String actualOutputs = cardStatusCodeMap.get("CardStatusCode");
            Assert.assertEquals(expectedOutput.toLowerCase(), actualOutputs.toLowerCase());*/
        }
    }

    @Then("^I validate card status is \"(.*)\" in Main database$")
    public void iValidateCardStatusIsInMainDatabase(String expectedOutput, DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            environment = System.getProperty("Environment");
            matrixData = cardValidation_pageobjects.readMemberDataFromMatrix(environment, cardNumber);
            String actualOutputs = matrixData.get("CardStatus");
            Assert.assertEquals(expectedOutput.toLowerCase(), actualOutputs.toLowerCase());
        }
    }

    @When("^I cancel the member card$")
    public void iCancelTheMemberCard() throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        cancelDataMap = cardValidation_pageobjects.cancelCardManagement();
    }

    @Then("^I validate the card cancellation confirmation message$")
    public void iValidateTheCardCancellationConfirmationMessage() throws Throwable {
        String expectedOutput = "Membership Card " + cancelDataMap.get("MembershipCardNumber") + " was cancelled successfully.";
        Assert.assertEquals(expectedOutput, cancelDataMap.get("CancelMessage"));
    }

    @Then("^I validate card status is \"([^\"]*)\" under Membership Cards tab$")
    public void iValidateCardStatusIsUnderMembershipCardsTab(String expected) throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        List<String> memberCardStatusList = cardValidation_pageobjects.getMembershipCardTableStatus();
        for(int i=0 ; i<memberCardStatusList.size(); i++){
            if (i==0){
                _scenario.write("Primary card status");
                _scenario.write("Expected Card Status : "+expected+"");
                _scenario.write("Actual Card Status : "+memberCardStatusList.get(i)+"");
                System.out.println("Expected Card Status : "+expected+"");
                System.out.println("Actual Card Status : "+memberCardStatusList.get(i)+"");
                Assert.assertEquals(expected, memberCardStatusList.get(i));
            }else if(i==1){
                _scenario.write("Supplementary card status");
                _scenario.write("Expected Supplementary Card Status : "+expected+"");
                _scenario.write("Actual Supplementary Card Status : "+memberCardStatusList.get(i)+"");
                System.out.println("Expected Supplementary Card Status : "+expected+"");
                System.out.println("Actual Supplementary Card Status : "+memberCardStatusList.get(i)+"");
                Assert.assertEquals(expected, memberCardStatusList.get(i));
            }
        }
    }

    @Given("^I query Matrix Card table with Card Number for MemberID$")
    public void iQueryMatrixCardTableWithCardNumberForMemberID(DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            memberID = cardValidation_pageobjects.gettingMembershipID(cardNumber);
        }
    }

    @Then("^I query matrix DB stagging table with MemberID and wait for it to be processed$")
    public void iQueryMatrixDBStaggingTableWithMemberIDAndWaitForItToBeProcessed() throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        cardValidation_pageobjects.checkingIsProcessedStatus(memberID);
    }

    @Given("^I delete the new card number to be replaced from matrix main table$")
    public void iDeleteTheNewCardNumberToBeReplacedFromMatrixMainTable(DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            cardValidation_pageobjects.deleteMemberCardFromMatrixDB(cardNumber);
        }
    }

    @When("^I replace the member card with a New Card number$")
    public void iReplaceTheMemberCardWithANewCardNumber(DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber_OLD = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number_OLD>"))).getAsString();
            String cardNumber_NEW = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number_NEW>"))).getAsString();
            replaceCardDataMap = cardValidation_pageobjects.replaceCardManagement(cardNumber_OLD, cardNumber_NEW);
        }
    }

    @Then("^I validate card status is \"([^\"]*)\" for the old card under Membership Cards tab$")
    public void iValidateCardStatusIsForTheOldCardUnderMembershipCardsTab(String expectedCardStatus, DataTable table) throws Throwable {
        MemberValidation_pageobjects memberValidation_pageobjects = new MemberValidation_pageobjects(driver, _scenario);
        String cardNumber_OLD = null;
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            cardNumber_OLD = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
        }
        ArrayList<ArrayList<String>> dataList = memberValidation_pageobjects.getMembershipCardTableRecords();
        if (!memberValidation_pageobjects.CardTier_sf.equalsIgnoreCase("Prestige Diamond")) {
            outerloop:
            for (ArrayList<String> arrayListOneRow : dataList) {
                innerloop:
                for (int i = 0; i < arrayListOneRow.size(); i++) {
                    String cardNumberInMemberTable = arrayListOneRow.get(i);
                    if (!cardNumberInMemberTable.equalsIgnoreCase(cardNumber_OLD)) {
                        break innerloop;
                    } else {
                        //getting card status
                        _scenario.write("Validate Card status in Membership card table");
                        _scenario.write("Expected Card Status : Cancelled");
                        _scenario.write("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        System.out.println("Expected Card Status : Cancelled");
                        System.out.println("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        Assert.assertEquals(expectedCardStatus, arrayListOneRow.get(i + 1));
                        break outerloop;
                    }
                }
            }
        } else {
            outerloop:
            for (ArrayList<String> arrayListOneRow : dataList) {
                innerloop:
                for (int i = 0; i < arrayListOneRow.size(); i++) {
                    String cardNumberInMemberTable = arrayListOneRow.get(i);
                    if (!cardNumberInMemberTable.equalsIgnoreCase(cardNumber_OLD)) {
                        break innerloop;
                    } else {
                        //getting card status
                        _scenario.write("Validate Card status in Membership card table");
                        _scenario.write("Expected Card Status : Cancelled");
                        _scenario.write("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        System.out.println("Expected Card Status : Cancelled");
                        System.out.println("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        Assert.assertEquals(expectedCardStatus, arrayListOneRow.get(i + 1));
                        break outerloop;
                    }
                }
            }

            outerloopS:
            for (ArrayList<String> arrayListOneRow : dataList) {
                innerloopS:
                for (int i = 0; i < arrayListOneRow.size(); i++) {
                    String cardTypeInMemberTable = arrayListOneRow.get(i + 2);
                    if (!cardTypeInMemberTable.equalsIgnoreCase("Supplementary")) {
                        break innerloopS;
                    } else {
                        //getting card status for prestige diamond members
                        supplementary_Card = arrayListOneRow.get(i);
                        _scenario.write("Validate Supplementary Card status in Membership card table");
                        _scenario.write("Expected Card Status : Active");
                        _scenario.write("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        System.out.println("Expected Card Status : Active");
                        System.out.println("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        Assert.assertEquals("Active", arrayListOneRow.get(i + 1));
                        break outerloopS;
                    }
                }
            }
        }
    }

    @Then("^I validate card status is \"([^\"]*)\" for the new card under Membership Cards tab$")
    public void iValidateCardStatusIsForTheNewCardUnderMembershipCardsTab(String expectedStatus) throws Throwable {
        MemberValidation_pageobjects memberValidation_pageobjects = new MemberValidation_pageobjects(driver, _scenario);
        ArrayList<ArrayList<String>> dataList = memberValidation_pageobjects.getMembershipCardTableRecords();

        if (!memberValidation_pageobjects.CardTier_sf.equalsIgnoreCase("Prestige Diamond")) {
            outerloop:
            for (ArrayList<String> arrayListOneRow : dataList) {
                innerloop:
                for (int i = 0; i < arrayListOneRow.size(); i++) {
                    String cardNumberInMemberTable = arrayListOneRow.get(i);
                    if (!cardNumberInMemberTable.equalsIgnoreCase(replaceCardDataMap.get("ReplaceNEW_Card"))) {
                        break innerloop;
                    } else {
                        //getting card status
                        _scenario.write("Validate Card status in Membership card table");
                        _scenario.write("Expected Card Status : Active");
                        _scenario.write("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        System.out.println("Expected Card Status : Active");
                        System.out.println("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        Assert.assertEquals(expectedStatus, arrayListOneRow.get(i + 1));
                        break outerloop;
                    }
                }
            }
        } else {
            outerloop:
            for (ArrayList<String> arrayListOneRow : dataList) {
                innerloop:
                for (int i = 0; i < arrayListOneRow.size(); i++) {
                    String cardNumberInMemberTable = arrayListOneRow.get(i);
                    if (!cardNumberInMemberTable.equalsIgnoreCase(replaceCardDataMap.get("ReplaceNEW_Card"))) {
                        break innerloop;
                    } else {
                        //getting card status
                        _scenario.write("Validate Card status in Membership card table");
                        _scenario.write("Expected Card Status : Active");
                        _scenario.write("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        System.out.println("Expected Card Status : Active");
                        System.out.println("Actual Card Status : " + arrayListOneRow.get(i + 1) + "");
                        Assert.assertEquals(expectedStatus, arrayListOneRow.get(i + 1));
                        break outerloop;
                    }
                }
            }
        }
    }

    @Then("^I validate \"(.*)\" is \"(.*)\" in Main database$")
    public void iValidateIsInMainDatabase(String fieldName, String expectedValue, DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            Map<String, String> mainDBDataMap = cardValidation_pageobjects.getMainDBRecords(cardNumber);
            if (fieldName.equalsIgnoreCase("RenewedTo")) {
                String cardNumberText = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(expectedValue)).getAsString();
                _scenario.write("Expected : " + cardNumberText);
                _scenario.write("Actual : " + mainDBDataMap.get(fieldName));
                System.out.println("Expected : " + cardNumberText);
                System.out.println("Actual : " + mainDBDataMap.get(fieldName));
                Assert.assertEquals(cardNumberText, mainDBDataMap.get(fieldName));
            } else {
                _scenario.write("Expected : " + expectedValue);
                _scenario.write("Actual : " + mainDBDataMap.get(fieldName));
                System.out.println("Expected : " + expectedValue);
                System.out.println("Actual : " + mainDBDataMap.get(fieldName));
                Assert.assertEquals(expectedValue, mainDBDataMap.get(fieldName));
            }

            if (mainDBDataMap.get("TierCode").equals("PRESTIGE DIAMOND")) {
                if (fieldName.equalsIgnoreCase("MembershipStatusCode") && supplementary == 0) {
                    if (expectedValue.equalsIgnoreCase("ACTIVE")) {
                        if (!(supplementary_Card == null)) {
                            Map<String, String> supplementaryMainDBDataMap = cardValidation_pageobjects.getMainDBRecords(supplementary_Card);
                            _scenario.write("Checking supplementary card status");
                            _scenario.write("Expected : ACTIVE");
                            _scenario.write("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                            System.out.println("Checking supplementary card status");
                            System.out.println("Expected : ACTIVE");
                            System.out.println("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                            Assert.assertEquals("ACTIVE", supplementaryMainDBDataMap.get(fieldName));
                            supplementary++;
                        } else {
                            String suppCardNumber = cardValidation_pageobjects.getSupplementaryCardNumber(cardNumber);
                            Map<String, String> supplementaryMainDBDataMap = cardValidation_pageobjects.getMainDBRecords(suppCardNumber);
                            _scenario.write("Checking supplementary card status");
                            _scenario.write("Expected : ACTIVE");
                            _scenario.write("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                            System.out.println("Checking supplementary card status");
                            System.out.println("Expected : ACTIVE");
                            System.out.println("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                            Assert.assertEquals("ACTIVE", supplementaryMainDBDataMap.get(fieldName));
                            supplementary++;
                        }
                    } else if (expectedValue.equalsIgnoreCase("BLACKLISTED")) {
                        String suppCardNumber = cardValidation_pageobjects.getSupplementaryCardNumber(cardNumber);
                        Map<String, String> supplementaryMainDBDataMap = cardValidation_pageobjects.getMainDBRecords(suppCardNumber);
                        _scenario.write("Checking supplementary card status");
                        _scenario.write("Expected : BLACKLISTED");
                        _scenario.write("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                        System.out.println("Checking supplementary card status");
                        System.out.println("Expected : BLACKLISTED");
                        System.out.println("Actual : " + supplementaryMainDBDataMap.get(fieldName));
                        Assert.assertEquals("BLACKLISTED", supplementaryMainDBDataMap.get(fieldName));
                        supplementary++;
                    }
                }
            }
        }
    }

    @When("^I Suspend the member card number$")
    public void iSuspendTheMemberCardNumber() throws Throwable {
        CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
        suspended_BlackListCardDataMap = cardValidation_pageobjects.suspendedCardManagement();
    }

    @Then("^I validate that the Suspended status is \"([^\"]*)\" in the matrix member table$")
    public void iValidateThatTheSuspendedStatusIsInTheMatrixMemberTable(String expectedStatus, DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            Map<String, String> suspendedDataMap = cardValidation_pageobjects.getMainDBRecordsForCombined(cardNumber);
            if (expectedStatus.equalsIgnoreCase("NULL")) {
                _scenario.write("Checking " + expectedStatus + " status");
                System.out.println("Checking " + expectedStatus + " status");
                _scenario.write("Expected : " + expectedStatus);
                System.out.println("Expected : " + expectedStatus);
                if (suspendedDataMap.get("GenericStr29").equals("")) {
                    String actual = "NULL";
                    _scenario.write("Actual : NULL");
                    System.out.println("Actual : " + actual);
                    Assert.assertEquals(expectedStatus, actual);
                }
            } else {
                _scenario.write("Checking " + expectedStatus + " status");
                _scenario.write("Expected : " + expectedStatus);
                _scenario.write("Actual : " + suspendedDataMap.get("GenericStr29"));
                System.out.println("Checking " + expectedStatus + " status");
                System.out.println("Expected : " + expectedStatus);
                System.out.println("Actual : " + suspendedDataMap.get("GenericStr29"));
                Assert.assertEquals(expectedStatus, suspendedDataMap.get("GenericStr29"));
            }
        }
    }

    @When("^I click \"([^\"]*)\" on the Member Request tab and \"([^\"]*)\" the request$")
    public void iClickOnTheMemberRequestTabAndTheRequest(String memberRequest, String type) throws Throwable {
        MemberValidation_pageobjects memberValidation_pageobjects = new MemberValidation_pageobjects(driver, _scenario);
        memberValidation_pageobjects.clickOnMemberRequest(memberRequest);
        if (type.equalsIgnoreCase("Blacklist")) {
            blackListOrResumeMessage = memberValidation_pageobjects.getMemberBlackList();
        } else if (type.equalsIgnoreCase("Resume")) {
            blackListOrResumeMessage = memberValidation_pageobjects.getMemberResume();
        }
    }

    @When("^I Blacklist the member card number$")
    public void iBlacklistTheMemberCardNumber(DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            blackListCardDataMap = cardValidation_pageobjects.blackListCardManagement(cardNumber);
        }
    }

    @When("^I merge the member card$")
    public void iMergeTheMemberCard(DataTable table) throws Throwable {
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            String cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"))).getAsString();
            CardValidation_pageobjects cardValidation_pageobjects = new CardValidation_pageobjects(driver, _scenario);
            mergeCardDataMap = cardValidation_pageobjects.mergeCardManagement(cardNumber);
        }
    }

    @Given("^I send a POST request to create ETR member with already blacklisted card number\"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreateETRMemberWithAlreadyBlacklistedCardNumber(String TestDataTagForCardNumber) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(TestDataTagForCardNumber)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = etr.getETRcreateMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);
        //send the POST call and get the response
        Response retrunedResponse = etr.getResponseOfCreateMemberETR(RequestBody);

        //get the return message and print
        APIReturnMessage = API.getAPIReturnMessage(_scenario, retrunedResponse).trim();
        System.out.println("ETR RESPONSE RETURN MESSAGE - " + APIReturnMessage);

    }

    @Then("^I validate the API return message for ETR$")
    public void iValidateTheAPIReturnMessageForETR() throws Throwable {
        try {
            Assert.assertEquals(APIReturnMessage.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("ETR create Member API call was successful");
            _scenario.write("ETR create Member API call was successful");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY. ! ETR create Member API response is " + APIReturnMessage);
            _scenario.write("TEST FAILED INTENTIONALLY. ! ETR create Member API response is " + APIReturnMessage);
            Assert.fail("TEST FAILED INTENTIONALLY. ! ETR create Member API response is " + APIReturnMessage);
        }
    }

    @Then("^I validate Custom logs for an error under ETR as \"([^\"]*)\"$")
    public void iValidateCustomLogsForAnErrorUnderETR(String ExpectedErrorMessageInCustomLogs) throws Throwable {
//        System.out.println(" ~~~~~ Waiting for 3 minutes before querying CMD logs table ~~~~~~");
//        Thread.sleep(180000);
//        System.out.println(" ~~~~~ Continuing to query CMD logs table after 3 minutes wait ~~~~~~");
        accessToken.getSFaccessToken();
        //System.out.println("Return message from CMD logs - "+ etr.getReturnMessageFromCmdLogsInMatrixForCardEnquiry(environment,cardNumber));
        String customLogsReturnMessage = null;
        try {
            customLogsReturnMessage = sfdata.getMessageFromCustomLogsInSF(accessToken.getSFaccessToken(), cardNumber, Property.ERROR_CLASS_FOR_API_CALLS);
        } catch (SQLException e) {
            System.out.println("TEST FAILED !. Unable to get Custom logs message due to an Exception - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unable to get Custom logs message due to an Exception - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get Custom logs message due to an Exception - " + e.getMessage());
        }

        try {
            Assert.assertEquals(customLogsReturnMessage.toLowerCase(), ExpectedErrorMessageInCustomLogs.toLowerCase());
            System.out.println("Custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
            _scenario.write("Custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Unexpected Custom logs message found for ETR create Member - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unexpected Custom logs message found for ETR create Member - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unexpected Custom logs message found for ETR create Member - " + e.getMessage());
        }
    }

    @Given("^I send a POST request to create POS member with already blacklisted card number\"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreatePOSMemberWithAlreadyBlacklistedCardNumber(String TestDataTagForCardNumber) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(TestDataTagForCardNumber)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = etr.getPOScreateMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);
        //send the POST call and get the response
        Response retrunedResponse = etr.getResponseFromCreateMemberPOS(RequestBody);

        //get the return message and print
        APIReturnMessage = API.getAPIReturnMessage(_scenario, retrunedResponse).trim();
        System.out.println("POS RESPONSE RETURN MESSAGE - " + APIReturnMessage);

    }

    @Then("^I validate the API return message for POS$")
    public void iValidateTheAPIReturnMessageForPOS() throws Throwable {
        try {
            Assert.assertEquals(APIReturnMessage.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("POS create member API call was successful");
            _scenario.write("POS create member API call was successful");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY. ! POS create member API response is " + APIReturnMessage);
            _scenario.write("TEST FAILED INTENTIONALLY. ! POS create member API response is " + APIReturnMessage);
            Assert.fail("TEST FAILED INTENTIONALLY. ! POS create member API response is " + APIReturnMessage);
        }
    }

    @Then("^I validate Custom logs for an error under POS as \"([^\"]*)\"$")
    public void iValidateCustomLogsForAnErrorUnderPOSAs(String ExpectedCustomLogReturnMessage) throws Throwable {
        //System.out.println(" ~~~~~ Waiting for 3 minutes before querying CMD logs table ~~~~~~");
        //Thread.sleep(180000);
        //System.out.println(" ~~~~~ Continuing to query CMD logs table after 3 minutes wait ~~~~~~");

        //System.out.println("Return message from CMD logs - "+ etr.getReturnMessageFromCmdLogsInMatrixForCardEnquiry(environment,cardNumber));
        accessToken.getSFaccessToken();

        String customLogsReturnMessage = null;
        try {
            customLogsReturnMessage = sfdata.getMessageFromCustomLogsInSF(accessToken.getSFaccessToken(), cardNumber, Property.ERROR_CLASS_FOR_API_CALLS);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
        }

        try {
            Assert.assertEquals(customLogsReturnMessage.toLowerCase(), ExpectedCustomLogReturnMessage.toLowerCase());
            System.out.println("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
            _scenario.write("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
        }
    }

    @Given("^I send a POST request to create POS member with already cancelled card number\"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreatePOSMemberWithAlreadyCancelledCardNumber(String TestDataTagForCardNumber) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(TestDataTagForCardNumber)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = etr.getPOScreateMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);
        //send the POST call and get the response
        Response retrunedResponse = etr.getResponseFromCreateMemberPOS(RequestBody);

        //get the return message and print
        APIReturnMessage = API.getAPIReturnMessage(_scenario, retrunedResponse).trim();
        System.out.println("POS RESPONSE RETURN MESSAGE - " + APIReturnMessage);
    }

    @Given("^I validate if card range with maximum value \"([^\"]*)\" and minimum value \"([^\"]*)\" and store location \"([^\"]*)\" does not exist$")
    public void iValidateIfCardRangeWithMaximumValueAndMinimumValueAndStoreLocationDoesNotExist(String maximumValueTag, String minimumValueTag, String storeLocationTag) throws Throwable {
        String maximumValue = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(maximumValueTag)).getAsString();
        String minimumValue = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(minimumValueTag)).getAsString();
        String storeLocation = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(storeLocationTag)).getAsString();

        try {
            Assert.assertFalse(sfdata.isCardRangeExist(accessToken.getSFaccessToken(), maximumValue, minimumValue, storeLocation));
            System.out.println("access token - " + accessToken.getSFaccessToken());
            System.out.println("Card Range " + minimumValue + " - " + maximumValue + " Does not exist in Salesforce as expected !");
            _scenario.write("Card Range " + minimumValue + " - " + maximumValue + " Does not exist in Salesforce as expected !");
        } catch (Exception e) {
            System.out.println("TEST FAILED ! Card Range " + minimumValue + " - " + maximumValue + " exist in Salesforce. Please use an card range that does not exist for this test scenario !");
            _scenario.write("TEST FAILED ! Card Range " + minimumValue + " - " + maximumValue + " exist in Salesforce. Please use an card range that does not exist for this test scenario !");
            Assert.fail("TEST FAILED ! Card Range " + minimumValue + " - " + maximumValue + " exist in Salesforce. Please use an card range that does not exist for this test scenario !");
        }
    }

    @Given("^I validate if card number \"([^\"]*)\" does not exist$")
    public void iValidateIfCardNumberDoesNotExist(String cardNumberTag) throws Throwable {
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTag)).getAsString();

        try {
            Assert.assertFalse(sfdata.isCardExist(accessToken.getSFaccessToken(), cardNumber));
            System.out.println("Card No " + cardNumber + " Does not exist in Salesforce as expected !");
            _scenario.write("Card No " + cardNumber + " Does not exist in Salesforce as expected !");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Given("^I send a POST request to create DSA member with out of range card number \"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreateDSAMemberWithOutOfRangeCardNumber(String TestDataTagForCardNumber) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(TestDataTagForCardNumber)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = etr.getDSAcreateMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);
        //send the POST call and get the response
        Response retrunedResponse = etr.getResponseFromCreateMemberDSA(RequestBody);

        //get the return message and print
        APIReturnMessage = API.getAPIReturnMessage(_scenario, retrunedResponse).trim();
        System.out.println("DSA RESPONSE RETURN MESSAGE - " + APIReturnMessage);
    }

    @Then("^I validate the API return message for DSA$")
    public void iValidateTheAPIReturnMessageForDSA() throws Throwable {
        try {
            Assert.assertEquals(APIReturnMessage.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("DSA create member API call was successful");
            _scenario.write("DSA create member API call was successful");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY. ! DSA create member API response is " + APIReturnMessage);
            _scenario.write("TEST FAILED INTENTIONALLY. ! DSA create member API response is " + APIReturnMessage);
            Assert.fail("TEST FAILED INTENTIONALLY. ! DSA create member API response is " + APIReturnMessage);
        }
    }

    @Then("^I validate Custom logs for an error under DSA as \"([^\"]*)\"$")
    public void iValidateCustomLogsForAnErrorUnderDSAAs(String ExpectedCustomLogReturnMessage) throws Throwable {
        accessToken.getSFaccessToken();

        String customLogsReturnMessage = null;
        try {
            customLogsReturnMessage = sfdata.getMessageFromCustomLogsInSF(accessToken.getSFaccessToken(), cardNumber, Property.ERROR_CLASS_FOR_API_CALLS);
            System.out.println("Actual custom Logs return message - " + customLogsReturnMessage);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
        }

        try {
            Assert.assertEquals(customLogsReturnMessage.toLowerCase(), ExpectedCustomLogReturnMessage.toLowerCase());
            System.out.println("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
            _scenario.write("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
        }
    }


    @Given("^I send a POST request to create DSA member with already Replaced card number \"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreateDSAMemberWithAlreadyReplacedCardNumber(String ReplacedCardTestDataTag) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(ReplacedCardTestDataTag)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = etr.getDSAcreateMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);
        //send the POST call and get the response
        Response retrunedResponse = etr.getResponseFromCreateMemberDSA(RequestBody);

        //get the return message and print
        APIReturnMessage = API.getAPIReturnMessage(_scenario, retrunedResponse).trim();
        System.out.println("DSA RESPONSE RETURN MESSAGE - " + APIReturnMessage);
    }

    @Given("^I send a POST request to POS get member for card number \"([^\"]*)\"$")
    public void iSendAPOSTRequestToPOSGetMemberForCardNumber(String RubyMemberCardNumberTestDataTag) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(RubyMemberCardNumberTestDataTag)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = pos.getPOSgetMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);

        //send the POST call and store POS member details
        try {
            pos.getMemberPOS(RequestBody);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            System.out.println("TEST FAILED INTENTIONALLY ! Exception occurred during POS get member - "+ ex.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! Exception occurred during POS get member - "+ ex.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Exception occurred during POS get member - "+ ex.getMessage());
        }

//        try {
//            Assert.assertEquals(pos.ReturnMessage.toLowerCase(), Property.API_EXPECTED_SUCCESS_MESSAGE.toLowerCase());
//            System.out.println("API return message is - " + pos.ReturnMessage);
//        } catch (AssertionError e) {
//            System.out.println("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
//            _scenario.write("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
//            Assert.fail("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
//        }
    }

    @Then("^I read the details in POS get member response for card number \"([^\"]*)\"$")
    public void iReadTheDetailsInPOSGetMemberResponseForCardNumber(String cardNumberTestDataTag) throws Throwable {
        System.out.println("======= POS Get member details for card number " + myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString() + " ==========");
        //ReturnMessage
        System.out.println("Return Message : " + pos.ReturnMessage);
        _scenario.write("Return Message : " + pos.ReturnMessage);

        // CardNo
        System.out.println("Card No : " + pos.CardNo);
        _scenario.write("Card No : " + pos.CardNo);

        // Membership Card Status
        _scenario.write("Member Card Status   : " + pos.MembershipcardStatusCode);
        System.out.println("Member Card Status   : " + pos.MembershipcardStatusCode);

        // MembershipTierID
        _scenario.write("Membership Tier  : " + pos.MembershipTierID);
        System.out.println("Membership Tier  : " + pos.MembershipTierID);

        //StatusDollarToUpgrade
        _scenario.write("StatusDollar To Upgrade   : " + pos.StatusDollarToUpgrade);
        System.out.println("StatusDollar To Upgrade   : " + pos.StatusDollarToUpgrade);

        // StatusDollarToRenew
        _scenario.write("StatusDollar To Renew : " + pos.StatusDollarToRenew);
        System.out.println("StatusDollar To Renew : " + pos.StatusDollarToRenew);

        //StartDate
        _scenario.write("StartDate : " + pos.StartDate);
        System.out.println("StartDate : " + pos.StartDate);

        //EndDate
        _scenario.write("EndDate  : " + pos.EndDate);
        System.out.println("EndDate  : " + pos.EndDate);

        //CarryForwardDollarUSDAmount  (Status Dollar)
        _scenario.write("CarryForwardDollarUSDAmount  : " + pos.CarryForwardDollarUSDAmount);
        System.out.println("CarryForwardDollarUSDAmount  : " + pos.CarryForwardDollarUSDAmount);

        //MemberPointBalance (Point Balance)
        _scenario.write("MemberPointBalance  : " + pos.MemberPointBalance);
        System.out.println("MemberPointBalance  : " + pos.MemberPointBalance);

        // The standard set ####################################################
        //FirstName
        _scenario.write("FirstName   : " + pos.FirstName);
        System.out.println("FirstName   : " + pos.FirstName);

        //LastName
        _scenario.write("LastName   : " + pos.LastName);
        System.out.println("LastName   : " + pos.LastName);

        //TitleCode
        _scenario.write("TitleCode   : " + pos.TitleCode);
        System.out.println("TitleCode   : " + pos.TitleCode);

        //NativeSalutation
        _scenario.write("NativeSalutation   : " + pos.NativeSalutation);
        System.out.println("NativeSalutation   : " + pos.NativeSalutation);

        //FirstNameNative
        _scenario.write("FirstNameNative   : " + pos.FirstNameNative);
        System.out.println("FirstNameNative   : " + pos.FirstNameNative);

        //LastNameNative
        _scenario.write("LastNameNative   : " + pos.LastNameNative);
        System.out.println("LastNameNative   : " + pos.LastNameNative);

        //GenderCode
        _scenario.write("LastNameNative   : " + pos.GenderCode);
        System.out.println("LastNameNative   : " + pos.GenderCode);

        //EmailAddressText
        _scenario.write("EmailAddressText   : " + pos.EmailAddressText);
        System.out.println("EmailAddressText   : " + pos.EmailAddressText);

        //ContactNumberText (Mobile Phone)
        _scenario.write("ContactNumberText   : " + pos.ContactNumberText);
        System.out.println("ContactNumberText   : " + pos.ContactNumberText);

        //MobileNo2 (Other Phone 1)
        _scenario.write("MobileNo2   : " + pos.MobileNo2);
        System.out.println("MobileNo2   : " + pos.MobileNo2);

        //HomeNo (Other Phone 2)
        _scenario.write("HomeNo   : " + pos.HomeNo);
        System.out.println("HomeNo   : " + pos.HomeNo);

        //CountryNameText   (Location of Residence (Mailing Address))
        _scenario.write("CountryNameText   : " + pos.CountryNameText);
        System.out.println("CountryNameText   : " + pos.CountryNameText);

        //MailingAddress1 (Mailing Address Line 1)
        _scenario.write("MailingAddress1   : " + pos.MailingAddress1);
        System.out.println("MailingAddress1   : " + pos.MailingAddress1);

        //MailingAddress2 (Mailing Address Line 2)
        _scenario.write("MailingAddress2   : " + pos.MailingAddress2);
        System.out.println("MailingAddress2   : " + pos.MailingAddress2);

        //MailingAddress3  (Mailing Address Line 3)
        _scenario.write("MailingAddress3   : " + pos.MailingAddress3);
        System.out.println("MailingAddress3   : " + pos.MailingAddress3);

        //SpokenLanguage
        _scenario.write("SpokenLanguage   : " + pos.SpokenLanguage);
        System.out.println("SpokenLanguage   : " + pos.SpokenLanguage);
        //  ##########################################################################
    }

    @Then("^I read POS member values from Salesfroce for card number \"([^\"]*)\"$")
    public void iReadPOSMemberValuesFromSalesfroce(String cardNumberTestDataTag) throws Throwable {


        String token = accessToken.getSFaccessToken();

        String CardNo = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString();
        //passing the card number and getting the required data from SF
        sfdata.querySFdataForDummyMmeber(token, CardNo);
        System.out.println("isDummy value --- " + sfdata.isDummy__c);

        Thread.sleep(1000);

        driver.switchTo().defaultContent();
        sf.getMembershipcardStatusCode(); // Member Card Status
        System.out.println("getMember Card Status : " + sf.MemberStatus_sf);

        sf.getPointBalance();
        System.out.println("getPointBalance : " + sf.pointBalance_sf);

        //current status dollar
        sf.getCurrentStatusDollar_MembershipTier();
        System.out.println("Current Status Dollars : "+sf.Mem_points_Current_status_Dollar_MembershipTier_sf);

        //expiry date
        try {
            sf.getExpiryDate();
            System.out.println("Expiry Date : "+sf.expiryDate_sf);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to read Expiry date from SF. "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to read Expiry date from SF. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read Expiry date from SF. "+e.getMessage());
        }

        if (sfdata.isDummy__c.equalsIgnoreCase("false")) { //When Member is NOT A DUMMY, do all as usual
            System.out.println("Member is NOT a Dummy");
            _scenario.write("Member is NOT a Dummy");
            // Elements in the General tab
            sf.switchToMainFrame();

            //Taking screenshot
            screenshot.takeScreenshot();

            sf.getFirstName();
            sf.getLastName();
            sf.getTitleCode();
            sf.getNativeSalutation();
            sf.getFirstNameNative();
            sf.getLastNameNative();
            sf.getGenderCode();
            sf.getEmailAddressText();
            sf.getContactNumberText();
            sf.getOtherPhone1(); // MobileNo2
            sf.getOtherPhone2(); // HomeNo
            sf.getEnglishCountry(); //Location of Residence (Mailing Address))
            sf.getMailingAddress1();
            sf.getMailingAddress2();
            sf.getMailingAddress3();
            sf.getCityNameText();

            //Elements in the Contact Preferences Tab
            sf.switchToContactPreferencesTab();

            //Taking screenshot 1
            screenshot.takeScreenshot();
            sf.getSpokenLanguage();


            //Elements in the Member Cycle Tab
            sf.switchToMemberCycleTab();
            //Taking screenshot 1
            screenshot.takeScreenshot();

            sf.getCardTier();  //Tier
            sf.getCycleStartDate();
            sf.getCycleEndDate();
            sf.getStatusDollarToUpgrade();
            sf.getStatusDollarToRenew();

            sf.getCarryForwardDollarAmount();


        } else { //When MEMBER IS A DUMMY, Only read data from member cycle tab
            System.out.println("Member is a Dummy");
            _scenario.write("Member is a Dummy");

            //Elements in the Member Cycle Tab
            sf.switchToMemberCycleTab();
            //Taking screenshot 1
            screenshot.takeScreenshot();

            sf.getCardTier();  //Tier
            sf.getCycleStartDate();
            sf.getCycleEndDate();
            sf.getStatusDollarToUpgrade();
            sf.getStatusDollarToRenew();

            sf.getCarryForwardDollarAmount();
            System.out.println("getCarryForwardDollarAmount : " + sf.carryForwardDollarAmount_sf);
        }
    }

    @Then("^I validate POS get Member values with Salesforce$")
    public void iValidatePOSGetMemberValuesWithSalesforce() throws Throwable {
        if (sfdata.isDummy__c.equalsIgnoreCase("false")) { //When member is NOT A DUMMY, do assertions as usual

            //Member Card Status
            softAssert.assertEquals(sf.MemberCardStatus_sf.toLowerCase(), pos.MembershipcardStatusCode.toLowerCase(), "Membership card StatusCode ");
            _scenario.write("=============== MemberStatus ===============");
            _scenario.write("Actual Member Card Status (SF) : " + sf.MemberCardStatus_sf);
            _scenario.write("Expected Member Card Status (Response) : " + pos.MembershipcardStatusCode);

            //PointBalance
            softAssert.assertEquals(sf.pointBalance_sf, pos.BalancePointCount, "PointBalance ");
            _scenario.write("=============== PointBalance ===============");
            _scenario.write("Actual PointBalance (SF) : " + sf.pointBalance_sf);
            _scenario.write("Expected PointBalance (Response) : " + pos.BalancePointCount);

            //CarryForwardDollarAmount
            softAssert.assertEquals(sf.carryForwardDollarAmount_sf.toLowerCase(), pos.CarryForwardDollarUSDAmount.toLowerCase(), "CarryForwardDollarAmount ");
            _scenario.write("=============== CarryForwardDollarAmount ===============");
            _scenario.write("Actual CarryForwardDollarAmount (SF) : " + sf.carryForwardDollarAmount_sf);
            _scenario.write("Expected CarryForwardDollarAmount (Response) : " + pos.CarryForwardDollarUSDAmount);

            //MembershipTierID
            softAssert.assertEquals(sf.CardTier_sf.toLowerCase(), pos.MembershipTierID.toLowerCase(), "MembershipTier ");
            _scenario.write("=============== MembershipTier ===============");
            _scenario.write("Actual MembershipTier (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected MembershipTier (Response) : " + pos.MembershipTierID);

            //StatusDollarToRenew
            softAssert.assertEquals(sf.statusDollarToRenew_sf, pos.StatusDollarToRenew, "StatusDollarToRenew ");
            _scenario.write("=============== StatusDollarToRenew ===============");
            _scenario.write("Actual StatusDollarToRenew (SF) : " + sf.statusDollarToRenew_sf);
            _scenario.write("Expected StatusDollarToRenew (Response) : " + pos.StatusDollarToRenew);

            //StartDate
            softAssert.assertEquals(sf.CycleStartDate_sf, pos.StartDate, "StartDate ");
            _scenario.write("=============== StartDate ===============");
            _scenario.write("Actual StartDate (SF) : " + sf.CycleStartDate_sf);
            _scenario.write("Expected StartDate (Response) : " + pos.StartDate);

            //EndDate
            softAssert.assertEquals(sf.CycleEndDate_sf, pos.EndDate, "EndDate ");
            _scenario.write("=============== EndDate ===============");
            _scenario.write("Actual EndDate (SF) : " + sf.CycleEndDate_sf);
            _scenario.write("Expected EndDate (Response) : " + pos.EndDate);

            //StatusDollarToUpgrade
            softAssert.assertEquals(sf.statusDollarToUpgrade_sf, pos.StatusDollarToUpgrade, "StatusDollarToUpgrade ");
            _scenario.write("=============== StatusDollarToUpgrade ===============");
            _scenario.write("Actual StatusDollarToUpgrade (SF) : " + sf.statusDollarToUpgrade_sf);
            _scenario.write("Expected StatusDollarToUpgrade (Response) : " + pos.StatusDollarToUpgrade);

            //First Name
            softAssert.assertEquals(sf.FirstName_sf, pos.FirstName, "First Name ");
            _scenario.write("=============== First Name ===============");
            _scenario.write("Actual First Name (SF) : " + sf.FirstName_sf);
            _scenario.write("Expected First Name (Response) : " + pos.FirstName);

            //Last Name
            softAssert.assertEquals(sf.LastName_sf, pos.LastName, "Last Name ");
            _scenario.write("=============== Last Name ===============");
            _scenario.write("Actual Last Name (SF) : " + sf.LastName_sf);
            _scenario.write("Expected Last Name (Response) : " + pos.LastName);

            //TitleCode
            softAssert.assertEquals(sf.Salutation_sf, pos.TitleCode, "TitleCode ");
            _scenario.write("=============== TitleCode ===============");
            _scenario.write("Actual TitleCode (SF) : " + sf.Salutation_sf);
            _scenario.write("Expected TitleCode (Response) : " + pos.TitleCode);

            //NativeSalutation
            softAssert.assertEquals(sf.NativeSalutation_sf, pos.NativeSalutation, "NativeSalutation ");
            _scenario.write("=============== NativeSalutation ===============");
            _scenario.write("Actual NativeSalutation (SF) : " + sf.NativeSalutation_sf);
            _scenario.write("Expected NativeSalutation (Response) : " + pos.NativeSalutation);

            //FirstNameNative
            softAssert.assertEquals(sf.FirstNameNative_sf, pos.FirstNameNative, "FirstNameNative ");
            _scenario.write("=============== FirstNameNative ===============");
            _scenario.write("Actual FirstNameNative (SF) : " + sf.FirstNameNative_sf);
            _scenario.write("Expected FirstNameNative (Response) : " + pos.FirstNameNative);

            //LastNameNative
            softAssert.assertEquals(sf.LastNameNative_sf, pos.LastNameNative, "LastNameNative ");
            _scenario.write("=============== LastNameNative ===============");
            _scenario.write("Actual LastNameNative (SF) : " + sf.LastNameNative_sf);
            _scenario.write("Expected LastNameNative (Response) : " + pos.LastNameNative);

            //GenderCode
            softAssert.assertEquals(sf.Gender_sf, pos.GenderCode, "GenderCode ");
            _scenario.write("=============== GenderCode ===============");
            _scenario.write("Actual GenderCode (SF) : " + sf.LastNameNative_sf);
            _scenario.write("Expected GenderCode (Response) : " + pos.GenderCode);

            //EmailAddressText
            softAssert.assertEquals(sf.EmailAddressText_sf, pos.EmailAddressText, "EmailAddressText ");
            _scenario.write("=============== EmailAddressText ===============");
            _scenario.write("Actual EmailAddressText (SF) : " + sf.EmailAddressText_sf);
            _scenario.write("Expected EmailAddressText (Response) : " + pos.EmailAddressText);

            //ContactNumberText
            softAssert.assertEquals(sf.ContactNumberText_sf, pos.ContactNumberText, "ContactNumberText ");
            _scenario.write("=============== ContactNumberText ===============");
            _scenario.write("Actual ContactNumberText (SF) : " + sf.ContactNumberText_sf);
            _scenario.write("Expected ContactNumberText (Response) : " + pos.ContactNumberText);

            //MobileNo2
            softAssert.assertEquals(sf.OtherPhone1_sf, pos.MobileNo2, "MobileNo2 ");
            _scenario.write("=============== MobileNo2 ===============");
            _scenario.write("Actual MobileNo2 (SF) : " + sf.OtherPhone1_sf);
            _scenario.write("Expected MobileNo2 (Response) : " + pos.MobileNo2);

            //HomeNo
            softAssert.assertEquals(sf.OtherPhone2_sf, pos.HomeNo, "HomeNo ");
            _scenario.write("=============== HomeNo ===============");
            _scenario.write("Actual HomeNo (SF) : " + sf.OtherPhone2_sf);
            _scenario.write("Expected HomeNo (Response) : " + pos.HomeNo);

            //CountryNameText
            softAssert.assertEquals(sf.EnglishCountry_sf, pos.CountryNameText, "CountryNameText ");
            _scenario.write("=============== CountryNameText ===============");
            _scenario.write("Actual CountryNameText (SF) : " + sf.EnglishCountry_sf);
            _scenario.write("Expected CountryNameText (Response) : " + pos.CountryNameText);

            //MailingAddress1
            softAssert.assertEquals(sf.MailingAddress1_sf, pos.MailingAddress1, "MailingAddress1 ");
            _scenario.write("=============== MailingAddress1 ===============");
            _scenario.write("Actual MailingAddress1 (SF) : " + sf.MailingAddress1_sf);
            _scenario.write("Expected MailingAddress1 (Response) : " + pos.MailingAddress1);

            //MailingAddress2
            softAssert.assertEquals(sf.MailingAddress2_sf, pos.MailingAddress2, "MailingAddress2 ");
            _scenario.write("=============== MailingAddress2 ===============");
            _scenario.write("Actual MailingAddress2 (SF) : " + sf.MailingAddress2_sf);
            _scenario.write("Expected MailingAddress2 (Response) : " + pos.MailingAddress2);

            //MailingAddress3
            softAssert.assertEquals(sf.MailingAddress3_sf, pos.MailingAddress3, "MailingAddress3 ");
            _scenario.write("=============== MailingAddress3 ===============");
            _scenario.write("Actual MailingAddress3 (SF) : " + sf.MailingAddress3_sf);
            _scenario.write("Expected MailingAddress3 (Response) : " + pos.MailingAddress3);

            //SpokenLanguage  //SpokenLanguage_sf
            softAssert.assertEquals(sf.SpokenLanguage_sf, pos.SpokenLanguage, "SpokenLanguage ");
            _scenario.write("=============== SpokenLanguage ===============");
            _scenario.write("Actual SpokenLanguage (SF) : " + sf.SpokenLanguage_sf);
            _scenario.write("Expected SpokenLanguage (Response) : " + pos.SpokenLanguage);

            //City Name
            softAssert.assertEquals(sf.CityName_sf.toLowerCase(), pos.ResCityNameText.toLowerCase(), "City Name ");
            _scenario.write("=============== City Name ===============");
            _scenario.write("Actual City Name (SF) : " + sf.ResStateNameText_sf.toLowerCase());
            _scenario.write("Expected City Name (Response) : " + pos.ResCityNameText.toLowerCase());

            //Current status dollar (on the right hand pannel)
            softAssert.assertEquals(sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase(), pos.StatusDollarUSDAmount.toLowerCase(), "Current status dollar ");
            _scenario.write("=============== Current status dollar ===============");
            _scenario.write("Actual Current status dollar (SF) : "+sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase());
            _scenario.write("Expected Current status dollar (Response) : "+pos.StatusDollarUSDAmount.toLowerCase());

            // Expiry date (on the right hand pannel)
            softAssert.assertEquals(sf.expiryDate_sf.toLowerCase(), pos.ExpiringDate.toLowerCase(), "Expiry date ");
            _scenario.write("=============== Expiry date ===============");
            _scenario.write("Actual Expiry date (SF) : "+sf.expiryDate_sf.toLowerCase());
            _scenario.write("Expected Expiry date (Response) : "+pos.ExpiringDate.toLowerCase());

        } else {

            //MembershipTierID
            softAssert.assertEquals(sf.CardTier_sf.toLowerCase(), pos.MembershipTierID.toLowerCase(), "MembershipTier ");
            _scenario.write("=============== MembershipTier ===============");
            _scenario.write("Actual MembershipTier (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected MembershipTier (Response) : " + pos.MembershipTierID);

            //StartDate
            softAssert.assertEquals(sf.CycleStartDate_sf, pos.StartDate, "StartDate ");
            _scenario.write("=============== StartDate ===============");
            _scenario.write("Actual StartDate (SF) : " + sf.CycleStartDate_sf);
            _scenario.write("Expected StartDate (Response) : " + pos.StartDate);

            //EndDate
            softAssert.assertEquals(sf.CycleEndDate_sf, pos.EndDate, "EndDate ");
            _scenario.write("=============== EndDate ===============");
            _scenario.write("Actual EndDate (SF) : " + sf.CycleEndDate_sf);
            _scenario.write("Expected EndDate (Response) : " + pos.EndDate);

            //StatusDollarToUpgrade
            softAssert.assertEquals(sf.statusDollarToUpgrade_sf, pos.StatusDollarToUpgrade, "StatusDollarToUpgrade ");
            _scenario.write("=============== StatusDollarToUpgrade ===============");
            _scenario.write("Actual StatusDollarToUpgrade (SF) : " + sf.statusDollarToUpgrade_sf);
            _scenario.write("Expected StatusDollarToUpgrade (Response) : " + pos.StatusDollarToUpgrade);

            //StatusDollarToRenew
            softAssert.assertEquals(sf.statusDollarToRenew_sf, pos.StatusDollarToRenew, "StatusDollarToRenew ");
            _scenario.write("=============== StatusDollarToRenew ===============");
            _scenario.write("Actual StatusDollarToRenew (SF) : " + sf.statusDollarToRenew_sf);
            _scenario.write("Expected StatusDollarToRenew (Response) : " + pos.StatusDollarToRenew);

            //CarryForwardDollarAmount
            softAssert.assertEquals(sf.carryForwardDollarAmount_sf, pos.CarryForwardDollarUSDAmount, "CarryForwardDollarAmount ");
            _scenario.write("=============== CarryForwardDollarAmount ===============");
            _scenario.write("Actual CarryForwardDollarAmount (SF) : " + sf.carryForwardDollarAmount_sf);
            _scenario.write("Expected CarryForwardDollarAmount (Response) : " + pos.CarryForwardDollarUSDAmount);

            //Current status dollar (on the right hand pannel)
            softAssert.assertEquals(sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase(), pos.StatusDollarUSDAmount.toLowerCase(), "Current status dollar ");
            _scenario.write("=============== Current status dollar ===============");
            _scenario.write("Actual Current status dollar (SF) : "+sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase());
            _scenario.write("Expected Current status dollar (Response) : "+pos.StatusDollarUSDAmount.toLowerCase());

            // Expiry date (on the right hand pannel)
            softAssert.assertEquals(sf.expiryDate_sf.toLowerCase(), pos.ExpiringDate.toLowerCase(), "Expiry date ");
            _scenario.write("=============== Expiry date ===============");
            _scenario.write("Actual Expiry date (SF) : "+sf.expiryDate_sf.toLowerCase());
            _scenario.write("Expected Expiry date (Response) : "+pos.ExpiringDate.toLowerCase());

        }


        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. " + e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. " + e.getMessage());
            Assert.fail();
        }

}

    @Given("^I send a POST request to POS search member for card number \"([^\"]*)\"$")
    public void iSendAPOSTRequestToPOSSearchMemberForCardNumber(String CancelledMemberTestDataTag) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(CancelledMemberTestDataTag)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = pos.getPOSsearchMemberRequestBodyForAnyCard(cardNumber);

        System.out.println("Request body -- " + RequestBody);

        //send the POST call and store POS member details
        pos.searcheMemberPOS(RequestBody);

        try {
            Assert.assertEquals(pos.ReturnMessage.toLowerCase(), Property.API_EXPECTED_SUCCESS_MESSAGE.toLowerCase());
            System.out.println("API return message is - " + pos.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
        }
    }

    @Then("^I read the details in POS search member response for card number \"([^\"]*)\"$")
    public void iReadTheDetailsInPOSSearchMemberResponseForCardNumber(String cardNumberTestDataTag) throws Throwable {
        System.out.println("======= POS Search member details for card number " + myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString() + " ==========");

        System.out.println("Member ID : " + pos.MemberID);
        _scenario.write("Member ID : " + pos.MemberID);

        System.out.println("First Name : "+ pos.FirstName);
        _scenario.write("First Name : "+ pos.FirstName);

        System.out.println("Last Name : "+ pos.LastName);
        _scenario.write("Last Name : "+ pos.LastName);

        System.out.println("Nationality : "+ pos.NationalityCode);
        _scenario.write("Nationality : "+ pos.NationalityCode);

        System.out.println("Mobile Phone : "+ pos.ContactNumberText);
        _scenario.write("Mobile Phone : "+ pos.ContactNumberText);

        System.out.println("Other Phone 1 : " + pos.MobileNo2);
        _scenario.write("Other Phone 1 : " + pos.MobileNo2);

        System.out.println("Other Phone 2 :"+ pos.OtherPhone2);
        _scenario.write("Other Phone 2 :"+ pos.OtherPhone2);

        System.out.println("Location of Residence : "+ pos.CountryNameText);
        _scenario.write("Location of Residence : "+ pos.CountryNameText);

        System.out.println("Email :"+ pos.EmailAddressText);
        _scenario.write("Email :"+ pos.EmailAddressText);

        System.out.println("Card No : "+ pos.CardNo);
        _scenario.write("Card No : "+ pos.CardNo);

        System.out.println("MemberStatus : "+ pos.MemberStatus);
        _scenario.write("MemberStatus : "+ pos.MemberStatus);

        System.out.println("JoinedDate : "+ pos.JoinedDate);
        _scenario.write("JoinedDate : "+ pos.JoinedDate);

        System.out.println("MembershipCardTierCode : "+ pos.MembershipCardTierCode);
        _scenario.write("MembershipCardTierCode : "+ pos.MembershipCardTierCode);
    }

    @Then("^I read POS search member values from Salesfroce for card number \"([^\"]*)\"$")
    public void iReadPOSSearchMemberValuesFromSalesfroceForCardNumber(String cardNumberTestDataTag) throws Throwable {

        String token = accessToken.getSFaccessToken();
        String CardNo = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString();

        sfdata.querySFdataForDummyMmeber(token,CardNo);
        sfdata.querySFdataforPOSsearch(token,CardNo);

        if(sfdata.isDummy__c.equalsIgnoreCase("false")) { // WHEN MEMBER IS NOT DUMMY, READ THE DETAILS AS USUAL

            Thread.sleep(1000);

            driver.switchTo().defaultContent();

            //Taking screenshot
            screenshot.takeScreenshot();

            sf.getCardStatus(); // Card Status
            sf.switchToMainFrame();
            sf.getFirstName();
            sf.getLastName();
            sf.getNationality();
            sf.getContactNumberText();  // Mobile Phone
            sf.getOtherPhone1(); //OtherPhone1
            sf.getOtherPhone2(); // OtherPhone2
            sf.getEnglishCountry(); // Location of Residence
            sf.getEmailAddressText(); // Email
            sf.switchToMemberCycleTab();
            //Taking screenshot
            screenshot.takeScreenshot();
            sf.getCardTier();  // Card Tier

        } else { //WHEN THE MEMBER IS DUMMY, ONLY READ DATA FROM MEMBER CYCLE TAB
            driver.switchTo().defaultContent();

            sf.getCardStatus(); // Card Status
            sf.switchToMemberCycleTab();
            //Taking screenshot
            screenshot.takeScreenshot();
            sf.getCardTier();  // Card Tier
        }
    }

    @Then("^I validate POS search Member values with Salesforce$")
    public void iValidatePOSSearchMemberValuesWithSalesforce() throws Throwable {
        if(sfdata.isDummy__c.equalsIgnoreCase("false")) {

            //First Name
            softAssert.assertEquals(sf.FirstName_sf, pos.FirstName, "First Name ");
            _scenario.write("=============== First Name ===============");
            _scenario.write("Actual First Name (SF) : " + sf.FirstName_sf);
            _scenario.write("Expected First Name (Response) : " + pos.FirstName);

            //Last Name
            softAssert.assertEquals(sf.LastName_sf, pos.LastName, "Last Name ");
            _scenario.write("=============== Last Name ===============");
            _scenario.write("Actual Last Name (SF) : " + sf.LastName_sf);
            _scenario.write("Expected Last Name (Response) : " + pos.LastName);

            //Nationality
            softAssert.assertEquals(sf.Nationality_sf, pos.NationalityCode, "Nationality ");
            _scenario.write("=============== Nationality ===============");
            _scenario.write("Actual Nationality (SF) : " + sf.Nationality_sf);
            _scenario.write("Expected Nationality (Response) : " + pos.NationalityCode);

            //Mobile Phone (ContactNumberText)
            softAssert.assertEquals(sf.ContactNumberText_sf, pos.ContactNumberText, "Mobile Phone (ContactNumberText) ");
            _scenario.write("=============== Mobile Phone (ContactNumberText) ===============");
            _scenario.write("Actual Mobile Phone (ContactNumberText) (SF) : " + sf.ContactNumberText_sf);
            _scenario.write("Expected Mobile Phone (ContactNumberText) (Response) : " + pos.ContactNumberText);

            //Other Phone 1
            softAssert.assertEquals(sf.OtherPhone1_sf, pos.MobileNo2, "Other Phone 1 ");
            _scenario.write("=============== Other Phone 1 ===============");
            _scenario.write("Actual Other Phone 1  (SF) : " + sf.OtherPhone1_sf);
            _scenario.write("Expected Other Phone 1 (Response) : " + pos.MobileNo2);

            //Other Phone 2
            softAssert.assertEquals(sf.OtherPhone2_sf, pos.OtherPhone2, "Other Phone 2 ");
            _scenario.write("=============== Other Phone 2 ===============");
            _scenario.write("Actual Other Phone 2  (SF) : " + sf.OtherPhone2_sf);
            _scenario.write("Expected Other Phone 2 (Response) : " + pos.OtherPhone2);

            //Location of Residence
            softAssert.assertEquals(sf.EnglishCountry_sf, pos.CountryNameText, "Location of Residence ");
            _scenario.write("=============== Location of Residence ===============");
            _scenario.write("Actual Location of Residence  (SF) : " + sf.EnglishCountry_sf);
            _scenario.write("Expected Location of Residence (Response) : " + pos.CountryNameText);

            //Email
            softAssert.assertEquals(sf.EmailAddressText_sf, pos.EmailAddressText, "Email Address ");
            _scenario.write("=============== Email Address ===============");
            _scenario.write("Actual EmailAddressText  (SF) : " + sf.EmailAddressText_sf);
            _scenario.write("Expected EmailAddressText (Response) : " + pos.EmailAddressText);

            //Member Status
            softAssert.assertTrue(pos.MemberStatus.equalsIgnoreCase(sf.cardStatus_sf), "Member Card Status ");
            _scenario.write("=============== Member Status ===============");
            _scenario.write("Actual Member Status  (SF) : " + sf.cardStatus_sf);
            _scenario.write("Expected Member Status (Response) : " + pos.MemberStatus);

            //CardTier
            softAssert.assertTrue(pos.MembershipCardTierCode.equalsIgnoreCase(sf.CardTier_sf), "CardTier ");
            _scenario.write("=============== CardTier ===============");
            _scenario.write("Actual CardTier  (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected CardTier (Response) : " + pos.MembershipCardTierCode);

            //JoinDate (validated with Start_Date__c FROM Salesforce)
            softAssert.assertEquals(sfdata.Start_Date__c, pos.JoinedDate, "JoinDate ");
            _scenario.write("=============== JoinDate ===============");
            _scenario.write("Actual JoinDate  (SF) : " + sfdata.Start_Date__c);
            _scenario.write("Expected JoinDate (Response) : " + pos.JoinedDate);

        } else {

            //CardTier
            softAssert.assertTrue(pos.MembershipCardTierCode.equalsIgnoreCase(sf.CardTier_sf), "CardTier ");
            _scenario.write("=============== CardTier ===============");
            _scenario.write("Actual CardTier  (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected CardTier (Response) : " + pos.MembershipCardTierCode);

            //Member Status
            softAssert.assertTrue(pos.MemberStatus.equalsIgnoreCase(sf.cardStatus_sf), "Member Card Status ");
            _scenario.write("=============== Member Status ===============");
            _scenario.write("Actual Member Status  (SF) : " + sf.cardStatus_sf);
            _scenario.write("Expected Member Status (Response) : " + pos.MemberStatus);

        }

        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }

    }

    @Given("^I send a POST request to create POS member with TitleCode \"([^\"]*)\" GenderCode \"([^\"]*)\" EmailAddressText \"([^\"]*)\" ContactNumberText \"([^\"]*)\" Mobile1AreaCode \"([^\"]*)\" FirstName \"([^\"]*)\" LastName \"([^\"]*)\" CardNumberTag \"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreatePOSMemberWithTitleCodeGenderCodeEmailAddressTextContactNumberTextMobileAreaCodeFirstNameLastNameCardNumberTag(String TitleCode, String GenderCode, String EmailAddressText, String ContactNumberText, String Mobile1AreaCode, String FirstName, String LastName,  String CardNumberTag) throws Throwable {
        //get the card number
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(CardNumberTag)).getAsString();

        System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = pos.getPOSCreateMemberRequestBodyWithUniqueValues(TitleCode,GenderCode,EmailAddressText,ContactNumberText,Mobile1AreaCode,FirstName,LastName,cardNumber);

        System.out.println("Request body -- " + RequestBody);

        //send the POST call and store POS member details
        pos.createMemberPOS(RequestBody);

    }


    @Then("^I validate if Member created successfully in SF and Matrix for Card Number \"([^\"]*)\"$")
    public void iValidateIfMemberCreatedSuccessfullyInSFAndMatrix(String cardNumberTag) throws Throwable {
        String cardNo = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTag)).getAsString();

        //validate member if from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberID(accessToken.getSFaccessToken(),cardNo,environment);
    }

    @Given("^I send a POST request to POS search member with multipal criteria FirstName \"([^\"]*)\" LastName \"([^\"]*)\" EmailAddressText \"([^\"]*)\" ContactNumberText \"([^\"]*)\"$")
    public void iSendAPOSTRequestToPOSSearchMemberWithMultipalCriteriaFirstNameLastName(String firstName, String lastName, String emailAddressText, String contactNumberText) throws Throwable {
        //get the card number
        //cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(CancelledMemberTestDataTag)).getAsString();

       // System.out.println("Card No : " + cardNumber);

        //create JSON request body
        RequestBody = pos.getPOSsearchMemberRequestBodyWithMultipleCriteria(firstName,lastName,emailAddressText,contactNumberText);

        System.out.println("Request body -- " + RequestBody);

        //send the POST call and store POS member details
        pos.searcheMemberPOS(RequestBody);

        try {
            Assert.assertEquals(pos.ReturnMessage.toLowerCase(), Property.API_EXPECTED_SUCCESS_MESSAGE.toLowerCase());
            System.out.println("API return message is - " + pos.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. API return message is not a success. " + e.getMessage());
        }
    }

    @Then("^I validate if POS search response card number is equal to \"([^\"]*)\"$")
    public void iValidateIfPOSSearchResponseCardNumberIsEqualTo(String cardNumberTestDataTag) throws Throwable {
        try {
            Assert.assertEquals(pos.CardNo,myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString(), "Card Number Validation - ");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Card Number from POS search API call is mismatched. " + e.getMessage());
            _scenario.write("TEST FAILED !. Card Number from POS search API call is mismatched. " + e.getMessage());
            Assert.fail("TEST FAILED !. Card Number from POS search API call is mismatched. " + e.getMessage());
        }
    }

    @Given("^I cleanup data for existing POS member for Card Number \"([^\"]*)\"$")
    public void iCleanupDataForExistingPOSMemberForCardNumber(String cardNumberTestDataTag) throws Throwable {
        //cleaning up data for the given card number only (Not reading from csv file)
        dataCleanup.cleanUpOneMemberCardData(_scenario,myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString());
    }

    @And("^I validate Custom logs for an error under ATP failure as \"([^\"]*)\" for card number \"([^\"]*)\"$")
    public void iValidateCustomLogsForAnErrorUnderATPFailureAsForCardNumber(String ExpectedCustomLogReturnMessage, String cardNumberTestDataTag) throws Throwable {
        cardNumber = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(cardNumberTestDataTag)).getAsString();

        String customLogsReturnMessage = null;
        try {
            customLogsReturnMessage = sfdata.getMessageFromCustomLogsInSFForATP(accessToken.getSFaccessToken(), cardNumber, Property.ERROR_CLASS_FOR_API_WHEN_ATP_FAILS);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get custom logs message due to Exception - " + e.getMessage());
        }

        try {
            Assert.assertEquals(customLogsReturnMessage.toLowerCase(), ExpectedCustomLogReturnMessage.toLowerCase());
            System.out.println("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
            _scenario.write("custom logs return message for the Card Number :" + cardNumber + " is - " + customLogsReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            _scenario.write("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
            Assert.fail("TEST FAILED !. Unexpected custom logs message found for POS create Member - " + e.getMessage());
        }
    }

    @Then("^I validate if POS get member return message is \"([^\"]*)\"$")
    public void iValidateIfPOSGetMemberReturnMessageIs(String ExpectedReturnMessage) throws Throwable {
        try {
            Assert.assertEquals(pos.ReturnMessage.toLowerCase(),ExpectedReturnMessage.trim().toLowerCase());
            System.out.println("POS get member return message for the Card Number :" + cardNumber + " is - " + pos.ReturnMessage);
            _scenario.write("POS get member return message for the Card Number :" + cardNumber + " is - " + pos.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("TEST !. POS get member return message mismatch - " + e.getMessage());
            _scenario.write("TEST !. POS get member return message mismatch - " + e.getMessage());
            Assert.fail("TEST !. POS get member return message mismatch - " + e.getMessage());
        }
    }
}
