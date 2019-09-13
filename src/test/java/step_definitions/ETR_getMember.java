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

public class ETR_getMember {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String POSTrequestBody;
    public DSAcreateMember_pageObjects member;
    public DSAcreateMember_pageObjects memberInfo;
    public SoftAssert softAssert;
    public String environment;
    public Login_pageobjects login;
    public SearchMember_pageobjects searchMember;
    public MemberValidation_pageobjects sf;
    public TakeScreenshot screenshot;
    public getDataFromSF sfData;
    
    public ETR_getMember() throws FileNotFoundException {
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
    
    @Given("^I read getMember POST request body from the Json file$")
    public void iReadGetMemberPOSTRequestBodyFromTheJsonFile() throws Throwable {

        //Storing the request body as a string
        POSTrequestBody = member.readRequestFromJson(member.getETRgetMemberJsonPath(environment));

        //PRINTING THE update response IN CUCUMBER REPORT
        _scenario.write("=============== The API Request body for ETR get member is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("================================ End of API Request body ===================================");
    }

    @Then("^I send the POST request to get Member in ETR$")
    public void iSendThePOSTRequestToGetMemberInETR() throws Throwable {
        member.getMemberETR(POSTrequestBody);
    }

    @Then("^I validate if ETR getMember response is valid$")
    public void iValidateIfETRGetMemberResponseIsValid() throws Throwable {

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

    @Then("^I read the details in get member response$")
    public void iReadTheDetailsInGetMemberResponse() throws Throwable {

        // CardNo
        System.out.println("Card No : " + member.CardNo);
        _scenario.write("Card No : " + member.CardNo);


        // MembershipTierID
        _scenario.write("Membership Tier  : " + member.MembershipTierID );
        System.out.println("Membership Tier  : " + member.MembershipTierID );

        // StatusDollarToRenew
        _scenario.write("StatusDollar To Renew : " + member.StatusDollarToRenew  );
        System.out.println("StatusDollar To Renew : " + member.StatusDollarToRenew  );

        //StartDate
        _scenario.write("StartDate : " + member.StartDate  );
        System.out.println("StartDate : " + member.StartDate  );

        //EndDate
        _scenario.write("EndDate  : " + member.EndDate );
        System.out.println("EndDate  : " + member.EndDate );

        //StatusDollarToUpgrade
        _scenario.write("StatusDollar To Upgrade   : " + member.StatusDollarToUpgrade  );
        System.out.println("StatusDollar To Upgrade   : " + member.StatusDollarToUpgrade  );

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
        _scenario.write("LastNameNative   : " + member.GenderCode  );
        System.out.println("LastNameNative   : " + member.GenderCode  );

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

        //MemberPointBalance
        _scenario.write("MemberPointBalance   : " + member.MemberPointBalance  );
        System.out.println("MemberPointBalance   : " + member.MemberPointBalance  );

        //Membership Status
        _scenario.write("Membership Status   : " + member.MemberStatus  );
        System.out.println("Membership Status   : " + member.MemberStatus  );

        //State Name
        _scenario.write("State Name    : " + member.ResStateNameText );
        System.out.println("State Name    : " + member.ResStateNameText );

        //Mobile1AreaCode
        _scenario.write("Mobile1AreaCode    : " + member.Mobile1AreaCode );
        System.out.println("Mobile1AreaCode    : " + member.Mobile1AreaCode );

    }

    @Then("^I validate if ETR Member is Dummy$")
    public void iValidateIfETRMemberIsDummy() throws Throwable {
        getSFaccessToken accessToken = new getSFaccessToken();
        String token = accessToken.getSFaccessToken();

        //passing the card number and getting the required data from SF
        sfData.querySFdataForDummyMmeber(token,member.CardNo);
        System.out.println("isDummy value --- "+sfData.isDummy__c);
    }

    @Then("^I login to SF to get ETR member;$")
    public void iLoginToSFToGetETRMember() throws Throwable {
        login.loginToSF();

    }

    @Then("^I search ETR get member using card number$")
    public void iSearchETRGetMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        searchMember.searchMemberThroughCardNumber(dataTable,member.CardNo);
    }

    @Then("^I read ETR member values from SF$")
    public void iReadETRMemberValuesFromSF() throws Throwable {
        Thread.sleep(1000);

        if(sfData.isDummy__c.equalsIgnoreCase("false")) { //When member is NOT A DUMMY, THEN DO ASSERTIONS AS USUAL

            sf.getCardStatus();
            sf.getPointBalance();
            // Elements in the General tab
            sf.switchToMainFrame();
            //Taking screenshot 1
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
            sf.getResStateNameText();
            sf.getCityNameText();
            sf.getMobile1AreaCode();


            //Elements in the Contact Preferences Tab
            sf.switchToContactPreferencesTab();
            //Taking screenshot 1
            screenshot.takeScreenshot();
            sf.getSpokenLanguage();


            //Elements in the Member Cycle Tab
            sf.switchToMemberCycleTab();
            //Taking screenshot 1
            screenshot.takeScreenshot();
            sf.getCardTier();
            sf.getCycleStartDate();
            sf.getCycleEndDate();
            sf.getStatusDollarToUpgrade();
            sf.getStatusDollarToRenew();
            sf.getCarryForwardDollarAmount();

        } else { //WHEN THE MEMBER IS A DUMMY, ONLY READ DATA FROM MEMBER CYCLE TAB
            sf.switchToMemberCycleTab();
            //Taking screenshot 1
            screenshot.takeScreenshot();
            sf.getCardTier();
            sf.getCycleStartDate();
            sf.getCycleEndDate();
            sf.getStatusDollarToUpgrade();
            sf.getStatusDollarToRenew();
            sf.getCarryForwardDollarAmount();
        }


    }

    @Then("^I validate ETR getMember details with SF$")
    public void iValidateETRGetMemberDetailsWithSF() throws Throwable {

        if(sfData.isDummy__c.equalsIgnoreCase("false")) {

            //MembershipTierID
            softAssert.assertTrue(sf.CardTier_sf.equalsIgnoreCase(member.MembershipTierID), "MembershipTier ");
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

            //Mobile1AreaCode   ============Added on 12/2/2019 as an enhancement based on ticket https://dfsrtr.atlassian.net/browse/SFDC-2199 ==============
            softAssert.assertEquals(sf.Mobile1AreaCode_sf,member.Mobile1AreaCode, "Mobile1AreaCode ");
            _scenario.write("============== Mobile1AreaCode ============= ");
            _scenario.write("Actual Mobile1AreaCode  (SF) : "+sf.Mobile1AreaCode_sf);
            _scenario.write("Expected Mobile1AreaCode  (Response) : "+member.Mobile1AreaCode);

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

            //SpokenLanguage
            softAssert.assertEquals(sf.SpokenLanguage_sf,member.SpokenLanguage, "SpokenLanguage ");
            _scenario.write("=============== SpokenLanguage ===============");
            _scenario.write("Actual SpokenLanguage (SF) : "+sf.SpokenLanguage_sf);
            _scenario.write("Expected SpokenLanguage (Response) : "+member.SpokenLanguage);

            //CarryForwardDollarAmount
            softAssert.assertEquals(sf.carryForwardDollarAmount_sf,member.CarryForwardDollarUSDAmount, "CarryForwardDollarAmount ");
            _scenario.write("=============== CarryForwardDollarAmount ===============");
            _scenario.write("Actual CarryForwardDollarAmount (SF) : "+sf.carryForwardDollarAmount_sf);
            _scenario.write("Expected CarryForwardDollarAmount (Response) : "+member.CarryForwardDollarUSDAmount);

            //Membership status
            softAssert.assertEquals(sf.cardStatus_sf.toLowerCase(),member.MemberStatus.toLowerCase(), "Membership status ");
            _scenario.write("=============== Membership status ===============");
            _scenario.write("Actual Membership status (SF) : "+sf.cardStatus_sf.toLowerCase());
            _scenario.write("Expected Membership status (Response) : "+member.MemberStatus.toLowerCase());

            //Point Balance
            String pointBalFromAPI = null;
            try {
                String decimalPointRow =  member.MemberPointBalance;
                pointBalFromAPI = member.MemberPointBalance.substring(0,decimalPointRow.indexOf(".")).trim();
            } catch (Exception e) {
                pointBalFromAPI="";
            }
            softAssert.assertEquals(sf.pointBalance_sf.toLowerCase(),pointBalFromAPI.toLowerCase(), "Point Balance ");
            _scenario.write("=============== Point Balance ===============");
            _scenario.write("Actual Point Balance (SF) : "+sf.pointBalance_sf);
            _scenario.write("Expected Point Balance (Response) : "+pointBalFromAPI);

            //State Name
            softAssert.assertEquals(sf.ResStateNameText_sf.toLowerCase(),member.ResStateNameText.toLowerCase(), "State Name ");
            _scenario.write("=============== State Name ===============");
            _scenario.write("Actual State Name (SF) : "+sf.ResStateNameText_sf);
            _scenario.write("Expected State Name (Response) : "+member.ResStateNameText);

            //City Name
            softAssert.assertEquals(sf.CityName_sf.toLowerCase(), member.ResCityNameText.toLowerCase(), "City Name ");
            _scenario.write("=============== City Name ===============");
            _scenario.write("Actual City Name (SF) : "+sf.ResStateNameText_sf.toLowerCase());
            _scenario.write("Expected City Name (Response) : "+member.ResCityNameText.toLowerCase());


        } else {

            //MembershipTierID
            softAssert.assertTrue(sf.CardTier_sf.equalsIgnoreCase(member.MembershipTierID), "MembershipTier ");
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
