package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.chain.web.faces.FacesGetLocaleCommand;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page_objects.*;
import property.Property;
import utilities.ReadJenkinsParameters;
import utilities.TakeScreenshot;
import utilities.readTestData;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Deactivate_Supplementary_Card_Steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects loginPage;
    public readTestData testData;
    public String Environment;
    public String token="";
    public String cardNo;
    public SearchMember_pageobjects searchMember;
    public CardValidation_pageobjects card;
    public TakeScreenshot screenshot;

    public Deactivate_Supplementary_Card_Steps() {
        this.driver = Hooks.driver;
        this._scenario = Hooks._scenario;
        Environment = System.getProperty("Environment");

        //creating loginPage object
        loginPage = new Login_pageobjects(driver,_scenario);

        //creating search page object
        searchMember = new SearchMember_pageobjects(driver,_scenario);

        //creating test data object
        testData = new readTestData();

        //create member object
        card = new CardValidation_pageobjects(driver,_scenario);

        screenshot = new TakeScreenshot(driver,_scenario);


    }

    @Given("^I have a valid member with associated transaction and with a supplementary card$")
    public void iHaveAValidMemberWithAssociatedTransactionAndWithASupplementaryCard() throws Throwable {
        try {
            cardNo = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter("Deactivate_Supplementary_Card_CardNumber"));
            System.out.println("card Number in test data file - " +cardNo);
            _scenario.write("card Number in test data file - " +cardNo);
        } catch (FileNotFoundException e) {
            System.out.println("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);
            System.out.println("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to find the test data file in given path - "+ Property.TESTDATA_FILE_PATH);

        }catch (NullPointerException ne) {
            System.out.println("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Deactivate_Supplementary_Card_CardNumber'");
            System.out.println("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Deactivate_Supplementary_Card_CardNumber'");
            Assert.fail("TEST FAILED INTENTIONALLY. Card Number not found under the tag - 'Deactivate_Supplementary_Card_CardNumber'");
        }
    }

    @Then("^I login to SF to deactivate the supplementary card$")
    public void iLoginToSFToDeactivateTheSupplementaryCard() throws Throwable {
        loginPage.loginToSF();
    }

    @Then("^I search member using card number to deactivate the supplementary card$")
    public void iSearchMemberUsingCardNumberToDeactivateTheSupplementaryCard(DataTable dataTable) throws Throwable {
        Thread.sleep(5000);
        searchMember.searchMemberThroughCardNumber(dataTable,cardNo);
    }

    @Then("^I navigate to memership card tab$")
    public void iNavigateToMemershipCardTab() throws Throwable {
        List<String> memberCardStatusList = null;
        try {
            memberCardStatusList = card.getMembershipCardTableStatus();
        } catch (Exception e) {
            System.out.println("Unable to read card status from member : "+cardNo);
            _scenario.write("Unable to read card status from member : "+cardNo);
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read statuses form the cards under member : "+cardNo);
        }

        for (String cardStatus : memberCardStatusList){
            try {
                Assert.assertEquals("Active".toLowerCase(), cardStatus.toLowerCase());
            } catch (AssertionError e) {
                screenshot.takeScreenshot();
                System.out.println("TEST FAILED INTENTIONALLY !. The card is not active");
                _scenario.write("TEST FAILED INTENTIONALLY !. The card is not active");
                Assert.fail("TEST FAILED INTENTIONALLY !. The card is not active");
            }
        }
    }

    @Given("^I validate if supplementary card is present$")
    public void iValidateIfSupplementaryCardIsPresent() throws Throwable {
        try {
            Assert.assertEquals("Supplementary".toLowerCase(), card.getCardTypeOfSupplementaryCard().toLowerCase());
            System.out.println("Found a Active supplementary card");
            _scenario.write("Found a Active supplementary card");
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("Supplementary card not found");
            _scenario.write("Supplementary card not found");
            Assert.fail("TEST FAILED INTENTIONALLY !. There is no record of supplementary card");
        }
    }

    @Then("^I navigate to cancel card section$")
    public void iNavigateToCancelCardSection() throws Throwable {
        //click on the cancel link
        card.clickOnCancelLink();
    }

    @Then("^I deactivate the \"([^\"]*)\" with reason \"([^\"]*)\" and remark \"([^\"]*)\"$")
    public void iDeactivateTheWithReasonAndRemark(String cardType, String reason, String remark) throws Throwable {
        //check if the popup is loaded
        if(card.isCancelCardPopupPresent()){
            screenshot.takeScreenshot();
            System.out.println("'Cancel Card' Popup loaded successfully");
            _scenario.write("'Cancel Card' Popup loaded successfully");
        } else {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. 'Cancel Card' Popup is NOT loaded");
            _scenario.write("TEST FAILED INTENTIONALLY. 'Cancel Card' Popup is NOT loaded");
            Assert.fail("TEST FAILED INTENTIONALLY. 'Cancel Card' Popup is NOT loaded");
        }

        //select card type
        try {
            card.selectCardType(cardType);
            System.out.println("Selected "+cardType+" option from the dropdown");
            _scenario.write("Selected "+cardType+" option from the dropdown");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to select 'card' due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to select 'card' due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to select 'card' due to an exception - "+e.getMessage());
        }

        //select reason
        try {
            card.selectReason(reason);
            System.out.println("Selected "+reason+" option from the dropdown");
            _scenario.write("Selected "+reason+" option from the dropdown");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to select 'Reason' due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to select 'Reason' due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to select 'Reason' due to an exception - "+e.getMessage());
        }


        // input remarks
        try {
            card.setRemark(remark);
            System.out.println("Set "+remark+" as the remarks text");
            _scenario.write("Set "+remark+" as the remarks text");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to set text in 'Remarks' text area due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to set text in 'Remarks' text area due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to set text in 'Remarks' text area due to an exception - "+e.getMessage());
        }

        //click next button
        try {
            card.clickOnNextButtonCancelCardPopup();
            System.out.println("Clicked on 'Next' button");
            _scenario.write("Clicked on 'Next' button");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to click 'Next' button due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to click 'Next' button due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to click 'Next' button due to an exception - "+e.getMessage());
        }

        //click finish button
        try {
            card.clickOnFinishButtonCancelCardPopup();
            System.out.println("Clicked on 'Finish' button");
            _scenario.write("Clicked on 'Finish' button");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to click 'Finish' button due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to click 'Finish' button due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to click 'Finish' button due to an exception - "+e.getMessage());
        }

        //click on confirm button
        try {
            card.clickOnConfirmButton();
            System.out.println("Clicked on 'Confirm' button");
            _scenario.write("Clicked on 'Confirm' button");
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to click 'Confirm' button due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to click 'Confirm' button due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to click 'Confirm' button due to an exception - "+e.getMessage());
        }

    }


    @Then("^I validate the status of Supplementary card to be \"([^\"]*)\" in SF$")
    public void iValidateTheStatusOfSupplementaryCardToBeInSF(String status) throws Throwable {
        // read the new status of the supplementary card and velidate if its set to Inactive
        try {
            String supCardStatus=card.getStatusOfSupplementaryCard();
            System.out.println("Supplementary card status - "+supCardStatus);
            screenshot.takeScreenshot();
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to read Supplementary card status due to an exception -"+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY !. Unable to read Supplementary card status due to an exception -"+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read Supplementary card status due to an exception -"+e.getMessage());
        }

        //Assertion
        try {
            Thread.sleep(5000);
            Assert.assertEquals(card.getStatusOfSupplementaryCard().toLowerCase(),status.toLowerCase());
        } catch (AssertionError e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED !. Supplementary card status is not updated to "+status+". "+e.getMessage());
            _scenario.write("TEST FAILED !. Supplementary card status is not updated to "+status+". "+e.getMessage());
            Assert.fail("TEST FAILED !. Supplementary card status is not updated to "+status+". "+e.getMessage());
        }
    }

    @Then("^I validate status in Matrix is \"([^\"]*)\"$")
    public void iValidateStatusInMatrix(String status) throws Throwable {
        //get the supplementary card number from SF
        String supplementaryCardNo=null;
        try {
            supplementaryCardNo = card.getSupplementaryCardNumber();
            System.out.println("Supplementary card number is "+supplementaryCardNo);
            _scenario.write("Supplementary card number is "+supplementaryCardNo);
        } catch (Exception e) {
            screenshot.takeScreenshot();
            System.out.println("TEST FAILED INTENTIONALLY. Unable to read Supplementary card number in SF. "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY. Unable to read Supplementary card number in SF. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY. Unable to read Supplementary card number in SF. "+e.getMessage());
        }

        // assert if sup card no is not null
        try {
            Assert.assertNotNull(supplementaryCardNo);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED. Supplementary card number is Null.");
            _scenario.write("TEST FAILED. Supplementary card number is Null.");
            Assert.fail("TEST FAILED. Supplementary card number is Null.");
        }

        //assert if sup card no is not empty
        try {
            Assert.assertTrue(!supplementaryCardNo.isEmpty());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED. Supplementary card number is Empty.");
            _scenario.write("TEST FAILED. Supplementary card number is Empty.");
            Assert.fail("TEST FAILED. Supplementary card number is Empty.");
        }


        Map<String, String> matrix = null;
        //trying to read MemberstatusCode field first time
        try {
            matrix = card.getMainDBRecords(supplementaryCardNo);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            System.out.println("Unable to read data from Matrix table. "+e.getMessage());
            _scenario.write("Unable to read data from Matrix table. "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read data from Matrix table. "+e.getMessage());
        }
        int retryCount=0;
        while (!matrix.get("MembershipStatusCode").toLowerCase().equalsIgnoreCase("CANCELLED") && retryCount<20){
            System.out.println("'MembershipStatusCode' value is still 'ACTIVE'. Waiting 30 seconds and retrying");
            try {
                matrix = card.getMainDBRecords(supplementaryCardNo);
            } catch (SQLException | ClassNotFoundException | InterruptedException e) {
                System.out.println("Unable to read data from Matrix table. "+e.getMessage());
                _scenario.write("Unable to read data from Matrix table. "+e.getMessage());
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read data from Matrix table. "+e.getMessage());
            }
            retryCount++;
        }

        try {
            Assert.assertEquals(matrix.get("MembershipStatusCode").toLowerCase(),status.toLowerCase());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. 'MembershipStatusCode' value has not updated to 'CANCELLED' in Matrix even after 10 minutes. "+e.getMessage());
            _scenario.write("TEST FAILED !. 'MembershipStatusCode' value has not updated to 'CANCELLED' in Matrix even after 10 minutes. "+e.getMessage());
            Assert.fail("TEST FAILED !. 'MembershipStatusCode' value has not updated to 'CANCELLED' in Matrix even after 10 minutes. "+e.getMessage());
        }

    }

    @Then("^I navigate to membership card tab again$")
    public void iNavigateToMembershipCardTabAgain() throws Throwable {

        //navigating
        List<String> memberCardStatusList = null;
        try {
            memberCardStatusList = card.getMembershipCardTableStatus();
        } catch (Exception e) {
            System.out.println("Unable to read card status from member : "+cardNo);
            _scenario.write("Unable to read card status from member : "+cardNo);
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read statuses form the cards under member : "+cardNo);
        }

        // validate if member card status list is not null
        try {
            Assert.assertNotNull(memberCardStatusList);
        } catch (Exception e) {
            System.out.println("TEST FAILED!. No records of cards found for card number : "+cardNo);
            _scenario.write("TEST FAILED!. No records of cards found for card number : "+cardNo);
            Assert.fail("TEST FAILED!. No records of cards found for card number : "+cardNo);
        }

    }
}
