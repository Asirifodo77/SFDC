package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page_objects.MemberValidation_pageobjects;
import page_objects.TierManagement_pageobjects;

import java.util.Map;

public class TierManagement_steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    private Map<String, String> manualDowngradeDataMap;
    public MemberValidation_pageobjects memberValidation_pageobjects;

    public TierManagement_steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        memberValidation_pageobjects = new MemberValidation_pageobjects(driver, _scenario);
    }

    @When("^I read the membership Information for manual downgrade$")
    public void iReadTheMembershipInformationForManualDowngrade() throws Throwable {
        TierManagement_pageobjects tierManagement_pageobjects = new TierManagement_pageobjects(driver, _scenario);
        memberValidation_pageobjects.readMembershipInformation();
        boolean memberTierValue = false;
        if (memberValidation_pageobjects.CardTier_sf.equalsIgnoreCase("Prestige Diamond") || memberValidation_pageobjects.CardTier_sf.equalsIgnoreCase("Prestige Ruby")){
            memberTierValue = true;
            int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
            manualDowngradeDataMap = tierManagement_pageobjects.manualDowngradeLogic(rowCount);
        }else{
            _scenario.write("Current Membership tier is : " + memberValidation_pageobjects.CardTier_sf);
            System.out.println("Current Membership tier is : " + memberValidation_pageobjects.CardTier_sf);
            _scenario.write("Membership Tier is not a Prestige Diamond or Prestige Ruby");
            System.out.println("Membership Tier is not a Prestige Diamond or Prestige Ruby");
            Assert.assertEquals(memberTierValue, true);
        }
    }


}
