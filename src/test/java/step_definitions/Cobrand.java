package step_definitions;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.JsonObject;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import page_objects.Cobrand.Cobrand_pageObjects;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.SearchMember_pageobjects;
import property.Property;
import utilities.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;

public class Cobrand {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Cobrand_pageObjects cobrand;
    public String requestBody;
    public String Environment;
    public readTestData testData;
    public testDataCleanup dataCleanup;
    public SearchMember_pageobjects memberSearch;
    public MemberValidation_pageobjects member;
    public TakeScreenshot screenshot;
    public boolean CobrandCheckboxStatus;
    public DataCleanup utilities;
    public getSFaccessToken token;
    public String memberId;
    public HashMap<String, String> matrixData;
    public HashMap<String, String> matrixData2;
    public DSAcreateMember_pageObjects matrixOperations;
    public getDataFromSF sfUtilities;
    public String NonPurchaseFlag;

    public Cobrand() throws Exception {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        Environment = System.getProperty("Environment");

        //test data object
        testData = new readTestData();

        //creating the page object of cobrand
        cobrand = new Cobrand_pageObjects(_scenario);

        //data cleanup object
        dataCleanup = new testDataCleanup();

        //search member object
        memberSearch = new SearchMember_pageobjects(driver,_scenario);

        //member object
        member = new MemberValidation_pageobjects(driver,_scenario);

        screenshot = new TakeScreenshot(driver,_scenario);

        utilities = new DataCleanup(_scenario);

        token = new getSFaccessToken();

        matrixOperations = new DSAcreateMember_pageObjects(_scenario);

        sfUtilities = new getDataFromSF();
    }

    @Given("^I read Create Cobrand member request body from json file$")
    public void iReadCreateCobrandMemberRequestBodyFromJsonFile() throws Throwable {
        requestBody = cobrand.readRequestFromCreateCobrandMmeberJsonFile(testData.readTestData(cobrand.getCobrandCreateMemberJsonPath(Environment)));

        System.out.println("=========START OF JSON REQUEST BODY =============");
        System.out.println(requestBody);
        System.out.println("=========END OF JSON REQUEST BODY ===============");

        cobrand.getElementValuesFromCobrandJsonFile(Environment);
        System.out.println("File Ref : "+cobrand.FileRef);
    }
    @Given("^I create Cobrand member using API call$")
    public void iCreateCobrandMemberUsingAPICall() throws Throwable {
        cobrand.createCobrandMember(requestBody);
    }


    @Then("^I search member using first name and last name$")
    public void iSearchMemberUsingFirstNameAndLastName() throws Throwable {

        try {
            memberSearch.clickAdvancedMemberSearch();
        } catch (Exception e) {
            System.out.println("Unable to click on Advanced member search tab. "+e.getMessage());
        }

        try {
            memberSearch.setFirstName(cobrand.FirstName);
        } catch (Exception e) {
            System.out.println("Unable to set first name. "+e.getMessage());
        }

        try {
            memberSearch.setLastName(cobrand.LastName);
        } catch (Exception e) {
            System.out.println("Unable to set Last name. "+e.getMessage());
        }

        try {
            memberSearch.clickAdvancedSearchButton();
        } catch (Exception e) {
            System.out.println("Unable to click on advanced search button. "+e.getMessage());
        }
    }

    @Then("^I read first name and last name in Salesforce$")
    public void iReadFirstNameAndLastNameInSalesforce() throws Throwable {

        //Switch to frame
        try {
            member.switchToMainFrame();
        } catch (InterruptedException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to switch to MainFrame due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to Unable to switch to MainFrame due to an exception - "+e.getMessage());
        }

        //get first name

        try {
            member.getFirstName();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'First Name' due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'First Name' due to an exception - "+e.getMessage());
        }

        //get last name
        try {
            member.getLastName();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Last Name' due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Last Name' due to an exception - "+e.getMessage());
        }

    }

    @Then("^I validate first name and last name with API request$")
    public void iValidateFirstNameAndLastNameWithAPIRequest() throws Throwable {
        try {
            Assert.assertEquals(member.FirstName_sf.toLowerCase(),cobrand.FirstName.toLowerCase());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. First Name mismatch in Salesforce and Json file. "+e.getMessage());
            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);
            Assert.fail("TEST FAILED !. First Name mismatch in Salesforce and Json file. "+e.getMessage());
        }

        try {
            Assert.assertEquals(member.LastName_sf.toLowerCase(),cobrand.LastName.toLowerCase());
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Last Name mismatch in Salesforce and Json file. "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. Last Name mismatch in Salesforce and Json file. "+e.getMessage());
        }
    }

    @Then("^I validate if cobrand indicator checkbox is checked$")
    public void iValidateIfCobrandIndicatorCheckboxIsChecked() throws Exception {
        try {
            Assert.assertTrue(CobrandCheckboxStatus);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED ! Cobrand Indicator checkbox is not checked");

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED ! Cobrand Indicator checkbox is not checked");

        }
    }

    @Then("^I cleanup data of created Cobrand member from SF and MatrixDB$")
    public void iCleanupDataOfCreatedCobrandMemberFromSFAndMatrixDB() throws Throwable {
        dataCleanup.cleanupCobrandMemberData(_scenario,member.mem_Card_Num_sf,cobrand.FileRef);
    }

    @Then("^I validate cobrand indicator value in matrix$")
    public void iValidateCobrandIndicatorValueInMatrix() throws Throwable {

        //VALIDATING IF RECORD IS PROCESSED WITHOUT ERROR IN CMD LOGS
        String ReturnMessageFromCmdLogs=null;
        try {
            ReturnMessageFromCmdLogs = matrixOperations.getReturnMessageFromCmdLogsInMatrix(Environment,member.mem_Card_Num_sf);
        } catch (SQLException e) {
            System.out.println("Unable to get data from Matrix Custom Log. "+e.getMessage());
            _scenario.write("Unable to get data from Matrix Custom Log. "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("Unable to get data from Matrix Custom Log. "+e.getMessage());
        }

        try {
            Assert.assertEquals(ReturnMessageFromCmdLogs.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+" .The record is successfully processed in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
            _scenario.write("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
        }


        //GETTING THE MEMBER ID FROM SF.
        try {
            memberId = sfUtilities.getMemberIDFromSF(token.getSFaccessToken(),member.mem_Card_Num_sf);
            System.out.println("Member Id : "+memberId);
        } catch (Exception e) {
            System.out.println("Unable to get member id from SF for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member id from SF for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());
        }

        //getting data from matrix
        try {
            matrixData = cobrand.readCobrandDataFromMatrix(Environment,cobrand.getCobrandIndicatorQuery(memberId));
            System.out.println("Cobrand Indicator flag in Matrix : "+matrixData.get("GenericStrAry11"));
        } catch (SQLException e) {
            System.out.println("Unable to get Cobrand Indicator value from Matrix for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get Cobrand Indicator value from Matrix for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());
        }

        //validating if cobrand indicator is true in matrix
        try {
            Assert.assertEquals(matrixData.get("GenericStrAry11").toLowerCase(),"true");
            System.out.println("The Cobrand indicator value in Matrix is set to 'true'");
            _scenario.write("The Cobrand indicator value in Matrix is set to 'true'");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Cobrand indicator value in Matrix is not set to 'true' . The actual value is - "+matrixData.get("GenericStrAry11"));
            _scenario.write("TEST FAILED !. Cobrand indicator value in Matrix is not set to 'true' . The actual value is - "+matrixData.get("GenericStrAry11"));

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. Cobrand indicator value in Matrix is not set to 'true' . The actual value is - "+matrixData.get("GenericStrAry11"));
        }

    }

    @Then("^I validate Non purchase checkbox value in Dev console$")
    public void iValidateNonPurchaseCheckboxValueInDevconsole() throws Throwable {

        //getting the Non purchase flag's value from SF
        try {
            NonPurchaseFlag = cobrand.getNonPurchaseFlagFromSF(member.mem_Card_Num_sf);
            System.out.println("Non Purchase Flag Value - "+NonPurchaseFlag);
        } catch (Exception e) {
            System.out.println("Unable to get NonPurchaseFlag value from Salesforce for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get NonPurchaseFlag value from Salesforce for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());
        }

        //validate if Non purchase flag value is 'true'
        try {
            Assert.assertEquals(NonPurchaseFlag.toLowerCase(),"true");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Non purchase flag value in SF is not set to 'true' . The actual value is - "+NonPurchaseFlag);
            _scenario.write("TEST FAILED !. Non purchase flag value in SF is not set to 'true' . The actual value is - "+NonPurchaseFlag);

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. Non purchase flag value in SF is not set to 'true' . The actual value is - "+NonPurchaseFlag);
        }

    }

    @Then("^I validate Movement type and Remarks values in Salesforce$")
    public void iValidateMovementTypeAndRemarksValuesInSalesforce() throws Throwable {
        //getting the Cycle id related information
        try {
            cobrand.getMembershipCycleDetailsFromSF(member.mem_Card_Num_sf);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get Membership cycle details from SF. Exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get Membership cycle details from SF. Exception - "+e.getMessage());
        }

        //validating the Movement type in SF.
        try {
            Assert.assertEquals(cobrand.MovementType.toLowerCase(),Property.EXPECTED_COBRAND_MOVEMENT_TYPE_SF_VALUE.toLowerCase());
            System.out.println("'Movement Type' is validated successfully");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());
        }


        //validating the Remarks in SF.
        try {
            Assert.assertEquals(cobrand.Remarks.toLowerCase(),Property.EXPECTED_COBRAND_REMARKS_VALUE.toLowerCase());
            System.out.println("'Remarks' is validated successfully");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());
        }


    }

    @Then("^I validate Movement type and Remarks values in Matrix$")
    public void iValidateMovementTypeAndRemarksValuesInMatrix() throws Throwable {

        //getting data from matrix
        try {
            matrixData2 = cobrand.readCobrandDataFromMatrix(Environment,cobrand.getMovementTypeandRemarkQuery(memberId));
            System.out.println("Movement Type in Matrix : "+matrixData2.get("MovementType"));
            System.out.println("Remarks in Matrix : "+matrixData2.get("Remarks"));
        } catch (SQLException e) {
            System.out.println("Unable to get 'Movement Type' and 'Remark' values from Matrix for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Movement Type' and 'Remark' values from Matrix for the card number "+member.mem_Card_Num_sf+" due to an exception - "+e.getMessage());
        }

        //validating the Movement type in matrix.
        try {
            Assert.assertEquals(matrixData2.get("MovementType").toLowerCase(),Property.EXPECTED_COBRAND_MOVEMENT_TYPE_MATRIX_VALUE.toLowerCase());
            System.out.println("'Movement Type' is validated successfully");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. 'Movement type' value Mismatch "+e.getMessage());
        }


        //validating the Remarks in matrix.
        try {
            Assert.assertEquals(matrixData2.get("Remarks").toLowerCase(),Property.EXPECTED_COBRAND_REMARKS_VALUE.toLowerCase());
            System.out.println("'Remarks' is validated successfully");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED !. 'Remarks' value Mismatch "+e.getMessage());
        }

    }

    @Then("^I read Cobrand Indicator status in Salesforce$")
    public void iReadCobrandIndicatorStatusInSalesforce() throws Exception {


        //reading cobrand indicator status
        try {
            CobrandCheckboxStatus = member.getCobrandIndicatorStatus();
            System.out.println("Cobrand Indicator Status : "+CobrandCheckboxStatus);
        } catch (WebDriverException we) {
            System.out.println(we.getMessage());
            System.out.println("===== Exception was caught at Main Level ======");
            screenshot.takeScreenshot();
            if(memberSearch.isAdvanceSearchButtonVisible()){   //When the search popup is still present
                System.out.println("Looking if search popup is still opened");
                cobrand.showErrorMessageWhenMultipleSearchResultsFound(cobrand,dataCleanup,screenshot);
            } else {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED INTENTIONALLY !. Unable to get Cobrand Indicator Status from Salesforce due to exception - "+we.getMessage());
                cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get Cobrand Indicator Status from Salesforce due to exception - "+we.getMessage());
            }
        }
    }

    @Then("^I read card number from Salesfroce$")
    public void iReadCardNumberFromSalesfroce() throws Throwable {

        //going inside Member Cycle tab
        try {
            member.switchToMemberCycleTab();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get switch to 'Member Cycle' tab due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get switch to 'Member Cycle' tab due to an exception - "+e.getMessage());
        }

        //Taking screenshot
        screenshot.takeScreenshot();

        //Get card number
        try {
            member.getMemberCardNumber();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Card Number' due to an exception - "+e.getMessage());

            cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Card Number' due to an exception - "+e.getMessage());
        }
    }
}
