package step_definitions;


import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import org.openqa.selenium.*;
import page_objects.TransactionDisassociation_pageobjects;

import java.util.Map;
import org.junit.Assert;

public class TransactionDisassociation_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    private String validationMessageText;

    public TransactionDisassociation_steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println ("Disassociate Txn constructor");
    }

    @And("^I Disassociate transaction with Card number$")
    public void transactionDisassociation(DataTable table) throws Throwable {
        TransactionDisassociation_pageobjects transactionDisassociation = new TransactionDisassociation_pageobjects(driver, _scenario, myJsonObj);

        for (Map<String, String> data : table.asMaps (String.class, String.class)) {
            validationMessageText = transactionDisassociation.createTransactionDisAssociation(data);
        }

    }

    @And("^Transaction DisAssociation message should be \"(.*)\"$")
    public void validateTDAMessage(String message) throws Throwable {
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
