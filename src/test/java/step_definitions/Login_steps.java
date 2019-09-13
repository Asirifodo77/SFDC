package step_definitions;

import com.google.gson.JsonObject;


import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import page_objects.Login_pageobjects;

import java.net.MalformedURLException;

public class Login_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;

    public Login_steps() throws MalformedURLException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("inside login constructor");
        loginPage = new Login_pageobjects(driver,_scenario);
    }

    @Given("^I login to SFDC$")
    public void iLoginToSF() throws Throwable  {
        //Login_pageobjects loginPage = new Login_pageobjects(driver,_scenario);
        loginPage.loginToSF();
    }

    @Given("^I login to Salesforce$")
    public void iLoginToSalesforce() throws Throwable {
        //Login_pageobjects loginPage = new Login_pageobjects(driver,_scenario);
        loginPage.loginToSF();
    }

    @And("^I logout from Salesforce$")
    public void iLogoutFromSalesforce() throws Throwable {
        //Login_pageobjects loginPage = new Login_pageobjects(driver,_scenario);
        loginPage.logoutSF();
    }

    @Given("^I login to Salesforce with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iLoginToSalesforceWithAnd(String username, String password) throws Throwable {

    }

    @Given("^I login to Salesforce with CRM user$")
    public void iLoginToSalesforceWithCRMUser() throws Throwable {
        Login_pageobjects loginPage = new Login_pageobjects(driver,_scenario);
        loginPage.loginToSFWithCRMUser();
    }
}
