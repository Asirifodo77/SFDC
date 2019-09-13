package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page_objects.CreateMember_pageobjects;

import java.util.Map;


public class CreateMember_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String memberCreateMessage;
    //WebDriver wDriver = new FirefoxDriver();

    public CreateMember_steps(){
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("Create member constructor");
    }

    @When("^I create a new member in SFDC$")
    public void iCreateANewMember(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            CreateMember_pageobjects createMember_pageobjects = new CreateMember_pageobjects(driver, _scenario, myJsonObj);
            createMember_pageobjects.createMemberInSF(data);
        }
    }


    @When("^I create a new member on Salesforce$")
    public void iCreateANewMemberOnSalesforce(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            CreateMember_pageobjects createMember_pageobjects = new CreateMember_pageobjects(driver, _scenario, myJsonObj);
            createMember_pageobjects.createMemberInSF(data);
        }
    }

    @When("^I create a new member on Salesforce to validate the message$")
    public void iCreateANewMemberOnSalesforceToValidateTheMessage(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            CreateMember_pageobjects createMember_pageobjects = new CreateMember_pageobjects(driver, _scenario, myJsonObj);
            memberCreateMessage = createMember_pageobjects.createMemberInSFReturnsMessage(data);
        }
    }

    @Then("^I validate the member creation message is \"([^\"]*)\"$")
    public void iValidateTheMemberCreateMessageIs(String message) throws Throwable {
        _scenario.write("Validate the member creation message");
        _scenario.write("Expected message: "+message+"");
        _scenario.write("Actual message: "+memberCreateMessage+"");
        System.out.println("Validate the member creation message");
        System.out.println("Expected message : "+message+"");
        System.out.println("Actual message : "+memberCreateMessage+"");
        Assert.assertEquals(message, memberCreateMessage);
    }
}
