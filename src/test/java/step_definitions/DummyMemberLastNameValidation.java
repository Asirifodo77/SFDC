package step_definitions;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.MemberValidation_pageobjects;
import property.Property;
import utilities.ReadJenkinsParameters;
import utilities.TakeScreenshot;
import utilities.readTestData;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class DummyMemberLastNameValidation {
    public WebDriver driver;
    public Scenario _scenario;
    public String environment;
    public MemberValidation_pageobjects member;
    public String ActualLastName;
    public TakeScreenshot screenshot;
    public DSAcreateMember_pageObjects pos;
    public readTestData testData;
    public String requestBody;
    public String CardNumber;

    public DummyMemberLastNameValidation() {
        driver = Hooks.driver;
        _scenario = Hooks._scenario;
        environment = System.getProperty("Environment");
        member = new MemberValidation_pageobjects(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
        pos = new DSAcreateMember_pageObjects(_scenario);
        testData = new readTestData();
    }

    @Then("^I read the last name of the member in SF$")
    public void iReadTheLastNameOfTheMemberInSF() throws Throwable {

        //switch to main frame
//        try {
//            Thread.sleep(3000);
//            //Switch to frame
//            member.switchToMemberCycleTab();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (NoSuchElementException e) {
//            screenshot.takeScreenshot();
//            System.out.println("Unable to find 'Member Cycle T' element in Salesforce. Description - "+e.getMessage());
//            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to switch to Main Frame since the element has been changed in SF");
//        }

        // get Title text
        try {
            member.getTitleText();
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            System.out.println("Unable to find 'Last Name' element from Salesforce. Description - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member's  'Last Name' since the element has been changed in SF");
        }

        try {

             ActualLastName = member.titleText_sf.substring(0,member.titleText_sf.indexOf(",")).trim();

        } catch (NullPointerException e) {
            System.out.println("Member's actual Last Name is Null");
            Assert.fail("TEST FAILED !. Member's actual Last Name is Null");
        }

        System.out.println("Member's Last Name - "+ActualLastName);

    }

    @Then("^I validate if last name of the member is equal to \"([^\"]*)\"$")
    public void iValidateIfLastNameOfTheMemberIsEqualTo(String ExpectedLastName) throws Throwable {
        try {
            Assert.assertEquals(ActualLastName,ExpectedLastName);
            System.out.println("Last name of the Dummy member is equal to '*'");
            _scenario.write("Last name of the Dummy member is equal to '*'");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Member's Last Name mismatch. "+e.getMessage());
            _scenario.write("TEST FAILED !. Member's Last Name mismatch. "+e.getMessage());
            Assert.fail("TEST FAILED !. Member's Last Name mismatch. "+e.getMessage());
        }
    }

    @Given("^I send the POST request to get Member \"([^\"]*)\" in POS$")
    public void iSendThePOSTRequestToGetMemberInPOS(String testDataTag) throws Throwable {

        //read tag in test data and get the card number
        try {
            CardNumber = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter(testDataTag));
        } catch (FileNotFoundException e) {
            Assert.fail("Unable to find Testdata file in the Location "+Property.TESTDATA_FILE_PATH+". ---> "+e.getLocalizedMessage());
        } catch (JsonSyntaxException e) {
            Assert.fail("Incorrect JsonSyntax in the Testdata file  - "+e.getLocalizedMessage());
        } catch (JsonIOException e) {
            Assert.fail("Json Input/ output file exception occurred - "+e.getLocalizedMessage());
        }

        //get the custom requestbody to send the API call
        try {
            requestBody = pos.getPOSgetMemberRequestBodyForAnyCard(CardNumber);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY. Unable to generate API request request body due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to generate API request request body due to an exception - "+e.getMessage());
        }

        try {
            pos.getMemberPOS(requestBody);
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY. Unable to get member from POS due to API request exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to get member from POS due to API request exception - "+e.getMessage());
        }

        //checking if the member card number is found
        try {
            assertFalse(pos.ReturnMessage.equalsIgnoreCase(Property.API_CARDNO_NOT_FOUND_MESSAGE));
            System.out.println("Member card Found from POS get Member");
            _scenario.write("Member card Found from POS get Member");
        } catch (AssertionError e) {
            System.out.println("Member with card Number : "+CardNumber+ " is not found in "+ environment);
            _scenario.write("Member with card Number : "+CardNumber+ " is not found in "+ environment);
            fail("TEST FAILED INTENTIONALLY !.  Member with card Number : "+CardNumber+ " is not found in "+ environment);
        }

        try {
            assertTrue((pos.ReturnMessage.equalsIgnoreCase(Property.API_EXPECTED_SUCCESS_MESSAGE) || pos.ReturnMessage.equalsIgnoreCase(Property.API_INACTIVE_CARD_MESSAGE) ),"The API response is : "+pos.ReturnMessage);
            System.out.println("POS get member API response is : "+pos.ReturnMessage);
            _scenario.write("POS get member API response is : "+pos.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("The test was failed since the API response did not return a success message. Actual message - "+pos.ReturnMessage);
            _scenario.write("The test was failed since the API response did not return a success message. Actual message - "+pos.ReturnMessage);
            fail("TEST FAILED INTENTIONALLY !.  API response did not return a success message. Actual message - "+pos.ReturnMessage);
        }
    }

    @Then("^I validate if last name of the member is a Blank$")
    public void iValidateIfLastNameOfTheMemberIsABlank() throws Throwable {
        try {
            Assert.assertTrue(pos.LastName.isEmpty());
            System.out.println("Dummy member's Last name is Blank");
            _scenario.write("Dummy member's Last name is Blank");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Member's Last Name on POS is not blank. "+e.getMessage());
            _scenario.write("TEST FAILED !. Member's Last Name on POS is not blank. "+e.getMessage());
            Assert.fail("TEST FAILED !. Member's Last Name on POS is not blank. "+e.getMessage());
        }
    }
}
