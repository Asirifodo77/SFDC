package page_objects;


//import com.sun.tools.javac.jvm.Gen;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.JsonObject;
import commonLibs.implementation.*;
import cucumber.api.Scenario;
import okhttp3.*;
import org.apache.poi.util.SystemOutLogger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import step_definitions.DataBase_connection;
import utilities.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;

public class MemberValidation_pageobjects {

    public WebDriver driver;
    public Scenario _scenario;
    public selectBoxControls selectBox;
    public textBoxControls textBox;
    public checkBoxControls checkBox;
    public CommonElements comElement;
    public Login_pageobjects login_pageobjects;
    public TakeScreenshot screenshot;
    public stringFunctions stringFunctions;
    public JavaScriptExecutor javaScriptExecutor;

    //String variables to store SF values
    public String FirstName_sf = "";
    public String LastName_sf = "";
    public String Nationality_sf = "";
    public String EnglishCountry_sf = "";
    public String OtherPhone2_sf = "";
    public String OtherPhone1_sf = "";
    public String ContactNumberText_sf = "";
    public String EmailAddressText_sf = "";
    public String Salutation_sf = "";
    public String Gender_sf = "";
    public String IsInvalidEmail_sf = "";
    public String IsEmptyEmail_sf = "";
    public String NativeSalutation_sf = "";
    public String Mobile1AreaCode_sf = "";
    public String ValidMobileNo1_sf = "";
    public String SpokenLanguage_sf = "";
    public String IsContactable_sf = "";
    public String CardTier_sf = "";
    public String CustomerRegistrationDatetime_sf = "";
    public String AgeRange_sf = "";
    public String MemberStatus_sf = "";
    public String cardStatus_sf = "";
    public String LastNameNative_sf = "";
    public String FirstNameNative_sf = "";
    public String MailingAddress1_sf = "";
    public String MailingAddress2_sf = "";
    public String MailingAddress3_sf = "";
    public String CycleStartDate_sf = "";
    public String CycleStartDate_sf_formattedDate = "";
    public String CycleEndDate_sf = "";
    public String CycleEndDate_sf_formattedDate = "";
    public String statusDollarToUpgrade_sf = "";
    public String statusDollarToRenew_sf = "";
    public String statusDollarToRenew_sf_formatted = "";
    public String mem_Card_Num_sf = "";
    public String enroll_Location_sf = "";
    public String enroll_Date_sf = "";
    public String entry_Status_doll_sf = "";
    public String first_Purchace_Date_sf = "";
    public String tier_Status_Doll_sf = "";
    public String data_Source_Enrolment_sf = "";
    public String pointBalance_sf = "";
    public String carryForwardDollarAmount_sf = "";
    public String PostalCode_sf = "";
    public String CityName_sf = "";
    public String EmailUnwillingToProvide_sf = "";
    public String BirthDate_sf = "";
    public String BirthMonth_sf = "";
    public String ZipCode_sf = "";
    public String ResCityNameText_sf = "";
    public String ResStateNameText_sf = "";
    public String MarketingSource_sf = "";
    public String ZipCodeValidFlag_sf = "";
    public String MarketingSourceOthers_sf = "";
    public String CustomerLeisureActivity_sf = "";
    public String CustomerShoppingPreference_sf = "";
    public String CustomerPreferredBrands_sf = "";
    public String LeisureActivitiesMultiple_sf = "";
    public String ShoppingPreferencesMultiple_sf = "";
    public String PreferredBrandsMultiple_sf = "";
    public String MemberCardStatus_sf = "";
    public String memberPoints_MembershipTier_sf = "";
    public String Mem_points_Current_status_Dollar_MembershipTier_sf = "";
    public String weChatId_sf = "";
    public int rowCountOfPurchaseHistoryTrancactionTableRow;
    public SoftAssert softAssert;
    public String access_token;
    public String cycleID;
    public List<String> status_INUSD_c_List = null;
    public List<String> transaction_Member_Cycle__c = null;
    public int renew_TierStatusDollar;
    public String renew_EnrollmentDate;
    public String renew_FirstPurchaseDate;
    public int renew_carryForwardDollar;
    public String renew_currentTier;
    public String renew_EntryStatusDollar;
    public String isContactablePhone_sf;
    public String isContactableMail_sf;
    public String isContactableEmail_sf;
    public String titleText_sf;
    public String OtherPhone1AreaCode_sf="";
    public String OtherPhone2AreaCode_sf="";
    public String ValidOtherPhone1_sf="";
    public String ValidOtherPhone2_sf="";
    public String IsInvalidAddress_sf="";
    public String expiryDate_sf="";


    public MemberValidation_pageobjects(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this._scenario = scenario;
        PageFactory.initElements(driver, this);
        selectBox = new selectBoxControls(driver, _scenario);
        textBox = new textBoxControls(_scenario);
        comElement = new CommonElements(driver, _scenario);
        checkBox = new checkBoxControls(_scenario);
        login_pageobjects = new Login_pageobjects(driver, _scenario);
        screenshot = new TakeScreenshot(driver, _scenario);
        softAssert = new SoftAssert();
        stringFunctions = new stringFunctions();
        javaScriptExecutor = new JavaScriptExecutor(driver);
    }

    @FindBy(xpath = "//*[contains(text(),'Member Cycle')]")
    public WebElement member_cycle;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Go to Top']")
    public WebElement goToTop_Button;

    @FindBy(how= How.CSS, using = "fieldset.slds-form-element > .slds-form-element__control.slds-m-bottom_small:nth-of-type(1) > .iconPointer > label.iconPointer")
    public WebElement transactionAssociationLink_link;

    public static final By enrollment_location = By.cssSelector(".slds-form > .slds-form-element.slds-p-horizontal_small.slds-size_1-of-2.setPadding:nth-of-type(3) > span > span > .slds-form-element.slds-has-divider_bottom > .slds-form-element__control > .slds-form-element__static > span");

    //Member- Membership Information

    //Membership status & Card Status
    public static final By Membership_Status = By.xpath("//*[@id=\"brandBand_1\"]/div/div[1]/div/div[1]/div/div[1]/div/header/div[2]/ul/li[4]/div/div/div");
    public static final By Card_Status = By.xpath("//*[@id=\"brandBand_1\"]/div/div[1]/div/div[1]/div/div[1]/div/header/div[2]/ul/li[5]/div/div/div");

    //Membership Tier
    @FindBy(how = How.XPATH, using = "//*[@id='brandBand_1']/div/div[1]/div/div/div/div[2]/div[2]/div[1]/div/div/div[3]/div/p/b")
    public WebElement Mem_points;

    @FindBy(how = How.CSS, using = "#brandBand_1 > div > div.center.oneCenterStage.lafSinglePaneWindowManager > div.windowViewMode-normal.oneContent.active.lafPageHost > div.flexipagePage.oneRecordHomeFlexipage > div > div.row.row-main > div.column.region-sidebar-right > div:nth-child(1) > div > div > div:nth-child(1) > dl > dd:nth-child(2) > div > b")
    public WebElement Mem_points_Current_status_Dollar;


    //Purchase History
    public static final By Membership_Dummy_Table = By.xpath("html/body/div[5]/div[1]/section/div[2]/div/div[1]/div/div[1]/div/div[2]/div[1]/div/div/section[2]/div/div/section/div/div/div[2]/article/div/div/div/div/table/tbody");
    public static final By Membership_profile_Table = By.cssSelector(".slds-table.slds-table_bordered.slds-no-row-hover.slds-table_cell-buffer>tbody");

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Purchase History')]")
    public WebElement PurchaseHistory;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Member Requests')]")
    public WebElement memberRequests;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Date/Time Opened')]/ancestor::table")
    public WebElement memberRequestTable;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Suspend Card Request')]/ancestor::tbody//td[1]/a")
    public WebElement suspendCardRequest_link;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Suspend Card Request')]")
    public WebElement suspendCardRequest_link_a;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Blacklist')]/ancestor::form/button[2]")
    public WebElement blackList_Button;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Resume')]/ancestor::form/button[1]")
    public WebElement resume_Button;

    @FindBy(how = How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Confirm']")
    public WebElement confirm_Button;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Approve')]/ancestor::li/a/div")
    public WebElement approve_Button;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Comments')]/ancestor::div[1]/textarea")
    public WebElement approve_comments_textArea;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Approve')]/ancestor::div[1]/button[2]")
    public WebElement approveButton_comments_textArea;

    //Membership Card Tab
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Membership Card')]")
    public WebElement membershipCard_Tab;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Supplementary')]/ancestor::tr/td[1]/span")
    public WebElement notDummyCardTableElement;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Membership Card Number')]/ancestor::table")
    public WebElement membershipCardTable;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Selling Location Name')]/ancestor::table//tbody")
    public WebElement purchaseHistoryTransactionsTable;

    ///*[contains(text(),'Selling Location Name')]/ancestor::table//tbody


    public static final By profile = By.id("//*[contains(text(),'Profile')]");

    @FindBy(xpath = "//iframe[contains(@src, 'Basic')]")
    public WebElement mainFrame;

    @FindBy(xpath = "//iframe[contains(@src,'Contact Preferences')]")
    public WebElement iframeContactPreferences;

    @FindBy(xpath = "//iframe[contains(@src,'Shopping&section1')]")
    public WebElement iframeShoppingPreferences;

    @FindBy(xpath = "//iframe[contains(@src, 'Membership')]")
    public WebElement iframeMemberCycle;


    @FindBy(xpath = "//span[@class='slds-form-element__label slds-truncate' and text()='Membership Status']//..//div[@class='slds-form-element__control']//div[@class='slds-form-element__static slds-truncate']//span")
    public WebElement MemberStatus;


    @FindBy(css = "#brandBand_1 > div > div.center.oneCenterStage.lafSinglePaneWindowManager > div > div.flexipagePage.oneRecordHomeFlexipage > div > div.row.region-header > div > header > div.slds-page-header.slds-page-header_record-home.s1FixedTop.forceHighlightsStencilDesktop.forceRecordLayout > ul > li:nth-child(5) > div > div > div > span")
    public WebElement MembershipcardStatusCode;

    @FindBy(xpath = "//*[@id=\"brandBand_1\"]/div/div[1]/div/div[1]/div/div[1]/div/header/div[2]/ul/li[5]/div/div/div")
    public WebElement cardStatus;


    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:0:j_id40:j_id41:selPicklist")
    public WebElement Salutation;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:7:j_id40:j_id41:selPicklist")
    public WebElement GenderCode;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:12:j_id40:j_id41:txtInput")
    public WebElement EmailAddressText;

    //POSTAL CODE
    @FindBy(xpath = "//label[contains (text(), 'Postal Code (Mailing Address)')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement PostalCode;

    //Point Balance
    @FindBy(css = "#brandBand_1 > div > div.center.oneCenterStage.lafSinglePaneWindowManager > div > div.flexipagePage.oneRecordHomeFlexipage > div > div.row.row-main > div.column.region-sidebar-right > div:nth-child(1) > div > div > div:nth-child(3) > div > p > b > lightning-formatted-number")
    public WebElement PointBalance;


    //city name
    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:20:j_id40:j_id41:selPicklist")
    public WebElement CityNameText;

    // COUNTRY NAME TEXT --NOT THERE IN SF


    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:1:j_id40:j_id41:txtInput")
    public WebElement ContactNumberText;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:29:j_id40:j_id41:checkBox")
    public WebElement IsInvalidAddress;

    @FindBy(xpath = "//span[text()='Valid Email Address']//..//..//div//input")
    public WebElement IsInvalidEmail;

    //IsEmptyEmail value has to be checked by checking EmailAddressText , if empty 1,  else 0

    @FindBy(xpath = "//span[text()='Email (Unwilling to Provide)']//..//..//div//input")
    public WebElement UnwillingToProvideEmail;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:2:j_id40:j_id41:txtInput")
    public WebElement FirstName;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:5:j_id40:j_id41:txtInput")
    public WebElement FirstNameNative;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:4:j_id40:j_id41:txtInput")
    public WebElement LastName;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:3:j_id40:j_id41:txtInput")
    public WebElement LastNameNative;

    @FindBy(xpath = "//label[@class='slds-form-element__label' and contains(text(),'Birthdate (Day)')]//..//div[@class='slds-form-element__control']//select")
    public WebElement BirthDate;

    @FindBy(xpath = "//label[@class='slds-form-element__label' and contains(text(),'Birthdate (Month)')]//..//div[@class='slds-form-element__control']//select")
    public WebElement BirthMonth;

    @FindBy(xpath = "//label[contains (text(), 'Mailing Address Line 2')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement MailingAddress2;

    @FindBy(xpath = "//label[contains (text(), 'Postal Code (Mailing Address)')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement ZipCode;

    @FindBy(xpath = "//label[contains (text(), 'Mailing Address Line 1')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement MailingAddress1;

    @FindBy(xpath = "//label[contains (text(), 'Mailing Address Line 3')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement MailingAddress3;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:20:j_id40:j_id41:selPicklist")
    public WebElement ResCityNameText;

    @FindBy(xpath = "//label[contains (text(), 'State/Province (Mailing Address)')]//..//div//span//select[@class=' slds-select']")
    public WebElement ResStateNameText;

    @FindBy(xpath = "//label[contains (text(), 'Location of Residence (Mailing Address)')]//..//div[@class='slds-form-element__control']//select[@class='slds-select']")
    public WebElement EnglishCountry;

    // 1 = valid / 0 = invalid
    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:3:j_id40:j_id41:checkBox")
    public WebElement ValidMobileNoCheckbox;

    // Inside "contact preferences" tab
    @FindBy(xpath = "//div[@class='tabset slds-tabs_card uiTabset--base uiTabset--default uiTabset--dense uiTabset flexipageTabset']//div[@class='uiTabBar']//ul[@class='tabs__nav']//li[@class='tabs__item uiTabItem']//a[@class='tabHeader']//span[@class='title' and text()='Contact Preferences']")
    public WebElement ContactPreferencesTab;

    @FindBy(xpath = "//label[contains(text(),'Preferred Language')]//ancestor::div[1]//div[1]//select")
    //    j_id0:j_id7:theRepeat:0:theRepeat:0:j_id40:j_id41:selPicklist
    public WebElement SpokenLanguage;

    // if all below checkboxes are unticked, then IsContactable = 0 else if all are checked its 1
    @FindBy(css = "#j_id0\\3a j_id7\\3a theRepeat\\3a 1\\3a theRepeat\\3a 0\\3a j_id40\\3a j_id41\\3a checkBox")
    public WebElement OptInPhoneCheckbox;

    @FindBy(css = "#j_id0\\3a j_id7\\3a theRepeat\\3a 1\\3a theRepeat\\3a 2\\3a j_id40\\3a j_id41\\3a checkBox")
    public WebElement OptInMailCheckbox;

    @FindBy(css = "#j_id0\\3a j_id7\\3a theRepeat\\3a 1\\3a theRepeat\\3a 1\\3a j_id40\\3a j_id41\\3a checkBox")
    public WebElement OptInEmailCheckbox;

    // Inside "Member cycle" tab
    @FindBy(xpath = "//span[@class='title' and text()='Member Cycle']")
    public WebElement MemberCycleTab;

    @FindBy(css = "#j_id0\\3a j_id7\\3a theRepeat\\3a 0\\3a theRepeat\\3a 3\\3a j_id40\\3a j_id41\\3a j_id66")
    public WebElement CustomerRegistrationDatetime;

    @FindBy(xpath = "//span[@class='title' and text()='Shopping Preferences']")
    public WebElement ShoppingPreferencesTab;

    @FindBy(xpath = "//*[contains(text(),'Leisure Activities Other')]//ancestor::div[1]//div[1]//input")
    public WebElement CustomerLeisureActivity;

    @FindBy(xpath = "//*[contains(text(),'Shopping Preferences Other')]//ancestor::div[1]//div[1]//input")
    public WebElement CustomerShoppingPreference;

    @FindBy(xpath = "//*[contains(text(),'Preferred Brands Other')]//ancestor::div[1]//div[1]//input")
    public WebElement CustomerPreferredBrands;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:1:j_id40:j_id41:Picklist_selected")
    public WebElement LeisureActivitiesMultiple;

    @FindBy(xpath = "//select[@id='j_id0:j_id7:theRepeat:1:theRepeat:1:j_id40:j_id41:Picklist_selected']/option")
    public List<WebElement> LeisureActivitiesMultipleList;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:1:j_id40:j_id41:Picklist_selected")
    public WebElement ShoppingPreferencesMultiple;

    @FindBy(xpath = "//select[@id='j_id0:j_id7:theRepeat:0:theRepeat:1:j_id40:j_id41:Picklist_selected']/option")
    public List<WebElement> ShoppingPreferencesMultipleList;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:0:j_id40:j_id41:Picklist_selected")
    public WebElement PreferredBrandsMultiple;

    @FindBy(xpath = "//select[@id='j_id0:j_id7:theRepeat:0:theRepeat:0:j_id40:j_id41:Picklist_selected']/option")
    public List<WebElement> PreferredBrandsMultipleList;

    @FindBy(xpath = "//label[contains (text(), 'Age Range')]//..//div[@class='slds-form-element__control']//select[@class='slds-select']")
    public WebElement AgeRange;

    @FindBy(xpath = "//label[contains (text(), 'Marketing Source')]//..//div[@class='slds-form-element__control']//select[@class='slds-select']")
    public WebElement MarketingSource;

    @FindBy(xpath = "//span[contains (text(), 'Valid Postal code')]//../..//div[@class='slds-form-element__control']//input")
    public WebElement ZipCodeValidFlag;

    @FindBy(xpath = "//span[contains(text(), 'Current Membership Tier')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement CardTier;

    @FindBy(xpath = "//span[contains(text(), 'Carry Forward Status Dollars')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement CarryForwardDollarUSDAmount;

    @FindBy(xpath = "//span[contains(text(), 'Cycle Start Date')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement CycleStartDate;

    @FindBy(xpath = "//span[contains(text(), 'Cycle End Date')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement CycleEndDate;

    @FindBy(xpath = "//span[contains(text(), 'Status Dollars Required to Renew')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement StatusDollarToRenew;

    @FindBy(xpath = "//span[contains(text(), 'Status Dollars Required to Upgrade')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement StatusDollarToUpgrade;

    @FindBy(xpath = "//span[contains(text(), 'Membership Card Number')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Mem_Card_Num;

    @FindBy(xpath = "//span[contains(text(), 'Enrollment Location')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Enroll_Location;

    @FindBy(xpath = "//span[contains(text(), 'Enrollment Date')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Enroll_Date;

    @FindBy(xpath = "//span[contains(text(), 'Entry Status Dollars')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Entry_Status_doll;

    @FindBy(xpath = "//span[contains(text(), 'First Purchase Date')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement First_Purchace_Date;

    @FindBy(xpath = "//span[contains(text(), 'Tier Status Dollars')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Tier_Status_Doll;

    @FindBy(xpath = "//span[contains(text(), 'Data Source (Enrollment)')]//ancestor::div[@class='slds-form-element slds-has-divider_bottom']//div//span/span")
    public WebElement Data_Source_Enrolment;

    @FindBy(xpath = "//label[contains (text(), 'Marketing Source Other')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement MarketingSourceOthers;

    //=============

    @FindBy(xpath = "//label[contains( text(),'Dialing Code (Mobile Phone)')]//..//div//select")
    public WebElement Mobile1AreaCode;

    @FindBy(xpath = "//label[contains( text(),'Dialing Code (Other Phone 1)')]//..//div//select")
    public WebElement OtherPhone1AreaCode;

    @FindBy(xpath = "//label[contains( text(),'Dialing Code (Other Phone 2)')]//..//div//select")
    public WebElement OtherPhone2AreaCode;

    @FindBy(xpath = "//span[contains( text(), 'Valid Other Phone 1')]//..//..//div//input")
    public WebElement ValidOtherPhone1CheckBox;

    @FindBy(xpath ="//span[contains( text(), 'Valid Other Phone 2')]//..//..//div//input")
    public WebElement ValidOtherPhone2CheckBox;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:1:j_id40:j_id41:selPicklist")
    public WebElement NativeSalutation;

    @FindBy(xpath = "//span[text()='Valid Mobile Phone']//..//..//div//input")
    public WebElement ValidMobileNo1;

    //@FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:16:j_id40:j_id41:selPicklist")
    // public WebElement EnglishCountry;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:0:j_id40:j_id41:checkBox")
    public WebElement OptInPhone;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:2:j_id40:j_id41:checkBox")
    public WebElement OptInMail;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:1:j_id40:j_id41:checkBox")
    public WebElement OptInEmail;

    @FindBy(id = "j_id0:j_id7:theRepeat:0:theRepeat:11:j_id40:j_id41:selPicklist")
    public WebElement Nationality;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:5:j_id40:j_id41:txtInput")
    public WebElement OtherPhone1;

    @FindBy(id = "j_id0:j_id7:theRepeat:1:theRepeat:9:j_id40:j_id41:txtInput")
    public WebElement OtherPhone2;

    @FindBy(xpath = "//label[contains (text(), 'WeChat ID')]//..//div[@class='slds-form-element__control']//input[@class='slds-input']")
    public WebElement WeChatID;

    @FindBy(xpath = "//iframe[contains(@src, 'Basic Information')]")
    public WebElement basicInfo_iFrame;

    @FindBy(how = How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Card Kept')]/ancestor::div[2]/div[2]/span")
    public WebElement mergeRequest_CardKept;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Card Cancelled')]/ancestor::div[2]/div[2]/span")
    public WebElement mergeRequest_CardCancelled;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Expected Points Balance')]/ancestor::div[2]/div[2]/span")
    public WebElement mergeRequest_pointsBalance;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Status Dollars Transferred')]/ancestor::div[2]/div[2]/span")
    public WebElement mergeRequest_DollarsTransferred;

    @FindBy(xpath = "//span[@title='Co-brand Credit Card']//..//div[@class='slds-form-element__control']//div//span//img")
    public WebElement CobrandIndicatorCheckboxImage;

    @FindBy(how = How.XPATH, using = "//div[@class='forceVirtualActionMarker forceVirtualAction']")
    public WebElement membershipCycleMoreViewDropDown;

    @FindBy(how = How.XPATH, using = "//div[@class='branding-actions actionMenu popupTargetContainer uiPopupTarget uiMenuList forceActionsDropDownMenuList uiMenuList--left uiMenuList--default visible positioned']/div/ul/li[1]/a")
    public WebElement membershipCycleMoreViewDropDown_EditLink;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'In Grace Period')]/ancestor::div[1]/input")
    public WebElement membershipCycle_gracePeriodCheckBox;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Exception Renewal')]/ancestor::div[1]/input")
    public WebElement membershipCycle_ExceptionalRenewalCheckBox;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'End Date')]/ancestor::div[2]/div[2]/span/span")
    public WebElement membershipCycle_CycleEndDate;

    @FindBy(how = How.XPATH, using = "//div[@class='container forceRelatedListSingleContainer']")
    public WebElement membershipCycle_TableElement;

    @FindBy(xpath = "//nav[@class='entityNameTitle']//..//div//span")
    public WebElement titleTextLabel;

    @FindBy(xpath = "//div[@id='cmdButtonsBottom']/input[@value='Save']")
    public WebElement SaveButtonInSF;

    @FindBy(xpath = "//label[contains(text(), 'Email')]")
    public WebElement whiteSpace;

    @FindBy(xpath = "//*[contains(text(),'Tax Amount (Local/Currency)')]/ancestor::div[2]/div[2]/span/span")
    public WebElement taxAmountLocalCurrency_text;

    @FindBy(xpath = "//*[contains(text(),'Tax Amount (USD)')]/ancestor::div[2]/div[2]/span/span")
    public WebElement taxUSDAmountLocalCurrency_text;

    @FindBy(xpath = "//*[contains(text(),'SKU Details')]")
    public WebElement SKUDetails_text;

    @FindBy(xpath = "//*[contains(text(),'Payments')]")
    public WebElement paymentsDetails_text;

    @FindBy(xpath = "//*[contains(text(),'External ID')]/ancestor::table/tbody/tr/th/div/a")
    public WebElement externalID_linktext;

    @FindBy(xpath = "//*[contains(text(),'Amount In Foreign Currency')]/ancestor::div[2]/div[2]/span/span")
    public WebElement AmountInFC_text;

    @FindBy(xpath = "//*[contains(text(),'Amount In Local Currency')]/ancestor::div[2]/div[2]/span/span")
    public WebElement AmountInLC_text;

    @FindBy(xpath = "//*[contains(text(),'Code')]/ancestor::div[2]/div[2]/span/span")
    public WebElement Code_text;

    @FindBy(xpath = "//*[contains(text(),'CreditCardNumber')]/ancestor::div[2]/div[2]/span/span")
    public WebElement CreditCardNumber_text;

    @FindBy(xpath = "//*[contains(text(),'ForeignCurrencyDescription')]/ancestor::div[5]/div[5]/div[1]/div[1]/div[2]/span/span")
    public WebElement Description_text;

    @FindBy(xpath = "//*[contains(text(),'ForeignCurrencyDescription')]/ancestor::div[2]/div[2]/span/span")
    public WebElement ForeignCurrencyDescription_text;

    @FindBy(xpath = "//*[contains(text(),'Type')]/ancestor::div[2]/div[2]/span/span")
    public WebElement Type_text;

    @FindBy(xpath = "//*[contains(text(),'Product Name')]/ancestor::table/tbody/tr/td[2]/div")
    public WebElement skuProductName_text;

    @FindBy(xpath = "//*[contains(text(),'Units')]/ancestor::table/tbody/tr/td[6]/div")
    public WebElement skuUnits_text;

    @FindBy(xpath = "//*[contains(text(),'Net Amount without Tax (USD)')]/ancestor::table/tbody/tr/td[7]/div")
    public WebElement skuNetAmountUSD_text;

    @FindBy(xpath = "//*[contains(text(),'Net Amount without Tax (Local)')]/ancestor::table/tbody/tr/td[8]/div")
    public WebElement skuNetAmountLocal_text;

    @FindBy(xpath = "//*[contains(text(),'Product Name')]/ancestor::table/tbody/tr[2]/td[2]/div")
    public WebElement skuProductName_text1;

    @FindBy(xpath = "//*[contains(text(),'Units')]/ancestor::table/tbody/tr[2]/td[6]/div")
    public WebElement skuUnits_text1;

    @FindBy(xpath = "//*[contains(text(),'Net Amount without Tax (USD)')]/ancestor::table/tbody/tr[2]/td[7]/div")
    public WebElement skuNetAmountUSD_text1;

    @FindBy(xpath = "//*[contains(text(),'Net Amount without Tax (Local)')]/ancestor::table/tbody/tr[2]/td[8]/div")
    public WebElement skuNetAmountLocal_text1;

    @FindBy(xpath = "//dd[contains(text(),'Expiry Date')]//ancestor::dl//dd//b//span")
    public WebElement ExpiryDate;

    //Contact Information
    public static final By profile_Preferences = By.xpath("//*[contains(text(),'Contact Preferences')]");
    //public static final By SpokenLanguage = By.id("j_id0:j_id7:theRepeat:0:theRepeat:0:j_id40:j_id41:selPicklist");

    //Title Code (Salutation) ===============================
    public void getTitleCode() throws Exception {
        String Mem_Salutation = null;
        try {
            Mem_Salutation = selectBox.getDefaultText(Salutation);
        } catch (NoSuchElementException e) {
            System.out.println("Salutation not found");
        }

        if (Mem_Salutation.isEmpty() || Mem_Salutation == "" || Mem_Salutation == "--None--") {
            Salutation_sf = "";
            _scenario.write("========Salutation=========");
            System.out.println("The Salutation is not found in SF: ");
            _scenario.write("The Salutation is not found in SF : ");

        } else {
            Salutation_sf = Mem_Salutation;
            _scenario.write("========Salutation=========");
            System.out.println("The Salutation in SF : " + Mem_Salutation);
            _scenario.write("The Salutation in SF : " + Mem_Salutation);
        }
    }

    //Gender Code ===============================
    public void getGenderCode() throws Exception {
        String gender = selectBox.getDefaultText(GenderCode);
        /*Runtime_TestData.gender = gender;
        System.out.println("The gender is : " + gender );*/
        if (gender.equalsIgnoreCase("Female")) {
            Gender_sf = "F";
            System.out.println("The Gender in SF : " + Gender_sf);
            _scenario.write("The Gender in SF : " + Gender_sf);
        } else if (gender.equalsIgnoreCase("Male")) {
            Gender_sf = "M";
            System.out.println("The Gender in SF : " + Gender_sf);
            _scenario.write("The Gender in SF : " + Gender_sf);
        } else {
            _scenario.write("========Gender=========");
            _scenario.write(" No acceptable value for Gender in SF. Actual Value : " + gender);
            System.out.println("No acceptable value for Gender in SF. Actual Value : " + gender);
        }

    }

    //Email Address Text ===============================
    public void getEmailAddressText() throws Exception {
        String emailAddressText = textBox.getText(EmailAddressText);
        if (emailAddressText.isEmpty() || emailAddressText == "") {
            EmailAddressText_sf = "";
            _scenario.write("========emailAddressText=========");
            System.out.println("emailAddressText is not found in SF ");
            _scenario.write("emailAddressText is not found in SF");
        } else {
            EmailAddressText_sf = emailAddressText;
            System.out.println("emailAddressText value in SF : " + emailAddressText);
            _scenario.write("emailAddressText value in SF : " + emailAddressText);
        }

    }

    public void setEmailAddressText(String updatedVal) throws Exception {
        try {
            textBox.setText(EmailAddressText,updatedVal);
        } catch (Exception e) {
            System.out.println("Unable to set text to 'Email Address'");
            throw e;
        }
    }

    public void getMobile1AreaCode() throws Exception {
        String mobile1AreaCode = null;
        try {
            mobile1AreaCode = stringFunctions.trimStringBeforeOpenBracket(selectBox.getDefaultText(Mobile1AreaCode));
                    //selectBox.getDefaultText(Mobile1AreaCode).substring(0, 4).replaceAll("\\(", "").trim();
        } catch(InterruptedException ie){
            System.out.println(ie.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Invalid format found in 'Dialing Code (Mobile Phone)' field. "+ie.getMessage());

        }catch (NullPointerException e) {
            System.out.println("Dialing Code (Mobile Phone) value is Null");
            mobile1AreaCode ="";
        }

        if (mobile1AreaCode.isEmpty() || mobile1AreaCode == "" || selectBox.getDefaultText(Mobile1AreaCode).trim().equalsIgnoreCase("--None--")) {
            Mobile1AreaCode_sf = "";
            _scenario.write("========mobile1AreaCode=========");
            _scenario.write(" mobile1AreaCode is NOT found in SF");
            System.out.println("mobile1AreaCode is not available in SF");
        } else {
            Mobile1AreaCode_sf = mobile1AreaCode;
            _scenario.write("mobile1AreaCode in SF : " + mobile1AreaCode);
            System.out.println("mobile1AreaCode in SF : " + mobile1AreaCode);
        }
    }

    public void setMobile1AreaCode(String updatedValue) throws Exception {
        try {
            //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",Mobile1AreaCode);
            //comElement.click(Mobile1AreaCode);

            //comElement.moveMouseAndClick(Mobile1AreaCode);
            selectBox.selectByVisibleText(Mobile1AreaCode, updatedValue);

        } catch (Exception e) {
            System.out.println("Unable to set value in 'Dialing Code (Mobile Phone)' in SF ");
            throw e;
        }
    }

    public void getOtherPhone1AreaCode() throws Exception {
        String otherPhone1AreaCode = null;
        try {
            otherPhone1AreaCode = stringFunctions.trimStringBeforeOpenBracket(selectBox.getDefaultText(OtherPhone1AreaCode));
                    //selectBox.getDefaultText(OtherPhone1AreaCode).substring(0, 4).replaceAll("\\(", "").trim();
        } catch(InterruptedException ie){
            System.out.println(ie.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Invalid format found in 'Dialing Code (Other Phone 1) ' field. "+ie.getMessage());

        }catch (NullPointerException e) {
            System.out.println("'Dialing Code (Other Phone 1)'  value is Null");
            otherPhone1AreaCode ="";
        }

        if (otherPhone1AreaCode.isEmpty() || otherPhone1AreaCode == "" || selectBox.getDefaultText(OtherPhone1AreaCode).trim().equalsIgnoreCase("--None--")) {
            Mobile1AreaCode_sf = "";
            _scenario.write("========OtherPhone1AreaCode=========");
            _scenario.write(" OtherPhone1AreaCode is NOT found in SF");
            System.out.println("OtherPhone1AreaCode is not available in SF");
        } else {
            OtherPhone1AreaCode_sf = otherPhone1AreaCode;
            _scenario.write("OtherPhone1AreaCode in SF : " + otherPhone1AreaCode);
            System.out.println("OtherPhone1AreaCode in SF : " + otherPhone1AreaCode);
        }
    }

    public void setOtherPhone1AreaCode(String updatedValue) throws Exception {
        try {
            selectBox.selectByVisibleText(OtherPhone1AreaCode, updatedValue);
        } catch (Exception e) {
            System.out.println("Unable to set value in 'Dialing Code (Other Phone 1) ' in SF ");
            throw e;
        }
    }

    public void getOtherPhone2AreaCode() throws Exception {
        String otherPhone2AreaCode = null;
        try {
            otherPhone2AreaCode = stringFunctions.trimStringBeforeOpenBracket(selectBox.getDefaultText(OtherPhone2AreaCode));
                    //selectBox.getDefaultText(OtherPhone2AreaCode).substring(0, 4).replaceAll("\\(", "").trim();
        } catch(InterruptedException ie){
            System.out.println(ie.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Invalid format found in 'Dialing Code (Other Phone 2)' field. "+ie.getMessage());

        }catch (NullPointerException e) {
            System.out.println("'Dialing Code (Other Phone 2)' value is Null");
            otherPhone2AreaCode ="";
        }

        if (otherPhone2AreaCode.isEmpty() || otherPhone2AreaCode == "" || selectBox.getDefaultText(OtherPhone1AreaCode).trim().equalsIgnoreCase("--None--")) {
            Mobile1AreaCode_sf = "";
            _scenario.write("========OtherPhone2AreaCode=========");
            _scenario.write(" OtherPhone2AreaCode is NOT found in SF");
            System.out.println("OtherPhone2AreaCode is not available in SF");
        } else {
            OtherPhone2AreaCode_sf = otherPhone2AreaCode;
            _scenario.write("OtherPhone2AreaCode in SF : " + otherPhone2AreaCode);
            System.out.println("OtherPhone2AreaCode in SF : " + otherPhone2AreaCode);
        }
    }

    public void setOtherPhone2AreaCode(String updatedValue) throws Exception {
        try {
            selectBox.selectByVisibleText(OtherPhone2AreaCode, updatedValue);
        } catch (Exception e) {
            System.out.println("Unable to set value in 'Dialing Code (Other Phone 2) ' in SF ");
            throw e;
        }
    }


    public void getValidOtherPhone1() throws Exception {
        Boolean validOtherPhone1 = checkBox.getCheckboxStatus(ValidOtherPhone1CheckBox);

        if (!validOtherPhone1) {  //if unchecked
            ValidOtherPhone1_sf = "0";
            _scenario.write("========ValidOtherPhone1=========");
            _scenario.write("ValidOtherPhone1 value in SF :" + ValidOtherPhone1_sf);
            System.out.println("ValidOtherPhone1 value in SF :" + ValidOtherPhone1_sf);
        } else { // if checked
            ValidOtherPhone1_sf = "1";
            _scenario.write("ValidOtherPhone1 value in SF :" + ValidOtherPhone1_sf);
            System.out.println("ValidOtherPhone1 value in SF :" + ValidOtherPhone1_sf);
        }
    }

    public void setValidOtherPhone1(boolean state) throws Exception {
        try {
            comElement.click(whiteSpace);
            System.out.println("Clicked on white space");
        } catch (Exception e) {
            System.out.println("ERROR clicking on white space");
        }

        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",ValidOtherPhone1CheckBox);
            System.out.println("scrolled to 'Valid Other Phone 1' checkbox successfully ");
        } catch (Exception e) {
            System.out.println("ERROR scrolled to 'Valid Other Phone 1' checkbox ");
        }

        try {
            javaScriptExecutor.changeCheckBoxStatus(ValidOtherPhone1CheckBox,state);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find 'Valid Other Phone 1' checkbox element");
            throw e;
        } catch (WebDriverException e) {
            System.out.println("Webdriver exception found when changing 'Valid Other Phone 1' checkbox ");
            throw e;
        } catch (Exception e) {
            System.out.println("Exception found when changing 'Valid Other Phone 1' checkbox status");
            throw e;
        }
    }

    public void getValidOtherPhone2() throws Exception {
        Boolean validOtherPhone2 = checkBox.getCheckboxStatus(ValidOtherPhone2CheckBox);

        if (!validOtherPhone2) {  //if unchecked
            ValidOtherPhone2_sf = "0";
            _scenario.write("========ValidOtherPhone2=========");
            _scenario.write("ValidOtherPhone2 value in SF :" + ValidOtherPhone2_sf);
            System.out.println("ValidOtherPhone2 value in SF :" + ValidOtherPhone2_sf);
        } else { // if checked
            ValidOtherPhone2_sf = "1";
            _scenario.write("ValidOtherPhone2 value in SF :" + ValidOtherPhone2_sf);
            System.out.println("ValidOtherPhone2 value in SF :" + ValidOtherPhone2_sf);
        }
    }

    public void setValidOtherPhone2(boolean state) throws Exception {
        try {
            comElement.click(whiteSpace);
            System.out.println("Clicked on white space");
        } catch (Exception e) {
            System.out.println("ERROR clicking on white space");
        }

        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",ValidOtherPhone2CheckBox);
            System.out.println("scrolled to 'Valid Other Phone 2' checkbox successfully ");
        } catch (Exception e) {
            System.out.println("ERROR scrolled to 'Valid Other Phone 2' checkbox ");
        }

        try {
            javaScriptExecutor.changeCheckBoxStatus(ValidOtherPhone2CheckBox,state);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find 'Valid Other Phone 2' checkbox element");
            throw e;
        } catch (WebDriverException e) {
            System.out.println("Webdriver exception found when changing 'Valid Other Phone 2' checkbox ");
            throw e;
        } catch (Exception e) {
            System.out.println("Exception found when changing 'Valid Other Phone 2' checkbox status");
            throw e;
        }
    }

    public void clickSaveButtonInSF() throws Exception {

        //Clicking on the white space and waiting 5 seconds for "save" button to appear
        try {
            Thread.sleep(5000);
            comElement.click(whiteSpace);
            System.out.println("clicked on white space");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Unable to click on whitespace");
            throw e;
        }

        //scrolling to the save button
        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",SaveButtonInSF);
            System.out.println("scrolled successfully");
        } catch (Exception e) {
            System.out.println("Unable to scroll to the Save button. Exception - "+e.getMessage());
            throw e;
        }

        //clicking on the save button
        try {
            Thread.sleep(3000);
            comElement.click(SaveButtonInSF);
            System.out.println("clicked on save button");

        } catch (Exception e) {
            System.out.println("Unable to click on Save button in SF");
            throw e;
        }
    }

    public void getPostalCode() throws Exception {
        String postalCode = textBox.getText(PostalCode);

        if (postalCode.isEmpty() || postalCode == "") {
            PostalCode_sf = "";
            _scenario.write("========PostalCode=========");
            _scenario.write(" PostalCode is NOT found in SF ");
            System.out.println("PostalCode is not available in SF ");
        } else {
            PostalCode_sf = postalCode;
            _scenario.write("PostalCode value in SF : " + postalCode);
            System.out.println("PostalCode value in SF : " + postalCode);
        }

    }

    public void getCityNameText() throws Exception {
        String cityName = selectBox.getDefaultText(CityNameText);
        if (cityName.equalsIgnoreCase("Others") || cityName.equalsIgnoreCase("--None--") || cityName.isEmpty() || cityName == "") {
            CityName_sf = "";
            _scenario.write("========cityName=========");
            _scenario.write(" cityName is NOT found in SF ");
            System.out.println("cityName is not available in SF ");
        } else {
            CityName_sf = cityName;
            _scenario.write("cityName value in SF : " + CityName_sf);
            System.out.println("cityName value in SF : " + CityName_sf);
        }
    }

    public void getContactNumberText() throws Exception {

        String contactNumber = textBox.getText(ContactNumberText);

        if (contactNumber.isEmpty() || contactNumber == "") {
            ContactNumberText_sf = "";
            _scenario.write("========contactNumber=========");
            _scenario.write(" contactNumber is NOT found in SF");
            System.out.println("contactNumber is not available in SF");
        } else {
            ContactNumberText_sf = contactNumber;
            _scenario.write("contactNumber value in SF : " + contactNumber);
            System.out.println("contactNumber value in SF : " + contactNumber);
        }

    }

    public void setContactNumberText(String updatedValue) throws Exception {
        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",ContactNumberText);
            textBox.setText(ContactNumberText, updatedValue);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setOtherPhone1(String updatedValue) throws Exception {
        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",OtherPhone1);
            textBox.setText(OtherPhone1, updatedValue);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setOtherPhone2(String updatedValue) throws Exception {
        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",OtherPhone2);
            textBox.setText(OtherPhone2, updatedValue);
        } catch (Exception e) {
            throw e;
        }
    }


    public void getOtherPhone1() throws Exception {

        String otherPhone1 = null;
        try {
            otherPhone1 = textBox.getText(OtherPhone1);
        } catch (ElementNotFoundException e) {
            System.out.println("Element not found - "+e.getMessage());
            throw e;
        } catch (WebDriverException e) {
            System.out.println("Webdriver exception occurred - "+e.getMessage());
            throw e;
        }

        if (otherPhone1.isEmpty() || otherPhone1 == "") {
            OtherPhone1_sf = "";
            _scenario.write("========otherPhone1=========");
            _scenario.write(" otherPhone1 is NOT found in SF");
            System.out.println("otherPhone1 is not available in SF");
        } else {
            OtherPhone1_sf = otherPhone1;
            _scenario.write("otherPhone1 value in SF : " + otherPhone1);
            System.out.println("otherPhone1 value in SF : " + otherPhone1);
        }

    }

    public void getOtherPhone2() throws Exception {
        String otherPhone2 = textBox.getText(OtherPhone2);

        if (otherPhone2.isEmpty() || otherPhone2 == "") {
            OtherPhone2_sf = "";
            _scenario.write("========otherPhone2=========");
            _scenario.write(" otherPhone2 is NOT found in SF ");
            System.out.println("otherPhone2 is not available in SF ");
        } else {
            OtherPhone2_sf = otherPhone2;
            _scenario.write("otherPhone2 value in SF : " + otherPhone2);
            System.out.println("otherPhone2 value in SF : " + otherPhone2);
        }
    }

    public void getCycleStartDate() throws Exception {
        String cycleStartDate = comElement.getText(CycleStartDate).trim();

        if (cycleStartDate.isEmpty() || cycleStartDate == "") {
            CycleStartDate_sf = "";
            _scenario.write("======== CycleStartDate =========");
            _scenario.write(" CycleStartDate is NOT found in SF");
            System.out.println("CycleStartDate is not available in SF");
        } else {

            String trimDate = cycleStartDate.trim();
            String[] dateParts = trimDate.split("/");
            String day = dateParts[0];
            if (day.toCharArray().length < 2) {
                day = "0" + day;
            }

            String month = dateParts[1];
            if (month.toCharArray().length < 2) {
                month = "0" + month;
            }

            String year = dateParts[2];
            System.out.println(year + "-" + month + "-" + day);
            String starttDate = year + "-" + month + "-" + day;

            CycleStartDate_sf = starttDate;
            _scenario.write("CycleStartDate value in SF : " + starttDate);
            System.out.println("CycleStartDate value IN SF : " + starttDate);
        }
    }

    public void getCycleEndDate() throws Exception {
        String cycleEndDate = comElement.getText(CycleEndDate).trim();

        if (cycleEndDate.isEmpty() || cycleEndDate == "") {
            CycleEndDate_sf = "";
            _scenario.write("======== CycleEndDate =========");
            _scenario.write(" CycleEndDate is NOT found in SF");
            System.out.println(" CycleEndDate is not available in SF");
        } else {

            String trimDate = cycleEndDate.trim();
            String[] dateParts = trimDate.split("/");
            String day = dateParts[0];
            if (day.toCharArray().length < 2) {
                day = "0" + day;
            }

            String month = dateParts[1];
            if (month.toCharArray().length < 2) {
                month = "0" + month;
            }

            String year = dateParts[2];
            System.out.println(year + "-" + month + "-" + day);
            String endDate = year + "-" + month + "-" + day;

            CycleEndDate_sf = endDate;
            _scenario.write("CycleEndDate value in SF : " + endDate);
            System.out.println("CycleEndDate value IN SF : " + endDate);
        }
    }

    public void getStatusDollarToUpgrade() throws Exception {
        String statusDollarToUpgrade = null;
        try {
            statusDollarToUpgrade = comElement.getText(StatusDollarToUpgrade).replaceAll(",", "").trim();
        } catch (NullPointerException e) {
            System.out.println("Status Dollar To Upgrade is Null");
            _scenario.write("Status Dollar To Upgrade is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting StatusDollarToUpgrade. - " + ex);
            _scenario.write("Exception occurred when getting StatusDollarToUpgrade. - " + ex);
        }

        if (statusDollarToUpgrade.isEmpty() || statusDollarToUpgrade == "") {
            statusDollarToUpgrade_sf = "";
            _scenario.write(" Status Dollar To Upgrade is NOT found in SF");
            System.out.println("Status Dollar To Upgrade is not available");
        } else {
            statusDollarToUpgrade_sf = statusDollarToUpgrade;
            _scenario.write("Status Dollar To Upgrade value in SF : " + statusDollarToUpgrade);
            System.out.println("Status Dollar To Upgrade value in SF : " + statusDollarToUpgrade);
        }


    }

    public void getStatusDollarToRenew() throws Exception {
        String statusDollarToRenew = null;
        try {
            statusDollarToRenew = comElement.getText(StatusDollarToRenew).replaceAll(",", "").trim();
        } catch (NullPointerException e) {
            System.out.println("statusDollarToRenew is Null");
            _scenario.write("statusDollarToRenew is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting statusDollarToRenew. - " + ex);
            _scenario.write("Exception occurred when getting statusDollarToRenew. - " + ex);
        }

        if (statusDollarToRenew.isEmpty() || statusDollarToRenew == "") {
            statusDollarToRenew_sf = "";
            _scenario.write("========statusDollarToRenew=========");
            _scenario.write(" statusDollarToRenew is NOT found in SF");
            System.out.println("statusDollarToRenew is not available");
        } else {
            statusDollarToRenew_sf = statusDollarToRenew;
            _scenario.write("statusDollarToRenew value in SF : " + statusDollarToRenew);
            System.out.println("statusDollarToRenew value in SF : " + statusDollarToRenew);
        }

    }

    public void getStatusDollarToRenew_SF() throws Exception {
        String statusDollarToRenew = null;
        try {
            statusDollarToRenew = comElement.getText(StatusDollarToRenew).trim();
        } catch (NullPointerException e) {
            System.out.println("Status Dollar To Renew is Null");
            _scenario.write("Status Dollar To Renew is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting statusDollarToRenew. - " + ex);
            _scenario.write("Exception occurred when getting statusDollarToRenew. - " + ex);
        }

        if (statusDollarToRenew.isEmpty() || statusDollarToRenew == "") {
            statusDollarToRenew_sf_formatted = "";
            _scenario.write("Status Dollar To Renew is NOT found in SF");
            System.out.println("Status Dollar To Renew is not available");
        } else {
            statusDollarToRenew_sf_formatted = statusDollarToRenew;
            _scenario.write("Status Dollar To Renew value in SF : " + statusDollarToRenew);
            System.out.println("Status Dollar To Renew value in SF : " + statusDollarToRenew);
        }

    }

    public void getCarryForwardDollarAmount() throws Exception {
        String carryForwardDollarAmount = null;
        try {
            carryForwardDollarAmount = comElement.getText(CarryForwardDollarUSDAmount).replaceAll(",", "").trim();

        } catch (NullPointerException e) {
            System.out.println("carryForwardDollarAmount is Null");
            _scenario.write("carryForwardDollarAmount is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting carryForwardDollarAmount. - " + ex);
            _scenario.write("Exception occurred when getting carryForwardDollarAmount. - " + ex);
        }

        if (carryForwardDollarAmount.isEmpty() || carryForwardDollarAmount == "") {
            carryForwardDollarAmount_sf = "";
            _scenario.write(" Carry Forward Dollar Amount is NOT found in SF");
            System.out.println("Carry Forward Dollar Amount is not available");
        } else {
            carryForwardDollarAmount_sf = carryForwardDollarAmount;
            _scenario.write("Carry Forward Dollar Amount value in SF : " + carryForwardDollarAmount);
            System.out.println("Carry Forward Dollar Amount value in SF : " + carryForwardDollarAmount);
        }
    }

    public void getPointBalance() throws Exception {
        String pointBalance = null;
        try {
            pointBalance = comElement.getText(PointBalance).replaceAll(",", "").trim();
        } catch (NullPointerException e) {
            System.out.println("PointBalance is Null");
            _scenario.write("PointBalance is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting PointBalance. - " + ex);
            _scenario.write("Exception occurred when getting PointBalance. - " + ex);
        }

        if (pointBalance.isEmpty() || pointBalance == "") {
            pointBalance_sf = "";
            _scenario.write("========PointBalance=========");
            _scenario.write(" PointBalance is NOT found in SF");
            System.out.println("PointBalance is not available");
        } else {
            pointBalance_sf = pointBalance;
            _scenario.write("PointBalance value in SF : " + pointBalance);
            System.out.println("PointBalance value in SF : " + pointBalance);
        }
    }

    //IsInvalidEmail ==========================================================
    public void getIsInvalidEmail() throws Exception {
        boolean IsInvalidEmailBoolian = checkBox.getCheckboxStatus(IsInvalidEmail);
        if (IsInvalidEmailBoolian) {
            IsInvalidEmail_sf = "0";
            _scenario.write("========IsInvalidEmail=========");
            _scenario.write(" IsInvalidEmail value in SF " + IsInvalidEmail_sf);
            System.out.println(" IsInvalidEmail value in SF " + IsInvalidEmail_sf);
        } else if (!IsInvalidEmailBoolian) {
            IsInvalidEmail_sf = "1";
            _scenario.write(" IsInvalidEmail value in SF " + IsInvalidEmail_sf);
            System.out.println(" IsInvalidEmail value in SF " + IsInvalidEmail_sf);

        }

    }

    //IsInvalidAddress
    public void getIsInvalidAddress() throws Exception {
        boolean IsInvalidAddressBoolian = checkBox.getCheckboxStatus(IsInvalidAddress);
        if (IsInvalidAddressBoolian) {
            IsInvalidAddress_sf = "0";
            _scenario.write("========IsInvalidAddress=========");
            _scenario.write(" IsInvalidAddress value in SF " + IsInvalidAddress_sf);
            System.out.println(" IsInvalidAddress value in SF " + IsInvalidAddress_sf);
        } else if (!IsInvalidAddressBoolian) {
            IsInvalidAddress_sf = "1";
            _scenario.write(" IsInvalidAddress value in SF " + IsInvalidAddress_sf);
            System.out.println(" IsInvalidAddress value in SF " + IsInvalidAddress_sf);

        }
    }

    public void setIsInvalidEmail(boolean state) throws Exception {

        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",IsInvalidEmail);
            System.out.println("scrolled to 'Valid Email Address' checkbox successfully ");
        } catch (Exception e) {
            System.out.println("ERROR scrolled to 'Valid Email Address' checkbox ");
        }

        try {
            javaScriptExecutor.changeCheckBoxStatus(IsInvalidEmail,state);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find 'Valid Email Address' checkbox element");
            throw e;
        } catch (WebDriverException e) {
            System.out.println("Webdriver exception found when changing 'Valid Email Address' checkbox ");
            throw e;
        } catch (Exception e) {
            System.out.println("Exception found when changing 'Valid Email Address' checkbox status");
            throw e;
        }
    }

    public void getIsEmptyEmail() throws Exception {
        if (EmailAddressText_sf.isEmpty() || EmailAddressText_sf == "") {
            IsEmptyEmail_sf = "1";
        } else if (!EmailAddressText_sf.isEmpty() || EmailAddressText_sf != "") {
            IsEmptyEmail_sf = "0";
        }
        _scenario.write("========IsEmptyEmail=========");
        _scenario.write(" IsEmptyEmail value in SF : " + IsEmptyEmail_sf);
        System.out.println("IsEmptyEmail value in SF : " + IsEmptyEmail_sf);
    }

    public void getUnwillingToProvideEmail() throws Exception {
        Boolean emailUnwillingToProvide = checkBox.getCheckboxStatus(UnwillingToProvideEmail);
        if (emailUnwillingToProvide) //if checked)
        {
            EmailUnwillingToProvide_sf = "1";
            _scenario.write(" EmailUnwillingToProvide value in SF " + EmailUnwillingToProvide_sf);
            System.out.println(" EmailUnwillingToProvide value in SF " + EmailUnwillingToProvide_sf);

        } else {
            EmailUnwillingToProvide_sf = "0";
            _scenario.write(" EmailUnwillingToProvide value in SF " + EmailUnwillingToProvide_sf);
            System.out.println(" EmailUnwillingToProvide value in SF " + EmailUnwillingToProvide_sf);
        }
    }

    public void switchToMainFrame() throws InterruptedException {
        //Switch to frame
        Thread.sleep(1000);
        try {
            driver.switchTo().frame(mainFrame);
            System.out.println("switched to main frame");
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find the Main frame element - " + e);
            _scenario.write("Unable to find the Main frame element - " + e);
            Assert.fail();
        }

    }

    public void getFirstName() throws Exception {
        String firstName = textBox.getText(FirstName);

        if (firstName.isEmpty() || firstName == "") {
            FirstName_sf = "";
            _scenario.write("========firstName=========");
            _scenario.write(" firstName is NOT found in SF ");
            System.out.println("firstName is not available in SF ");
        } else {
            FirstName_sf = firstName;
            _scenario.write("firstName value in SF : " + firstName);
            System.out.println("firstName value in SF : " + firstName);
        }
    }

    public void getFirstNameNative() throws Exception {
        String firstNameNative = textBox.getText(FirstNameNative);
        if (firstNameNative.isEmpty() || firstNameNative == "") {
            FirstNameNative_sf = "";
            _scenario.write("========firstNameNative=========");
            _scenario.write(" firstNameNative is NOT found in SF ");
            System.out.println("firstNameNative is not available in SF ");
        } else {
            FirstNameNative_sf = firstNameNative;
            _scenario.write("firstNameNative value in SF : " + firstNameNative);
            System.out.println("firstNameNative value in SF : " + firstNameNative);
        }
    }

    public void getLastName() throws Exception {

        String lastName = textBox.getText(LastName);

        if (lastName.isEmpty() || lastName == "") {
            LastName_sf = "";
            _scenario.write("========lastName=========");
            _scenario.write(" lastName is NOT found in SF");
            System.out.println("lastName is not available in SF");
        } else {
            LastName_sf = lastName;
            _scenario.write("lastName value in SF : " + lastName);
            System.out.println("lastName value in SF : " + lastName);
        }

    }

    public void getTitleText() throws Exception {
        String title = comElement.getText(titleTextLabel);

        if (title.isEmpty() || title == "") {
            titleText_sf = "";
            _scenario.write("========Title Text=========");
            _scenario.write(" Title Text is NOT found in SF");
            System.out.println("Title Text is not available in SF");
        } else {
            titleText_sf = title;
            _scenario.write("Title Text value in SF : " + title);
            System.out.println("Title Text value in SF : " + title);
        }
    }

    public void getNativeSalutation() throws Exception {
        String nativeSalutation = selectBox.getDefaultText(NativeSalutation);
        if (nativeSalutation.isEmpty() || nativeSalutation.equalsIgnoreCase("--None--")) {
            NativeSalutation_sf = "";
            _scenario.write("========Native Salutation=========");
            _scenario.write(" Native Salutation is not found in SF");
            System.out.println(" Native Salutation is not found in SF");
        } else {
            NativeSalutation_sf = nativeSalutation;
            _scenario.write("========Native Salutation=========");
            _scenario.write(" Native Salutation Value in SF : " + nativeSalutation);
            System.out.println("Native Salutation Value in SF : " + nativeSalutation);
        }

    }

    public void getNationality() throws Exception {

        _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
        String nationality = selectBox.getDefaultText(Nationality);
        /*Runtime_TestData.gender = gender;
        System.out.println("The gender is : " + gender );*/
        if (nationality.isEmpty() || nationality.equalsIgnoreCase("--None--")) {
            Nationality_sf = "";
            _scenario.write("========Nationality=========");
            _scenario.write(" Nationality is not found in SF");
            System.out.println(" Nationality is not found in SF");
        } else {
            Nationality_sf = nationality;
            _scenario.write("========Nationality=========");
            _scenario.write(" Nationality Value in SF : " + nationality);
            System.out.println("Nationality Value in SF : " + nationality);
        }

    }

    public void getMemberStatus() throws Exception {

        String memberStatus = null;
        try {
            memberStatus = comElement.getText(MemberStatus).trim();
        } catch (NullPointerException e) {
            System.out.println("Member Status is Null");
            _scenario.write("Member Status is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Member Status. - " + ex);
            _scenario.write("Exception occurred when getting Member Status. - " + ex);
        }

        if (memberStatus.isEmpty() || memberStatus == "") {
            MemberStatus_sf = "";
            _scenario.write("========Membership Status=========");
            _scenario.write(" Member Status is not found in SF");
            System.out.println(" Member Status is not found in SF");
        } else {
            MemberStatus_sf = memberStatus;
            _scenario.write("========Membership Status=========");
            _scenario.write(" Member Status Value in SF : " + memberStatus);
            System.out.println("Member Status Value in SF : " + memberStatus);
        }
    }

    public void getCardStatus() throws Exception {

        String cardStatusS = null;
        try {
            cardStatusS = comElement.getText(cardStatus).trim();
        } catch (NullPointerException e) {
            System.out.println("Card Status is Null");
            _scenario.write("Card Status is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Card Status. - " + ex);
            _scenario.write("Exception occurred when getting Card Status. - " + ex);
        }

        if (cardStatusS.isEmpty() || cardStatusS == "") {
            cardStatus_sf = "";
            _scenario.write("========Card Status=========");
            _scenario.write(" Card Status is not found in SF");
            System.out.println(" Card Status is not found in SF");
        } else {
            cardStatus_sf = cardStatusS;
            _scenario.write("========Card Status=========");
            _scenario.write("Card Status Value in SF : " + cardStatusS);
            System.out.println("Card Status Value in SF : " + cardStatusS);
        }
    }

    public boolean getCobrandIndicatorStatus() throws Exception {
        String cobrandIndicatorStatus =null;
        try {
            cobrandIndicatorStatus = comElement.getAttribute(CobrandIndicatorCheckboxImage,"class").trim().toLowerCase();
            System.out.println("Cobrand Indicator status - "+cobrandIndicatorStatus);
        } catch (WebDriverException we) {
            System.out.println("Unable to read the status from Cobrand indicator element. "+ we.getMessage());
            screenshot.takeScreenshot();
            throw we;
        }

        if(cobrandIndicatorStatus.equalsIgnoreCase("checked")){
            return true;
        } else {
            return false;
        }
    }

    public void getMembershipcardStatusCode() throws Exception {
        String memberCardStatus = null;
        try {
            memberCardStatus = comElement.getText(cardStatus).trim();
        } catch (NullPointerException e) {
            System.out.println("Membership Card Status is Null");
            _scenario.write("Membership Card Status is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Membership Card Status. - " + ex);
            _scenario.write("Exception occurred when getting Membership Card Status. - " + ex);
        }

        if (memberCardStatus.isEmpty() || memberCardStatus == "") {
            MemberCardStatus_sf = "";
            _scenario.write("========Member Card Status=========");
            _scenario.write(" Membership Card Status is not found in SF");
            System.out.println(" Membership Card Status is not found in SF");
        } else {
            MemberCardStatus_sf = memberCardStatus;
            _scenario.write("========Member Card Status=========");
            _scenario.write(" Membership Card Status Value in SF : " + memberCardStatus);
            System.out.println("Membership Card Status Value in SF : " + memberCardStatus);
        }
    }

    public void getLastNameNative() throws Exception {
        String lastNameNative = textBox.getText(LastNameNative);

        if (lastNameNative.isEmpty() || lastNameNative == "") {
            LastNameNative_sf = "";
            _scenario.write("========LastNameNative=========");
            _scenario.write(" LastNameNative is NOT found in SF ");
            System.out.println("LastNameNative is not available in SF ");
        } else {
            LastNameNative_sf = lastNameNative;
            _scenario.write("LastNameNative value in SF : " + lastNameNative);
            System.out.println("LastNameNative value in SF : " + lastNameNative);
        }
    }

    //WeChatID
    public void getWeChatID() throws Exception {
        String weChatId = textBox.getText(WeChatID);

        if (weChatId.isEmpty() || weChatId == "") {
            weChatId_sf = "";
            _scenario.write("========WeChatID=========");
            _scenario.write(" WeChatID is NOT found in SF ");
            System.out.println("WeChatID is not available in SF ");
        } else {
            weChatId_sf = weChatId;
            _scenario.write("WeChatID value in SF : " + weChatId);
            System.out.println("WeChatID value in SF : " + weChatId);
        }
    }


    public void getBirthDate() throws Exception {
        String birthDate = selectBox.getDefaultText(BirthDate);
        if (birthDate != "" && birthDate.toCharArray().length < 2) {
            birthDate = "0" + birthDate;
        } else {
            birthDate = "";
        }

        if (birthDate.isEmpty() || birthDate == "") {
            BirthDate_sf = "";
            _scenario.write("========birthDate=========");
            _scenario.write(" birthDate is NOT found in SF ");
            System.out.println("birthDate is not available in SF");
        } else {
            BirthDate_sf = birthDate;
            _scenario.write("birthDate value in SF : " + birthDate);
            System.out.println("birthDate value in SF in SF :" + birthDate);
        }

    }

    public void getBirthMonth() throws Exception {
        readTestData monthMap = new readTestData();

        String birthMonthOriginal = selectBox.getDefaultText(BirthMonth);

        String birthMonth = null;
        try {
            birthMonth = monthMap.readDSABrandMapping(birthMonthOriginal);

        } catch (FileNotFoundException e) {

            _scenario.write(" File Not Found !. Unable to read the file that includes mapping of Birth month ");
            System.out.println(" File Not Found !. Unable to read the file that includes mapping of Birth month ");

        } catch (NullPointerException ne) {

            _scenario.write(" The birth month code provided by the element is Not valid. Unable to convert it into valid value ");
            System.out.println(" The birth month code provided by the element is Not valid. Unable to convert it into valid value ");
        }

        if (birthMonth.isEmpty() || birthMonth == "") {

            BirthMonth_sf = "";
            _scenario.write("========birthMonth=========");
            _scenario.write(" birthMonth is NOT found in SF ");
            System.out.println("birthMonth is not available in SF ");
        } else {
            BirthMonth_sf = birthMonth;
            _scenario.write("birthMonth value in SF : " + birthMonth);
            System.out.println("birthMonth value in SF : " + birthMonth);
        }
    }

    public void getMailingAddress2() throws Exception {
        String mailingAddress2 = textBox.getText(MailingAddress2);

        if (mailingAddress2.isEmpty() || mailingAddress2 == "") {
            MailingAddress2_sf = "";
            _scenario.write("========mailingAddress2=========");
            _scenario.write(" mailingAddress2 is NOT found in SF ");
            System.out.println("mailingAddress2 is not available in SF ");
        } else {
            MailingAddress2_sf = mailingAddress2;
            _scenario.write("mailingAddress2 value in SF : " + mailingAddress2);
            System.out.println("mailingAddress2 value in SF : " + mailingAddress2);
        }

    }

    public void getZipCode() throws Exception {
        String zipCode = textBox.getText(ZipCode);

        if (zipCode.isEmpty() || zipCode == "") {
            ZipCode_sf = "";
            _scenario.write("========zipCode=========");
            _scenario.write(" zipCode is NOT found in SF ");
            System.out.println("zipCode is not available in SF ");
        } else {
            ZipCode_sf = zipCode;
            _scenario.write("zipCode value in SF : " + zipCode);
            System.out.println("zipCode value in SF : " + zipCode);
        }
    }

    public void getMailingAddress1() throws Exception {
        String mailingAddress1 = textBox.getText(MailingAddress1);

        if (mailingAddress1.isEmpty() || mailingAddress1 == "") {
            MailingAddress1_sf = "";
            _scenario.write("========mailingAddress1=========");
            _scenario.write(" mailingAddress1 is NOT found in SF ");
            System.out.println("mailingAddress1 is not available in SF ");
        } else {
            MailingAddress1_sf = mailingAddress1;
            _scenario.write("mailingAddress1 value in SF : " + mailingAddress1);
            System.out.println("mailingAddress1 value in SF : " + mailingAddress1);
        }
    }

    public void getMailingAddress3() throws Exception {
        String mailingAddress3 = textBox.getText(MailingAddress3).trim();

        if (mailingAddress3.isEmpty() || mailingAddress3 == "") {
            MailingAddress3_sf = "";
            _scenario.write("========mailingAddress3=========");
            _scenario.write(" mailingAddress3 is NOT found in SF ");
            System.out.println("mailingAddress3 is not available in SF ");
        } else {
            MailingAddress3_sf = mailingAddress3;
            _scenario.write("mailingAddress3 value in SF : " + mailingAddress3);
            System.out.println("mailingAddress3 value in SF : " + mailingAddress3);
        }

    }

    public void getResCityNameText() throws Exception {
        String resCityName = selectBox.getDefaultText(ResCityNameText).trim();

        if (resCityName.equalsIgnoreCase("Others") || resCityName.equalsIgnoreCase("--None--") || resCityName.isEmpty() || resCityName == "") {
            ResCityNameText_sf = "";
            _scenario.write("========ResCityName=========");
            _scenario.write(" ResCityName is NOT found in SF ");
            System.out.println("ResCityName is not available in SF ");
        } else {
            ResCityNameText_sf = resCityName;
            _scenario.write("ResCityName value in SF : " + ResCityNameText_sf);
            System.out.println("ResCityName value in SF : " + ResCityNameText_sf);
        }
    }

    public void getResStateNameText() throws Exception {
        String resStateyName = selectBox.getDefaultText(ResStateNameText);

        if (resStateyName.equalsIgnoreCase("Others") || resStateyName.equalsIgnoreCase("--None--") || resStateyName.isEmpty() || resStateyName == "") {
            ResStateNameText_sf = "";
            _scenario.write("========ResStateName=========");
            _scenario.write(" ResStateName is NOT found");
            System.out.println("ResStateName is not available");
        } else {
            ResStateNameText_sf = resStateyName;
            _scenario.write("ResStateName value -" + ResStateNameText_sf);
            System.out.println("ResStateName value -" + ResStateNameText_sf);
        }
    }

    public void getEnglishCountry() throws Exception {

        String englishCountry = selectBox.getDefaultText(EnglishCountry);

        if (englishCountry.isEmpty() || englishCountry == "") {
            EnglishCountry_sf = "";
            _scenario.write("========englishCountry=========");
            _scenario.write(" englishCountry is NOT found in SF");
            System.out.println("englishCountry is not available in SF");
        } else {
            EnglishCountry_sf = englishCountry;
            _scenario.write("englishCountry value in SF : " + englishCountry);
            System.out.println("englishCountry value in SF : " + englishCountry);
        }


    }

    public void getValidMobileNo1() throws Exception {
        Boolean validMobileNo1 = checkBox.getCheckboxStatus(ValidMobileNo1);

        if (!validMobileNo1) {  //if unchecked
            ValidMobileNo1_sf = "0";
            _scenario.write("========validMobileNo1=========");
            _scenario.write("validMobileNo1 value in SF :" + ValidMobileNo1_sf);
            System.out.println("validMobileNo1 value in SF :" + ValidMobileNo1_sf);
        } else { // if checked
            ValidMobileNo1_sf = "1";
            _scenario.write("validMobileNo1 value in SF :" + ValidMobileNo1_sf);
            System.out.println("validMobileNo1 value in SF :" + ValidMobileNo1_sf);
        }

    }

    public void setValidMobileNo1(boolean state) throws Exception {

        try {
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();",ValidMobileNo1);
            System.out.println("scrolled to Valid Mobile Phone' checkbox successfully ");
        } catch (Exception e) {
            System.out.println("ERROR scrolled to Valid Mobile Phone' checkbox ");
        }

        try {
            javaScriptExecutor.changeCheckBoxStatus(ValidMobileNo1,state);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find 'Valid Mobile Phone' checkbox element");
            throw e;
        } catch (WebDriverException e) {
            System.out.println("Webdriver exception found when changing 'Valid Mobile Phone' checkbox ");
            throw e;
        } catch (Exception e) {
            System.out.println("Exception found when changing 'Valid Mobile Phone' checkbox status");
            throw e;
        }
    }

    public void switchToContactPreferencesTab() throws Exception {
        // Navigate Inside Contact Preferences +++++++++++++++++++
        System.out.println("~~~~~Inside switchToContactPreferencesTab~~~~~");
        Thread.sleep(5000);

        try {
            driver.switchTo().defaultContent();
            System.out.println("switched to default content");
        } catch (Exception e) {
            System.out.println("Exception occurred when switching to default content - " + e);
        }

        //clicking on contact preferences tab
        Thread.sleep(5000);

        try {
            ContactPreferencesTab.click();
            System.out.println("clicked on Contact preferences tab");
        } catch (NoSuchElementException ne) {
            System.out.println(" unable to find the ContactPreferencesTab - " + ne);
        }
        //
        //Switch to frame
        try {
            driver.switchTo().frame(iframeContactPreferences);
            System.out.println("Switch to iframe - Contact preferences");
        } catch (Exception e) {
            System.out.println("Unable to switch to iframe Contact preferences - " + e);
        }

    }

    public void switchToShoppingPreferencesTab() throws Exception {
        //switching to default content
        try {
            driver.switchTo().defaultContent();
            System.out.println("switched to default content");
        } catch (Exception e) {
            System.out.println("Exception occurred when switching to default content - " + e);
        }

        //clicking on shopping preferences tab
        Thread.sleep(5000);

        try {
            ShoppingPreferencesTab.click();
            System.out.println("clicked on Shopping Preferences Tab");
        } catch (NoSuchElementException ne) {
            System.out.println(" unable to find the Shopping Preferences Tab - " + ne);
        }

        //Switch to frame
        try {
            driver.switchTo().frame(iframeShoppingPreferences);
            System.out.println("Switch to iframe - Shopping Preferences");
        } catch (Exception e) {
            System.out.println("Unable to switch to iframe Shopping Preferences - " + e);
        }
    }

    public void switchToDefaultContent() {
        //switching to default content
        try {
            Thread.sleep(3000);
            driver.switchTo().defaultContent();
            System.out.println("switched to default content");
        } catch (Exception e) {
            System.out.println("Exception occurred when switching to default content - " + e);
        }

    }

    public void getSpokenLanguage() {
        String spokenLanguage = null;
        try {
            spokenLanguage = selectBox.getDefaultText(SpokenLanguage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpokenLanguage_sf = "";

        if (spokenLanguage.isEmpty() || spokenLanguage == "") {
            SpokenLanguage_sf = "";
            _scenario.write("========spokenLanguage=========");
            _scenario.write(" spokenLanguage is NOT found in SF");
            System.out.println("spokenLanguage is not available in SF");
        } else {
            SpokenLanguage_sf = spokenLanguage;
            _scenario.write("spokenLanguage value in SF : " + spokenLanguage);
            System.out.println("spokenLanguage value in SF : " + spokenLanguage);
        }
    }


    public void getIsContactable() throws Exception {
        Boolean isContactablePhone = null;
        try {
            isContactablePhone = checkBox.getCheckboxStatus(OptInPhoneCheckbox);
        } catch (Exception e) {
            System.out.println("Unable to get the status of 'isContactablePhone'. The exception is - " + e);
        }

        Boolean isContactableMail = null;
        try {
            isContactableMail = checkBox.getCheckboxStatus(OptInMailCheckbox);
        } catch (Exception e) {
            System.out.println("Unable to get the status of 'isContactableMail'. The exception is - " + e);
        }

        Boolean isContactableEmail = null;
        try {
            isContactableEmail = checkBox.getCheckboxStatus(OptInEmailCheckbox);
        } catch (Exception e) {
            System.out.println("Unable to get the status of 'isContactableEmail'. The exception is - " + e);
        }

        if (!isContactablePhone && !isContactableMail && !isContactableEmail) { //all checkboxes are unchecked
            IsContactable_sf = "0";

            //individual checkbox values
            isContactablePhone_sf ="0";
            isContactableMail_sf="0";
            isContactableEmail_sf="0";

            _scenario.write("========IsContactable=========");
            _scenario.write("isContactable in SF : " + IsContactable_sf);
            System.out.println("isContactable in SF : " + IsContactable_sf);
            _scenario.write("isContactablePhone value : " + isContactablePhone + "  |  isContactableMail : " + isContactableMail + " | isContactableEmail : " + isContactableEmail);
            System.out.println("isContactablePhone value : " + isContactablePhone + "  |  isContactableMail : " + isContactableMail + " | isContactableEmail : " + isContactableEmail);

        } else if (isContactablePhone && isContactableMail && isContactableEmail) {
            IsContactable_sf = "1";

            //individual checkbox values
            isContactablePhone_sf ="1";
            isContactableMail_sf="1";
            isContactableEmail_sf="1";

            _scenario.write("isContactable in SF : " + IsContactable_sf);
            System.out.println("isContactable in SF : " + IsContactable_sf);
            _scenario.write("isContactablePhone value : " + isContactablePhone + "  |  isContactableMail : " + isContactableMail + " | isContactableEmail : " + isContactableEmail);
            System.out.println("isContactablePhone value : " + isContactablePhone + "  |  isContactableMail : " + isContactableMail + " | isContactableEmail : " + isContactableEmail);

        }
    }

    public void switchToMemberCycleTab() throws Exception {
        Thread.sleep(5000);
        // clicking member cycle tab
        try {
            driver.switchTo().defaultContent();  // Must have ++++++
            System.out.println("switched to default content");
        } catch (Exception e) {
            System.out.println("Exception occurred when switching to default content - " + e);
            System.out.println("Retrying to Switch to Member Cycle Tab");
            switchToMemberCycleTab();
        }
        Thread.sleep(5000);
        try {
            MemberCycleTab.click();
        } catch (NoSuchElementException ne) {
            System.out.println(" Unable to find the Member Cycle Tab - " + ne);
            System.out.println("Retrying to Switch to Member Cycle Tab");
            switchToMemberCycleTab();
        }
        //Switch to frame
        try {
            driver.switchTo().frame(iframeMemberCycle);
            System.out.println("Switch to iframe - Member Cycle");
        } catch (Exception e) {
            System.out.println("Unable to switch to iframe Member Cycle - " + e);
            System.out.println("Retrying to Switch to Member Cycle Tab");
            switchToMemberCycleTab();
        }
    }

    public void getCardTier() throws Exception {

        String cardTier = null;
        try {
            cardTier = comElement.getText(CardTier).trim();
        } catch (NullPointerException e) {
            System.out.println("CardTier is Null");
            _scenario.write("CardTier is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting CardTier. - " + ex);
            _scenario.write("Exception occurred when getting CardTier. - " + ex);
        }

        if (cardTier.isEmpty() || cardTier == "") {
            CardTier_sf = "";
            _scenario.write("========cardTier=========");
            _scenario.write(" cardTier is NOT found in SF");
            System.out.println("cardTier is not available");
        } else {
            CardTier_sf = cardTier;
            _scenario.write("cardTier value in SF : " + cardTier);
            System.out.println("cardTier value in SF : " + cardTier);
        }
    }

    public void getMemberCardNumber() throws Exception {
        String mem_Card_Num = null;
        try {
            mem_Card_Num = comElement.getText(Mem_Card_Num).trim();
        } catch (NullPointerException e) {
            System.out.println("Membership Card Number is Null");
            _scenario.write("Membership Card Number is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Membership Card Number. - " + ex);
            _scenario.write("Exception occurred when getting Membership Card Number. - " + ex);
        }

        if (mem_Card_Num.isEmpty() || mem_Card_Num == "") {
            mem_Card_Num_sf = "";
            _scenario.write("========Membership Card Number=========");
            _scenario.write(" Membership Card Number is NOT found in SF");
            System.out.println("Membership Card Number is not available");
        } else {
            mem_Card_Num_sf = mem_Card_Num;
            _scenario.write("Membership Card Number value in SF : " + mem_Card_Num);
            System.out.println("Membership Card Number value in SF : " + mem_Card_Num);
        }
    }

    public void getEnrollmentLocation() throws Exception {
        String enroll_Location = null;
        try {
            enroll_Location = comElement.getText(Enroll_Location).trim();
        } catch (NullPointerException e) {
            System.out.println("Enrollment Location is Null");
            _scenario.write("Enrollment Location is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Enrollment Location. - " + ex);
            _scenario.write("Exception occurred when getting Enrollment Location. - " + ex);
        }

        if (enroll_Location.isEmpty() || enroll_Location == "") {
            enroll_Location_sf = "";
            _scenario.write("========Enrollment Location=========");
            _scenario.write("Enrollment Location is NOT found in SF");
            System.out.println("Enrollment Location is not available");
        } else {
            enroll_Location_sf = enroll_Location;
            _scenario.write("Enrollment Location value in SF : " + enroll_Location);
            System.out.println("Enrollment Location value in SF : " + enroll_Location);
        }
    }

    public void getEnrollmentDate() throws Exception {
        String enroll_Date = null;
        try {
            String enroll_Date_actual = comElement.getText(Enroll_Date).trim();
            Date myDate = new SimpleDateFormat("dd/MM/yyyy").parse(enroll_Date_actual);
            enroll_Date = new SimpleDateFormat("dd MMM yyyy").format(myDate);
        } catch (NullPointerException e) {
            System.out.println("Enrollment Date is Null");
            _scenario.write("Enrollment Date is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Enrollment Date. - " + ex);
            _scenario.write("Exception occurred when getting Enrollment Date. - " + ex);
        }

        if (enroll_Date.isEmpty() || enroll_Date == "") {
            enroll_Date_sf = "";
            _scenario.write("========Enrollment Date=========");
            _scenario.write("Enrollment Location is NOT found in SF");
            System.out.println("Enrollment Location is not available");
        } else {
            enroll_Date_sf = enroll_Date;
            _scenario.write("Enrollment Date value in SF : " + enroll_Date);
            System.out.println("Enrollment Date value in SF : " + enroll_Date);
        }
    }

    public void getEntryStatusDollars() throws Exception {
        String entry_Status_doll = null;
        try {
            entry_Status_doll = comElement.getText(Entry_Status_doll).trim().replaceAll("[, ;]", "");
        } catch (NullPointerException e) {
            System.out.println("Entry Status Dollars is Null");
            _scenario.write("Entry Status Dollars is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Entry Status Dollars. - " + ex);
            _scenario.write("Exception occurred when getting Entry Status Dollars. - " + ex);
        }

        if (entry_Status_doll.isEmpty() || entry_Status_doll == "") {
            entry_Status_doll_sf = "";
            _scenario.write("========Entry Status Dollars=========");
            _scenario.write("Entry Status Dollars is NOT found in SF");
            System.out.println("Entry Status Dollars is not available");
        } else {
            entry_Status_doll_sf = entry_Status_doll;
            _scenario.write("Entry Status Dollars value in SF : " + entry_Status_doll);
            System.out.println("Entry Status Dollars value in SF : " + entry_Status_doll);
        }
    }

    public void getFirstPurchaseDate() throws Exception {
        String first_Purchace_Date = null;
        try {
            String first_Purchace_Date_actual = comElement.getText(First_Purchace_Date).trim();
            Date myDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(first_Purchace_Date_actual);
            first_Purchace_Date = new SimpleDateFormat("dd MMM yyyy").format(myDate1);
        } catch (NullPointerException e) {
            System.out.println("First Purchase Date is Null");
            _scenario.write("First Purchase Date is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting First Purchase Date. - " + ex);
            _scenario.write("Exception occurred when getting First Purchase Date. - " + ex);
        }

        if (first_Purchace_Date == null || first_Purchace_Date.isEmpty() ) {
            first_Purchace_Date_sf = "";
            _scenario.write("========First Purchase Date=========");
            _scenario.write("First Purchase Date is NOT found in SF");
            System.out.println("First Purchase Date is not available");
        } else {
            first_Purchace_Date_sf = first_Purchace_Date;
            _scenario.write("First Purchase Date value in SF : " + first_Purchace_Date);
            System.out.println("First Purchase Date value in SF : " + first_Purchace_Date);
        }
    }

    public void getCycleStartDate_Formatted() throws Exception {
        String cycle_start_date = null;
        try {
            String cycle_start_date_actual = comElement.getText(CycleStartDate).trim();
            Date myDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(cycle_start_date_actual);
            cycle_start_date = new SimpleDateFormat("dd MMM yyyy").format(myDate1);
        } catch (NullPointerException e) {
            System.out.println("Cycle Start Date is Null");
            _scenario.write("Cycle Start Date is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Cycle Start Date. - " + ex);
            _scenario.write("Exception occurred when getting Cycle Start Date. - " + ex);
        }

        if (cycle_start_date == null || cycle_start_date.isEmpty() ) {
            CycleStartDate_sf_formattedDate = "";
            _scenario.write("========Cycle Start Date=========");
            _scenario.write("Cycle Start Date is NOT found in SF");
            System.out.println("Cycle Start Date is not available");
        } else {
            CycleStartDate_sf_formattedDate = cycle_start_date;
            _scenario.write("Cycle Start Date value in SF : " + cycle_start_date);
            System.out.println("Cycle Start Date value in SF : " + cycle_start_date);
        }
    }

    public void getCycleEndDate_Formatted() throws Exception {
        String cycle_end_date = null;
        try {
            String cycle_end_date_actual = comElement.getText(CycleEndDate).trim();
            Date myDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(cycle_end_date_actual);
            cycle_end_date = new SimpleDateFormat("dd MMM yyyy").format(myDate1);
        } catch (NullPointerException e) {
            System.out.println("Cycle End Date is Null");
            _scenario.write("Cycle End Date  is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Cycle End Date. - " + ex);
            _scenario.write("Exception occurred when getting Cycle End Date. - " + ex);
        }

        if (cycle_end_date == null || cycle_end_date.isEmpty()) {
            CycleEndDate_sf_formattedDate = "";
            _scenario.write("========Cycle Start Date=========");
            _scenario.write("Cycle End Date is NOT found in SF");
            System.out.println("Cycle End Date is not available");
        } else {
            CycleEndDate_sf_formattedDate = cycle_end_date;
            _scenario.write("Cycle End Date value in SF : " + cycle_end_date);
            System.out.println("Cycle End Date value in SF : " + cycle_end_date);
        }
    }

    public void getTierStatusDollars() throws Exception {
        String tier_Status_Doll = null;
        try {
            tier_Status_Doll = comElement.getText(Tier_Status_Doll).trim().replaceAll("[, ;]", "");
        } catch (NullPointerException e) {
            System.out.println("Tier Status Dollars is Null");
            _scenario.write("Tier Status Dollars is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Tier Status Dollars. - " + ex);
            _scenario.write("Exception occurred when getting Tier Status Dollars. - " + ex);
        }

        if (tier_Status_Doll == null || tier_Status_Doll.isEmpty()) {
            tier_Status_Doll_sf = "";
            _scenario.write("========Tier Status Dollars=========");
            _scenario.write("Tier Status Dollars is NOT found in SF");
            System.out.println("Tier Status Dollars is not available");
        } else {
            tier_Status_Doll_sf = tier_Status_Doll;
            _scenario.write("Tier Status Dollars value in SF : " + tier_Status_Doll);
            System.out.println("Tier Status Dollars value in SF : " + tier_Status_Doll);
        }
    }

    public void getExpiryDate() throws Exception {
        String expiryDate=null;
        try {
            expiryDate = comElement.getText(ExpiryDate).trim();
        } catch (NullPointerException e) {
            System.out.println("Expiry Date is Null");
            _scenario.write("Expiry Date is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Expiry Date. - " + ex.getMessage());
            _scenario.write("Exception occurred when getting Expiry Date. - " + ex.getMessage());
        }

        if(expiryDate==null || expiryDate.isEmpty()) {
            expiryDate_sf ="";
            _scenario.write("======== Expiry Date =========");
            _scenario.write("Expiry Date is NOT found in SF");
            System.out.println("Expiry Date is not available");
        } else {
            dateOperations dateOpr = new dateOperations();

            try {
                expiryDate_sf = dateOpr.convertSimpleDateIntoNumberDate(expiryDate);
            } catch (Exception e) {
                throw e;
            }

            _scenario.write("Expiry Date value in SF : " + expiryDate);
            System.out.println("Expiry Date value in SF : " + expiryDate);
        }
    }

    public void getDataSourceEnrollment() throws Exception {
        String data_Source_Enrolment = null;
        try {
            data_Source_Enrolment = comElement.getText(Data_Source_Enrolment).trim();
        } catch (NullPointerException e) {
            System.out.println("Data Source (Enrollment) is Null");
            _scenario.write("Data Source (Enrollment) is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Data Source (Enrollment). - " + ex);
            _scenario.write("Exception occurred when getting Data Source (Enrollment). - " + ex);
        }

        if (data_Source_Enrolment == null || data_Source_Enrolment.isEmpty() ) {
            data_Source_Enrolment_sf = "";
            _scenario.write("========Data Source (Enrollment)=========");
            _scenario.write("Data Source (Enrollment) is NOT found in SF");
            System.out.println("Data Source (Enrollment) is not available");
        } else {
            data_Source_Enrolment_sf = data_Source_Enrolment;
            _scenario.write("Data Source (Enrollment) value in SF : " + data_Source_Enrolment);
            System.out.println("Data Source (Enrollment) value in SF : " + data_Source_Enrolment);
        }
    }

    public void getCurrentStatusDollar_MembershipTier() throws Exception {
        String Mem_pointsCurrentstatusDollar = null;
        try {
            Mem_pointsCurrentstatusDollar = comElement.getText(Mem_points_Current_status_Dollar).trim().replaceAll("[, ;]", "");
        } catch (NullPointerException e) {
            System.out.println("Current status dollars - Membership Tier is Null");
            _scenario.write("Current status dollars - Membership Tier is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting Current status dollars - Membership Tier. - " + ex);
            _scenario.write("Exception occurred when getting Current status dollars - Membership Tier. - " + ex);
        }

        if (Mem_pointsCurrentstatusDollar == null || Mem_pointsCurrentstatusDollar.isEmpty()) {
            Mem_points_Current_status_Dollar_MembershipTier_sf = "";
            _scenario.write("========Current status dollars - Membership Tier=========");
            _scenario.write("Current status dollars - Membership Tier is NOT found in SF");
            System.out.println("Current status dollars - Membership Tier is not available");
        } else {
            Mem_points_Current_status_Dollar_MembershipTier_sf = Mem_pointsCurrentstatusDollar;
            _scenario.write("Current status dollars - Membership Tier value in SF : " + Mem_pointsCurrentstatusDollar);
            System.out.println("Current status dollars - Membership Tier Value in SF : " + Mem_pointsCurrentstatusDollar);
        }
    }

    public void getMemberPoints_MembershipTier() throws Exception {
        String Mem_points_S = null;
        try {
            String Mem_points_actual = comElement.getText(Mem_points).trim();
            Mem_points_actual = Mem_points_actual.replace("Point Balance:", "");
            Mem_points_actual = Mem_points_actual.replace("points", "");
            Mem_points_actual = Mem_points_actual.replaceAll("[, ;]", "");
            Mem_points_S = Mem_points_actual;
        } catch (NullPointerException e) {
            System.out.println("RH side panel - Point Balance is Null");
            _scenario.write("RH side panel - Point Balance is Null");
        } catch (Exception ex) {
            System.out.println("Exception occurred when getting RH side panel - Point Balance. - " + ex);
            _scenario.write("Exception occurred when getting RH side panel - Point Balance. - " + ex);
        }

        if (Mem_points_S == null || Mem_points_S.isEmpty()) {
            memberPoints_MembershipTier_sf = "";
            _scenario.write("========Member Points=========");
            _scenario.write("RH side panel - Point Balance is NOT found in SF");
            System.out.println("RH side panel - Point Balance is not available");
        } else {
            memberPoints_MembershipTier_sf = Mem_points_S;
            _scenario.write("RH side panel - Point Balance value in SF : " + Mem_points_S);
            System.out.println("RH side panel - Point Balance Value in SF : " + Mem_points_S);
        }
    }

    public WebElement getOptInPhoneCheckbox() {
        return OptInPhoneCheckbox;
    }

    public WebElement getOptInMailCheckbox() {
        return OptInMailCheckbox;
    }

    public WebElement getOptInEmailCheckbox() {
        return OptInEmailCheckbox;
    }


    public void getCustomerRegistrationDatetime() throws Exception {

        String enrollmentDatebeforeEdit = comElement.getText(CustomerRegistrationDatetime).trim();

        if (enrollmentDatebeforeEdit.isEmpty() || enrollmentDatebeforeEdit == "") {
            CustomerRegistrationDatetime_sf = "";
            _scenario.write("========CustomerRegistrationDatetime=========");
            _scenario.write(" CustomerRegistrationDatetime is NOT found in SF");
            System.out.println("CustomerRegistrationDatetime is not available in SF");
        } else {

            String trimDate = enrollmentDatebeforeEdit.trim();
            String[] dateParts = trimDate.split("/");
            String day = dateParts[0];
            if (day.toCharArray().length < 2) {
                day = "0" + day;
            }

            String month = dateParts[1];
            if (month.toCharArray().length < 2) {
                month = "0" + month;
            }

            String year = dateParts[2];
            System.out.println(year + "-" + month + "-" + day);
            String enrollmentDate = year + "-" + month + "-" + day;

            CustomerRegistrationDatetime_sf = enrollmentDate;
            _scenario.write("CustomerRegistrationDatetime value in SF : " + enrollmentDate);
            System.out.println("CustomerRegistrationDatetime value IN SF : " + enrollmentDate);
        }
    }

    public void getCustomerLeisureActivity() throws Exception {
        String customerLeisureActivity = textBox.getText(CustomerLeisureActivity);

        if (customerLeisureActivity.isEmpty() || customerLeisureActivity == "") {
            CustomerLeisureActivity_sf = "";
            _scenario.write("========customerLeisureActivity=========");
            _scenario.write(" customerLeisureActivity is NOT found in SF ");
            System.out.println("customerLeisureActivity is not available in SF");
        } else {
            CustomerLeisureActivity_sf = customerLeisureActivity;
            _scenario.write("customerLeisureActivity value in SF :" + customerLeisureActivity);
            System.out.println("customerLeisureActivity value in SF :" + customerLeisureActivity);
        }
    }

    public void getCustomerShoppingPreference() throws Exception {
        String customerShoppingPreference = textBox.getText(CustomerShoppingPreference);

        if (customerShoppingPreference.isEmpty() || customerShoppingPreference == "") {
            CustomerShoppingPreference_sf = "";
            _scenario.write("========customerShoppingPreference=========");
            _scenario.write(" customerShoppingPreference is NOT found in SF");
            System.out.println("customerShoppingPreference is not available in SF");
        } else {
            CustomerShoppingPreference_sf = customerShoppingPreference;
            _scenario.write("customerShoppingPreference value in SF :" + customerShoppingPreference);
            System.out.println("customerShoppingPreference value in SF :" + customerShoppingPreference);
        }
    }

    public void getCustomerPreferredBrands() throws Exception {
        String customerPreferredBrands = textBox.getText(CustomerPreferredBrands);

        if (customerPreferredBrands.isEmpty() || customerPreferredBrands == "") {
            CustomerPreferredBrands_sf = "";
            _scenario.write("========customerPreferredBrands=========");
            _scenario.write(" customerPreferredBrands is NOT found in SF");
            System.out.println("customerPreferredBrands is not available in SF");
        } else {
            CustomerPreferredBrands_sf = customerPreferredBrands;
            _scenario.write("customerPreferredBrands value in SF :" + customerPreferredBrands);
            System.out.println("customerPreferredBrands value in SF :" + customerPreferredBrands);
        }
    }

    public void getLeisureActivitiesMultiple() throws Exception {
        List<String> leisureActivitiesMultiple = null;
        if (selectBox.isSelectHasMultipleOptions(LeisureActivitiesMultipleList)) {
            try {
                leisureActivitiesMultiple = selectBox.getAllSelectedOptions(LeisureActivitiesMultiple);
            } catch (Exception e) {
                _scenario.write(" Unable to read multiple values from Leisure Activities. (Description - " + e + ")");
                System.out.println(" Unable to read multiple values from Leisure Activities. (Description - " + e + ")");
            }
        } else {
            leisureActivitiesMultiple = null;
        }
        String singleValueActivities = "";

        if (leisureActivitiesMultiple != null) {
            for (int i = 0; i < leisureActivitiesMultiple.size(); i++) {
                if (i < leisureActivitiesMultiple.size() - 1) {
                    singleValueActivities = singleValueActivities + leisureActivitiesMultiple.get(i);
                } else {
                    singleValueActivities = singleValueActivities + "|" + leisureActivitiesMultiple.get(i);
                }
            }
        } else { //when there are no multiple values by default
            singleValueActivities = "";
        }

        if (singleValueActivities.isEmpty() || singleValueActivities == "") {
            LeisureActivitiesMultiple_sf = "";
            _scenario.write("========leisureActivitiesMultiple=========");
            _scenario.write(" leisureActivitiesMultiple is NOT found in SF");
            System.out.println("leisureActivitiesMultiple is not available in SF");
        } else {
            LeisureActivitiesMultiple_sf = singleValueActivities;
            _scenario.write("leisureActivitiesMultiple value in SF : " + singleValueActivities);
            System.out.println("leisureActivitiesMultiple value in SF : " + singleValueActivities);
        }
    }

    public void getShoppingPreferencesMultiple() throws Exception {
        List<String> shoppingPreferencesMultiple = null;
        if (selectBox.isSelectHasMultipleOptions(ShoppingPreferencesMultipleList)) {
            try {
                shoppingPreferencesMultiple = selectBox.getAllSelectedOptions(ShoppingPreferencesMultiple);
            } catch (Exception e) {
                _scenario.write(" Unable to read multiple values from Shopping Preferences. (Description - " + e + ")");
                System.out.println(" Unable to read multiple values from Shopping Preferences. (Description - " + e + ")");
            }
        } else {
            shoppingPreferencesMultiple = null;
        }
        String singleValuePreferences = "";

        if (shoppingPreferencesMultiple != null) {
            for (int i = 0; i < shoppingPreferencesMultiple.size(); i++) {
                if (i < shoppingPreferencesMultiple.size() - 1) {
                    singleValuePreferences = singleValuePreferences + shoppingPreferencesMultiple.get(i) + "|";
                } else {
                    singleValuePreferences = singleValuePreferences + shoppingPreferencesMultiple.get(i);
                }
            }
        } else { //when there are no multiple values by default
            singleValuePreferences = "";
        }

        if (singleValuePreferences.isEmpty() || singleValuePreferences == "") {
            ShoppingPreferencesMultiple_sf = "";
            _scenario.write("========ShoppingPreferencesMultiple=========");
            _scenario.write(" ShoppingPreferencesMultiple is NOT found in SF");
            System.out.println("ShoppingPreferencesMultiple is not available in SF");
        } else {
            ShoppingPreferencesMultiple_sf = singleValuePreferences;
            _scenario.write("ShoppingPreferencesMultiple value in SF : " + singleValuePreferences);
            System.out.println("ShoppingPreferencesMultiple value in SF : " + singleValuePreferences);
        }
    }

    public void getPreferredBrandsMultiple() throws Exception {
        readTestData brandMap = new readTestData();
        List<String> preferredBrandsMultiple = null;
        if (selectBox.isSelectHasMultipleOptions(PreferredBrandsMultipleList)) {
            try {
                preferredBrandsMultiple = selectBox.getAllSelectedOptions(PreferredBrandsMultiple);
            } catch (Exception e) {
                _scenario.write(" Unable to read multiple values from Preferred Brands. (Description - " + e + ")");
                System.out.println(" Unable to read multiple values from Preferred Brands. (Description - " + e + ")");
            }
        } else {
            preferredBrandsMultiple = null;
        }
        String singleValueMultipleBrands = "";

        if (preferredBrandsMultiple != null) {
            for (int i = 0; i < preferredBrandsMultiple.size(); i++) {
                String brandCode = brandMap.readDSABrandMapping(preferredBrandsMultiple.get(i));

                if (i < preferredBrandsMultiple.size() - 1) {

                    singleValueMultipleBrands = singleValueMultipleBrands + brandCode + "|";

                } else {
                    singleValueMultipleBrands = singleValueMultipleBrands + brandCode;

                }
            }
        } else {
            singleValueMultipleBrands = "";
        }

        if (singleValueMultipleBrands.isEmpty() || singleValueMultipleBrands == "") {
            PreferredBrandsMultiple_sf = "";
            _scenario.write("========PreferredBrandsMultiple=========");
            _scenario.write(" PreferredBrandsMultiple is NOT found in SF");
            System.out.println("PreferredBrandsMultiple is not available in SF");
        } else {
            PreferredBrandsMultiple_sf = singleValueMultipleBrands;
            _scenario.write("PreferredBrandsMultiple value in SF : " + singleValueMultipleBrands);
            System.out.println("PreferredBrandsMultiple value in SF : " + singleValueMultipleBrands);
        }
    }

    public void getAgeRange() throws Exception {

        String ageRange = selectBox.getDefaultText(AgeRange);

        if (ageRange.isEmpty() || ageRange.equalsIgnoreCase("--None--")) {
            AgeRange_sf = "";
            _scenario.write("========ageRange=========");
            _scenario.write(" ageRange is NOT found in SF");
            System.out.println("ageRange is not available in SF");
        } else {
            AgeRange_sf = ageRange;
            _scenario.write("ageRange value in SF : " + ageRange);
            System.out.println("ageRange value in SF : " + ageRange);
        }
    }

    public void getMarketingSource() throws Exception {
        String marketingSource = selectBox.getDefaultText(MarketingSource);

        if (marketingSource.isEmpty() || marketingSource == "") {
            MarketingSource_sf = "";
            _scenario.write("========marketingSource=========");
            _scenario.write(" marketingSource is NOT found in SF ");
            System.out.println("marketingSource is not available in SF ");
        } else {
            MarketingSource_sf = marketingSource;
            _scenario.write("marketingSource value in SF :" + marketingSource);
            System.out.println("marketingSource value in SF :" + marketingSource);
        }
    }

    public void getZipCodeValidFlag() throws Exception {
        Boolean zipCodeValidFlag = null;
        try {
            zipCodeValidFlag = checkBox.getCheckboxStatus(ZipCodeValidFlag);
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            throw e;

        } catch (NullPointerException ne) {
            System.out.println("The 'valid ZipCode' checkbox status is Null.");
            throw new InterruptedException("Unable to proceed. Can not read 'valid ZipCode' checkbox stats");
        }

        if (!zipCodeValidFlag) { //if unchecked
            ZipCodeValidFlag_sf = "0";

            _scenario.write("zipCodeValidFlag value in SF : " + ZipCodeValidFlag_sf);
            System.out.println("zipCodeValidFlag value in SF : " + ZipCodeValidFlag_sf);
        } else { //if checked
            ZipCodeValidFlag_sf = "1";
            _scenario.write("zipCodeValidFlag value in SF : " + ZipCodeValidFlag_sf);
            System.out.println("zipCodeValidFlag value in SF :" + ZipCodeValidFlag_sf);
        }
    }

    public void getMarketingSourceOthers() throws Exception {
        String marketingSourceOthers = textBox.getText(MarketingSourceOthers);

        if (marketingSourceOthers.isEmpty() || marketingSourceOthers == "") {
            MarketingSourceOthers_sf = "";
            _scenario.write("========marketingSourceOthers=========");
            _scenario.write(" marketingSourceOthers Value in SF - " + MarketingSourceOthers_sf);
            System.out.println(" marketingSourceOthers Value in SF - " + MarketingSourceOthers_sf);
        } else {
            MarketingSourceOthers_sf = marketingSourceOthers;
            _scenario.write("marketingSourceOthers value in SF :" + MarketingSourceOthers_sf);
            System.out.println("marketingSourceOthers value in SF : " + MarketingSourceOthers_sf);
        }
    }


    public String getMemberBasicInformationDetails(String readString) throws Exception {
        String actualValue = null;
        try {
            Thread.sleep(6000);
            _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            if (readString.equalsIgnoreCase("FirstName")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(FirstName);
                System.out.println("FirstName : " + actualValue);
                driver.switchTo().defaultContent();
            } else if (readString.equalsIgnoreCase("LastName")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(LastName);
                System.out.println("LastName : " + actualValue);
                driver.switchTo().defaultContent();
            } else if (readString.equalsIgnoreCase("Email")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(EmailAddressText);
                System.out.println("EmailAddressText : " + actualValue);
                driver.switchTo().defaultContent();
            } else if (readString.equalsIgnoreCase("FirstNameNative")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(FirstNameNative);
                System.out.println("FirstNameNative : " + actualValue);
                driver.switchTo().defaultContent();
            } else if (readString.equalsIgnoreCase("LastNameNative")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(LastNameNative);
                System.out.println("LastNameNative : " + actualValue);
                driver.switchTo().defaultContent();
            } else if (readString.equalsIgnoreCase("Mobile")) {
                comElement.waitForElement(basicInfo_iFrame);
                driver.switchTo().frame(basicInfo_iFrame);
                System.out.println("switch to frame its working ");
                actualValue = getStringValue(ContactNumberText);
                System.out.println("ContactNumberText : " + actualValue);
                driver.switchTo().defaultContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
            _scenario.write(e.getMessage());
            screenshot.takeScreenshot();
        }
        return actualValue;
    }

    public String getStringValue(WebElement element) throws Exception {
        String actualOutput = null;
        try {
            Thread.sleep(5000);
            comElement.waitForElement(element);
            actualOutput = element.getAttribute("value");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            driver.navigate().refresh();
            Thread.sleep(6000);
            comElement.waitForElement(element);
            actualOutput = element.getAttribute("value");
            _scenario.write(e.getMessage());
            screenshot.takeScreenshot();
        }
        return actualOutput;
    }

    public void readMembershipInformation() throws Exception {
        driver.navigate().refresh();
        Thread.sleep(4000);
        driver.switchTo().defaultContent();
        getMemberStatus();
        getCardStatus();
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        try {
            comElement.waitForElement(member_cycle);
        } catch (Exception e) {
            driver.navigate().refresh();
            Thread.sleep(3000);
            comElement.waitForElement(member_cycle);
        }
        comElement.click(member_cycle);
        Thread.sleep(2000);
        driver.switchTo().frame(iframeMemberCycle);
        System.out.println("switch to frame Member Cycle Frame");
        System.out.println("========Membership Information========");
        _scenario.write("========Membership Information========");
        screenshot.takeScreenshot();
        getMemberCardNumber();
        getCardTier();
        getEnrollmentLocation();
        getEnrollmentDate();
        getEntryStatusDollars();
        getFirstPurchaseDate();
        getTierStatusDollars();
        getCarryForwardDollarAmount();
        getCycleStartDate_Formatted();
        getCycleEndDate_Formatted();
        getStatusDollarToRenew_SF();
        getStatusDollarToUpgrade();
        driver.switchTo().defaultContent();
        getCurrentStatusDollar_MembershipTier();
        getMemberPoints_MembershipTier();
        driver.switchTo().defaultContent();
    }

    public void readMembershipCardTab() throws Exception {
        driver.switchTo().defaultContent();
        //Membership Card View for "Prestige Diamond"
        if (CardTier_sf.equalsIgnoreCase("Prestige Diamond")) {
            _scenario.write("========Validating Supplementary Card for Prestige Diamond Tier ========");
            comElement.click(membershipCard_Tab);
            comElement.waitForElement(membershipCardTable);
            screenshot.takeScreenshot();
            List<WebElement> membershipCardTable_trList = membershipCardTable.findElements(By.xpath("//tbody/tr"));
            if (membershipCardTable_trList.size() > 1) {
                _scenario.write("Searching for Current Membership Tier Card Type : Supplementary");
                List<WebElement> supplementaryCardTypeText_element = driver.findElements(By.xpath("//*[contains(text(),'Supplementary')]"));
                if (supplementaryCardTypeText_element.size() > 0) {
                    _scenario.write("Supplementary card record is found");
                    List<WebElement> cardStatusElements = driver.findElements(By.xpath("//*[contains(text(),'Supplementary')]/ancestor::tr/td[2]/div"));
                    String actualCardStatus = null;
                    if (cardStatusElements.size() > 0) {
                        for (WebElement cardStatusElement : cardStatusElements) {
                            actualCardStatus = cardStatusElement.getText();
                        }
                    } else {
                        actualCardStatus = comElement.getText(notDummyCardTableElement);
                    }
                    _scenario.write("Supplementary Card Status verify in Membership Card Tab");
                    _scenario.write("Supplementary Card Expected output : Active");
                    _scenario.write("Supplementary Card Actual output : " + actualCardStatus);
                    System.out.println("Supplementary Card Status verify in Membership Card Tab");
                    System.out.println("Supplementary Card Expected output : Active");
                    System.out.println("Supplementary Card Actual output : " + actualCardStatus);
                    Assert.assertEquals(actualCardStatus, "Active");
                } else {
                    _scenario.write("No Supplementary Card records are found");
                }
            } else {
                _scenario.write("No Membership Card records are found");
            }
        }

    }

    public int switchToPurchaseHistoryTab() throws Exception {
        //Purchase History tab
        try {
            comElement.click(PurchaseHistory);
            Thread.sleep(500);
        } catch (NoSuchElementException e) {
            driver.navigate().refresh();
            Thread.sleep(1000);
            comElement.click(PurchaseHistory);
            Thread.sleep(500);
        }
        String row = null;
        comElement.waitForElement(purchaseHistoryTransactionsTable);
        screenshot.takeScreenshot();
        List<WebElement> rowList = purchaseHistoryTransactionsTable.findElements(By.tagName("tr"));
        rowCountOfPurchaseHistoryTrancactionTableRow = rowList.size();
        return rowList.size();
    }

    public Map<String, String> mainLogicCalculation(int rowCount) throws ParseException {
        Map<String, String> mainLogicMap = new HashMap<>();

        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        if (status_INUSD_c_List == null) {
            for (int i = 1; i < rowCount + 1; i++) {
                String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
                value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
                System.out.println(value);

            }
            for (int i = 1; i < rowCount + 1; i++) {
                String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
                value1.add(PurchaceH_StatusDolla1);
                System.out.println(value1);
            }
        }

        String[] transactionHistory;
        String[] transactionMemberCycle;

        if (status_INUSD_c_List == null) {
            transactionHistory = value.toArray(new String[value.size()]);
            transactionMemberCycle = value1.toArray(new String[value.size()]);
        } else {
            transactionHistory = status_INUSD_c_List.toArray(new String[status_INUSD_c_List.size()]);
            transactionMemberCycle = transaction_Member_Cycle__c.toArray(new String[status_INUSD_c_List.size()]);
        }

        String manualDowngradeTier = "";
        String entryStatusDollar = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String currentTier = "";
        int tierStatusDollar = 0;
        int carryForwardDollar = 0;
        int currentStatusDollar = 0;
        //int totalPoints = 0;
        System.out.println("Array size: " + transactionHistory.length);


        for (int i = 0; i < transactionHistory.length; i++) {                            //Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);

            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            if (i == 0) {                                                        //if first transaction
                currentTier = "LOYAL T";
                carryForwardDollar += currentTransactionAmt;                    //CF = transaction amount
                entryStatusDollar = "0";                                            //Initialize entryStatusDollar to 0 first

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;
                cycleStartDate = firstDateStr;
                cycleEndDate = secondDateStr;

                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            } else {
                tierStatusDollar += currentTransactionAmt;                    //add to tierStatusDollar
            }


            System.out.println("CF$ : " + carryForwardDollar);
            System.out.println("TS$ : " + tierStatusDollar);
            System.out.println("Current Tier: " + currentTier);
            System.out.println("Entry Status Dollars: " + entryStatusDollar);


            if ((tierStatusDollar + carryForwardDollar) >= 5000 && currentTier.equals("LOYAL T")) { //upgrade to Jade

                System.out.println("Upgrading to JADE Tier...");
                currentTier = "JADE";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 5000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "5000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0

                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 15000 && currentTier.equals("JADE")) { //upgrade to prestige ruby

                System.out.println("Upgrading to PRESTIGE RUBY Tier...");
                currentTier = "PRESTIGE RUBY";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 15000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "20000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0

                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 60000 && currentTier.equals("PRESTIGE RUBY")) {

                System.out.println("Upgrading to PRESTIGE DIAMOND Tier...");
                currentTier = "PRESTIGE DIAMOND";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 60000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "80000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0

                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            //For refunds
            if (currentTransactionAmt < 0 && currentTier.equals("JADE") && (tierStatusDollar) < 0) {            //Jade Tier has auto-downgrade
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0; //reset tierStatusDollar to 0
                if (carryForwardDollar < 0) { //CF also -ve
                    //Perform Downgrade for Jade
                    System.out.println("Downgrading to LOYAL T Tier...");
                    currentTier = "LOYAL T";
                    carryForwardDollar = carryForwardDollar + 5000;
                    //downgrade will reset entryStatusDollar to 0
                    entryStatusDollar = "0";
                    //downgrade will reset cycleEndDate and cycleStartDate
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            } else if (currentTransactionAmt < 0 && (tierStatusDollar) < 0) {            //Other tiers will not be auto-downgraded
                System.out.println("Deducting balance from CarryForwardDollar");
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0;
                if (carryForwardDollar < 0) {
                    tierStatusDollar = carryForwardDollar;        //carryForwardDollar will not be -ve
                    carryForwardDollar = 0;
                }

                int tempTotalStatusDollar = 0;
                int tempEntryStatusDollar = 0;
                if (currentTier.equals("LOYAL T")) {
                    tempEntryStatusDollar = 0;
                } else if (currentTier.equals("JADE")) {
                    tempEntryStatusDollar = 5000;
                } else if (currentTier.equals("PRESTIGE RUBY")) {
                    tempEntryStatusDollar = 20000;
                } else if (currentTier.equals("PRESTIGE DIAMOND")) {
                    tempEntryStatusDollar = 80000;
                }

                tempTotalStatusDollar = carryForwardDollar + tempEntryStatusDollar + tierStatusDollar;
                System.out.println("tempTotalStatusDollar -- " + tempTotalStatusDollar);
                if (tempTotalStatusDollar < 5000) {
                    manualDowngradeTier = "LOYAL T";
                } else if (tempTotalStatusDollar >= 5000 && tempTotalStatusDollar < 20000) {
                    manualDowngradeTier = "JADE";
                } else if (tempTotalStatusDollar >= 20000 && tempTotalStatusDollar < 80000) {
                    manualDowngradeTier = "PRESTIGE RUBY";
                } else if (tempTotalStatusDollar >= 80000) {
                    manualDowngradeTier = "PRESTIGE DIAMOND";
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
            }

        }


        String statusDollarToUpgrade = "";

        //Status Dollar Required to Upgrade

        if (currentTier.equals("LOYAL T")) {

            statusDollarToUpgrade = (5000 - (carryForwardDollar + tierStatusDollar)) + "";  // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("JADE")) {

            statusDollarToUpgrade = (15000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("PRESTIGE RUBY")) {

            statusDollarToUpgrade = (60000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }


        String statusDollarToRenew = "";
        int result = 0;

        switch (currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE RUBY":
                result = 10000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE DIAMOND":
                result = 40000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }

                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }

        renew_TierStatusDollar = tierStatusDollar;
        renew_EnrollmentDate = enrollmentDate;
        renew_FirstPurchaseDate = firstPurchaseDate;
        renew_carryForwardDollar = carryForwardDollar;
        renew_currentTier = currentTier;
        renew_EntryStatusDollar = entryStatusDollar;

        //Results
        currentStatusDollar = (carryForwardDollar + tierStatusDollar);
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);
        System.out.println("Tier after manual Downgrade : " + manualDowngradeTier);

        mainLogicMap.put("MembershipTier", currentTier);
        mainLogicMap.put("CarryForwardDollar", String.valueOf(carryForwardDollar));
        mainLogicMap.put("TierStatusDollar", String.valueOf(tierStatusDollar));
        mainLogicMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        mainLogicMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));
        mainLogicMap.put("EntryStatusDollar", entryStatusDollar);
        mainLogicMap.put("CycleStartDate", cycleStartDate);
        mainLogicMap.put("CycleEndDate", cycleEndDate);
        mainLogicMap.put("EnrollmentDate", enrollmentDate);
        mainLogicMap.put("FirstPurchaseDate", firstPurchaseDate);
        mainLogicMap.put("StatusDollarToRenew", statusDollarToRenew);
        mainLogicMap.put("TierAfterManualDowngrade", manualDowngradeTier);

        return mainLogicMap;

    }

    public void validateResults(Map<String, String> mainLogicMap) {

        if (statusDollarToRenew_sf_formatted.contains(",")) {
            String StatusRenewal = statusDollarToRenew_sf_formatted.replaceAll("[, ;]", "");
            String Status1 = StatusRenewal.replaceAll("[, ;]", "");
            DecimalFormat df = new DecimalFormat("#.#####");
            double value2 = Double.parseDouble(Status1);
            statusDollarToRenew_sf_formatted = df.format(value2);
        }

        _scenario.write("========Status Dollar Required to Renew=========");
        _scenario.write("UI Value ===" + statusDollarToRenew_sf_formatted);
        _scenario.write("Expected value ===" + mainLogicMap.get("StatusDollarToRenew"));

        if (statusDollarToRenew_sf_formatted.contains("Any")) {
            assertThat(statusDollarToRenew_sf_formatted, anyOf(
                    containsString("0"),
                    containsString("Any Amount")));
        } else {
            assertEquals(statusDollarToRenew_sf_formatted, mainLogicMap.get("StatusDollarToRenew"));
        }

        _scenario.write("========Current Membership Tier=========");
        _scenario.write("UI Value===" + CardTier_sf.toUpperCase());
        _scenario.write("Expected Value===" + mainLogicMap.get("MembershipTier"));
        softAssert.assertEquals(CardTier_sf.toUpperCase(), mainLogicMap.get("MembershipTier"), "Current Membership Tier : ");

        _scenario.write("========Carry Forward Status Dollar=========");
        _scenario.write("UI Value===" + carryForwardDollarAmount_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("CarryForwardDollar"));
        softAssert.assertEquals(carryForwardDollarAmount_sf, mainLogicMap.get("CarryForwardDollar"), "Carry Forward Status Dollar : ");

        _scenario.write("========Tier Status Dollar=========");
        _scenario.write("UI Value===" + tier_Status_Doll_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("TierStatusDollar"));
        softAssert.assertEquals(tier_Status_Doll_sf, mainLogicMap.get("TierStatusDollar"), "Tier Status Dollar : ");

        _scenario.write("========Status Dollar Required For Upgrade=========");
        _scenario.write("UI Value===" + statusDollarToUpgrade_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("StatusDollarToUpgrade"));
        softAssert.assertEquals(statusDollarToUpgrade_sf, mainLogicMap.get("StatusDollarToUpgrade"));

        _scenario.write("========current Status Dollar=========");
        _scenario.write("UI Value===" + Mem_points_Current_status_Dollar_MembershipTier_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("CurrentStatusDollar"));
        softAssert.assertEquals(Mem_points_Current_status_Dollar_MembershipTier_sf, mainLogicMap.get("CurrentStatusDollar"));

        _scenario.write("========Entry Status Dollar=========");
        _scenario.write("UI Value===" + entry_Status_doll_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("EntryStatusDollar"));
        softAssert.assertEquals(entry_Status_doll_sf, mainLogicMap.get("EntryStatusDollar"), "Entry Status Dollar : ");

        _scenario.write("========Cycle Start Date=========");
        _scenario.write("UI Value===" + CycleStartDate_sf_formattedDate);
        _scenario.write("Expected Value===" + mainLogicMap.get("CycleStartDate"));
        softAssert.assertEquals(CycleStartDate_sf_formattedDate, mainLogicMap.get("CycleStartDate"), "Cycle Start Date : ");

        _scenario.write("========Cycle End Date=========");
        _scenario.write("UI Value===" + CycleEndDate_sf_formattedDate);
        _scenario.write("Expected Value===" + mainLogicMap.get("CycleEndDate"));
        softAssert.assertEquals(CycleEndDate_sf_formattedDate, mainLogicMap.get("CycleEndDate"), "Cycle End Date : ");

        _scenario.write("========Enrollment Date=========");
        _scenario.write("UI Value===" + enroll_Date_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("EnrollmentDate"));
        softAssert.assertEquals(enroll_Date_sf, mainLogicMap.get("EnrollmentDate"), "Enrollment Date : ");

        _scenario.write("========First Purchase Date=========");
        _scenario.write("UI Value===" + first_Purchace_Date_sf);
        _scenario.write("Expected Value===" + mainLogicMap.get("FirstPurchaseDate"));
        softAssert.assertEquals(first_Purchace_Date_sf, mainLogicMap.get("FirstPurchaseDate"), "First Purchase Date : ");

        softAssert.assertAll();
    }

    public Map<String, String> reactivationLogicCalculation(int rowCount) throws ParseException {
        Map<String, String> reactivationLogicMap = new HashMap<>();

        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
            value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
            System.out.println(value);

        }
        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
            value1.add(PurchaceH_StatusDolla1);
            System.out.println(value1);

        }


        String[] transactionHistory = value.toArray(new String[value.size()]);

        String[] transactionMemberCycle = value1.toArray(new String[value.size()]);

        String entryStatusDollar = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String currentTier = "";
        int tierStatusDollar = 0;
        int carryForwardDollar = 0;
        int currentStatusDollar = 0;
        //int totalPoints = 0;
        System.out.println("Array size: " + transactionHistory.length);

        int firstTransaction = 0;

        for (int i = 0; i < transactionHistory.length; i++) {                            //Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);


            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date tDate = new Date();
            String dateTodayStr = formatter.format(tDate);
            Date dateToday = new SimpleDateFormat("yyyy/MM/dd").parse(dateTodayStr);
            System.out.println(dateToday.toString());
            System.out.println(cycleDate2.toString());
            if (i == 0) {

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;

                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);

            }

            if (dateToday.toString().equals(cycleDate1) | dateToday.toString().equals(cycleDate2) | (!(dateToday.before(cycleDate1) | dateToday.after(cycleDate2)))) {
                firstTransaction++;
                System.out.println("-------------" + firstTransaction);
                if (firstTransaction == 1) {                                                        //if first transaction
                    currentTier = "LOYAL T";
                    carryForwardDollar += currentTransactionAmt;                    //CF = transaction amount
                    entryStatusDollar = "0";                                            //Initialize entryStatusDollar to 0 first

                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                    System.out.println("Cycle Start Date: " + cycleStartDate);
                    System.out.println("Cycle End Date: " + cycleEndDate);
                } else {
                    tierStatusDollar += currentTransactionAmt;                    //add to tierStatusDollar
                }


                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);

                if ((tierStatusDollar + carryForwardDollar) >= 5000 && currentTier.equals("LOYAL T")) { //upgrade to Jade

                    System.out.println("Upgrading to JADE Tier...");
                    currentTier = "JADE";
                    carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 5000;
                    tierStatusDollar = 0;

                    if (firstTransaction == 1) {        //if first transaction
                        entryStatusDollar = "5000";
                    } else {
                        entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                        cycleStartDate = firstDateStr;
                        cycleEndDate = secondDateStr;

                    }

                    System.out.println("CF$ : " + carryForwardDollar);
                    System.out.println("TS$ : " + tierStatusDollar);
                    System.out.println("Current Tier: " + currentTier);
                    System.out.println("Entry Status Dollars: " + entryStatusDollar);
                    System.out.println("Cycle Start Date: " + cycleStartDate);
                    System.out.println("Cycle End Date: " + cycleEndDate);
                }

                if ((tierStatusDollar + carryForwardDollar) >= 15000 && currentTier.equals("JADE")) { //upgrade to prestige ruby

                    System.out.println("Upgrading to PRESTIGE RUBY Tier...");
                    currentTier = "PRESTIGE RUBY";
                    carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 15000;
                    tierStatusDollar = 0;

                    if (firstTransaction == 1) {        //if first transaction
                        entryStatusDollar = "20000";
                    } else {
                        entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                        cycleStartDate = firstDateStr;
                        cycleEndDate = secondDateStr;
                    }

                    System.out.println("CF$ : " + carryForwardDollar);
                    System.out.println("TS$ : " + tierStatusDollar);
                    System.out.println("Current Tier: " + currentTier);
                    System.out.println("Entry Status Dollars: " + entryStatusDollar);
                    System.out.println("Cycle Start Date: " + cycleStartDate);
                    System.out.println("Cycle End Date: " + cycleEndDate);
                }

                if ((tierStatusDollar + carryForwardDollar) >= 60000 && currentTier.equals("PRESTIGE RUBY")) {

                    System.out.println("Upgrading to PRESTIGE DIAMOND Tier...");
                    currentTier = "PRESTIGE DIAMOND";
                    carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 60000;
                    tierStatusDollar = 0;

                    if (firstTransaction == 1) {        //if first transaction
                        entryStatusDollar = "80000";
                    } else {
                        entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                        cycleStartDate = firstDateStr;
                        cycleEndDate = secondDateStr;

                    }

                    System.out.println("CF$ : " + carryForwardDollar);
                    System.out.println("TS$ : " + tierStatusDollar);
                    System.out.println("Current Tier: " + currentTier);
                    System.out.println("Entry Status Dollars: " + entryStatusDollar);
                    System.out.println("Cycle Start Date: " + cycleStartDate);
                    System.out.println("Cycle End Date: " + cycleEndDate);
                }

                //For refunds
                if (currentTransactionAmt < 0 && currentTier.equals("JADE") && (tierStatusDollar) < 0) {            //Jade Tier has auto-downgrade
                    carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                    tierStatusDollar = 0; //reset tierStatusDollar to 0
                    if (carryForwardDollar < 0) { //CF also -ve
                        //Perform Downgrade for Jade
                        System.out.println("Downgrading to LOYAL T Tier...");
                        currentTier = "LOYAL T";
                        carryForwardDollar = carryForwardDollar + 5000;
                        //downgrade will reset entryStatusDollar to 0
                        entryStatusDollar = "0";
                        //downgrade will reset cycleEndDate and cycleStartDate
                        cycleStartDate = firstDateStr;
                        cycleEndDate = secondDateStr;

                    }

                    System.out.println("CF$ : " + carryForwardDollar);
                    System.out.println("TS$ : " + tierStatusDollar);
                    System.out.println("Current Tier: " + currentTier);
                    System.out.println("Entry Status Dollars: " + entryStatusDollar);
                    System.out.println("Cycle Start Date: " + cycleStartDate);
                    System.out.println("Cycle End Date: " + cycleEndDate);

                } else if (currentTransactionAmt < 0 && (tierStatusDollar) < 0) {            //Other tiers will not be auto-downgraded
                    System.out.println("Deducting balance from CarryForwardDollar");
                    carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                    tierStatusDollar = 0;
                    if (carryForwardDollar < 0) {
                        tierStatusDollar = carryForwardDollar;        //carryForwardDollar will not be -ve
                        carryForwardDollar = 0;
                    }
                    System.out.println("CF$ : " + carryForwardDollar);
                    System.out.println("TS$ : " + tierStatusDollar);
                    System.out.println("Current Tier: " + currentTier);
                    System.out.println("Entry Status Dollars: " + entryStatusDollar);
                }

            }
        }

        String statusDollarToUpgrade = "";

        //Status Dollar Required to Upgrade

        if (currentTier.equals("LOYAL T")) {

            statusDollarToUpgrade = (5000 - (carryForwardDollar + tierStatusDollar)) + "";  // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("JADE")) {

            statusDollarToUpgrade = (15000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("PRESTIGE RUBY")) {

            statusDollarToUpgrade = (60000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }

        String statusDollarToRenew = "";
        int result = 0;

        switch (currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE RUBY":
                result = 10000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE DIAMOND":
                result = 40000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }

                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }


        //Results
        currentStatusDollar = (carryForwardDollar + tierStatusDollar);
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);

        reactivationLogicMap.put("MembershipTier", currentTier);
        reactivationLogicMap.put("CarryForwardDollar", String.valueOf(carryForwardDollar));
        reactivationLogicMap.put("TierStatusDollar", String.valueOf(tierStatusDollar));
        reactivationLogicMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        reactivationLogicMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));
        reactivationLogicMap.put("EntryStatusDollar", entryStatusDollar);
        reactivationLogicMap.put("CycleStartDate", cycleStartDate);
        reactivationLogicMap.put("CycleEndDate", cycleEndDate);
        reactivationLogicMap.put("EnrollmentDate", enrollmentDate);
        reactivationLogicMap.put("FirstPurchaseDate", firstPurchaseDate);
        reactivationLogicMap.put("StatusDollarToRenew", statusDollarToRenew);

        return reactivationLogicMap;

    }

    public String readPointCalculationInMatrix(String cardNumber, JsonObject myJsonObj) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");

        String queryForGetPointsBal = "select PointsBAL from MatrixTpReward.dbo.card where CardNo like '" + cardNumber + "'";
        HashMap<String, String> matrixDataMap = db.getDatabaseTableRecords(env, queryForGetPointsBal);
        String pointsBAL_DB = matrixDataMap.get("PointsBAL");

        double value = Double.parseDouble(pointsBAL_DB);
        System.out.println("Matrix DB PointsBAL is : " + pointsBAL_DB);

        createDbConnection db_1 = new createDbConnection();
        String queryForGetGracePointsBAL = "select GracePointsBAL from MatrixTpReward.dbo.rewardscyclegrace where CardNo like '" + cardNumber + "'";
        HashMap<String, String> matrixGracePointsBAL_DataMap = db_1.getDatabaseTableRecords(env, queryForGetGracePointsBAL);
        String gracePointsBAL_DB = matrixGracePointsBAL_DataMap.get("GracePointsBAL");
        if (!(gracePointsBAL_DB == null)) {
            System.out.println("Matrix DB GracePointsBAL is : " + gracePointsBAL_DB);
            if (!gracePointsBAL_DB.isEmpty()) {
                value += Double.parseDouble(gracePointsBAL_DB);
            }
        }
        DecimalFormat df = new DecimalFormat("#.#####");
        System.out.println("DB Points Balance is :" + value);
        System.out.println("========Points=========");
        System.out.println("UI Value===" + memberPoints_MembershipTier_sf);
        System.out.println("DB Value===" + df.format(value));
        _scenario.write("========Points=========");
        _scenario.write("UI Value===" + memberPoints_MembershipTier_sf);
        _scenario.write("DB Value===" + df.format(value));

        login_pageobjects.logoutSF();
        return df.format(value);
    }

    public Map<String, String> readMembershipInformationInMatrix(String cardNumber, String scenario, JsonObject myJsonObj, String cardNumMap) throws SQLException, ClassNotFoundException, ParseException, InterruptedException {
        Map<String, String> formattedDataMap = new HashMap<>();
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        System.out.println("====Reading Matrix table " + env);
        boolean dbResults = false;
        //String queryForGetMembershionInfo = "select PointsBAL from MatrixTpReward.dbo.card where CardNo like '"+cardNumber+"'";
        String queryForGetMembershionInfo = "select TOP(1) Tier,StatusDollar,CarryForwardDollar,EntryDollar,StartDate,EndDate,AmtForUpgrade,AmtForRenewal, MovementType, Remarks from " +
                "MatrixTpReward.dfs.DFS_MembershipCycle where MemberID in (select MemberID from MatrixTpReward.dbo.card where CardNo like '" + cardNumber + "') order by StartDate desc;";
        HashMap<String, String> matrixDataMap = db.getDatabaseTableRecords(env, queryForGetMembershionInfo);

        if (!(matrixDataMap.get("Tier") == null)) {
            dbResults = true;
            String db_Tier = matrixDataMap.get("Tier");
            String db_StatusDollar = matrixDataMap.get("StatusDollar");
            String db_CarryForwardDollar = matrixDataMap.get("CarryForwardDollar");
            String db_EntryDollar = matrixDataMap.get("EntryDollar");
            String db_StartDate = matrixDataMap.get("StartDate").substring(0, 10);
            String db_EndDate = matrixDataMap.get("EndDate").substring(0, 10);
            String db_AmtForUpgrade = matrixDataMap.get("AmtForUpgrade");
            String db_AmtForRenewal = matrixDataMap.get("AmtForRenewal");
            String db_MovementType = matrixDataMap.get("MovementType");
            String db_Remarks = matrixDataMap.get("Remarks");

            Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(db_StartDate);
            db_StartDate = new SimpleDateFormat("dd MMM yyyy").format(myDate);
            Date myeDate = new SimpleDateFormat("yyyy-MM-dd").parse(db_EndDate);
            db_EndDate = new SimpleDateFormat("dd MMM yyyy").format(myeDate);

            DecimalFormat df = new DecimalFormat("#.#####");
            double value1 = Double.parseDouble(db_StatusDollar);
            String DB_StatusDollar = df.format(value1);
            double value2 = Double.parseDouble(db_CarryForwardDollar);
            String DB_CarryForwardDollar = df.format(value2);
            double value3 = Double.parseDouble(db_EntryDollar);
            String DB_EntryDollar = df.format(value3);
            String DB_amtTU = "";
            if (!db_Tier.equalsIgnoreCase("PRESTIGE DIAMOND")) {
                double value4 = Double.parseDouble(db_AmtForUpgrade);
                DB_amtTU = df.format(value4);
            }

            String featurename = _scenario.getId();
            System.out.println("Feature File name" + featurename);
            if (featurename.contains("Merge_Card") && tier.equalsIgnoreCase("Diamond")){
                if (db_AmtForUpgrade.contains("0")){
                    DB_amtTU = "0";
                }
            }

            if (featurename.contains("Merge_Card") && tier.equalsIgnoreCase("All") && cardNumMap.contains("Diamond")){
                if (db_AmtForUpgrade.contains("0")){
                    DB_amtTU = "0";
                }
            }

            System.out.println("Tier DB : " + db_Tier);
            System.out.println("StatusDollar DB : " + DB_StatusDollar);
            System.out.println("CarryForwardDollar DB : " + DB_CarryForwardDollar);
            System.out.println("EntryDollar DB : " + DB_EntryDollar);
            System.out.println("StartDate DB : " + db_StartDate);
            System.out.println("EndDate DB : " + db_EndDate);
            System.out.println("AmtForUpgrade DB : " + DB_amtTU);
            System.out.println("AmtForRenewal DB : " + db_AmtForRenewal);
            System.out.println("MovementType DB : " + db_MovementType);
            System.out.println("Remarks DB : " + db_Remarks);
            _scenario.write("Tier DB : " + db_Tier);
            _scenario.write("StatusDollar DB : " + DB_StatusDollar);
            _scenario.write("CarryForwardDollar DB : " + DB_CarryForwardDollar);
            _scenario.write("EntryDollar DB : " + DB_EntryDollar);
            _scenario.write("StartDate DB : " + db_StartDate);
            _scenario.write("EndDate DB : " + db_EndDate);
            _scenario.write("AmtForUpgrade DB : " + DB_amtTU);
            _scenario.write("AmtForRenewal DB : " + db_AmtForRenewal);
            _scenario.write("MovementType DB : " + db_MovementType);
            _scenario.write("Remarks DB : " + db_Remarks);

            formattedDataMap.put("Tier", db_Tier);
            formattedDataMap.put("StatusDollar", DB_StatusDollar);
            formattedDataMap.put("CarryForwardDollar", DB_CarryForwardDollar);
            formattedDataMap.put("EntryDollar", DB_EntryDollar);
            formattedDataMap.put("StartDate", db_StartDate);
            formattedDataMap.put("EndDate", db_EndDate);
            formattedDataMap.put("AmtForUpgrade", DB_amtTU);
            formattedDataMap.put("AmtForRenewal", db_AmtForRenewal);
            formattedDataMap.put("MovementType", db_MovementType);
            formattedDataMap.put("Remarks", db_Remarks);
        } else {
            System.out.println("There is no Membership Information record for this member in Matrix Table");
            _scenario.write("There is no Membership Information record for this member in Matrix Table");
            Assert.assertEquals(true, dbResults);
        }

        return formattedDataMap;
    }

    public ArrayList<ArrayList<String>> getMembershipCardTableRecords() throws Exception {
        driver.navigate().refresh();
        Thread.sleep(4000);
        driver.switchTo().defaultContent();
        //getMemberStatus();
        //getCardStatus();
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        try {
            comElement.waitForElement(member_cycle);
        } catch (Exception e) {
            driver.navigate().refresh();
            Thread.sleep(3000);
            comElement.waitForElement(member_cycle);
        }
        comElement.click(member_cycle);
        Thread.sleep(2000);
        comElement.click(membershipCard_Tab);
        Thread.sleep(4000);
        comElement.waitForElement(membershipCardTable);
        screenshot.takeScreenshot();
        ArrayList<ArrayList<String>> tableArrayList = new ArrayList<ArrayList<String>>();
        try {
            List<WebElement> membershipCardTable_trList = membershipCardTable.findElements(By.xpath("//tbody/tr"));
            if (membershipCardTable_trList.size() > 1) {
                List<WebElement> supplementaryCardTypeText_element = driver.findElements(By.xpath("//*[contains(text(),'Supplementary')]"));
                if (supplementaryCardTypeText_element.size() > 0) {
                    CardTier_sf = "Prestige Diamond";
                }
                for (int i = 1; i <= membershipCardTable_trList.size(); i++) {
                    ArrayList<String> rowDataArrayList = new ArrayList<>();
                    String cardNumber = driver.findElement(By.xpath("//*[contains(text(),'Membership Card Number')]/ancestor::table/tbody/tr[" + i + "]/th/span")).getText();
                    System.out.println("cardNumber :" + cardNumber);
                    rowDataArrayList.add(cardNumber);
                    String cardStatus = driver.findElement(By.xpath("//*[contains(text(),'Membership Card Number')]/ancestor::table/tbody/tr[" + i + "]/td[1]/span")).getText();
                    System.out.println("cardStatus :" + cardStatus);
                    rowDataArrayList.add(cardStatus);
                    String cardType = driver.findElement(By.xpath("//*[contains(text(),'Membership Card Number')]/ancestor::table/tbody/tr[" + i + "]/td[2]/span")).getText();
                    System.out.println("cardType :" + cardType);
                    rowDataArrayList.add(cardType);
                    tableArrayList.add(rowDataArrayList);
                }
            } else {
                _scenario.write("No records in Membership Card Table");
                System.out.println("No records in Membership Card Table");
            }
        } catch (Exception e) {
            System.out.println("Exception Found in Membership Card Table : " + e.getMessage());
        }
        System.out.println(tableArrayList);
        return tableArrayList;
    }

    public void clickOnMemberRequest(String memberRequest) throws Exception {
        try {
            comElement.click(memberRequests);
            Thread.sleep(500);
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot();
            driver.navigate().refresh();
            Thread.sleep(1000);
            comElement.click(memberRequests);
            Thread.sleep(500);
        }
        Thread.sleep(3000);
        comElement.waitForElement(memberRequestTable);
        screenshot.takeScreenshot();
        List<WebElement> membershipCardRequestTable_trList = memberRequestTable.findElements(By.xpath("//tbody/tr"));
        System.out.println("Tr List" + membershipCardRequestTable_trList.size());
        if (membershipCardRequestTable_trList.size() >= 1) {
            for (int i = 1; i <= membershipCardRequestTable_trList.size(); i++) {
                WebElement titleElement = memberRequestTable.findElement(By.xpath("//tbody/tr[" + i + "]/td[1]/a"));
                String requestTitle = titleElement.getText();
                System.out.println("Web Request :- " + requestTitle);
                if (requestTitle.contains(memberRequest)) {
                    Thread.sleep(3000);
                    WebElement titleElement_TD = memberRequestTable.findElement(By.xpath("//tbody/tr[" + i + "]/th/div/a"));
                    comElement.click(titleElement_TD);
                    screenshot.takeScreenshot();
                    break;
                }
            }
        } else {
            _scenario.write("No records in Membership Request Table");
            System.out.println("No records in Membership Request Table");
        }
    }

    public String getMemberBlackList() throws Exception {
        String message = null;
        try {
            comElement.waitForElement(blackList_Button);
            comElement.click(blackList_Button);
            screenshot.takeScreenshot();
            comElement.waitForElement(confirm_Button);
            comElement.click(confirm_Button);
            screenshot.takeScreenshot();
            comElement.waitForElement(validationMessageText);
            screenshot.takeScreenshot();
            message = comElement.getText(validationMessageText);
        } catch (NoSuchElementException e) {
            e.getMessage();
            _scenario.write("No confirmation message loaded.");
            System.out.println("No confirmation message loaded.");
        }
        return message;
    }

    public String getMemberResume() throws Exception {
        String message = null;
        try {
            comElement.waitForElement(resume_Button);
            comElement.click(resume_Button);
            screenshot.takeScreenshot();
            comElement.waitForElement(confirm_Button);
            comElement.click(confirm_Button);
            screenshot.takeScreenshot();
            comElement.waitForElement(validationMessageText);
            screenshot.takeScreenshot();
            message = comElement.getText(validationMessageText);
        } catch (NoSuchElementException e) {
            e.getMessage();
            _scenario.write("No confirmation message loaded.");
            System.out.println("No confirmation message loaded.");
        }
        return message;
    }

    public int getTransactionAssociationValues(String cardNumber, JsonObject myJsonObj) throws IOException, InterruptedException {
        status_INUSD_c_List = new ArrayList<>();
        transaction_Member_Cycle__c = new ArrayList<>();
        Thread.sleep(40000);
        String env = System.getProperty("Environment");
        System.out.println("------" + env + "------");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String id = "";
        String secret = "";
        String URL1 = "";

        if (env.equalsIgnoreCase("Preprod")) {
            id = myJsonObj.get("Preprod_client_id").getAsString();
            secret = myJsonObj.get("Preprod_client_secret").getAsString();
            URL1 = myJsonObj.get("Preprod_URL1").getAsString();

            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")) {

            id = myJsonObj.get("QACore2_client_id").getAsString();
            secret = myJsonObj.get("QACore2_client_secret").getAsString();
            URL1 = myJsonObj.get("QACore2_URL1").getAsString();

            System.out.println("The Execution Environment is:QACORE2");
        }

        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=" + id + "&client_secret=" + secret + "&username=" + myJsonObj.get(env).getAsString() + "&password=" + myJsonObj.get("SFDCAdmin_Pwd").getAsString() + "");
        Request request = new Request.Builder()
                .url(URL1)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        JSONObject jsonObj = new JSONObject(jsonData);

        access_token = jsonObj.getString("access_token");
        System.out.println("The status code recieved: " + access_token);
        MediaType mediaType1 = MediaType.parse("application/json");

        String url = "";
        if (env.equalsIgnoreCase("Preprod")) {
            url = "https://dfscrm--preprod.cs76.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")) {
            url = "https://dfscrm--qacore2.cs31.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: QACORE2");
        }
        Request request2 = new Request.Builder()
                .url(url + "?q=SELECT Status_in_USD__c, Transaction_Member_Cycle__c FROM Asset WHERE Membership_Cycle__r.Member__r.Membership_Card_Number__c = '" + myJsonObj.get(cardNumber).getAsString() + "' AND ParentId = NULL  order by PurchaseDate ASC")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token + "")
                .addHeader("x-http-method-override", "DELETE")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response3 = client.newCall(request2).execute();

        String jsonData2 = response3.body().string();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + jsonData2);
        JSONObject Jobject2 = new JSONObject(jsonData2);
        JSONArray Jarray = Jobject2.getJSONArray("records");
        //String cycleID = "";
        String Status_in_USD__c = null;
        String Transaction_Member_Cycle__c = "";
        for (int i = 0; i < Jarray.length(); i++) {
            JSONObject object = Jarray.getJSONObject(i);
            System.out.println(object.get("Status_in_USD__c"));
            System.out.println(object.get("Transaction_Member_Cycle__c"));
            Status_in_USD__c = object.get("Status_in_USD__c").toString();
            Transaction_Member_Cycle__c = object.get("Transaction_Member_Cycle__c").toString();
        }
        Thread.sleep(40000);
        System.out.println("Status_in_USD__c: " + Status_in_USD__c);
        System.out.println("Transaction_Member_Cycle__c : " + Transaction_Member_Cycle__c);
        System.out.println("Status_in_USD__c: in - value " + Status_in_USD__c);

        if (Status_in_USD__c == null) {
            Assert.fail();
            _scenario.write("Status_in_USD__c is null");
            System.out.println("Status_in_USD__c is null");
        }

        Status_in_USD__c = Status_in_USD__c.split("\\.")[0].replaceAll(",", "");
        status_INUSD_c_List.add(Status_in_USD__c);
        transaction_Member_Cycle__c.add(Transaction_Member_Cycle__c);
        System.out.println("Status_in_USD__c list: " + status_INUSD_c_List);
        System.out.println("Transaction_Member_Cycle__c list: " + transaction_Member_Cycle__c);
        return transaction_Member_Cycle__c.size();
    }

    public int getTransactionDisassociationValues(String cardNumber, JsonObject myJsonObj) throws IOException, InterruptedException {
        Thread.sleep(40000);
        String env = System.getProperty("Environment");
        System.out.println("------" + env + "------");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String id = "";
        String secret = "";
        String URL1 = "";

        if (env.equalsIgnoreCase("Preprod")) {
            id = myJsonObj.get("Preprod_client_id").getAsString();
            secret = myJsonObj.get("Preprod_client_secret").getAsString();
            URL1 = myJsonObj.get("Preprod_URL1").getAsString();

            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")) {

            id = myJsonObj.get("QACore2_client_id").getAsString();
            secret = myJsonObj.get("QACore2_client_secret").getAsString();
            URL1 = myJsonObj.get("QACore2_URL1").getAsString();

            System.out.println("The Execution Environment is:QACORE2");
        }

        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=" + id + "&client_secret=" + secret + "&username=" + myJsonObj.get(env).getAsString() + "&password=" + myJsonObj.get("SFDCAdmin_Pwd").getAsString() + "");
        Request request = new Request.Builder()
                .url(URL1)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        JSONObject jsonObj = new JSONObject(jsonData);

        access_token = jsonObj.getString("access_token");
        System.out.println("The status code recieved: " + access_token);
        MediaType mediaType1 = MediaType.parse("application/json");

        String url = "";
        if (env.equalsIgnoreCase("Preprod")) {
            url = "https://dfscrm--preprod.cs76.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")) {
            url = "https://dfscrm--qacore2.cs31.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: QACORE2");
        }
        Request request2 = new Request.Builder()
                .url(url + "?q=SELECT Status_in_USD__c, Transaction_Member_Cycle__c FROM Asset WHERE Membership_Cycle__r.Member__r.Membership_Card_Number__c = '" + myJsonObj.get(cardNumber).getAsString() + "' AND ParentId = NULL  order by PurchaseDate ASC")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token + "")
                .addHeader("x-http-method-override", "DELETE")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response3 = client.newCall(request2).execute();

        String jsonData2 = response3.body().string();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + jsonData2);
        JSONObject Jobject2 = new JSONObject(jsonData2);
        JSONArray Jarray = Jobject2.getJSONArray("records");
        //String cycleID = "";
        String Status_in_USD__c = null;
        String Transaction_Member_Cycle__c = "";
        for (int i = 0; i < Jarray.length(); i++) {
            JSONObject object = Jarray.getJSONObject(i);
            System.out.println(object.get("Status_in_USD__c"));
            System.out.println(object.get("Transaction_Member_Cycle__c"));
            Status_in_USD__c = object.get("Status_in_USD__c").toString();
            Transaction_Member_Cycle__c = object.get("Transaction_Member_Cycle__c").toString();
        }
        Thread.sleep(40000);
        System.out.println("Status_in_USD__c: " + Status_in_USD__c);
        System.out.println("Transaction_Member_Cycle__c : " + Transaction_Member_Cycle__c);
        System.out.println("Status_in_USD__c: in - value " + Status_in_USD__c);

        if (Status_in_USD__c == null) {
            Assert.fail();
            _scenario.write("Status_in_USD__c is null");
            System.out.println("Status_in_USD__c is null");
        }

        Status_in_USD__c = Status_in_USD__c.split("\\.")[0].replaceAll(",", "");
        status_INUSD_c_List.add("-" + Status_in_USD__c);
        System.out.println("Asso list size >> Transaction_Member_Cycle__c : " + transaction_Member_Cycle__c.size());
        String finalDisassociationString = null;
        for (String memberCycleAsso : transaction_Member_Cycle__c) {
            String disSecondPart = Transaction_Member_Cycle__c.split(" - ")[1];
            String assoFirstPart = memberCycleAsso.split(" - ")[0];
            StringBuffer sb = new StringBuffer(assoFirstPart).append(" - ").append(disSecondPart);
            finalDisassociationString = sb.toString();
            System.out.println("Final Dis >> Transaction_Member_Cycle__c : " + finalDisassociationString);
        }

        transaction_Member_Cycle__c.remove(0);
        transaction_Member_Cycle__c.add(finalDisassociationString);
        transaction_Member_Cycle__c.add(Transaction_Member_Cycle__c);

        System.out.println("Status_in_USD__c list: " + status_INUSD_c_List);
        System.out.println("Transaction_Member_Cycle__c list: " + transaction_Member_Cycle__c);
        return transaction_Member_Cycle__c.size();
    }

    public void approveAllPendingRequests() throws Exception {
        String message = null;
        try {
            int count = 1;
            boolean approveButtonVisibility = true;
            while (approveButtonVisibility == true) {
                List<WebElement> approveButtonElement = driver.findElements(By.xpath("//*[contains(text(),'Approve')]/ancestor::li/a/div"));
                WebElement RelatedTitleTxt = driver.findElement(By.xpath("//span[text()='Related' and @class='title']"));

                if (approveButtonElement.size() >= 1) {
                    screenshot.takeScreenshot();

                    try {
                        comElement.waitForElement(approve_Button);
                    } catch (NoSuchElementException e) {
                        System.out.println("Unable to wait for element. Retrying again");
                        comElement.moveMouseAndClick(RelatedTitleTxt);
                        Thread.sleep(3000);
                        comElement.waitForElement(approve_Button);
                    }

                    try {
                        comElement.click(approve_Button);
                    } catch (NoSuchElementException e) {
                        System.out.println("Unable to click on 'Approve' button, retrying again");
                        comElement.moveMouseAndClick(RelatedTitleTxt);
                        Thread.sleep(3000);
                        comElement.click(approve_Button);
                    }
                    Thread.sleep(4000);
                    textBox.setText(approve_comments_textArea, "Request approved :- " + count);
                    comElement.waitForElement(approveButton_comments_textArea);
                    comElement.click(approveButton_comments_textArea);
                    System.out.println("Request approved :- " + count);
                    count++;
                    Thread.sleep(8000);
                } else {
                    approveButtonVisibility = false;
                    screenshot.takeScreenshot();
                    System.out.println("All the request are approved no more request.");
                }
            }
        } catch (NoSuchElementException e) {
            e.getMessage();
            System.out.println("No Approve button loaded.");
        }
    }

    public HashMap<String, String> manualDowngradeLogic(int rowCount) throws ParseException {

        HashMap<String, String> manualDowngradeLoginMap = new HashMap<>();
        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
            value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
            System.out.println(value);

        }
        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
            value1.add(PurchaceH_StatusDolla1);
            System.out.println(value1);

        }


        String[] transactionHistory = value.toArray(new String[value.size()]);
        String[] transactionMemberCycle = value1.toArray(new String[value.size()]);

        String entryStatusDollar = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String membershipStatus = "Active";
        String currentTier = "";
        int tierStatusDollar = 0;
        int carryForwardDollar = 0;
        int currentStatusDollar = 0;
        //int totalPoints = 0;
        System.out.println("Array size: " + transactionHistory.length);


        for (int i = 0; i < transactionHistory.length; i++) {                            //Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);

            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            if (i == 0) {                                                        //if first transaction
                currentTier = "LOYAL T";                    //Set the first transaction date
                carryForwardDollar += currentTransactionAmt;                    //CF = transaction amount
                entryStatusDollar = "0";                                            //Initialize entryStatusDollar to 0 first

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;
                cycleStartDate = firstDateStr;
                cycleEndDate = secondDateStr;

                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            }
            //else if(firstTransactionDate.equals(transactionMemberCycle[i])){		// if the second line of transaction falls on the same date
            //	carryForwardDollar += currentTransactionAmt;					// add the transaction amount to CF$
            else {
                tierStatusDollar += currentTransactionAmt;                    //add to tierStatusDollar
            }


            System.out.println("CF$ : " + carryForwardDollar);
            System.out.println("TS$ : " + tierStatusDollar);
            System.out.println("Current Tier: " + currentTier);
            System.out.println("Entry Status Dollars: " + entryStatusDollar);


            if ((tierStatusDollar + carryForwardDollar) >= 5000 && currentTier.equals("LOYAL T")) { //upgrade to Jade

                System.out.println("Upgrading to JADE Tier...");
                currentTier = "JADE";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 5000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "5000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 15000 && currentTier.equals("JADE")) { //upgrade to prestige ruby

                System.out.println("Upgrading to PRESTIGE RUBY Tier...");
                currentTier = "PRESTIGE RUBY";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 15000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "20000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 60000 && currentTier.equals("PRESTIGE RUBY")) {

                System.out.println("Upgrading to PRESTIGE DIAMOND Tier...");
                currentTier = "PRESTIGE DIAMOND";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 60000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "80000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            //For refunds
            if (currentTransactionAmt < 0 && currentTier.equals("JADE") && (tierStatusDollar) < 0) {            //Jade Tier has auto-downgrade
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0; //reset tierStatusDollar to 0
                if (carryForwardDollar < 0) { //CF also -ve
                    //Perform Downgrade for Jade
                    System.out.println("Downgrading to LOYAL T Tier...");
                    currentTier = "LOYAL T";
                    carryForwardDollar = carryForwardDollar + 5000;
                    //downgrade will reset entryStatusDollar to 0
                    entryStatusDollar = "0";
                    //downgrade will reset cycleEndDate and cycleStartDate
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            } else if (currentTransactionAmt < 0 && (tierStatusDollar) < 0) {            //Other tiers will not be auto-downgraded
                System.out.println("Deducting balance from CarryForwardDollar");
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0;

                //if(carryForwardDollar < 0 ){
                //    tierStatusDollar = carryForwardDollar;		//carryForwardDollar will not be -ve
                //    carryForwardDollar = 0;
                //}

                Calendar cal = Calendar.getInstance();
                Date tDate = new Date();
                cal.setTime(tDate);
                String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(tDate);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.YEAR, 2);
                String cycleEndDateStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
                //System.out.println(dateTodayStr2);

                if (carryForwardDollar <= 0) { //CF also -ve
                    //Perform Downgrade
                    int tempEntryStatusDollar = 0;
                    int tempTotalStatusDollar = 0;
                    if (currentTier.equals("PRESTIGE RUBY")) {
                        tempEntryStatusDollar = 20000;
                    } else if (currentTier.equals("PRESTIGE DIAMOND")) {
                        tempEntryStatusDollar = 80000;
                    }

                    tempTotalStatusDollar = carryForwardDollar + tempEntryStatusDollar + tierStatusDollar;
                    System.out.println("tempTotalStatusDollar -- " + tempTotalStatusDollar);
                    if (tempTotalStatusDollar < 5000) {
                        currentTier = "LOYAL T";
                        carryForwardDollar = tempTotalStatusDollar;
                        entryStatusDollar = "0";


                        if (tempTotalStatusDollar <= 0) {
                            membershipStatus = "Inactive";
                            cycleStartDate = dateTodayStr;
                            cycleEndDate = dateTodayStr;
                        } else {
                            //TBC Partial refund
                            cycleStartDate = dateTodayStr;
                            cycleEndDate = cycleEndDateStr;
                            //TBC
                        }
                    } else if (tempTotalStatusDollar >= 5000 && tempTotalStatusDollar < 20000) {
                        currentTier = "JADE";
                        carryForwardDollar = tempTotalStatusDollar - 5000;
                        entryStatusDollar = "0";
                        cycleStartDate = dateTodayStr;
                        cycleEndDate = cycleEndDateStr;
                        ;
                    } else if (tempTotalStatusDollar >= 20000 && tempTotalStatusDollar < 80000) {
                        currentTier = "PRESTIGE RUBY";
                        carryForwardDollar = tempTotalStatusDollar - 20000;
                        entryStatusDollar = "0";
                        cycleStartDate = dateTodayStr;
                        cycleEndDate = cycleEndDateStr;
                    } else if (tempTotalStatusDollar >= 80000) {
                        currentTier = "PRESTIGE DIAMOND";
                        carryForwardDollar = tempTotalStatusDollar - 80000;
                        entryStatusDollar = "0";
                        cycleStartDate = dateTodayStr;
                        cycleEndDate = cycleEndDateStr;
                    }


                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
            }

        }


        String statusDollarToUpgrade = "";

        //Status Dollar Required to Upgrade

        if (currentTier.equals("LOYAL T")) {

            statusDollarToUpgrade = (5000 - (carryForwardDollar + tierStatusDollar)) + "";  // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("JADE")) {

            statusDollarToUpgrade = (15000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("PRESTIGE RUBY")) {

            statusDollarToUpgrade = (60000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }

        String statusDollarToRenew = "";
        int result = 0;

        switch (currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE RUBY":
                result = 10000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE DIAMOND":
                result = 40000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }

                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }


        //Results
        currentStatusDollar = (carryForwardDollar + tierStatusDollar);
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);

        manualDowngradeLoginMap.put("MembershipTier", currentTier);
        manualDowngradeLoginMap.put("CarryForwardDollar", String.valueOf(carryForwardDollar));
        manualDowngradeLoginMap.put("TierStatusDollar", String.valueOf(tierStatusDollar));
        manualDowngradeLoginMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        manualDowngradeLoginMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));
        manualDowngradeLoginMap.put("EntryStatusDollar", entryStatusDollar);
        manualDowngradeLoginMap.put("CycleStartDate", cycleStartDate);
        manualDowngradeLoginMap.put("CycleEndDate", cycleEndDate);
        manualDowngradeLoginMap.put("EnrollmentDate", enrollmentDate);
        manualDowngradeLoginMap.put("FirstPurchaseDate", firstPurchaseDate);
        manualDowngradeLoginMap.put("StatusDollarToRenew", statusDollarToRenew);

        return manualDowngradeLoginMap;
    }

    public HashMap<String, String> newMemberRefundLogic(int rowCount) throws ParseException {
        HashMap<String, String> memberRefundLogicInMap = new HashMap<>();

        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
            value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
            System.out.println(value);
        }
        for (int i = 1; i < rowCount + 1; i++) {
            String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
            value1.add(PurchaceH_StatusDolla1);
            System.out.println(value1);
        }

        String[] transactionHistory = value.toArray(new String[value.size()]);
        String[] transactionMemberCycle = value1.toArray(new String[value.size()]);

        String manualDowngradeTier = "";
        String entryStatusDollar = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String membershipStatus = "Active";
        String currentTier = "";
        int tierStatusDollar = 0;
        int carryForwardDollar = 0;
        int currentStatusDollar = 0;
        //int totalPoints = 0;
        System.out.println("Array size: " + transactionHistory.length);


        for (int i = 0; i < transactionHistory.length; i++) {                            //Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);

            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            if (i == 0) {                                                        //if first transaction
                currentTier = "LOYAL T";                    //Set the first transaction date
                carryForwardDollar += currentTransactionAmt;                    //CF = transaction amount
                entryStatusDollar = "0";                                            //Initialize entryStatusDollar to 0 first

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;
                cycleStartDate = firstDateStr;
                cycleEndDate = secondDateStr;

                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            }
            //else if(firstTransactionDate.equals(transactionMemberCycle[i])){		// if the second line of transaction falls on the same date
            //	carryForwardDollar += currentTransactionAmt;					// add the transaction amount to CF$
            else {
                tierStatusDollar += currentTransactionAmt;                    //add to tierStatusDollar
            }


            System.out.println("CF$ : " + carryForwardDollar);
            System.out.println("TS$ : " + tierStatusDollar);
            System.out.println("Current Tier: " + currentTier);
            System.out.println("Entry Status Dollars: " + entryStatusDollar);


            if ((tierStatusDollar + carryForwardDollar) >= 5000 && currentTier.equals("LOYAL T")) { //upgrade to Jade

                System.out.println("Upgrading to JADE Tier...");
                currentTier = "JADE";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 5000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "5000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 15000 && currentTier.equals("JADE")) { //upgrade to prestige ruby

                System.out.println("Upgrading to PRESTIGE RUBY Tier...");
                currentTier = "PRESTIGE RUBY";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 15000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "20000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if ((tierStatusDollar + carryForwardDollar) >= 60000 && currentTier.equals("PRESTIGE RUBY")) {

                System.out.println("Upgrading to PRESTIGE DIAMOND Tier...");
                currentTier = "PRESTIGE DIAMOND";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 60000;
                tierStatusDollar = 0;

                if (i == 0) {        //if first transaction
                    entryStatusDollar = "80000";
                } else {
                    entryStatusDollar = "0";    //if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            //For refunds
            if (currentTransactionAmt < 0 && currentTier.equals("JADE") && (tierStatusDollar) < 0) {            //Jade Tier has auto-downgrade
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0; //reset tierStatusDollar to 0
                if (carryForwardDollar < 0) { //CF also -ve
                    //Perform Downgrade for Jade
                    System.out.println("Downgrading to LOYAL T Tier...");
                    currentTier = "LOYAL T";
                    carryForwardDollar = carryForwardDollar + 5000;
                    //downgrade will reset entryStatusDollar to 0
                    entryStatusDollar = "0";
                    //downgrade will reset cycleEndDate and cycleStartDate
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            } else if (currentTransactionAmt < 0 && (tierStatusDollar) < 0) {            //Other tiers will not be auto-downgraded
                System.out.println("Deducting balance from CarryForwardDollar");
                carryForwardDollar = carryForwardDollar + tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0;
                //if(carryForwardDollar < 0 ){
                //	tierStatusDollar = carryForwardDollar;		//carryForwardDollar will not be -ve
                //	carryForwardDollar = 0;
                //}
                int tempTotalStatusDollar = 0;
                int tempEntryStatusDollar = 0;
                if (currentTier.equals("LOYAL T")) {
                    tempEntryStatusDollar = 0;
                } else if (currentTier.equals("JADE")) {
                    tempEntryStatusDollar = 5000;
                } else if (currentTier.equals("PRESTIGE RUBY")) {
                    tempEntryStatusDollar = 20000;
                } else if (currentTier.equals("PRESTIGE DIAMOND")) {
                    tempEntryStatusDollar = 80000;
                }

                tempTotalStatusDollar = carryForwardDollar + tempEntryStatusDollar + tierStatusDollar;
                System.out.println("tempTotalStatusDollar -- " + tempTotalStatusDollar);
                if (tempTotalStatusDollar < 5000) {
                    manualDowngradeTier = "LOYAL T";
                } else if (tempTotalStatusDollar >= 5000 && tempTotalStatusDollar < 20000) {
                    manualDowngradeTier = "JADE";
                } else if (tempTotalStatusDollar >= 20000 && tempTotalStatusDollar < 80000) {
                    manualDowngradeTier = "PRESTIGE RUBY";
                } else if (tempTotalStatusDollar >= 80000) {
                    manualDowngradeTier = "PRESTIGE DIAMOND";
                }

                if (tempTotalStatusDollar < 5000) {
                    currentTier = "LOYAL T";
                    carryForwardDollar = tempTotalStatusDollar;
                    entryStatusDollar = "0";

                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    //Date tDate = new Date();
                    ///String dateTodayStr = formatter.format(tDate);
                    //String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(tDate);

                    //if(tempTotalStatusDollar <=0 ){
                    //	membershipStatus = "Inactive";
                    //	cycleStartDate = dateTodayStr;
                    //	cycleEndDate = dateTodayStr;
                    //}else{
                    //TBC Partial refund
                    //	cycleStartDate = firstDateStr;
                    //	cycleEndDate = secondDateStr;
                    //TBC
                    //}
                } else if (tempTotalStatusDollar >= 5000 && tempTotalStatusDollar < 20000) {
                    currentTier = "JADE";
                    carryForwardDollar = tempTotalStatusDollar - 5000;
                    entryStatusDollar = "0";
                    //cycleStartDate = firstDateStr;
                    //cycleEndDate = secondDateStr;
                } else if (tempTotalStatusDollar >= 20000 && tempTotalStatusDollar < 80000) {
                    currentTier = "PRESTIGE RUBY";
                    carryForwardDollar = tempTotalStatusDollar - 20000;
                    entryStatusDollar = "0";
                    //cycleStartDate = firstDateStr;
                    //cycleEndDate = secondDateStr;
                } else if (tempTotalStatusDollar >= 80000) {
                    currentTier = "PRESTIGE DIAMOND";
                    carryForwardDollar = tempTotalStatusDollar - 80000;
                    entryStatusDollar = "0";
                    //cycleStartDate = firstDateStr;
                    //cycleEndDate = secondDateStr;
                }


                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
            }

        }


        String statusDollarToUpgrade = "";

        //Status Dollar Required to Upgrade

        if (currentTier.equals("LOYAL T")) {

            statusDollarToUpgrade = (5000 - (carryForwardDollar + tierStatusDollar)) + "";  // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("JADE")) {

            statusDollarToUpgrade = (15000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        } else if (currentTier.equals("PRESTIGE RUBY")) {

            statusDollarToUpgrade = (60000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }

        String statusDollarToRenew = "";
        int result = 0;

        switch (currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE RUBY":
                result = 10000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE DIAMOND":
                result = 40000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0) {
                    statusDollarToRenew = Integer.toString(result);
                }

                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }

        //Results
        currentStatusDollar = (carryForwardDollar + tierStatusDollar);
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);
        System.out.println("Tier after manual Downgrade : " + manualDowngradeTier);

        memberRefundLogicInMap.put("MembershipTier", currentTier);
        memberRefundLogicInMap.put("CarryForwardDollar", String.valueOf(carryForwardDollar));
        memberRefundLogicInMap.put("TierStatusDollar", String.valueOf(tierStatusDollar));
        memberRefundLogicInMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        memberRefundLogicInMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));
        memberRefundLogicInMap.put("EntryStatusDollar", entryStatusDollar);
        memberRefundLogicInMap.put("CycleStartDate", cycleStartDate);
        memberRefundLogicInMap.put("CycleEndDate", cycleEndDate);
        memberRefundLogicInMap.put("EnrollmentDate", enrollmentDate);
        memberRefundLogicInMap.put("FirstPurchaseDate", firstPurchaseDate);
        memberRefundLogicInMap.put("StatusDollarToRenew", statusDollarToRenew);
        memberRefundLogicInMap.put("TierAfterManualDowngrade", manualDowngradeTier);

        return memberRefundLogicInMap;

    }

    public Map<String, String> manualUpgradeLogicCalculation(int rowCount, String cardNumText) throws ParseException {
        Map<String, String> mainLogicMap = new HashMap<>();

        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        if (status_INUSD_c_List == null) {
            for (int i = 1; i < rowCount + 1; i++) {
                String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
                value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
                System.out.println(value);

            }
            for (int i = 1; i < rowCount + 1; i++) {
                String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
                value1.add(PurchaceH_StatusDolla1);
                System.out.println(value1);
            }
        }

        String[] transactionHistory;
        String[] transactionMemberCycle;

        if (status_INUSD_c_List == null) {
            transactionHistory = value.toArray(new String[value.size()]);
            transactionMemberCycle = value1.toArray(new String[value.size()]);
        } else {
            transactionHistory = status_INUSD_c_List.toArray(new String[status_INUSD_c_List.size()]);
            transactionMemberCycle = transaction_Member_Cycle__c.toArray(new String[status_INUSD_c_List.size()]);
        }

        String manualDowngradeTier = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String currentTier = "";
        String tierStatusDollar = null;
        String carryForwardDollar = null;
        String entryStatusDollar = null;
        String currentStatusDollar = null;
        System.out.println("Array size: " + transactionHistory.length);


        for (int i = 0; i < transactionHistory.length; i++) {                            //Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);

            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            if (i == 0) {
                String tier = System.getProperty("Tier");
                if (tier == null) {
                    tier = "Not_Added";
                }

                if (!(tier.equalsIgnoreCase("All"))) {
                    String tierToBeUpgrade = tier.split("TO")[1];
                    if (tierToBeUpgrade.equalsIgnoreCase("Jade")) {
                        currentTier = "JADE";
                    } else if (tierToBeUpgrade.equalsIgnoreCase("Ruby")) {
                        currentTier = "PRESTIGE RUBY";
                    } else if (tierToBeUpgrade.equalsIgnoreCase("Diamond")) {
                        currentTier = "PRESTIGE DIAMOND";
                    }
                } else {
                    String tierFromAll = cardNumText.split("_")[1];
                    String tierToBeUpgrade = tierFromAll.split("TO")[1];
                    if (tierToBeUpgrade.equalsIgnoreCase("Jade")) {
                        currentTier = "JADE";
                    } else if (tierToBeUpgrade.equalsIgnoreCase("Ruby")) {
                        currentTier = "PRESTIGE RUBY";
                    } else if (tierToBeUpgrade.equalsIgnoreCase("Diamond")) {
                        currentTier = "PRESTIGE DIAMOND";
                    }
                }

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;
                entryStatusDollar = "0";
                tierStatusDollar = "0";
                carryForwardDollar = "0";

                System.out.println("Carry Forward $: " + carryForwardDollar);
                System.out.println("Tier Status $: " + tierStatusDollar);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);

                Calendar cal = Calendar.getInstance();
                Date tDate = new Date();
                cal.setTime(tDate);
                String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(tDate);
                cycleStartDate = dateTodayStr;
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.YEAR, 2);
                cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());

                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }
        }

        String statusDollarToUpgrade = "";
        //Status Dollar Required to Upgrade
        if (currentTier.equals("LOYAL T")) {
            statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
        } else if (currentTier.equals("JADE")) {
            statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
        } else if (currentTier.equals("PRESTIGE RUBY")) {
            statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
        }
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

        String statusDollarToRenew = "";
        int result = 0;

        switch (currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500;
                statusDollarToRenew = Integer.toString(result);
                break;

            case "PRESTIGE RUBY":
                result = 10000;
                statusDollarToRenew = Integer.toString(result);
                break;

            case "PRESTIGE DIAMOND":
                result = 40000;
                statusDollarToRenew = Integer.toString(result);
                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }

        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);

        //Results
        currentStatusDollar = "0";
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);

        mainLogicMap.put("MembershipTier", currentTier);
        mainLogicMap.put("CarryForwardDollar", carryForwardDollar);
        mainLogicMap.put("TierStatusDollar", tierStatusDollar);
        mainLogicMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        mainLogicMap.put("EntryStatusDollar", entryStatusDollar);
        mainLogicMap.put("CycleStartDate", cycleStartDate);
        mainLogicMap.put("CycleEndDate", cycleEndDate);
        mainLogicMap.put("EnrollmentDate", enrollmentDate);
        mainLogicMap.put("FirstPurchaseDate", firstPurchaseDate);
        mainLogicMap.put("StatusDollarToRenew", statusDollarToRenew);
        mainLogicMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));

        return mainLogicMap;

    }

    public Map<String, String> renewalLogicCalculation(int rowCount, String renewalCriteria) throws ParseException {

        Map<String, String> mainLogicMap = new HashMap<>();
        //Renew flow calculations

        String cycleStartDate = "";
        String cycleEndDate = "";
        String statusDollarToUpgrade = "";
        String entryStatusDollarR = "";
        String tierStatusDollarR = "";
        String carryForwardDollarR = "";
        int currentStatusDollar = 0;

        if(renewalCriteria.equalsIgnoreCase("Basic_Renewal")){
            if (renew_TierStatusDollar >= 0 && renew_currentTier.equals("LOYAL T")){
                renew_currentTier = "LOYAL T";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 2500 && renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 10000 && renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 40000 && renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if(renewalCriteria.equalsIgnoreCase("Threshold_Renewal")){
            if (renew_TierStatusDollar >= 2000 && renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 8000 && renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 32000 && renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if(renewalCriteria.equalsIgnoreCase("CFAndStatus_Renewal")){
            int amountToRenew = renew_TierStatusDollar + renew_carryForwardDollar;
            if (amountToRenew >= 2500 && renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (amountToRenew >= 10000 && renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (amountToRenew >= 40000 && renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if (renewalCriteria.equalsIgnoreCase("Manual_Exception_Rule")){
            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if(renewalCriteria.equalsIgnoreCase("Grace_Rule")){
            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = renew_EnrollmentDate;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = String.valueOf(Integer.parseInt("5000") - renew_carryForwardDollar);  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = String.valueOf(Integer.parseInt("15000") - renew_carryForwardDollar); // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = String.valueOf(Integer.parseInt("60000") - renew_carryForwardDollar); // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = renew_EntryStatusDollar;
            tierStatusDollarR = "0";
            carryForwardDollarR = String.valueOf(renew_carryForwardDollar);
            currentStatusDollar = renew_TierStatusDollar + renew_carryForwardDollar;
        }

        if(renewalCriteria.equalsIgnoreCase("All_Exception_Rule")){

            int amountToRenew = renew_TierStatusDollar + renew_carryForwardDollar;

            if (renew_TierStatusDollar >= 2000 && amountToRenew >= 2500 && renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 8000 && amountToRenew >= 10000 && renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 32000 && amountToRenew >= 40000 && renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if(renewalCriteria.equalsIgnoreCase("NO_Rule")){
            String tier = System.getProperty("Tier");
            if (tier==null){
                tier = "Not_Added";
            }

            if (!(tier.equalsIgnoreCase("LoyalT"))){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
                cycleStartDate = dateTodayStr;
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.YEAR, 2);
                cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

                entryStatusDollarR = "0";
                tierStatusDollarR = "0";
                carryForwardDollarR = "0";
                currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);

            }else{
                Calendar cal = Calendar.getInstance();
                cycleStartDate = renew_EnrollmentDate;
                Date date = new SimpleDateFormat("dd MMM yyyy").parse(cycleStartDate);
                cal.setTime(date);
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.YEAR, 2);
                cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

                entryStatusDollarR = String.valueOf(renew_EntryStatusDollar);
                tierStatusDollarR = String.valueOf(renew_TierStatusDollar);
                carryForwardDollarR = String.valueOf(renew_carryForwardDollar);
                currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
            }
            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                renew_currentTier = "LOYAL T";
                statusDollarToUpgrade = (5000 - (renew_carryForwardDollar + renew_TierStatusDollar)) + "";
            }else if (renew_currentTier.equals("JADE")) {
                renew_currentTier = "LOYAL T";
                statusDollarToUpgrade = (5000) + "";
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                renew_currentTier = "JADE";
                statusDollarToUpgrade = (15000) + "";
            }else if (renew_currentTier.equals("PRESTIGE DIAMOND")) {
                renew_currentTier = "PRESTIGE RUBY";
                statusDollarToUpgrade = (60000) + "";
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        }

        if(renewalCriteria.equalsIgnoreCase("ManualExceptionRule_duringGracePeriod")){
            if (renew_currentTier.equals("LOYAL T")){
                renew_currentTier = "LOYAL T";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if (renewalCriteria.equalsIgnoreCase("Posting_Renewal_Flow")){
            if (renew_TierStatusDollar >= 0 && renew_currentTier.equals("LOYAL T")){
                renew_currentTier = "LOYAL T";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 2500 && renew_currentTier.equals("JADE")){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 10000 && renew_currentTier.equals("PRESTIGE RUBY")){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 40000 && renew_currentTier.equals("PRESTIGE DIAMOND")){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("LOYAL T")) {
                statusDollarToUpgrade = (5000) + "";  // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = (15000) + ""; // convert to string to compare with SF UI and dB
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = (60000) + ""; // convert to string to compare with SF UI and dB
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = "0";
            currentStatusDollar = Integer.parseInt(tierStatusDollarR) + Integer.parseInt(carryForwardDollarR);
        }

        if (renewalCriteria.equalsIgnoreCase("Posting_Upgrade_Flow")){
            if (renew_TierStatusDollar >= 5000){
                renew_currentTier = "JADE";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 20000){
                renew_currentTier = "PRESTIGE RUBY";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }
            if (renew_TierStatusDollar >= 80000){
                renew_currentTier = "PRESTIGE DIAMOND";
                System.out.println("After run renewal job current tier is : " + renew_currentTier);
            }

            Calendar cal = Calendar.getInstance();
            String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            cycleStartDate = dateTodayStr;
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.YEAR, 2);
            cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
            System.out.println("Cycle Start Date: " + cycleStartDate);
            System.out.println("Cycle End Date: " + cycleEndDate);

            //Status Dollar Required to Upgrade
            if (renew_currentTier.equals("JADE")) {
                statusDollarToUpgrade = String.valueOf(Integer.parseInt("15000") - renew_carryForwardDollar);
            } else if (renew_currentTier.equals("PRESTIGE RUBY")) {
                statusDollarToUpgrade = String.valueOf(Integer.parseInt("60000") - renew_carryForwardDollar);
            }
            System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);

            entryStatusDollarR = "0";
            tierStatusDollarR = "0";
            carryForwardDollarR = String.valueOf(renew_carryForwardDollar);
            currentStatusDollar = renew_TierStatusDollar + renew_carryForwardDollar;
        }

        String statusDollarToRenew = "";
        int result = 0;
        switch (renew_currentTier) {
            case "LOYAL T":
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE":
                result = 2500;
                statusDollarToRenew = Integer.toString(result);
                break;

            case "PRESTIGE RUBY":
                result = 10000;
                statusDollarToRenew = Integer.toString(result);
                break;

            case "PRESTIGE DIAMOND":
                result = 40000;
                statusDollarToRenew = Integer.toString(result);
                break;
            default:
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }


        //Results
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + renew_currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollarR);
        System.out.println("Tier Status $: " + tierStatusDollarR);
        System.out.println("Entry Status Dollars: " + entryStatusDollarR);
        System.out.println("Current Status $: " + String.valueOf(currentStatusDollar));
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Enrollment Date: " + renew_EnrollmentDate);
        System.out.println("First Transaction Date: " + renew_FirstPurchaseDate);

        mainLogicMap.put("MembershipTier", renew_currentTier);
        mainLogicMap.put("CarryForwardDollar", carryForwardDollarR);
        mainLogicMap.put("TierStatusDollar", tierStatusDollarR);
        mainLogicMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        mainLogicMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));
        mainLogicMap.put("EntryStatusDollar", entryStatusDollarR);
        mainLogicMap.put("CycleStartDate", cycleStartDate);
        mainLogicMap.put("CycleEndDate", cycleEndDate);
        mainLogicMap.put("EnrollmentDate", renew_EnrollmentDate);
        mainLogicMap.put("FirstPurchaseDate", renew_FirstPurchaseDate);
        mainLogicMap.put("StatusDollarToRenew", statusDollarToRenew);

        return mainLogicMap;
    }

    public ArrayList<ArrayList<String>> readPurchaseHistoryTab(int rowCount){
        ArrayList<ArrayList<String>> fullListOfTransactions = new ArrayList<>();
        for (int i = 1; i < rowCount + 1; i++) {
            ArrayList<String> transactionsList = new ArrayList<String>();
            String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
            transactionsList.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
            String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(9) > div:nth-child(1)")).getText();
            transactionsList.add(PurchaceH_StatusDolla1);
            String transactionNumber = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(4) > div:nth-child(1)")).getText();
            transactionsList.add(transactionNumber);
            fullListOfTransactions.add(transactionsList);
        }
        return fullListOfTransactions;
    }

    public Map<String, String> mergeLogicCalculation(int rowCount) throws ParseException {
        Map<String, String> mainLogicMap = new HashMap<>();

        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String currentTier = "";
        String tierStatusDollar = null;
        String carryForwardDollar = null;
        String currentStatusDollar = null;
        //System.out.println("Array size: " + transactionHistory.length);

        String tier = System.getProperty("Tier");
        if (tier == null) {
            tier = "Not_Added";
        }

        String firstDateStr = CycleStartDate_sf_formattedDate;
        //String firstDateStr = "01 Oct 2018";

        firstPurchaseDate = firstDateStr;
        enrollmentDate = firstDateStr;
        tierStatusDollar = "0";
        carryForwardDollar = "0";

        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);

        Calendar cal = Calendar.getInstance();
        Date tDate = new Date();
        cal.setTime(tDate);
        String dateTodayStr = new SimpleDateFormat("dd MMM yyyy").format(tDate);
        cycleEndDate = dateTodayStr;
        cycleStartDate = firstDateStr;


        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);

        String entryStatusDollar  = "";
        //Status Dollar Required to Upgrade
        //currentTier = "PRESTIGE DIAMOND";
        currentTier = CardTier_sf.toUpperCase();
        if (currentTier.equals("LOYAL T")) {
            entryStatusDollar = "0";
        } else if (currentTier.equals("JADE")) {
            entryStatusDollar = "5000";
        } else if (currentTier.equals("PRESTIGE RUBY")) {
            entryStatusDollar = "20000";
        }else if (currentTier.equals("PRESTIGE DIAMOND")) {
            entryStatusDollar = "80000";
        }

        String statusDollarToUpgrade = "0";
        String statusDollarToRenew = "0";
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);

        //Results
        currentStatusDollar = "0";
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        System.out.println("Status Dollar Required to Renew:" + statusDollarToRenew);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);

        mainLogicMap.put("MembershipTier", currentTier);
        mainLogicMap.put("CarryForwardDollar", carryForwardDollar);
        mainLogicMap.put("TierStatusDollar", tierStatusDollar);
        mainLogicMap.put("StatusDollarToUpgrade", statusDollarToUpgrade);
        mainLogicMap.put("EntryStatusDollar", entryStatusDollar);
        mainLogicMap.put("CycleStartDate", cycleStartDate);
        mainLogicMap.put("CycleEndDate", cycleEndDate);
        mainLogicMap.put("EnrollmentDate", enrollmentDate);
        mainLogicMap.put("FirstPurchaseDate", firstPurchaseDate);
        mainLogicMap.put("StatusDollarToRenew", statusDollarToRenew);
        mainLogicMap.put("CurrentStatusDollar", String.valueOf(currentStatusDollar));

        return mainLogicMap;

    }

    public String readPointCalculationInMatrixForCancelledCard(String cardNumber, JsonObject myJsonObj) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");

        String queryForGetPointsBal = "select PointsBAL from MatrixTpReward.dbo.card where CardNo like '" + cardNumber + "'";
        HashMap<String, String> matrixDataMap = db.getDatabaseTableRecords(env, queryForGetPointsBal);
        String pointsBAL_DB = matrixDataMap.get("PointsBAL");

        double value = Double.parseDouble(pointsBAL_DB);
        System.out.println("Matrix DB PointsBAL is : " + pointsBAL_DB);

        createDbConnection db_1 = new createDbConnection();
        String queryForGetGracePointsBAL = "select GracePointsBAL from MatrixTpReward.dbo.rewardscyclegrace where CardNo like '" + cardNumber + "'";
        HashMap<String, String> matrixGracePointsBAL_DataMap = db_1.getDatabaseTableRecords(env, queryForGetGracePointsBAL);
        String gracePointsBAL_DB = matrixGracePointsBAL_DataMap.get("GracePointsBAL");
        if (!(gracePointsBAL_DB == null)) {
            System.out.println("Matrix DB GracePointsBAL is : " + gracePointsBAL_DB);
            if (!gracePointsBAL_DB.isEmpty()) {
                value += Double.parseDouble(gracePointsBAL_DB);
            }
        }
        DecimalFormat df = new DecimalFormat("#.#####");
        System.out.println("DB Points Balance is :" + value);
        System.out.println("========Points=========");
        System.out.println("UI Value===" + memberPoints_MembershipTier_sf);
        System.out.println("DB Value===" + df.format(value));
        _scenario.write("========Points=========");
        _scenario.write("UI Value===" + memberPoints_MembershipTier_sf);
        _scenario.write("DB Value===" + df.format(value));

        return df.format(value);
    }

    public Map<String, String> readRequestDetails() throws Exception {
        Map<String, String> requestDetailsMap =  new HashMap<>();
        comElement.waitForElement(mergeRequest_CardKept);
        String mergeRequest_CardKepts = mergeRequest_CardKept.getText();
        String mergeRequest_CardCancelleds = mergeRequest_CardCancelled.getText();
        String mergeRequest_pointsBalances = mergeRequest_pointsBalance.getText().replaceAll(",", "");
        String mergeRequest_DollarsTransferreds = mergeRequest_DollarsTransferred.getText().replaceAll(",", "");
        requestDetailsMap.put("MergeRequest_CardKept", mergeRequest_CardKepts);
        requestDetailsMap.put("MergeRequest_CardCancelled", mergeRequest_CardCancelleds);
        requestDetailsMap.put("MergeRequest_pointsBalance", mergeRequest_pointsBalances);
        requestDetailsMap.put("MergeRequest_DollarsTransferred", mergeRequest_DollarsTransferreds);
        return requestDetailsMap;
    }

    public HashMap<String, String> getMainDBRecordsForTransactions(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 1 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(60000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.Transact where CardNo like '"+cardNumber+"';";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dbo.Transact where CardNo like '"+cardNumber+"';");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        return mainDBRecordMap;
    }

    public HashMap<String, String> getRowCountMainDBRecordsForTransactions(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 1 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(60000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select COUNT(*) AS Count FROM MatrixTpReward.dbo.Transact where CardNo like '"+cardNumber+"';";
        System.out.println("Getting Main DB records ROW count from the MatrixTpReward.dbo.Transact - Query : select COUNT(*) AS Count FROM MatrixTpReward.dbo.Transact where CardNo like '"+cardNumber+"';");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        return mainDBRecordMap;
    }

    public void goToMembershipCycleTable(String rowString) throws Exception {
        driver.navigate().refresh();
        Thread.sleep(4000);
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        try {
            comElement.waitForElement(member_cycle);
        } catch (Exception e) {
            driver.navigate().refresh();
            Thread.sleep(3000);
            comElement.waitForElement(member_cycle);
        }
        comElement.click(member_cycle);
        Thread.sleep(2000);
        JavaScriptExecutor javaScriptExecutor = new JavaScriptExecutor(driver);
        javaScriptExecutor.scrollDown(0,500);
        Thread.sleep(4000);
        screenshot.takeScreenshot();
        List<WebElement> dropDownList = driver.findElements(By.xpath("//div[@class='forceVirtualActionMarker forceVirtualAction']"));
        System.out.println("Membership Cycle Count in SF : " + dropDownList.size());

        if (rowString.equalsIgnoreCase("first")){
            comElement.click(dropDownList.get(0));
            Thread.sleep(5000);
            comElement.click(membershipCycleMoreViewDropDown_EditLink);
            Thread.sleep(5000);
        }else if (rowString.equalsIgnoreCase("second")) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", dropDownList.get(1));
            Thread.sleep(2000);
            comElement.click(dropDownList.get(1));
            Thread.sleep(5000);
            comElement.click(membershipCycleMoreViewDropDown_EditLink);
            Thread.sleep(5000);
        }
    }

    public Map<String, String> readMembershipCycleTable() throws Exception {
        Map<String, String> memberCycleInfoMap = new HashMap<>();
        comElement.waitForElement(membershipCycle_gracePeriodCheckBox);
        boolean grace_status = membershipCycle_gracePeriodCheckBox.isSelected();
        memberCycleInfoMap.put("GracePeriodCheckbox", String.valueOf(grace_status));
        System.out.println("GracePeriodCheckbox : " + String.valueOf(grace_status));
        boolean except_status = membershipCycle_ExceptionalRenewalCheckBox.isSelected();
        memberCycleInfoMap.put("ExceptionalRenewalCheckBox", String.valueOf(except_status));
        System.out.println("ExceptionalRenewalCheckBox : " + String.valueOf(except_status));

        boolean image_status = false;
        List<WebElement> imageList = driver.findElements(By.xpath("//span[@class='test-id__field-value slds-form-element__static slds-grow  is-read-only']/span/img"));
        if (imageList.size()>=1){
            image_status = true;
        }
        memberCycleInfoMap.put("ActiveImageStatus", String.valueOf(image_status));
        String cycleEndDate = comElement.getText(membershipCycle_CycleEndDate);
        System.out.println("CycleEndDate : " + cycleEndDate);
        memberCycleInfoMap.put("CycleEndDate", cycleEndDate);

        return memberCycleInfoMap;
    }

    public void updateMembershipCycleDateInSFUsingQuery(String cardNum) throws FileNotFoundException {
        updateCycleEndDate updateCycleEndDate = new updateCycleEndDate(_scenario);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String dateTodayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        String cycleEndDate = dateTodayStr;
        updateCycleEndDate.updateCycleEndDateInSF(cardNum, cycleEndDate);
    }

    public Map<String, String> readTransactionDetails(int rowCount) throws Exception {
        Map<String, String> transactionDetailsMap = new HashMap<>();
        for (int i = 1; i < rowCount + 1; i++) {
            WebElement element = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(4) > div:nth-child(1)"));
            comElement.click(element);
            Thread.sleep(10000);
            screenshot.takeScreenshot();
            String taxAmountText = comElement.getText(taxAmountLocalCurrency_text);
            System.out.println("Tax Amount (Local/Currency) : " + taxAmountText);
            transactionDetailsMap.put("TaxAmountLocal", taxAmountText);
            String taxAmountUSDText = comElement.getText(taxUSDAmountLocalCurrency_text);
            System.out.println("Tax Amount (USD) : " + taxAmountUSDText);
            transactionDetailsMap.put("TaxAmountUSD", taxAmountUSDText);
        }
        return transactionDetailsMap;
    }

    public HashMap<String, String> readMatrixTransactionsDBTable(String transactionNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 1 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(60000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        String transactAutoID = mainDBRecordMap.get("AutoID");
        System.out.println("Transact AutoID :- " + transactAutoID);

        String queryDBDetails_2 = "select * from MatrixTpReward.dfs.DFS_Transact where transact_AutoID in ('"+transactAutoID+"')";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dfs.DFS_Transact where transact_AutoID in ('"+transactAutoID+"')");
        HashMap<String, String> mainDBRecordMap_2 = db.getDatabaseTableRecords(env, queryDBDetails_2);
        return mainDBRecordMap_2;
    }

    public Map<String, String> readTransactionSKUDetails(int rowCount) throws Exception {
        Map<String, String> transactionSKUDetailsMap = new HashMap<>();
        for (int i = 1; i < rowCount + 1; i++) {
            Thread.sleep(5000);
            WebElement element = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(4) > div:nth-child(1)"));
            comElement.click(element);
            Thread.sleep(10000);
            javaScriptExecutor.scrollDown(0,200);
            //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", SKUDetails_text);
            Thread.sleep(10000);
            screenshot.takeScreenshot();
            String productNameText = comElement.getText(skuProductName_text);
            System.out.println("Product Name : " + productNameText);
            transactionSKUDetailsMap.put("ProductName", productNameText);
            String unitsText = comElement.getText(skuUnits_text);
            System.out.println("Units : " + unitsText);
            transactionSKUDetailsMap.put("Units", unitsText);
            String netAmountUSDText = comElement.getText(skuNetAmountUSD_text);
            System.out.println("Net Amount without Tax (USD) : " + netAmountUSDText);
            transactionSKUDetailsMap.put("NetAmountUSD", netAmountUSDText);
            String netAmountLocalText = comElement.getText(skuNetAmountLocal_text);
            System.out.println("Net Amount without Tax (Local) : " + netAmountLocalText);
            transactionSKUDetailsMap.put("NetAmountLocal", netAmountLocalText);
            String productNameText1 = comElement.getText(skuProductName_text1);
            System.out.println("Product Name : " + productNameText1);
            transactionSKUDetailsMap.put("ProductName1", productNameText1);
            String unitsText1 = comElement.getText(skuUnits_text1);
            System.out.println("Units : " + unitsText1);
            transactionSKUDetailsMap.put("Units1", unitsText1);
            String netAmountUSDText1 = comElement.getText(skuNetAmountUSD_text1);
            System.out.println("Net Amount without Tax (USD) : " + netAmountUSDText1);
            transactionSKUDetailsMap.put("NetAmountUSD1", netAmountUSDText1);
            String netAmountLocalText1 = comElement.getText(skuNetAmountLocal_text1);
            System.out.println("Net Amount without Tax (Local) : " + netAmountLocalText1);
            transactionSKUDetailsMap.put("NetAmountLocal1", netAmountLocalText1);
        }
        return transactionSKUDetailsMap;
    }

    public Map<String, String> readTransactionPaymentDetails(int rowCount) throws Exception {
        Map<String, String> transactionSKUDetailsMap = new HashMap<>();
        for (int i = 1; i < rowCount + 1; i++) {
            Thread.sleep(5000);
            WebElement element = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(4) > div:nth-child(1)"));
            comElement.click(element);
            Thread.sleep(10000);
            javaScriptExecutor.scrollDown(0,200);
            Thread.sleep(10000);
            javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", externalID_linktext);
            comElement.waitForElement(externalID_linktext);
            comElement.click(externalID_linktext);
            Thread.sleep(10000);
            String AmountInFC = comElement.getText(AmountInFC_text);
            System.out.println("Amount In Foreign Currency : " + AmountInFC);
            transactionSKUDetailsMap.put("AmountInFC", AmountInFC);
            String AmountInLC = comElement.getText(AmountInLC_text);
            System.out.println("Amount In Local Currency : " + AmountInLC);
            transactionSKUDetailsMap.put("AmountInLC", AmountInLC);
            String Code = comElement.getText(Code_text);
            System.out.println("Code : " + Code);
            transactionSKUDetailsMap.put("Code", Code);
            String CreditCardNumber = comElement.getText(CreditCardNumber_text);
            System.out.println("CreditCardNumber : " + CreditCardNumber);
            transactionSKUDetailsMap.put("CreditCardNumber", CreditCardNumber);
            String Description = comElement.getText(Description_text);
            System.out.println("Description : " + Description);
            transactionSKUDetailsMap.put("Description", Description);
            String ForeignCurrencyDescription = comElement.getText(ForeignCurrencyDescription_text);
            System.out.println("ForeignCurrencyDescription : " + ForeignCurrencyDescription);
            transactionSKUDetailsMap.put("ForeignCurrencyDescription", ForeignCurrencyDescription);
            String Type = comElement.getText(Type_text);
            System.out.println("Type : " + Type);
            transactionSKUDetailsMap.put("Type", Type);
        }
        return transactionSKUDetailsMap;
    }

    public HashMap<String, String> readMatrixPaymentsDBTable(String transactionNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 1 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(60000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        String transactAutoID = mainDBRecordMap.get("AutoID");
        System.out.println("Transact AutoID :- " + transactAutoID);

        String queryDBDetails_2 = "select * from MatrixTpReward.dfs.DFS_TransactTender WITH (NOLOCK)where Transact_AutoID in ('"+transactAutoID+"')";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dfs.DFS_TransactTender WITH (NOLOCK)where Transact_AutoID in ('"+transactAutoID+"')");
        HashMap<String, String> mainDBRecordMap_2 = db.getDatabaseTableRecords(env, queryDBDetails_2);
        return mainDBRecordMap_2;
    }

    public HashMap<String, String> readMatrixSKUDBTable(String transactionNumber, String skuNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 1 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(60000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dbo.Transact where ReceiptNo = '"+transactionNumber+"'");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        String transactAutoID = mainDBRecordMap.get("AutoID");
        System.out.println("Transact AutoID :- " + transactAutoID);

        String queryDBDetails_2 = "select * from MatrixTpReward.dbo.TransactDetails WITH (NOLOCK) where Transact_AutoID  in ('"+transactAutoID+"') AND Item_Code ="+skuNumber+"";
        System.out.println("Getting Main DB records from the MatrixTpReward.dbo.Transact - Query : select * from MatrixTpReward.dbo.TransactDetails WITH (NOLOCK) where Transact_AutoID  in ('"+transactAutoID+"') AND Item_Code ="+skuNumber+"");
        HashMap<String, String> mainDBRecordMap_2 = db.getDatabaseTableRecords(env, queryDBDetails_2);
        return mainDBRecordMap_2;
    }
}
