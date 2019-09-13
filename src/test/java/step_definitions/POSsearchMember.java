package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.SearchMember_pageobjects;
import property.Property;
import utilities.TakeScreenshot;
import utilities.getDataFromSF;
import utilities.getSFaccessToken;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class POSsearchMember {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String POSTrequestBody;
    public DSAcreateMember_pageObjects member;
    public SoftAssert softAssert;
    public String environment;
    public Login_pageobjects login;
    public SearchMember_pageobjects searchMember;
    public MemberValidation_pageobjects sf;
    public getDataFromSF sfData;
    public TakeScreenshot screenshot;
    public String token;



    //Constructor class
    public POSsearchMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;

        environment= System.getProperty("Environment");
        softAssert = new SoftAssert();
        login = new Login_pageobjects(driver,_scenario);
        searchMember = new SearchMember_pageobjects(driver,_scenario);
        sf = new MemberValidation_pageobjects(driver,_scenario);


        //creating member object
        member = new DSAcreateMember_pageObjects(_scenario);
        sfData = new getDataFromSF();
        screenshot = new TakeScreenshot(driver,_scenario);


    }

    @Given("^I read POS search member request body from the Json file$")
    public void iReadPOSSearchMemberRequestBodyFromTheJsonFile() throws Throwable {
        //reading the full json request body
        POSTrequestBody = member.readRequestFromJson(member.getPOSsearchMemberJsonPath(environment));
    }

    @Given("^I send the POST request to search member in POS$")
    public void iSendThePOSTRequestToSearchMemberInPOS() throws Throwable {
        member.searcheMemberPOS(POSTrequestBody);
    }

    @Then("^I validate if POS search member response is valid$")
    public void iValidateIfPOSSearchMemberResponseIsValid() throws Throwable {
        //checking if the member card number is found
        try {
            assertFalse(member.ReturnMessage.equalsIgnoreCase(Property.API_CARDNO_NOT_FOUND_MESSAGE));
        } catch (AssertionError e) {
            System.out.println("Member with card Number : "+member.CardNo+ " is not found in "+ environment);
            _scenario.write("Member with card Number : "+member.CardNo+ " is not found in "+ environment);
            fail("TEST FAILED INTENTIONALLY !.  Member with card Number : "+member.CardNo+ " is not found in "+ environment);
        }

        try {
            assertTrue((member.ReturnMessage.equalsIgnoreCase(Property.API_EXPECTED_SUCCESS_MESSAGE) || member.ReturnMessage.equalsIgnoreCase(Property.API_INACTIVE_CARD_MESSAGE)),"The API response is : "+member.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("The test was failed since the API response did not return a success message. Actual message - "+member.ReturnMessage);
            _scenario.write("The test was failed since the API response did not return a success message. Actual message - "+member.ReturnMessage);
            fail("TEST FAILED INTENTIONALLY !. API response did not send a success message. The Actual message - "+member.ReturnMessage);
        }

    }

    @Then("^I read member details in the response$")
    public void iReadMemberDetailsInTheResponse() throws Throwable {
        System.out.println("Member ID : " + member.MemberID);
        _scenario.write("Member ID : " + member.MemberID);

        System.out.println("First Name : "+ member.FirstName);
        _scenario.write("First Name : "+ member.FirstName);

        System.out.println("Last Name : "+ member.LastName);
        _scenario.write("Last Name : "+ member.LastName);

        System.out.println("Nationality : "+ member.NationalityCode);
        _scenario.write("Nationality : "+ member.NationalityCode);

        System.out.println("Mobile Phone : "+ member.ContactNumberText);
        _scenario.write("Mobile Phone : "+ member.ContactNumberText);

        System.out.println("Other Phone 1 : " + member.MobileNo2);
        _scenario.write("Other Phone 1 : " + member.MobileNo2);

        System.out.println("Other Phone 2 :"+ member.OtherPhone2);
        _scenario.write("Other Phone 2 :"+ member.OtherPhone2);

        System.out.println("Location of Residence : "+ member.CountryNameText);
        _scenario.write("Location of Residence : "+ member.CountryNameText);

        System.out.println("Email :"+ member.EmailAddressText);
        _scenario.write("Email :"+ member.EmailAddressText);

        System.out.println("Card No : "+ member.CardNo);
        _scenario.write("Card No : "+ member.CardNo);
        
        System.out.println("MemberStatus : "+ member.MemberStatus);
        _scenario.write("MemberStatus : "+ member.MemberStatus);

        System.out.println("JoinedDate : "+ member.JoinedDate);
        _scenario.write("JoinedDate : "+ member.JoinedDate);

        System.out.println("MembershipCardTierCode : "+ member.MembershipCardTierCode);
        _scenario.write("MembershipCardTierCode : "+ member.MembershipCardTierCode);

    }

    @Then("^I read JoinDate value from salesforce$")
    public void iReadJoinDateValueFromSalesforce() throws Throwable {
        getSFaccessToken accessToken = new getSFaccessToken();
        token = accessToken.getSFaccessToken();

        //passing the card number and getting the required data from SF and assining them to Run time data
        sfData.querySFdataforPOSsearch(token,member.CardNo);

    }

    @Then("^I validate if POS search member is Dummy$")
    public void iValidateIfPOSSearchMemberIsDummy() throws Throwable {
        sfData.querySFdataForDummyMmeber(token,member.CardNo);
    }

    @Then("^I login to SF to search POS member$")
    public void iLoginToSFToSearchPOSMember() throws Throwable {
        login.loginToSF();
    }


    @Then("^I search the member using card number$")
    public void iSearchTheMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        searchMember.searchMemberThroughCardNumber(dataTable,member.CardNo);
    }

    @Then("^I read the member details in SF$")
    public void iReadTheMemberDetailsInSF() throws Throwable {
        if(sfData.isDummy__c.equalsIgnoreCase("false")) { // WHEN MEMBER IS NOT DUMMY, READ THE DETAILS AS USUAL

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

    @Then("^I validate POS search member details with SF$")
    public void iValidatePOSSearchMemberDetailsWithSF() throws Throwable {
        // Expected values - data from POST response
        // Actual values - data from SF

        if(sfData.isDummy__c.equalsIgnoreCase("false")) {

            //First Name
            softAssert.assertEquals(sf.FirstName_sf, member.FirstName, "First Name ");
            _scenario.write("=============== First Name ===============");
            _scenario.write("Actual First Name (SF) : " + sf.FirstName_sf);
            _scenario.write("Expected First Name (Response) : " + member.FirstName);

            //Last Name
            softAssert.assertEquals(sf.LastName_sf, member.LastName, "Last Name ");
            _scenario.write("=============== Last Name ===============");
            _scenario.write("Actual Last Name (SF) : " + sf.LastName_sf);
            _scenario.write("Expected Last Name (Response) : " + member.LastName);

            //Nationality
            softAssert.assertEquals(sf.Nationality_sf, member.NationalityCode, "Nationality ");
            _scenario.write("=============== Nationality ===============");
            _scenario.write("Actual Nationality (SF) : " + sf.Nationality_sf);
            _scenario.write("Expected Nationality (Response) : " + member.NationalityCode);

            //Mobile Phone (ContactNumberText)
            softAssert.assertEquals(sf.ContactNumberText_sf, member.ContactNumberText, "Mobile Phone (ContactNumberText) ");
            _scenario.write("=============== Mobile Phone (ContactNumberText) ===============");
            _scenario.write("Actual Mobile Phone (ContactNumberText) (SF) : " + sf.ContactNumberText_sf);
            _scenario.write("Expected Mobile Phone (ContactNumberText) (Response) : " + member.ContactNumberText);

            //Other Phone 1
            softAssert.assertEquals(sf.OtherPhone1_sf, member.MobileNo2, "Other Phone 1 ");
            _scenario.write("=============== Other Phone 1 ===============");
            _scenario.write("Actual Other Phone 1  (SF) : " + sf.OtherPhone1_sf);
            _scenario.write("Expected Other Phone 1 (Response) : " + member.MobileNo2);

            //Other Phone 2
            softAssert.assertEquals(sf.OtherPhone2_sf, member.OtherPhone2, "Other Phone 2 ");
            _scenario.write("=============== Other Phone 2 ===============");
            _scenario.write("Actual Other Phone 2  (SF) : " + sf.OtherPhone2_sf);
            _scenario.write("Expected Other Phone 2 (Response) : " + member.OtherPhone2);

            //Location of Residence
            softAssert.assertEquals(sf.EnglishCountry_sf, member.CountryNameText, "Location of Residence ");
            _scenario.write("=============== Location of Residence ===============");
            _scenario.write("Actual Location of Residence  (SF) : " + sf.EnglishCountry_sf);
            _scenario.write("Expected Location of Residence (Response) : " + member.CountryNameText);

            //Email
            softAssert.assertEquals(sf.EmailAddressText_sf, member.EmailAddressText, "Email Address ");
            _scenario.write("=============== Email Address ===============");
            _scenario.write("Actual EmailAddressText  (SF) : " + sf.EmailAddressText_sf);
            _scenario.write("Expected EmailAddressText (Response) : " + member.EmailAddressText);

            //Member Status
            softAssert.assertTrue(member.MemberStatus.equalsIgnoreCase(sf.cardStatus_sf), "Member Card Status ");
            _scenario.write("=============== Member Status ===============");
            _scenario.write("Actual Member Status  (SF) : " + sf.cardStatus_sf);
            _scenario.write("Expected Member Status (Response) : " + member.MemberStatus);

            //CardTier
            softAssert.assertTrue(member.MembershipCardTierCode.equalsIgnoreCase(sf.CardTier_sf), "CardTier ");
            _scenario.write("=============== CardTier ===============");
            _scenario.write("Actual CardTier  (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected CardTier (Response) : " + member.MembershipCardTierCode);

            //JoinDate (validated with Start_Date__c FROM Salesforce)
            softAssert.assertEquals(sfData.Start_Date__c, member.JoinedDate, "JoinDate ");
            _scenario.write("=============== JoinDate ===============");
            _scenario.write("Actual JoinDate  (SF) : " + sfData.Start_Date__c);
            _scenario.write("Expected JoinDate (Response) : " + member.JoinedDate);

        } else {

            //CardTier
            softAssert.assertTrue(member.MembershipCardTierCode.equalsIgnoreCase(sf.CardTier_sf), "CardTier ");
            _scenario.write("=============== CardTier ===============");
            _scenario.write("Actual CardTier  (SF) : " + sf.CardTier_sf);
            _scenario.write("Expected CardTier (Response) : " + member.MembershipCardTierCode);

            //Member Status
            softAssert.assertTrue(member.MemberStatus.equalsIgnoreCase(sf.cardStatus_sf), "Member Card Status ");
            _scenario.write("=============== Member Status ===============");
            _scenario.write("Actual Member Status  (SF) : " + sf.cardStatus_sf);
            _scenario.write("Expected Member Status (Response) : " + member.MemberStatus);

        }

        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }

    }


}
