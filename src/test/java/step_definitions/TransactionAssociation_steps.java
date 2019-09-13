package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import org.openqa.selenium.*;
import page_objects.TransactionAssociation_PageObjects;

import java.util.Map;
import org.junit.Assert;

public class TransactionAssociation_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    private String validationMessageText;

    public TransactionAssociation_steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println ("Associate Txn constructor");
    }

    @And("^I Associate transaction with Card number$")
    public void transactionAssociation(DataTable table) throws Throwable {

        TransactionAssociation_PageObjects transactionAssociation = new TransactionAssociation_PageObjects(driver, _scenario, myJsonObj);

        for (Map<String, String> data : table.asMaps (String.class, String.class)) {
            validationMessageText = transactionAssociation.createTransactionAssociation(data);
        }
    }

    @And("^Transaction Association message should be \"(.*)\"$")
    public void validateTAMessage(String message) throws Throwable {
        String tier = System.getProperty("Tier");
        System.out.println("Tier is : "+ tier);
        if (tier==null){
            tier = "Not_Added";
        }
        /*if (!(tier.equalsIgnoreCase("All"))){
            driver.close();
        }*/
        Assert.assertEquals(message, validationMessageText);
    }


}
