package page_objects;

import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import utilities.TakeScreenshot;
import utilities.readTestData;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

public class Login_pageobjects {
    public WebDriver driver;
    public Scenario _scenario;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;

    //page objects
    @FindBy(id = "username")
    public WebElement txt_Username;

    @FindBy(id = "password")
    public WebElement txt_Password;

    @FindBy(id = "Login")
    public WebElement btn_Login;

    @FindBy(xpath = "//button[contains(@class, 'bare branding-userProfile-button slds-button uiButton forceHeaderButton')]")
    public WebElement btn_setup;

    @FindBy(xpath = "//a[contains(@class, 'profile-link-label logout uiOutputURL')]")
    public WebElement btn_Logout;

    //constructor
    public Login_pageobjects(WebDriver driver, Scenario _scenario) {
        this.driver=driver;
        this._scenario=_scenario;

        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    //common methods
    public void loginToSF() throws FileNotFoundException, MalformedURLException, InterruptedException {
        //creating object to read test data
        readTestData testData = new readTestData();
        System.out.println(Instant.now().toString() + " sF_login_page");
        String url = testData.readTestData("Application_URL") ;
        final URL appURL = new URL(url);
        driver.get(appURL.toString());

        //Taking screenshot
        screenshot.takeScreenshot();

        String env = null;
        try {
            env = System.getProperty("Environment");
            System.out.println("------" + env + "------");
        } catch (NullPointerException e) {
            System.out.println("Environment variable is not Set. Java exception is : "+e);
        }
        String username= null;
        String password=null;
        if(env.equalsIgnoreCase("Preprod")){
            username = "Preprod";
            password = "SFDCAdmin_Pwd";

        } else if(env.equalsIgnoreCase("QACore2")){
            username = "QACore2";
            password = "SFDCAdmin_Pwd";
        }
        Thread.sleep(10000);
        try {
            //clear the username and type username
            textBoxObj.clearTextBox(txt_Username);
            textBoxObj.setText(txt_Username, testData.readTestData(username));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Username. Java exception is : "+e);
            _scenario.write("Unable to set Username. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }

        try {
            // clear the password field and typing password
            textBoxObj.clearTextBox(txt_Password);
            textBoxObj.setText(txt_Password, testData.readTestData(password));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Password. Java exception is : "+e);
            _scenario.write("Unable to set Password. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }

        //taking screenshot
        screenshot.takeScreenshot();

        try {
            //click on login button
            commonElements.click(btn_Login);
        } catch (Exception e) {
            System.out.println("Unable to Click Login button. Java exception is : "+e);
            _scenario.write("Unable to Click Login button. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }
    }

    public void loginToSFWithOtherCredentials(String username, String password) throws FileNotFoundException, MalformedURLException, InterruptedException {
        //creating object to read test data
        readTestData testData = new readTestData();
        System.out.println(Instant.now().toString() + " sF_login_page");
        String url = testData.readTestData("Application_URL") ;
        final URL appURL = new URL(url);
        driver.get(appURL.toString());

        //taking screenshot
        screenshot.takeScreenshot();

        String env = null;
        try {
            env = System.getProperty("Environment");
            System.out.println("------" + env + "------");
        } catch (NullPointerException e) {
            System.out.println("Environment variable is not Set. Java exception is : "+e);;
        }
        Thread.sleep(10000);
        try {
            //clear the username and type username
            textBoxObj.clearTextBox(txt_Username);
            textBoxObj.setText(txt_Username, testData.readTestData(username));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Username. Java exception is : "+e);
            _scenario.write("Unable to set Username. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }

        try {
            // clear the password field and typing password
            textBoxObj.clearTextBox(txt_Password);
            textBoxObj.setText(txt_Password, testData.readTestData(password));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Password. Java exception is : "+e);
            _scenario.write("Unable to set Password. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }
        //taking screenshot
        screenshot.takeScreenshot();
        try {
            //click on login button
            commonElements.click(btn_Login);
        } catch (Exception e) {
            System.out.println("Unable to Click Login button. Java exception is : "+e);
            _scenario.write("Unable to Click Login button. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }
    }

    public void logoutSF(){
        try {
            commonElements.click(btn_setup);
            Thread.sleep(2000);
            commonElements.waitForElement(btn_Logout);
            commonElements.click(btn_Logout);
        }catch (Exception e) {
            String msg = "Element not found or not visible";
            _scenario.write("Element not found or not visible - ");
            _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            throw new WebDriverException(msg+e.getMessage());
        }
    }

    public void loginToSFWithCRMUser() throws FileNotFoundException, MalformedURLException, InterruptedException {
        //creating object to read test data
        readTestData testData = new readTestData();
        System.out.println(Instant.now().toString() + " sF_login_page");
        String url = testData.readTestData("Application_URL") ;
        final URL appURL = new URL(url);
        driver.get(appURL.toString());

        //Taking screenshot
        screenshot.takeScreenshot();

        String env = null;
        try {
            env = System.getProperty("Environment");
            System.out.println("------" + env + "------");
        } catch (NullPointerException e) {
            System.out.println("Environment variable is not Set. Java exception is : "+e);;
        }
        String username= null;
        String password=null;
        if(env.equalsIgnoreCase("Preprod")){
            username = "Preprod_CRM";
            password = "SFDCAdmin_Pwd";

        } else if(env.equalsIgnoreCase("QACore2")){
            username = "QACore2_CRM";
            password = "SFDCAdmin_Pwd";
        }

        Thread.sleep(10000);
        try {
            //clear the username and type username
            textBoxObj.clearTextBox(txt_Username);
            textBoxObj.setText(txt_Username, testData.readTestData(username));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Username. Java exception is : "+e);
            _scenario.write("Unable to set Username. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }

        try {
            // clear the password field and typing password
            textBoxObj.clearTextBox(txt_Password);
            textBoxObj.setText(txt_Password, testData.readTestData(password));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to set Password. Java exception is : "+e);
            _scenario.write("Unable to set Password. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }

        //taking screenshot
        screenshot.takeScreenshot();

        try {
            //click on login button
            commonElements.click(btn_Login);
        } catch (Exception e) {
            System.out.println("Unable to Click Login button. Java exception is : "+e);
            _scenario.write("Unable to Click Login button. Java exception is : "+e);
            //taking screenshot
            screenshot.takeScreenshot();
        }
    }

}
