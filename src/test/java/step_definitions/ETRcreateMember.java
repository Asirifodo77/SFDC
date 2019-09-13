package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
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
import java.util.HashMap;

import static org.testng.Assert.fail;

public class ETRcreateMember {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public DSAcreateMember_pageObjects ETRcreateMember;
    public SearchMember_pageobjects searchMember_pageobjects;
    public SearchMember_pageobjects searchMember_pageobjects2;
    public String POSTrequestBody;
    public String UpdatePOSTrequestBody;
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
    public MemberValidation_pageobjects sf;
    public testDataCleanup dataCleanup;
    public TakeScreenshot screenshot;
    public getSFaccessToken accessToken;
    public String token;
    public validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;

    public ETRcreateMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;

        //creating loginPage object
        loginPage = new Login_pageobjects(driver,_scenario);

        ETRcreateMember = new DSAcreateMember_pageObjects(_scenario);

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
        sf = new MemberValidation_pageobjects(driver,_scenario);
        dataCleanup = new testDataCleanup();
        screenshot = new TakeScreenshot(driver,_scenario);
        accessToken = new getSFaccessToken();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);

    }


    @Given("^I read ETR request body from Json file$")
    public void iReadETRRequestBodyFromJsonFile() throws Throwable {
        POSTrequestBody = ETRcreateMember.readRequestFromJson(ETRcreateMember.getETRjsonPath(environment)).trim();
        //PRINTING THE RESPONSE IN CUCUMBER REPORT
        _scenario.write("=============== The API request body for ETR create member is as follows ===================");
        _scenario.write(POSTrequestBody);
        _scenario.write("================================ End of API request body ===================================");
    }

    @Given("^I cleanup data for existing ETR member from SF and MatrixDB$")
    public void iCleanupDataForExistingETRMemberFromSFAndMatrixDB() throws Throwable {
        //reading the data from Json request file
        ETRcreateMember.getElementValuesFromETRJsonFile(environment);
        //cleaning up data for the given card number only (Not reading from csv file)
        cardNumber = ETRcreateMember.CardNo;
        dataCleanup.cleanUpOneMemberCardData(_scenario,cardNumber);
    }


    @When("^I send POST request to create new member through ETR$")
    public void iSendPOSTRequestToCreateNewMemberThroughETR() throws Throwable {
        ETRcreateMember.createMemberETR(POSTrequestBody);
    }

    @Then("^I validate the memberID in salesforce and Matrix for ETR Created member$")
    public void iValidateTheMemberIDInSalesforceAndMatrixForETRCreatedMember() throws Throwable {

        //getting the access token
        token = accessToken.getSFaccessToken();

        //validate member id from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberID(token,cardNumber,environment);

    }

    @Then("^I login to SF to search ETR member$")
    public void iLoginToSFToSearchETRMember() throws Throwable {
        loginPage.loginToSF();
    }

    @Then("^I search ETR member using card number$")
    public void iSearchETRMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        searchMember_pageobjects.searchMemberThroughCardNumber(dataTable,cardNumber);
    }

    @Then("^I read Store Location data from SF$")
    public void iReadStoreLocationDataFromSF() throws Throwable {

        //getting the expected values from the json
        ETRcreateMember.getElementValuesFromETRJsonFile(environment);

        //getting access token
        String tokenToGetStoreLocation = null;
        try {
            tokenToGetStoreLocation = accessToken.getSFaccessToken();
        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to generate Access token due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to generate Access token due to an exception - "+e.getMessage());
        }

        //Getting the store Location Name from Salesforce
        if(ETRcreateMember.RegistrationDivisionCode!=null && ETRcreateMember.RegistrationLocationID!=null) {  //checking if divisionCode and DivisionId is not null
            sfData.querySFDataToGetStoreLocationName(tokenToGetStoreLocation,ETRcreateMember.RegistrationDivisionCode,ETRcreateMember.RegistrationLocationID);
        } else {
            System.out.println("TEST FAILED INTENTIONALLY ! Unable to get store Location Name from SF, due to 'RegistrationDivisionCode' or 'RegistrationLocationID' is NULL in the Json file");
            Assert.fail("TEST FAILED INTENTIONALLY ! Unable to get store Location Name from SF, due to 'RegistrationDivisionCode' or 'RegistrationLocationID' is NULL in the Json file");
        }


    }

    @Then("^I read details from ETR member profile in SF$")
    public void iReadDetailsFromETRMemberProfileInSF() throws Throwable {
        sf.switchToMainFrame();

        //Taking screenshot 1
        screenshot.takeScreenshot();

        sf.getTitleCode();
        sf.getGenderCode();
        sf.getEmailAddressText();
        sf.getPostalCode();
        sf.getCityNameText();
        sf.getContactNumberText();
        sf.getIsInvalidEmail();
        sf.getIsEmptyEmail();
        sf.getUnwillingToProvideEmail();
        sf.getFirstNameNative();
        sf.getLastNameNative();
        sf.getBirthDate();
        sf.getBirthMonth();
        sf.getMailingAddress2();
        sf.getZipCode();
        sf.getMailingAddress1();
        sf.getMailingAddress3();
        sf.getResCityNameText();
        sf.getResStateNameText();
        sf.getMarketingSource();
        sf.getZipCodeValidFlag();
        sf.getMarketingSourceOthers();


        sf.getFirstName();
        sf.getLastName();
        sf.getNativeSalutation();
        sf.getMobile1AreaCode();
        sf.getValidMobileNo1();
        sf.getEnglishCountry();
        sf.getAgeRange();

        // going inside ContactPreferencesTab

        sf.switchToContactPreferencesTab();

        //Taking screenshot 1
        screenshot.takeScreenshot();

        sf.getSpokenLanguage();
        sf.getIsContactable();


        // going inside Shopping preferences tab
        sf.switchToShoppingPreferencesTab();

        //Taking screenshot 1
        screenshot.takeScreenshot();

        sf.getCustomerLeisureActivity();
        sf.getCustomerShoppingPreference();
        sf.getCustomerPreferredBrands();
        sf.getLeisureActivitiesMultiple();
        sf.getShoppingPreferencesMultiple();
        sf.getPreferredBrandsMultiple();


        sf.switchToMemberCycleTab();

        //Taking screenshot 1
        screenshot.takeScreenshot();

        sf.getCardTier();
        sf.getCustomerRegistrationDatetime();
        sf.getEnrollmentLocation();

    }

    @Then("^I read ETR member details from Matrix$")
    public void iReadETRMemberDetailsFromMatrix() throws Throwable {
        matrixData= matrix.readMemberDataFromMatrix(environment,cardNumber);
    }

    @Then("^I validate ETR Membership Information with SF$")
    public void iValidateETRMembershipInformationWithSF() throws Throwable {
        //reading all values of Json file so that it can be accessed through the ETRcreateMember object
        ETRcreateMember.getElementValuesFromETRJsonFile(environment);

        System.out.println("==================== Validating fields In SF =======================");
        _scenario.write("==================== Validating fields In SF =======================");

        //Title Code
        softAssert.assertEquals(sf.Salutation_sf,ETRcreateMember.TitleCode, "Title Code (SF)  ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode : "+sf.Salutation_sf);
        _scenario.write("Expected TitleCode : "+ETRcreateMember.TitleCode);

        //GenderCode
        softAssert.assertEquals(sf.Gender_sf,ETRcreateMember.GenderCode, "Gender Code (SF)  ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode : "+sf.Gender_sf);
        _scenario.write("Expected GenderCode : "+ETRcreateMember.GenderCode);


        // EmailAddressText
        softAssert.assertTrue(sf.EmailAddressText_sf.equalsIgnoreCase(ETRcreateMember.EmailAddressText), "EmailAddressText (SF)  ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText : "+sf.EmailAddressText_sf);
        _scenario.write("Expected EmailAddressText : "+ETRcreateMember.EmailAddressText);

        //ContactNumberText
        softAssert.assertEquals(sf.ContactNumberText_sf,ETRcreateMember.ContactNumberText, "ContactNumberText (SF)  ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText : "+sf.ContactNumberText_sf);
        _scenario.write("Expected ContactNumberText : "+ETRcreateMember.ContactNumberText);

        //IsInvalidEmail
        softAssert.assertEquals(sf.IsInvalidEmail_sf,ETRcreateMember.IsInvalidEmail, "Valid Email Address(IsInvalidEmail) (SF)  ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail) : "+sf.IsInvalidEmail_sf);
        _scenario.write("Expected Valid Email Address(IsInvalidEmail) : "+ETRcreateMember.IsInvalidEmail);

        //IsEmptyEmail
        softAssert.assertEquals(sf.IsEmptyEmail_sf,ETRcreateMember.IsEmptyEmail, "IsEmptyEmail (SF) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail : "+sf.IsEmptyEmail_sf);
        _scenario.write("Expected IsEmptyEmail : "+ETRcreateMember.IsEmptyEmail);


        // FirstName
        softAssert.assertEquals(sf.FirstName_sf,ETRcreateMember.FirstName, "FirstName (SF) ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName : "+sf.FirstName_sf);
        _scenario.write("Expected FirstName : "+ETRcreateMember.FirstName);

        // LastName
        softAssert.assertEquals(sf.LastName_sf,ETRcreateMember.LastName, "LastName (SF) ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName : "+sf.LastName_sf);
        _scenario.write("Expected LastName : "+ETRcreateMember.LastName);

        // NativeSalutation
        softAssert.assertEquals(sf.NativeSalutation_sf,ETRcreateMember.NativeSalutation, "NativeSalutation (SF) ");
        _scenario.write("==============NativeSalutation ============= ");
        _scenario.write("Actual NativeSalutation : "+sf.NativeSalutation_sf);
        _scenario.write("Expected NativeSalutation : "+ETRcreateMember.NativeSalutation);

        //Mobile1AreaCode
        softAssert.assertEquals(sf.Mobile1AreaCode_sf,ETRcreateMember.Mobile1AreaCode, "Mobile1AreaCode (SF) ");
        _scenario.write("==============Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode : "+sf.Mobile1AreaCode_sf);
        _scenario.write("Expected Mobile1AreaCode : "+ETRcreateMember.Mobile1AreaCode);

        //ValidMobileNo1
        softAssert.assertEquals(sf.ValidMobileNo1_sf,ETRcreateMember.ValidMobileNo1, "ValidMobileNo1 (SF) ");
        _scenario.write("==============ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1 : "+sf.ValidMobileNo1_sf);
        _scenario.write("Expected ValidMobileNo1 : "+ETRcreateMember.ValidMobileNo1);

        //EnglishCountry
        softAssert.assertEquals(sf.EnglishCountry_sf,ETRcreateMember.EnglishCountry, "EnglishCountry (SF) ");
        _scenario.write("==============EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry : "+sf.EnglishCountry_sf);
        _scenario.write("Expected EnglishCountry : "+ETRcreateMember.EnglishCountry);

        // SpokenLanguage
        softAssert.assertEquals(sf.SpokenLanguage_sf,ETRcreateMember.SpokenLanguage, "SpokenLanguage (SF) ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage : "+sf.SpokenLanguage_sf);
        _scenario.write("Expected SpokenLanguage : "+ETRcreateMember.SpokenLanguage);

        //IsContactable
        softAssert.assertEquals(sf.IsContactable_sf,ETRcreateMember.IsContactable, "IsContactable (SF) ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable : "+sf.IsContactable_sf);
        _scenario.write("Expected IsContactable : "+ETRcreateMember.IsContactable);
        _scenario.write("Actual IsContactable value is "+sf.IsContactable_sf +" when 'Opt In Marketing & Promotional (Phone)' = "+sf.isContactablePhone_sf+ " and 'Opt In Marketing & Promotional (Mail)' = "+sf.isContactableMail_sf+ " and 'Opt In Marketing & Promotional (Email)' = "+sf.isContactableEmail_sf);

        //CardTier - In SF its allways blank until there is a valid transaction done. So ignoring this assertion on purpose

        //CustomerRegistrationDatetime
        softAssert.assertEquals(sf.CustomerRegistrationDatetime_sf,ETRcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime (SF) ");
        _scenario.write("==============CustomerRegistrationDatetime =============== ");
        _scenario.write("Actual CustomerRegistrationDatetime : "+sf.CustomerRegistrationDatetime_sf);
        _scenario.write("Expected CustomerRegistrationDatetime : "+ETRcreateMember.CustomerRegistrationDatetime);

        //AgeRange
        softAssert.assertEquals(sf.AgeRange_sf,ETRcreateMember.AgeRange, "AgeRange (SF) ");
        _scenario.write("==============AgeRange =============== ");
        _scenario.write("Actual AgeRange : "+sf.AgeRange_sf);
        _scenario.write("Expected AgeRange : "+ETRcreateMember.AgeRange);

        //StoreLocation (Store_Name__c)
        softAssert.assertEquals(sf.enroll_Location_sf.toLowerCase(), sfData.Store_Name__c.toLowerCase(),"StoreLocation  (SF) ");
        _scenario.write("==============StoreLocation ============= ");
        _scenario.write("Actual StoreLocation (SF)  : "+sf.enroll_Location_sf);
        _scenario.write("Expected StoreLocation  (SF) : "+sfData.Store_Name__c);

        // Fields that should be Null ############################################################
        //########################################################################################
        softAssert.assertEquals(member.PostalCode_sf,"", "PostalCode  (SF) ");
        _scenario.write("==============PostalCode ============= ");
        _scenario.write("Actual PostalCode  (SF) : "+member.PostalCode_sf);
        _scenario.write("Expected PostalCode  (SF) : "+"");

        softAssert.assertEquals(member.CityName_sf,"", "CityNameText  (SF) ");
        _scenario.write("==============CityNameText ============= ");
        _scenario.write("Actual CityNameText  (SF) : "+member.CityName_sf);
        _scenario.write("Expected CityNameText  (SF) : "+"");

        softAssert.assertEquals(member.FirstNameNative_sf,"", "FirstNameNative (SF) : ");
        _scenario.write("==============FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative : "+member.FirstNameNative_sf);
        _scenario.write("Expected FirstNameNative : "+"");

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

        softAssert.assertEquals(member.ResStateNameText_sf,"", "ResStateNameText  (SF) ");
        _scenario.write("==============ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText  (SF) : "+member.ResStateNameText_sf);
        _scenario.write("Expected ResStateNameText  (SF) : "+"");

        softAssert.assertEquals(member.ZipCodeValidFlag_sf,"", "ZipCodeValidFlag  (SF) ");
        _scenario.write("==============ZipCodeValidFlag ============= ");
        _scenario.write("Actual ZipCodeValidFlag  (SF) : "+member.ZipCodeValidFlag_sf);
        _scenario.write("Expected ZipCodeValidFlag  (SF) : "+"");

        softAssert.assertEquals(member.MarketingSourceOthers_sf,"", "MarketingSourceOthers  (SF) ");
        _scenario.write("==============MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers  (SF) : "+member.MarketingSourceOthers_sf);
        _scenario.write("Expected MarketingSourceOthers  (SF) : "+"");

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

        //############################################ END OF NULL FIELD VALIDATION ###################################################
    }

    @Then("^I validate ETR Membership Information with matrixDB$")
    public void iValidateETRMembershipInformationWithMatrixDB() throws Throwable {

        System.out.println("==================== Validating fields In Matrix  =======================");
        _scenario.write("==================== Validating fields In Matrix  =======================");

        softAssert.assertEquals(matrixData.get("Salutation"),ETRcreateMember.TitleCode, "TitleCode (Matrix) ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode : "+matrixData.get("Salutation"));
        _scenario.write("Expected TitleCode : "+ETRcreateMember.TitleCode);

        softAssert.assertEquals(matrixData.get("Gender"),ETRcreateMember.GenderCode, "Gender (Matrix) ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode : "+matrixData.get("Gender"));
        _scenario.write("Expected GenderCode : "+ETRcreateMember.GenderCode);

        softAssert.assertTrue(matrixData.get("Email").equalsIgnoreCase(ETRcreateMember.EmailAddressText), "EmailAddressText (Matrix) ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText : "+matrixData.get("Email"));
        _scenario.write("Expected EmailAddressText : "+ETRcreateMember.EmailAddressText);

        softAssert.assertEquals(matrixData.get("MobileNo"),ETRcreateMember.ContactNumberText, "ContactNumberText (Matrix) ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText : "+matrixData.get("MobileNo"));
        _scenario.write("Expected ContactNumberText : "+ETRcreateMember.ContactNumberText);

        // IsInvalidEmail
        String IsInvalidEmail_status=null;
        if(ETRcreateMember.EmailAddressText.isEmpty()){
            IsInvalidEmail_status = "1";
        }else {
            IsInvalidEmail_status="0";
        }
        softAssert.assertEquals(matrixData.get("IsInvalidEmail"),IsInvalidEmail_status, "Valid Email Address (IsInvalidEmail) (Matrix) ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address (IsInvalidEmail) : "+matrixData.get("IsInvalidEmail"));
        _scenario.write("Expected Valid Email Address (IsInvalidEmail) : "+IsInvalidEmail_status);

        //IsEmptyEmail
        String isEmptyEmail_status=null;
        if( ETRcreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }

        softAssert.assertEquals(matrixData.get("IsEmptyEmail"),isEmptyEmail_status, "IsEmptyEmail (Matrix) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail : "+matrixData.get("IsEmptyEmail"));
        _scenario.write("Expected IsEmptyEmail : "+isEmptyEmail_status);

        // FirstName
        softAssert.assertEquals(matrixData.get("FirstName"),ETRcreateMember.FirstName, "FirstName (Matrix) ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName : "+matrixData.get("FirstName"));
        _scenario.write("Expected FirstName : "+ETRcreateMember.FirstName);

        //LastName
        softAssert.assertEquals(matrixData.get("LastName"),ETRcreateMember.LastName, "LastName (Matrix) ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName : "+matrixData.get("LastName"));
        _scenario.write("Expected LastName : "+ETRcreateMember.LastName);

        //NativeSalutation
        softAssert.assertEquals(matrixData.get("NativeSalutation"),ETRcreateMember.NativeSalutation, "NativeSalutation (Matrix) ");
        _scenario.write("==============NativeSalutation ============= ");
        _scenario.write("Actual NativeSalutation : "+matrixData.get("NativeSalutation"));
        _scenario.write("Expected NativeSalutation : "+ETRcreateMember.NativeSalutation);


        //ValidMobileNo1

        //value of ValidMobileNo1 will be "1" wheneve there is contactNumberText is provided (not null)
        String ValidMobileNo1_status=null;
        if(ETRcreateMember.ContactNumberText.isEmpty()){
            ValidMobileNo1_status ="0";
        } else {
            ValidMobileNo1_status="1";
        }

        softAssert.assertEquals(matrixData.get("ValidMobileNo1"), ValidMobileNo1_status, "ValidMobileNo1 (Matrix) ");
        _scenario.write("==============ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1 : "+matrixData.get("ValidMobileNo1"));
        _scenario.write("Expected ValidMobileNo1 : "+ValidMobileNo1_status);

        //EnglishCountry
        softAssert.assertEquals(matrixData.get("EnglishCountry"),ETRcreateMember.EnglishCountry, "EnglishCountry (Matrix) ");
        _scenario.write("==============EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry : "+matrixData.get("EnglishCountry"));
        _scenario.write("Expected EnglishCountry : "+ETRcreateMember.EnglishCountry);

        //SpokenLanguage
        softAssert.assertEquals(matrixData.get("SpokenLanguageCode"),ETRcreateMember.SpokenLanguage, "SpokenLanguageCode (Matrix) ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage : "+matrixData.get("SpokenLanguageCode"));
        _scenario.write("Expected SpokenLanguage : "+ETRcreateMember.SpokenLanguage);

        //IsContactable
        softAssert.assertEquals(matrixData.get("IsContactable"),ETRcreateMember.IsContactable, "IsContactable (Matrix) ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable : "+matrixData.get("IsContactable"));
        _scenario.write("Expected IsContactable : "+ETRcreateMember.IsContactable);

        // #############################  More validations added as per the ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957 ############################
        // 1. Opt in Marketing & promotional (Phone)  ===================================================================

        // 1.1 isOptOutMobile1  --------------------------------------------------------------------
        String Expected_isOptOutMobile1=null;
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableEmail_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableMail_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableMail_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutAddressOthers="0";
        } else {
            Expected_isOptOutAddressOthers="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutAddressOthers"),Expected_isOptOutAddressOthers, "isOptOutAddressOthers (Matrix) ");
        _scenario.write("============== isOptOutAddressOthers ============= ");
        _scenario.write("Actual isOptOutAddressOthers (Matrix) : "+matrixData.get("IsOptOutAddressOthers"));
        _scenario.write("Expected isOptOutAddressOthers (Matrix) : "+Expected_isOptOutAddressOthers);
        // #############################  end of additional validations for ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957  ############################

        //CardTier
        softAssert.assertEquals(matrixData.get("IssuedCardTier"),ETRcreateMember.CardTier, "IssuedCardTier (Matrix) ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier : "+matrixData.get("IssuedCardTier"));
        _scenario.write("Expected CardTier : "+ETRcreateMember.CardTier);

        //CardPickupMethod
        softAssert.assertEquals(matrixData.get("CardPickupMethod"),ETRcreateMember.CardPickupMethod, "CardPickupMethod (Matrix) ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod : "+matrixData.get("CardPickupMethod"));
        _scenario.write("Expected CardPickupMethod : "+ETRcreateMember.CardPickupMethod);


        //SourceSystem
        softAssert.assertEquals(matrixData.get("SourceSystem"),ETRcreateMember.SourceSystem, "SourceSystem (Matrix) ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem : "+matrixData.get("SourceSystem"));
        _scenario.write("Expected SourceSystem : "+ETRcreateMember.SourceSystem);


        //RegistrationDivisionCode
        softAssert.assertEquals(matrixData.get("RegistrationDivisionCode"),ETRcreateMember.RegistrationDivisionCode, "RegistrationDivisionCode (Matrix) ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode : "+matrixData.get("RegistrationDivisionCode"));
        _scenario.write("Expected RegistrationDivisionCode : "+ETRcreateMember.RegistrationDivisionCode);


        //RegistrationLocationID
        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),ETRcreateMember.RegistrationLocationID, "RegistrationLocationID (Matrix) ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID : "+ETRcreateMember.RegistrationLocationID);

        //CustomerRegistrationDatetime
        String customerRegistrationDateTimeMatrix="";
        try {
            customerRegistrationDateTimeMatrix = matrixData.get("CustomerRegistrationDatetime").substring(0,10);
        } catch (Exception e) {
            customerRegistrationDateTimeMatrix = "";
        }

        softAssert.assertEquals(customerRegistrationDateTimeMatrix,ETRcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime (Matrix/Updated) ");
        _scenario.write("==============CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime  (Matrix/Updated) : "+customerRegistrationDateTimeMatrix);
        _scenario.write("Expected CustomerRegistrationDatetime  (Matrix/Updated) : "+ETRcreateMember.CustomerRegistrationDatetime);

        //AgeRange
        softAssert.assertEquals(matrixData.get("AgeRange"),ETRcreateMember.AgeRange, "AgeRange (Matrix) ");
        _scenario.write("==============AgeRange ============= ");
        _scenario.write("Actual AgeRange : "+matrixData.get("AgeRange"));
        _scenario.write("Expected AgeRange : "+ETRcreateMember.AgeRange);

        //StoreLocation
        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),ETRcreateMember.RegistrationLocationID, "RegistrationLocationID  (Matrix) ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID  (Matrix) : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID  (Matrix) : "+ETRcreateMember.RegistrationLocationID);

        //Mobile1AreaCode
        softAssert.assertEquals(matrixData.get("MobileAreaCode"),ETRcreateMember.Mobile1AreaCode, "MobileAreaCode  (Matrix) ");
        _scenario.write("==============MobileAreaCode ============= ");
        _scenario.write("Actual MobileAreaCode  (Matrix) : "+matrixData.get("MobileAreaCode"));
        _scenario.write("Expected MobileAreaCode  (Matrix) : "+ETRcreateMember.RegistrationLocationID);



    }

    @Then("^I read the Update ETR request body from Json file$")
    public void iReadTheUpdateETRRequestBodyFromJsonFile() throws Throwable {
        UpdatePOSTrequestBody = ETRcreateMember.readRequestFromJson(ETRcreateMember.getUpdateETRjsonPath(environment)).trim();

        //PRINTING THE update request IN CUCUMBER REPORT
        _scenario.write("=============== The API request body for ETR Update member is as follows ===================");
        _scenario.write(UpdatePOSTrequestBody);
        _scenario.write("================================ End of API request body ===================================");

    }

    @Then("^I send POST request to update ETR member$")
    public void iSendPOSTRequestToUpdateETRMember() throws Throwable {
        ETRcreateMember.updateMemberETR(UpdatePOSTrequestBody);
    }

    @Then("^I validate Updated ETR Membership Information with SF$")
    public void iValidateUpdatedETRMembershipInformationWithSF() throws Throwable {

        //reading all values of Json file so that it can be accessed through the ETRupdateMember object
        ETRcreateMember.getElementValuesFromUpdateETRJsonFile(environment);

        System.out.println("==================== Validating fields In SF for Updated ETR member =======================");
        _scenario.write("==================== Validating fields In SF for Updated ETR member=======================");

        //Title Code
        softAssert.assertEquals(sf.Salutation_sf,ETRcreateMember.TitleCode, "Title Code (SF/Updated)");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode (SF/Updated) : "+sf.Salutation_sf);
        _scenario.write("Expected TitleCode (SF/Updated) : "+ETRcreateMember.TitleCode);

        //GenderCode
        softAssert.assertEquals(sf.Gender_sf,ETRcreateMember.GenderCode, "Gender Code (SF/Updated) ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode (SF/Updated) : "+sf.Gender_sf);
        _scenario.write("Expected GenderCode (SF/Updated) : "+ETRcreateMember.GenderCode);


        // EmailAddressText
        softAssert.assertTrue(sf.EmailAddressText_sf.equalsIgnoreCase(ETRcreateMember.EmailAddressText), "EmailAddressText (SF/Updated) ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText (SF/Updated) : "+sf.EmailAddressText_sf);
        _scenario.write("Expected EmailAddressText (SF/Updated) : "+ETRcreateMember.EmailAddressText);

        //ContactNumberText
        softAssert.assertEquals(sf.ContactNumberText_sf,ETRcreateMember.ContactNumberText, "ContactNumberText (SF/Updated)");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText (SF/Updated): "+sf.ContactNumberText_sf);
        _scenario.write("Expected ContactNumberText (SF/Updated): "+ETRcreateMember.ContactNumberText);

        //IsInvalidEmail
        softAssert.assertEquals(sf.IsInvalidEmail_sf,ETRcreateMember.IsInvalidEmail, "Valid Email Address(IsInvalidEmail) (SF/Updated) ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail) (SF/Updated) : "+sf.IsInvalidEmail_sf);
        _scenario.write("Expected Valid Email Address(IsInvalidEmail) (SF/Updated) : "+ETRcreateMember.IsInvalidEmail);

        //IsEmptyEmail
        softAssert.assertEquals(sf.IsEmptyEmail_sf,ETRcreateMember.IsEmptyEmail, "IsEmptyEmail (SF/Updated) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail  (SF/Updated) : "+sf.IsEmptyEmail_sf);
        _scenario.write("Expected IsEmptyEmail  (SF/Updated) : "+ETRcreateMember.IsEmptyEmail);


        // FirstName
        softAssert.assertEquals(sf.FirstName_sf,ETRcreateMember.FirstName, "FirstName  (SF/Updated)  ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName  (SF/Updated) : "+sf.FirstName_sf);
        _scenario.write("Expected FirstName  (SF/Updated) : "+ETRcreateMember.FirstName);

        // LastName
        softAssert.assertEquals(sf.LastName_sf,ETRcreateMember.LastName, "LastName  (SF/Updated)  ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName  (SF/Updated) : "+sf.LastName_sf);
        _scenario.write("Expected LastName  (SF/Updated) : "+ETRcreateMember.LastName);

        // NativeSalutation
        softAssert.assertEquals(sf.NativeSalutation_sf,ETRcreateMember.NativeSalutation, "NativeSalutation  (SF/Updated)  ");
        _scenario.write("==============NativeSalutation ============= ");
        _scenario.write("Actual NativeSalutation  (SF/Updated) : "+sf.NativeSalutation_sf);
        _scenario.write("Expected NativeSalutation  (SF/Updated) : "+ETRcreateMember.NativeSalutation);

        //Mobile1AreaCode
        softAssert.assertEquals(sf.Mobile1AreaCode_sf,ETRcreateMember.Mobile1AreaCode, "Mobile1AreaCode  (SF/Updated)  ");
        _scenario.write("==============Mobile1AreaCode ============= ");
        _scenario.write("Actual Mobile1AreaCode  (SF/Updated) : "+sf.Mobile1AreaCode_sf);
        _scenario.write("Expected Mobile1AreaCode  (SF/Updated) : "+ETRcreateMember.Mobile1AreaCode);

        //ValidMobileNo1
        softAssert.assertEquals(sf.ValidMobileNo1_sf,ETRcreateMember.ValidMobileNo1, "ValidMobileNo1  (SF/Updated)  ");
        _scenario.write("==============ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1  (SF/Updated) : "+sf.ValidMobileNo1_sf);
        _scenario.write("Expected ValidMobileNo1  (SF/Updated) : "+ETRcreateMember.ValidMobileNo1);

        //EnglishCountry
        softAssert.assertEquals(sf.EnglishCountry_sf,ETRcreateMember.EnglishCountry, "EnglishCountry  (SF/Updated)  ");
        _scenario.write("==============EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry  (SF/Updated) : "+sf.EnglishCountry_sf);
        _scenario.write("Expected EnglishCountry  (SF/Updated) : "+ETRcreateMember.EnglishCountry);

        // SpokenLanguage
        softAssert.assertEquals(sf.SpokenLanguage_sf,ETRcreateMember.SpokenLanguage, "SpokenLanguage  (SF/Updated)  ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage  (SF/Updated) : "+sf.SpokenLanguage_sf);
        _scenario.write("Expected SpokenLanguage  (SF/Updated) : "+ETRcreateMember.SpokenLanguage);

        //IsContactable
        softAssert.assertEquals(sf.IsContactable_sf,ETRcreateMember.IsContactable, "IsContactable  (SF/Updated)  ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable  (SF/Updated) : "+sf.IsContactable_sf);
        _scenario.write("Expected IsContactable  (SF/Updated) : "+ETRcreateMember.IsContactable);
        _scenario.write("Actual IsContactable value is "+sf.IsContactable_sf +" when 'Opt In Marketing & Promotional (Phone)' = "+sf.isContactablePhone_sf+ " and 'Opt In Marketing & Promotional (Mail)' = "+sf.isContactableMail_sf+ " and 'Opt In Marketing & Promotional (Email)' = "+sf.isContactableEmail_sf);


        //CardTier - In SF its allways blank until there is a valid transaction done. So ignoring this assertion on purpose

        //CustomerRegistrationDatetime
        softAssert.assertEquals(sf.CustomerRegistrationDatetime_sf,ETRcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime  (SF/Updated)  ");
        _scenario.write("==============CustomerRegistrationDatetime =============== ");
        _scenario.write("Actual CustomerRegistrationDatetime  (SF/Updated) : "+sf.CustomerRegistrationDatetime_sf);
        _scenario.write("Expected CustomerRegistrationDatetime  (SF/Updated) : "+ETRcreateMember.CustomerRegistrationDatetime);

        //AgeRange
        softAssert.assertEquals(sf.AgeRange_sf,ETRcreateMember.AgeRange, "AgeRange  (SF/Updated) : ");
        _scenario.write("==============AgeRange =============== ");
        _scenario.write("Actual AgeRange  (SF/Updated) : "+sf.AgeRange_sf);
        _scenario.write("Expected AgeRange  (SF/Updated) : "+ETRcreateMember.AgeRange);

        // Fields that should be Null ############################################################
        //########################################################################################
        softAssert.assertEquals(member.PostalCode_sf,"", "PostalCode (SF/Updated) : ");
        _scenario.write("==============PostalCode ============= ");
        _scenario.write("Actual PostalCode  (SF) : "+member.PostalCode_sf);
        _scenario.write("Expected PostalCode  (SF) : "+"");

        softAssert.assertEquals(member.CityName_sf,"", "CityNameText  (SF/Updated) : ");
        _scenario.write("==============CityNameText ============= ");
        _scenario.write("Actual CityNameText  (SF) : "+member.CityName_sf);
        _scenario.write("Expected CityNameText  (SF) : "+"");

        softAssert.assertEquals(member.FirstNameNative_sf,"", "FirstNameNative (SF/Updated) : ");
        _scenario.write("==============FirstNameNative ============= ");
        _scenario.write("Actual FirstNameNative : "+member.FirstNameNative_sf);
        _scenario.write("Expected FirstNameNative : "+"");

        softAssert.assertEquals(member.LastNameNative_sf,"", "LastNameNative (SF/Updated) : ");
        _scenario.write("==============LastNameNative ============= ");
        _scenario.write("Actual LastNameNative  (SF) : "+member.LastNameNative_sf);
        _scenario.write("Expected LastNameNative  (SF) : "+"");

        softAssert.assertEquals(member.BirthDate_sf,"", "BirthDate (SF/Updated) : ");
        _scenario.write("==============BirthDate ============= ");
        _scenario.write("Actual BirthDate  (SF) : "+member.BirthDate_sf);
        _scenario.write("Expected BirthDate  (SF) : "+"");

        softAssert.assertEquals(member.BirthMonth_sf,"", "BirthMonth (SF/Updated) : ");
        _scenario.write("==============BirthMonth ============= ");
        _scenario.write("Actual BirthMonth  (SF) : "+member.BirthMonth_sf);
        _scenario.write("Expected BirthMonth  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress2_sf,"", "MailingAddress2  (SF/Updated) : ");
        _scenario.write("==============MailingAddress2 ============= ");
        _scenario.write("Actual MailingAddress2  (SF) : "+member.MailingAddress2_sf);
        _scenario.write("Expected MailingAddress2  (SF) : "+"");

        softAssert.assertEquals(member.ZipCode_sf,"", "ZipCode  (SF/Updated) : ");
        _scenario.write("==============ZipCode ============= ");
        _scenario.write("Actual ZipCode  (SF) : "+member.ZipCode_sf);
        _scenario.write("Expected ZipCode  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress1_sf,"", "MailingAddress1  (SF/Updated) : ");
        _scenario.write("==============MailingAddress1 ============= ");
        _scenario.write("Actual MailingAddress1  (SF) : "+member.MailingAddress1_sf);
        _scenario.write("Expected MailingAddress1  (SF) : "+"");

        softAssert.assertEquals(member.MailingAddress3_sf,"", "MailingAddress3  (SF/Updated) : ");
        _scenario.write("==============MailingAddress3 ============= ");
        _scenario.write("Actual MailingAddress3  (SF) : "+member.MailingAddress3_sf);
        _scenario.write("Expected MailingAddress3  (SF) : "+"");

        softAssert.assertEquals(member.ResCityNameText_sf,"", "ResCityNameText  (SF/Updated) : ");
        _scenario.write("==============ResCityNameText ============= ");
        _scenario.write("Actual ResCityNameText  (SF) : "+member.ResCityNameText_sf);
        _scenario.write("Expected ResCityNameText  (SF) : "+"");

        softAssert.assertEquals(member.ResStateNameText_sf,"", "ResStateNameText  (SF/Updated) : ");
        _scenario.write("==============ResStateNameText ============= ");
        _scenario.write("Actual ResStateNameText  (SF) : "+member.ResStateNameText_sf);
        _scenario.write("Expected ResStateNameText  (SF) : "+"");

        softAssert.assertEquals(member.ZipCodeValidFlag_sf,"", "ZipCodeValidFlag  (SF/Updated) : ");
        _scenario.write("==============ZipCodeValidFlag ============= ");
        _scenario.write("Actual ZipCodeValidFlag  (SF) : "+member.ZipCodeValidFlag_sf);
        _scenario.write("Expected ZipCodeValidFlag  (SF) : "+"");

        softAssert.assertEquals(member.MarketingSourceOthers_sf,"", "MarketingSourceOthers  (SF/Updated) : ");
        _scenario.write("==============MarketingSourceOthers ============= ");
        _scenario.write("Actual MarketingSourceOthers  (SF) : "+member.MarketingSourceOthers_sf);
        _scenario.write("Expected MarketingSourceOthers  (SF) : "+"");

        softAssert.assertEquals(member.CustomerLeisureActivity_sf,"", "CustomerLeisureActivity  (SF/Updated) : ");
        _scenario.write("==============CustomerLeisureActivity ============= ");
        _scenario.write("Actual CustomerLeisureActivity  (SF) : "+member.CustomerLeisureActivity_sf);
        _scenario.write("Expected CustomerLeisureActivity  (SF) : "+"");

        softAssert.assertEquals(member.CustomerShoppingPreference_sf,"", "CustomerShoppingPreference  (SF/Updated) :");
        _scenario.write("==============CustomerShoppingPreference ============= ");
        _scenario.write("Actual CustomerShoppingPreference  (SF) : "+member.CustomerShoppingPreference_sf);
        _scenario.write("Expected CustomerShoppingPreference  (SF) : "+"");

        softAssert.assertEquals(member.CustomerPreferredBrands_sf,"", "CustomerPreferredBrands  (SF/Updated) : ");
        _scenario.write("==============CustomerPreferredBrands ============= ");
        _scenario.write("Actual CustomerPreferredBrands  (SF) : "+member.CustomerPreferredBrands_sf);
        _scenario.write("Expected CustomerPreferredBrands  (SF) : "+"");

        softAssert.assertEquals(member.LeisureActivitiesMultiple_sf,"", "LeisureActivitiesMultiple  (SF/Updated) : ");
        _scenario.write("==============LeisureActivitiesMultiple ============= ");
        _scenario.write("Actual LeisureActivitiesMultiple  (SF) : "+member.LeisureActivitiesMultiple_sf);
        _scenario.write("Expected LeisureActivitiesMultiple  (SF) : "+"");

        softAssert.assertEquals(member.ShoppingPreferencesMultiple_sf,"", "ShoppingPreferencesMultiple  (SF/Updated) : ");
        _scenario.write("==============ShoppingPreferencesMultiple ============= ");
        _scenario.write("Actual ShoppingPreferencesMultiple  (SF) : "+member.ShoppingPreferencesMultiple_sf);
        _scenario.write("Expected ShoppingPreferencesMultiple  (SF) : "+"");

        softAssert.assertEquals(member.PreferredBrandsMultiple_sf,"", "PreferredBrandsMultiple  (SF/Updated) : ");
        _scenario.write("==============PreferredBrandsMultiple ============= ");
        _scenario.write("Actual PreferredBrandsMultiple  (SF) : "+member.PreferredBrandsMultiple_sf);
        _scenario.write("Expected PreferredBrandsMultiple  (SF) : "+"");

        //############################################ END OF NULL FIELD VALIDATION ###################################################

    }

    @Then("^I validate Updated ETR Membership Information with matrixDB$")
    public void iValidateUpdatedETRMembershipInformationWithMatrixDB() throws Throwable {

        System.out.println("==================== Validating fields In Matrix for Updated ETR Member  =======================");
        _scenario.write("==================== Validating fields In Matrix for Updated ETR Member =======================");

        softAssert.assertEquals(matrixData.get("Salutation"),ETRcreateMember.TitleCode, "TitleCode (Matrix/Updated) ");
        _scenario.write("==============TitleCode ============= ");
        _scenario.write("Actual TitleCode (Matrix/Updated) : "+matrixData.get("Salutation"));
        _scenario.write("Expected TitleCode  (Matrix/Updated) : "+ETRcreateMember.TitleCode);

        softAssert.assertEquals(matrixData.get("Gender"),ETRcreateMember.GenderCode, "Gender (Matrix/Updated) ");
        _scenario.write("==============GenderCode ============= ");
        _scenario.write("Actual GenderCode  (Matrix/Updated) : "+matrixData.get("Gender"));
        _scenario.write("Expected GenderCode  (Matrix/Updated) : "+ETRcreateMember.GenderCode);

        softAssert.assertTrue(matrixData.get("Email").equalsIgnoreCase(ETRcreateMember.EmailAddressText), "EmailAddressText (Matrix/Updated) ");
        _scenario.write("==============EmailAddressText ============= ");
        _scenario.write("Actual EmailAddressText  (Matrix/Updated) : "+matrixData.get("Email"));
        _scenario.write("Expected EmailAddressText  (Matrix/Updated) : "+ETRcreateMember.EmailAddressText);

        softAssert.assertEquals(matrixData.get("MobileNo"),ETRcreateMember.ContactNumberText, "ContactNumberText (Matrix/Updated) ");
        _scenario.write("==============ContactNumberText ============= ");
        _scenario.write("Actual ContactNumberText  (Matrix/Updated) : "+matrixData.get("MobileNo"));
        _scenario.write("Expected ContactNumberText  (Matrix/Updated) : "+ETRcreateMember.ContactNumberText);

        // IsInvalidEmail
        String IsInvalidEmail_status=null;
        if(ETRcreateMember.EmailAddressText.isEmpty()){
            IsInvalidEmail_status = "1";
        }else {
            IsInvalidEmail_status="0";
        }
        softAssert.assertEquals(matrixData.get("IsInvalidEmail"),IsInvalidEmail_status, "Valid Email Address(IsInvalidEmail) (Matrix/Updated) ");
        _scenario.write("==============Valid Email Address(IsInvalidEmail) ============= ");
        _scenario.write("Actual Valid Email Address(IsInvalidEmail)  (Matrix/Updated) : "+matrixData.get("IsInvalidEmail"));
        _scenario.write("Expected Valid Email Address(IsInvalidEmail)  (Matrix/Updated) : "+IsInvalidEmail_status);

        //IsEmptyEmail
        String isEmptyEmail_status=null;
        if( ETRcreateMember.EmailAddressText.isEmpty()){
            isEmptyEmail_status="1";
        } else {
            isEmptyEmail_status="0";
        }

        softAssert.assertEquals(matrixData.get("IsEmptyEmail"),isEmptyEmail_status, "IsEmptyEmail (Matrix/Updated) ");
        _scenario.write("==============IsEmptyEmail ============= ");
        _scenario.write("Actual IsEmptyEmail  (Matrix/Updated) : "+matrixData.get("IsEmptyEmail"));
        _scenario.write("Expected IsEmptyEmail  (Matrix/Updated) : "+isEmptyEmail_status);

        // FirstName
        softAssert.assertEquals(matrixData.get("FirstName"),ETRcreateMember.FirstName, "FirstName (Matrix/Updated) ");
        _scenario.write("==============FirstName ============= ");
        _scenario.write("Actual FirstName  (Matrix/Updated) : "+matrixData.get("FirstName"));
        _scenario.write("Expected FirstName  (Matrix/Updated) : "+ETRcreateMember.FirstName);

        //LastName
        softAssert.assertEquals(matrixData.get("LastName"),ETRcreateMember.LastName, "LastName (Matrix/Updated) ");
        _scenario.write("==============LastName ============= ");
        _scenario.write("Actual LastName  (Matrix/Updated) : "+matrixData.get("LastName"));
        _scenario.write("Expected LastName  (Matrix/Updated) : "+ETRcreateMember.LastName);

        //NativeSalutation
         String NativeSalutationConverted = ETRcreateMember.convertNativeSalutation(environment,matrixData.get("NativeSalutation"));

        softAssert.assertEquals(matrixData.get("NativeSalutation"),ETRcreateMember.NativeSalutation, "NativeSalutation (Matrix/Updated) ");
        _scenario.write("==============NativeSalutation ============= ");
        _scenario.write("Actual NativeSalutation  (Matrix/Updated) : "+matrixData.get("NativeSalutation"));
        _scenario.write("Expected NativeSalutation  (Matrix/Updated) : "+ETRcreateMember.NativeSalutation);


        //ValidMobileNo1

        //value of ValidMobileNo1 will be "1" wheneve there is contactNumberText is provided (not null)
        String ValidMobileNo1_status=null;
        if(ETRcreateMember.ContactNumberText.isEmpty()){
            ValidMobileNo1_status ="0";
        } else {
            ValidMobileNo1_status="1";
        }

        softAssert.assertEquals(matrixData.get("ValidMobileNo1"), ValidMobileNo1_status, "ValidMobileNo1 (Matrix/Updated) ");
        _scenario.write("==============ValidMobileNo1 ============= ");
        _scenario.write("Actual ValidMobileNo1  (Matrix/Updated) : "+matrixData.get("ValidMobileNo1"));
        _scenario.write("Expected ValidMobileNo1  (Matrix/Updated) : "+ValidMobileNo1_status);

        //EnglishCountry
        softAssert.assertEquals(matrixData.get("EnglishCountry"),ETRcreateMember.EnglishCountry, "EnglishCountry (Matrix/Updated) ");
        _scenario.write("==============EnglishCountry ============= ");
        _scenario.write("Actual EnglishCountry  (Matrix/Updated) : "+matrixData.get("EnglishCountry"));
        _scenario.write("Expected EnglishCountry  (Matrix/Updated) : "+ETRcreateMember.EnglishCountry);

        //SpokenLanguage
        softAssert.assertEquals(matrixData.get("SpokenLanguageCode"),ETRcreateMember.SpokenLanguage, "SpokenLanguageCode (Matrix/Updated) ");
        _scenario.write("==============SpokenLanguage ============= ");
        _scenario.write("Actual SpokenLanguage  (Matrix/Updated) : "+matrixData.get("SpokenLanguageCode"));
        _scenario.write("Expected SpokenLanguage  (Matrix/Updated) : "+ETRcreateMember.SpokenLanguage);

        //IsContactable
        softAssert.assertEquals(matrixData.get("IsContactable"),ETRcreateMember.IsContactable, "IsContactable (Matrix/Updated) ");
        _scenario.write("==============IsContactable ============= ");
        _scenario.write("Actual IsContactable  (Matrix/Updated) : "+matrixData.get("IsContactable"));
        _scenario.write("Expected IsContactable  (Matrix/Updated) : "+ETRcreateMember.IsContactable);

        // #############################  More validations added as per the ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957 ############################
        // 1. Opt in Marketing & promotional (Phone)  ===================================================================

        // 1.1 isOptOutMobile1  --------------------------------------------------------------------
        String Expected_isOptOutMobile1=null;
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactablePhone_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableEmail_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableMail_sf.equalsIgnoreCase("1")) {
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
        if(sf.isContactableMail_sf.equalsIgnoreCase("1")) {
            Expected_isOptOutAddressOthers="0";
        } else {
            Expected_isOptOutAddressOthers="1";
        }

        softAssert.assertEquals(matrixData.get("IsOptOutAddressOthers"),Expected_isOptOutAddressOthers, "isOptOutAddressOthers (Matrix) ");
        _scenario.write("============== isOptOutAddressOthers ============= ");
        _scenario.write("Actual isOptOutAddressOthers (Matrix) : "+matrixData.get("IsOptOutAddressOthers"));
        _scenario.write("Expected isOptOutAddressOthers (Matrix) : "+Expected_isOptOutAddressOthers);
        // #############################  end of additional validations for ticket - https://dfsrtr.atlassian.net/browse/SFDC-1957  ############################

        //CardTier
        softAssert.assertEquals(matrixData.get("IssuedCardTier"),ETRcreateMember.CardTier, "IssuedCardTier (Matrix/Updated) ");
        _scenario.write("==============CardTier ============= ");
        _scenario.write("Actual CardTier  (Matrix/Updated) : "+matrixData.get("IssuedCardTier"));
        _scenario.write("Expected CardTier  (Matrix/Updated) : "+ETRcreateMember.CardTier);

        //CardPickupMethod
        softAssert.assertEquals(matrixData.get("CardPickupMethod"),ETRcreateMember.CardPickupMethod, "CardPickupMethod (Matrix/Updated) ");
        _scenario.write("==============CardPickupMethod ============= ");
        _scenario.write("Actual CardPickupMethod  (Matrix/Updated) : "+matrixData.get("CardPickupMethod"));
        _scenario.write("Expected CardPickupMethod  (Matrix/Updated) : "+ETRcreateMember.CardPickupMethod);


        //SourceSystem
        softAssert.assertEquals(matrixData.get("SourceSystem"),ETRcreateMember.SourceSystem, "SourceSystem (Matrix/Updated) ");
        _scenario.write("==============SourceSystem ============= ");
        _scenario.write("Actual SourceSystem  (Matrix/Updated) : "+matrixData.get("SourceSystem"));
        _scenario.write("Expected SourceSystem  (Matrix/Updated) : "+ETRcreateMember.SourceSystem);


        //RegistrationDivisionCode
        softAssert.assertEquals(matrixData.get("RegistrationDivisionCode"),ETRcreateMember.RegistrationDivisionCode, "RegistrationDivisionCode (Matrix/Updated) ");
        _scenario.write("==============RegistrationDivisionCode ============= ");
        _scenario.write("Actual RegistrationDivisionCode  (Matrix/Updated) : "+matrixData.get("RegistrationDivisionCode"));
        _scenario.write("Expected RegistrationDivisionCode  (Matrix/Updated) : "+ETRcreateMember.RegistrationDivisionCode);


        //RegistrationLocationID
        softAssert.assertEquals(matrixData.get("RegistrationLocationID"),ETRcreateMember.RegistrationLocationID, "RegistrationLocationID (Matrix/Updated) ");
        _scenario.write("==============RegistrationLocationID ============= ");
        _scenario.write("Actual RegistrationLocationID  (Matrix/Updated) : "+matrixData.get("RegistrationLocationID"));
        _scenario.write("Expected RegistrationLocationID  (Matrix/Updated) : "+ETRcreateMember.RegistrationLocationID);

        //CustomerRegistrationDatetime
        String customerRegistrationDateTimeMatrix="";
        try {
            customerRegistrationDateTimeMatrix = matrixData.get("CustomerRegistrationDatetime").substring(0,10);
        } catch (Exception e) {
            customerRegistrationDateTimeMatrix = "";
        }

        softAssert.assertEquals(customerRegistrationDateTimeMatrix,ETRcreateMember.CustomerRegistrationDatetime, "CustomerRegistrationDatetime (Matrix/Updated) ");
        _scenario.write("==============CustomerRegistrationDatetime ============= ");
        _scenario.write("Actual CustomerRegistrationDatetime  (Matrix/Updated) : "+customerRegistrationDateTimeMatrix);
        _scenario.write("Expected CustomerRegistrationDatetime  (Matrix/Updated) : "+ETRcreateMember.CustomerRegistrationDatetime);

        //AgeRange
        softAssert.assertEquals(matrixData.get("AgeRange"),ETRcreateMember.AgeRange, "AgeRange (Matrix/Updated) ");
        _scenario.write("==============AgeRange ============= ");
        _scenario.write("Actual AgeRange  (Matrix/Updated) : "+matrixData.get("AgeRange"));
        _scenario.write("Expected AgeRange  (Matrix/Updated) : "+ETRcreateMember.AgeRange);

        //Mobile1AreaCode
        softAssert.assertEquals(matrixData.get("MobileAreaCode"),ETRcreateMember.Mobile1AreaCode, "MobileAreaCode  (Matrix/Updated) ");
        _scenario.write("==============MobileAreaCode ============= ");
        _scenario.write("Actual MobileAreaCode  (Matrix) : "+matrixData.get("MobileAreaCode"));
        _scenario.write("Expected MobileAreaCode  (Matrix) : "+ETRcreateMember.Mobile1AreaCode);


        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }
    }

    @Then("^I search updated ETR member using card number$")
    public void iSearchUpdatedETRMemberUsingCardNumber(DataTable dataTable) throws Throwable {
        ETRcreateMember.getElementValuesFromUpdateETRJsonFile(environment);
        cardNumber = ETRcreateMember.CardNo;

        searchMember_pageobjects2 = new SearchMember_pageobjects(driver,_scenario);

        searchMember_pageobjects2.searchMemberThroughCardNumber(dataTable,cardNumber);
    }


    @Then("^I check for assertion failures during ETR create Member$")
    public void iCheckForAssertionFailuresDuringETRCreateMember() throws Throwable {
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            _scenario.write("TEST FAILED DUE TO ASSERTION FAILURES. "+e.getMessage());
            Assert.fail();
        }
    }

    @Then("^I validate the memberID in salesforce and Matrix for ETR Updated member$")
    public void iValidateTheMemberIDInSalesforceAndMatrixForETRUpdatedMember() throws Throwable {
        //getting the access token
        token = accessToken.getSFaccessToken();

        //validate member id from sf and matrix
        validateMemberIDinSFandMatrix.validateMemberIDAfterUpdate(token,cardNumber,environment);
    }
}
