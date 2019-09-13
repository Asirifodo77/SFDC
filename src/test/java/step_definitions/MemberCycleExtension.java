package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.MembershipCycleExtension_pageObjects;
import page_objects.SearchMember_pageobjects;
import property.Property;
import utilities.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class MemberCycleExtension {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public String Environment;
    public readTestData testData;
    public String cardNo="";
    public MembershipCycleExtension_pageObjects memCycle;
    public MemberValidation_pageobjects sf;
    public SearchMember_pageobjects searchMember;
    public TakeScreenshot screenshot;
    public dateOperations dateOperations;
    public String cycleEndYearExp="";
    public String cycleEndMonthExp="";
    public String remarkExp="";
    public String reasonExp="";
    public getDataFromSF sfdata;
    public getSFaccessToken accessToken;
    public String token;
    public String memberId;
    public String cycleEndDateFromMatrix;
    public String ExpectedCycleEndDate;



    public MemberCycleExtension() throws FileNotFoundException {
        this.driver = Hooks.driver;
        this._scenario = Hooks._scenario;
        Environment = System.getProperty("Environment");


        loginPage = new Login_pageobjects(driver,_scenario);
        testData = new readTestData();
        memCycle = new MembershipCycleExtension_pageObjects(driver,_scenario);
        searchMember = new SearchMember_pageobjects(driver,_scenario);
        sf = new MemberValidation_pageobjects(driver,_scenario);
        dateOperations = new dateOperations();
        screenshot = new TakeScreenshot(driver,_scenario);
        sfdata = new getDataFromSF();
        accessToken = new getSFaccessToken();

    }

    @Given("^I have a valid member with associated transaction$")
    public void iHaveAValidMemberWithAssociatedTransaction() throws Throwable {
        try {
            cardNo = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Membership_Cycle_Extension_CardNumber"));
            System.out.println("card Number in test data file - " +cardNo);
            _scenario.write("card Number in test data file - " +cardNo);
        } catch (FileNotFoundException e) {
            System.out.println("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);
            System.out.println("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);

        }catch (NullPointerException ne) {
            System.out.println("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Member_Transaction_Association_Disassociation_Card_Number'");
            System.out.println("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Member_Transaction_Association_Disassociation_Card_Number'");
            Assert.fail("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Member_Transaction_Association_Disassociation_Card_Number'");
        }

    }

    @Then("^I login to Salesforce to Extend membership cycle$")
    public void iLoginToSalesforceToExtendMembershipCycle() throws Throwable {
        loginPage.loginToSF();
    }

    @Then("^I search member using card number to Extend membership cycle$")
    public void iSearchMemberUsingCardNumberToExtendMembershipCycle(DataTable dataTable) throws Throwable {
        searchMember.searchMemberThroughCardNumber(dataTable,cardNo);
    }

    @Then("^I navigate to member cycle extension section$")
    public void iNavigateToMemberCycleExtensionSection() throws Throwable {
        //click on the link
        memCycle.clickMembershipCycleExtensionLink();

        //check if the popup is loaded
        if(memCycle.isManualCycleExtensionPopupPresent()){
            screenshot.takeScreenshot();
            System.out.println("Manual Cycle Extension Popup loaded successfully");
            _scenario.write("Manual Cycle Extension Popup loaded successfully");
        } else {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED. Manual Cycle Extension Popup is NOT loaded");
            _scenario.write("TEST FAILED. Manual Cycle Extension Popup is NOT loaded");
            Assert.fail("TEST FAILED. Manual Cycle Extension Popup is NOT loaded");
        }

    }


    @Then("^I verify if Manual cycle extension popup is loaded successfully$")
    public void iVerifyIfManualCycleExtensionPopupIsLoadedSuccessfully() throws Throwable {
        try {
            Thread.sleep(3000);
            screenshot.takeScreenshot();
            Assert.assertTrue(memCycle.isManualCycleExtensionPopupPresent());
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED. Manual cycle extension popup is NOT loaded");
            _scenario.write("TEST FAILED. Manual cycle extension popup is NOT loaded");
            Assert.fail("TEST FAILED. Manual cycle extension popup is NOT loaded");
        }
    }

    @Then("^I select value for Cycle end year dropdown$")
    public void iSelectValueForCycleEndYearDropdown() throws Throwable {
        try {
            cycleEndYearExp = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Membership_Cycle_Extension_Cycle_End_Year"));
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Year'- "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Year'- "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Year'- "+e.getLocalizedMessage());
        }

        try {
            memCycle.setCycleEndYear(cycleEndYearExp);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when selecting CycleEndYear - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when selecting CycleEndYear - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when selecting CycleEndYear - "+e.getLocalizedMessage());
        }

    }

    @Then("^I select value for Cycle end month dropdown$")
    public void iSelectValueForCycleEndMonthDropdown() throws Throwable {
        try {
            cycleEndMonthExp = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Membership_Cycle_Extension_Cycle_End_Month"));
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Month' - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Month' - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_End_Month' - "+e.getLocalizedMessage());
        }

        try {
            memCycle.setCycleEndMonth(cycleEndMonthExp);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when selecting CycleEndMonth - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when selecting CycleEndMonth - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when selecting CycleEndMonth - "+e.getLocalizedMessage());
        }
    }

    @Then("^I set value for Remarks textarea$")
    public void iSetValueForRemarksTextarea() throws Throwable {
        try {
            remarkExp = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Membership_Cycle_Extension_Cycle_Remarks"));
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Remarks' - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Remarks' - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Remarks' - "+e.getLocalizedMessage());
        }

        try {
            memCycle.setRemark(remarkExp);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when setting Remark - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when setting Remark - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when setting Remark - "+e.getLocalizedMessage());
        }
    }

    @Then("^I select value for Reason dropdown$")
    public void iSelectValueForReasonDropdown() throws Throwable {
        try {
            reasonExp = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Membership_Cycle_Extension_Cycle_Reason"));
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Reason' - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Reason' - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when reading test data 'Membership_Cycle_Extension_Cycle_Reason' - "+e.getLocalizedMessage());
        }

        try {
            memCycle.setReason(reasonExp);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when selecting Reason - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when selecting Reason - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when selecting Reason - "+e.getLocalizedMessage());
        }
    }

    @Then("^I click Next button$")
    public void iClickNextButton() throws Throwable {
        try {
            screenshot.takeScreenshot();
            memCycle.clickNext();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when clicking Next button - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when clicking Next button - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when clicking Next button - "+e.getLocalizedMessage());
        }
    }

    @Then("^I verify New cycle end date value in the popup before finishing$")
    public void iVerifyNewCycleEndDateValueInThePopupBeforeFinishing() throws Throwable {
        String concatEndMonthandYear = cycleEndMonthExp+"-"+cycleEndYearExp;

        try {
            Assert.assertEquals(memCycle.getnewCycleEndDateLabelValue(),concatEndMonthandYear);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'New cycle end date' value mismatch - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. 'New cycle end date' value mismatch - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. 'New cycle end date' value mismatch - "+e.getLocalizedMessage());
        }

    }

    @Then("^I verify Reason value in the popup before finishing$")
    public void iVerifyReasonValueInThePopupBeforeFinishing() throws Throwable {
        try {
            Assert.assertEquals(memCycle.getReasonsLabelValue(),reasonExp);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Reason' value mismatch - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. 'Reason' value mismatch - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. 'Reason' value mismatch - "+e.getLocalizedMessage());
        }
    }

    @Then("^I verify Remarks value in the popup before finishing$")
    public void iVerifyRemarksValueInThePopupBeforeFinishing() throws Throwable {
        try {
            Assert.assertEquals(memCycle.getRemarkLabelValue(),remarkExp);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Remarks' value mismatch - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. 'Remarks' value mismatch - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. 'Remarks' value mismatch - "+e.getLocalizedMessage());
        }
    }

    @Then("^I click Finish button$")
    public void iClickFinishButton() throws Throwable {
        try {
            screenshot.takeScreenshot();
            memCycle.clickFinish();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when clicking Finish button - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when clicking Finish button - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when clicking Finish button - "+e.getLocalizedMessage());
        }
    }


    @Then("^I approve all \"([^\"]*)\" requests$")
    public void iApproveAllRequests(String requestSubject) throws Throwable {
        Thread.sleep(5000);
        try {
            screenshot.takeScreenshot();
            sf.clickOnMemberRequest(requestSubject);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("Unable to navigate to Member requests");
            _scenario.write("Unable to navigate to Member requests");
        }

        try {
            sf.approveAllPendingRequests();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("Unable to approve all requests");
            _scenario.write("Unable to approve all requests");
        }
    }

    @Then("^I click on Member Cycle tab$")
    public void iClickOnMemberCycleTab() throws Throwable {

        //refreshing the page
        driver.navigate().refresh();
        Thread.sleep(5000);

        try {
            //going inside Member Cycle tab
            sf.switchToMemberCycleTab();
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Exception occurred when switching to Member Cycle tab - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Exception occurred when switching to Member Cycle tab - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Exception occurred when switching to Member Cycle tab - "+e.getLocalizedMessage());
        }
    }

    @Then("^I validate if cycle end date value in Saleforce is updated successfully$")
    public void iValidateIfCycleEndDateValueInSaleforceIsUpdatedSuccessfully() throws Throwable {
        try {
            sf.getCycleEndDate();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Unable to get Cycle End Date value from Salesforce - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Unable to get Cycle End Date value from Salesforce - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Unable to get Cycle End Date value from Salesforce - "+e.getLocalizedMessage());
        }
        // Actual value = sf.CycleEndDate_sf   returns the date in yyyy-mm-dd  format
        //Expected value = CycleEndYearExp - cycleExp month converted to month number - last date of the month
        String monthExp = null;
        try {
            monthExp = dateOperations.getIntValueOfMonth(cycleEndMonthExp);
        } catch (ParseException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Unable to convert month name into a number  - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Unable to convert month name into a number  - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Unable to convert month name into a number  - "+e.getLocalizedMessage());
        }

        String lastDateExp = null;
        try {
            lastDateExp = dateOperations.getLastDateOfMonth(cycleEndMonthExp);
        } catch (ParseException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Unable to calculate last date of given month  - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Unable to calculate last date of given month  - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Unable to calculate last date of given month  - "+e.getLocalizedMessage());
        }

        try {
            ExpectedCycleEndDate = cycleEndYearExp+"-"+monthExp+"-"+lastDateExp;
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Unable to calculate Expected Cycle EndDate - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. Unable to calculate Expected Cycle EndDate  - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. Unable to calculate Expected Cycle EndDate  - "+e.getLocalizedMessage());
        }

        try {
            Assert.assertEquals(sf.CycleEndDate_sf,ExpectedCycleEndDate);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED !. - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED !. - "+e.getLocalizedMessage());
        }
    }


    @Then("^I validate if cycle end date value in Matrix is updated successfully$")
    public void iValidateIfCycleEndDateValueInMatrixIsUpdatedSuccessfully() throws Throwable {
        //query SF and get Member ID using card number
        token = accessToken.getSFaccessToken();

        try {
            memberId = sfdata.getMemberIDFromSF(token,cardNo);

            //retrying for maximum 5 mins while the memberId is null
            int retryCount=0;
            while((memberId==null || memberId.isEmpty()) && retryCount<20){
                System.out.println("Member ID in SF is Null. Retrying in another 30 seconds");
                Thread.sleep(30000);
                memberId = sfdata.getMemberIDFromSF(token,cardNo);
                retryCount++;
            }

        } catch (Exception e) {
            System.out.println("Unable to get memberId from salesforce due to an exception - "+ e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get memberId from salesforce due to an exception - "+ e.getMessage());
        }

        //Checking if memberId is not empty
        try {
            assert memberId != null;
            Assert.assertTrue(!memberId.isEmpty());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'Member ID' in Salesforce is empty even after retrying for 10 minutes");
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'Member ID' in Salesforce is empty even after retrying for 10 minutes");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'Member ID' in Salesforce is empty even after retrying for 10 minutes");
        }

        //query matrix and get cycle end date
        try {

            //waiting for 1 minute
            System.out.println("======= Waiting for 1 minute before querying Matrix DB ==========");
            Thread.sleep(60000);
            System.out.println("=========== Start querying Matrix DB after 1 minute =============");

            //query db
            cycleEndDateFromMatrix = memCycle.getCycleEndDateFromMatrix(Environment,memberId);

            int retryCount=0;
            //retrying for max 5 minutes while cycle end date is not equal to expected cycle end date
            while (!cycleEndDateFromMatrix.substring(0,10).equalsIgnoreCase(ExpectedCycleEndDate) && retryCount<20){
                System.out.println("~~~ The 'cycle end date' value in Matrix DB is not yet updated. Retrying again in 30 seconds ~~~");
                Thread.sleep(30000);
                cycleEndDateFromMatrix = memCycle.getCycleEndDateFromMatrix(Environment,memberId);
                retryCount++;
            }

        } catch (SQLException | InterruptedException | ClassNotFoundException e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get cycle end date from matrix database due to following exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to get cycle end date from matrix database due to following exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get cycle end date from matrix database due to following exception - "+e.getMessage());
        }

        //Checking if Cycle end date is not empty
        try {
            Assert.assertTrue(!cycleEndDateFromMatrix.isEmpty());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'cycle End Date' in Matrix is empty even after retrying for 10 minutes");
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'cycle End Date' in Matrix is empty even after retrying for 10 minutes");
           Assert.fail("TEST FAILED INTENTIONALLY !. Unable to proceed with the rest of the test because the 'cycle End Date' in Matrix is empty even after retrying for 10 minutes");
        }

        //Assertion
        try {
            Assert.assertEquals(cycleEndDateFromMatrix.substring(0,10),ExpectedCycleEndDate);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Cycle end date in matrix is not matching with updated cycle end date after retrying for 10 minutes. "+e.getMessage());
            _scenario.write("TEST FAILED !. Cycle end date in matrix is not matching with updated cycle end date after retrying for 10 minutes. "+e.getMessage());
            Assert.fail("TEST FAILED !. Cycle end date in matrix is not matching with updated cycle end date after retrying for 10 minutes. "+e.getMessage());
        }
    }
}
