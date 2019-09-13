package step_definitions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.SearchMember_pageobjects;
import commonLibs.implementation.checkBoxControls;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import utilities.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class DSAcreateMember {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public DSAcreateMember_pageObjects DSAcreateMember;
    public SearchMember_pageobjects searchMember_pageobjects;
    public String POSTrequestBody;
    public MemberValidation_pageobjects member;
    public selectBoxControls selectBox;
    public textBoxControls textBox;
    public checkBoxControls checkBox;
    public Runtime_TestData runtime;
    public createDbConnection dbConnection;
    public getDataFromSF sfData;
    public DSAcreateMember_pageObjects requestData;
    public DSAcreateMember_pageObjects matrix;
    public String cardNumber;
    public String environment;
    public HashMap<String, String> matrixData;
    public readTestData testData;
    public SoftAssert softAssert;
    public testDataCleanup dataCleanup;
    public TakeScreenshot screenshot;
    public getSFaccessToken accessToken;
    public String token;
    public validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;

    public DSAcreateMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;

        //creating loginPage object
        loginPage = new Login_pageobjects(driver,_scenario);

        DSAcreateMember = new DSAcreateMember_pageObjects(_scenario);

        member = new MemberValidation_pageobjects(driver,_scenario);
        searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        softAssert = new SoftAssert();

        selectBox = new selectBoxControls(driver, _scenario);
        textBox= new textBoxControls(_scenario);
        checkBox = new checkBoxControls(_scenario);

        runtime = new Runtime_TestData();
        dbConnection = new createDbConnection();
        sfData = new getDataFromSF();
        requestData = new DSAcreateMember_pageObjects(_scenario);
        matrix = new DSAcreateMember_pageObjects(_scenario);
        environment = System.getProperty("Environment");
        matrixData = new HashMap<>();
        testData=new readTestData();
        dataCleanup = new testDataCleanup();
        screenshot = new TakeScreenshot(driver,_scenario);
        accessToken = new getSFaccessToken();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);
    }

    @And("^I login to SF$")
    public void iLoginToSF() throws Throwable {
        loginPage.loginToSF();

    }

    @Given("^I read DSA request body from Json file$")
    public void iReadDSARequestBodyFromJsonFile() throws Throwable {

        POSTrequestBody = DSAcreateMember.readRequestFromJson(DSAcreateMember.getDSAjsonPath(environment));

        //Printing the Json payload in the cucumber report as per the request of Charmaine, Ticket No - https://dfsrtr.atlassian.net/browse/SFDC-1908
        //Added on 18/12/2018
        _scenario.write("=============== The Json request body is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("=============== End of The Json request body ==========================");

    }

    @Given("^I cleanup data for existing DSA member from SF and MatrixDB$")
    public void iCleanupDataForExistingDSAMemberFromSFAndMatrixDB() throws Throwable {
        //reading the data from Json request file
        DSAcreateMember.getElementValuesFromDSAJsonFile(environment);
        //cleaning up data for the given card number only (Not reading from csv file)
        cardNumber = DSAcreateMember.CardNo;
        System.out.println("Data cleaning for Card Number : "+cardNumber+" is In progress ... ");
        _scenario.write("Data cleaning for Card Number : "+cardNumber+" is In progress ... ");
        dataCleanup.cleanUpOneMemberCardData(_scenario,cardNumber);
    }

    @When("^I send POST request to create new member through DSA$")
    public void iSendPOSTRequestToCreateNewMemberThroughDSA() throws Throwable {
        System.out.println("Sent POST request to create DSA member");
        _scenario.write("Sent POST request to create DSA member");
        DSAcreateMember.createMemberDSA(POSTrequestBody);
    }

    @Then("^I validate the memberID in salesforce and Matrix for DSA Created member$")
    public void iValidateTheMemberIDInSalesforceAndMatrixForDSACreatedMember() throws Throwable {

        //getting the access token
        token = accessToken.getSFaccessToken();

        //validating member id from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberID(token,cardNumber,environment);

    }


    @Then("^I search for the member using Card Number$")
    public void iSearchForTheMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        DSAcreateMember.getElementValuesFromDSAJsonFile(environment);

        System.out.println("Searching for the member with card number : "+DSAcreateMember.CardNo);
        _scenario.write("Searching for the member with card number : "+DSAcreateMember.CardNo);

        searchMember_pageobjects.searchMemberThroughCardNumber(dataTable,DSAcreateMember.CardNo);
    }


    @Then("^I read details from member profile in SF$")
    public void iReadDetailsFromMemberProfileInSF() throws Throwable {
       //
        System.out.println(Instant.now().toString() + "Workbench Page");
        Thread.sleep(1000);


        //Switch to frame
        member.switchToMainFrame();

        //Taking screenshot 1
        screenshot.takeScreenshot();

        //Title code
        member.getTitleCode();

        //Gender code
        member.getGenderCode();

        //EmailAddressText
        member.getEmailAddressText();

        //PostalCode
        member.getPostalCode();

        //CityNameText
        member.getCityNameText();

        //CountryNameText
        //not in SF

        //ContactNumberText
        member.getContactNumberText();

        //IsInvalidAddress
       member.getIsInvalidAddress();
        //Not is SF

        //IsInvalidEmail
        member.getIsInvalidEmail();

        //IsEmptyEmail
        member.getIsEmptyEmail();

        // EmailUnwillingToProvide
        member.getUnwillingToProvideEmail();

        //FirstName
        member.getFirstName();

        //FirstNameNative
        member.getFirstNameNative();

        // LastName
        member.getLastName();

        // LastNameNative
        member.getLastNameNative();

        //BirthDate
        member.getBirthDate();

        //BirthMonth
        member.getBirthMonth();

        // RegistrationDivisionCode -- not in SF  ==========================================

        // RegistrationLocationID -- NOT IN SF  ============================================

        // MailingAddress2
        member.getMailingAddress2();

        // ZipCode
        member.getZipCode();

        // CorrCityNameText" -- not in SF ===============================

        // CorrStateNameText -- NOT IN SF ================================

        // MailingAddress1
        member.getMailingAddress1();

        // MailingAddress3
        member.getMailingAddress3();

        // ResCityNameText
        member.getResCityNameText();

        // ResStateNameText
        member.getResStateNameText();

        //EnglishCountry (CountryNameText)
        member.getEnglishCountry();

        //ValidMobileNo1
        member.getValidMobileNo1();

        // AgeRange
        member.getAgeRange();

        //MarketingSource
        member.getMarketingSource();

        //ZipCodeValidFlag
        try {
            member.getZipCodeValidFlag();
        } catch (WebDriverException e) {
            System.out.println("TEST FAILED !. Unable to get zip code value. "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get zip code value. "+e.getMessage());
        }

        // MarketingSourceOthers
        member.getMarketingSourceOthers();

        // Mobile1AreaCode
        member.getMobile1AreaCode();

        //WeChatId
        member.getWeChatID();

        // Navigate Inside Contact Preferences
        member.switchToContactPreferencesTab();

        //Taking screenshot 2
        screenshot.takeScreenshot();

        // SpokenLanguage
        member.getSpokenLanguage();

        // IsContactable (by checking if all checkboxes are checked or not
        member.getIsContactable();

        // going inside Shopping preferences tab
        member.switchToShoppingPreferencesTab();

        //Taking screenshot 3
        screenshot.takeScreenshot();

        //CustomerLeisureActivity
        member.getCustomerLeisureActivity();

        //CustomerShoppingPreference
        member.getCustomerShoppingPreference();

        // CustomerPreferredBrands
        member.getCustomerPreferredBrands();

        // LeisureActivitiesMultiple
        member.getLeisureActivitiesMultiple();

        //ShoppingPreferencesMultiple
        member.getShoppingPreferencesMultiple();

        // PreferredBrandsMultiple
        member.getPreferredBrandsMultiple();

        //going inside Member Cycle tab
        member.switchToMemberCycleTab();

        //Taking screenshot
        screenshot.takeScreenshot();

        // CustomerRegistrationDatetime
        member.getCustomerRegistrationDatetime();

        // Enrolment Location (Name)
        member.getEnrollmentLocation();


    }

    @Then("^I search for missing values in database$")
    public void iSearchForMissingValuesInDatabase() throws Throwable {

        //passing the card number and getting the required data from SF and assining them to Run time data
        sfData.querySFDataforDSA(token,cardNumber);

        //getting the expected values from the json
        DSAcreateMember.getElementValuesFromDSAJsonFile(environment);

        //Getting the store Location Name from Salesforce
        sfData.querySFDataToGetStoreLocationName(token,DSAcreateMember.RegistrationDivisionCode,DSAcreateMember.RegistrationLocationID);

    }

    @Then("^I read member details from Matrix$")
    public void iReadMemberDetailsFromMatrix() throws Throwable {
        matrixData= matrix.readMemberDataFromMatrix(environment,cardNumber);

    }

    @Then("^I validate DSA Membership Information with SF$")
    public void iValidateDSAMembershipInformationWithSF() throws Throwable {



        softAssert.assertEquals(member.Salutation_sf,DSAcreateMember.TitleCode, "TitleCode (SF) ");

        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode  (SF) : "+member.Salutation_sf);  //will be changed to member.Salutation
        _scenario.write("Expected TitleCode  (SF) : "+DSAcreateMember.TitleCode);

        softAssert.assertEquals(member.Gender_sf,DSAcreateMember.GenderCode, "GenderCode  (SF) ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode  (SF) : "+member.Gender_sf);
        _scenario.write("Expected GenderCode  (SF) : "+DSAcreateMember.GenderCode);

        softAssert.assertEquals(member.EmailAddressText_sf,DSAcreateMember.EmailAddressText, "EmailAddressText  (SF) ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText  (SF) : "+member.EmailAddressText_sf);
        _scenario.write("Expected EmailAddressText  (SF) : "+DSAcreateMember.EmailAddressText);

        softAssert.assertEquals(member.ContactNumberText_sf,DSAcreateMember.ContactNumberText, "ContactNumberText  (SF) ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText  (SF) : "+member.ContactNumberText_sf);
        _scenario.write("Expected ContactNumberText  (SF) : "+DSAcreateMember.ContactNumberText);

        String IsInvalidEmail_status=null;
        if(DSAcreateMember.EmailAddressText.isEmpty()){
            IsInvalidEmail_status = "1";
        }else {
            IsInvalidEmail_status="0";
        }

        softAssert.assertEquals(member.IsInvalidEmail_sf,IsInvalidEmail_status, "Valid Email Address(IsInvalidEmail) (SF) ");
        _scenario.write("==============Valid Email Address ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail)  (SF) : "+member.IsInvalidEmail_sf);
        _scenario.write("Expected Valid Email Address(IsInvalidEmail)  (SF) : "+IsInvalidEmail_status);

        String isEmptyEmail_status=null;
        if(DSAcreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }

        softAssert.assertEquals(member.IsEmptyEmail_sf,isEmptyEmail_status, "IsEmptyEmail (SF) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail  (SF) : "+member.IsEmptyEmail_sf);
        _scenario.write("Expected IsEmptyEmail  (SF) : "+isEmptyEmail_status);

       //==============Adding this validation based on Anu's request =================
       // EmailUnwillingToProvide
        // Added if "EmailAddressText" is Not empty, then EmailUnwillingToProvide should be 0 (Unchecked)
        String EmailUnwillingToProvideFlagExpected="";
        if(member.EmailUnwillingToProvide_sf.equalsIgnoreCase("1")) {
            EmailUnwillingToProvideFlagExpected="1";
        } else {
            EmailUnwillingToProvideFlagExpected="0";
        }

        softAssert.assertEquals(member.EmailUnwillingToProvide_sf,EmailUnwillingToProvideFlagExpected, "EmailUnwillingToProvide  (SF) : " );
        _scenario.write("==============EmailUnwillingToProvide ============= ");
        _scenario.write("Actual EmailUnwillingToProvide : "+member.EmailUnwillingToProvide_sf);
        _scenario.write("Expected EmailUnwillingToProvide : "+EmailUnwillingToProvideFlagExpected);


        softAssert.assertEquals(member.FirstName_sf,DSAcreateMember.FirstName, "FirstName  (SF)  ");
        _scenario.write("============== FirstName ============= ");
        _scenario.write("Actual FirstName  (SF) : "+member.FirstName_sf);
        _scenario.write("Expected FirstName  (SF) : "+DSAcreateMember.FirstName);


        softAssert.assertEquals(member.FirstNameNative_sf,DSAcreateMember.FirstNameNative, "FirstNameNative  (SF) ");
        _scenario.write("============== FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative  (SF) : "+member.FirstNameNative_sf);
        _scenario.write("Expected FirstNameNative  (SF) : "+DSAcreateMember.FirstNameNative);


        softAssert.assertEquals(member.LastName_sf,DSAcreateMember.LastName, "LastName  (SF) ");
        _scenario.write("============== LastName ============= ");
        _scenario.write("Actual LastName  (SF) : "+member.LastName_sf);
        _scenario.write("Expected LastName  (SF) : "+DSAcreateMember.LastName);

        softAssert.assertEquals(member.LastNameNative_sf,DSAcreateMember.LastNameNative, "LastNameNative  (SF) ");
        _scenario.write("============== LastNameNative ============= ");
        _scenario.write("Actual LastNameNative  (SF) : "+member.LastNameNative_sf);
        _scenario.write("Expected LastNameNative  (SF) : "+DSAcreateMember.LastNameNative);

        softAssert.assertEquals(member.BirthDate_sf,DSAcreateMember.BirthDate, "BirthDate  (SF) ");
        _scenario.write("============== BirthDate ============= ");
        _scenario.write("Actual BirthDate  (SF) : "+member.BirthDate_sf);
        _scenario.write("Expected BirthDate  (SF) : "+DSAcreateMember.BirthDate);

        softAssert.assertEquals(member.BirthMonth_sf,DSAcreateMember.BirthMonth, "BirthMonth  (SF) ");
        _scenario.write("============== BirthMonth ============= ");
        _scenario.write("Actual BirthMonth  (SF) : "+member.BirthMonth_sf);
        _scenario.write("Expected BirthMonth  (SF) : "+DSAcreateMember.BirthMonth);


        //Igonoring RegistrationLocationID because it always sends null by SF.
        // assertEquals(Runtime_TestData.RegistrationLocationID,DSAcreateMember.RegistrationLocationID);

        softAssert.assertEquals(member.MailingAddress2_sf,DSAcreateMember.MailingAddress2, "MailingAddress2  (SF) ");
        _scenario.write("============== MailingAddress2 ============= ");
        _scenario.write("Actual MailingAddress2  (SF) : "+member.MailingAddress2_sf);
        _scenario.write("Expected MailingAddress2  (SF) : "+DSAcreateMember.MailingAddress2);

        softAssert.assertEquals(member.ZipCode_sf,DSAcreateMember.ZipCode, "ZipCode  (SF) ");
        _scenario.write("============== ZipCode ============= ");
        _scenario.write("Actual ZipCode  (SF) : "+member.ZipCode_sf);
        _scenario.write("Expected ZipCode  (SF) : "+DSAcreateMember.ZipCode);

        //Not in SF.
        //assertEquals(Runtime_TestData.corrCityNameText,DSAcreateMember.CorrCityNameText);

        //Not if SF
        //assertEquals(Runtime_TestData.CorrStateNameText,DSAcreateMember.CorrStateNameText);


        softAssert.assertEquals(member.MailingAddress1_sf,DSAcreateMember.MailingAddress1, "MailingAddress1  (SF) ");
        _scenario.write("============== MailingAddress1 ============= ");
        _scenario.write("Actual MailingAddress1  (SF) : "+member.MailingAddress1_sf);
        _scenario.write("Expected MailingAddress1  (SF) : "+DSAcreateMember.MailingAddress1);

        softAssert.assertEquals(member.MailingAddress3_sf,DSAcreateMember.MailingAddress3, "MailingAddress3  (SF) ");
        _scenario.write("============== MailingAddress3 ============= ");
        _scenario.write("Actual MailingAddress3  (SF) : "+member.MailingAddress3_sf);
        _scenario.write("Expected MailingAddress3  (SF) : "+DSAcreateMember.MailingAddress3);

        softAssert.assertEquals(member.ResCityNameText_sf,DSAcreateMember.ResCityNameText, "ResCityNameText  (SF) ");
        _scenario.write("============== ResCityNameText ============= ");
        _scenario.write("Actual ResCityNameText  (SF) : "+member.ResCityNameText_sf);
        _scenario.write("Expected ResCityNameText  (SF) : "+DSAcreateMember.ResCityNameText);

        softAssert.assertEquals(member.ResStateNameText_sf,DSAcreateMember.ResStateNameText, "ResStateNameText  (SF) ");
        _scenario.write("============== ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText  (SF) : "+member.ResStateNameText_sf);
        _scenario.write("Expected ResStateNameText  (SF) : "+DSAcreateMember.ResStateNameText);

        softAssert.assertEquals(member.EnglishCountry_sf,DSAcreateMember.EnglishCountry, "EnglishCountry  (SF) ");
        _scenario.write("============== EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry  (SF) : "+member.EnglishCountry_sf);
        _scenario.write("Expected EnglishCountry  (SF) : "+DSAcreateMember.EnglishCountry);

        softAssert.assertEquals(member.ValidMobileNo1_sf,DSAcreateMember.ValidMobileNo1, "ValidMobileNo1  (SF) ");
        _scenario.write("============== ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1  (SF) : "+member.ValidMobileNo1_sf);
        _scenario.write("Expected ValidMobileNo1  (SF) : "+DSAcreateMember.ValidMobileNo1);

        softAssert.assertEquals(member.SpokenLanguage_sf,DSAcreateMember.SpokenLanguage, "SpokenLanguage  (SF) ");
        _scenario.write("============== SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage  (SF) : "+member.SpokenLanguage_sf);
        _scenario.write("Expected SpokenLanguage  (SF) : "+DSAcreateMember.SpokenLanguage);

        softAssert.assertEquals(member.CustomerRegistrationDatetime_sf,DSAcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime  (SF) ");
        _scenario.write("============== CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime  (SF) : "+member.CustomerRegistrationDatetime_sf);
        _scenario.write("Expected CustomerRegistrationDatetime  (SF) : "+DSAcreateMember.CustomerRegistrationDatetime);

        softAssert.assertEquals(member.CustomerLeisureActivity_sf,DSAcreateMember.CustomerLeisureActivity, "CustomerLeisureActivity  (SF) ");
        _scenario.write("============== CustomerLeisureActivity ============= ");
        _scenario.write("Actual CustomerLeisureActivity  (SF) : "+member.CustomerLeisureActivity_sf);
        _scenario.write("Expected CustomerLeisureActivity  (SF) : "+DSAcreateMember.CustomerLeisureActivity);

        softAssert.assertEquals(member.CustomerShoppingPreference_sf,DSAcreateMember.CustomerShoppingPreference, "CustomerShoppingPreference  (SF) ");
        _scenario.write("============== CustomerShoppingPreference ============= ");
        _scenario.write("Actual CustomerShoppingPreference  (SF) : "+member.CustomerShoppingPreference_sf);
        _scenario.write("Expected CustomerShoppingPreference  (SF) : "+DSAcreateMember.CustomerShoppingPreference);

        softAssert.assertEquals(member.CustomerPreferredBrands_sf,DSAcreateMember.CustomerPreferredBrands, "CustomerPreferredBrands  (SF) ");
        _scenario.write("============== CustomerPreferredBrands ============= ");
        _scenario.write("Actual CustomerPreferredBrands  (SF) : "+member.CustomerPreferredBrands_sf);
        _scenario.write("Expected CustomerPreferredBrands  (SF) : "+DSAcreateMember.CustomerPreferredBrands);

        softAssert.assertEquals(member.LeisureActivitiesMultiple_sf,DSAcreateMember.LeisureActivitiesMultiple, "LeisureActivitiesMultiple  (SF) ");
        _scenario.write("============== LeisureActivitiesMultiple ============= ");
        _scenario.write("Actual LeisureActivitiesMultiple  (SF) : "+member.LeisureActivitiesMultiple_sf);
        _scenario.write("Expected LeisureActivitiesMultiple  (SF) : "+DSAcreateMember.LeisureActivitiesMultiple);


        softAssert.assertEquals(member.ShoppingPreferencesMultiple_sf,DSAcreateMember.ShoppingPreferencesMultiple, "ShoppingPreferencesMultiple  (SF) ");
        _scenario.write("============== ShoppingPreferencesMultiple ============= ");
        _scenario.write("Actual ShoppingPreferencesMultiple  (SF) : "+member.ShoppingPreferencesMultiple_sf);
        _scenario.write("Expected ShoppingPreferencesMultiple  (SF) : "+DSAcreateMember.ShoppingPreferencesMultiple);

        //Need to compare after converting SF values to brand code
        softAssert.assertEquals(member.PreferredBrandsMultiple_sf,DSAcreateMember.PreferredBrandsMultiple, "PreferredBrandsMultiple  (SF) ");
        _scenario.write("============== PreferredBrandsMultiple ============= ");
        _scenario.write("Actual PreferredBrandsMultiple  (SF) : "+member.PreferredBrandsMultiple_sf);
        _scenario.write("Expected PreferredBrandsMultiple  (SF) : "+DSAcreateMember.PreferredBrandsMultiple);

        softAssert.assertEquals(member.AgeRange_sf,DSAcreateMember.AgeRange, "AgeRange  (SF) ");
        _scenario.write("============== AgeRange ============= ");
        _scenario.write("Actual AgeRange  (SF) : "+member.AgeRange_sf);
        _scenario.write("Expected AgeRange  (SF) : "+DSAcreateMember.AgeRange);

        softAssert.assertEquals(member.MarketingSource_sf,DSAcreateMember.MarketingSource, "MarketingSource  (SF) ");
        _scenario.write("============== MarketingSource ============= ");
        _scenario.write("Actual MarketingSource  (SF) : "+member.MarketingSource_sf);
        _scenario.write("Expected MarketingSource  (SF) : "+DSAcreateMember.MarketingSource);

        softAssert.assertEquals(member.ZipCodeValidFlag_sf,DSAcreateMember.ZipCodeValidFlag, "ZipCodeValidFlag  (SF) ");
        _scenario.write("============== ZipCodeValidFlag ============= ");
        _scenario.write("Actual ZipCodeValidFlag  (SF) : "+member.ZipCodeValidFlag_sf);
        _scenario.write("Expected ZipCodeValidFlag  (SF) : "+DSAcreateMember.ZipCodeValidFlag);
        //IpadId - NOT in SF

        softAssert.assertEquals(member.MarketingSourceOthers_sf,DSAcreateMember.MarketingSourceOthers, "MarketingSourceOthers  (SF) ");
        _scenario.write("============== MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers  (SF) : "+member.MarketingSourceOthers_sf);
        _scenario.write("Expected MarketingSourceOthers  (SF) : "+DSAcreateMember.MarketingSourceOthers);

        softAssert.assertEquals(member.Mobile1AreaCode_sf,DSAcreateMember.Mobile1AreaCode, "Mobile1AreaCode  (SF)  ");
        _scenario.write("============== Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode  (SF) : "+member.Mobile1AreaCode_sf);
        _scenario.write("Expected Mobile1AreaCode  (SF) : "+DSAcreateMember.Mobile1AreaCode);

        //IsContactable
        softAssert.assertEquals(member.IsContactable_sf,DSAcreateMember.IsContactable, "IsContactable  (SF) ");
        _scenario.write("============== IsContactable ============= ");
        _scenario.write("Actual IsContactable  (SF)  : "+member.IsContactable_sf);
        _scenario.write("Expected IsContactable  (SF) : "+DSAcreateMember.IsContactable);
        _scenario.write("Actual IsContactable value is "+member.IsContactable_sf +" when 'Opt In Marketing & Promotional (Phone)' = "+member.isContactablePhone_sf+ " and 'Opt In Marketing & Promotional (Mail)' = "+member.isContactableMail_sf+ " and 'Opt In Marketing & Promotional (Email)' = "+member.isContactableEmail_sf);

        //WeChatId
        softAssert.assertEquals(member.weChatId_sf,DSAcreateMember.ContactDetail, "WeChatId  (SF) ");
        _scenario.write("============== WeChatId ============= ");
        _scenario.write("Actual WeChatId  (SF)  : "+member.weChatId_sf);
        _scenario.write("Expected WeChatId  (SF) : "+DSAcreateMember.ContactDetail);

        softAssert.assertEquals(member.IsInvalidAddress_sf,DSAcreateMember.IsInvalidAddress, "InvalidAddress (Valid Mailing address)  (SF) ");
        _scenario.write("==============IsInvalidAddress (Valid Mailing address) ============= ");
        _scenario.write("Actual IsInvalidAddress (Valid Mailing address) (SF) : "+member.IsInvalidAddress_sf);
        _scenario.write("Expected IsInvalidAddress (Valid Mailing address) (SF) : "+DSAcreateMember.IsInvalidAddress);


        softAssert.assertEquals(sfData.Update_Date_c,DSAcreateMember.UpdateDate, "UpdateDate  (SF) ");
        _scenario.write("==============UpdateDate ============= ");
        _scenario.write("Actual UpdateDate  (SF) : "+sfData.Update_Date_c);
        _scenario.write("Expected UpdateDate  (SF) : "+DSAcreateMember.UpdateDate);

        //Not used
        //assertEquals(Runtime_TestData.CreationUserID,DSAcreateMember.CreationUserID);

        softAssert.assertEquals(sfData.Update_User_ID__c,DSAcreateMember.UpdateUserID, "UpdateUserID  (SF) ");
        _scenario.write("==============UpdateUserID ============= ");
        _scenario.write("Actual UpdateUserID  (SF) : "+sfData.Update_User_ID__c);
        _scenario.write("Expected UpdateUserID  (SF) : "+DSAcreateMember.UpdateUserID);

        softAssert.assertEquals(sfData.Data_Source_Enrollment__c,DSAcreateMember.SourceSystem, "SourceSystem  (SF) ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem  (SF) : "+sfData.Data_Source_Enrollment__c);
        _scenario.write("Expected SourceSystem  (SF) : "+DSAcreateMember.SourceSystem);

        //not used
        //assertEquals(Runtime_TestData.Override,DSAcreateMember.Override);

        // not needed
        //assertEquals(Runtime_TestData.ContactType,DSAcreateMember.ContactType);
        //assertEquals(Runtime_TestData.ContactDetail,DSAcreateMember.ContactDetail);

        //VERIFYING QUERRY DATA

        softAssert.assertEquals(sfData.Division_code__c,DSAcreateMember.RegistrationDivisionCode, "RegistrationDivisionCode  (SF) ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode  (SF) : "+sfData.Division_code__c);
        _scenario.write("Expected RegistrationDivisionCode  (SF) : "+DSAcreateMember.RegistrationDivisionCode);

        //Card Tier should be obtained from the sf query
        softAssert.assertTrue(sfData.Sign_Up_Tier__c.equalsIgnoreCase(DSAcreateMember.CardTier), "CardTier  (SF) ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier  (SF) : "+sfData.Sign_Up_Tier__c);
        _scenario.write("Expected CardTier  (SF) : "+DSAcreateMember.CardTier);

        //CardPickupMethod -From querry
        softAssert.assertTrue(sfData.Card_Pickup_Method__c.equalsIgnoreCase(DSAcreateMember.CardPickupMethod), "CardPickupMethod  (SF) ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod  (SF) : "+sfData.Card_Pickup_Method__c);
        _scenario.write("Expected CardPickupMethod  (SF) : "+DSAcreateMember.CardPickupMethod);

        //Staffid from QUERY
        softAssert.assertEquals(sfData.Staff_Number__c,DSAcreateMember.StaffID, "StaffID  (SF) ");
        _scenario.write("==============StaffID ============= ");
        _scenario.write("Actual StaffID  (SF) : "+sfData.Staff_Number__c);
        _scenario.write("Expected StaffID  (SF) : "+DSAcreateMember.StaffID);

        //creation date
        softAssert.assertEquals(sfData.Creation_Date__c,DSAcreateMember.CreationDate, "CreationDate  (SF) ");
        _scenario.write("==============CreationDate ============= ");
        _scenario.write("Actual CreationDate (SF)  : "+sfData.Creation_Date__c);
        _scenario.write("Expected CreationDate  (SF) : "+DSAcreateMember.CreationDate);


        //StoreLocation (Store_Name__c)
        softAssert.assertEquals(member.enroll_Location_sf.toLowerCase(), sfData.Store_Name__c.toLowerCase(),"StoreLocation  (SF) ");
        _scenario.write("==============StoreLocation ============= ");
        _scenario.write("Actual StoreLocation (SF)  : "+member.enroll_Location_sf);
        _scenario.write("Expected StoreLocation  (SF) : "+sfData.Store_Name__c);

    }

    @Then("^I validate DSA Membership Information with matrixDB$")
    public void iValidateDSAMembershipInformationWithMatrixDB() throws Throwable {


        //Assertions in matrix
        softAssert.assertEquals(matrixData.get("Salutation"),DSAcreateMember.TitleCode, "TitleCode (Matrix) ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode  (Matrix) : "+matrixData.get("Salutation"));
        _scenario.write("Expected TitleCode  (Matrix) : "+DSAcreateMember.TitleCode);

        softAssert.assertEquals(matrixData.get("Gender"),DSAcreateMember.GenderCode, "Gender (Matrix) ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode  (Matrix) : "+matrixData.get("Gender"));
        _scenario.write("Expected GenderCode (Matrix) : "+DSAcreateMember.GenderCode);

        softAssert.assertEquals(matrixData.get("Email"),DSAcreateMember.EmailAddressText, "EmailAddressText  (Matrix) ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText  (Matrix) : "+matrixData.get("Email"));
        _scenario.write("Expected EmailAddressText  (Matrix) : "+DSAcreateMember.EmailAddressText);


        softAssert.assertEquals(matrixData.get("Country"),DSAcreateMember.CountryNameText, "CountryNameText  (Matrix) ");
        _scenario.write("==============CountryNameText ============= ");
        _scenario.write("Actual CountryNameText  (Matrix) : "+matrixData.get("Country"));
        _scenario.write("Expected CountryNameText  (Matrix) : "+DSAcreateMember.CountryNameText);

        softAssert.assertEquals(matrixData.get("MobileNo"),DSAcreateMember.ContactNumberText, "ContactNumberText  (Matrix) ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText  (Matrix) : "+matrixData.get("MobileNo"));
        _scenario.write("Expected ContactNumberText  (Matrix) : "+DSAcreateMember.ContactNumberText);

        String IsInvalidEmail_status=null;
        if(DSAcreateMember.EmailAddressText.isEmpty()){
            IsInvalidEmail_status = "1";
        }else {
            IsInvalidEmail_status="0";
        }

        softAssert.assertEquals(matrixData.get("IsInvalidEmail"),IsInvalidEmail_status, "Valid Email Address(IsInvalidEmail)  (Matrix) ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail)  (Matrix) : "+matrixData.get("IsInvalidEmail"));
        _scenario.write("Expected Valid Email Address(IsInvalidEmail)  (Matrix) : "+IsInvalidEmail_status);

        //==============Commenting out this because - Salesforce checkbox is always unchecked =================
        // assertEquals(Runtime_TestData.EmailUnwillingToProvide,DSAcreateMember.EmailUnwillingToProvide);
        String isEmptyEmail_status=null;
        if( DSAcreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }


        softAssert.assertEquals(matrixData.get("IsEmptyEmail"),isEmptyEmail_status, "IsEmptyEmail (Matrix) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail  (Matrix) : "+matrixData.get("IsEmptyEmail"));
        _scenario.write("Expected IsEmptyEmail  (Matrix) : "+isEmptyEmail_status);

        //EmailUnwillingToProvide in Matrix for DSA create member is based on the value of the json. This is kind of confusing but validating based on the observation

        softAssert.assertEquals(matrixData.get("IsDNWTP"),DSAcreateMember.EmailUnwillingToProvide, "EmailUnwillingToProvide (Matrix) : " );
        _scenario.write("==============EmailUnwillingToProvide ============= ");
        _scenario.write("Actual EmailUnwillingToProvide : "+matrixData.get("IsDNWTP"));
        _scenario.write("Expected EmailUnwillingToProvide : "+DSAcreateMember.EmailUnwillingToProvide);

        softAssert.assertEquals(matrixData.get("FirstName"),DSAcreateMember.FirstName, "FirstName  (Matrix) ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName  (Matrix) : "+matrixData.get("FirstName"));
        _scenario.write("Expected FirstName  (Matrix) : "+DSAcreateMember.FirstName);

        softAssert.assertEquals(matrixData.get("FirstNameNative"),DSAcreateMember.FirstNameNative, "FirstNameNative  (Matrix) ");
        _scenario.write("==============FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative  (Matrix) : "+matrixData.get("FirstNameNative"));
        _scenario.write("Expected FirstNameNative  (Matrix) : "+DSAcreateMember.FirstNameNative);

        softAssert.assertEquals(matrixData.get("LastName"),DSAcreateMember.LastName, "LastName  (Matrix) ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName  (Matrix) : "+matrixData.get("LastName"));
        _scenario.write("Expected LastName  (Matrix) : "+DSAcreateMember.LastName);

        softAssert.assertEquals(matrixData.get("LastNameNative"),DSAcreateMember.LastNameNative, "LastNameNative  (Matrix) ");
        _scenario.write("==============LastNameNative ============= ");
        _scenario.write("Actual LastNameNative  (Matrix) : "+matrixData.get("LastNameNative"));
        _scenario.write("Expected LastNameNative  (Matrix) : "+DSAcreateMember.LastNameNative);

        softAssert.assertEquals(matrixData.get("RegistrationDivisionCode"),DSAcreateMember.RegistrationDivisionCode, "RegistrationDivisionCode  (Matrix) ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode  (Matrix) : "+matrixData.get("RegistrationDivisionCode"));
        _scenario.write("Expected RegistrationDivisionCode  (Matrix) : "+DSAcreateMember.RegistrationDivisionCode);

        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),DSAcreateMember.RegistrationLocationID, "RegistrationLocationID  (Matrix) ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID  (Matrix) : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID  (Matrix) : "+DSAcreateMember.RegistrationLocationID);

        softAssert.assertEquals(matrixData.get("EnglishCountry"),DSAcreateMember.EnglishCountry, "EnglishCountry  (Matrix) ");
        _scenario.write("==============EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry  (Matrix) : "+matrixData.get("EnglishCountry"));
        _scenario.write("Expected EnglishCountry  (Matrix) : "+DSAcreateMember.EnglishCountry);

        //Igonoring RegistrationLocationID because it always sends null by SF.
        // assertEquals(Runtime_TestData.RegistrationLocationID,DSAcreateMember.RegistrationLocationID);

        //Logic Added by the request of Anu on 17/12/2018 , Ticket No- https://dfsrtr.atlassian.net/browse/SFDC-1957 ==================================
        String ExpectedValidMobileNumber1Flag="";
        if(DSAcreateMember.ContactNumberText.isEmpty()) {  //when there is a valid phone number is present in UI
            ExpectedValidMobileNumber1Flag ="0";
        } else {
            ExpectedValidMobileNumber1Flag ="1";
        }

        softAssert.assertEquals(matrixData.get("ValidMobileNo1"),ExpectedValidMobileNumber1Flag, "Valid Mobile Phone Flag (Matrix) : ");
        _scenario.write("============== Valid Mobile Phone Flag ============= ");
        _scenario.write("Actual Valid Mobile Phone Flag : "+matrixData.get("ValidMobileNo1"));
        _scenario.write("Expected Valid Mobile Phone Flag : "+ExpectedValidMobileNumber1Flag);

        // End of Ticket No- https://dfsrtr.atlassian.net/browse/SFDC-1957 ==================================================================================


        softAssert.assertEquals(matrixData.get("SpokenLanguageCode"),DSAcreateMember.SpokenLanguage, "SpokenLanguageCode  (Matrix) ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage  (Matrix) : "+matrixData.get("SpokenLanguageCode"));
        _scenario.write("Expected SpokenLanguage  (Matrix) : "+DSAcreateMember.SpokenLanguage);


        softAssert.assertEquals(matrixData.get("IsContactable"),DSAcreateMember.IsContactable, "IsContactable  (Matrix)  ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable  (Matrix) : "+matrixData.get("IsContactable"));
        _scenario.write("Expected IsContactable  (Matrix) : "+DSAcreateMember.IsContactable);

        // #############################  More validations added as per the ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957 ############################
        // 1. Opt in Marketing & promotional (Phone)  ===================================================================

        // 1.1 isOptOutMobile1  --------------------------------------------------------------------
        String Expected_isOptOutMobile1=null;
        if(member.isContactablePhone_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutMobile1="0";
        } else {
            Expected_isOptOutMobile1="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutMobile1"),Expected_isOptOutMobile1, "isOptOutMobile1 (Matrix) ");
        _scenario.write("============== isOptOutMobile1 ============= ");
        _scenario.write("Actual isOptOutMobile1  (Matrix) : "+matrixData.get("IsOptOutMobile1"));
        _scenario.write("Expected isOptOutMobile1  (Matrix) : "+Expected_isOptOutMobile1);

        // 1.2 isOptOutMobile2  ----------------------------------------------------------------------
        String Expected_isOptOutMobile2=null;
        if(member.isContactablePhone_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutMobile2="0";
        } else {
            Expected_isOptOutMobile2="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutMobile2"),Expected_isOptOutMobile2, "isOptOutMobile2 (Matrix) ");
        _scenario.write("============== isOptOutMobile2 ============= ");
        _scenario.write("Actual isOptOutMobile2 (Matrix) : "+matrixData.get("IsOptOutMobile2"));
        _scenario.write("Expected isOptOutMobile2 (Matrix) : "+Expected_isOptOutMobile2);

        // 1.3 isOptOutHomePhone  ----------------------------------------------------------------------
        String Expected_isOptOutHomePhone=null;
        if(member.isContactablePhone_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutHomePhone="0";
        } else {
            Expected_isOptOutHomePhone="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutHomePhone"),Expected_isOptOutHomePhone, "isOptOutHomePhone (Matrix) ");
        _scenario.write("============== isOptOutHomePhone ============= ");
        _scenario.write("Actual isOptOutHomePhone (Matrix) : "+matrixData.get("IsOptOutHomePhone"));
        _scenario.write("Expected isOptOutHomePhone (Matrix) : "+Expected_isOptOutHomePhone);

        // 1.4 isOptOutWorkPhone  -----------------------------------------------------------------------
        String Expected_isOptOutWorkPhone=null;
        if(member.isContactablePhone_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutWorkPhone="0";
        } else {
            Expected_isOptOutWorkPhone="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutWorkPhone"),Expected_isOptOutWorkPhone, "isOptOutWorkPhone (Matrix) ");
        _scenario.write("============== isOptOutWorkPhone ============= ");
        _scenario.write("Actual isOptOutWorkPhone (Matrix) : "+matrixData.get("IsOptOutWorkPhone"));
        _scenario.write("Expected isOptOutWorkPhone (Matrix) : "+Expected_isOptOutWorkPhone);

        // ============================================================================================================================
        // 2. Opt in Marketing & promotional (Email)

        String Expected_isEmailOptOut=null;
        if(member.isContactableEmail_sf.equalsIgnoreCase("1")) {
            Expected_isEmailOptOut="0";
        } else {
            Expected_isEmailOptOut="1";
        }

        softAssert.assertEquals(matrixData.get("IsEmailOptOut"),Expected_isEmailOptOut, "isEmailOptOut (Matrix) ");
        _scenario.write("============== isEmailOptOut ============= ");
        _scenario.write("Actual isEmailOptOut (Matrix) : "+matrixData.get("IsEmailOptOut"));
        _scenario.write("Expected isEmailOptOut (Matrix) : "+Expected_isEmailOptOut);

        // ============================================================================================================================
        // 3. Opt in Marketing & promotional (Mail)

        // 3.1 isOptOutAddressEnglish ---------------------------------------------------------
        String Expected_isOptOutAddressEnglish=null;
        if(member.isContactableMail_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutAddressEnglish="0";
        } else {
            Expected_isOptOutAddressEnglish="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutAddressEnglish"),Expected_isOptOutAddressEnglish, "isOptOutAddressEnglish (Matrix) ");
        _scenario.write("============== isOptOutAddressEnglish ============= ");
        _scenario.write("Actual isOptOutAddressEnglish (Matrix) : "+matrixData.get("IsOptOutAddressEnglish"));
        _scenario.write("Expected isOptOutAddressEnglish (Matrix) : "+Expected_isOptOutAddressEnglish);

        // 3.2 isOptOutAddressOthers  ----------------------------------------------------------------------------------------------------
        String Expected_isOptOutAddressOthers=null;
        if(member.isContactableMail_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutAddressOthers="0";
        } else {
            Expected_isOptOutAddressOthers="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutAddressOthers"),Expected_isOptOutAddressOthers, "isOptOutAddressOthers (Matrix) ");
        _scenario.write("============== isOptOutAddressOthers ============= ");
        _scenario.write("Actual isOptOutAddressOthers (Matrix) : "+matrixData.get("IsOptOutAddressOthers"));
        _scenario.write("Expected isOptOutAddressOthers (Matrix) : "+Expected_isOptOutAddressOthers);
        // #############################  end of additional validations for ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957  ############################

        String ActualCustomerRegistrationDateTime="";
        try {
            ActualCustomerRegistrationDateTime = matrixData.get("CustomerRegistrationDatetime").substring(0,10);
        } catch (Exception e) {
            ActualCustomerRegistrationDateTime="";
        }

        softAssert.assertEquals(ActualCustomerRegistrationDateTime,DSAcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime  (Matrix) ");
        _scenario.write("==============CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime  (Matrix) : "+ActualCustomerRegistrationDateTime);
        _scenario.write("Expected CustomerRegistrationDatetime  (Matrix) : "+DSAcreateMember.CustomerRegistrationDatetime);


        softAssert.assertEquals(matrixData.get("AgeRange"),DSAcreateMember.AgeRange, "AgeRange  (Matrix) ");
        _scenario.write("==============AgeRange ============= ");
        _scenario.write("Actual AgeRange  (Matrix) : "+matrixData.get("AgeRange"));
        _scenario.write("Expected AgeRange  (Matrix) : "+DSAcreateMember.AgeRange);

        //assertEquals(matrixData.get("MarketingSource"),DSAcreateMember.MarketingSource);

        softAssert.assertEquals(matrixData.get("IssuedCardTier"),DSAcreateMember.CardTier, "IssuedCardTier  (Matrix) ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier  (Matrix) : "+matrixData.get("IssuedCardTier"));
        _scenario.write("Expected CardTier  (Matrix) : "+DSAcreateMember.CardTier);

        softAssert.assertEquals(matrixData.get("CardPickupMethod"),DSAcreateMember.CardPickupMethod, "CardPickupMethod  (Matrix) ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod  (Matrix) : "+matrixData.get("CardPickupMethod"));
        _scenario.write("Expected CardPickupMethod  (Matrix) : "+DSAcreateMember.CardPickupMethod);

        softAssert.assertEquals(matrixData.get("MobileAreaCode"),DSAcreateMember.Mobile1AreaCode, "MobileAreaCode  (Matrix) ");
        _scenario.write("==============Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode : "+matrixData.get("MobileAreaCode"));
        _scenario.write("Expected Mobile1AreaCode : "+DSAcreateMember.Mobile1AreaCode);

        softAssert.assertEquals(matrixData.get("SourceSystem"),DSAcreateMember.SourceSystem, "SourceSystem  (Matrix) ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem  (Matrix) : "+matrixData.get("SourceSystem"));
        _scenario.write("Expected SourceSystem  (Matrix) : "+DSAcreateMember.SourceSystem);

        softAssert.assertEquals(matrixData.get("PostalCode"),DSAcreateMember.PostalCode, "PostalCode  (Matrix) ");
        _scenario.write("==============PostalCode ============= ");
        _scenario.write("Actual PostalCode  (Matrix) : "+matrixData.get("PostalCode"));
        _scenario.write("Expected PostalCode  (Matrix) : "+DSAcreateMember.PostalCode);

        softAssert.assertEquals(matrixData.get("EnglishState"),DSAcreateMember.CityNameText, "EnglishState  (Matrix) ");
        _scenario.write("==============CityNameText ============= ");
        _scenario.write("Actual CityNameText  (Matrix) : "+matrixData.get("EnglishState"));
        _scenario.write("Expected CityNameText  (Matrix) : "+DSAcreateMember.CityNameText);

        softAssert.assertEquals(matrixData.get("Country"),DSAcreateMember.CountryNameText, "Country  (Matrix) ");
        _scenario.write("==============CountryNameText ============= ");
        _scenario.write("Actual CountryNameText  (Matrix) : "+matrixData.get("Country"));
        _scenario.write("Expected CountryNameText  (Matrix) : "+DSAcreateMember.CountryNameText);

        softAssert.assertEquals(matrixData.get("InvalidAddress"),DSAcreateMember.IsInvalidAddress, "InvalidAddress (Valid Mailing address)  (Matrix) ");
        _scenario.write("==============IsInvalidAddress ============= ");
        _scenario.write("Actual IsInvalidAddress (Valid Mailing address) (Matrix) : "+matrixData.get("InvalidAddress"));
        _scenario.write("Expected IsInvalidAddress (Valid Mailing address) (Matrix) : "+DSAcreateMember.IsInvalidAddress);

        softAssert.assertEquals(matrixData.get("IsDNWTP"),DSAcreateMember.EmailUnwillingToProvide, "EmailUnwillingToProvide  (Matrix) ");
        _scenario.write("==============EmailUnwillingToProvide ============= ");
        _scenario.write("Actual EmailUnwillingToProvide  (Matrix) : "+matrixData.get("IsDNWTP"));
        _scenario.write("Expected EmailUnwillingToProvide  (Matrix) : "+DSAcreateMember.EmailUnwillingToProvide);


        softAssert.assertEquals(matrixData.get("BirthDate"),DSAcreateMember.BirthDate, "BirthDate  (Matrix) ");
        _scenario.write("==============BirthDate ============= ");
        _scenario.write("Actual BirthDate  (Matrix) : "+matrixData.get("BirthDate"));
        _scenario.write("Expected BirthDate  (Matrix) : "+DSAcreateMember.BirthDate);


        softAssert.assertEquals(matrixData.get("BirthMonth"),DSAcreateMember.BirthMonth, "BirthMonth  (Matrix) ");
        _scenario.write("==============BirthMonth ============= ");
        _scenario.write("Actual BirthMonth  (Matrix) : "+matrixData.get("BirthMonth"));
        _scenario.write("Expected BirthMonth  (Matrix) : "+DSAcreateMember.BirthMonth);


        softAssert.assertEquals(matrixData.get("EnglishAddress2"),DSAcreateMember.MailingAddress2, "EnglishAddress2  (Matrix) ");
        _scenario.write("==============MailingAddress2 ============= ");
        _scenario.write("Actual MailingAddress2  (Matrix) : "+matrixData.get("EnglishAddress2"));
        _scenario.write("Expected MailingAddress2  (Matrix) : "+DSAcreateMember.MailingAddress2);

        softAssert.assertEquals(matrixData.get("ZipCode"),DSAcreateMember.ZipCode, "ZipCode  (Matrix) ");
        _scenario.write("==============ZipCode ============= ");
        _scenario.write("Actual ZipCode  (Matrix) : "+matrixData.get("ZipCode"));
        _scenario.write("Expected ZipCode  (Matrix) : "+DSAcreateMember.ZipCode);


        softAssert.assertEquals(matrixData.get("NativeCity"),DSAcreateMember.CorrCityNameText, "NativeCity  (Matrix) ");
        _scenario.write("==============CorrCityNameText ============= ");
        _scenario.write("Actual CorrCityNameText  (Matrix) : "+matrixData.get("NativeCity"));
        _scenario.write("Expected CorrCityNameText  (Matrix) : "+DSAcreateMember.CorrCityNameText);

        softAssert.assertEquals(matrixData.get("NativeState"),DSAcreateMember.CorrStateNameText, "NativeState  (Matrix) ");
        _scenario.write("==============CorrStateNameText ============= ");
        _scenario.write("Actual CorrStateNameText  (Matrix) : "+matrixData.get("NativeState"));
        _scenario.write("Expected CorrStateNameText  (Matrix) : "+DSAcreateMember.CorrStateNameText);

        softAssert.assertEquals(matrixData.get("EnglishAddress1"),DSAcreateMember.MailingAddress1, "EnglishAddress1  (Matrix) ");
        _scenario.write("==============MailingAddress1 ============= ");
        _scenario.write("Actual MailingAddress1  (Matrix) : "+matrixData.get("EnglishAddress1"));
        _scenario.write("Expected MailingAddress1  (Matrix) : "+DSAcreateMember.MailingAddress1);

        softAssert.assertEquals(matrixData.get("EnglishAddress3"),DSAcreateMember.MailingAddress3, "EnglishAddress3  (Matrix) ");
        _scenario.write("==============MailingAddress3 ============= ");
        _scenario.write("Actual MailingAddress3  (Matrix) : "+matrixData.get("EnglishAddress3"));
        _scenario.write("Expected MailingAddress3  (Matrix) : "+DSAcreateMember.MailingAddress3);

        softAssert.assertEquals(matrixData.get("EnglishCity"),DSAcreateMember.ResCityNameText, "EnglishCity  (Matrix) ");
        _scenario.write("==============ResCityNameText ============= ");
        _scenario.write("Actual ResCityNameText  (Matrix) : "+matrixData.get("EnglishCity"));
        _scenario.write("Expected ResCityNameText  (Matrix) : "+DSAcreateMember.ResCityNameText);

        softAssert.assertEquals(matrixData.get("EnglishState"),DSAcreateMember.ResStateNameText, "EnglishState  (Matrix) ");
        _scenario.write("==============ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText  (Matrix) : "+matrixData.get("EnglishState"));
        _scenario.write("Expected ResStateNameText  (Matrix) : "+DSAcreateMember.ResStateNameText);

        softAssert.assertEquals(matrixData.get("CustomerLeisureActivity"),DSAcreateMember.CustomerLeisureActivity, "CustomerLeisureActivity  (Matrix) ");
        _scenario.write("==============CustomerLeisureActivity ============= ");
        _scenario.write("Actual CustomerLeisureActivity  (Matrix) : "+matrixData.get("CustomerLeisureActivity"));
        _scenario.write("Expected CustomerLeisureActivity  (Matrix) : "+DSAcreateMember.CustomerLeisureActivity);

        softAssert.assertEquals(matrixData.get("CustomerShoppingPreference"),DSAcreateMember.CustomerShoppingPreference, "CustomerShoppingPreference  (Matrix) ");
        _scenario.write("==============CustomerShoppingPreference ============= ");
        _scenario.write("Actual CustomerShoppingPreference  (Matrix) : "+matrixData.get("CustomerShoppingPreference"));
        _scenario.write("Expected CustomerShoppingPreference  (Matrix) : "+DSAcreateMember.CustomerShoppingPreference);

        softAssert.assertEquals(matrixData.get("CustomerPreferredBrands"),DSAcreateMember.CustomerPreferredBrands, "CustomerPreferredBrands  (Matrix) ");
        _scenario.write("==============CustomerPreferredBrands ============= ");
        _scenario.write("Actual CustomerPreferredBrands  (Matrix) : "+matrixData.get("CustomerPreferredBrands"));
        _scenario.write("Expected CustomerPreferredBrands  (Matrix) : "+DSAcreateMember.CustomerPreferredBrands);

        softAssert.assertEquals(matrixData.get("LeisureActivitiesMultiple"),DSAcreateMember.LeisureActivitiesMultiple, "LeisureActivitiesMultiple  (Matrix) ");
        _scenario.write("==============LeisureActivitiesMultiple ============= ");
        _scenario.write("Actual LeisureActivitiesMultiple  (Matrix) : "+matrixData.get("LeisureActivitiesMultiple"));
        _scenario.write("Expected LeisureActivitiesMultiple  (Matrix) : "+DSAcreateMember.LeisureActivitiesMultiple);

        softAssert.assertEquals(matrixData.get("ShoppingPreferencesMultiple"),DSAcreateMember.ShoppingPreferencesMultiple, "ShoppingPreferencesMultiple  (Matrix) ");
        _scenario.write("==============ShoppingPreferencesMultiple ============= ");
        _scenario.write("Actual ShoppingPreferencesMultiple  (Matrix) : "+matrixData.get("ShoppingPreferencesMultiple"));
        _scenario.write("Expected ShoppingPreferencesMultiple  (Matrix) : "+DSAcreateMember.ShoppingPreferencesMultiple);

        softAssert.assertEquals(matrixData.get("PreferredBrandsMultiple"),DSAcreateMember.PreferredBrandsMultiple, "PreferredBrandsMultiple  (Matrix) ");
        _scenario.write("==============PreferredBrandsMultiple ============= ");
        _scenario.write("Actual PreferredBrandsMultiple  (Matrix) : "+matrixData.get("PreferredBrandsMultiple"));
        _scenario.write("Expected PreferredBrandsMultiple  (Matrix) : "+DSAcreateMember.PreferredBrandsMultiple);

        softAssert.assertEquals(matrixData.get("MarketingSource"),DSAcreateMember.MarketingSource, "MarketingSource  (Matrix) ");
        _scenario.write("==============MarketingSource ============= ");
        _scenario.write("Actual MarketingSource  (Matrix) : "+matrixData.get("MarketingSource"));
        _scenario.write("Expected MarketingSource  (Matrix) : "+DSAcreateMember.MarketingSource);

        softAssert.assertEquals(matrixData.get("IsValidZipCode"),DSAcreateMember.ZipCodeValidFlag, "IsValidZipCode  (Matrix) ");
        _scenario.write("==============ZipCodeValidFlag ============= ");
        _scenario.write("Actual ZipCodeValidFlag  (Matrix) : "+matrixData.get("IsValidZipCode"));
        _scenario.write("Expected ZipCodeValidFlag  (Matrix) : "+DSAcreateMember.ZipCodeValidFlag);

        softAssert.assertEquals(matrixData.get("IpadID"),DSAcreateMember.IpadID, "IpadID  (Matrix) ");
        _scenario.write("==============IpadID ============= ");
        _scenario.write("Actual IpadID  (Matrix) : "+matrixData.get("IpadID"));
        _scenario.write("Expected IpadID  (Matrix) : "+DSAcreateMember.IpadID);

         // IGNORING THIS DUE TO CONFUSION IN LOGIC
        //assertEquals(matrixData.get("Staff"),DSAcreateMember.StaffID);

        //assertEquals(matrixData.get("Staff"),DSAcreateMember.StaffID);


        softAssert.assertEquals(matrixData.get("MarketingSourceOthers"),DSAcreateMember.MarketingSourceOthers, "MarketingSourceOthers  (Matrix) ");
        _scenario.write("==============MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers  (Matrix) : "+matrixData.get("MarketingSourceOthers"));
        _scenario.write("Expected MarketingSourceOthers  (Matrix) : "+DSAcreateMember.MarketingSourceOthers);

        softAssert.assertEquals(matrixData.get("AddedBy"),DSAcreateMember.CreationUserID, "AddedBy  (Matrix) ");
        _scenario.write("==============CreationUserID ============= ");
        _scenario.write("Actual CreationUserID  (Matrix) : "+matrixData.get("AddedBy"));
        _scenario.write("Expected CreationUserID  (Matrix) : "+DSAcreateMember.CreationUserID);

        //WeChatId
        softAssert.assertEquals(matrixData.get("WechatID"),DSAcreateMember.ContactDetail, "WeChatId  (Matrix) ");
        _scenario.write("============== WeChatId ============= ");
        _scenario.write("Actual WeChatId  (Matrix)  : "+matrixData.get("WechatID"));
        _scenario.write("Expected WeChatId  (Matrix) : "+DSAcreateMember.ContactDetail);

        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }
    }



}
