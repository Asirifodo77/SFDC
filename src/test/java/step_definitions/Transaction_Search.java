package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import page_objects.Transation_Search_PageObjects;

import java.time.Instant;
import java.util.Map;

public class Transaction_Search {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    //WebDriver wDriver = new FirefoxDriver();

    public Transaction_Search() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println ("Transaction_Search constructor");
    }

    @When("^I search for the transaction to fetch Division and TxnDate$")
    public void TxnSearch(DataTable table) throws Throwable {
        System.out.println (Instant.now ().toString () + "TransactionSearch");
        Thread.sleep (10000);
        for (Map<String, String> data : table.asMaps (String.class, String.class)) {
            Transation_Search_PageObjects transation_Search_PageObjects = new Transation_Search_PageObjects(driver, _scenario, myJsonObj );
            transation_Search_PageObjects.searchTransactions(data);
        }

    }

}
