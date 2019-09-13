package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.textBoxControls;
import utilities.TakeScreenshot;

import java.util.Map;

public class EditMember_pageobjects {

    public WebDriver driver;
    public Scenario _scenario;
    private JsonObject myJsonObj;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public TakeScreenshot screenshot;

    @FindBy(how= How.CSS, using = "a[href=\"/lightning/o/Contact/home\"] > .slds-truncate")
    public WebElement member;

    @FindBy(how= How.CSS, using = "table.slds-table > tbody > tr:nth-of-type(1) > td.slds-cell-edit.cellContainer:nth-of-type(6) > span > .forceVirtualActionMarker.forceVirtualAction > a.rowActionsPlaceHolder.slds-button.slds-button--icon-x-small.slds-button--icon-border-filled.keyboardMode--trigger > lightning-icon.slds-icon-utility-down.slds-icon_container > lightning-primitive-icon")
    public WebElement dropdown;

    @FindBy(how= How.CSS, using = ".scrollable > .uiMenuItem:nth-of-type(1) > a")
    public WebElement edit;

    @FindBy(how= How.CSS, using = ".full > .test-id__section.slds-section.full.forcePageBlockSection.forcePageBlockSectionEdit:nth-of-type(1) > .test-id__section-content.slds-section__content.section__content > .slds-form.slds-is-editing > .full.forcePageBlockSectionRow:nth-of-type(1) > .slds-has-flexi-truncate.slds-p-horizontal_medium.full.forcePageBlockItem.forcePageBlockItemEdit:nth-of-type(1) > .slds-form-element.slds-form-element_edit.slds-hint-parent.slds-p-vertical_xx-small > .slds-form-element__control > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement staff_name;

    @FindBy(how= How.CSS, using = ".full > .test-id__section.slds-section.full.forcePageBlockSection.forcePageBlockSectionEdit:nth-of-type(1) > .test-id__section-content.slds-section__content.section__content > .slds-form.slds-is-editing > .full.forcePageBlockSectionRow:nth-of-type(2) > .slds-has-flexi-truncate.slds-p-horizontal_medium.full.forcePageBlockItem.forcePageBlockItemEdit:nth-of-type(1) > .slds-form-element.slds-form-element_edit.slds-hint-parent.slds-p-vertical_xx-small > .slds-form-element__control > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement dept_name;

    @FindBy(how= How.CSS, using = ".full > .test-id__section.slds-section.full.forcePageBlockSection.forcePageBlockSectionEdit:nth-of-type(4) > .test-id__section-content.slds-section__content.section__content > .slds-form.slds-is-editing > .full.forcePageBlockSectionRow:nth-of-type(1) > .slds-has-flexi-truncate.slds-p-horizontal_medium.full.forcePageBlockItem.forcePageBlockItemEdit:nth-of-type(1) > .slds-form-element.slds-form-element_edit.slds-hint-parent.slds-p-vertical_xx-small > .slds-form-element__control > .uiInput.forceInputPicklist.uiInput--default > .uiMenu > .uiPopupTrigger > div > div > a")
    public WebElement countrycode;

    @FindBy(how= How.CSS, using = ".scrollable > .uiMenuItem.uiRadioMenuItem:nth-of-type(3) > a")
    public WebElement country;

    @FindBy(how= How.CSS, using = ".slds-form > .full.forcePageBlockSectionRow:nth-of-type(1) > .slds-has-flexi-truncate.slds-p-horizontal_medium.full.forcePageBlockItem.forcePageBlockItemEdit:nth-of-type(2) > .slds-form-element.slds-form-element_edit.slds-hint-parent.slds-p-vertical_xx-small > .slds-form-element__control > .uiInput.uiInputPhone.uiInput--default.uiInput--input > input[type=\"tel\"].input")
    public WebElement number;

    @FindBy(how= How.CSS, using = ".full > .test-id__section.slds-section.full.forcePageBlockSection.forcePageBlockSectionEdit:nth-of-type(4) > .test-id__section-content.slds-section__content.section__content > .slds-form.slds-is-editing > .full.forcePageBlockSectionRow:nth-of-type(2) > .slds-has-flexi-truncate.slds-p-horizontal_medium.full.forcePageBlockItem.forcePageBlockItemEdit > .slds-form-element.slds-form-element_edit.slds-hint-parent.slds-p-vertical_xx-small > .slds-form-element__control > .uiInput.uiInputCheckbox.uiInput--default.uiInput--checkbox > input[type=\"checkbox\"]")
    public WebElement validcheck;

    @FindBy(how= How.CSS, using = "button[type=\"button\"].slds-button.slds-button--neutral.uiButton--default.uiButton--brand.uiButton.forceActionButton > .label.bBody")
    public WebElement savebtn;

    @FindBy(how= How.XPATH, using = "//*[text()[contains(.,'was saved')]]")
    public WebElement successMessage;

    public EditMember_pageobjects(WebDriver driver, Scenario _scenario, JsonObject myJsonObj) {
        this.driver=driver;
        this._scenario=_scenario;
        this.myJsonObj = myJsonObj;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        commonElements = new CommonElements(driver, _scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    public void editingCurrentMember(Map<String,String> data) throws Exception {
        //click on member
        commonElements.click(member);
        //click on dropdown list and select edit
        commonElements.click(dropdown);
        commonElements.click(edit);
        String staffName = data.get("<StaffName>");
        if (staffName != null) {
            textBoxObj.clearTextBox(staff_name);
            textBoxObj.setText(staff_name, myJsonObj.get(staffName).getAsString());
        }
        //edit staff dept
        String DeptName = data.get("<DepartmentName>");
        if (DeptName != null) {
            textBoxObj.clearTextBox(dept_name);
            textBoxObj.setText(dept_name, myJsonObj.get(DeptName).getAsString());
        }
        //add phone number
        String phone = data.get("<PhoneNumber>");
        if (phone != null) {
            textBoxObj.clearTextBox(number);
            textBoxObj.setText(number, myJsonObj.get(phone).getAsString());
        }
        //choose country code
        commonElements.click(countrycode);
        commonElements.click(country);
        //phone number to be valid
        commonElements.click(validcheck);
        //process to save
        commonElements.click(savebtn);
        String successMsg = commonElements.getText(successMessage);
        //ensure that success msg is displayed
        Assert.assertTrue(successMessage.isDisplayed());
        screenshot.takeScreenshot();
    }

}
