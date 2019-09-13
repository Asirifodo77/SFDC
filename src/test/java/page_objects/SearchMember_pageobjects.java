package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import org.testng.Assert;
import utilities.ReadJenkinsParameters;
import utilities.TakeScreenshot;
import utilities.readTestData;
import java.util.List;
import java.util.Map;

public class SearchMember_pageobjects {
    public WebDriver driver;
    public Scenario _scenario;
    public readTestData testData;
    public DSAcreateMember_pageObjects DSAcreateMember;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;

    @FindBy(css = ".utilitybar > .slds-utility-bar__item:nth-of-type(1) > .flexipageComponent > .slds-utility-bar__item.oneUtilityBarItem > button[type=\"button\"].bare.slds-button.slds-utility-bar__action.uiButton > .itemTitle")
    public WebElement search_Tab;

    @FindBy(xpath = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[1]/div[1]/div/div[1]/div[2]/input")
    public  WebElement card_Numb;

    @FindBy(xpath = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[1]/div[1]/div/div[2]/button")
    public  WebElement search_Btn;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[1]/div[1]/div[2]/input")
    public WebElement firstName_TextBox;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div[2]/input")
    public WebElement lastName_TextBox;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[3]/div[1]/div[2]/input")
    public WebElement email_TextBox;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[4]/div[1]/div[2]/input")
    public WebElement firstNameNative_TextBox;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[5]/div[1]/div[2]/input")
    public WebElement lastNameNative_TextBox;

    @FindBy(how= How.XPATH, using = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[1]/div/div[6]/div[1]/div[2]/input")
    public WebElement mobile_TextBox;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Membership Tier')]/ancestor::table")
    public WebElement dataTable;

    @FindBy(how= How.XPATH, using = "//span[contains(@class, 'toastMessage slds-text-heading--small forceActionsText')]")
    public WebElement offlineElement;

    @FindBy(xpath = "html/body/div[5]/div[1]/section/div[2]/div/div[2]/div[1]/div[3]/div/div/div/div[2]/div[2]/button[2]")
    public  WebElement advancedSearch_Btn;

    @FindBy(xpath = "//span[contains(text(),'Create Member')]")
    public  WebElement advancedSearch_Btn_memberSearch;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    @FindBy(xpath = "//div[@class='slds-align_absolute-center slds-p-top_medium search-message' and text()='No matching records found.']")
    public WebElement noRecordsFoundMessage;

    @FindBy(xpath = "//*[contains(text(),'Membership Tier')]/ancestor::table/tbody/tr[1]/th/div/div")
    public WebElement oneRecordFoundInSearchTable;

    public SearchMember_pageobjects(WebDriver driver, Scenario _scenario) {
        this.driver=driver;
        this._scenario=_scenario;
        PageFactory.initElements(driver,this);
        testData=new readTestData();
        DSAcreateMember = new DSAcreateMember_pageObjects(_scenario);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    //common methods
    public void searchMemberThroughCardNumber(DataTable table, String cardNumber) throws Exception {
       // System.out.println(Instant.now().toString() + "iSearchForTheMemberThroughCardNumber");
        System.out.println("Inside search member function");
        WebDriverWait wait = new WebDriverWait(driver, 120);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(search_Tab));
        } catch (Exception e) {
            driver.navigate().refresh();
        }
        commonElements.click(search_Tab);
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Enter card number

            cardNum = data.get("<Card_Number>");
            if (cardNum != null) {
                commonElements.waitForElementToBeClickable(driver, card_Numb);
                textBoxObj.clearTextBox(card_Numb);
                cardNum = cardNumber;
                System.out.println( "The card number inside Json is - "+cardNum);
                //typing card number

                try {
                    Thread.sleep(5000);
                    textBoxObj.setText(card_Numb, cardNum);
                    //taking screenshot
                    screenshot.takeScreenshot();
                } catch (Exception e) {
                    System.out.println("Unable to set text in search box");
                    screenshot.takeScreenshot();
                    throw new Exception("Unable to set text in search box");
                }


                //clicking on search button
                try {
                    Thread.sleep(3000);
                    commonElements.click(search_Btn);
                } catch (Exception e) {
                    System.out.println("Unable to click on 'Search' button in search box");
                    screenshot.takeScreenshot();
                    throw new Exception("Unable to click on 'Search' button in search box");
                }

                //wait for 3 seconds
                Thread.sleep(3000);

                // check if search_Btn is visible. if visible, then check if error message is found and validate
                if(commonElements.isElementVisible(search_Btn)){
                    if(commonElements.isElementVisible(noRecordsFoundMessage) && commonElements.getText(noRecordsFoundMessage).equalsIgnoreCase("No matching records found.")){
                        screenshot.takeScreenshot();
                        System.out.println("TEST FAILED Intentionally, Unable to proceed because there are no valid member with card number - "+cardNum);
                        _scenario.write("TEST FAILED Intentionally, Unable to proceed because there are no valid member with card number - "+cardNum);
                        Assert.fail("TEST FAILED Intentionally, Unable to proceed because there are no valid member with card number - "+cardNum);
                    }
                } else {  //else , do nothing
                    System.out.println("Member found successfully");
                }

            }
        }
    }

    public void searchMemberThroughCardNumber(DataTable table, JsonObject myJsonObj) throws Exception{
        String env = System.getProperty("Environment");
        Thread.sleep(10000);
        commonElements.waitForElement(search_Tab);
        commonElements.click(search_Tab);
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Enter card number
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            if (cardNum != null){
                commonElements.waitForElementToBeClickable(driver, card_Numb);
                Thread.sleep(3000);
                textBoxObj.clearTextBox(card_Numb);
                textBoxObj.setText(card_Numb, myJsonObj.get(cardNum).getAsString());
            }
        }
        //click on search button
        Thread.sleep(5000);
        commonElements.click(search_Btn);
        try {
            String offline = commonElements.getText(offlineElement);
            System.out.println(" Card number = "+offline);
            //driver.navigate().refresh();
            commonElements.waitForElementToBeClickable(driver, search_Tab);
            commonElements.click(search_Tab);
            Thread.sleep(5000);
            commonElements.waitForElementToBeClickable(driver, card_Numb);
            textBoxObj.clearTextBox(card_Numb);
            textBoxObj.setText(card_Numb, myJsonObj.get(cardNum).getAsString());
            //click on search button
            //WebElement tabElement1 = driver.findElement(SearchMember_pageobjects.search_Btn);
            commonElements.click(search_Btn);
            Thread.sleep(3000);
            System.out.println("Finishing of try block");
        } catch(NoSuchElementException e){
            System.out.println("Offline message is not present");
            e.printStackTrace();
        }
    }

    public void memberSearchFromAnyFields(String key, String value) throws Exception {
        System.out.println("Inside search member function >> "+key+" as "+value+"");
        _scenario.write("Inside search member function >> "+key+" as "+value+"");
        Thread.sleep(6000);
        WebDriverWait wait = new WebDriverWait(driver, 120);
        System.out.println("Search Tab click");
        try {
            commonElements.waitForElementToBeClickable(driver, search_Tab);
            commonElements.click(search_Tab);
            commonElements.waitForElement(firstName_TextBox);
            System.out.println("Search Tab click >> try");
        } catch (Exception e) {
            driver.navigate().refresh();
            commonElements.waitForElementToBeClickable(driver, search_Tab);
            commonElements.click(search_Tab);
            commonElements.waitForElement(firstName_TextBox);
            System.out.println("Search Tab click >> catch");
        }
        Thread.sleep(6000);
        if (key.equalsIgnoreCase("FirstName")){
            searchMemberTextBoxHandled(firstName_TextBox ,value);
        }else if (key.equalsIgnoreCase("LastName")){
            searchMemberTextBoxHandled(lastName_TextBox ,value);
        }else if (key.equalsIgnoreCase("Email")){
            searchMemberTextBoxHandled(email_TextBox ,value);
        }else if (key.equalsIgnoreCase("FirstNameNative")){
            searchMemberTextBoxHandled(firstNameNative_TextBox ,value);
        }else if (key.equalsIgnoreCase("LastNameNative")){
            searchMemberTextBoxHandled(lastNameNative_TextBox ,value);
        }else if (key.equalsIgnoreCase("Mobile")){
            searchMemberTextBoxHandled(mobile_TextBox ,value);
        }
        screenshot.takeScreenshot();
        Thread.sleep(8000);
        try{
            commonElements.click(advancedSearch_Btn);
            System.out.println("advancedSearch_Btn >> try");
            Thread.sleep(5000);
        }catch (Exception e){
            commonElements.click(search_Tab);
            System.out.println("advancedSearch_Btn >> catch");
            Thread.sleep(6000);
            commonElements.click(advancedSearch_Btn);
        }

        try {
            List<WebElement> tableElement = driver.findElements(By.xpath("//*[contains(text(),'Membership Tier')]/ancestor::table"));
            if (tableElement.size()>=1){
                System.out.println("There are more members for the given search hence click first element from the Table loaded");
                screenshot.takeScreenshot();
                commonElements.click(oneRecordFoundInSearchTable);
                Thread.sleep(8000);
            }else{
                System.out.println("Only one member available for the given search");
            }
        }catch (Exception e){
            screenshot.takeScreenshot();
            System.out.println("advancedSearch >> Table >> catch block");
            e.printStackTrace();
        }
    }

    public void searchMemberTextBoxHandled(WebElement element, String value) throws Exception {
        try {
            textBoxObj.clearTextBox(element);
            textBoxObj.setText(element, value);
            System.out.println("inside try block");
        }catch (Exception e){
            driver.navigate().refresh();
            commonElements.waitForElement(search_Tab);
            commonElements.click(search_Tab);
            Thread.sleep(6000);
            commonElements.waitForElement(element);
            textBoxObj.clearTextBox(element);
            textBoxObj.setText(element, value);
            System.out.println("inside catch block");
            screenshot.takeScreenshot();
        }
    }

    public void memberFullSearch(Map<String,String> data, JsonObject myJsonObj) throws Exception {
        try{
            commonElements.waitForElement(search_Tab);
            commonElements.click(search_Tab);
            screenshot.takeScreenshot();
            Thread.sleep(6000);
        }catch (Exception e){
            driver.navigate().refresh();
            commonElements.waitForElement(search_Tab);
            commonElements.click(search_Tab);
            screenshot.takeScreenshot();
            Thread.sleep(6000);
        }
        commonElements.waitForElement(firstName_TextBox);
        textBoxObj.clearTextBox(firstName_TextBox);
        textBoxObj.setText(firstName_TextBox, myJsonObj.get(data.get("<FirstName>")).getAsString());
        textBoxObj.clearTextBox(lastName_TextBox);
        textBoxObj.setText(lastName_TextBox, myJsonObj.get(data.get("<LastName>")).getAsString());
        textBoxObj.clearTextBox(email_TextBox);
        textBoxObj.setText(email_TextBox, myJsonObj.get(data.get("<Email>")).getAsString());
        textBoxObj.clearTextBox(firstNameNative_TextBox);
        textBoxObj.setText(firstNameNative_TextBox, myJsonObj.get(data.get("<FirstNameNative>")).getAsString());
        textBoxObj.clearTextBox(lastNameNative_TextBox);
        textBoxObj.setText(lastNameNative_TextBox, myJsonObj.get(data.get("<LastNameNative>")).getAsString());
        String mobileNo = myJsonObj.get(data.get("<Mobile>")).getAsString().split("-")[1];
        textBoxObj.clearTextBox(mobile_TextBox);
        textBoxObj.setText(mobile_TextBox, mobileNo);
        screenshot.takeScreenshot();
        Thread.sleep(8000);
        screenshot.takeScreenshot();
        commonElements.click(advancedSearch_Btn);
    }

    public String getSearchWarningMessage() throws Exception {
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        return commonElements.getText(validationMessageText);
    }

    //Method will click on the advanced member search tab on the bottom left corner in SF
    public void clickAdvancedMemberSearch() throws Exception {
        try {
            commonElements.waitForElement(search_Tab);
            commonElements.click(search_Tab);
            screenshot.takeScreenshot();
            Thread.sleep(6000);
        } catch (Exception e) {
            driver.navigate().refresh();
            commonElements.waitForElement(search_Tab);
            commonElements.click(search_Tab);
            screenshot.takeScreenshot();
            Thread.sleep(6000);
        }
    }

    public void setFirstName(String firstName) {
        textBoxObj.setText(firstName_TextBox,firstName);
    }

    public void setLastName(String lastName) {
        textBoxObj.setText(lastName_TextBox,lastName);
    }

    public void clickAdvancedSearchButton() {
        try {
            commonElements.click(advancedSearch_Btn);
        } catch (Exception e) {
            System.out.println("Unable to click on 'Advanced Search' button. Exception - "+e.getMessage());
        }
    }

    public boolean isAdvanceSearchButtonVisible() {
        try {
            return commonElements.isElementVisible(advancedSearch_Btn);
        } catch (Exception e) {
            return false;
        }
    }


}
