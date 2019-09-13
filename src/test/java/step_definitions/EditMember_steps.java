package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import page_objects.EditMember_pageobjects;

import java.time.Instant;
import java.util.Map;

public class EditMember_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;

    public EditMember_steps(){
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("Edit member constructor");
    }

    @When("^Edit a current member$")
    public void editACurrentMember(DataTable table) throws Throwable {
        System.out.println(Instant.now().toString() + "editACurrentMember");
        Thread.sleep(5000);
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            EditMember_pageobjects editMember_pageobjects = new EditMember_pageobjects(driver, _scenario, myJsonObj);
            editMember_pageobjects.editingCurrentMember(data);
        }
    }

}
