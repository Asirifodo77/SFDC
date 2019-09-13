
package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
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
import java.util.List;

import static org.testng.Assert.*;

public class POScreateMember {
    public DSAcreateMember_pageObjects POScreateMember;
    public Login_pageobjects loginPage;
    public SearchMember_pageobjects searchMember_pageobjects;
    public MemberValidation_pageobjects member;
    public selectBoxControls selectBox;
    public textBoxControls textBox;
    public checkBoxControls checkBox;
    public String cardNumber;
    public getDataFromSF sfData;
    public HashMap<String, String> matrixData;
    public DSAcreateMember_pageObjects matrix;
    public SoftAssert softAssert;
    public String environment;
    public testDataCleanup dataCleanup;
    public TakeScreenshot screenshot;
    public getSFaccessToken accessToken;
    public String token;
    public validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;

    public String POSTrequestBody;
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;


    public POScreateMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        environment= System.getProperty("Environment");
        softAssert = new SoftAssert();

        POScreateMember = new DSAcreateMember_pageObjects(_scenario);
        searchMember_pageobjects= new SearchMember_pageobjects(driver,_scenario);
        loginPage = new Login_pageobjects(driver,_scenario);
        member = new MemberValidation_pageobjects(driver,_scenario);
        selectBox = new selectBoxControls(driver, _scenario);
        textBox = new textBoxControls(_scenario);
        checkBox = new checkBoxControls(_scenario);
        matrixData = new HashMap<>();
        matrix = new DSAcreateMember_pageObjects(_scenario);
        sfData = new getDataFromSF();
        dataCleanup = new testDataCleanup();
        screenshot = new TakeScreenshot(driver,_scenario);
        accessToken = new getSFaccessToken();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);
    }

    @Given("^I read POS request body from Json file$")
    public void iReadPOSRequestBodyFromJsonFile() throws Throwable {
        POSTrequestBody = POScreateMember.readRequestFromJson(POScreateMember.getPOSjsonPath(environment));

        //priting out the request on cucumber report
        _scenario.write("=============== The API Request body for POS create member is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("================================ End of API Request body ===================================");

    }

    @Given("^I cleanup data for existing POS member from SF and MatrixDB$")
    public void iCleanupDataForExistingPOSMemberFromSFAndMatrixDB() throws Throwable {
        //reading the data from Json request file
        POScreateMember.getElementValuesFromPOSJsonFile(environment);
        //cleaning up data for the given card number only (Not reading from csv file)
        dataCleanup.cleanUpOneMemberCardData(_scenario,POScreateMember.CardNo);
    }


    @When("^I send POST request to create new member through POS$")
    public void iSendPOSTRequestToCreateNewMemberThroughPOS() throws Throwable {
        POScreateMember.createMemberPOS(POSTrequestBody);
    }

    @Then("^I validate the memberID in salesforce and Matrix for POS Created member$")
    public void iValidateTheMemberIDInSalesforceAndMatrixForPOSCreatedMember() throws Throwable {
        //getting the access token
        token = accessToken.getSFaccessToken();

        //validate member if from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberID(token,POScreateMember.CardNo,environment);
    }


    @And("^I login to SF to verify POS member$")
    public void iLoginToSFToVerifyPOSMember() throws Throwable {
        loginPage.loginToSF();
    }

    @Then("^I search for the member using POS json Card Number$")
    public void iSearchForTheMemberUsingPOSJsonCardNumber(DataTable dataTable) throws Throwable {
        searchMember_pageobjects.searchMemberThroughCardNumber(dataTable,POScreateMember.CardNo);
    }

    @Then("^I read details from POS member profile in SF$")
    public void iReadDetailsFromPOSMemberProfileInSF() throws Throwable {
       // reading all required data from salesforce UI
        //
        System.out.println(Instant.now().toString() + "Workbench Page");
        Thread.sleep(1000);


        //Switch to frame
        member.switchToMainFrame();

        //Taking screenshot
        screenshot.takeScreenshot();

        //Title code =
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

        // RegistrationDivisionCode -- not in SF

        // RegistrationLocationID -- NOT IN SF

        // MailingAddress2
        member.getMailingAddress2();

        // ZipCode
        member.getZipCode();

        // CorrCityNameText" -- not in SF

        // CorrStateNameText -- NOT IN SF

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
        member.getZipCodeValidFlag();

        // MarketingSourceOthers
        member.getMarketingSourceOthers();

        // Mobile1AreaCode
        member.getMobile1AreaCode();

        // Navigate Inside Contact Preferences
        member.switchToContactPreferencesTab();

        // SpokenLanguage
        member.getSpokenLanguage();

        // IsContactable (by checking if all checkboxes are checked or not
        member.getIsContactable();

        // going inside Shopping preferences tab
        member.switchToShoppingPreferencesTab();

        //Taking screenshot
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

        // Store Location
        member.getEnrollmentLocation();

    }

    @Then("^I search for missing POS member values in database$")
    public void iSearchForMissingPOSMemberValuesInDatabase() throws Throwable {
        getSFaccessToken accessToken = new getSFaccessToken();
        String token = accessToken.getSFaccessToken();

        // reading the member card number from json file
        POScreateMember.getElementValuesFromPOSJsonFile(environment);
        cardNumber = POScreateMember.CardNo;

        //passing the card number and getting the required data from SF and assining them to Run time data
        sfData.querySFdataforPOS(token,cardNumber);

        //Getting the store Location Name from Salesforce
        sfData.querySFDataToGetStoreLocationName(token,POScreateMember.RegistrationDivisionCode,POScreateMember.RegistrationLocationID);
    }

    @Then("^I read POS member details from Matrix$")
    public void iReadPOSMemberDetailsFromMatrix() throws Throwable {
        matrixData= matrix.readMemberDataFromMatrix(environment,cardNumber);
    }

    @Then("^I validate POS Membership Information with SF$")
    public void iValidatePOSMembershipInformationWithSF() throws Throwable {

        //from UI
        softAssert.assertEquals(member.Salutation_sf,POScreateMember.TitleCode,"TitleCode  (SF) : ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode : "+member.Salutation_sf);
        _scenario.write("Expected TitleCode : "+POScreateMember.TitleCode);

        softAssert.assertEquals(member.Gender_sf,POScreateMember.GenderCode, "GenderCode  (SF) : ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode : "+member.Gender_sf);
        _scenario.write("Expected GenderCode : "+POScreateMember.GenderCode);

        softAssert.assertEquals(member.EmailAddressText_sf,POScreateMember.EmailAddressText, "EmailAddressText  (SF) : ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText : "+member.EmailAddressText_sf);
        _scenario.write("Expected EmailAddressText : "+POScreateMember.EmailAddressText);


        softAssert.assertEquals(member.ContactNumberText_sf,POScreateMember.ContactNumberText, "ContactNumberText  (SF) : ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText : "+member.ContactNumberText_sf);
        _scenario.write("Expected ContactNumberText : "+POScreateMember.ContactNumberText);

        softAssert.assertEquals(member.Mobile1AreaCode_sf,POScreateMember.Mobile1AreaCode, "Mobile1AreaCode  (SF) : " );
        _scenario.write("==============Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode : "+member.Mobile1AreaCode_sf);
        _scenario.write("Expected Mobile1AreaCode : "+POScreateMember.Mobile1AreaCode);


        softAssert.assertEquals(member.IsContactable_sf,POScreateMember.IsContactable, "IsContactable  (SF) : " );
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable : "+member.IsContactable_sf);
        _scenario.write("Expected IsContactable : "+POScreateMember.IsContactable);
        _scenario.write("Actual IsContactable value is "+member.IsContactable_sf +" when 'Opt In Marketing & Promotional (Phone)' = "+member.isContactablePhone_sf+ " and 'Opt In Marketing & Promotional (Mail)' = "+member.isContactableMail_sf+ " and 'Opt In Marketing & Promotional (Email)' = "+member.isContactableEmail_sf);


        softAssert.assertEquals(member.FirstNameNative_sf,POScreateMember.FirstNameNative, "FirstNameNative  (SF) : ");
        _scenario.write("==============FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative : "+member.FirstNameNative_sf);
        _scenario.write("Expected FirstNameNative : "+POScreateMember.FirstNameNative);

        softAssert.assertEquals(member.FirstName_sf,POScreateMember.FirstName, "FirstName  (SF) : ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName : "+member.FirstName_sf);
        _scenario.write("Expected FirstName : "+POScreateMember.FirstName);

        softAssert.assertEquals(member.LastName_sf,POScreateMember.LastName, "LastName  (SF) : ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName : "+member.LastName_sf);
        _scenario.write("Expected LastName : "+POScreateMember.LastName);


        softAssert.assertEquals(member.EnglishCountry_sf,POScreateMember.CountryNameText, "CountryNameText  (SF) : ");
        _scenario.write("==============CountryNameText ============= ");
        _scenario.write("Actual CountryNameText : "+member.EnglishCountry_sf);
        _scenario.write("Expected CountryNameText : "+POScreateMember.CountryNameText);


        softAssert.assertEquals(member.SpokenLanguage_sf,POScreateMember.SpokenLanguage, "SpokenLanguage  (SF) : ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage : "+member.SpokenLanguage_sf);
        _scenario.write("Expected SpokenLanguage : "+POScreateMember.SpokenLanguage);

        softAssert.assertEquals(member.AgeRange_sf,POScreateMember.AgeRange, "AgeRange  (SF) : ");
        _scenario.write("==============AgeRange ============= ");
        _scenario.write("Actual AgeRange : "+member.AgeRange_sf);
        _scenario.write("Expected AgeRange : "+POScreateMember.AgeRange);

        // CustomerRegistrationDatetime
        softAssert.assertEquals(member.CustomerRegistrationDatetime_sf,POScreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime(Enrolment Date) (SF) ");
        _scenario.write("==============CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime  (SF) : "+member.CustomerRegistrationDatetime_sf);
        _scenario.write("Expected CustomerRegistrationDatetime  (SF) : "+POScreateMember.CustomerRegistrationDatetime);

        //EmailUnwillingToProvide
        // Added if "EmailAddressText" is Not empty, then EmailUnwillingToProvide should be 0 (Unchecked)
        // MAKING THE CHANGE ACCORDING TO https://dfsrtr.atlassian.net/browse/SFDC-2224
        // EmailUnwillingToProvide IS ALWAYS EXPECTED TO BE "0" FOR POS CREATE MEMBER

//        String EmailUnwillingToProvideFlagExpected="";
//        if(member.EmailAddressText_sf.isEmpty()) {
//            EmailUnwillingToProvideFlagExpected="1";
//        } else {
//            EmailUnwillingToProvideFlagExpected="0";
//        }

        softAssert.assertEquals(member.EmailUnwillingToProvide_sf,"0", "EmailUnwillingToProvide  (SF) : " );
        _scenario.write("==============EmailUnwillingToProvide ============= ");
        _scenario.write("Actual EmailUnwillingToProvide : "+member.EmailUnwillingToProvide_sf);
        _scenario.write("Expected EmailUnwillingToProvide : "+"0");

        //from query
        softAssert.assertTrue(sfData.Card_Pickup_Method__c.equalsIgnoreCase(POScreateMember.CardPickupMethod), "CardPickupMethod  (SF) : ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod : "+sfData.Card_Pickup_Method__c);
        _scenario.write("Expected CardPickupMethod : "+POScreateMember.CardPickupMethod);

        softAssert.assertTrue(sfData.Sign_Up_Tier__c.equalsIgnoreCase(POScreateMember.CardTier), "CardTier  (SF) : ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier : "+sfData.Sign_Up_Tier__c);
        _scenario.write("Expected CardTier : "+POScreateMember.CardTier);


        softAssert.assertTrue(sfData.Division_code__c.equalsIgnoreCase(POScreateMember.RegistrationDivisionCode), "RegistrationDivisionCode  (SF) : ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode : "+sfData.Division_code__c);
        _scenario.write("Expected RegistrationDivisionCode : "+POScreateMember.RegistrationDivisionCode);


        softAssert.assertTrue(sfData.Data_Source_Enrollment__c.equalsIgnoreCase(POScreateMember.SourceSystem), "SourceSystem  (SF) : ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem : "+sfData.Data_Source_Enrollment__c);
        _scenario.write("Expected SourceSystem : "+POScreateMember.SourceSystem);

        // Fields that should be null or empty.


        String IsInvalidEmail_status=null;
        if(POScreateMember.EmailAddressText.isEmpty()){
            IsInvalidEmail_status = "1";
        }else {
            IsInvalidEmail_status="0";
        }

        softAssert.assertTrue(member.IsInvalidEmail_sf.equalsIgnoreCase(IsInvalidEmail_status), "Valid Email Address(IsInvalidEmail)  (SF) : ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail) : "+member.IsInvalidEmail_sf);
        _scenario.write("Expected Valid Email Address(IsInvalidEmail) : "+IsInvalidEmail_status);


        String isEmptyEmail_status=null;
        if(POScreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }
        softAssert.assertTrue(member.IsEmptyEmail_sf.equalsIgnoreCase(isEmptyEmail_status), "IsEmptyEmail (SF) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail : "+member.IsEmptyEmail_sf);
        _scenario.write("Expected IsEmptyEmail : "+ isEmptyEmail_status);



        //Fields that should be null #################################################################################
        //############################################################################################################

        softAssert.assertEquals(member.LastNameNative_sf,"", "LastNameNative  (SF) ");
        _scenario.write("==============LastNameNative ============= ");
        _scenario.write("Actual LastNameNative  (SF) : "+member.LastNameNative_sf);
        _scenario.write("Expected LastNameNative  (SF) : "+"");

        softAssert.assertEquals(member.BirthDate_sf,"", "BirthDate  (SF) ");
        _scenario.write("==============BirthDate ============= ");
        _scenario.write("Actual BirthDate  (SF) : "+member.BirthDate_sf);
        _scenario.write("Expected BirthDate  (SF) : "+"");

        softAssert.assertEquals(member.BirthMonth_sf,"", "BirthMonth  (SF) ");
        _scenario.write("==============BirthMonth ============= ");
        _scenario.write("Actual BirthMonth  (SF) : "+member.BirthMonth_sf);
        _scenario.write("Expected BirthMonth  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress2_sf,"", "MailingAddress2  (SF) ");
        _scenario.write("==============MailingAddress2 ============= ");
        _scenario.write("Actual MailingAddress2  (SF) : "+member.MailingAddress2_sf);
        _scenario.write("Expected MailingAddress2  (SF) : "+"");

        softAssert.assertEquals(member.ZipCode_sf,"", "ZipCode  (SF) ");
        _scenario.write("==============ZipCode ============= ");
        _scenario.write("Actual ZipCode  (SF) : "+member.ZipCode_sf);
        _scenario.write("Expected ZipCode  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress1_sf,"", "MailingAddress1  (SF) ");
        _scenario.write("==============MailingAddress1 ============= ");
        _scenario.write("Actual MailingAddress1  (SF) : "+member.MailingAddress1_sf);
        _scenario.write("Expected MailingAddress1  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress3_sf,"", "MailingAddress3  (SF) ");
        _scenario.write("==============MailingAddress3 ============= ");
        _scenario.write("Actual MailingAddress3  (SF) : "+member.MailingAddress3_sf);
        _scenario.write("Expected MailingAddress3  (SF) : "+"");

        softAssert.assertEquals(member.ResCityNameText_sf,"", "ResCityNameText  (SF) ");
        _scenario.write("==============ResCityNameText ============= ");
        _scenario.write("Actual ResCityNameText  (SF) : "+member.ResCityNameText_sf);
        _scenario.write("Expected ResCityNameText  (SF) : "+"");

        softAssert.assertEquals(member.CityName_sf,"", "CityNameText  (SF) ");
        _scenario.write("==============CityNameText ============= ");
        _scenario.write("Actual CityNameText  (SF) : "+member.CityName_sf);
        _scenario.write("Expected CityNameText  (SF) : "+"");

        softAssert.assertEquals(member.ResStateNameText_sf,"", "ResStateNameText  (SF) ");
        _scenario.write("==============ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText  (SF) : "+member.ResStateNameText_sf);
        _scenario.write("Expected ResStateNameText  (SF) : "+"");


        //Added this logic as requested and confirmed by Anu in the ticket - https://dfsrtr.atlassian.net/browse/SFDC-1961
        //the expcted ValidMobileNo1 is decided as follows
        // if ContactNumberText_sf is not null then validMobileNo_sf IS true
        //else if ContactNumberText_sf is NULL then validMobileNo_sf is false

        String ExpectedValidMobileNumber1Flag="";
        if(member.ContactNumberText_sf.isEmpty()) {  //when there is a valid phone number is present in UI
            ExpectedValidMobileNumber1Flag ="0";
        } else {
            ExpectedValidMobileNumber1Flag ="1";
        }

        softAssert.assertEquals(member.ValidMobileNo1_sf,ExpectedValidMobileNumber1Flag, "ValidMobileNo1  (SF) ");
        _scenario.write("==============ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1  (SF) : "+member.ValidMobileNo1_sf);
        _scenario.write("Expected ValidMobileNo1  (SF) : "+ ExpectedValidMobileNumber1Flag);

        softAssert.assertEquals(member.CustomerLeisureActivity_sf,"", "CustomerLeisureActivity  (SF) ");
        _scenario.write("==============CustomerLeisureActivity ============= ");
        _scenario.write("Actual CustomerLeisureActivity  (SF) : "+member.CustomerLeisureActivity_sf);
        _scenario.write("Expected CustomerLeisureActivity  (SF) : "+"");

        softAssert.assertEquals(member.CustomerShoppingPreference_sf,"", "CustomerShoppingPreference  (SF) ");
        _scenario.write("==============CustomerShoppingPreference ============= ");
        _scenario.write("Actual CustomerShoppingPreference  (SF) : "+member.CustomerShoppingPreference_sf);
        _scenario.write("Expected CustomerShoppingPreference  (SF) : "+"");

        softAssert.assertEquals(member.CustomerPreferredBrands_sf,"", "CustomerPreferredBrands  (SF) ");
        _scenario.write("==============CustomerPreferredBrands ============= ");
        _scenario.write("Actual CustomerPreferredBrands  (SF) : "+member.CustomerPreferredBrands_sf);
        _scenario.write("Expected CustomerPreferredBrands  (SF) : "+"");

        softAssert.assertEquals(member.LeisureActivitiesMultiple_sf,"", "LeisureActivitiesMultiple  (SF) ");
        _scenario.write("==============LeisureActivitiesMultiple ============= ");
        _scenario.write("Actual LeisureActivitiesMultiple  (SF) : "+member.LeisureActivitiesMultiple_sf);
        _scenario.write("Expected LeisureActivitiesMultiple  (SF) : "+"");

        softAssert.assertEquals(member.ShoppingPreferencesMultiple_sf,"", "ShoppingPreferencesMultiple  (SF) ");
        _scenario.write("==============ShoppingPreferencesMultiple ============= ");
        _scenario.write("Actual ShoppingPreferencesMultiple  (SF) : "+member.ShoppingPreferencesMultiple_sf);
        _scenario.write("Expected ShoppingPreferencesMultiple  (SF) : "+"");

        softAssert.assertEquals(member.PreferredBrandsMultiple_sf,"", "PreferredBrandsMultiple  (SF) ");
        _scenario.write("==============PreferredBrandsMultiple ============= ");
        _scenario.write("Actual PreferredBrandsMultiple  (SF) : "+member.PreferredBrandsMultiple_sf);
        _scenario.write("Expected PreferredBrandsMultiple  (SF) : "+"");

        softAssert.assertEquals(member.MarketingSource_sf,"", "MarketingSource  (SF) ");
        _scenario.write("==============MarketingSource ============= ");
        _scenario.write("Actual MarketingSource  (SF) : "+member.MarketingSource_sf);
        _scenario.write("Expected MarketingSource  (SF) : "+"");

        softAssert.assertEquals(member.ZipCodeValidFlag_sf,"0", "ZipCodeValidFlag  (SF) ");
        _scenario.write("==============ZipCodeValidFlag ============= ");
        _scenario.write("Actual ZipCodeValidFlag  (SF) : "+member.ZipCodeValidFlag_sf);
        _scenario.write("Expected ZipCodeValidFlag  (SF) : "+"0");

        softAssert.assertEquals(member.MarketingSourceOthers_sf,"", "MarketingSourceOthers  (SF) ");
        _scenario.write("==============MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers  (SF) : "+member.MarketingSourceOthers_sf);
        _scenario.write("Expected MarketingSourceOthers  (SF) : "+"");

        softAssert.assertEquals(member.PostalCode_sf,"", "PostalCode  (SF) ");
        _scenario.write("==============PostalCode ============= ");
        _scenario.write("Actual PostalCode  (SF) : "+member.PostalCode_sf);
        _scenario.write("Expected PostalCode  (SF) : "+"");


        //StoreLocation (Store_Name__c)
        softAssert.assertEquals(member.enroll_Location_sf.toLowerCase(), sfData.Store_Name__c.toLowerCase(),"StoreLocation  (SF) ");
        _scenario.write("==============StoreLocation ============= ");
        _scenario.write("Actual StoreLocation (SF)  : "+member.enroll_Location_sf);
        _scenario.write("Expected StoreLocation  (SF) : "+sfData.Store_Name__c);

        /*softAssert.assertEquals(matrixData.get("LastNameNative"), "NULL");

        assertEquals(matrixData.get("PostalCode"),"NULL");
        assertEquals(matrixData.get("EnglishState"),"NULL");
        assertEquals(matrixData.get("InvalidAddress"),"NULL");
        assertEquals(matrixData.get("IsDNWTP"),"NULL");
        assertEquals(matrixData.get("BirthDate"),"NULL");
        assertEquals(matrixData.get("BirthMonth"),"NULL");
        assertEquals(matrixData.get("EnglishAddress2"),"NULL");
        assertEquals(matrixData.get("ZipCode"),"NULL");
        assertEquals(matrixData.get("NativeCity"),"NULL");
        assertEquals(matrixData.get("NativeState"),"NULL");
        assertEquals(matrixData.get("EnglishAddress1"),"NULL");
        assertEquals(matrixData.get("EnglishAddress3"),"NULL");
        assertEquals(matrixData.get("EnglishCity"),"NULL");
        assertEquals(matrixData.get("EnglishState"),"NULL");
        assertEquals(matrixData.get("CustomerLeisureActivity"),"NULL");
        assertEquals(matrixData.get("CustomerShoppingPreference"),"NULL");
        assertEquals(matrixData.get("CustomerPreferredBrands"),"NULL");
        assertEquals(matrixData.get("LeisureActivitiesMultiple"),"NULL");
        assertEquals(matrixData.get("ShoppingPreferencesMultiple"),"NULL");
        assertEquals(matrixData.get("PreferredBrandsMultiple"),"NULL");
        assertEquals(matrixData.get("MarketingSource"),"NULL");
        assertEquals(matrixData.get("IsValidZipCode"),"NULL");
        assertEquals(matrixData.get("IpadID"),"NULL");


        // assertEquals(matrixData.get("Staff"),POScreateMember.CreationUserID);

        //assertEquals(matrixData.get("Staff"),POScreateMember.StaffID);

        assertEquals(matrixData.get("MarketingSourceOthers"),"NULL");
        assertEquals(matrixData.get("AddedBy"),POScreateMember.CreationUserID);
*/
    }


    @Then("^I validate POS Membership Information with matrixDB$")
    public void iValidatePOSMembershipInformationWithMatrixDB() throws Throwable {

        //from UI
        softAssert.assertEquals(matrixData.get("Salutation"),POScreateMember.TitleCode, "TitleCode  (Matrix) : ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode : "+matrixData.get("Salutation"));
        _scenario.write("Expected TitleCode : "+POScreateMember.TitleCode);


        softAssert.assertEquals(matrixData.get("Gender"),POScreateMember.GenderCode, "GenderCode  (Matrix) : ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode : "+matrixData.get("Gender"));
        _scenario.write("Expected GenderCode : "+POScreateMember.GenderCode);

        softAssert.assertEquals(matrixData.get("Email"),POScreateMember.EmailAddressText, "Email  (Matrix) : ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText : "+matrixData.get("Email"));
        _scenario.write("Expected EmailAddressText : "+POScreateMember.EmailAddressText);

        softAssert.assertEquals(matrixData.get("MobileNo"),POScreateMember.ContactNumberText, "MobileNo  (Matrix) : ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText : "+matrixData.get("MobileNo"));
        _scenario.write("Expected ContactNumberText : "+POScreateMember.ContactNumberText);

        softAssert.assertEquals(matrixData.get("MobileAreaCode"),POScreateMember.Mobile1AreaCode, "MobileAreaCode  (Matrix) : ");
        _scenario.write("==============Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode : "+matrixData.get("MobileAreaCode"));
        _scenario.write("Expected Mobile1AreaCode : "+POScreateMember.Mobile1AreaCode);

        softAssert.assertEquals(matrixData.get("IsContactable"),POScreateMember.IsContactable, "IsContactable  (Matrix) : ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable : "+matrixData.get("IsContactable"));
        _scenario.write("Expected IsContactable : "+POScreateMember.IsContactable);

        // #############################  More validations added as per the ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957  and related ticket https://dfsrtr.atlassian.net/browse/SFDC-1961############################
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

        softAssert.assertEquals(matrixData.get("FirstNameNative"),POScreateMember.FirstNameNative, "FirstNameNative  (Matrix) : ");
        _scenario.write("==============FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative : "+matrixData.get("FirstNameNative"));
        _scenario.write("Expected FirstNameNative : "+POScreateMember.FirstNameNative);


        softAssert.assertEquals(matrixData.get("FirstName"),POScreateMember.FirstName, "FirstName (Matrix) : ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName : "+matrixData.get("FirstName"));
        _scenario.write("Expected FirstName : "+POScreateMember.FirstName);


        softAssert.assertEquals(matrixData.get("LastName"),POScreateMember.LastName, "LastName (Matrix) : ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName : "+matrixData.get("LastName"));
        _scenario.write("Expected LastName : "+POScreateMember.LastName);


        softAssert.assertEquals(matrixData.get("Country"),POScreateMember.CountryNameText, "CountryNameText (Matrix) : ");
        _scenario.write("==============CountryNameText ============= ");
        _scenario.write("Actual CountryNameText : "+matrixData.get("Country"));
        _scenario.write("Expected CountryNameText : "+POScreateMember.CountryNameText);


        softAssert.assertEquals(matrixData.get("SpokenLanguageCode"),POScreateMember.SpokenLanguage, "SpokenLanguage (Matrix) :  ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage : "+matrixData.get("SpokenLanguageCode"));
        _scenario.write("Expected SpokenLanguage : "+POScreateMember.SpokenLanguage);

        softAssert.assertEquals(matrixData.get("AgeRange"),POScreateMember.AgeRange, "AgeRange  (Matrix) : ");
        _scenario.write("==============AgeRange ============= ");
        _scenario.write("Actual AgeRange : "+matrixData.get("AgeRange"));
        _scenario.write("Expected AgeRange : "+POScreateMember.AgeRange);

        softAssert.assertEquals(matrixData.get("CustomerRegistrationDatetime").substring(0,10).trim(),POScreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime (Matrix) : ");
        _scenario.write("==============CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime : "+matrixData.get("CustomerRegistrationDatetime").substring(0,10).trim());
        _scenario.write("Expected CustomerRegistrationDatetime : "+POScreateMember.CustomerRegistrationDatetime);
        //from query
        softAssert.assertEquals(matrixData.get("CardPickupMethod"),POScreateMember.CardPickupMethod, "CardPickupMethod (Matrix) : ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod : "+matrixData.get("CardPickupMethod"));
        _scenario.write("Expected CardPickupMethod : "+POScreateMember.CardPickupMethod);


        softAssert.assertEquals(matrixData.get("IssuedCardTier"),POScreateMember.CardTier, "CardTier (Matrix) : ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier : "+matrixData.get("IssuedCardTier"));
        _scenario.write("Expected CardTier : "+POScreateMember.CardTier);

        softAssert.assertEquals(matrixData.get("RegistrationDivisionCode"),POScreateMember.RegistrationDivisionCode, "RegistrationDivisionCode (Matrix) : ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode : "+matrixData.get("RegistrationDivisionCode"));
        _scenario.write("Expected RegistrationDivisionCode : "+POScreateMember.RegistrationDivisionCode);

        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),POScreateMember.RegistrationLocationID, "RegistrationLocationID  (Matrix) ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID  (Matrix) : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID  (Matrix) : "+POScreateMember.RegistrationLocationID);


        softAssert.assertEquals(matrixData.get("SourceSystem"),POScreateMember.SourceSystem, "SourceSystem (Matrix) : ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem : "+matrixData.get("SourceSystem"));
        _scenario.write("Expected SourceSystem : "+POScreateMember.SourceSystem);

        //Logic Added by the request of Anu on 17/12/2018 , Ticket No- https://dfsrtr.atlassian.net/browse/SFDC-1961
        String ExpectedValidMobileNumber1Flag="";
        if(POScreateMember.ContactNumberText.isEmpty() || POScreateMember.ContactNumberText=="") {  //when there is a valid phone number is present in UI
            ExpectedValidMobileNumber1Flag ="0";
        } else {
            ExpectedValidMobileNumber1Flag ="1";
        }

        softAssert.assertEquals(matrixData.get("ValidMobileNo1"),ExpectedValidMobileNumber1Flag, "Valid Mobile Phone Flag (Matrix) : ");
        _scenario.write("============== Valid Mobile Phone Flag ============= ");
        _scenario.write("Actual Valid Mobile Phone Flag : "+matrixData.get("ValidMobileNo1"));
        _scenario.write("Expected Valid Mobile Phone Flag : "+ExpectedValidMobileNumber1Flag);

        // Logic re added by the request of Anu on 17/12/2018 , Ticket no - https://dfsrtr.atlassian.net/browse/SFDC-1961  =============================
        // changing the Logic according to the ticket https://dfsrtr.atlassian.net/browse/SFDC-2224
        // the isInvaldEmail value in Matrix should be compared with IsInvalidEmail checkbox in SF

//    <<Removed
// String IsInvalidEmail_status=null;
//        if(POScreateMember.EmailAddressText.isEmpty()){
//            IsInvalidEmail_status = "1";
//        }else {
//            IsInvalidEmail_status="0";
//        }
// Removed >>

        softAssert.assertEquals(matrixData.get("IsInvalidEmail"),member.IsInvalidEmail_sf, "Valid Email Address(IsInvalidEmail) (Matrix) : ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail) : "+matrixData.get("IsInvalidEmail"));
        _scenario.write("Expected Valid Email Address(IsInvalidEmail) : "+member.IsInvalidEmail_sf);


        // RegistrationLocationID

        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),POScreateMember.RegistrationLocationID, "RegistrationLocationID (Matrix) : ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID : "+POScreateMember.RegistrationLocationID);


        // ================== End of the request of Anu =================================================================================================

        String isEmptyEmail_status=null;
        if(POScreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }


        softAssert.assertEquals(matrixData.get("IsEmptyEmail"),isEmptyEmail_status, "IsEmptyEmail (Matrix) : " );
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail : "+matrixData.get("IsEmptyEmail"));
        _scenario.write("Expected IsEmptyEmail : "+isEmptyEmail_status);

        softAssert.assertEquals(matrixData.get("LastNameNative"), "", "LastNameNative (Matrix) : ");
        _scenario.write("==============LastNameNative ============= ");
        _scenario.write("Actual LastNameNative : "+matrixData.get("LastNameNative"));
        _scenario.write("Expected LastNameNative : "+POScreateMember.LastNameNative);

        softAssert.assertEquals(matrixData.get("PostalCode"),"", "PostalCode (Matrix) : " );
        _scenario.write("==============PostalCode ============= ");
        _scenario.write("Actual PostalCode : "+matrixData.get("PostalCode"));
        _scenario.write("Expected PostalCode : "+POScreateMember.PostalCode);

        softAssert.assertEquals(matrixData.get("EnglishState"),"", "EnglishState (Matrix) : ");
        _scenario.write("==============CityNameText ============= ");
        _scenario.write("Actual CityNameText : "+matrixData.get("EnglishState"));
        _scenario.write("Expected CityNameText : "+POScreateMember.CityNameText);

        //Removing assertion
//        softAssert.assertEquals(matrixData.get("InvalidAddress"),"", "InvalidAddress : ");
//        _scenario.write("==============IsInvalidAddress ============= ");
//        _scenario.write("Actual IsInvalidAddress : "+matrixData.get("InvalidAddress"));
//        _scenario.write("Expected IsInvalidAddress : "+POScreateMember.IsInvalidAddress);

        // MAKING THE CHANGE ACCORDING TO https://dfsrtr.atlassian.net/browse/SFDC-2224
        // EmailUnwillingToProvide IS ALWAYS EXPECTED TO BE "0" FOR POS CREATE MEMBER

//        String EmailUnwillingToProvideFlagExpected="";
//        if(member.EmailAddressText_sf.isEmpty()) {
//            EmailUnwillingToProvideFlagExpected="1";
//        } else {
//            EmailUnwillingToProvideFlagExpected="0";
//        }

        softAssert.assertEquals(matrixData.get("IsDNWTP"),"0", "EmailUnwillingToProvide (Matrix) : " );
        _scenario.write("==============EmailUnwillingToProvide ============= ");
        _scenario.write("Actual EmailUnwillingToProvide : "+matrixData.get("IsDNWTP"));
        _scenario.write("Expected EmailUnwillingToProvide : "+"0");

        softAssert.assertEquals(matrixData.get("BirthDate"),"", "BirthDate (Matrix) : ");
        _scenario.write("==============BirthDate ============= ");
        _scenario.write("Actual BirthDate : "+matrixData.get("BirthDate"));
        _scenario.write("Expected BirthDate : "+POScreateMember.BirthDate);

        softAssert.assertEquals(matrixData.get("BirthMonth"),"", "BirthMonth (Matrix) : ");
        _scenario.write("==============BirthMonth ============= ");
        _scenario.write("Actual BirthMonth : "+matrixData.get("BirthMonth"));
        _scenario.write("Expected BirthMonth : "+POScreateMember.BirthMonth);

        softAssert.assertEquals(matrixData.get("EnglishAddress2"),"", "EnglishAddress2 (Matrix) : ");
        _scenario.write("==============MailingAddress2 ============= ");
        _scenario.write("Actual MailingAddress2 : "+matrixData.get("EnglishAddress2"));
        _scenario.write("Expected MailingAddress2 : "+POScreateMember.MailingAddress2);

        softAssert.assertEquals(matrixData.get("ZipCode"),"", "ZipCode (Matrix) : ");
        _scenario.write("==============ZipCode ============= ");
        _scenario.write("Actual ZipCode : "+matrixData.get("ZipCode"));
        _scenario.write("Expected ZipCode : "+POScreateMember.ZipCode);

        softAssert.assertEquals(matrixData.get("NativeCity"),"", "NativeCity (Matrix) : ");
        _scenario.write("==============CorrCityNameText ============= ");
        _scenario.write("Actual CorrCityNameText : "+matrixData.get("NativeCity"));
        _scenario.write("Expected CorrCityNameText : "+POScreateMember.CorrCityNameText);

        softAssert.assertEquals(matrixData.get("NativeState"),"", "NativeState (Matrix) : ");
        _scenario.write("==============CorrStateNameText ============= ");
        _scenario.write("Actual CorrStateNameText : "+matrixData.get("NativeState"));
        _scenario.write("Expected CorrStateNameText : "+POScreateMember.CorrStateNameText);


        softAssert.assertEquals(matrixData.get("EnglishAddress1"),"", "EnglishAddress1 (Matrix) : ");
        _scenario.write("==============MailingAddress1 ============= ");
        _scenario.write("Actual MailingAddress1 : "+matrixData.get("EnglishAddress1"));
        _scenario.write("Expected MailingAddress1 : "+POScreateMember.MailingAddress1);

        softAssert.assertEquals(matrixData.get("EnglishAddress3"),"", "EnglishAddress3 (Matrix) : ");
        _scenario.write("==============MailingAddress3 ============= ");
        _scenario.write("Actual MailingAddress3 : "+matrixData.get("EnglishAddress3"));
        _scenario.write("Expected MailingAddress3 : "+POScreateMember.MailingAddress3);

        softAssert.assertEquals(matrixData.get("EnglishCity"),"", "EnglishCity (Matrix) : ");
        _scenario.write("==============ResCityNameText ============= ");
        _scenario.write("Actual ResCityNameText : "+matrixData.get("EnglishCity"));
        _scenario.write("Expected ResCityNameText : "+POScreateMember.ResCityNameText);

        softAssert.assertEquals(matrixData.get("EnglishState"),"", "EnglishState (Matrix) : ");
        _scenario.write("==============ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText : "+matrixData.get("EnglishState"));
        _scenario.write("Expected ResStateNameText : "+POScreateMember.ResStateNameText);

        softAssert.assertEquals(matrixData.get("CustomerLeisureActivity"),"", "CustomerLeisureActivity (Matrix) : ");
        _scenario.write("==============CustomerLeisureActivity ============= ");
        _scenario.write("Actual CustomerLeisureActivity : "+matrixData.get("CustomerLeisureActivity"));
        _scenario.write("Expected CustomerLeisureActivity : "+POScreateMember.CustomerLeisureActivity);

        softAssert.assertEquals(matrixData.get("CustomerShoppingPreference"),"", "CustomerShoppingPreference (Matrix): ");
        _scenario.write("==============CustomerShoppingPreference ============= ");
        _scenario.write("Actual CustomerShoppingPreference : "+matrixData.get("CustomerShoppingPreference"));
        _scenario.write("Expected CustomerShoppingPreference : "+POScreateMember.CustomerShoppingPreference);


        softAssert.assertEquals(matrixData.get("CustomerPreferredBrands"),"", "CustomerPreferredBrands (Matrix): ");
        _scenario.write("==============CustomerPreferredBrands ============= ");
        _scenario.write("Actual CustomerPreferredBrands : "+matrixData.get("CustomerPreferredBrands"));
        _scenario.write("Expected CustomerPreferredBrands : "+POScreateMember.CustomerPreferredBrands);


        softAssert.assertEquals(matrixData.get("LeisureActivitiesMultiple"),"", "LeisureActivitiesMultiple  (Matrix) : ");
        _scenario.write("==============LeisureActivitiesMultiple ============= ");
        _scenario.write("Actual LeisureActivitiesMultiple : "+matrixData.get("LeisureActivitiesMultiple"));
        _scenario.write("Expected LeisureActivitiesMultiple : "+POScreateMember.LeisureActivitiesMultiple);


        softAssert.assertEquals(matrixData.get("ShoppingPreferencesMultiple"),"", "ShoppingPreferencesMultiple  (Matrix) : ");
        _scenario.write("==============ShoppingPreferencesMultiple ============= ");
        _scenario.write("Actual ShoppingPreferencesMultiple : "+matrixData.get("ShoppingPreferencesMultiple"));
        _scenario.write("Expected ShoppingPreferencesMultiple : "+POScreateMember.ShoppingPreferencesMultiple);


        softAssert.assertEquals(matrixData.get("PreferredBrandsMultiple"),"", "PreferredBrandsMultiple  (Matrix) : ");
        _scenario.write("==============PreferredBrandsMultiple ============= ");
        _scenario.write("Actual PreferredBrandsMultiple : "+matrixData.get("PreferredBrandsMultiple"));
        _scenario.write("Expected PreferredBrandsMultiple : "+POScreateMember.PreferredBrandsMultiple);


        softAssert.assertEquals(matrixData.get("MarketingSource"),"", "MarketingSource (Matrix) : ");
        _scenario.write("==============MarketingSource ============= ");
        _scenario.write("Actual MarketingSource : "+matrixData.get("MarketingSource"));
        _scenario.write("Expected MarketingSource : "+POScreateMember.MarketingSource);

        //Removing assertion
//        softAssert.assertEquals(matrixData.get("IsValidZipCode"),"", "IsValidZipCode : ");
//        _scenario.write("==============ZipCodeValidFlag ============= ");
//        _scenario.write("Actual ZipCodeValidFlag : "+matrixData.get("IsValidZipCode"));
//        _scenario.write("Expected ZipCodeValidFlag : "+POScreateMember.ZipCodeValidFlag);


        softAssert.assertEquals(matrixData.get("IpadID"),"", "IpadID (Matrix) : ");
        _scenario.write("==============IpadID ============= ");
        _scenario.write("Actual IpadID : "+matrixData.get("IpadID"));
        _scenario.write("Expected IpadID : "+POScreateMember.IpadID);


       // assertEquals(matrixData.get("Staff"),POScreateMember.CreationUserID);

        //assertEquals(matrixData.get("Staff"),POScreateMember.StaffID);


        softAssert.assertEquals(matrixData.get("MarketingSourceOthers"),"", "MarketingSourceOthers (Matrix) : ");
        _scenario.write("==============MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers : "+matrixData.get("MarketingSourceOthers"));
        _scenario.write("Expected MarketingSourceOthers : "+POScreateMember.MarketingSourceOthers);


        softAssert.assertEquals(matrixData.get("AddedBy"),POScreateMember.CreationUserID, "AddedBy (Matrix) : ");
        _scenario.write("==============CreationUserID ============= ");
        _scenario.write("Actual CreationUserID : "+matrixData.get("AddedBy"));
        _scenario.write("Expected CreationUserID : "+POScreateMember.CreationUserID);


        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }

    }

}
