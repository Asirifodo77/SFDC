package step_definitions;

import com.google.gson.JsonObject;
import commonLibs.implementation.JavaScriptExecutor;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.SearchMember_pageobjects;
import utilities.TakeScreenshot;
import utilities.getSFaccessToken;
import utilities.testDataCleanup;
import utilities.validateMemberIDinSFandMatrix;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class UpdateMemberContactDetails_steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public DSAcreateMember_pageObjects DSAcreateMember;
    public String POSTrequestBody;
    public String environment;
    public testDataCleanup dataCleanup;
    public String token;
    public utilities.validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;
    public getSFaccessToken accessToken;
    public SearchMember_pageobjects searchMember_pageobjects;
    public MemberValidation_pageobjects member;
    public DSAcreateMember_pageObjects matrix;
    public HashMap<String, String> matrixData;
    public TakeScreenshot screenshot;


    public UpdateMemberContactDetails_steps() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;

        //creating loginPage object
        loginPage = new Login_pageobjects(driver,_scenario);
        DSAcreateMember = new DSAcreateMember_pageObjects(_scenario);
        environment = System.getProperty("Environment");
        dataCleanup = new testDataCleanup();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);
        accessToken = new getSFaccessToken();
        searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        member = new MemberValidation_pageobjects(driver,_scenario);
        matrix = new DSAcreateMember_pageObjects(_scenario);
        matrixData = new HashMap<>();
        screenshot = new TakeScreenshot(driver,_scenario);

    }

    @Given("^I delete existing data for \"([^\"]*)\" create member with json request body in \"([^\"]*)\"$")
    public void iDeleteExistingDataForCreateMemberWithJsonRequestBodyIn(String channel, String testDataTag) throws Throwable {

        //read member number from json body
        try {
            DSAcreateMember.getElementValuesFromAnyDSAJsonFile(testDataTag, environment);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Could not delete existing data due to following exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Could not delete existing data due to following exception - "+e.getMessage());
        }

        //data cleanup
        System.out.println("Data cleaning for Card Number : "+DSAcreateMember.CardNo+" is In progress ... ");
        _scenario.write("Data cleaning for Card Number : "+DSAcreateMember.CardNo+" is In progress ... ");
        dataCleanup.cleanUpOneMemberCardData(_scenario,DSAcreateMember.CardNo);
    }

    @Then("^I create \"([^\"]*)\" member with json request body in \"([^\"]*)\"$")
    public void iCreateMemberWithJsonRequestBodyIn(String channel, String testDataTag) throws Throwable {

        //get POSTrequestBody body
        try {
            POSTrequestBody = DSAcreateMember.readRequestFromJson(DSAcreateMember.getDSAmemberContactDetailsUpdateJsonPath(testDataTag,environment));
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to read POST request body due to following exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read POST request body due to following exception - "+e.getMessage());
        }
        _scenario.write("=============== The Json request body is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("=============== End of The Json request body ==========================");

        // create member with API call
        System.out.println("Sent POST request to create DSA member");
        _scenario.write("Sent POST request to create DSA member");
        DSAcreateMember.createMemberDSA(POSTrequestBody);


    }

    @Then("^I validate the memberID in salesforce and Matrix$")
    public void iValidateTheMemberIDInSalesforceAndMatrix() throws Throwable {

        // validate the memberID in salesforce and Matrix for Created member
        //getting the access token
        token = accessToken.getSFaccessToken();

        //validating member id from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberID(token,DSAcreateMember.CardNo,environment);
    }

    @Then("^I search for DSA member with card number$")
    public void iSearchForDSAMemberWithCardNumber(DataTable dataTable) throws Throwable {

        System.out.println("Searching for the member with card number : "+DSAcreateMember.CardNo);
        _scenario.write("Searching for the member with card number : "+DSAcreateMember.CardNo);


        try {
            searchMember_pageobjects.searchMemberThroughCardNumber(dataTable,DSAcreateMember.CardNo);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to search the member in Salesforce due to exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! Unable to search the member in Salesforce due to exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to search the member in Salesforce due to exception - "+e.getMessage());
        }

        Thread.sleep(5000);

        //Switch to frame
        member.switchToMainFrame();
    }

    @Then("^I validate if email address is empty in SF$")
    public void iValidateIfEmailAddressIsEmptyInSF() throws Throwable {
        //Thread.sleep(5000);

        //Switch to frame
       // member.switchToMainFrame();

        //EmailAddressText
        member.getEmailAddressText();

        try {
            Assert.assertTrue(member.EmailAddressText_sf.isEmpty());
            System.out.println("'Email Address' is Empty");
            _scenario.write("'Email Address' is Empty");

        } catch (AssertionError e) {
            if(DSAcreateMember.EmailAddressText.isEmpty()) {
                //when User has passed some value
                System.out.println("TEST FAILED INTENTIONALLY ! The 'Email Address' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
                Assert.fail("TEST FAILED INTENTIONALLY ! The 'Email Address' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
            } else {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED !. 'Email Address' is NOT empty");
                _scenario.write("TEST FAILED !. 'Email Address' is NOT empty");
                Assert.fail("TEST FAILED !. 'Email Address' is NOT empty");
            }

        }

    }

    @Then("^I validate if valid email address checkbox is unchecked$")
    public void iValidateIfValidEmailAddressCheckboxIsUnchecked() throws Throwable {

        //IsInvalidEmail
        member.getIsInvalidEmail();

        try {
            Assert.assertTrue(member.IsInvalidEmail_sf.equalsIgnoreCase("1"));
            System.out.println("'Valid Email Address' checkbox is Unchecked");
            _scenario.write("'Valid Email Address' checkbox is Unchecked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Email Address' checkbox is checked.");
            _scenario.write("TEST FAILED !. 'Valid Email Address' checkbox is checked.");
            Assert.fail("TEST FAILED !. 'Valid Email Address' checkbox is checked.");
        }
    }

    @Then("^I update the email address field to \"([^\"]*)\" in SF$")
    public void iUpdateTheEmailAddressFieldToInSF(String updateEmailAddressValue) throws Throwable {

        //check if update value is not empty
        if(updateEmailAddressValue==null) {
            System.out.println("TEST FAILED INTENTIONALLY !. Updated Email Address should not be Null");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated Email Address should not be Null");
        }

        // set text
        try {
            member.setEmailAddressText(updateEmailAddressValue);
            System.out.println("updated Email Address field value to : "+updateEmailAddressValue);
            _scenario.write("updated Email Address field value to : "+updateEmailAddressValue);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update Email Address field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update Email Address field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update Email Address field due to an exception. ");
        }

    }

    @Then("^I validate if Mobile Phone is empty in SF$")
    public void iValidateIfMobilePhoneIsEmptyInSF() throws Throwable {

        //ContactNumberText (Mobile Phone)
        member.getContactNumberText();

        try {
            Assert.assertTrue(member.ContactNumberText_sf.isEmpty());
            System.out.println("'Mobile Phone' is Empty");
            _scenario.write("'Mobile Phone' is Empty");

        } catch (AssertionError e) {
            if(DSAcreateMember.ContactNumberText.isEmpty()) {
                //when User has passed some value
                System.out.println("TEST FAILED INTENTIONALLY ! The 'Mobile Phone' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
                Assert.fail("TEST FAILED INTENTIONALLY ! The 'Mobile Phone' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
            } else {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED !. 'Mobile Phone' is NOT empty. ");
                _scenario.write("TEST FAILED !. 'Mobile Phone' is NOT empty. ");
                Assert.fail("TEST FAILED !. 'Mobile Phone' is NOT empty. ");
            }

        }
    }

    @Then("^I validate if Dialing Code-Mobile Phone is empty in SF$")
    public void iValidateIfDialingCodeMobilePhoneIsEmptyInSF() throws Throwable {

        // Mobile1AreaCode
        member.getMobile1AreaCode();

        try {
            Assert.assertTrue(member.Mobile1AreaCode_sf.isEmpty());
            System.out.println("Dialing Code-Mobile Phone is empty");
            _scenario.write("Dialing Code-Mobile Phone is empty");
        } catch (Exception e) {
            if(DSAcreateMember.Mobile1AreaCode.isEmpty()) {
                //When value is not set to empty
                System.out.println("TEST FAILED INTENTIONALLY ! The 'Mobile1AreaCode' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
                Assert.fail("TEST FAILED INTENTIONALLY ! The 'Mobile1AreaCode' value in Json file should be empty for this test scenario. Please set it to empty and try again. ");
            } else {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED !. 'Dialing Code (Mobile Phone)' is NOT empty. ");
                _scenario.write("TEST FAILED !. 'Dialing Code (Mobile Phone)' is NOT empty. ");
                Assert.fail("TEST FAILED !. 'Dialing Code (Mobile Phone)' is NOT empty. ");
            }
        }

    }

    @Then("^I validate if Valid Mobile Phone checkbox is unchecked$")
    public void iValidateIfValidMobilePhoneCheckboxIsUnchecked() throws Throwable {
        //ValidMobileNo1
        member.getValidMobileNo1();

        try {
            Assert.assertTrue(member.ValidMobileNo1_sf.equalsIgnoreCase("0"));
            System.out.println("'Valid Mobile Phone' checkbox is Unchecked");
            _scenario.write("'Valid Mobile Phone' checkbox is Unchecked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Mobile Phone' checkbox is checked.");
            _scenario.write("TEST FAILED !. 'Valid Mobile Phone' checkbox is checked.");
            Assert.fail("TEST FAILED !. 'Valid Mobile Phone' checkbox is checked.");
        }
    }

    @Then("^I update the Dialing Code-Mobile Phone field to \"([^\"]*)\" in SF$")
    public void iUpdateTheDialingCodeMobilePhoneFieldToInSF(String updatedDialingCodeMobilePhone) throws Throwable {
        try {
            member.setMobile1AreaCode(updatedDialingCodeMobilePhone);
            System.out.println("Dialing Code-Mobile Phone field to "+updatedDialingCodeMobilePhone);
            _scenario.write("Update the Dialing Code-Mobile Phone field to "+updatedDialingCodeMobilePhone);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Mobile Phone)' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Mobile Phone)' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Mobile Phone)' field due to an exception. ");
        }
    }

    @Then("^I update Mobile Phone field to \"([^\"]*)\" in SF$")
    public void iUpdateMobilePhoneFieldToInSF(String updatedMobileNo) throws Throwable {

        if(updatedMobileNo==null){
            System.out.println("TEST FAILED INTENTIONALLY !. Updated 'Mobile Phone' should not be Null ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated 'Mobile Phone' should not be Null");
        }

        try {
            member.setContactNumberText(updatedMobileNo);
            System.out.println("Update Mobile Phone field to "+updatedMobileNo);
            _scenario.write("Update Mobile Phone field to "+updatedMobileNo);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Mobile Phone' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Mobile Phone' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Mobile Phone' field due to an exception. ");
        }
    }


    @Then("^I validate if OtherPhone1 is empty in SF$")
    public void iValidateIfOtherPhone1IsEmptyInSF() throws Throwable {

        // other phone 1
        member.getOtherPhone1();

        try {
            Assert.assertTrue(member.OtherPhone1_sf.isEmpty());
            System.out.println("'Other Phone1' value is empty");
            _scenario.write("'Other Phone1' value is empty");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Other Phone1' value is not empty.");
            _scenario.write("TEST FAILED !. 'Other Phone1' value is not empty.");
            Assert.fail("TEST FAILED !. 'Other Phone1' value is not empty.");
        }

    }

    @Then("^I validate if Dialing Code-Other Phone 1 is empty in SF$")
    public void iValidateIfDialingCodeOtherPhone1IsEmptyInSF() throws Throwable {
        //get Dialing Code (Mobile Phone 1)
        member.getOtherPhone1AreaCode();

        try {
            Assert.assertTrue(member.OtherPhone1AreaCode_sf.isEmpty());
            System.out.println("'Dialing Code (Mobile Phone 1)' is empty.");
            _scenario.write("'Dialing Code (Mobile Phone 1)' is empty.");
        } catch (Exception e) {
            screenshot.takeScreenshot();
            _scenario.write("TEST FAILED !. 'Dialing Code (Mobile Phone 1)' is NOT empty. ");
            System.out.println("TEST FAILED !. 'Dialing Code (Mobile Phone 1)' is NOT empty. ");
            Assert.fail("TEST FAILED !. 'Dialing Code (Mobile Phone 1)' is NOT empty. ");
        }
    }

    @Then("^I validate if Valid Other Phone 1 checkbox is unchecked$")
    public void iValidateIfValidOtherPhone1CheckboxIsUnchecked() throws Throwable {

        // other phone 1 checkbox
        member.getValidOtherPhone1();

        try {
            Assert.assertTrue(member.ValidOtherPhone1_sf.equalsIgnoreCase("0"));
            System.out.println("'Valid Other Phone 1' checkbox is Unchecked. ");
            _scenario.write("'Valid Other Phone 1' checkbox is Unchecked. ");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Other Phone 1' checkbox is Checked. ");
            _scenario.write("TEST FAILED !. 'Valid Other Phone 1' checkbox is Checked. ");
            Assert.fail("TEST FAILED !. 'Valid Other Phone 1' checkbox is Checked. ");
        }

    }

    @Then("^I update Dialing Code-Other Phone 1 field to \"([^\"]*)\" in SF$")
    public void iUpdateDialingCodeOtherPhone1FieldToInSF(String updatedOtherPhone1AreaCode) throws Throwable {
        if(updatedOtherPhone1AreaCode.isEmpty()){
            System.out.println("TEST FAILED INTENTIONALLY !. Updated 'Dialing Code (Other Phone 1)' should not be Null or empty.");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated 'Dialing Code (Other Phone 1)' should not be Null or empty.");
        }

        try {
            member.setOtherPhone1AreaCode(updatedOtherPhone1AreaCode);
            System.out.println("Updated Dialing Code-Other Phone 1 field to "+updatedOtherPhone1AreaCode);
            _scenario.write("Updated Dialing Code-Other Phone 1 field to "+updatedOtherPhone1AreaCode);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 1)' value due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 1)' value due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 1)' value due to an exception - "+e.getMessage());
        }
    }

    @Then("^I update the Other Phone 1 field to \"([^\"]*)\" in SF$")
    public void iUpdateTheOtherPhone1FieldToInSF(String updatedOtherPhone1) throws Throwable {
        if(updatedOtherPhone1==null){
            System.out.println("TEST FAILED INTENTIONALLY !. Updated 'Other Phone 1' should not be Null ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated 'Other Phone 1' should not be Null ");
        }

        try {
            member.setOtherPhone1(updatedOtherPhone1);
            System.out.println("Update the Other Phone 1 field to "+updatedOtherPhone1);
            _scenario.write("Update the Other Phone 1 field to "+updatedOtherPhone1);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 1' value due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 1' value due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 1' value due to an exception - "+e.getMessage());
        }
    }



    @Then("^I validate if Other Phone 2 is empty in SF$")
    public void iValidateIfOtherPhone2IsEmptyInSF() throws Throwable {
        // other phone 2
        member.getOtherPhone2();

        try {
            Assert.assertTrue(member.OtherPhone2_sf.isEmpty());
            System.out.println("'Other Phone 2' value is empty");
            _scenario.write("'Other Phone 2' value is empty");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Other Phone 2' value is not empty.");
            _scenario.write("TEST FAILED !. 'Other Phone 2' value is not empty.");
            Assert.fail("TEST FAILED !. 'Other Phone 2' value is not empty.");
        }
    }

    @Then("^I validate if Dialing Code-Other Phone 2 is empty in SF$")
    public void iValidateIfDialingCodeOtherPhone2IsEmptyInSF() throws Throwable {
        //get Dialing Code (Mobile Phone 1)
        member.getOtherPhone2AreaCode();

        try {
            Assert.assertTrue(member.OtherPhone2AreaCode_sf.isEmpty());
            System.out.println("'Dialing Code (Mobile Phone 2)' is empty.");
            _scenario.write("'Dialing Code (Mobile Phone 2)' is empty.");
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Dialing Code (Mobile Phone 2)' is NOT empty. ");
            _scenario.write("TEST FAILED !. 'Dialing Code (Mobile Phone 2)' is NOT empty. ");
            Assert.fail("TEST FAILED !. 'Dialing Code (Mobile Phone 2)' is NOT empty. ");
        }
    }

    @Then("^I validate if Valid Other Phone 2 checkbox is unchecked$")
    public void iValidateIfValidOtherPhone2CheckboxIsUnchecked() throws Throwable {
        // other phone 2 checkbox
        member.getValidOtherPhone2();

        try {
            Assert.assertTrue(member.ValidOtherPhone2_sf.equalsIgnoreCase("0"));
            System.out.println("'Valid Other Phone 2' checkbox is Unchecked. ");
            _scenario.write("'Valid Other Phone 2' checkbox is Unchecked. ");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Other Phone 2' checkbox is Checked. ");
            _scenario.write("TEST FAILED !. 'Valid Other Phone 2' checkbox is Checked. ");
            Assert.fail("TEST FAILED !. 'Valid Other Phone 2' checkbox is Checked. ");
        }
    }

    @Then("^I update if Dialing Code-Other Phone 2 field to \"([^\"]*)\" in SF$")
    public void iUpdateIfDialingCodeOtherPhone2FieldToInSF(String updatedOtherPhone2AreaCode) throws Throwable {
        if(updatedOtherPhone2AreaCode.isEmpty()){
            System.out.println("TEST FAILED INTENTIONALLY !. Updated 'Dialing Code (Other Phone 2)' should not be Null or empty.");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated 'Dialing Code (Other Phone 2)' should not be Null or empty.");
        }

        try {
            member.setOtherPhone2AreaCode(updatedOtherPhone2AreaCode);
            System.out.println("Update Dialing Code-Other Phone 2 field to "+updatedOtherPhone2AreaCode);
            _scenario.write("Update Dialing Code-Other Phone 2 field to "+updatedOtherPhone2AreaCode);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 2)' value due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 2)' value due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Dialing Code (Other Phone 2)' value due to an exception - "+e.getMessage());
        }
    }

    @Then("^I update the Other Phone 2 field to \"([^\"]*)\" in SF$")
    public void iUpdateTheOtherPhone2FieldToInSF(String updatedOtherPhone2) throws Throwable {
        if(updatedOtherPhone2==null){
            System.out.println("TEST FAILED INTENTIONALLY !. Updated 'Other Phone 2' should not be Null ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Updated 'Other Phone 2' should not be Null");
        }

        try {
            member.setOtherPhone2(updatedOtherPhone2);
            System.out.println("Updated the Other Phone 2 field to "+updatedOtherPhone2);
            _scenario.write("Updated the Other Phone 2 field to "+updatedOtherPhone2);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 2' value due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 2' value due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to update 'Other Phone 2' value due to an exception - "+e.getMessage());
        }
    }

    @Then("^I click save button in SF$")
    public void iClickSaveButtonInSF() throws Throwable {
        //click save button
        try {
            member.clickSaveButtonInSF();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to click on 'Save' button due to an exception - "+e.getMessage());
        }
    }

    @Given("^I validate if member profile is updated in CMDlogs$")
    public void iValidateIfMemberProfileIsUpdatedInCMDlogs() throws Throwable {
        token = accessToken.getSFaccessToken();
        validateMemberIDinSFandMatrix.validateMemberIDAfterUpdate(token,DSAcreateMember.CardNo,environment);
    }

    @Then("^I query Matrix$")
    public void iQueryMatrix() throws Throwable {
        matrixData = matrix.readMemberDataFromMatrix(environment,DSAcreateMember.CardNo);
    }

    @Then("^I validate if Email is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfEmailIsUpdatedToInMatrix(String ExpectedEmail) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("Email"),ExpectedEmail);
            System.out.println("Email is updated to "+ExpectedEmail+" in Matrix");
            _scenario.write("Email is updated to "+ExpectedEmail+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Email address' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Email address' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Email address' mismatch in Matrix. "+e.getMessage());
        }
    }

    @And("^I validate if IsInvalidEmail is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfIsInvalidEmailIsUpdatedToInMatrix(String ExpectedIsInvalidEmail) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("IsInvalidEmail"),ExpectedIsInvalidEmail);
            System.out.println("IsInvalidEmail is updated to "+ExpectedIsInvalidEmail+" in Matrix");
            _scenario.write("IsInvalidEmail is updated to "+ExpectedIsInvalidEmail+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Valid Email Address' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Valid Email Address' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Valid Email Address' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if MobileNo is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfMobileNoIsUpdatedToInMatrix(String ExpectedMobileNo) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("MobileNo"),ExpectedMobileNo);
            System.out.println("MobileNo is updated to "+ExpectedMobileNo+" in Matrix");
            _scenario.write("MobileNo is updated to "+ExpectedMobileNo+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Mobile No' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Mobile No' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Mobile No' mismatch in Matrix. "+e.getMessage());
        }
    }

    @And("^I validate if ValidMobileNo1 is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfValidMobileNo1IsUpdatedToInMatrix(String ExpectedValidMobileNo1) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("ValidMobileNo1"),ExpectedValidMobileNo1);
            System.out.println("ValidMobileNo1 is updated to "+ExpectedValidMobileNo1+" in Matrix");
            _scenario.write("ValidMobileNo1 is updated to "+ExpectedValidMobileNo1+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
        }
    }

    @And("^I validate if MobileAreaCode is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfMobileAreaCodeIsUpdatedToInMatrix(String ExpectedMobileAreaCode) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("MobileAreaCode"),ExpectedMobileAreaCode);
            System.out.println("MobileAreaCode is updated to "+ExpectedMobileAreaCode+" in Matrix");
            _scenario.write("MobileAreaCode is updated to "+ExpectedMobileAreaCode+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Valid Mobile Phone' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if MobileNo2 is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfMobileNo2IsUpdatedToInMatrix(String ExpectedMobileNo2) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("MobileNo2"),ExpectedMobileNo2);
            System.out.println("MobileNo2 is updated to "+ExpectedMobileNo2+" in Matrix");
            _scenario.write("MobileNo2 is updated to "+ExpectedMobileNo2+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Other Phone 1' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Other Phone 1' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Other Phone 1' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if IsValidMobile2 is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfIsValidMobile2IsUpdatedToInMatrix(String ExpectedIsValidMobile2) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("IsValidMobile2"),ExpectedIsValidMobile2);
            System.out.println("IsValidMobile2 is updated to "+ExpectedIsValidMobile2+" in Matrix");
            _scenario.write("IsValidMobile2 is updated to "+ExpectedIsValidMobile2+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Valid Other Phone 1' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Valid Other Phone 1' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Valid Other Phone 1' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if MobileNoArea2 is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfMobileNoArea2IsUpdatedToInMatrix(String ExpectedMobileNoArea2) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("MobileNoArea2"),ExpectedMobileNoArea2);
            System.out.println("MobileNoArea2 is updated to "+ExpectedMobileNoArea2+" in Matrix");
            _scenario.write("MobileNoArea2 is updated to "+ExpectedMobileNoArea2+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Dialing Code (Other Phone 1)' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Dialing Code (Other Phone 1)' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Dialing Code (Other Phone 1)' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if HomeNo is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfHomeNoIsUpdatedToInMatrix(String ExpectedHomeNo) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("HomeNo"),ExpectedHomeNo);
            System.out.println("HomeNo is updated to "+ExpectedHomeNo+" in Matrix");
            _scenario.write("HomeNo is updated to "+ExpectedHomeNo+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Other Phone 2' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Other Phone 2' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Other Phone 2' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if ValidHomeNo is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfValidHomeNoIsUpdatedToInMatrix(String ExpectedValidHomeNo) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("ValidHomeNo"),ExpectedValidHomeNo);
            System.out.println("ValidHomeNo is updated to "+ExpectedValidHomeNo+" in Matrix");
            _scenario.write("ValidHomeNo is updated to "+ExpectedValidHomeNo+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Valid Other Phone 2' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Valid Other Phone 2' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Valid Other Phone 2' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if HomePhoneAreaCode is updated to \"([^\"]*)\" in Matrix$")
    public void iValidateIfHomePhoneAreaCodeIsUpdatedToInMatrix(String ExpectedHomePhoneAreaCode) throws Throwable {
        try {
            Assert.assertEquals(matrixData.get("HomePhoneAreaCode"),ExpectedHomePhoneAreaCode);
            System.out.println("HomePhoneAreaCode is updated to "+ExpectedHomePhoneAreaCode+" in Matrix");
            _scenario.write("HomePhoneAreaCode is updated to "+ExpectedHomePhoneAreaCode+" in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'Dialing Code (Other Phone 2)' mismatch in Matrix. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'Dialing Code (Other Phone 2)' mismatch in Matrix. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'Dialing Code (Other Phone 2)' mismatch in Matrix. "+e.getMessage());
        }
    }

    @Then("^I validate if Email is updated to \"([^\"]*)\" in SF$")
    public void iValidateIfEmailIsUpdatedToInSF(String ExpectedEmailAddressValue) throws Throwable {
        //check if update value is not empty
        if(ExpectedEmailAddressValue==null) {
            System.out.println("TEST FAILED INTENTIONALLY !. Expected Email Address should not be Null");
            _scenario.write("TEST FAILED INTENTIONALLY !. Expected Email Address should not be Null");
            Assert.fail("TEST FAILED INTENTIONALLY !. Expected Email Address should not be Null");
        }

        // get email address
        member.getEmailAddressText();
        try {
           Assert.assertEquals(member.EmailAddressText_sf,ExpectedEmailAddressValue);
            System.out.println("Email Address field value - : "+ExpectedEmailAddressValue);
            _scenario.write("Email Address field value - : "+ExpectedEmailAddressValue);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Email Address' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to get 'Email Address' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Email Address' field due to an exception. ");
        }
    }

    @Then("^I validate if Valid Email Address checkbox is checked$")
    public void IValidateIfValidEmailAddressCheckboxIsChecked() throws Throwable {
        //IsInvalidEmail
        member.getIsInvalidEmail();

        try {
            Assert.assertTrue(member.IsInvalidEmail_sf.equalsIgnoreCase("0"));
            System.out.println("'Valid Email Address' checkbox is Checked");
            _scenario.write("'Valid Email Address' checkbox is Checked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Email Address' checkbox is Unchecked.");
            _scenario.write("TEST FAILED !. 'Valid Email Address' checkbox is Unchecked.");
            Assert.fail("TEST FAILED !. 'Valid Email Address' checkbox is Unchecked.");
        }
    }

    @Then("^I update Valid Email Address checkbox to \"([^\"]*)\"$")
    public void iUpdateValidEmailAddressCheckboxTo(String state) throws Throwable {
        System.out.println("New state - "+ state);
        boolean changeToStatus ;
        if(state.equalsIgnoreCase("true")) {
            changeToStatus=true;
        } else {
            changeToStatus=false;
        }

        try {
            Thread.sleep(3000);
            member.setIsInvalidEmail(changeToStatus);
            System.out.println("Updated Valid Email Address checkbox to "+changeToStatus);
            _scenario.write("Updated Valid Email Address checkbox to "+changeToStatus);
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false because element has changed in Salesfroce - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false because element has changed in Salesfroce ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false because element has changed in Salesfroce ");
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to a Webdriver exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to a Webdriver exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to a Webdriver exception ");
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to an exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid Email Address' checkbox to false due to a an exception ");
        }
    }

    @Then("^I validate if MobileNo is updated to \"([^\"]*)\" in SF$")
    public void iValidateIfMobileNoIsUpdatedToInSF(String ExpectedMobilePhone) throws Throwable {
        //check if update value is not empty
        if(ExpectedMobilePhone==null) {
            System.out.println("TEST FAILED INTENTIONALLY !. Expected 'Mobile Phone' should not be Null");
            Assert.fail("TEST FAILED INTENTIONALLY !. Expected 'Mobile Phone' should not be Null");
        }

        // get email address
        member.getContactNumberText();
        try {
            Assert.assertEquals(member.ContactNumberText_sf,ExpectedMobilePhone);
            System.out.println("Mobile Phone value is : "+ExpectedMobilePhone);
            _scenario.write("Mobile Phone value is : "+ExpectedMobilePhone);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Mobile Phone' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to get 'Mobile Phone' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Mobile Phone' field due to an exception. ");
        }
    }

    @Then("^I validate if Valid Mobile Phone checkbox is checked$")
    public void iValidateIfValidMobilePhoneCheckboxIsChecked() throws Throwable {
        member.getValidMobileNo1();

        try {
            Assert.assertTrue(member.ValidMobileNo1_sf.equalsIgnoreCase("1"));
            System.out.println("'Valid Mobile Phone' checkbox is Checked");
            _scenario.write("'Valid Mobile Phone' checkbox is Checked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Mobile Phone' checkbox is Unchecked.");
            _scenario.write("TEST FAILED !. 'Valid Mobile Phone' checkbox is Unchecked.");
            Assert.fail("TEST FAILED !. 'Valid Mobile Phone' checkbox is Unchecked.");
        }
    }

    @Then("^I update Valid Mobile Phone checkbox to \"([^\"]*)\"$")
    public void iUpdateValidMobilePhoneCheckboxTo(String state) throws Throwable {
        System.out.println("New state - "+ state);
        boolean changeToStatus ;
        if(state.equalsIgnoreCase("true")) {
            changeToStatus=true;
        } else {
            changeToStatus=false;
        }
        try {
            member.setValidMobileNo1(changeToStatus);
            System.out.println("Update Valid Mobile Phone checkbox to "+changeToStatus);
            _scenario.write("Update Valid Mobile Phone checkbox to "+changeToStatus);
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false because element has changed in Salesfroce - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false because element has changed in Salesfroce ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false because element has changed in Salesfroce ");
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to a Webdriver exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to a Webdriver exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to a Webdriver exception ");
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to an exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Mobile Phone' checkbox to false due to an exception ");
        }
    }

    @Then("^I validate if Other Phone 1 is updated to \"([^\"]*)\" in SF$")
    public void iValidateIfOtherPhone1IsUpdatedToInSF(String ExpectedOtherPhone1) throws Throwable {
        //check if update value is not empty
        if(ExpectedOtherPhone1==null) {
            System.out.println("TEST FAILED INTENTIONALLY !. Expected 'Other Phone 1' should not be Null");
            Assert.fail("TEST FAILED INTENTIONALLY !. Expected 'Other Phone 1' should not be Null");
        }

        // get email address
        member.getOtherPhone1();
        try {
            Assert.assertEquals(member.OtherPhone1_sf,ExpectedOtherPhone1);
            System.out.println("'Other Phone 1' value is : "+ExpectedOtherPhone1);
            _scenario.write("'Other Phone 1' value is : "+ExpectedOtherPhone1);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 1' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 1' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 1' field due to an exception. ");
        }
    }

    @Then("^I validate if Valid Other Phone 1 checkbox is checked$")
    public void iValidateIfValidOtherPhone1CheckboxIsChecked() throws Throwable {

        member.getValidOtherPhone1();

        try {
            Assert.assertTrue(member.ValidOtherPhone1_sf.equalsIgnoreCase("1"));
            System.out.println("'Valid Other Phone 1' checkbox is Checked");
            _scenario.write("'Valid Other Phone 1' checkbox is Checked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Other Phone 1' checkbox is Unchecked.");
            _scenario.write("TEST FAILED !. 'Valid Other Phone 1' checkbox is Unchecked.");
            Assert.fail("TEST FAILED !. 'Valid Other Phone 1' checkbox is Unchecked.");
        }
    }

    @Then("^I update Valid Other Phone 1 checkbox to \"([^\"]*)\"$")
    public void iUpdateValidOtherPhone1CheckboxTo(String state) throws Throwable {
        System.out.println("New state - "+ state);
        boolean changeToStatus ;
        if(state.equalsIgnoreCase("true")) {
            changeToStatus=true;
        } else {
            changeToStatus=false;
        }
        try {
            member.setValidOtherPhone1(changeToStatus);
            System.out.println("Update Valid Other Phone 1 checkbox to "+changeToStatus);
            _scenario.write("Update Valid Other Phone 1 checkbox to "+changeToStatus);
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false because element has changed in Salesfroce - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false because element has changed in Salesfroce ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false because element has changed in Salesfroce ");
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to a Webdriver exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to a Webdriver exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to a Webdriver exception ");
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to an exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 1' checkbox to false due to an exception ");
        }
    }

    @Then("^I validate if Other Phone 2 is updated to \"([^\"]*)\" in SF$")
    public void iValidateIfOtherPhone2IsUpdatedToInSF(String ExpectedOtherPhone2) throws Throwable {
        //check if update value is not empty
        if(ExpectedOtherPhone2==null) {
            System.out.println("TEST FAILED INTENTIONALLY !. Expected 'Other Phone 2' should not be Null");
            Assert.fail("TEST FAILED INTENTIONALLY !. Expected 'Other Phone 2' should not be Null");
        }

        // get email address
        member.getOtherPhone2();
        try {
            Assert.assertEquals(member.OtherPhone2_sf,ExpectedOtherPhone2);
            System.out.println("'Other Phone 2' value is : "+ExpectedOtherPhone2);
            _scenario.write("'Other Phone 2' value is : "+ExpectedOtherPhone2);
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 2' field due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 2' field due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get 'Other Phone 2' field due to an exception. ");
        }
    }

    @Then("^I validate if Valid Other Phone 2 checkbox is checked$")
    public void iValidateIfValidOtherPhone2CheckboxIsChecked() throws Throwable {
        member.getValidOtherPhone2();

        try {
            Assert.assertTrue(member.ValidOtherPhone2_sf.equalsIgnoreCase("1"));
            System.out.println("'Valid Other Phone 2' checkbox is Checked");
            _scenario.write("'Valid Other Phone 2' checkbox is Checked");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. 'Valid Other Phone 2' checkbox is Unchecked.");
            _scenario.write("TEST FAILED !. 'Valid Other Phone 2' checkbox is Unchecked.");
            Assert.fail("TEST FAILED !. 'Valid Other Phone 2' checkbox is Unchecked.");
        }
    }

    @Then("^I update Valid Other Phone 2 checkbox to \"([^\"]*)\"$")
    public void iUpdateValidOtherPhone2CheckboxTo(String state) throws Throwable {
        System.out.println("New state - "+ state);
        boolean changeToStatus ;
        if(state.equalsIgnoreCase("true")) {
            changeToStatus=true;
        } else {
            changeToStatus=false;
        }
        try {
            member.setValidOtherPhone2(changeToStatus);
            System.out.println("Update Valid Other Phone 2 checkbox to "+changeToStatus);
            _scenario.write("Update Valid Other Phone 2 checkbox to "+changeToStatus);
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false because element has changed in Salesfroce - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false because element has changed in Salesfroce ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false because element has changed in Salesfroce ");
        } catch (WebDriverException e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to a Webdriver exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to a Webdriver exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to a Webdriver exception ");
        }catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to an exception ");
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to set 'Valid  Other Phone 2' checkbox to false due to an exception ");
        }
    }
}
