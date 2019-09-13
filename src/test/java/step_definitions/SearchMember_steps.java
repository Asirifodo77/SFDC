package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import org.testng.Assert;
import page_objects.SearchMember_pageobjects;

import java.time.Instant;
import java.util.Map;

public class SearchMember_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public SearchMember_pageobjects searchMember_pageobjects;

    public SearchMember_steps(){
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("search member constructor");
    }

    @And("^I search for the member through Card Number$")
    public void iSearchForTheMemberThroughCardNumber(DataTable table) throws Throwable {
        System.out.println(Instant.now().toString() + "iSearchForTheMemberThroughCardNumber");
        searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        searchMember_pageobjects.searchMemberThroughCardNumber(table, myJsonObj);
    }

    @When("^I search for the member through \"(.*)\" as \"(.*)\"$")
    public void iSearchForTheMemberThroughAs(String key, String value) throws Exception {
        SearchMember_pageobjects searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        searchMember_pageobjects.memberSearchFromAnyFields(key, value);
    }

    @When("^I search for the member with all fields$")
    public void iSearchForTheMemberWithAllFields(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            SearchMember_pageobjects searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
            searchMember_pageobjects.memberFullSearch(data, myJsonObj);
        }
    }

    @And("^I search for the member with Card Number$")
    public void iSearchForTheMemberWithCardNumber(DataTable table) throws Throwable {
        System.out.println(Instant.now().toString() + "iSearchForTheMemberThroughCardNumber");
        searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        searchMember_pageobjects.searchMemberThroughCardNumber(table, myJsonObj);
    }

    @Then("^I validate \"([^\"]*)\" warning message$")
    public void iValidateWarningMessage(String expectedMessage) throws Throwable {
        searchMember_pageobjects = new SearchMember_pageobjects(driver,_scenario);
        String actualMessage = searchMember_pageobjects.getSearchWarningMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
