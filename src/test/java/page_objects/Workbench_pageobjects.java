package page_objects;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import utilities.TakeScreenshot;

import java.net.URL;
import java.time.Instant;

public class Workbench_pageobjects {

    private WebDriver driver;
    private Scenario _scenario;
    private JsonObject myJsonObj;
    public textBoxControls textBoxObj;
    public selectBoxControls comboBoxObj;
    public CommonElements commonElements;
    public TakeScreenshot screenshot;

    @FindBy(how= How.ID, using = "oauth_env")
    public WebElement dropdown_Env;

    @FindBy(how= How.ID, using = "termsAccepted")
    public WebElement checkbox_agree;

    @FindBy(how= How.ID, using = "loginBtn")
    public WebElement btn_Login_SF;

    @FindBy(how= How.CSS, using = "#nav > li:nth-child(6) > a")
    public WebElement menubox_utilities;

    @FindBy(how= How.CSS, using = "#nav > li:nth-child(6) > ul > li:nth-child(1) > a")
    public WebElement submenu_utilities;

    @FindBy(how= How.XPATH, using = "//*[@id='mainBlock']/form/p[2]/label[2]/input")
    public WebElement radio_btn_method;

    @FindBy(how= How.ID, using = "urlInput")
    public WebElement txt_url;

    @FindBy(how= How.XPATH, using = "//*[@id='requestBodyContainer']/textarea")
    public WebElement txt_body_;

    @FindBy(how= How.ID, using = "execBtn")
    public WebElement btn_execute;

    @FindBy(how= How.CSS, using = "#responseList > li:nth-child(2)")
    public WebElement get_success;


    public Workbench_pageobjects(WebDriver driver, Scenario _scenario, JsonObject myJsonObj) {
        this.driver=driver;
        this._scenario=_scenario;
        this.myJsonObj = myJsonObj;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    public void loginWorkBenchAndPostNotification() throws Exception {

        System.out.println(Instant.now().toString() + "Workbench Page");
        String url = myJsonObj.get("Workbench_URL").getAsString();
        final URL appURL = new URL(url);
        driver.get(appURL.toString());
        screenshot.takeScreenshot();
        comboBoxObj.selectByVisibleText(dropdown_Env, "Sandbox");
        commonElements.click(checkbox_agree);
        screenshot.takeScreenshot();
        commonElements.click(btn_Login_SF);
        String userName = myJsonObj.get("SFDCAdmin_UserName").getAsString();
        String password = myJsonObj.get("SFDCAdmin_Pwd").getAsString();
        Login_pageobjects login_pageobjects = new Login_pageobjects(driver, _scenario);
        login_pageobjects.loginToSFWithOtherCredentials(userName, password);
        screenshot.takeScreenshot();
        Thread.sleep(5000);
        Thread.sleep(2500);
        commonElements.click(menubox_utilities);
        commonElements.click(submenu_utilities);
        commonElements.click(radio_btn_method);
        textBoxObj.clearTextBox(txt_url);
        textBoxObj.setText(txt_url, myJsonObj.get("Rest_Explorer_URL").getAsString());
        textBoxObj.clearTextBox(txt_body_);
        textBoxObj.setText(txt_body_, myJsonObj.get("Request_Body").getAsString());
        commonElements.click(btn_execute);
        screenshot.takeScreenshot();
    }

    public String getMessage() throws Exception {
        screenshot.takeScreenshot();
        String message = commonElements.getText(get_success);
        return message;
    }

}
