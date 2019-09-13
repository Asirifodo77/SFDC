package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.*;
import org.testng.Assert;
import utilities.TakeScreenshot;
import utilities.*;

import java.time.Instant;
import java.util.Map;

public class CreateMember_pageobjects {

    private WebDriver driver;
    private Scenario _scenario;
    private JsonObject myJsonObj;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;

    @FindBy(how= How.XPATH, using = "//span[contains(text(),'Create Member')]")
    public WebElement txt_member;

    @FindBy(how= How.XPATH, using = "//span[text()='Membership Card Number']//..//..//input")  //input[contains(@class,'invalidInput input')]
    public WebElement mem_card;

    @FindBy(how= How.CSS, using = ".slds-col.slds-p-left_xx-small.slds-size--1-of-2 > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement email;

    @FindBy(how= How.CSS, using = "div:nth-of-type(5) > .slds-col.slds-p-top_xx-small.slds-p-left_xx-small.slds-size--2-of-12:nth-of-type(1) > .uiInput.uiInput--default > select")
    public WebElement salutation;

    @FindBy(how= How.CSS, using = "div:nth-of-type(5) > .slds-col.slds-p-top_xx-small.slds-p-left_xx-small.slds-size--2-of-12:nth-of-type(2) > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement txt_Lastname;

    @FindBy(how= How.CSS, using = "div:nth-of-type(5) > .slds-col.slds-p-top_xx-small.slds-p-left_xx-small.slds-size--2-of-12:nth-of-type(3) > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement txt_Firstname;

    @FindBy(how= How.CSS, using = ".slds-p-around_xx-small > .slds-size_1-of-6:nth-of-type(1) > .uiInput.uiInput--default > select")
    public WebElement Residency;

    @FindBy(how= How.CSS, using = ".slds-col.slds-size--1-of-2 > .uiInput.uiInput--default > select")
    public WebElement language;

    @FindBy(how= How.CSS, using = "input[name='consent']")
    public WebElement opt;

    @FindBy(how= How.CSS, using = "div:nth-of-type(20) > .slds-size--4-of-12.slds-p-left_xx-small.slds-p-top--xx-small:nth-of-type(1) > .uiInput.uiInput--default > select")
    public WebElement tier_Drop;

    @FindBy(how= How.CSS, using = "div:nth-of-type(20) > .slds-size--4-of-12.slds-p-left_xx-small.slds-p-top--xx-small:nth-of-type(2) > .uiInput.uiInput--default > select")
    public WebElement card_Drop;

    @FindBy(how= How.CSS, using = "div:nth-of-type(20) > .slds-size--4-of-12.slds-p-left_xx-small.slds-p-top--xx-small:nth-of-type(3) > .uiInput.uiInput--default > select")
    public WebElement enrollment_Drop;

    @FindBy(how= How.CSS, using = ".slds-p-top--xx-small > .slds-size--4-of-12.slds-p-left_xx-small.slds-p-top--xx-small:nth-of-type(1) > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement staff_No;

    @FindBy(how= How.XPATH, using = "//button[contains(text(),'Submit')]")
    public WebElement btn_Save;

    @FindBy(how= How.XPATH, using = "//*[text()[contains(.,'was successfully created')]]")
    public WebElement txt_SuccessMsg;

    @FindBy(how= How.CSS, using = "div:nth-of-type(5) > .slds-col.slds-p-top_xx-small.slds-p-left_xx-small.slds-size--2-of-12:nth-of-type(6) > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement firstNameNative_TextBox;

    @FindBy(how= How.CSS, using = "div:nth-of-type(5) > .slds-col.slds-p-top_xx-small.slds-p-left_xx-small.slds-size--2-of-12:nth-of-type(5) > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement lastNameNative_TextBox;

    @FindBy(how= How.CSS, using = ".slds-col.slds-p-left_xx-small.slds-size--4-of-6 > .uiInput.uiInputText.uiInput--default.uiInput--input > input[type=\"text\"].input")
    public WebElement mobileNO_TextBox;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Dialing Code')]/ancestor::div[1]/select")
    public WebElement mobileCountryCode;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    public CreateMember_pageobjects(WebDriver driver, Scenario _scenario, JsonObject myJsonObj) {
        this.driver=driver;
        this._scenario=_scenario;
        this.myJsonObj = myJsonObj;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    public void createMemberInSF(Map<String,String> data) throws Exception {
        String env = System.getProperty("Environment");
        System.out.println(Instant.now().toString() + "iCreateANewMember");
        //clicking on 'Create Member' tab in Salesfroce
        try {
            commonElements.waitForElement(txt_member);
            commonElements.click(txt_member);
            Thread.sleep(7000);
        } catch (Exception e) {
            System.out.println("Unable to click on 'Create Member' tab in Salesfroce due to en exception - "+e.getMessage());
        }

        String memNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<MemberNumber>"));
        //commonElements.waitForElement(mem_card);  // commenting out this wait for element.
        if (memNum != null) {
            try {
                commonElements.click(mem_card);
                textBoxObj.clearTextBox(mem_card);
                textBoxObj.setText(mem_card, myJsonObj.get(memNum).getAsString());
            } catch (Exception e) {
                System.out.println("mem_card textbox is not found");
            }
        } else {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
            _scenario.write("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
            Assert.fail("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
        }

        String salutationS = data.get("<SalutationEnglish>");
        if (salutationS != null) {
            comboBoxObj.selectByVisibleText(salutation, myJsonObj.get(salutationS).getAsString());
        }
        String firstName = data.get("<FirstName>");
        System.out.print("THE FIRST NAME IS "+firstName);
        if (firstName != null) {
            textBoxObj.clearTextBox(txt_Firstname);
            textBoxObj.setText(txt_Firstname, myJsonObj.get(firstName).getAsString());
        }
        //enter last name
        String lastname = data.get("<LastName>");
        System.out.print("THE LAST NAME IS"+lastname);
        if (lastname != null) {
            textBoxObj.clearTextBox(txt_Lastname);
            textBoxObj.setText(txt_Lastname, myJsonObj.get(lastname).getAsString());
        }
        //enter email
        String emailS = data.get("<Email>");
        if (emailS != null) {
            textBoxObj.clearTextBox(email);
            textBoxObj.setText(email, myJsonObj.get(emailS).getAsString());
        }
        String residenceS = data.get("<Residence>");
        if (residenceS != null) {
            comboBoxObj.selectByVisibleText(Residency, myJsonObj.get(residenceS).getAsString());
        }
        String languageS = data.get("<PreferredLanguage>");
        if (languageS != null) {
            comboBoxObj.selectByVisibleText(language, myJsonObj.get(languageS).getAsString());
        }
        String OptInForMarketing = data.get("<OptInForMarketingCommunication>");
        if (OptInForMarketing != null) {
            commonElements.click(opt);
        }
        String tierD = data.get("<Tier>");
        if (tierD != null) {
            comboBoxObj.selectByVisibleText(tier_Drop, myJsonObj.get(tierD).getAsString());
        }
        String cardPickupMethod = data.get("<CardPickupMethod>");
        if (cardPickupMethod != null) {
            comboBoxObj.selectByVisibleText(card_Drop, myJsonObj.get(cardPickupMethod).getAsString());
        }
        String enrollmentLocation = data.get("<EnrollmentLocation>");
        if (enrollmentLocation != null) {
            comboBoxObj.selectByVisibleText(enrollment_Drop, myJsonObj.get(enrollmentLocation).getAsString());
        }
        String numb = data.get("<StaffNo>");
        if (numb != null) {
            textBoxObj.clearTextBox(staff_No);
            textBoxObj.setText(staff_No, myJsonObj.get(numb).getAsString());
        }
        String firstNameNative = data.get("<FirstName_Native>");
        if (firstNameNative != null) {
            textBoxObj.clearTextBox(firstNameNative_TextBox);
            textBoxObj.setText(firstNameNative_TextBox, myJsonObj.get(firstNameNative).getAsString());
        }
        String lastNameNative = data.get("<LastName_Native>");
        if (lastNameNative != null) {
            textBoxObj.clearTextBox(lastNameNative_TextBox);
            textBoxObj.setText(lastNameNative_TextBox, myJsonObj.get(lastNameNative).getAsString());
        }
        String mobilePhone = data.get("<Mobile_Phone>");
        if (mobilePhone != null) {
            String countryCode = myJsonObj.get(mobilePhone).getAsString().split("-")[0];
            String mobileNo = myJsonObj.get(mobilePhone).getAsString().split("-")[1];
            textBoxObj.clearTextBox(mobileNO_TextBox);
            textBoxObj.setText(mobileNO_TextBox, mobileNo);
            comboBoxObj.selectByVisibleText(mobileCountryCode, countryCode);
        }
        //submit
        System.out.println(Instant.now().toString() + "iCreateANewMember");
        commonElements.click(btn_Save);
        screenshot.takeScreenshot();
    }

    public String createMemberInSFReturnsMessage(Map<String,String> data) throws Exception {
        String env = System.getProperty("Environment");
        System.out.println(Instant.now().toString() + "iCreateANewMember");
        //clicking on 'Create Member' tab in Salesfroce
        try {
            try {
                commonElements.waitForElement(txt_member);
                commonElements.click(txt_member);
                Thread.sleep(7000);
            } catch (Exception e) {
                System.out.println("Unable to click on 'Create Member' tab in Salesfroce due to en exception - "+e.getMessage());
            }

            String memNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<MemberNumber>"));
            //commonElements.waitForElement(mem_card);  // commenting out this wait for element.
            if (memNum != null) {
                try {
                    commonElements.click(mem_card);
                    textBoxObj.clearTextBox(mem_card);
                    textBoxObj.setText(mem_card, myJsonObj.get(memNum).getAsString());
                } catch (Exception e) {
                    System.out.println("mem_card textbox is not found");
                }
            } else {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
                _scenario.write("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
                Assert.fail("TEST FAILED INTENTIONALLY !. Member Number is Null in Test Data. Please check if test data file is accurate");
            }

            String salutationS = data.get("<SalutationEnglish>");
            if (salutationS != null) {
                comboBoxObj.selectByVisibleText(salutation, myJsonObj.get(salutationS).getAsString());
            }
            String firstName = data.get("<FirstName>");
            System.out.print("THE FIRST NAME IS "+firstName);
            if (firstName != null) {
                textBoxObj.clearTextBox(txt_Firstname);
                textBoxObj.setText(txt_Firstname, myJsonObj.get(firstName).getAsString());
            }
            //enter last name
            String lastname = data.get("<LastName>");
            System.out.print("THE LAST NAME IS"+lastname);
            if (lastname != null) {
                textBoxObj.clearTextBox(txt_Lastname);
                textBoxObj.setText(txt_Lastname, myJsonObj.get(lastname).getAsString());
            }
            //enter email
            String emailS = data.get("<Email>");
            if (emailS != null) {
                textBoxObj.clearTextBox(email);
                textBoxObj.setText(email, myJsonObj.get(emailS).getAsString());
            }
            String residenceS = data.get("<Residence>");
            if (residenceS != null) {
                comboBoxObj.selectByVisibleText(Residency, myJsonObj.get(residenceS).getAsString());
            }
            String languageS = data.get("<PreferredLanguage>");
            if (languageS != null) {
                comboBoxObj.selectByVisibleText(language, myJsonObj.get(languageS).getAsString());
            }
            String OptInForMarketing = data.get("<OptInForMarketingCommunication>");
            if (OptInForMarketing != null) {
                commonElements.click(opt);
            }
            String tierD = data.get("<Tier>");
            if (tierD != null) {
                comboBoxObj.selectByVisibleText(tier_Drop, myJsonObj.get(tierD).getAsString());
            }
            String cardPickupMethod = data.get("<CardPickupMethod>");
            if (cardPickupMethod != null) {
                comboBoxObj.selectByVisibleText(card_Drop, myJsonObj.get(cardPickupMethod).getAsString());
            }
            String enrollmentLocation = data.get("<EnrollmentLocation>");
            if (enrollmentLocation != null) {
                comboBoxObj.selectByVisibleText(enrollment_Drop, myJsonObj.get(enrollmentLocation).getAsString());
            }
            String numb = data.get("<StaffNo>");
            if (numb != null) {
                textBoxObj.clearTextBox(staff_No);
                textBoxObj.setText(staff_No, myJsonObj.get(numb).getAsString());
            }
            String firstNameNative = data.get("<FirstName_Native>");
            if (firstNameNative != null) {
                textBoxObj.clearTextBox(firstNameNative_TextBox);
                textBoxObj.setText(firstNameNative_TextBox, myJsonObj.get(firstNameNative).getAsString());
            }
            String lastNameNative = data.get("<LastName_Native>");
            if (lastNameNative != null) {
                textBoxObj.clearTextBox(lastNameNative_TextBox);
                textBoxObj.setText(lastNameNative_TextBox, myJsonObj.get(lastNameNative).getAsString());
            }
            String mobilePhone = data.get("<Mobile_Phone>");
            if (mobilePhone != null) {
                String countryCode = myJsonObj.get(mobilePhone).getAsString().split("-")[0];
                String mobileNo = myJsonObj.get(mobilePhone).getAsString().split("-")[1];
                textBoxObj.clearTextBox(mobileNO_TextBox);
                textBoxObj.setText(mobileNO_TextBox, mobileNo);
                comboBoxObj.selectByVisibleText(mobileCountryCode, countryCode);
            }
            //submit
            System.out.println(Instant.now().toString() + "iCreateANewMember");
            commonElements.click(btn_Save);
            commonElements.waitForElement(validationMessageText);
            screenshot.takeScreenshot();

        }catch (Exception e){
            commonElements.waitForElement(validationMessageText);
            screenshot.takeScreenshot();
        }

        return commonElements.getText(validationMessageText);
    }


}