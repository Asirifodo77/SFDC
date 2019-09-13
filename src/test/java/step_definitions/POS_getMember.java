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

import static org.testng.Assert.*;

public class POS_getMember {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String POSTrequestBody;
    public DSAcreateMember_pageObjects member;
    public DSAcreateMember_pageObjects memberInfo;
    public SoftAssert softAssert;
    public Assert hardAssert;
    public String environment;
    public Login_pageobjects login;
    public SearchMember_pageobjects searchMember;
    public MemberValidation_pageobjects sf;
    public TakeScreenshot screenshot;
    public getDataFromSF sfData;

    public POS_getMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        environment= System.getProperty("Environment");
        member = new DSAcreateMember_pageObjects(_scenario);
        login = new Login_pageobjects(driver,_scenario);
        searchMember = new SearchMember_pageobjects(driver,_scenario);
        sf = new MemberValidation_pageobjects(driver,_scenario);
        softAssert = new SoftAssert();
        screenshot = new TakeScreenshot(driver,_scenario);
        sfData = new getDataFromSF();
    }


    @Given("^I read POS getMember POST request body from the Json file$")
    public void iReadPOSGetMemberPOSTRequestBodyFromTheJsonFile() throws Throwable {
        POSTrequestBody = member.readRequestFromJson(member.getPOSgetMemberJsonPath(environment));

        //PRINTING THE update response IN CUCUMBER REPORT
        _scenario.write("=============== The API Request body for POS get member is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("================================ End of API Request body ===================================");

    }

    @Then("^I send the POST request to get Member in POS$")
    public void iSendThePOSTRequestToGetMemberInPOS() throws Throwable {
        member.getMemberPOS(POSTrequestBody);
    }

    @Then("^I validate if POS getMember response is valid$")
    public void iValidateIfPOSGetMemberResponseIsValid() throws Throwable {

        //checking if the member card number is found
        try {
            assertFalse(member.ReturnMessage.equalsIgnoreCase(Property.API_CARDNO_NOT_FOUND_MESSAGE));
        } catch (AssertionError e) {
            System.out.println("Member with card Number : "+member.CardNo+ " is not found in "+ environment);
            _scenario.write("Member with card Number : "+member.CardNo+ " is not found in "+ environment);
            fail("TEST FAILED INTENTIONALLY !.  Member with card Number : "+member.CardNo+ " is not found in "+ environment);
        }

        try {
            assertTrue((member.ReturnMessage.equalsIgnoreCase(Property.API_EXPECTED_SUCCESS_MESSAGE) || member.ReturnMessage.equalsIgnoreCase(Property.API_INACTIVE_CARD_MESSAGE) ),"The API response is : "+member.ReturnMessage);
        } catch (AssertionError e) {
            System.out.println("The test was failed since the API response did not return a success message. Actual message - "+member.ReturnMessage);
            _scenario.write("The test was failed since the API response did not return a success message. Actual message - "+member.ReturnMessage);
            fail("TEST FAILED INTENTIONALLY !.  API response did not return a success message. Actual message - "+member.ReturnMessage);
        }
    }

    @Then("^I validate if POS member is Dummy$")
    public void iValidateIfPOSMemberIsDummy() throws Throwable {
        getSFaccessToken accessToken = new getSFaccessToken();
        String token = accessToken.getSFaccessToken();

        //passing the card number and getting the required data from SF
        sfData.querySFdataForDummyMmeber(token,member.CardNo);
        System.out.println("isDummy value --- "+sfData.isDummy__c);

    }

    @Then("^I read the details in POS get member response$")
    public void iReadTheDetailsInPOSGetMemberResponse() throws Throwable {
        //Return message from Response
        System.out.println("Return Message : " + member.ReturnMessage);

        // CardNo
        System.out.println("Card No : " + member.CardNo);
        _scenario.write("Card No : " + member.CardNo);

        // Membership Card Status
        _scenario.write("Member Card Status   : " + member.MembershipcardStatusCode );
        System.out.println("Member Card Status   : " + member.MembershipcardStatusCode );

        // MembershipTierID
        _scenario.write("Membership Tier  : " + member.MembershipTierID );
        System.out.println("Membership Tier  : " + member.MembershipTierID );

        //StatusDollarToUpgrade
        _scenario.write("StatusDollar To Upgrade   : " + member.StatusDollarToUpgrade  );
        System.out.println("StatusDollar To Upgrade   : " + member.StatusDollarToUpgrade  );

        // StatusDollarToRenew
        _scenario.write("StatusDollar To Renew : " + member.StatusDollarToRenew  );
        System.out.println("StatusDollar To Renew : " + member.StatusDollarToRenew  );

        //StartDate
        _scenario.write("StartDate : " + member.StartDate  );
        System.out.println("StartDate : " + member.StartDate  );

        //EndDate
        _scenario.write("EndDate  : " + member.EndDate );
        System.out.println("EndDate  : " + member.EndDate );

        //CarryForwardDollarUSDAmount  (Status Dollar)
        _scenario.write("CarryForwardDollarUSDAmount  : " + member.CarryForwardDollarUSDAmount );
        System.out.println("CarryForwardDollarUSDAmount  : " + member.CarryForwardDollarUSDAmount );

        //MemberPointBalance (Point Balance)
        _scenario.write("MemberPointBalance  : " + member.MemberPointBalance );
        System.out.println("MemberPointBalance  : " + member.MemberPointBalance );

        // The standard set ####################################################
        //FirstName
        _scenario.write("FirstName   : " + member.FirstName  );
        System.out.println("FirstName   : " + member.FirstName  );

        //LastName
        _scenario.write("LastName   : " + member.LastName  );
        System.out.println("LastName   : " + member.LastName  );

        //TitleCode
        _scenario.write("TitleCode   : " + member.TitleCode  );
        System.out.println("TitleCode   : " + member.TitleCode  );

        //NativeSalutation
        _scenario.write("NativeSalutation   : " + member.NativeSalutation  );
        System.out.println("NativeSalutation   : " + member.NativeSalutation  );

        //FirstNameNative
        _scenario.write("FirstNameNative   : " + member.FirstNameNative  );
        System.out.println("FirstNameNative   : " + member.FirstNameNative  );

        //LastNameNative
        _scenario.write("LastNameNative   : " + member.LastNameNative  );
        System.out.println("LastNameNative   : " + member.LastNameNative  );

        //GenderCode
        _scenario.write("Gender Code   : " + member.GenderCode  );
        System.out.println("Gender Code   : " + member.GenderCode  );

        //EmailAddressText
        _scenario.write("EmailAddressText   : " + member.EmailAddressText  );
        System.out.println("EmailAddressText   : " + member.EmailAddressText  );

        //ContactNumberText (Mobile Phone)
        _scenario.write("ContactNumberText   : " + member.ContactNumberText  );
        System.out.println("ContactNumberText   : " + member.ContactNumberText  );

        //MobileNo2 (Other Phone 1)
        _scenario.write("MobileNo2   : " + member.MobileNo2  );
        System.out.println("MobileNo2   : " + member.MobileNo2  );

        //HomeNo (Other Phone 2)
        _scenario.write("HomeNo   : " + member.HomeNo  );
        System.out.println("HomeNo   : " + member.HomeNo  );

        //CountryNameText   (Location of Residence (Mailing Address))
        _scenario.write("CountryNameText   : " + member.CountryNameText  );
        System.out.println("CountryNameText   : " + member.CountryNameText  );

        //MailingAddress1 (Mailing Address Line 1)
        _scenario.write("MailingAddress1   : " + member.MailingAddress1  );
        System.out.println("MailingAddress1   : " + member.MailingAddress1  );

        //MailingAddress2 (Mailing Address Line 2)
        _scenario.write("MailingAddress2   : " + member.MailingAddress2  );
        System.out.println("MailingAddress2   : " + member.MailingAddress2  );

        //MailingAddress3  (Mailing Address Line 3)
        _scenario.write("MailingAddress3   : " + member.MailingAddress3  );
        System.out.println("MailingAddress3   : " + member.MailingAddress3  );

        //SpokenLanguage
        _scenario.write("SpokenLanguage   : " + member.SpokenLanguage  );
        System.out.println("SpokenLanguage   : " + member.SpokenLanguage  );

        //ExpiringDate
        _scenario.write("ExpiringDate : "+ member.ExpiringDate);
        System.out.println(" =========  ExpiringDate : "+ member.ExpiringDate);
        //  ##########################################################################

    }

    @Then("^I login to SF to get POS member;$")
    public void iLoginToSFToGetPOSMember() throws Throwable {
        login.loginToSF();
    }

    @Then("^I search POS get member using card number$")
    public void iSearchPOSGetMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        searchMember.searchMemberThroughCardNumber(dataTable,member.CardNo);
    }

    @Then("^I read POS member values from SF$")
    public void iReadPOSMemberValuesFromSF() throws Throwable {
        Thread.sleep(1000);

        driver.switchTo().defaultContent();
        sf.getMembershipcardStatusCode(); // Member Card Status
        System.out.println("getMember Card Status : "+sf.MemberStatus_sf);

        sf.getPointBalance();
        System.out.println("getPointBalance : "+sf.pointBalance_sf);

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

        if(sfData.isDummy__c.equalsIgnoreCase("false")) { //When Member is NOT A DUMMY, do all as usual
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
            System.out.println("getCarryForwardDollarAmount : "+sf.carryForwardDollarAmount_sf);

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
            System.out.println("getCarryForwardDollarAmount : "+sf.carryForwardDollarAmount_sf);
        }


    }

    @Then("^I validate POS getMember details with SF$")
    public void iValidatePOSGetMemberDetailsWithSF() throws Throwable {

        if(sfData.isDummy__c.equalsIgnoreCase("false")) { //When member is NOT A DUMMY, do assertions as usual

            //Member Card Status
            softAssert.assertEquals(sf.MemberCardStatus_sf.toLowerCase(),member.MembershipcardStatusCode.toLowerCase(), "Membership card StatusCode ");
            _scenario.write("=============== MemberStatus ===============");
            _scenario.write("Actual Member Card Status (SF) : "+sf.MemberCardStatus_sf);
            _scenario.write("Expected Member Card Status (Response) : "+member.MembershipcardStatusCode);

            //PointBalance
            softAssert.assertEquals(sf.pointBalance_sf,member.BalancePointCount, "PointBalance ");
            _scenario.write("=============== PointBalance ===============");
            _scenario.write("Actual PointBalance (SF) : "+sf.pointBalance_sf);
            _scenario.write("Expected PointBalance (Response) : "+member.BalancePointCount);

            //CarryForwardDollarAmount
            softAssert.assertEquals(sf.carryForwardDollarAmount_sf.toLowerCase(),member.CarryForwardDollarUSDAmount.toLowerCase(), "CarryForwardDollarAmount ");
            _scenario.write("=============== CarryForwardDollarAmount ===============");
            _scenario.write("Actual CarryForwardDollarAmount (SF) : "+sf.carryForwardDollarAmount_sf);
            _scenario.write("Expected CarryForwardDollarAmount (Response) : "+member.CarryForwardDollarUSDAmount);

            //MembershipTierID
            softAssert.assertEquals(sf.CardTier_sf.toLowerCase(),member.MembershipTierID.toLowerCase(), "MembershipTier ");
            _scenario.write("=============== MembershipTier ===============");
            _scenario.write("Actual MembershipTier (SF) : "+sf.CardTier_sf);
            _scenario.write("Expected MembershipTier (Response) : "+member.MembershipTierID);

            //StatusDollarToRenew
            softAssert.assertEquals(sf.statusDollarToRenew_sf,member.StatusDollarToRenew, "StatusDollarToRenew ");
            _scenario.write("=============== StatusDollarToRenew ===============");
            _scenario.write("Actual StatusDollarToRenew (SF) : "+sf.statusDollarToRenew_sf);
            _scenario.write("Expected StatusDollarToRenew (Response) : "+member.StatusDollarToRenew);

            //StartDate
            softAssert.assertEquals(sf.CycleStartDate_sf,member.StartDate, "StartDate ");
            _scenario.write("=============== StartDate ===============");
            _scenario.write("Actual StartDate (SF) : "+sf.CycleStartDate_sf);
            _scenario.write("Expected StartDate (Response) : "+member.StartDate);

            //EndDate
            softAssert.assertEquals(sf.CycleEndDate_sf,member.EndDate, "EndDate ");
            _scenario.write("=============== EndDate ===============");
            _scenario.write("Actual EndDate (SF) : "+sf.CycleEndDate_sf);
            _scenario.write("Expected EndDate (Response) : "+member.EndDate);

            //StatusDollarToUpgrade
            softAssert.assertEquals(sf.statusDollarToUpgrade_sf ,member.StatusDollarToUpgrade, "StatusDollarToUpgrade ");
            _scenario.write("=============== StatusDollarToUpgrade ===============");
            _scenario.write("Actual StatusDollarToUpgrade (SF) : "+sf.statusDollarToUpgrade_sf);
            _scenario.write("Expected StatusDollarToUpgrade (Response) : "+member.StatusDollarToUpgrade);

            //First Name
            softAssert.assertEquals(sf.FirstName_sf,member.FirstName, "First Name ");
            _scenario.write("=============== First Name ===============");
            _scenario.write("Actual First Name (SF) : "+sf.FirstName_sf);
            _scenario.write("Expected First Name (Response) : "+member.FirstName);

            //Last Name
            softAssert.assertEquals(sf.LastName_sf,member.LastName, "Last Name ");
            _scenario.write("=============== Last Name ===============");
            _scenario.write("Actual Last Name (SF) : "+sf.LastName_sf);
            _scenario.write("Expected Last Name (Response) : "+member.LastName);

            //TitleCode
            softAssert.assertEquals(sf.Salutation_sf,member.TitleCode, "TitleCode ");
            _scenario.write("=============== TitleCode ===============");
            _scenario.write("Actual TitleCode (SF) : "+sf.Salutation_sf);
            _scenario.write("Expected TitleCode (Response) : "+member.TitleCode);

            //NativeSalutation
            softAssert.assertEquals(sf.NativeSalutation_sf,member.NativeSalutation, "NativeSalutation ");
            _scenario.write("=============== NativeSalutation ===============");
            _scenario.write("Actual NativeSalutation (SF) : "+sf.NativeSalutation_sf);
            _scenario.write("Expected NativeSalutation (Response) : "+member.NativeSalutation);

            //FirstNameNative
            softAssert.assertEquals(sf.FirstNameNative_sf,member.FirstNameNative, "FirstNameNative ");
            _scenario.write("=============== FirstNameNative ===============");
            _scenario.write("Actual FirstNameNative (SF) : "+sf.FirstNameNative_sf);
            _scenario.write("Expected FirstNameNative (Response) : "+member.FirstNameNative);

            //LastNameNative
            softAssert.assertEquals(sf.LastNameNative_sf,member.LastNameNative, "LastNameNative ");
            _scenario.write("=============== LastNameNative ===============");
            _scenario.write("Actual LastNameNative (SF) : "+sf.LastNameNative_sf);
            _scenario.write("Expected LastNameNative (Response) : "+member.LastNameNative);

            //GenderCode
            softAssert.assertEquals(sf.Gender_sf,member.GenderCode, "GenderCode ");
            _scenario.write("=============== GenderCode ===============");
            _scenario.write("Actual GenderCode (SF) : "+sf.Gender_sf);
            _scenario.write("Expected GenderCode (Response) : "+member.GenderCode);

            //EmailAddressText
            softAssert.assertEquals(sf.EmailAddressText_sf,member.EmailAddressText, "EmailAddressText ");
            _scenario.write("=============== EmailAddressText ===============");
            _scenario.write("Actual EmailAddressText (SF) : "+sf.EmailAddressText_sf);
            _scenario.write("Expected EmailAddressText (Response) : "+member.EmailAddressText);

            //ContactNumberText
            softAssert.assertEquals(sf.ContactNumberText_sf,member.ContactNumberText, "ContactNumberText ");
            _scenario.write("=============== ContactNumberText ===============");
            _scenario.write("Actual ContactNumberText (SF) : "+sf.ContactNumberText_sf);
            _scenario.write("Expected ContactNumberText (Response) : "+member.ContactNumberText);

            //MobileNo2
            softAssert.assertEquals(sf.OtherPhone1_sf,member.MobileNo2, "MobileNo2 ");
            _scenario.write("=============== MobileNo2 ===============");
            _scenario.write("Actual MobileNo2 (SF) : "+sf.OtherPhone1_sf);
            _scenario.write("Expected MobileNo2 (Response) : "+member.MobileNo2);

            //HomeNo
            softAssert.assertEquals(sf.OtherPhone2_sf,member.HomeNo, "HomeNo ");
            _scenario.write("=============== HomeNo ===============");
            _scenario.write("Actual HomeNo (SF) : "+sf.OtherPhone2_sf);
            _scenario.write("Expected HomeNo (Response) : "+member.HomeNo);

            //CountryNameText
            softAssert.assertEquals(sf.EnglishCountry_sf,member.CountryNameText, "CountryNameText ");
            _scenario.write("=============== CountryNameText ===============");
            _scenario.write("Actual CountryNameText (SF) : "+sf.EnglishCountry_sf);
            _scenario.write("Expected CountryNameText (Response) : "+member.CountryNameText);

            //MailingAddress1
            softAssert.assertEquals(sf.MailingAddress1_sf,member.MailingAddress1, "MailingAddress1 ");
            _scenario.write("=============== MailingAddress1 ===============");
            _scenario.write("Actual MailingAddress1 (SF) : "+sf.MailingAddress1_sf);
            _scenario.write("Expected MailingAddress1 (Response) : "+member.MailingAddress1);

            //MailingAddress2
            softAssert.assertEquals(sf.MailingAddress2_sf,member.MailingAddress2, "MailingAddress2 ");
            _scenario.write("=============== MailingAddress2 ===============");
            _scenario.write("Actual MailingAddress2 (SF) : "+sf.MailingAddress2_sf);
            _scenario.write("Expected MailingAddress2 (Response) : "+member.MailingAddress2);

            //MailingAddress3
            softAssert.assertEquals(sf.MailingAddress3_sf,member.MailingAddress3, "MailingAddress3 ");
            _scenario.write("=============== MailingAddress3 ===============");
            _scenario.write("Actual MailingAddress3 (SF) : "+sf.MailingAddress3_sf);
            _scenario.write("Expected MailingAddress3 (Response) : "+member.MailingAddress3);

            //SpokenLanguage  //SpokenLanguage_sf
            softAssert.assertEquals(sf.SpokenLanguage_sf,member.SpokenLanguage, "SpokenLanguage ");
            _scenario.write("=============== SpokenLanguage ===============");
            _scenario.write("Actual SpokenLanguage (SF) : "+sf.SpokenLanguage_sf);
            _scenario.write("Expected SpokenLanguage (Response) : "+member.SpokenLanguage);

            //City Name
            softAssert.assertEquals(sf.CityName_sf.toLowerCase(), member.ResCityNameText.toLowerCase(), "City Name ");
            _scenario.write("=============== City Name ===============");
            _scenario.write("Actual City Name (SF) : "+sf.ResStateNameText_sf.toLowerCase());
            _scenario.write("Expected City Name (Response) : "+member.ResCityNameText.toLowerCase());

            //Current status dollar (on the right hand pannel)
            softAssert.assertEquals(sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase(), member.StatusDollarUSDAmount.toLowerCase(), "Current status dollar ");
            _scenario.write("=============== Current status dollar ===============");
            _scenario.write("Actual Current status dollar (SF) : "+sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase());
            _scenario.write("Expected Current status dollar (Response) : "+member.StatusDollarUSDAmount.toLowerCase());

            // Expiry date (on the right hand pannel)
            softAssert.assertEquals(sf.expiryDate_sf.toLowerCase(), member.ExpiringDate.toLowerCase(), "Expiry date ");
            _scenario.write("=============== Expiry date ===============");
            _scenario.write("Actual Expiry date (SF) : "+sf.expiryDate_sf.toLowerCase());
            _scenario.write("Expected Expiry date (Response) : "+member.ExpiringDate.toLowerCase());

        } else {

            //MembershipTierID
            softAssert.assertEquals(sf.CardTier_sf.toLowerCase(),member.MembershipTierID.toLowerCase(), "MembershipTier ");
            _scenario.write("=============== MembershipTier ===============");
            _scenario.write("Actual MembershipTier (SF) : "+sf.CardTier_sf);
            _scenario.write("Expected MembershipTier (Response) : "+member.MembershipTierID);

            //StartDate
            softAssert.assertEquals(sf.CycleStartDate_sf,member.StartDate, "StartDate ");
            _scenario.write("=============== StartDate ===============");
            _scenario.write("Actual StartDate (SF) : "+sf.CycleStartDate_sf);
            _scenario.write("Expected StartDate (Response) : "+member.StartDate);

            //EndDate
            softAssert.assertEquals(sf.CycleEndDate_sf,member.EndDate, "EndDate ");
            _scenario.write("=============== EndDate ===============");
            _scenario.write("Actual EndDate (SF) : "+sf.CycleEndDate_sf);
            _scenario.write("Expected EndDate (Response) : "+member.EndDate);

            //StatusDollarToUpgrade
            softAssert.assertEquals(sf.statusDollarToUpgrade_sf ,member.StatusDollarToUpgrade, "StatusDollarToUpgrade ");
            _scenario.write("=============== StatusDollarToUpgrade ===============");
            _scenario.write("Actual StatusDollarToUpgrade (SF) : "+sf.statusDollarToUpgrade_sf);
            _scenario.write("Expected StatusDollarToUpgrade (Response) : "+member.StatusDollarToUpgrade);

            //StatusDollarToRenew
            softAssert.assertEquals(sf.statusDollarToRenew_sf,member.StatusDollarToRenew, "StatusDollarToRenew ");
            _scenario.write("=============== StatusDollarToRenew ===============");
            _scenario.write("Actual StatusDollarToRenew (SF) : "+sf.statusDollarToRenew_sf);
            _scenario.write("Expected StatusDollarToRenew (Response) : "+member.StatusDollarToRenew);

            //CarryForwardDollarAmount
            softAssert.assertEquals(sf.carryForwardDollarAmount_sf,member.CarryForwardDollarUSDAmount, "CarryForwardDollarAmount ");
            _scenario.write("=============== CarryForwardDollarAmount ===============");
            _scenario.write("Actual CarryForwardDollarAmount (SF) : "+sf.carryForwardDollarAmount_sf);
            _scenario.write("Expected CarryForwardDollarAmount (Response) : "+member.CarryForwardDollarUSDAmount);

            //Current status dollar (on the right hand pannel)
            softAssert.assertEquals(sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase(), member.StatusDollarUSDAmount.toLowerCase(), "Current status dollar ");
            _scenario.write("=============== Current status dollar ===============");
            _scenario.write("Actual Current status dollar (SF) : "+sf.Mem_points_Current_status_Dollar_MembershipTier_sf.toLowerCase());
            _scenario.write("Expected Current status dollar (Response) : "+member.StatusDollarUSDAmount.toLowerCase());

            // Expiry date (on the right hand pannel)
            softAssert.assertEquals(sf.expiryDate_sf.toLowerCase(), member.ExpiringDate.toLowerCase(), "Expiry date ");
            _scenario.write("=============== Expiry date ===============");
            _scenario.write("Actual Expiry date (SF) : "+sf.expiryDate_sf.toLowerCase());
            _scenario.write("Expected Expiry date (Response) : "+member.ExpiringDate.toLowerCase());

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
