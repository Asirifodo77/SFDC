package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import utilities.ReadJenkinsParameters;
import utilities.testDataCleanup;

import java.time.Instant;
import java.util.Map;

public class DataCleanUp_Steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;

    public DataCleanUp_Steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("inside login constructor");
    }

    @When("^I delete all test data in SalesForce and Matrix DB$")
    public void iDeleteAllTestData() throws Throwable {
        System.out.println(Instant.now().toString() + "iDeleteAllTestData");
        //Call deletion Runnable
        testDataCleanup cleaner = new testDataCleanup();
        cleaner.cleanUpData(_scenario);

    }

    @Given("^I delete test data in Salesforce and Matrix DB$")
    public void iDeleteTestDataInSalesforceAndMatrixDB(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNum = "";
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("Card Number To Delete" +  myJsonObj.get(cardNum).getAsString() + "------");
            cardNum = myJsonObj.get(cardNum).getAsString();
            testDataCleanup cleaner = new testDataCleanup();
            cleaner.cleanUpOneMemberCardData(_scenario, cardNum);
        }
    }
}
