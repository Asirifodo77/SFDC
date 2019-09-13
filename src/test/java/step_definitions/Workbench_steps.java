package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import page_objects.Login_pageobjects;
import page_objects.Workbench_pageobjects;

public class Workbench_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects login_pageobjects;
    //WebDriver wDriver = new FirefoxDriver();

    public Workbench_steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        login_pageobjects = new Login_pageobjects(driver,_scenario);
        System.out.println ("Transaction_Search constructor");
    }



    @Given("^Login to workbench and post Notification")
    public void Login_to_workbench_and_post_Notification() throws Throwable  {

        Workbench_pageobjects workbench_pageobjects = new Workbench_pageobjects(driver, _scenario, myJsonObj);
        workbench_pageobjects.loginWorkBenchAndPostNotification();
    }

    @Given("^validate the success message")
    public void validate_the_success_message() throws Throwable {

        Workbench_pageobjects workbench_pageobjects = new Workbench_pageobjects(driver, _scenario, myJsonObj);
        String actualMessage = workbench_pageobjects.getMessage();
        Assert.assertEquals(actualMessage,"success: true");
    }

}
