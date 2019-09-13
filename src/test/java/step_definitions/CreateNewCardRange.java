package step_definitions;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.JsonObject;
import commonLibs.implementation.JavaScriptExecutor;
import commonLibs.implementation.checkBoxControls;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import page_objects.*;
import property.Property;
import utilities.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewCardRange {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public DSAcreateMember_pageObjects DSAcreateMember;
    public SearchMember_pageobjects searchMember;
    public CardRange_pageObjects cardRange;
    public String POSTrequestBody;
    public MemberValidation_pageobjects member;
    public selectBoxControls selectBox;
    public textBoxControls textBox;
    public checkBoxControls checkBox;
    public Runtime_TestData runtime;
    public createDbConnection dbConnection;
    public readTestData testData;
    public SoftAssert softAssert;
    public testDataCleanup dataCleanup;
    public TakeScreenshot screenshot;
    public String storeLocationExp="";
    public String TierExp="";
    public String MinimumValueExp="";
    public String MaximumValueExp="";
    public String RemarksExp="";
    public String CardRangeNameValueExp="";
    public HashMap<String,String> stagingData;
    public getDataFromStagingDB staging;
    public deleteDataFromMatrixDB delete;
    public String Environment;
    public ArrayList MatrixRowList;
    public getDataFromStagingDB matrix;
    public String token="";
    public String cardRangeName="";
    public JavaScriptExecutor javaScriptExecutor;


    public CreateNewCardRange() throws FileNotFoundException {
        this.driver = Hooks.driver;
        this._scenario = Hooks._scenario;
        Environment = System.getProperty("Environment");

        //creating loginPage object
        loginPage = new Login_pageobjects(driver,_scenario);
        cardRange = new CardRange_pageObjects(driver,_scenario);
        testData = new readTestData();
        stagingData = new HashMap<>();
        staging = new getDataFromStagingDB(_scenario);
        matrix = new getDataFromStagingDB(_scenario);
        delete = new deleteDataFromMatrixDB(_scenario);
        MatrixRowList = new ArrayList<>();
        javaScriptExecutor = new JavaScriptExecutor(driver);
        screenshot = new TakeScreenshot(driver,_scenario);

        //reading the min and max card values from test data
        if (Environment.equalsIgnoreCase("Preprod")) {
            MinimumValueExp = testData.readTestData("Create_New_Card_Range_MinimumValue_Preprod");
        } else if (Environment.equalsIgnoreCase("QACore2")){
            MinimumValueExp = testData.readTestData("Create_New_Card_Range_MinimumValue_QACore2");
        }


        if (Environment.equalsIgnoreCase("Preprod")) {
            MaximumValueExp = testData.readTestData("Create_New_Card_Range_MaximumValue_Preprod");
        } else if (Environment.equalsIgnoreCase("QACore2")) {
            MaximumValueExp = testData.readTestData("Create_New_Card_Range_MaximumValue_QACore2");
        }

    }

    @Given("^If previous test data is deleted from Matrix$")
    public void ifPreviousTestDataIsDeletedFromMatrix() throws Throwable {


        delete.deleteCardRangeFromStaging(Environment,MinimumValueExp,MaximumValueExp);
        delete.deleteCardRangeromMatrix(Environment,MinimumValueExp,MaximumValueExp);


    }

    @Given("^I login to Salesforce to delete existing card range$")
    public void iLoginToSalesforceToDeleteExistingCardRange() throws Throwable {
        loginPage.loginToSF();
    }



    @Given("^I navigate to create new card range tab$")
    public void iNavigateToCreateNewCardRangeTab() throws Throwable {
        //clicking on card range tab
        try {
            Thread.sleep(5000);
            cardRange.clickOnCardRangeTab();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab due to an Webdriver exception -"+e.getMessage());
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab due to an exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Card Range' tab due to an exception -"+e.getMessage());
        }

    }

    @Given("^I sort data by All tag$")
    public void iSortDataByAllTag() throws Throwable {
        //checking if left side filter value is set to All or else set it to All
        Thread.sleep(3000);
        cardRange.filterAllRecords();

        //set records in order (this will compare the minimum value of the first row and decide if the records are orderd or not and then click on the created date header to sort it out
        Thread.sleep(3000);
        cardRange.sortTheRecordOrder(MinimumValueExp);
    }

    @Then("^I select create new card range option$")
    public void iSelectCreateNewCardRangeOption() throws Throwable {

        //clicking on new card range button
        try {
            Thread.sleep(5000);
            cardRange.clickOnNewCardRangeButton();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button due to an Webdriver exception -"+e.getMessage());
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button due to an exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'New Card Range' button due to an exception -"+e.getMessage());
        }
    }

    @Given("^If create new card range popup is loaded correctly$")
    public void ifCreateNewCardRangePopupIsLoadedCorrectly() throws Throwable {
        try {
            Thread.sleep(2000);
            Assert.assertTrue(cardRange.isNewCardRangePopPresent(), "Verifying if New Card Range Popup is loaded successfully");
            screenshot.takeScreenshot();

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. New Card Range Popup was not loaded successfully  - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! New Card Range Popup was not loaded successfully  - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! New Card Range Popup was not loaded successfully");
        }
    }

    @Then("^I select Store location$")
    public void iSelectStoreLocation() throws Throwable {
        if(Environment.equalsIgnoreCase("Preprod")){
            storeLocationExp = testData.readTestData("Create_New_Card_Range_StoreLocation_Preprod");
        } else if (Environment.equalsIgnoreCase("QACore2")){
            storeLocationExp = testData.readTestData("Create_New_Card_Range_StoreLocation_QACore2");
        }

        try {
            cardRange.selectStoreLocation(storeLocationExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select  'Store Location' dropdown in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select  'Store Location' dropdown in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select  'Store Location' dropdown due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select  'Store Location' dropdown due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to select 'Store Location' due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to select 'Store Location' due to an exception. "+e.getMessage());
        }
    }

    @Then("^I select Tier$")
    public void iSelectTier() throws Throwable {
        if (Environment.equalsIgnoreCase("Preprod")) {
            TierExp = testData.readTestData("Create_New_Card_Range_Tier_Preprod");
        } else  if (Environment.equalsIgnoreCase("QACore2")){
            TierExp = testData.readTestData("Create_New_Card_Range_Tier_QACore2");
        }

        try {
            cardRange.selectTier(TierExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select  'Tier' dropdown in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select  'Tier' dropdown in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select 'Tier' dropdown due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select 'Tier' dropdown due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to select 'Tier' due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to select 'Tier' due to an exception. "+e.getMessage());
        }
    }

    @Then("^I enable Manually choose range checkbox$")
    public void iEnableManuallyChooseRangeCheckbox() throws Throwable {
        try {
            cardRange.enableManuallyChooseRangeCheckbox();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select  'Manually Choose Range' checkbox in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select  'Manually Choose Range' checkbox in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to select 'Manually Choose Range' checkbox due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to select 'Manually Choose Range' checkbox due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to enable 'Manually Choose Range' Checkbox due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to enable 'Manually Choose Range' Checkbox due to an exception. "+e.getMessage());
        }
    }


    @Then("^I type MinimumValue$")
    public void iTypeMinimumValue() throws Throwable {

        try {
            Thread.sleep(2000);
            cardRange.setMinimumValue(MinimumValueExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set  'Minimum Value' in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set  'Minimum Value' in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Minimum Value' due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Minimum Value' due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to set 'Minimum Value' value due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to set 'Minimum Value' value due to an exception. "+e.getMessage());
        }
    }

    @Then("^I type Maximum value$")
    public void iTypeMaximumValue() throws Throwable {

        try {
            Thread.sleep(2000);
            cardRange.setMaximumValue(MaximumValueExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set  'Maximum Value' in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set  'Maximum Value' in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Maximum Value' due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Maximum Value' due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to set  'Maximum Value' value due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to set  'Maximum Value' value due to an exception. "+e.getMessage());
        }
    }

    @Then("^I type Remark$")
    public void iTypeRemark() throws Throwable {
        if (Environment.equalsIgnoreCase("Preprod")) {
            RemarksExp = testData.readTestData("Create_New_Card_Range_Remarks_Preprod");
        } else if (Environment.equalsIgnoreCase("QACore2")) {
            RemarksExp = testData.readTestData("Create_New_Card_Range_Remarks_QACore2");
        }

        try {
            cardRange.setRemarksValue(RemarksExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Remarks' in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Remarks' in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Remarks' due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Remarks' due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to set 'Remarks' value due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to set 'Remarks' value due to an exception. "+e.getMessage());
        }
    }

    @Then("^I click on Next button$")
    public void iClickOnNextButton() throws Throwable {
        try {
            cardRange.clickNext();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click  'Next' button in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click  'Next' button in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click  'Next' button due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click  'Next' button due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to click  'Next' button due to an exception. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to click  'Next' button due to an exception. "+e.getMessage());
        }
    }

    @Given("^If next new card range popup is loaded correctly$")
    public void ifNextNewCardRangePopupIsLoadedCorrectly() throws Throwable {
        try {
            Assert.assertTrue(cardRange.isNewCardRangePopPresent(), "Verifying if New Card Range Popup 2 is loaded successfully");
            System.out.println("New Card Range Popup 2 was loaded successfully" );
            _scenario.write("New Card Range Popup 2 was  loaded successfully" );
            screenshot.takeScreenshot();
        } catch (WebDriverException we){
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! New Card Range Popup is not present. The exception - "+we.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! New Card Range Popup is not present. The exception - "+we.getMessage());
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("New Card Range Popup 2 was not loaded successfully  - "+e.getLocalizedMessage());
            _scenario.write("New Card Range Popup 2 was not loaded successfully  - "+e.getLocalizedMessage());
            Assert.fail("Failing the test purposefully");
        }
    }

    @Then("^I validate Tier label value$")
    public void iValidateTierLabelValue() throws Throwable {
        String tierValLabel = cardRange.getTierValue();
        System.out.println("Actual Tier (Label) : "+tierValLabel);
        System.out.println("Expected Tier (Label) : "+TierExp);
        _scenario.write("Actual Tier (Label) : "+tierValLabel);
        _scenario.write("Expected Tier (Label) : "+TierExp);

        try {
            Assert.assertEquals(tierValLabel,TierExp);
            screenshot.takeScreenshot();
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED  ! 'Tier' value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED  ! 'Tier' value mismatch - "+e.getMessage());
        }
    }

    @Then("^I validate Minimum value label value$")
    public void iValidateMinimumValueLabelValue() throws Throwable {
        String minimumValLabel = cardRange.getMinimumValue();
        System.out.println("Actual Minimum Value (Label) : "+minimumValLabel);
        System.out.println("Expected Minimum Value (Label) : "+MinimumValueExp);
        _scenario.write("Actual Minimum Value (Label) : "+minimumValLabel);
        _scenario.write("Expected Minimum Value (Label) : "+MinimumValueExp);

        try {
            Assert.assertEquals(minimumValLabel,MinimumValueExp);
            screenshot.takeScreenshot();
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED  ! 'Minimum value' value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED  ! 'Minimum value' value mismatch - "+e.getMessage());
        }
    }

    @Then("^I validate Maximum value label value$")
    public void iValidateMaximumValueLabelValue() throws Throwable {
        String maximumValLabel = cardRange.getMaximumValue();
        System.out.println("Actual Maximum Value (Label) : "+maximumValLabel);
        System.out.println("Expected Maximum Value (Label) : "+MaximumValueExp);
        _scenario.write("Actual Maximum Value (Label) : "+maximumValLabel);
        _scenario.write("Expected Maximum Value (Label) : "+MaximumValueExp);

        try {
            Assert.assertEquals(maximumValLabel,MaximumValueExp);
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED  ! 'Maximum value' value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED  ! 'Maximum value' value mismatch - "+e.getMessage());
        }
    }

    @Then("^I validate Store Location label value$")
    public void iValidateStoreLocationLabelValue() throws Throwable {
        String storeLocationValLabel = cardRange.getStoreLocationValue();
        System.out.println("Actual Store Location Value (Label) : "+storeLocationValLabel);
        System.out.println("Expected Store Location Value (Label) : "+storeLocationExp);
        _scenario.write("Actual Store Location Value (Label) : "+storeLocationValLabel);
        _scenario.write("Expected Store Location Value (Label) : "+storeLocationExp);

        try {
            Assert.assertEquals(storeLocationValLabel,storeLocationExp);
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED  ! 'Store Location' value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED  ! 'Store Location' value mismatch - "+e.getMessage());
        }
    }

    @Then("^I validate Remark label value$")
    public void iValidateRemarkLabelValue() throws Throwable {
        String remarksValLabel = cardRange.getRemarksValue();
        System.out.println("Actual Remarks Value (Label) : "+remarksValLabel);
        System.out.println("Expected Remarks Value (Label) : "+RemarksExp);
        _scenario.write("Actual Remarks Value (Label) : "+remarksValLabel);
        _scenario.write("Expected Remarks Value (Label) : "+RemarksExp);

        try {
            Assert.assertEquals(remarksValLabel,RemarksExp);
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED  ! 'Remarks' value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED  ! 'Remarks' value mismatch - "+e.getMessage());
        }
    }

    @Then("^I click on Finish button$")
    public void iClickOnFinishButton() throws Throwable {
        try {
            cardRange.clickFinish();
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to click on 'Finish' button due to an exception- "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY  ! Unable to click on 'Finish' button due to an exception- "+e.getMessage());
        }
    }

    @Given("^If created date column header is loaded correctly$")
    public void ifCreatedDateColumnHeaderIsLoadedCorrectly() throws Throwable {
        try {
            Assert.assertTrue(cardRange.isCreatedDateHeaderPresent());
            System.out.println("date column header is loaded correctly");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("date column header is NOT loaded correctly");
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Then("^I click on created date column header to order records by date$")
    public void iClickOnCreatedDateColumnHeaderToOrderRecordsByDate() throws Throwable {

        //check if table is getting loaded successfully
        try {
            if(cardRange.isCreatedDateHeaderPresent()){
                System.out.println("created date column header is loaded successfully");
                _scenario.write("created date column header is loaded successfully");
                screenshot.takeScreenshot();
            }
            //Assert.assertTrue(cardRange.isCreatedDateHeaderPresent());

        } catch (ElementNotFoundException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Card range Data table is not loaded due to an exception  - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! Card range Data table is not loaded due to an exception  - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Card range Data table is not loaded due to an exception - "+e.getLocalizedMessage());
        }

        //checking if left side filter value is set to All or else set it to All
        Thread.sleep(3000);
        cardRange.filterAllRecords();

        //set records in order (this will compare the minimum value of the first row and decide if the records are orderd or not and then click on the created date header to sort it out
        Thread.sleep(3000);
        cardRange.sortTheRecordOrder(MinimumValueExp);


    }

    @Then("^I obtain card range name from table$")
    public void iObtainCardRangeNameFromTable() throws Throwable {
        try {
            CardRangeNameValueExp = cardRange.getCardRangeNameaFromTabel();
            System.out.println("Card Range Name - "+CardRangeNameValueExp);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Card Range Name' element has changed - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! Card Range Name' element has changed - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Card Range Name' element has changed - "+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to obtain 'Card range name' from salesforce due to an Webdriver exception - "+e.getLocalizedMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! Unable to obtain 'Card range name' from salesforce due to an Webdriver exception- "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to obtain 'Card range name' from salesforce due to an Webdriver exception- "+e.getLocalizedMessage());
        }
    }

    @Then("^I validate Store Location value in table$")
    public void iValidateStoreLocationValueInTable() throws Throwable {

        Thread.sleep(3000);

        //GETTING THE STORE LOCATION VALUE
        String storeLocationTabelVal = null;
        try {
            storeLocationTabelVal = cardRange.getStoreLocationFromTabel();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Store Location' element has changed. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Store Location' element has changed  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Store Location' element has changed - "+e.getMessage());
        } catch (NullPointerException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Store Location' value is null. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Store Location' value is null  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Store Location' value is null  - "+e.getMessage());
        }

        //Assertion of 'store location' value
        try {
            System.out.println("Actual Store Location (Table) : "+storeLocationTabelVal);
            System.out.println("Expected Store Location (Table) : "+storeLocationExp);
            _scenario.write("Actual Store Location (Table) : "+storeLocationTabelVal);
            _scenario.write("Expected Store Location (Table) : "+storeLocationExp);

            Assert.assertEquals(storeLocationTabelVal,storeLocationExp);

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Store Location' value mismatch  - "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Store Location' value mismatch  - "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Store Location' value mismatch  - "+e.getMessage());
        }
    }

    @Then("^I validate Tier value in table$")
    public void iValidateTierValueInTable() throws Throwable {

        //Getting the 'Tier' value
        String tierTabelVal = null;
        try {
            tierTabelVal = cardRange.getTierFromTabel();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Tier' element has changed. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Tier' element has changed  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Tier' element has changed - "+e.getMessage());
        } catch (NullPointerException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Tier' value is null. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Tier' value is null  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Tier' value is null  - "+e.getMessage());
        }

        //Assertion of Tier value
        try {
            System.out.println("Actual Tier (Table) : "+tierTabelVal);
            System.out.println("Expected Tier (Table) : "+TierExp);
            _scenario.write("Actual Tier (Table) : "+tierTabelVal);
            _scenario.write("Expected Tier (Table) : "+TierExp);
            Assert.assertEquals(tierTabelVal,TierExp);

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Tier' value mismatch  - "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Tier' value mismatch  - "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Tier' value mismatch  - "+e.getMessage());
        }
    }

    @Then("^I validate Minimum Value in table$")
    public void iValidateMinimumValueInTable() throws Throwable {

        //Getting Minimum value
        String minimumValueTabelVal = null;
        try {
            minimumValueTabelVal = cardRange.getMinimumValueFromTabel();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Minimum Value' element has changed. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Minimum Value' element has changed  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Minimum Value' element has changed - "+e.getMessage());
        } catch (NullPointerException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Minimum Value'  is null. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Minimum Value'  is null  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Minimum Value' is null  - "+e.getMessage());
        }

        try {
            System.out.println("Actual Minimum Value (Table) : "+minimumValueTabelVal);
            System.out.println("Expected Minimum Value (Table) : "+MinimumValueExp);
            _scenario.write("Actual Minimum Value (Table) : "+minimumValueTabelVal);
            _scenario.write("Expected Minimum Value (Table) : "+MinimumValueExp);

            Assert.assertEquals(minimumValueTabelVal,MinimumValueExp);

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Minimum Value' value mismatch  - "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Minimum Value' value mismatch  - "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Minimum Value' value mismatch  - "+e.getMessage());
        }
    }

    @Then("^I validate Maximum value in table$")
    public void iValidateMaximumValueInTable() throws Throwable {

        String maximumValueTabelVal = null;
        try {
            maximumValueTabelVal = cardRange.getMaximumValueFromTabel();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Maximum Value' element has changed. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Maximum Value' element has changed  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Maximum Value' element has changed - "+e.getMessage());
        } catch (NullPointerException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Maximum Value'  is null. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Maximum Value'  is null  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Maximum Value' is null  - "+e.getMessage());
        }

        try {

            System.out.println("Actual Maximum Value (Table) : "+maximumValueTabelVal);
            System.out.println("Expected Maximum Value (Table) : "+MaximumValueExp);
            _scenario.write("Actual Maximum Value (Table) : "+maximumValueTabelVal);
            _scenario.write("Expected Maximum Value (Table) : "+MaximumValueExp);

            Assert.assertEquals(maximumValueTabelVal,MaximumValueExp);

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Maximum Value' value mismatch  - "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Maximum Value' value mismatch  - "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Maximum Value' value mismatch  - "+e.getMessage());
        }
    }

    @Then("^I delete the card range from Salesforce UI$")
    public void iDeleteTheCardRangeFromSalesforceUI() throws Throwable {
        cardRange.clickOnRecordOptionsPulldownMenu();
        cardRange.clickOnDeleteOptionInPulldownMenu();
        cardRange.deleteCardRangeRecord();
    }

    @Then("^I check if Status is Enabled$")
    public void iCheckIfStatusIsEnabled() throws Throwable {
        try {
            Assert.assertEquals(cardRange.getStatusFromTabel(),"Enable");
            System.out.println("Card Range status is Enable");
            _scenario.write("Card Range status is Enable");

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Status is not 'Enable'");
            _scenario.write("TEST FAILED !. Status is not 'Enable'");
            Assert.fail("TEST FAILED !. Status is not 'Enable'");
        }
    }

    @Then("^I validate Created Date value is equal to today$")
    public void iValidateCreatedDateValueIsEqualToToday() throws Throwable {

        //Getting actual created date from SF
        String createdDateFromTable = null;
        try {
            createdDateFromTable = cardRange.getCreatedDateFromTabel();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Created Date' element has changed. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Created Date' element has changed  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Created Date' element has changed - "+e.getMessage());
        } catch (NullPointerException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. 'Created Date'  is null. - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. 'Created Date'  is null  - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. 'Created Date' is null  - "+e.getMessage());
        }

        //calculating today's date
        String todaysDate = null;
        try {
            todaysDate = cardRange.getTodaysDate();
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Automation script was unable to calculate today's date due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Automation script was unable to calculate today's date due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Automation script was unable to calculate today's date due to an exception - "+e.getMessage());
        }

        //Assertion
        try {
            Assert.assertEquals(createdDateFromTable,todaysDate);
            System.out.println("created date is equal to today's date ");
            _scenario.write("created date is equal to today's date ");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. created date is not equal to today's date - "+e.getMessage());
            _scenario.write("TEST FAILED !. created date is not equal to today's date - "+e.getMessage());
            Assert.fail("TEST FAILED !. created date is not equal to today's date - "+e.getMessage());
        }
    }

    @Then("^I validate Remark value in table$")
    public void iValidateRemarkValueInTable() throws Throwable {
        try {
            Assert.assertEquals(cardRange.getRemarksFromTabel(),RemarksExp);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Remark'  value mismatch - "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Remark'  value mismatch - "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Remark'  value mismatch - "+e.getMessage());
        }
    }

    @Then("^I query DB staging table and check if there is a record for given card range$")
    public void iQueryDBStagingTableAndCheckIfThereIsARecordForGivenCardRange() throws Throwable {
        try {

            System.out.println("~~~~~~~~~~~~~ Waiting for 1 minute before querying Matrix table ~~~~~~~~~~~");
            Thread.sleep(60000);
            System.out.println("~~~~~~~~ Continuing to query Matrix table after waiting for 1 minute ~~~~~~");

            stagingData = staging.readIsProcessedFromStagingDB(Environment,MinimumValueExp,MaximumValueExp);

        } catch (InterruptedException | ClassNotFoundException | SQLException e) {
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to read Is Processed status from Staging DB due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to read Is Processed status from Staging DB due to an exception - "+e.getMessage());
        }

//        while (stagingData.get("isProcessed")==null || stagingData.get("isProcessed").equalsIgnoreCase("null")){
//            System.out.println("Retrying to get isProcessed value since it is "+stagingData.get("isProcessed"));
//            stagingData = staging.readIsProcessedFromStagingDB(Environment,MinimumValueExp,MaximumValueExp);
//        }

        if(stagingData!=null){

            if(stagingData.get("isProcessed")==null ) {
                System.out.println("isProcessed value NOT found in the database");
                _scenario.write("isProcessed value NOT found in the database");
                Assert.fail("TEST FAILED INTENTIONALLY !. isProcessed value NOT found in the database");
            }

            if(stagingData.get("ErrorMessage")==null ) {
                System.out.println("ErrorMessage value NOT found in the database");
                _scenario.write("ErrorMessage value NOT found in the database");
                Assert.fail("TEST FAILED INTENTIONALLY !. ErrorMessage value NOT found in the database");
            }
            //if isProcessed is null and error message contains "error"  OR isProcessed =1 and error message contains "error"
            if (((stagingData.get("isProcessed")==null || stagingData.get("isProcessed").equalsIgnoreCase("NULL")) && stagingData.get("ErrorMessage").contains("Error")) || (stagingData.get("isProcessed").equalsIgnoreCase("0") && stagingData.get("ErrorMessage").contains("Error") )) {

                System.out.println("Record is not processed in staging table due to an error. Actual state is : "+stagingData.get("isProcessed") + " And Error message is : "+stagingData.get("ErrorMessage"));
                _scenario.write("Record is not processed in staging table due to an error. Actual state is : "+stagingData.get("isProcessed") + " And Error message is : "+stagingData.get("ErrorMessage"));
                Assert.fail("Record is not processed in staging table due to an error. Actual state is : "+stagingData.get("isProcessed") + " And Error message is : "+stagingData.get("ErrorMessage"));

            }
            //when isProcessed is Null or 2 AND message is NOT "Error"
            else if ((stagingData.get("isProcessed")==null || stagingData.get("isProcessed").equalsIgnoreCase("NULL") || stagingData.get("isProcessed").equalsIgnoreCase("2")) && !stagingData.get("ErrorMessage").contains("Error") ){
                System.out.println("Record is waiting to be processed. Retrying in another 1 minute ");
                //_scenario.write("Record is waiting to be processed. Retrying in another 1 minute");

                //waiting for 1 minute
                Thread.sleep(60000);

                //reading the isProcessed value again from staging table
                stagingData = staging.readIsProcessedFromStagingDB(Environment,MinimumValueExp,MaximumValueExp);

                //Loping while record set is not null and value is 2 and  message is not "Error"
                while( stagingData != null && (!stagingData.get("ErrorMessage").contains("Error") || stagingData.get("ErrorMessage")==null || stagingData.get("ErrorMessage")=="") && (stagingData.get("isProcessed").equalsIgnoreCase("2") || stagingData.get("isProcessed")==null || stagingData.get("isProcessed").equalsIgnoreCase("NULL") || stagingData.get("isProcessed")=="" )) {

                    System.out.println("Record is waiting to be processed. Retrying in another 1 minute ");
                   // _scenario.write("Record is waiting to be processed. Retrying in another 1 minute");

                    //waiting for 1 minute
                    Thread.sleep(60000);

                    stagingData.clear();
                    stagingData = staging.readIsProcessedFromStagingDB(Environment,MinimumValueExp,MaximumValueExp);
                }

                // when exit from while loop ,,

                if(stagingData == null || (stagingData.get("isProcessed").equalsIgnoreCase("1") && !stagingData.get("ErrorMessage").contains("Error"))) {  //when record is processed and moved from the staging table
                    System.out.println("Record Processed successfully. Proceeding with Matrix table verification");
                   // _scenario.write("Record Processed successfully. Proceeding with Matrix table verification");

                } else if (stagingData.get("isProcessed").equalsIgnoreCase("0") ) {

                    System.out.println("Record is not processed in staging table. Actual result is : "+stagingData.get("isProcessed") +" Error message is : "+stagingData.get("ErrorMessage"));
                    _scenario.write("Record is not processed in staging table. Actual result is : "+stagingData.get("isProcessed") +" Error message is : "+stagingData.get("ErrorMessage"));
                    Assert.fail("Failing test due to Error processing isProcessed value in staging table. Actual value is "+stagingData.get("isProcessed") +" Error message is : "+stagingData.get("ErrorMessage"));

                }

            }
        }else {
            System.out.println("Record is not found in Database (Assuming it was already processed). Proceeding with Matrix table verification");
            //_scenario.write("Record is not found in Database (Assuming it was already processed). Proceeding with Matrix table verification");
        }
    }


    @Given("^If record is processed I query Matrix table and check the card count$")
    public void ifRecordIsProcessedIQueryMatrixTableAndCheckTheCardCount() throws Throwable {
        try {
            Thread.sleep(10000);
            MatrixRowList = matrix.readNewCardRangeCountFromMatrixDB(Environment,MinimumValueExp,MaximumValueExp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Matrix table didn't return valid record set");
            //_scenario.write("Matrix table didn't return valid record set");
        }

        if(MatrixRowList!=null && !MatrixRowList.isEmpty()) {
            System.out.println("The Card range has : "+MatrixRowList.size()+ " records");
            //_scenario.write("The Card range has : "+MatrixRowList.size()+ " records");

            Long minimumValInt = null;
            Long maximumValInt = null;
            try {
                minimumValInt = Long.parseLong(MinimumValueExp);
                maximumValInt = Long.parseLong(MaximumValueExp);
            } catch (NumberFormatException e) {
                System.out.println(e.getLocalizedMessage());
               // _scenario.write(e.getLocalizedMessage());
            }


            //Asserting if range count is equal
            try {
                Assert.assertEquals(MatrixRowList.size(),(maximumValInt-minimumValInt)+1);
                System.out.println("Expected count : "+ ((maximumValInt-minimumValInt)+1));
                System.out.println("Actual count : "+ MatrixRowList.size() );
            } catch (AssertionError e) {
                System.out.println("Test failed : " + e.getLocalizedMessage());
                _scenario.write("Test failed : " + e.getLocalizedMessage());
                Assert.fail();
            }
        } else {
            System.out.println("============ No data rows are returned for NewCardRangeCount ============");
            Assert.fail("============ No data rows are returned for NewCardRangeCount ============");
        }


    }



    @Then("^I validate Minimum Value, Maximum value and delete the Existing card range record from Salesforce UI$")
    public void iValidateMinimumValueMaximumValueAndDeleteTheExistingCardRangeRecordFromSalesforceUI() throws Throwable {
        //Changes of the method deleting the saleforce record according to ticket no - https://dfsrtr.atlassian.net/browse/SFDC-2031
        // get the card range name using a API call to SF
        //getting the access token
        getSFaccessToken accessToken = new getSFaccessToken();
        token = accessToken.getSFaccessToken();
        System.out.println("Access token "+token);

        String cardRangeName = cardRange.getCardRangeNamefromSF(token, MinimumValueExp, MaximumValueExp);
        //search for the card range name by searching the record
        // if it comes on top, then delete that record as usual

        if (cardRangeName.isEmpty()) {
            System.out.println("No existing record of card range between "+MinimumValueExp+" and "+MaximumValueExp);

        } else {

            System.out.println("Record of Card range name to be deleted - "+cardRangeName);

            // commented out for testing /////////////////////////////////////
//            Thread.sleep(5000);
//            //click on main search box to pull down the list
//            cardRange.clickOnMainSearchDropdown();
//
//            //waiting for 3 sec
//            Thread.sleep(3000);
//
//            //click on "Card Range" option under main search
//            cardRange.clickOnCardRangeOptionUnderMainSearch();
//
//            Thread.sleep(3000);
//            //type and search for card range name on the searchbox
//            cardRange.typeAndSearchCardRangeinMainSearchTextBox(cardRangeName);
//
//            //validate if card range window title is present
////            try {
////                Thread.sleep(5000);
////                Assert.assertTrue(cardRange.isCardRangeTitlePresent());
////            } catch (AssertionError e) {
////                System.out.println("Search results are not loaded successfully");
////                _scenario.write("Search results are not loaded successfully");
////                Assert.fail("TEST FAILED INTENTIONALLY ! Search results are not loaded successfully");
////            }
//
//            // validate if there is one search result only
//            try {
//                Assert.assertEquals(cardRange.getSearchResultCountText(), "1 Result");
//            } catch (AssertionError e) {
//                System.out.println("There are more than one search results or none");
//                _scenario.write("There are more than one search results or none");
//                Assert.fail("TEST FAILED INTENTIONALLY ! There are more than one search results for the card range - " + cardRangeName + " or none at all");
//            }
//
//            try {
//                cardRange.clickOnPulldownMenuInSearchResultsPage();
//                cardRange.clickOnDeleteOptionInPulldownMenu();
//                cardRange.deleteCardRangeRecord();
//
//            } catch (Exception e) {
//                System.out.println(e.getLocalizedMessage());
//                _scenario.write(e.getLocalizedMessage());
//                screenshot.takeScreenshot();
//            }
            //////////////////////// commented out for testing //////////////////////////////

            //deleteing the card range using API call and checking if deletion is confirmed
            try {
                cardRange.deleteCardRangeFromSF(token,cardRangeName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //waiting for 3 sec before confirming the deletion
            Thread.sleep(3000);

            //confirming the deletion
            try {
                Assert.assertSame(cardRange.getCardRangeNamefromSF(token, MinimumValueExp, MaximumValueExp), "");
                System.out.println("Card Range record with card range name: "+cardRangeName +" was deleted successfully from Salesforce");
                _scenario.write("Card Range record with card range name: "+cardRangeName +" was deleted successfully from Salesforce");
            } catch (AssertionError e) {
                System.out.println("Card Range with name: "+cardRangeName +" still Exists in Salesforce.");
                _scenario.write("Card Range with name: "+cardRangeName +" still Exists in Salesforce.");
                Assert.fail("TEST WAS FAILED INTENTIONALLY. ! The card range was deleted through the API call. But it has not actually deleted it from the Salesforce UI");
            }
        }
    }

    @Then("^I validate Minimum Value, Maximum value and Edit the Existing card range record from Salesforce UI$")
    public void iValidateMinimumValueMaximumValueAndEditTheExistingCardRangeRecordFromSalesforceUI() throws Throwable {
//        getSFaccessToken accessToken = new getSFaccessToken();
//        token = accessToken.getSFaccessToken();
//
//        String cardRangeName = cardRange.getCardRangeNamefromSF(token, MinimumValueExp, MaximumValueExp);
//        //search for the card range name by searching the record
//        // if it comes on top, then delete that record as usual

        if (cardRangeName.isEmpty()) {
            System.out.println("No existing record of card range between "+MinimumValueExp+" and "+MaximumValueExp);

        } else {

            System.out.println("Record of Card range name to be edited - "+cardRangeName);
            //waiting for 3 sec
            Thread.sleep(3000);

            //click on main search box to pull down the list
            cardRange.clickOnMainSearchDropdown();

            //waiting for 3 sec
            Thread.sleep(3000);

            //click on "Card Range" option under main search
            cardRange.clickOnCardRangeOptionUnderMainSearch();

            Thread.sleep(3000);
            //type and search for card range name on the searchbox
            cardRange.typeAndSearchCardRangeinMainSearchTextBox(cardRangeName);

            try {
                Thread.sleep(5000);
                System.out.println("scrolling horizontally until the pulldown menu is visible");
                javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",cardRange.getPulldownMenuElementInSearchResultsPage());
                cardRange.clickOnPulldownMenuInSearchResultsPage();
                cardRange.clickOnEditOptionInPulldownMenu();
            } catch (Exception e) {
                System.out.println("==== Pulldown menu is not visible in the UI, scrolling required ===");
                screenshot.takeScreenshot();

                // do a search again
                try {
                    //waiting for 3 sec
                    Thread.sleep(3000);
                    //click on main search box to pull down the list
                    cardRange.clickOnMainSearchDropdown();
                    //waiting for 3 sec
                    Thread.sleep(3000);
                    //click on "Card Range" option under main search
                    cardRange.clickOnCardRangeOptionUnderMainSearch();

                    Thread.sleep(3000);
                    //type and search for card range name on the searchbox
                    cardRange.typeAndSearchCardRangeinMainSearchTextBox(cardRangeName);
                } catch (InterruptedException e1) {
                    System.out.println("Unable to search for the card range name in the main search for the second time");
                }
                // end of search again

                try {
                    //scrolling Horizontally until the pulldown menu is visble
                    System.out.println("scrolling horizontally until the pulldown menu is visible");
                    javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",cardRange.getPulldownMenuElementInSearchResultsPage());
                    Thread.sleep(3000);

                    //retrying to click on pulldown menu and select edit option
                    cardRange.clickOnPulldownMenuInSearchResultsPage();
                    cardRange.clickOnEditOptionInPulldownMenu();

                } catch (Exception e1) {

                    //validate if card range window title is present
                    try {
                        Thread.sleep(5000);
                        Assert.assertTrue(cardRange.isCardRangeTitlePresent());
                    } catch (AssertionError e2) {
                        System.out.println("Search results are not loaded successfully");
                        _scenario.write("Search results are not loaded successfully");
                        Assert.fail("TEST FAILED INTENTIONALLY ! Search results are not loaded successfully");
                    }

                    // validate if there is one search result only
                    try {
                        Assert.assertEquals(cardRange.getSearchResultCountText(), "1 Result");
                    } catch (AssertionError e3) {
                        System.out.println("There are more than one search results or none");
                        _scenario.write("There are more than one search results or none");
                        Assert.fail("TEST FAILED INTENTIONALLY ! There are more than one search results for the card range - " + cardRangeName + " or none at all");
                    }

                    System.out.println("Unable to scroll to the pulldown menu element");
                    Assert.fail();
                }

            }
        }
    }

    @Then("^I validate if edit popup is loaded successfully$")
    public void iValidateIfEditPopupIsLoadedSuccessfully() throws Throwable {
        if(cardRange.isEditPopupPresent()){
            System.out.println("Edit Pop is loaded successfully");
            _scenario.write("Edit Pop is loaded successfully");
        } else { //Edit popup is not present
            System.out.println("Edit Pop is NOT loaded successfully. Unable to proceed through with the test");
            _scenario.write("Edit Pop is NOT loaded successfully. Unable to proceed through with the test");
            Assert.fail("Edit Pop is NOT loaded successfully. Unable to proceed through with the test");
        }
    }

    @Then("^I set the status to Disable$")
    public void iSetTheStatusToDisable() throws Throwable {
        try {
            cardRange.clickOnEditOptionInPulldownMenu();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu due to an exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Edit' option in pulldown menu due to an exception -"+e.getMessage());
        }

        try {
            cardRange.clickOnStatusDropdown();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown due to an Webdriver exception -"+e.getMessage());
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown due to an exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Status' dropdown due to an exception -"+e.getMessage());
        }

        try {
            cardRange.clickOnDisableOptionFromStatusDropdown();
            Thread.sleep(3000);
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Disabled' option in Status dropdown in Salesforce because the element has been changed -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Disabled' option in Status dropdown in Salesforce because the element has been changed -"+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Disabled' option in Status dropdown due to an Webdriver exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Disabled' option in Status dropdown due to an Webdriver exception -"+e.getMessage());
        }catch (InterruptedException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED ! Unable to click on 'Disabled' option in Status dropdown - "+e.getMessage());
            Assert.fail("TEST FAILED ! Unable to click on 'Disabled' option in Status dropdown - "+e.getMessage());
        }
    }

    @Then("^I save the edited record$")
    public void iSaveTheEditedRecord() throws Throwable {
        try {
            cardRange.clickOnSaveButton();
            screenshot.takeScreenshot();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button in Salesforce because the element has been changed - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button in Salesforce because the element has been changed - "+e.getMessage());
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an Webdriver exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an Webdriver exception -"+e.getMessage());
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an exception - "+e.getMessage());
        }
    }

    @Then("^I check if Status is Disable$")
    public void iCheckIfStatusIsDisable() throws Throwable {
        Thread.sleep(5000);
        try {
            Assert.assertEquals(cardRange.getStatusFromTabel(),"Disable");
            System.out.println("Card Range status is Disable");
            _scenario.write("Card Range status is Disable");

        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Status is not 'Disable'");
            _scenario.write("TEST FAILED !. Status is not 'Disable'");
            Assert.fail("TEST FAILED !. Status is not 'Disable'");
        }
    }

    @Then("^I log into salesforce$")
    public void iLogIntoSalesforce() throws Throwable {
        System.out.println("Logging into salesforce to start edit scenario");
        _scenario.write("Logging into salesforce to start edit scenario");
        loginPage.loginToSF();
    }

    @Then("^I search record using card range name$")
    public void iSearchRecordUsingCardRangeName() throws Throwable {
        //getting the access token
        getSFaccessToken accessToken = new getSFaccessToken();
        token = accessToken.getSFaccessToken();

        cardRangeName = cardRange.getCardRangeNamefromSF(token, MinimumValueExp, MaximumValueExp);

        //retrying if cardRangeName is empty and retrycount is under 5 (total of 5 minutes)
        int retryCount=0;
        while (cardRangeName.isEmpty() && retryCount < Property.RETRY_COUNT_FOR_GETTING_CARD_RANGE_NAME_FROM_SF){
            System.out.println("~~~~ Retrying in another 30 seconds ~~~~");
            Thread.sleep(30000);
            cardRangeName = cardRange.getCardRangeNamefromSF(token, MinimumValueExp, MaximumValueExp);
            retryCount++;
        } //end of while loop

        if (cardRangeName.isEmpty()) {
            System.out.println("No existing record of card range between " + MinimumValueExp + " and " + MaximumValueExp);
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to find a card range name in salesforce for card range between " + MinimumValueExp + " and " + MaximumValueExp +" after trying for 5 minutes");

        } else {

            System.out.println("Record of Card range found for - " + cardRangeName);
            //click on main search box to pull down the list
            Thread.sleep(5000);
            cardRange.clickOnMainSearchDropdown();

            //waiting for 3 sec
            Thread.sleep(3000);

            //click on "Card Range" option under main search
            try {
                cardRange.clickOnCardRangeOptionUnderMainSearch();
            } catch (Exception e) {
                System.out.println("Unable to click on main search box. "+e.getLocalizedMessage());
            }

            //type and search for card range name on the searchbox
            try {
                cardRange.typeAndSearchCardRangeinMainSearchTextBox(cardRangeName);
            } catch (InterruptedException e) {
                System.out.println("Unable to type and search the card range in main search box. "+e.getLocalizedMessage());
            }

            //validate if card range window title is present
//            try {
//                Thread.sleep(5000);
//                Assert.assertTrue(cardRange.isCardRangeTitlePresent());
//            } catch (AssertionError e) {
//                System.out.println("Search results are not loaded successfully");
//                _scenario.write("Search results are not loaded successfully");
//                Assert.fail("TEST FAILED INTENTIONALLY ! Search results are not loaded successfully");
//            }

            // validate if there is one search result only
            //Commented out this assertion since it creates failure due to not reading the text in the element most of the time. Instead added this assertion inside the clickOnCardRangeNameLink() method
//            String resultsCount=null;
//            try {
//                Thread.sleep(5000);
//                resultsCount = cardRange.getSearchResultCountText();
//                Assert.assertEquals(resultsCount, "1 Result");
//                System.out.println("Found 1 matching record");
//            } catch (AssertionError e) {
//                System.out.println("There are more than one search results or none. Actual record count - "+ resultsCount);
//                _scenario.write("There are more than one search results or noneActual record count - "+ resultsCount);
//                Assert.fail("TEST FAILED INTENTIONALLY ! There are more than one search results for the card range - " + cardRangeName + " or none at all. Actual record count - " +resultsCount);
//            }
        }
    }

    @Then("^I click on card range name value and wait for the popup to be opened$")
    public void iClickOnCardRangeNameValueAndWaitForThePopupToBeOpened() throws Throwable {
        Thread.sleep(5000);
        cardRange.clickOnCardRangeNameLink();
        Thread.sleep(5000);
        screenshot.takeScreenshot();

        try {
            Assert.assertEquals(cardRange.getCardRangeNameaFromTabel(),cardRangeName);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Card range name mismatch with Salesforce - "+e.getMessage());
            _scenario.write("TEST FAILED !. Card range name mismatch with Salesforce - "+e.getMessage());
            Assert.fail("TEST FAILED !. Card range name mismatch with Salesforce - "+e.getMessage());
        }
    }


}
