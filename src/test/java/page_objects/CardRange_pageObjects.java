package page_objects;

import commonLibs.implementation.*;
import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.TakeScreenshot;
import utilities.convertCardNumberToSFStyle;
import utilities.readTestData;

import javax.xml.bind.SchemaOutputResolver;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;

public class CardRange_pageObjects {
    public WebDriver driver;
    public Scenario _scenario;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public checkBoxControls checkBox;
    public TakeScreenshot screenshot;
    public JavaScriptExecutor javaScriptExecutor;
    public String baseURL;
    public String client_id;
    public String client_secret;
    public String username;
    public String password;
    public readTestData testData;


//========================== Page Objects =============================
@FindBy(xpath = "//a[@title='Card Range']")
public WebElement cardRangeTab;

@FindBy(xpath = "//h2[text()='New Card Range']")
public WebElement newCardRangePopupTitle;

@FindBy(xpath = "//a[@title='New']//div[@title='New' and text()='New']")
public WebElement newCardRangeBtn;

@FindBy(xpath = "//*[contains(text(),'Store Location')]//ancestor::div[1]//select")
public WebElement storeLocationDropdown;

@FindBy(xpath = "//*[contains(text(),'Tier')]//ancestor::div[1]//select")
public WebElement tierDropdown;

@FindBy(xpath = "//label[contains(text(),'Manually choose range')]//..//input")
public WebElement manuallyChooseRangeCheckbox;

@FindBy(xpath = "//label//span[contains(text(),'Minimum Value')]//..//..//input")
public WebElement minimumValueTxtbox;

@FindBy(xpath = "//label//span[contains(text(),'Maximum Value')]//..//..//input")
public WebElement maximumValueTextbox;

@FindBy(xpath = "//*[contains(text(),'Remarks')]//ancestor::div[1]//textarea")
public WebElement remarksTextarea;

@FindBy(xpath = "//*[contains(text(),'Next')]//preceding-sibling::button[1]//following::button[contains(text(),'Next')]")
public WebElement nextBtn;

@FindBy(xpath = "//*[contains(text(),'Next')]//preceding-sibling::button[1]")
public WebElement cancelBtn;

@FindBy(xpath = "//div[contains(text(),'Tier:')]//following::div[1]//span[@class='uiOutputText']")
public WebElement tierLabel;

@FindBy(xpath = "//div[contains(text(),'Maximum Value:')]//following::div[1]//span[@class='uiOutputText']")
public WebElement maximumValueLabel;

@FindBy(xpath = "//div[contains(text(),'Minimum Value:')]//following::div[1]//span[@class='uiOutputText']")
public WebElement minimumValueLabel;

@FindBy(xpath = "//div[contains(text(),'Store Location:')]//following::div[1]//span[@class='uiOutputText']")
public WebElement storeLocationLabel;

@FindBy(xpath = "//div[contains(text(),'Remarks:')]//following::div[1]//span[@class='uiOutputText']")
public WebElement remarksLabel;

@FindBy(xpath = "//*[contains(text(),'Finish')]//preceding-sibling::button[1]//following::button[contains(text(),'Finish')]")
public WebElement finishBtn;

@FindBy(xpath = "//*[contains(text(),'Finish')]//preceding-sibling::button[1]")
public WebElement previousBtn;

@FindBy(xpath = "//span[contains(text(),'All') and @class='triggerLinkText selectedListView uiOutputText']")
public WebElement allRecordsFilter;

@FindBy(xpath ="//span[contains(text(),'Recently Viewed') and @class='triggerLinkText selectedListView uiOutputText']")
public WebElement recentlyViewedFilter;

@FindBy(xpath = "//span[contains(text(),'All') and @class=' virtualAutocompleteOptionText']")
public WebElement allFilterToBeClicked;

@FindBy(xpath = "//span[@title='Created Date']")
public WebElement createdDateColumnHeader;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Card Range Name']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement cardRangeNameColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Description']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement storeLocationColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Tier']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement tierColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Minimum Value']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement minimumValueColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Maximum Value']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement maximumValueColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Status']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement statusColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Created By']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span[@class='uiOutputDateTime forceOutputModStampWithPreview']")
public WebElement createdDateColumn;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Remarks']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//span//span")
public WebElement remarksColumn;

@FindBy(xpath = "//tbody//tr[1]//th//following::td[9][@class='slds-cell-edit cellContainer']//span[@class='slds-grid slds-grid--align-spread']//div[@class='forceVirtualActionMarker forceVirtualAction']")
public WebElement recordOptionsPulldownMenu;

@FindBy(xpath = "//div[text()='Delete']")
public WebElement recordDeleteOption;

@FindBy(xpath = "//div[text()='Edit']")
public WebElement recordEditOption;

@FindBy(xpath = "//div[@class='modal-header slds-modal__header']//h2[@class='title slds-text-heading--medium' and text()='Delete Card Range']")
public WebElement deleteCardRagngePopup;

@FindBy(xpath = "//div[@class='modal-footer slds-modal__footer']//button[@title='Delete']")
public WebElement deleteBtn;

@FindBy(xpath = "//a[@class='slds-button slds-button--reset downIcon slds-m-top_xxx-small slds-p-right_xxx-small']")
public WebElement allAndRecentViewArrowdrop;

@FindBy(xpath = "//div[@class='forceModalActionContainer--footerAction forceModalActionContainer']//button[@class='slds-button slds-button--neutral uiButton--default uiButton--brand uiButton forceActionButton' and @title='Save']")
public WebElement saveBtn;

@FindBy(xpath = "//span[@class='label inputLabel uiPicklistLabel-left form-element__label uiPicklistLabel']//span[text()='Status']//following::div[@class='uiMenu'][1]")
public WebElement statusDropdown;

@FindBy(xpath ="//a[@title='Disable' and text()='Disable']")
public WebElement disableOptionInStatusDropdown;

@FindBy(xpath = "//input[@id='input-6']")
public WebElement MainSearchCriteriaDropdown;

@FindBy(xpath = "//lightning-base-combobox-formatted-text[text()='Card Range']//ancestor::li")
public WebElement cardRangeOptionUnderMainSearch;

@FindBy(xpath = "//span[contains(text(),'in Card Range')]//ancestor::li")
public WebElement showAllSearchResultsOption;

@FindBy(xpath = "//input[@class='slds-input slds-text-color_default slds-p-left--none slds-size--1-of-1 input default input uiInput uiInputTextForAutocomplete uiInput--{remove}']")
public WebElement searchBox;

@FindBy(xpath = "//div[text()='Card Range']")
public WebElement cardRangeTitleinSearchResultWindow;

@FindBy(xpath = "//div[text()='Card Range']//..//..//div[@class='slds-list_horizontal searchResultsSummaryText']//div//div[2]//p")
public WebElement searchResultCountLabel;

@FindBy(xpath = "//table[@class='slds-table forceRecordLayout slds-table--header-fixed slds-table--edit slds-table--bordered resizable-cols slds-table--resizable-cols uiVirtualDataTable']//tbody//tr//td[6]//span//div")
public WebElement pulldownArrowInSearchResult;

@FindBy(xpath = "//tbody//tr[1]//th//span[@class='slds-grid slds-grid--align-spread']//a")
public WebElement cardRangeNameLink;

@FindBy(xpath = "//div[@class='test-id__field-label-container slds-form-element__label']//span[text()='Status']//..//..//div[@class='slds-form-element__control slds-grid itemBody']//button[@title='Edit Status']")
public WebElement editIconOfStatusDropdown;










    //======================== End of Page Objects ========================

    public CardRange_pageObjects(WebDriver driver, Scenario scenario) throws FileNotFoundException {
        this.driver = driver;
        this._scenario=scenario;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        checkBox = new checkBoxControls(_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
        testData = new readTestData();
        javaScriptExecutor = new JavaScriptExecutor(driver);

        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")){
            baseURL = testData.readTestData("Preprod_SF_BaseURL");
            client_id=testData.readTestData("Preprod_client_id");
            client_secret=testData.readTestData("Preprod_client_secret");
            username=testData.readTestData("Preprod");
            password=testData.readTestData("SFDCAdmin_Pwd");

        } else if (System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = testData.readTestData("QACore2_SF_BaseURL");
            client_id=testData.readTestData("QACore2_client_id");
            client_secret=testData.readTestData("QACore2_client_secret");
            username=testData.readTestData("QACore2");
            password=testData.readTestData("SFDCAdmin_Pwd");
        }
    }

    public void clickOnEditIconOfStatusDropdown(){
        try {
            Thread.sleep(5000);
            commonElements.click(editIconOfStatusDropdown);
        } catch (Exception e) {
            System.out.println("Unable to click on edit icon of status dropdown - "+e.getLocalizedMessage());
        }
    }

    public void clickOnCardRangeNameLink(){
        try {
            commonElements.click(cardRangeNameLink);
        } catch (Exception e) {
            System.out.println("Unable to click on card Range Name value link - "+e.getLocalizedMessage());
            String resultsCount=null;
            try {
                Thread.sleep(5000);
                resultsCount = getSearchResultCountText();
                Assert.assertEquals(resultsCount.toLowerCase(), "1 Result".toLowerCase());
                System.out.println("Found 1 matching record");

                // clicking again on card range name link
                try {
                    System.out.println("Trying to click on the record second time");
                    commonElements.click(cardRangeNameLink);
                } catch (Exception e1) {
                    System.out.println("Unable to click on card name range record. Actual error - "+ e1.getLocalizedMessage());
                    _scenario.write("Unable to click on card name range record. Actual error - "+ e1.getLocalizedMessage());
                    Assert.fail("TEST FAILED INTENTIONALLY ! Unable to click on card name range record. Actual error");
                }

            } catch (AssertionError | InterruptedException en) {
                System.out.println("There are more than one search results or none at all. Actual record count - "+ resultsCount);
                _scenario.write("There are more than one search results or none at all. Actual record count - "+ resultsCount);
                Assert.fail("TEST FAILED INTENTIONALLY ! There are more than one search results or none at all. Actual record count - " +resultsCount);
            }
        }
    }

    public void clickOnPulldownMenuInSearchResultsPage() {
        try {
            commonElements.click(pulldownArrowInSearchResult);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public WebElement getPulldownMenuElementInSearchResultsPage(){
        return pulldownArrowInSearchResult;
    }

    public String getSearchResultCountText() {
        try {
            driver.navigate().refresh();
            Thread.sleep(5000);
            try {
                if(commonElements.waitForElement(searchResultCountLabel)!=null) {
                    System.out.println("Found search result count element");
                } else {
                    System.out.println("Unable to find result count element");
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("FAILING THE TEST INTENTIONALLY !. Unable to find the search result count element");
                System.out.println("FAILING THE TEST INTENTIONALLY !. Unable to find the search result count element");
                Assert.fail("FAILING THE TEST INTENTIONALLY !. Unable to find the search result count element");
            }
            return commonElements.getText(searchResultCountLabel);
        } catch (Exception e) {
            System.out.println("Unable to get text inside searchResultCountLabel : "+ e.getLocalizedMessage());
            return null;
        }
    }


    public Boolean isCardRangeTitlePresent() {
        try {
            Thread.sleep(5000);
            if(commonElements.waitForElement(cardRangeTitleinSearchResultWindow)!=null) {
                System.out.println("Search result window is loaded");
                return true;
            } else {
                System.out.println("Search result window is NOT loaded");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred when checking element visibility : "+e.getLocalizedMessage());
            return false;
        }

    }

    public void typeAndSearchCardRangeinMainSearchTextBox(String cardRangeName) throws InterruptedException {

        //type the given card range name
        try {
            Thread.sleep(5000);
            textBoxObj.setText(searchBox,cardRangeName);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        //wait for 3 sec again
        Thread.sleep(5000);

        //hit enter key in the keyboard
        try {
            //textBoxObj.hitEnterKey(searchBox);
            clickOnAllSearchResultsOptionUnderMainSearchBox();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void clickOnMainSearchDropdown()  {
        try {
            //commonElements.click(MainSearchCriteriaDropdown);
            javaScriptExecutor.executeJavaScript("arguments[0].click();",MainSearchCriteriaDropdown);
        } catch (Exception e) {
            System.out.println("Unable to click on MainSearchCriteriaDropdown element : "+e.getLocalizedMessage());
        }
    }

    public void clickOnCardRangeOptionUnderMainSearch() {
        try {
            Thread.sleep(5000);
            commonElements.moveMouseAndClick(cardRangeOptionUnderMainSearch);
        } catch (Exception e) {
            System.out.println("Unable to click on CARD RANGE option in main search : "+e.getLocalizedMessage());
        }
    }

    public void clickOnAllSearchResultsOptionUnderMainSearchBox() {
        try {
            Thread.sleep(5000);
            commonElements.moveMouseAndClick(showAllSearchResultsOption);
        } catch (Exception e) {
            System.out.println("Unable to click on all search results option in main search : "+e.getLocalizedMessage());
        }
    }

    public void clickOnCardRangeTab() throws Exception {
        //commonElements.click(cardRangeTab);
        javaScriptExecutor.executeJavaScript("arguments[0].click();",cardRangeTab);

    }

    public void clickOnNewCardRangeButton() throws Exception {
        commonElements.click(newCardRangeBtn);
    }

    public boolean isNewCardRangePopPresent() throws Exception {
        boolean status=false;
        try {
            Thread.sleep(5000);
            status = commonElements.isElementVisible(newCardRangePopupTitle);
        } catch (WebDriverException we) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Webdriver Exception found. "+we.getMessage());
            System.out.println("=============================================================");
            throw we;
        }
        return status;
    }

    public void selectStoreLocation(String storeName) {
        try {
            comboBoxObj.selectByVisibleText(storeLocationDropdown,storeName);
        } catch (WebDriverException e) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Webdriver Exception found. "+e.getMessage());
            System.out.println("=============================================================");
            throw e;
        } catch (Exception ge) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Exception found. "+ge.getMessage());
            System.out.println("=============================================================");
            throw ge;
        }
    }

    public void enableManuallyChooseRangeCheckbox() throws Exception {
        try {
            checkBox.changeCheckboxStatus(manuallyChooseRangeCheckbox,true);
        } catch (WebDriverException e) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Webdriver Exception found. "+e.getMessage());
            System.out.println("=============================================================");
            throw e;
        } catch (Exception ge) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Exception found. "+ge.getMessage());
            System.out.println("=============================================================");
            throw ge;
        }
    }

    public void selectTier(String tier) {
        try {
            comboBoxObj.selectByVisibleText(tierDropdown,tier);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setMinimumValue(String minimumVal) {
        textBoxObj.setText(minimumValueTxtbox,minimumVal);
    }

    public void setMaximumValue(String maximumValue) {
        textBoxObj.clearTextBox(maximumValueTextbox);
        textBoxObj.setText(maximumValueTextbox,maximumValue);
    }

    public void setRemarksValue(String remarksValue) {
        textBoxObj.setText(remarksTextarea,remarksValue);
    }

    public void clickNext() throws Exception {
        commonElements.click(nextBtn);
    }

    public String getTierValue() throws Exception {
        return commonElements.getText(tierLabel);
    }

    public String getMinimumValue() throws Exception {
        return commonElements.getText(minimumValueLabel).replaceAll(" ","").trim();
    }

    public String getMaximumValue() throws Exception {
        return commonElements.getText(maximumValueLabel).replaceAll(" ","").trim();
    }

    public String getStoreLocationValue() throws Exception {
        return commonElements.getText(storeLocationLabel);
    }

    public String getRemarksValue() throws Exception {
        return commonElements.getText(remarksLabel);
    }

    public void clickFinish() throws Exception {
        commonElements.click(finishBtn);
    }

    public void filterAllRecords() throws Exception {
        try {
            if(commonElements.isElementVisible(allAndRecentViewArrowdrop)) {
                System.out.println("Recently Viewed records are shown");
                commonElements.click(allAndRecentViewArrowdrop);
                commonElements.click(allFilterToBeClicked);
                screenshot.takeScreenshot();
            } //else if(commonElements.isElementVisible(allRecordsFilter)) {
//                System.out.println("All records are shown");
//                commonElements.click(allRecordsFilter);
//                commonElements.click(allFilterToBeClicked);
//            }
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void sortTheRecordOrder(String expectedMinimumValue) throws Exception {
        if(isRecordsSortedInOrder(expectedMinimumValue)) {
            System.out.println("Records are sorted in correct order");
            screenshot.takeScreenshot();
        } else {
            //click on created date column header to sort all records in correct order
            System.out.println("Records have to sorted !. clicking on created date column header to sort all records in correct order");
            commonElements.click(createdDateColumnHeader);
            screenshot.takeScreenshot();
        }

    }

    public boolean isRecordsSortedInOrder(String expectedMinimumValue) throws Exception {
        //check if first row's minimum value is correct
        if(getMinimumValueFromTable().replaceAll(" ","").trim().equalsIgnoreCase(expectedMinimumValue)){
            //if true, then records are sorted in order
            return true;
        } else {
            return false;
        }
    }

    public String getMinimumValueFromTable() throws Exception {
        return commonElements.getText(minimumValueColumn);
    }

    public String getCardRangeNameaFromTabel() throws Exception {
        try {
            return commonElements.getText(cardRangeNameColumn);
        } catch (NoSuchElementException e) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Card Range Name' element has changed - "+e.getMessage());
            System.out.println("=============================================================");
            throw e;
        } catch (WebDriverException we) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Unable to find 'Card Range Name' element due to an Webdriver exception - "+we.getMessage());
            System.out.println("=============================================================");
            throw we;
        }
    }

    public String getStoreLocationFromTabel() throws Exception {
        if(commonElements.getText(storeLocationColumn).contains("Card")) {
            return commonElements.getText(storeLocationColumn).replaceAll("Card", "").trim();
        } else {
            return commonElements.getText(storeLocationColumn).trim();
        }
    }

    public String getTierFromTabel() throws Exception {
        return commonElements.getText(tierColumn);
    }

    public String getMinimumValueFromTabel() throws Exception {
        return commonElements.getText(minimumValueColumn).replaceAll(" ","").trim();
    }

    public String getMaximumValueFromTabel() throws Exception {
        return commonElements.getText(maximumValueColumn).replaceAll(" ","").trim();
    }

    public String getStatusFromTabel() throws Exception {
        return commonElements.getText(statusColumn);
    }

    public String getCreatedDateFromTabel() throws Exception {
        String createdDateRow = commonElements.getText(createdDateColumn).substring(0,10).trim().replaceAll(",","").trim();

        String[] dateParts = createdDateRow.split("/");
        String day = dateParts[0];
        if(day.toCharArray().length<2) {
            day="0"+day;
        }

        String month = dateParts[1];
        if(month.toCharArray().length<2) {
            month="0"+month;
        }

        String year = dateParts[2];
        System.out.println(day+"/"+month+"/"+year);
        return day+"/"+month+"/"+year;
    }

    public String getTodaysDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getRemarksFromTabel() throws Exception {
        return commonElements.getText(remarksColumn);
    }

    public boolean isCreatedDateHeaderPresent() throws Exception {
        try {
            Thread.sleep(10000);
            return commonElements.isElementVisible(createdDateColumnHeader);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public void clickOnRecordOptionsPulldownMenu() throws Exception {
        try {
            commonElements.click(recordOptionsPulldownMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnDeleteOptionInPulldownMenu() throws Exception {
        Thread.sleep(3000);
        try {
            //Assert.assertTrue(commonElements.isElementVisible(recordDeleteOption));
            commonElements.moveMouseAndClick(recordDeleteOption);
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
        }

//        try {
//
//            commonElements.click(recordDeleteOption);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void clickOnEditOptionInPulldownMenu() throws Exception {
        Thread.sleep(3000);
        try {
            //Assert.assertTrue(commonElements.isElementVisible(recordDeleteOption));
            commonElements.moveMouseAndClick(recordEditOption);
        } catch(NoSuchElementException e) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Element has changed. "+e.getMessage());
            System.out.println("=============================================================");
            throw e;

        }catch (WebDriverException e){
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Webdriver exception found. "+e.getMessage());
            System.out.println("=============================================================");
            throw e;

        }catch (Exception e) {
            System.out.println("=========================== ERROR ===========================");
            System.out.println("Eexception found. "+e.getMessage());
            System.out.println("=============================================================");
            throw e;
        }

    }

    public void deleteCardRangeRecord() throws Exception {
        try {
            Thread.sleep(3000);
            Assert.assertTrue(commonElements.isElementVisible(deleteCardRagngePopup));
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("Delete card range pop is not loaded - "+e.getLocalizedMessage());
            _scenario.write("Delete card range pop is not loaded - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Delete card range pop is not loaded");
        }

        try {
            commonElements.click(deleteBtn);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("Unable to click on Delete button - "+e.getLocalizedMessage());
            _scenario.write("Unable to click on Delete button - "+e.getLocalizedMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to click on Delete button");
        }
    }

    public boolean isEditPopupPresent() throws Exception {
        Thread.sleep(3000);
        try {
            return commonElements.isElementVisible(saveBtn);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public void clickOnStatusDropdown() throws Exception {
        try {
            commonElements.click(statusDropdown);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void clickOnDisableOptionFromStatusDropdown() throws InterruptedException {
        Thread.sleep(3000);
        try {
            //Assert.assertTrue(commonElements.isElementVisible(recordDeleteOption));
            commonElements.moveMouseAndClick(disableOptionInStatusDropdown);
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
            _scenario.write(e.getLocalizedMessage());
        }
    }

    public void clickOnSaveButton() throws Exception {
        try {
            Thread.sleep(3000);
            commonElements.click(saveBtn);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deleteCardRangeFromSF(String accessToken, String cardRangeName) {
        System.out.println("=================== INSIDE DELETE CARD RANGE FROM SF FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForDeleteCardRange(cardRangeName);
        String token = accessToken.trim();


        Response response=
                null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/json" , "Authorization", token)
                    .delete(URL)
                    .then()
                    //.contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());

        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        if (response.getStatusCode()==HttpStatus.SC_NO_CONTENT) {
            System.out.println("Card Range record with card range name: "+cardRangeName +" was deleted successfully from Salesforce");

        } else {
            System.out.println("Unable to delete card Range from Salesfroce using the API call. Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Actual Response from the server- "+response.asString());
            _scenario.write("Unable to delete card Range from Salesfroce using the API call. Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Actual Response from the server - "+response.asString());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to delete the card Range record  for range - " +cardRangeName+ " prior to testing.");
        }

    }

    public String getCardRangeNamefromSF(String accessToken, String minimumVal, String maximumVal) {
        System.out.println("=================== INSIDE GET CARD RANGE NAME FROM SF FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String CardRangeName ="";
        String querryURL = getQueryForCardRangeName(minimumVal, maximumVal);
        String token = accessToken.trim();


        Response response=
                null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/json" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        if (response.getStatusCode()==HttpStatus.SC_OK) {
            //Getting RegistrationDivisionCode
            try {
                String IdOriginal = response.jsonPath().getString("records.Id").replaceAll("\\p{P}", "").trim();
                CardRangeName = IdOriginal.substring(0,IdOriginal.toCharArray().length-3).trim();
            } catch (Exception e) {
                System.out.println("NO RECORD OF CARD RANGE NAME RETURNED FROM API");
                CardRangeName="";
            }

        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }
            return CardRangeName;
    }

    public String getQueryForCardRangeName( String minimumVal,String maximumVal){
        convertCardNumberToSFStyle converter = new convertCardNumberToSFStyle();
        return "query/?q=SELECT Id From Card_Range__c WHERE Maximum_Value__c='"+converter.convertCardNoToSFstyle(maximumVal)+"' AND Minimal_Value__c='"+converter.convertCardNoToSFstyle(minimumVal)+"'";
    }
    public String getQueryForDeleteCardRange(String cardRangeName) {
        return "sobjects/Card_Range__c/"+cardRangeName;
    }


}
