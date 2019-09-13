package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import utilities.ReadJenkinsParameters;
import utilities.TakeScreenshot;

import java.util.Map;

public class TransactionDisassociation_pageobjects {

    private WebDriver driver;
    private Scenario _scenario;
    private JsonObject myJsonObj;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;

    @FindBy(how= How.CSS, using = "fieldset.slds-form-element > .slds-form-element__control.slds-m-bottom_small:nth-of-type(2) > .iconPointer > label.iconPointer")
    public WebElement transactionDisAssociationLink_link;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-form-element__control slds-p-right_small']//select[@class='requiredInput fixed-element-control select uiInput uiInputSelect uiInput--default uiInput--select']")
    public WebElement membershipCardType_DropDown;

    @FindBy(how= How.CSS, using = "input[type='text'].slds-lookup__search-input")
    public WebElement division_testBox;

    @FindBy(how= How.XPATH, using = "//*[@id=\"listbox-option-unique-id-01\"]")
    public WebElement divisionSelect_dropdown;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-form-element__control slds-m-left_small slds-p-right_small']//select[@class='requiredInput fixed-element-control select uiInput uiInputSelect uiInput--default uiInput--select']")
    public WebElement remarks_dropdown;

    @FindBy(how= How.CSS, using = ".slds-size_2-of-6.slds-p-right_x-small > input[type='text'].input.uiInput.uiInputText.uiInput--default.uiInput--input")
    public WebElement transactionNumber_FirstTwo_textbox;

    @FindBy(how= How.CSS, using = ".slds-size_2-of-6.slds-p-horizontal_xx-small > input[type='text'].input.uiInput.uiInputText.uiInput--default.uiInput--input")
    public WebElement transactionNumber_Middle5_textbox;

    @FindBy(how= How.CSS, using = ".slds-size_2-of-6.slds-p-left_x-small > input[type='text'].input.uiInput.uiInputText.uiInput--default.uiInput--input")
    public WebElement transactionNumber_Last5_textbox;

    @FindBy(how= How.CSS, using = ".form-element > input[type='text'].input")
    public WebElement transactionDate;

    @FindBy(how= How.XPATH, using = "//button[contains(text(),\'Add Transaction\')]")
    public WebElement addTransaction_button;

    @FindBy(how= How.CSS, using = ".slds-modal__footer > button[type=\"button\"].slds-button.slds-button_brand")
    public WebElement transactionNext_button;

    @FindBy(how= How.CSS, using = ".slds-modal__footer > button[type=\"button\"].slds-button.slds-button_brand")
    public WebElement transactionFinish_button;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    @FindBy(how= How.XPATH, using = "//*[@id=\"brandBand_1\"]/div/div[2]/div[1]/div[5]/ul/li[2]/div/div/button/span")
    public WebElement createMember_button;

    public TransactionDisassociation_pageobjects(WebDriver driver, Scenario _scenario, JsonObject myJsonObj) {
        this.driver=driver;
        this._scenario=_scenario;
        this.myJsonObj = myJsonObj;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    public String createTransactionDisAssociation(Map<String, String> data) throws Exception {
        System.out.println("Creating Transaction DisAssociation");
        _scenario.write("=====Creating Transaction DisAssociation=====");
        commonElements.waitForElement(transactionDisAssociationLink_link);
        commonElements.click(transactionDisAssociationLink_link);
        commonElements.waitForElement(membershipCardType_DropDown);
        commonElements.click(membershipCardType_DropDown);
        comboBoxObj.selectByVisibleText(membershipCardType_DropDown,"Primary");
        System.out.println("Membership card type : Primary");
        _scenario.write("Membership card type : Primary");
        String division = data.get("<Division>");
        System.out.println("Division : "+ myJsonObj.get(division).getAsString());
        _scenario.write("Division : "+ myJsonObj.get(division).getAsString());

        textBoxObj.clearTextBox(division_testBox);
        textBoxObj.setText(division_testBox, myJsonObj.get(division).getAsString());
        Thread.sleep(2000);
        commonElements.waitForElement(divisionSelect_dropdown);
        commonElements.click(divisionSelect_dropdown);
        System.out.println("Remarks : Transaction with PD discount");
        _scenario.write("Remarks : Transaction with PD discount");
        comboBoxObj.selectByValue(remarks_dropdown, "Transaction with PD discount");
        String transaction = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Transaction_Number>"))).getAsString();
        System.out.println("Transaction Number : "+ transaction);
        _scenario.write("Transaction Number : "+ transaction);
        textBoxObj.setText(transactionNumber_FirstTwo_textbox, transaction.substring(0, (transaction.length()-10)));
        textBoxObj.setText(transactionNumber_Middle5_textbox, transaction.substring((transaction.length()-10), (transaction.length()-5)));
        textBoxObj.setText(transactionNumber_Last5_textbox, transaction.substring((transaction.length()-5), transaction.length()));
        System.out.println("Transaction Date : "+ myJsonObj.get(data.get("<Transaction_Date>")).getAsString());
        _scenario.write("Transaction Date : "+ myJsonObj.get(data.get("<Transaction_Date>")).getAsString());
        textBoxObj.setText(transactionDate, myJsonObj.get(data.get("<Transaction_Date>")).getAsString());
        Thread.sleep(5000);
        screenshot.takeScreenshot();
        commonElements.click(addTransaction_button);
        Thread.sleep(5000);
        commonElements.waitForElement(transactionNext_button);
        screenshot.takeScreenshot();
        commonElements.click(transactionNext_button);
        Thread.sleep(5000);
        commonElements.waitForElement(transactionFinish_button);
        commonElements.click(transactionFinish_button);
        commonElements.waitForElement(validationMessageText);
        System.out.println("Transaction DisAssociation Message :" + commonElements.getText(validationMessageText));
        _scenario.write("Transaction DisAssociation Message : " + commonElements.getText(validationMessageText));
        Assert.assertTrue(validationMessageText.isDisplayed());
        screenshot.takeScreenshot();
        return commonElements.getText(validationMessageText);
    }


}
