package page_objects;

import commonLibs.implementation.JavaScriptExecutor;
import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import org.testng.Assert;
import utilities.TakeScreenshot;
import utilities.createDbConnection;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.XMLFormatter;

public class CardValidation_pageobjects {

    public WebDriver driver;
    public Scenario _scenario;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;
    public JavaScriptExecutor javaScriptExecutor;

    @FindBy(how= How.CSS, using = "fieldset.slds-form-element > .slds-form-element__control.slds-m-bottom_small:nth-of-type(1) > .iconPointer > label.iconPointer")
    public WebElement transactionAssociationLink_link;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Transaction Association')]/ancestor::ul//*[contains(text(),'More')]")
    public WebElement more_link;

    @FindBy(xpath = "//*[contains(text(),'More')]//ancestor::ul//li[5]//div[@class='uiMenu']")
    public WebElement moreTab;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul")
    public WebElement cardManagementMAIN_link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Cancel']")
    public WebElement cancel_Link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Merge']")
    public WebElement merge_Link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Replace']")
    public WebElement replace_Link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Blacklist']")
    public WebElement blackList_Link;

    @FindBy(how= How.XPATH, using = "//label[text()='Blacklist']")
    public WebElement blackList_Link_2;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Suspend']")
    public WebElement suspend_Link;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Card')]/ancestor::label/../select")
    public WebElement cancelCard_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Reason')]/ancestor::label/../select")
    public WebElement cancelCardReason_dropdown;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Next']")
    public WebElement next_Button;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-size_1-of-1 slds-align_absolute-center slds-p-around_small slds-text-heading_small']/div[1]/div[2]")
    public WebElement memberShipCard_text;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Finish']")
    public WebElement finish_Button;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Confirm']")
    public WebElement confirm_Button;

    @FindBy(how= How.XPATH, using = "//*[@id=\"brandBand_1\"]/div/div[1]/div/div[1]/div/div[1]/div/header/div[2]/ul/li[5]/div/div/div/span")
    public WebElement cardStatus_text;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    @FindBy(how= How.XPATH, using = "//iframe[contains(@src, 'Membership')]")
    public WebElement MmembershipFrame;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Member Cycle')]")
    public WebElement member_cycle_Tab;

    @FindBy(how= How.XPATH, using = ".//*[@id='j_id0:j_id7:theRepeat:0:theRepeat:1:j_id40:j_id41:j_id66']")
    public WebElement current_Mem_Tier;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Membership Card')]")
    public WebElement membershipCard_Tab;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Membership Card Number')]/ancestor::table")
    public WebElement membershipCardTable;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'New Membership Card Number')]/ancestor::div[1]/input")
    public WebElement replaceCard_NewMemberCard_textBox;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Reason')]/ancestor::div[1]/select")
    public WebElement replaceCard_Reason_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Remarks')]/ancestor::div[1]/textarea")
    public WebElement replaceCard_Remarks_textBox;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Remarks')]/ancestor::div[1]/textarea")
    public WebElement suspendCard_Remarks_textBox;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Remarks')]/ancestor::div[1]/textarea")
    public WebElement blacklistCard_Remarks_textBox;

    @FindBy(xpath ="//a[text()='Card Management' and @title='Card Management']")
    public WebElement cardManagementTabUnderMore;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Reason')]/ancestor::label/../select")
    public WebElement merge_Reason_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Remarks')]/ancestor::div[1]/textarea")
    public WebElement merge_Remarks_textarea;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Card to be Cancelled')]/ancestor::div[1]/input")
    public WebElement merge_cancel_textBox;

    @FindBy(xpath = "//table[@class='forceRecordLayout slds-table slds-no-row-hover slds-table_cell-buffer slds-table_fixed-layout uiVirtualDataGrid--default uiVirtualDataGrid']//tbody//tr//td//span[text()='Supplementary']")
    public WebElement cardTypeOfSupplementaryCard;

    @FindBy(xpath = "//table[@class='forceRecordLayout slds-table slds-no-row-hover slds-table_cell-buffer slds-table_fixed-layout uiVirtualDataGrid--default uiVirtualDataGrid']//tbody//tr//td//span[text()='Supplementary']//../..//td//div[@class='forceVirtualActionMarker forceVirtualAction']//a")
    public WebElement pulldownMenuofSupplementaryCard;

    @FindBy(xpath = "//div[@title='Edit' and @role='button']")
    public WebElement editOptionInPulldownMenuOfSupplementaryCard;

    @FindBy(xpath = "//span[@class='label inputLabel uiPicklistLabel-left form-element__label uiPicklistLabel']//span[text()='Card Status']//..//..//div[@class='uiMenu']")
    public WebElement cardStatusDropdownInEditPopup;

    @FindBy(xpath = "//li[@class='uiMenuItem uiRadioMenuItem']//a[@title='Inactive' and text()='Inactive']")
    public WebElement inactiveOptionInCardStatusDropdown;

    @FindBy(xpath = " //button[@title='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//table[@class='forceRecordLayout slds-table slds-no-row-hover slds-table_cell-buffer slds-table_fixed-layout uiVirtualDataGrid--default uiVirtualDataGrid']//tbody//tr//td//span[text()='Supplementary']//preceding::td[1]//span")
    public WebElement statusOfSupplementaryCard;

    @FindBy(xpath = "//span[@class='title' and text()='Card Management']")
    public WebElement cardManagementTab;

    @FindBy(xpath = "//label[text()='Cancel']")
    public WebElement cancelLink;

    @FindBy(xpath = "//span[contains(text(),'Card')]//parent::label[@class='uiLabel-left form-element__label uiLabel']//parent::div//select")
    public WebElement cardSelectionDropdown;

    @FindBy(xpath = "//span[contains(text(),'Reason')]//parent::label[@class='uiLabel-left form-element__label uiLabel']//parent::div//select")
    public WebElement reasonSelectionDropdown;

    @FindBy(xpath = "//span[contains(text(),'Remarks')]//parent::label[@class='uiLabel-left form-element__label uiLabel']//parent::div//textarea")
    public WebElement remarksTextarea;

    @FindBy(xpath = "//button[@class='slds-button slds-button_brand' and contains(text(),'Next')]")
    public WebElement nextButtonInCancelCardPopup;

    @FindBy(xpath = "//button[@class='slds-button slds-button_brand' and contains(text(),'Finish')]")
    public WebElement finishButtonInCancelCardPopup;

    @FindBy(xpath = "//button[@class='slds-button slds-button_brand' and contains(text(),'Confirm')]")
    public WebElement confirmButton;

    @FindBy(xpath = "//table[@class='forceRecordLayout slds-table slds-no-row-hover slds-table_cell-buffer slds-table_fixed-layout uiVirtualDataGrid--default uiVirtualDataGrid']//tbody//tr//td//span[text()='Supplementary']//preceding::td[1]//span//preceding::th[1]//span")
    public WebElement supplementoryCardNumberText;

    @FindBy(xpath = "//li/a[@title='Card Management' and text()='Card Management']")
    public WebElement cardManagementSubOption;





    public CardValidation_pageobjects(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this._scenario=scenario;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
        javaScriptExecutor = new JavaScriptExecutor(driver);
    }

    public Map<String, String> cancelCardManagement() throws Exception {
        Map<String, String> cardCancelDataMap = new HashMap<>();
        System.out.println("waiting for 1 minute");
        Thread.sleep(60000);
        System.out.println("Cancelling Card");
        _scenario.write("=====Cancelling Card=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        commonElements.click(more_link);
        screenshot.takeScreenshot();
        Thread.sleep(10000);
        //clicking on card management option under more tab
        try {
            commonElements.moveMouseAndClick(cardManagementSubOption);
            System.out.println("click on 'Card Management' option");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("Unable to click on 'Card Management' option under 'More' tab. Exception - "+e.getMessage());
            Assert.fail("Unable to click on 'Card Management' option under 'More' tab. Exception - "+e.getMessage());
        }

        //scrolling down to the element
        try {
            javaScriptExecutor.scrollToElement(cancel_Link);
            System.out.println("Scrolled to the 'Cancel' option ");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            System.out.println("Unable to scroll to the 'Cancel' option due to an exception - "+e.getMessage());
            screenshot.takeScreenshot();
        }

//        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
//        for (int i=1; i<=liElememtsList.size(); i++){
//            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
//            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
//            if (elementName.equalsIgnoreCase("Card Management")){
//                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
//                Thread.sleep(20000);
//                driver.findElement(By.xpath(xpathLi)).click();
//                break;
//            }
//        }
        //screenshot.takeScreenshot();
        _scenario.write("Clicking Cancel link");
        Thread.sleep(5000);
        commonElements.click(cancel_Link);
        Thread.sleep(30000);
        commonElements.waitForElement(cancelCard_dropdown);
        screenshot.takeScreenshot();
        _scenario.write("Next button click");
        _scenario.write("Selecting Card type and Reason");
        comboBoxObj.selectByValue(cancelCard_dropdown,"Primary Card");
        comboBoxObj.selectByValue(cancelCardReason_dropdown,"No purchase made");
        screenshot.takeScreenshot();
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        screenshot.takeScreenshot();
        String membershipCardNumber_cancel = memberShipCard_text.getText();
        cardCancelDataMap.put("MembershipCardNumber", membershipCardNumber_cancel);
        System.out.println("Cancelling Card : " + membershipCardNumber_cancel);
        _scenario.write("Cancelling Card : " + membershipCardNumber_cancel);
        //click finish button
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        //confirm button
        commonElements.waitForElement(confirm_Button);
        commonElements.click(confirm_Button);
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        cardCancelDataMap.put("CancelMessage", commonElements.getText(validationMessageText));
        return cardCancelDataMap;
    }

    public String getCardStatus(String expected) throws Exception {
        driver.navigate().refresh();
        System.out.println("Waiting for the card status");
        commonElements.waitForElement(cardStatus_text);
        _scenario.write("validating the card status after "+expected+" member card");
        _scenario.write("Expected Value :- "+expected);
        driver.switchTo().defaultContent();
        String actualCardStatus = cardStatus_text.getText();
        System.out.println("The card Status is : " + actualCardStatus);
        _scenario.write("Actual Card status :- "+actualCardStatus+"");
        screenshot.takeScreenshot();
        return actualCardStatus;
    }

    public List<String> getMembershipCardTableStatus() throws Exception {

        WebElement brand = null;
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        try {
            commonElements.waitForElement(member_cycle_Tab);
            brand = member_cycle_Tab;
        }catch(Exception e){
            driver.navigate().refresh();
            commonElements.waitForElement(member_cycle_Tab);
            brand = member_cycle_Tab;
        }
        brand.click();
        Thread.sleep(2000);

        driver.switchTo().frame(MmembershipFrame);
        System.out.println("switch to frame its working ");

        //Get the Current Membership Tier
        String currentMemTier = current_Mem_Tier.getText();
        System.out.println("The Current Membership Tier is : " + currentMemTier);

        screenshot.takeScreenshot();
        driver.switchTo().defaultContent();

        List<String> memberCardStatusList = new ArrayList<>();

        if (currentMemTier.equalsIgnoreCase("Prestige Diamond") || currentMemTier.equalsIgnoreCase("Prestige Ruby") || currentMemTier.equalsIgnoreCase("Jade")){
            commonElements.click(membershipCard_Tab);
            commonElements.waitForElement(membershipCardTable);
            screenshot.takeScreenshot();
            List<WebElement> membershipCardTable_trList = membershipCardTable.findElements(By.xpath("//tbody/tr"));
            int tyu = membershipCardTable_trList.size();
            System.out.println("count is " + tyu);
            if (membershipCardTable_trList.size() > 1) {
                for (int i = 1; i <= (membershipCardTable_trList.size()-2); i++) {
                    String cardStatus_xpath = "//*[contains(text(),'Membership Card Number')]/ancestor::table//tbody/tr[" + i + "]/td[1]/span";
                    WebElement membershipCardTable_V = driver.findElement(By.xpath(cardStatus_xpath));
                    commonElements.waitForElement(membershipCardTable_V);
                    String cardStatus = membershipCardTable_V.getText();
                    memberCardStatusList.add(cardStatus);
                }
            } else {
                _scenario.write("No Membership Card records are found");
            }
        }else if (currentMemTier.equalsIgnoreCase("LOYAL T")){
            commonElements.click(membershipCard_Tab);
            commonElements.waitForElement(membershipCardTable);
            screenshot.takeScreenshot();
            List<WebElement> membershipCardTable_trList = membershipCardTable.findElements(By.xpath("//tbody/tr"));
            int tyu = membershipCardTable_trList.size();
            System.out.println("count is " + tyu);
            if (membershipCardTable_trList.size() > 1) {
                for (int i = 1; i <= membershipCardTable_trList.size()-2; i++) {
                    String cardStatus_xpath = "//*[contains(text(),'Membership Card Number')]/ancestor::table//tbody/tr[" + i + "]/td[1]/span";
                    WebElement membershipCardTable_V = driver.findElement(By.xpath(cardStatus_xpath));
                    String cardStatus = membershipCardTable_V.getText();
                    memberCardStatusList.add(cardStatus);
                }
            } else {
                _scenario.write("No Membership Card records are found");
            }
        }

        return memberCardStatusList;
    }

    public String gettingMembershipID(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Waiting to change isProcessed to 1 in Env " + env);
        String queryMemberID = "select MemberID from MatrixTpReward.dbo.card where CardNo = '" + cardNumber + "'";
        System.out.println("Getting memberID from the Matrix DB - Query : select MemberID from MatrixTpReward.dbo.card where CardNo = '" + cardNumber + "'");
        HashMap<String, String> membershipIDMap = db.getDatabaseTableRecords(env, queryMemberID);
        String memberID = membershipIDMap.get("MemberID");
        System.out.println("Member id is :" + memberID);
        return memberID;
    }

    public void checkingIsProcessedStatus(String memberID) throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("Getting isProcessed status");
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        createDbConnection db = new createDbConnection();
        boolean queryComplete1 = false;
        while (!queryComplete1) {
            String isProcessedQuery = "select TOP(1) isProcessed, ErrorMessage, MemberID, addedOn, ProcessedDateTime from DFSSTAGING.dbo.CustomerCard where memberID = '"+memberID+"' order by AddedOn desc";
            System.out.println("Getting isProcessed status - Query : select TOP(1) isProcessed, ErrorMessage, MemberID, addedOn, ProcessedDateTime from DFSSTAGING.dbo.CustomerCard where memberID = '"+memberID+"' order by AddedOn desc");
            HashMap<String, String> isProcessedMAP = db.getDatabaseTableRecordsForProcessedValueChecks(env, isProcessedQuery);
            String isProcessedVal = isProcessedMAP.get("isProcessed");
            String errorMessage = isProcessedMAP.get("ErrorMessage");
            String processedDateTime = isProcessedMAP.get("ProcessedDateTime");
            System.out.println("Initial isProcessed value is :" + isProcessedVal);
            System.out.println("Query processed");
            System.out.println("++++++++++++++++++++++++++ "+ isProcessedVal);

            if ((isProcessedVal).equals("0") || errorMessage.equals("error")) {
                System.out.println("Error");
                System.out.println("There is an error while waiting for isProcessed value - "+ errorMessage);
                _scenario.write("There is an error while waiting for isProcessed value - "+ errorMessage);
                Assert.fail("There is an error while waiting for isProcessed value - "+ errorMessage);
                break;
            } else if (isProcessedVal.equals("1") && !(processedDateTime.isEmpty())){
                System.out.println("Status = 1");
                queryComplete1 = true;

            } else if ((isProcessedVal).equals("2")) {
                System.out.println("Status = 2");
                Thread.sleep(15000);
                System.out.println("30 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }

            }  else if (isProcessedVal.isEmpty()) {
                System.out.println("Status = empty");
                Thread.sleep(15000);
                System.out.println("30 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            }
        }
    }

    public HashMap<String, String> getStagingTableRecords(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Reading staging table " + env);
        String queryMemberID = "select MemberID from MatrixTpReward.dbo.card where CardNo = '"+cardNumber+"'";
        System.out.println("Getting memberID from the Matrix DB - Query : select MemberID from MatrixTpReward.dbo.card where CardNo = '"+cardNumber+"'");
        HashMap<String, String> membershipIDMap = db.getDatabaseTableRecords(env, queryMemberID);
        String memberID = membershipIDMap.get("MemberID");
        System.out.println("Member id is :" + memberID);
        System.out.println("Getting CardStatusCode");

        String CardStatusCodeQuery = "select TOP(1) isProcessed, ErrorMessage, MemberID, addedOn, CardStatusCode from DFSSTAGING.dbo.CustomerCard where memberID = '"+memberID+"' order by AddedOn desc";
        System.out.println("Getting isProcessed status - Query : select isProcessed, ErrorMessage, MemberID, addedOn, CardStatusCode from DFSSTAGING.dbo.CustomerCard where memberID = '"+memberID+"'");
        HashMap<String, String> stagingTableDetailsMap = db.getDatabaseTableRecords(env, CardStatusCodeQuery);
        return stagingTableDetailsMap;
    }

    public HashMap<String, String> readMemberDataFromMatrix(String environment, String cardNumber) throws ClassNotFoundException, SQLException, InterruptedException {

        String query = "select GenericStr1 AS FirstName,GenericStr6 AS LastName,S1.Name AS " +
                "Salutation,GenericStr8 AS NativeSalutation,Email,GenericStr4 AS FirstNameNative," +
                "GenericStr7 AS LastNameNative,AreaCode AS MobileAreaCode,MobileNo AS MobileNo,S4.Name " +
                "AS SpokenLanguageCode,S.Name AS Country,S7.Name AS AgeRange,'' as EnrolmentType,'' " +
                "AS ActualTier,GenericStr48 AS IssuedCardTier,GenericStr44 AS CardPickupMethod,GenericStr18 " +
                "AS EnglishAddress1,GenericStr19 AS EnglishAddress2,GenericStr24 AS EnglishAddress3,GenericStr23 " +
                "AS NativeAddress1,AddressOthers2,AddressOthers3,GenericStr26 " +
                "AS EnglishCity,GenericStr20 AS ZipCode,GenericStr39 AS IsContactable,GenericStr15 " +
                "AS RegistrationLocationID,GenericDate1 AS CustomerRegistrationDatetime,C.MembershipStatusCode " +
                "AS CardStatus,GenericStr45 AS Staff,M.AutoID AS AutoID,Gender AS Gender,MaritalStatus " +
                "AS MaritalStatus,Email AS Email,DateofBirth AS DateofBirth, PostalCode AS PostalCode, ContactNo " +
                "AS ContactNo,InvalidAddress AS InvalidAddress,IsInvalidEmail AS IsInvalidEmail, IsEmptyEmail " +
                "AS IsEmptyEmail,IsDNWTP AS IsDNWTP,GenericStr9 AS BirthDate,GenericStr10 AS BirthMonth,GenericStr11 " +
                "AS BirthYear,S2.Name AS CustomerEnrolmentSource,GenericStr13 AS WechatID,GenericStr14 " +
                "AS RegistrationDivisionCode,GenericStr16 AS RegistrationLocationCode, GenericStr17 AS HomeNo,GenericStr21 " +
                "AS NativeCity, GenericStr22 AS NativeState,GenericStr25 AS isOptOutResearch,GenericStr27 AS EnglishState,S3.Name " +
                "AS EnglishCountry, GenericStr29 AS SuspendedStatus, GenericStr30 AS GenericStr30,GenericStr31 AS GenericStr31, GenericStr32 " +
                "AS ValidMobileNo1, GenericStr33 AS ValidHomeNo, GenericStr34 AS ValidOfficeNo,GenericStr35 " +
                "AS ValidFaxNo,GenericStr36 AS MobileNo2,GenericStr38 AS CustomerIsContactableFlag,GenericStr40 " +
                "AS OpenToSurveyFlag,S5.Name AS ChildrenCountRangeCode,GenericStr42 AS HomePhoneAreaCode,GenericStr43 " +
                "AS OfficePhoneAreacode,GenericStr45 AS GenericStr45, GenericStr46 AS PortalActivationFlag,GenericStr47 " +
                "AS IpadID,GenericStr49 AS SourceSystem, GenericStr50 AS IsVCardDownloaded,GenericDate2 " +
                "AS DateforDirectMarketing,GenericDate3 AS DateforMarketingResearch,GenericDate10 AS DateforTC,GenericStrAry1 " +
                "AS Remarks,GenericStrAry5 AS CustomerLeisureActivity, GenericStrAry6 AS CustomerShoppingPreference,GenericStrAry7 " +
                "AS CustomerPreferredBrands, GenericStrAry8 AS LeisureActivitiesMultiple,GenericStrAry9 AS ShoppingPreferencesMultiple,GenericStrAry10 " +
                "AS PreferredBrandsMultiple,M.AddedBy AS AddedBy,M.AddedOn AS AddedOn,M.ModifiedBy AS ModifiedBy,M.ModifiedOn " +
                "AS ModifiedOn,M.DeletedBy AS DeletedBy, M.DeletedOn AS DeletedOn,S6.Name " +
                "AS MarketingSource,IsEmailOptOut,IsOptOutMobile1,IsValidMobile2,IsOptOutMobile2,MobileNoArea2,IsOptOutHomePhone,IsOptOutWorkPhone," +
                "IsOptOutAddressEnglish,IsValidZipCode,IsValidAddressOthers,IsOptOutAddressOthers,MarketingSourceOthers from " +
                "MatrixTpReward.dbo.Member M left join MatrixTpReward.dbo.SystemCode S on S.Code=M.Country left join MatrixTpReward.dbo.SystemCode S1 on S1.Code=M.Salutation " +
                "left join MatrixTpReward.dbo.SystemCode S2 on S2.Code=M.GenericStr12 left join MatrixTpReward.dbo.SystemCode S3 on S3.Code=M.GenericStr28 " +
                "left join MatrixTpReward.dbo.SystemCode S4 on S4.Code=M.GenericStr37 left join MatrixTpReward.dbo.SystemCode S5 on S5.Code=M.GenericStr41 " +
                "left join MatrixTpReward.DFS.DFS_Member DM on DM.Member_AutoID=M.AutoID left join MatrixTpReward.dbo.SystemCode S6 On S6.ParentCode=916 " +
                "and S6.Code=CAST(DM.MarketingSource AS VARCHAR(5)) left join MatrixTpReward.dbo.SystemCode S7 On S7.ParentCode=905 " +
                "and S7.Code=CAST(DM.AgeRange AS VARCHAR(5)) inner join MatrixTpReward.dbo.Card C on C.MemberID=M.ID  where C.CardNo IN ('"+cardNumber+"');";

        createDbConnection db = new createDbConnection();
        //Adding 3 min of waiting time due to db slowness
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 3 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(180000);
        Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                        if(rsmd.getColumnName(i).equalsIgnoreCase("NativeSalutation")) {
                            nativeSalutationConvertedValue = convertNativeSalutation(environment, resultSet.getString(i).trim());
                            //updating the value in dataset
                            dataset.put(rsmd.getColumnName(i),nativeSalutationConvertedValue);
                        } else {
                            dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                        }

                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                    _scenario.write(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
        }

        return dataset;


    }

    public String convertNativeSalutation(String environment, String nativeSalutationCode) throws SQLException {

        String NativeSalutationCode = nativeSalutationCode;

        //Pass the NativeSalutationCode into another query and get the actual character
        String getNativeSalutationQuery = "select description from  MatrixTpReward.dfs.GenderSalutation WITH (NOLOCK) where salutationcode IN ('" + NativeSalutationCode + "');";

        //creating new DB connection object
        createDbConnection db = new createDbConnection();

        ResultSet resultSetNative = null;
        ResultSetMetaData rsmdNative=null;
        String nativeSalutation=null;
        try {
            resultSetNative = db.queryDB(environment, getNativeSalutationQuery);
            rsmdNative = resultSetNative.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert rsmdNative != null;
        int columnCountNative = rsmdNative.getColumnCount();

        if (resultSetNative!=null && columnCountNative>0) {
            while (resultSetNative.next()){
                for(int i=1; i<=columnCountNative;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    if(resultSetNative.getString(i)!=null) {
                        nativeSalutation = resultSetNative.getString(i);
                    } else {
                        nativeSalutation="";
                    }
                    // printing out the data collected from Matrix
                    System.out.println("NativeSalutationCode : "+nativeSalutationCode + " is converted to NativeSalutation : "+nativeSalutation);


                } //end of for loop

            } // end of while

        } else {
            System.out.println("No matching result for given NativeSalutation code was found. Result set is empty");
        }
        return nativeSalutation;
    }

    public void deleteMemberCardFromMatrixDB(String cardNumber) throws FileNotFoundException, SQLException, ClassNotFoundException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Delete record from Matrix main table " + env);
        String deleteQuery = "delete from MatrixTpReward.dbo.card where CardNo = '"+cardNumber+"'";
        db.deleteRow(env, deleteQuery);
    }

    public Map<String, String> replaceCardManagement(String oldCardNumber, String newCardNumber) throws Exception {
        Map<String, String> replaceCardDataMap = new HashMap<>();
        System.out.println("Replacing Card");
        _scenario.write("=====Replacing Card=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        commonElements.click(more_link);
        screenshot.takeScreenshot();
        Thread.sleep(10000);
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        System.out.println("list size :- "+liElememtsList.size());
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Card Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        _scenario.write("Clicking Replace link");
        Thread.sleep(2000);
        commonElements.click(replace_Link);
        commonElements.waitForElement(replaceCard_NewMemberCard_textBox);
        screenshot.takeScreenshot();
        textBoxObj.setText(replaceCard_NewMemberCard_textBox, newCardNumber);
        comboBoxObj.selectByValue(replaceCard_Reason_dropdown,"Lost Card");
        textBoxObj.setText(replaceCard_Remarks_textBox, "Replace Card");
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        screenshot.takeScreenshot();
        //click finish button
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        replaceCardDataMap.put("ReplaceMessage", commonElements.getText(validationMessageText));
        replaceCardDataMap.put("ReplaceOLD_Card", oldCardNumber);
        replaceCardDataMap.put("ReplaceNEW_Card", newCardNumber);
        return replaceCardDataMap;
    }

    public HashMap<String, String> getMainDBRecords(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.card where CardNo = '"+cardNumber+"'";
        System.out.println("Getting Main DB records from the Matrix DB - Query : select * from MatrixTpReward.dbo.card where CardNo = '"+cardNumber+"'");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        return mainDBRecordMap;
    }

    public HashMap<String, String> getMainDBRecordsForCombined(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.member where id in (select MemberID from MatrixTpReward.dbo.card where CardNo like '"+cardNumber+"');";
        System.out.println("Getting Main DB records from the Matrix DB - Query : select * from MatrixTpReward.dbo.member where id in (select MemberID from MatrixTpReward.dbo.card where CardNo like '"+cardNumber+"');");
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        return mainDBRecordMap;
    }

    public String getSupplementaryCardNumber(String cardNumber) throws SQLException, ClassNotFoundException, InterruptedException {
        createDbConnection db = new createDbConnection();
        String env = System.getProperty("Environment");
        System.out.println("====Reading Main Database " + env);
        String queryDBDetails = "select * from MatrixTpReward.dbo.card where MemberID in (select MemberID from MatrixTpReward.dbo.card where CardNo like '"+cardNumber+"') AND isSupplementary = '1'";
        System.out.println("Getting Main DB records from the Matrix DB - Query : "+ queryDBDetails);
        HashMap<String, String> mainDBRecordMap = db.getDatabaseTableRecords(env, queryDBDetails);
        String cardNumbSup = mainDBRecordMap.get("CardNo");
        System.out.println("Supplementary Crad No : " + cardNumbSup);
        return cardNumbSup;
    }

    public Map<String, String> suspendedCardManagement() throws Exception {
        Map<String, String> suspendedCardDataMap = new HashMap<>();
        System.out.println("Suspending Card");
        _scenario.write("=====Suspending Card=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        commonElements.click(more_link);
        screenshot.takeScreenshot();
        Thread.sleep(10000);
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Card Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        _scenario.write("Clicking Suspend link");
        Thread.sleep(2000);
        commonElements.click(suspend_Link);
        commonElements.waitForElement(suspendCard_Remarks_textBox);
        screenshot.takeScreenshot();
        textBoxObj.setText(suspendCard_Remarks_textBox, "Suspend Card");
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        screenshot.takeScreenshot();
        String membershipCardNumber_cancel = memberShipCard_text.getText();
        suspendedCardDataMap.put("MembershipCardNumber", membershipCardNumber_cancel);
        System.out.println("Suspending Card : " + membershipCardNumber_cancel);
        _scenario.write("Suspending Card : " + membershipCardNumber_cancel);
        //click finish button
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        suspendedCardDataMap.put("SuspendMessage", commonElements.getText(validationMessageText));
        return suspendedCardDataMap;
    }

    public Map<String, String> blackListCardManagement(String cardNumber) throws Exception {
        Map<String, String> blackCardDataMap = new HashMap<>();
        System.out.println("Waiting for 1 minute till all the page objects are loading");
        Thread.sleep(60000);
        System.out.println("Blacklist Card");
        _scenario.write("=====Blacklist Card=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        javaScriptExecutor.scrollDown(0,200);
        System.out.println("Page scrolling");
        Thread.sleep(4000);
        List<WebElement> moreLinkElementList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul//*[contains(text(),'More')]"));
        System.out.println("Size of morelink element :"+ moreLinkElementList.size());
        List<WebElement> liElememtsList = null;
        for (WebElement moreLink : moreLinkElementList){
            Actions build = new Actions(driver);
            Action moreLinkAction = build.moveToElement(moreLink)
                    .click()
                    .build();
            moreLinkAction.perform();
            screenshot.takeScreenshot();
            Thread.sleep(10000);
            liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
            System.out.println("Size of more down :"+ liElememtsList.size());
            if (liElememtsList.size()>=1){
                break;
            }
        }
        try {
            System.out.println("Click Card Management link");
            List<WebElement> cardManagementLinkList = driver.findElements(By.xpath("//a[text()='Card Management' and @title='Card Management']"));
            System.out.println("Click Card Management size : " +cardManagementLinkList.size());
            if (cardManagementLinkList.size()>=1){
                Actions build = new Actions(driver);
                Action cardManagementAction = build.moveToElement(cardManagementTabUnderMore)
                        .click()
                        .build();
                cardManagementAction.perform();
                screenshot.takeScreenshot();
            }else{
                driver.navigate().refresh();
                System.out.println("Waiting for 1 minute till all the page objects are loading");
                Thread.sleep(60000);
                commonElements.waitForElement(transactionAssociationLink_link);
                /*commonElements.click(more_link);*/
                javaScriptExecutor.scrollDown(0,200);
                System.out.println("Page scrolling");
                Thread.sleep(4000);
                List<WebElement> moreLinkElementList_1 = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul//*[contains(text(),'More')]"));
                System.out.println("Size of morelink element :"+ moreLinkElementList_1.size());
                List<WebElement> liElememtsList_1 = null;
                for (WebElement moreLink : moreLinkElementList_1){
                    Actions build = new Actions(driver);
                    Action cardManagementAction = build.moveToElement(moreLink)
                            .click()
                            .build();
                    cardManagementAction.perform();
                    screenshot.takeScreenshot();
                    Thread.sleep(10000);
                    liElememtsList_1 = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
                    System.out.println("Size of more down :"+ liElememtsList_1.size());
                    if (liElememtsList_1.size()>=1){
                        break;
                    }
                }
                System.out.println("Click Card Management link");
                List<WebElement> cardManagementLinkList_1 = driver.findElements(By.xpath("//a[text()='Card Management' and @title='Card Management']"));
                System.out.println("Click Card Management size : " +cardManagementLinkList_1.size());
                if (cardManagementLinkList_1.size()>=1) {
                    Actions build = new Actions(driver);
                    Action cardManagementAction = build.moveToElement(cardManagementTabUnderMore)
                            .click()
                            .build();
                    cardManagementAction.perform();
                    screenshot.takeScreenshot();
                }
            }
        }catch (Exception e){
            System.out.println("TEST FAILED!. Unable to click on tier management under 'More' Tab - "+e.getLocalizedMessage());
        }

        boolean blackListLinkVisibility = false;
        System.out.println("Starting while loop for clickling BlackList link");
        while (blackListLinkVisibility==false){
            System.out.println("Clicking Blacklist link");
            Thread.sleep(8000);
            List<WebElement> blackListLinkList = driver.findElements(By.xpath("//label[text()='Blacklist']"));
            System.out.println("Size of Blacklist element :"+ blackListLinkList.size());
            if (blackListLinkList.size()>=1){
                commonElements.click(blackList_Link_2);
                blackListLinkVisibility=true;
            }else{
                try{
                    driver.navigate().refresh();
                    System.out.println("Page refreshed");
                    System.out.println("Waiting for 1 minute till all the page objects are loading");
                    Thread.sleep(60000);
                    commonElements.waitForElement(transactionAssociationLink_link);
                    javaScriptExecutor.scrollDown(0,200);
                    System.out.println("Page scrolling");
                    Thread.sleep(4000);
                    List<WebElement> moreLinkElementList_1 = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul//*[contains(text(),'More')]"));
                    System.out.println("Size of morelink element :"+ moreLinkElementList_1.size());
                    List<WebElement> liElememtsList_1 = null;
                    for (WebElement moreLink : moreLinkElementList_1){
                        Actions build = new Actions(driver);
                        Action cardManagementAction = build.moveToElement(moreLink)
                                .click()
                                .build();
                        cardManagementAction.perform();
                        screenshot.takeScreenshot();
                        Thread.sleep(10000);
                        liElememtsList_1 = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
                        System.out.println("Size of more down :"+ liElememtsList_1.size());
                        if (liElememtsList_1.size()>=1){
                            break;
                        }
                    }
                    System.out.println("Click Card Management link");
                    List<WebElement> cardManagementLinkList_1 = driver.findElements(By.xpath("//a[text()='Card Management' and @title='Card Management']"));
                    System.out.println("Click Card Management size : " +cardManagementLinkList_1.size());
                    if (cardManagementLinkList_1.size()>=1) {
                        Actions build = new Actions(driver);
                        Action cardManagementAction = build.moveToElement(cardManagementTabUnderMore)
                                .click()
                                .build();
                        cardManagementAction.perform();
                        screenshot.takeScreenshot();
                    }
                    Thread.sleep(10000);
                }catch (Exception e){
                    System.out.println("TEST FAILED!. Unable to click on Blacklist - "+e.getLocalizedMessage());
                }
            }
        }
        Thread.sleep(8000);
        System.out.println("Waiting for blacklistCard_Remarks_textBox");
        commonElements.waitForElement(blacklistCard_Remarks_textBox);
        screenshot.takeScreenshot();
        textBoxObj.setText(blacklistCard_Remarks_textBox, "Blacklist Card");
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        String membershipCardNumber = memberShipCard_text.getText();
        blackCardDataMap.put("MembershipCardNumber", cardNumber);
        screenshot.takeScreenshot();
        //click finish button
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        blackCardDataMap.put("BlacklistMessage", commonElements.getText(validationMessageText));
        return blackCardDataMap;
    }

    public String getCardTypeOfSupplementaryCard() {
        String cardType=null;
        try {
            cardType = commonElements.getText(cardTypeOfSupplementaryCard);
        } catch (Exception e) {
            System.out.println("Unable to find a record for Supplementary card");
            return null;
        }
        return cardType;
    }

    public void clickOnEditOptionInPulldownMenuOfSupplementaryCard() throws Exception {
        Thread.sleep(3000);
        try {
            //Assert.assertTrue(commonElements.isElementVisible(recordDeleteOption));
            commonElements.moveMouseAndClick(editOptionInPulldownMenuOfSupplementaryCard);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickOnPullDownMenuOfSupplementaryCard(){
        try {
            commonElements.click(pulldownMenuofSupplementaryCard);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickOnCardStatusDropdown() {
        try {
            Thread.sleep(5000);
            commonElements.click(cardStatusDropdownInEditPopup);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isCancelCardPopupPresent(){
        try {
            if(commonElements.isElementVisible(cardSelectionDropdown)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("'Cancel Card' Popup is not present");
            return false;
        }
    }

    public void selectCardType(String cardType) {
        try {
            comboBoxObj.selectByVisibleText(cardSelectionDropdown,cardType);
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectReason(String reason) {
        try {
            comboBoxObj.selectByVisibleText(reasonSelectionDropdown,reason);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setRemark(String remark) {
        try {
            textBoxObj.setText(remarksTextarea,remark);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickOnNextButtonCancelCardPopup() throws Exception {
        try {
            commonElements.click(nextButtonInCancelCardPopup);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickOnFinishButtonCancelCardPopup() throws Exception{
        try {
            commonElements.click(finishButtonInCancelCardPopup);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickOnConfirmButton() throws Exception{
        try {
            commonElements.click(confirmButton);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickOnInactiveOptionInCardStatusDropdown(){
        try {
            Thread.sleep(5000);
            commonElements.moveMouseAndClick(inactiveOptionInCardStatusDropdown);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickOnSaveButton(){
        try {
            Thread.sleep(3000);
            commonElements.click(saveButton);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getStatusOfSupplementaryCard() throws Exception {
        try {
            Thread.sleep(5000);
            return commonElements.getText(statusOfSupplementaryCard);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public String getSupplementaryCardNumber() throws Exception {
        String supplementoryCardNumber="";
        try {
            supplementoryCardNumber = commonElements.getText(supplementoryCardNumberText).trim();
        } catch (Exception e) {
            System.out.println("Unable to get Supplementary card Number");
            throw e;
        }
        return supplementoryCardNumber;
    }

    public Map<String, String> mergeCardManagement(String cardNumber) throws Exception {
        Map<String, String> cardMergeDataMap = new HashMap<>();
        Thread.sleep(60000);
        System.out.println("Merge Card");
        _scenario.write("=====Merge Card=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        commonElements.click(more_link);
        screenshot.takeScreenshot();
        Thread.sleep(20000);
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Card Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                Thread.sleep(20000);
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        _scenario.write("Clicking Merge link");
        Thread.sleep(20000);
        commonElements.click(merge_Link);
        Thread.sleep(30000);
        commonElements.waitForElement(merge_Reason_dropdown);
        screenshot.takeScreenshot();
        textBoxObj.setText(merge_cancel_textBox, cardNumber);
        Thread.sleep(5000);
        comboBoxObj.selectByValue(merge_Reason_dropdown,"Duplicated membership accounts");
        Thread.sleep(5000);
        textBoxObj.setText(merge_Remarks_textarea, "Merge Card Remarks");
        Thread.sleep(10000);
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        Thread.sleep(5000);
        screenshot.takeScreenshot();
        commonElements.click(finish_Button);
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        cardMergeDataMap.put("MergeMessage", commonElements.getText(validationMessageText));
        return cardMergeDataMap;
    }

    public void clickOnCancelLink() {
        //see if default element is present
        try {
            Thread.sleep(10000);

            if(commonElements.isElementVisible(cardManagementTab)){  // card Management is visible
                //Scrolling down until element is found
                Thread.sleep(5000);
                System.out.println("'Card Management' tab is visible");


                try {
                    commonElements.click(cardManagementTab);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on card management tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on card management tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on card management tab - "+e.getMessage());
                }

                //Scrolling to the element first
                try {
                    //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", membershipCycleExtensionLink);
                    javaScriptExecutor.scrollDown(0,100);
                    Thread.sleep(5000);
                    System.out.println("===== Scrolling done ======");
                } catch (Exception e) {
                    System.out.println("Exception occurred while scrolling down - "+e.getMessage());
                }

                //checking if element is clickable
                if(commonElements.isElementClickable(cancelLink)) {
                    System.out.println("Cancel link is clickable");
                } else {
                    screenshot.takeScreenshot();
                    System.out.println("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                    _scenario.write("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                    Assert.fail("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                }

                //clicking on the cancel link
                try {
                    commonElements.click(cancelLink);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                }

            } else if (commonElements.isElementVisible(moreTab)){  //More tab is visible
                System.out.println("'More' Tab is visible");
                //click on more tab
                try {
                    commonElements.click(moreTab);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                }


                try {
                    Thread.sleep(3000);
                    commonElements.moveMouseAndClick(cardManagementTabUnderMore);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on Card management under 'More' Tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on Card management under 'More' Tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on Card management under 'More' Tab - "+e.getMessage());
                }

                //waiting for 3 seconds
                Thread.sleep(3000);

                //check again if card management tab is there
                if(commonElements.isElementVisible(cardManagementTab)) {

                    try {
                        commonElements.click(cardManagementTab);
                    } catch (Exception e) {
                        System.out.println("TEST FAILED!. Unable to click on Card management tab - "+e.getMessage());
                        _scenario.write("TEST FAILED!. Unable to click on Card management tab - "+e.getMessage());
                        Assert.fail("TEST FAILED!. Unable to click on Card management tab - "+e.getMessage());
                    }

                    //Scrolling to the element first
                    try {
                        //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", membershipCycleExtensionLink);
                        javaScriptExecutor.scrollDown(0,100);
                        Thread.sleep(5000);
                        System.out.println("===== Scrolling done ======");
                    } catch (Exception e) {
                        System.out.println("Exception occurred while scrolling down - "+e.getMessage());
                    }

                    //checking if element is clickable
                    if(commonElements.isElementClickable(cancelLink)) {
                        System.out.println("Cancel link is clickable");
                    } else {
                        screenshot.takeScreenshot();
                        System.out.println("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                        _scenario.write("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                        Assert.fail("TEST FAILED!. 'Cancel' link IS DISABLED for this member.");
                    }

                    try {
                        commonElements.click(cancelLink);
                    } catch (Exception e) {
                        System.out.println("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                        _scenario.write("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                        Assert.fail("TEST FAILED!. Unable to click on 'Cancel' link - "+e.getMessage());
                    }
                } else {
                    System.out.println("TEST FAILED!. Unable to Locate  Card management tab ");
                    _scenario.write("TEST FAILED!. Unable to Locate  Card management tab ");
                    Assert.fail("TEST FAILED!. Unable to Locate  Card management tab ");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
