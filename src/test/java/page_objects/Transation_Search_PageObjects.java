package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.textBoxControls;

import java.util.Map;

public class Transation_Search_PageObjects {

    private WebDriver driver;
    private Scenario _scenario;
    private JsonObject myJsonObj;
    public textBoxControls textBoxObj;
    public String var_Txn_Date;
    public String var_Txn_Division;
    public CommonElements commonElements;

    @FindBy(how= How.XPATH, using = "#input-1-12 > .slds-media__body > lightning-base-combobox-formatted-text.slds-truncate")
    public WebElement universal_search_Transacation;

    @FindBy(how= How.XPATH, using = "//input[@placeholder='Search Salesforce']")
    public WebElement search_Text;

    @FindBy(how= How.XPATH, using = "//span[@class=\"uiOutputDate\"]")
    public WebElement txn_Date;

    @FindBy(how= How.CSS, using = "th.slds-cell-edit > span > a[href=\"/lightning/r/02i0k00000KkBitAAF/view\"].slds-truncate.outputLookupLink.slds-truncate.forceOutputLookup")
    public WebElement search_result;

    @FindBy(how= How.CSS, using = ".slds-form > .full.forcePageBlockSectionRow:nth-of-type(1) > .slds-has-flexi-truncate.slds-p-horizontal_x-small.full.forcePageBlockItem.forcePageBlockItemView:nth-of-type(1) > .slds-form-element.slds-m-bottom_xx-small.slds-form-element_edit.slds-grow.slds-hint-parent > .slds-form-element__control.itemBody > .test-id__field-value.slds-form-element__static.slds-grow.slds-form-element_separator.is-read-only > .uiOutputTextArea")
    public WebElement txn_Div;

    public Transation_Search_PageObjects(WebDriver driver, Scenario _scenario, JsonObject myJsonObj) {
        this.driver=driver;
        this._scenario=_scenario;
        this.myJsonObj = myJsonObj;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        commonElements = new CommonElements(driver,_scenario);
    }

    public void searchTransactions(Map<String, String> data) throws Exception {
        String txn_Number = data.get ("<Transaction_Number>");
        if (txn_Number != null) {
            textBoxObj.clearTextBox(search_Text);
            textBoxObj.setText(search_Text, myJsonObj.get(txn_Number).getAsString());
            search_Text.sendKeys(Keys.ENTER);
            commonElements.waitForElement(search_result);
            commonElements.click(search_result);
            Thread.sleep (10000);
            var_Txn_Date = commonElements.getText(txn_Date);
            System.out.println(var_Txn_Date);
            var_Txn_Division = commonElements.getText(txn_Div);
            System.out.println(var_Txn_Division);
        }
    }

}
