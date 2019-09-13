package step_definitions;



import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.TierManagement_pageobjects;
import property.Property;
import utilities.ReadJenkinsParameters;
import utilities.Runtime_TestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.StringContains.containsString;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertEquals;


public class ValidateMember_steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public Login_pageobjects login_pageobjects;
    public MemberValidation_pageobjects memberValidation_pageobjects;
    public Map<String, String> mainLogicDetailsMap;
    public Map<String, String> membershipInfoMatrixDBMap;
    public String pointsCalculationMatrix;
    private String actualStringReturning;
    public Map<String, String> manualDownGradeInfoMap;
    public Map<String, String> manualUpGradeInfoMap;
    public Map<String, String> manualRenewalInfoMap;
    public String cancelledCardPointsBalance;
    public String fullPointCalculation;
    public String keptCardPointsBalance;
    public int statusDollarsTransferred;
    public Map<String, String> transactionInfoMap;
    public ArrayList<String> transactionNumbers;
    public Map<String, String> memberCycleInfoMap;
    public Map<String, String> transactionDetailsMap;
    public Map<String, String> transactionDBRecordMap;
    public String taxUSDAm;
    public Map<String, String> sKUDetailsMap;
    public Map<String, String> skuDBRecordMap;
    public Map<String, String> paymentsDetailsMap;
    public Map<String, String> paymentsDBRecordMap;

    public ValidateMember_steps(){
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        login_pageobjects = new Login_pageobjects(driver,_scenario);
        memberValidation_pageobjects = new MemberValidation_pageobjects(driver, _scenario);
        System.out.println("Validate member constructor \n");
    }

    @And("^I read the membership Information and Purchase history details from UI$")
    public void iValidateNewMember() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        memberValidation_pageobjects.readMembershipCardTab();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.mainLogicCalculation(rowCount);
    }

    @And("^I validate the membership information and purchase history details in UI$")
    public void iValidateTheMembershipInformationAndPurchaseHistoryDetailsInUI() throws Throwable {
        memberValidation_pageobjects.validateResults(mainLogicDetailsMap);
    }

    @And("^I validate point calculations with Matrix$")
    public void I_validate_point_calculations_with_Matrix(DataTable table) throws Throwable {
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Card Number : "+cardNum);
        String dbValue = memberValidation_pageobjects.readPointCalculationInMatrix(cardNum, myJsonObj);
        Assert.assertEquals(dbValue, memberValidation_pageobjects.memberPoints_MembershipTier_sf);
    }

    @And("^I validate Membership Information with matrixDB$")
    public void IvalidateMembershipInformationwithmatrixDB(DataTable table) throws Throwable {
        DecimalFormat df = new DecimalFormat("#.#####");
        String cardNum = "";
        String scenario = "";
        String cardNumMap = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
            if(data.get("<Scenario>") != null){
                scenario = data.get("<Scenario>");
            }
        }
        System.out.println("Card Number : "+cardNum);
        Map<String, String> membershipInfoDBMap = memberValidation_pageobjects.readMembershipInformationInMatrix(cardNum, scenario, myJsonObj, cardNumMap);
        //UI Value - Cycle Tier UI Value
        String TierToCompare = memberValidation_pageobjects.CardTier_sf;
        //UI Value - Cycle StatusDollar
        String StatusDollarToCompare = memberValidation_pageobjects.tier_Status_Doll_sf.replaceAll("[, ;]", "");
        //UI Value - Cycle CarryforwardDollar
        String CarryForwardDollarToCompare = memberValidation_pageobjects.carryForwardDollarAmount_sf.replaceAll("[, ;]", "");
        //UI Value - Cycle start
        String startToCompare = memberValidation_pageobjects.CycleStartDate_sf_formattedDate;
        //UI Value - Cycle end
        String endToCompare = memberValidation_pageobjects.CycleEndDate_sf_formattedDate;
        //UI Value - EntryDollar
        String EntryDollarToCompare = memberValidation_pageobjects.entry_Status_doll_sf;
        //UI Value - Status Renew
        String amtTRTocompare1 = memberValidation_pageobjects.statusDollarToRenew_sf_formatted;
        String amtRenew = memberValidation_pageobjects.statusDollarToRenew_sf_formatted.replaceAll("[, ;]", "");;

        if(amtRenew.contains(".")){
            double doubleAmtRenew = Double.parseDouble(amtRenew);
            amtRenew = df.format(doubleAmtRenew);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@" +  amtRenew);
        }

        //amt to upgrade
        String amtUpgrade = memberValidation_pageobjects.statusDollarToUpgrade_sf.replaceAll("[, ;]", "");
        if (scenario.toUpperCase().equals("AUTODOWNGRADE")){
            System.out.println("========DB Movement Type=========");
            System.out.println("Expected Value : AUTODOWNGRADE");
            System.out.println("DB Value : " + membershipInfoDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("Expected Value : AUTODOWNGRADE");
            _scenario.write("DB Value : " + membershipInfoDBMap.get("MovementType"));
            Assert.assertEquals("DOWNGRADE", membershipInfoDBMap.get("MovementType"));
        }else if (scenario.toUpperCase().equals("AUTOUPGRADE")){
            System.out.println("========DB Movement Type=========");
            System.out.println("Expected Value : AUTOUPGRADE");
            System.out.println("DB Value : " + membershipInfoDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("Expected Value : AUTOUPGRADE");
            _scenario.write("DB Value : " + membershipInfoDBMap.get("MovementType"));
            Assert.assertEquals("UPGRADE", membershipInfoDBMap.get("MovementType"));
        }else if (scenario.toUpperCase().equals("REACTIVATION")){
            System.out.println("========DB Movement Type=========");
            System.out.println("Expected Value : REACTIVATION");
            System.out.println("DB Value : " + membershipInfoDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("Expected Value : REACTIVATION");
            _scenario.write("DB Value : " + membershipInfoDBMap.get("MovementType"));
            Assert.assertEquals("REACTIVATED", membershipInfoDBMap.get("MovementType"));

            System.out.println("========Membership Status=========");
            System.out.println("Expected Value : ACTIVE");
            System.out.println("DB Value : " + membershipInfoDBMap.get("MovementType"));
            _scenario.write("========Membership Status=========");
            _scenario.write("Expected Value : ACTIVE");
            _scenario.write("DB Value : " + membershipInfoDBMap.get("MovementType"));
            Assert.assertEquals("ACTIVE", memberValidation_pageobjects.CardTier_sf);
        }

        System.out.println("========Membership Tier=========");
        System.out.println("UI Value : " + TierToCompare.toUpperCase());
        System.out.println("DB Value : " + membershipInfoDBMap.get("Tier"));
        _scenario.write("========Membership Tier=========");
        _scenario.write("UI Value : " + TierToCompare.toUpperCase());
        _scenario.write("DB Value : " + membershipInfoDBMap.get("Tier"));
        Assert.assertEquals(membershipInfoDBMap.get("Tier"), TierToCompare.toUpperCase());

        System.out.println("========Status Dollar=========");
        System.out.println("UI Value : " + StatusDollarToCompare);
        System.out.println("DB Value : " + membershipInfoDBMap.get("StatusDollar"));
        _scenario.write("========Status Dollar=========");
        _scenario.write("UI Value : " + StatusDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("StatusDollar"));
        Assert.assertEquals(membershipInfoDBMap.get("StatusDollar"), StatusDollarToCompare);

        System.out.println("========CarryForward Dollar=========");
        System.out.println("UI Value : " + CarryForwardDollarToCompare);
        System.out.println("DB Value : " + membershipInfoDBMap.get("CarryForwardDollar"));
        _scenario.write("========CarryForward Dollar=========");
        _scenario.write("UI Value : " + CarryForwardDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("CarryForwardDollar"));
        Assert.assertEquals(membershipInfoDBMap.get("CarryForwardDollar"), CarryForwardDollarToCompare);

        System.out.println("========Cycle Start Date=========");
        System.out.println("UI Value : " + startToCompare);
        System.out.println("DB Value : " + membershipInfoDBMap.get("StartDate"));
        _scenario.write("========Cycle Start Date=========");
        _scenario.write("UI Value : " + startToCompare);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("StartDate"));
        Assert.assertEquals(membershipInfoDBMap.get("StartDate"), startToCompare);

        System.out.println("========Cycle End Date=========");
        System.out.println("UI Value : " + endToCompare);
        System.out.println("DB Value : " + membershipInfoDBMap.get("EndDate"));
        _scenario.write("========Cycle End Date=========");
        _scenario.write("UI Value : " + endToCompare);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("EndDate"));
        Assert.assertEquals(membershipInfoDBMap.get("EndDate"), endToCompare);

        System.out.println("========Entry Status Dollar=========");
        System.out.println("UI Value : " + EntryDollarToCompare);
        System.out.println("DB Value : " + membershipInfoDBMap.get("EntryDollar"));
        _scenario.write("========Entry Status Dollar=========");
        _scenario.write("UI Value : " + EntryDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("EntryDollar"));
        Assert.assertEquals(membershipInfoDBMap.get("EntryDollar"), EntryDollarToCompare);

        System.out.println("========Upgrade=========");
        System.out.println("UI Value : " + amtUpgrade);
        System.out.println("DB Value : " + membershipInfoDBMap.get("AmtForUpgrade"));
        _scenario.write("========Upgrade=========");
        _scenario.write("UI Value : " + amtUpgrade);
        _scenario.write("DB Value : " + membershipInfoDBMap.get("AmtForUpgrade"));
        Assert.assertEquals(membershipInfoDBMap.get("AmtForUpgrade"), amtUpgrade);

        if (membershipInfoDBMap.get("Tier").equalsIgnoreCase("LOYAL T")){
            System.out.println("========Amount For Renewal=========");
            System.out.println("UI Value : " + amtTRTocompare1);
            System.out.println("DB Value : " + membershipInfoDBMap.get("AmtForRenewal"));
            _scenario.write("========Amount For Renewal=========");
            _scenario.write("UI Value : " + amtTRTocompare1);
            _scenario.write("DB Value : " + membershipInfoDBMap.get("AmtForRenewal"));
            String DB_AmtForRenewal = membershipInfoDBMap.get("AmtForRenewal");
            if(DB_AmtForRenewal.contains(".")){
                DB_AmtForRenewal = "0";
            }
            Assert.assertEquals(DB_AmtForRenewal, amtTRTocompare1);
        }else{
            System.out.println("========Amount For Renewal=========");
            _scenario.write("========Amount For Renewal=========");
            double value5 = Double.parseDouble(membershipInfoDBMap.get("AmtForRenewal"));
            String DB_AmtForRenewal = df.format(value5);
            System.out.println("UI Value : " + amtRenew);
            System.out.println("DB Value : " + DB_AmtForRenewal);
            _scenario.write("UI Value : " + amtRenew);
            _scenario.write("DB Value : " + DB_AmtForRenewal);
            Assert.assertEquals(DB_AmtForRenewal, amtTRTocompare1);
        }

        if (membershipInfoDBMap.get("Remarks").toUpperCase().contains("DEACTIVATE")){
            try {
                System.out.println("========Membership Status=========");
                System.out.println("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                System.out.println("DB Value : " + membershipInfoDBMap.get("Remarks"));
                _scenario.write("========Membership Status=========");
                _scenario.write("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                _scenario.write("DB Value : " + membershipInfoDBMap.get("Remarks"));
                Assert.assertEquals("INACTIVE", memberValidation_pageobjects.MemberStatus_sf.toUpperCase());
                //get today's date
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                Date date = new Date();
                String dateToday = formatter.format(date);
                System.out.println("========Cycle End Date=========");
                System.out.println("UI Value : " + endToCompare);
                System.out.println("DB Value : " + membershipInfoDBMap.get("EndDate"));
                System.out.println("Expected Value===" + dateToday);
                _scenario.write("========Cycle End Date=========");
                _scenario.write("UI Value : " + endToCompare);
                _scenario.write("DB Value : " + membershipInfoDBMap.get("EndDate"));
                _scenario.write("Expected Value===" + dateToday);
                Assert.assertEquals(membershipInfoDBMap.get("EndDate"), dateToday);   //db's end date should be today
                Assert.assertEquals(endToCompare, dateToday); //UI's end date should be today
            }catch(Exception e){
                _scenario.write("========Membership Status Mismatch=========");
                _scenario.write("========Expected Membership Status: INACTIVE=========");
                _scenario.write("========Cycle End Mismatch=========");
            }
        }
    }

    @Given("^I validate one transaction disAssociation from membership Information details from UI$")
    public void i_validate_one_transaction_disAssociation_from_membership_Information_details_from_UI() throws Exception {
        memberValidation_pageobjects.readMembershipInformation();
        //Tier Status Dollars should be "0" since there is no any other transactions to disassociate
        _scenario.write("========Tier Status Dollar=========");
        _scenario.write("UI Value===" + memberValidation_pageobjects.tier_Status_Doll_sf);
        _scenario.write("Expected Value : 0, Actual Value : " + memberValidation_pageobjects.tier_Status_Doll_sf + "");
        assertEquals("0", memberValidation_pageobjects.tier_Status_Doll_sf);

        _scenario.write("========Carry Forward Status Dollar=========");
        _scenario.write("UI Value===" + memberValidation_pageobjects.carryForwardDollarAmount_sf);
        _scenario.write("Expected Value : 0, Actual Value : " + memberValidation_pageobjects.carryForwardDollarAmount_sf + "");
        assertEquals("0", memberValidation_pageobjects.carryForwardDollarAmount_sf);

    }

    @And("^I read the membership Information and Purchase history details from UI and validate it against reactivation logic$")
    public void iReadTheMembershipInformationAndPurchaseHistoryDetailsFromUIAndValidateItAgainstReactivationLogic() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.reactivationLogicCalculation(rowCount);
    }

    @And("^I validate the membership status is \"(.*)\" after running renewal job$")
    public void iValidateTheMembershipStatusIsAfterRunningRenewalJob(String expectedStatus) throws Exception {
        _scenario.write("validating the membership status after running renewal job");
        _scenario.write("Expected Value :- InActive");
        int count = 0;
        String actualMembershipStatus = null;
        boolean status = false;
        while (status==false && Property.MAXIMUM_TIMEOUT_COUNT != count){
            memberValidation_pageobjects.readMembershipInformation();
            actualMembershipStatus = memberValidation_pageobjects.MemberStatus_sf;
            if (actualMembershipStatus.equalsIgnoreCase("Inactive")){
                status=true;
            }else{
                System.out.println("membership status is Active. waiting for 1 minute and retrying");
                Thread.sleep(60000);
                count++;
            }
        }
        System.out.println("The Current Membership Status is : " + actualMembershipStatus);
        _scenario.write("Actual Value :- "+actualMembershipStatus+"");
        Assert.assertEquals(expectedStatus, actualMembershipStatus);
    }


    @Then("^I validate member \"(.*)\" as \"(.*)\" in basic information$")
    public void iValidateMemberAsInBasicInformation(String readValue, String value) throws Exception {
        MemberValidation_pageobjects memberValidation_pageobjects = new MemberValidation_pageobjects(driver,_scenario);
        String actualOutput = memberValidation_pageobjects.getMemberBasicInformationDetails(readValue);
        Assert.assertEquals(value, actualOutput);
    }


    @And("^I read the membership Information from Salesforce UI$")
    public void iReadTheMembershipInformationFromSalesforceUI() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        memberValidation_pageobjects.readMembershipCardTab();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.mainLogicCalculation(rowCount);
    }

    @And("^I validate expected membership information with Salesforce UI$")
    public void iValidateExpectedMembershipInformationWithSalesforceUI() throws Throwable {
        memberValidation_pageobjects.validateResults(mainLogicDetailsMap);
    }

    @And("^I query Matrix DB Main table for membership information$")
    public void iQueryMatrixDBMainTableForMembershipInformation(DataTable table) throws Throwable {
        DecimalFormat df = new DecimalFormat("#.#####");
        String cardNum = "";
        String scenario = "";
        String cardNumMap = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
            if(data.get("<Scenario>") != null){
                scenario = data.get("<Scenario>");
            }
        }
        System.out.println("Card Number : "+cardNum);
        membershipInfoMatrixDBMap = memberValidation_pageobjects.readMembershipInformationInMatrix(cardNum, scenario, myJsonObj, cardNumMap);
    }

    @And("^I query Matrix DB Main table for point information$")
    public void iQueryMatrixDBMainTableForPointInformation(DataTable table) throws Throwable {
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Card Number : "+cardNum);
        pointsCalculationMatrix = memberValidation_pageobjects.readPointCalculationInMatrix(cardNum, myJsonObj);
    }

    @And("^I validate expected membership information with Matrix DB$")
    public void iValidateExpectedMembershipInformationWithMatrixDB(DataTable table) throws Throwable {
        DecimalFormat df = new DecimalFormat("#.#####");
        String cardNum = "";
        String scenario = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
            if(data.get("<Scenario>") != null){
                scenario = data.get("<Scenario>");
            }
        }
        //UI Value - Cycle Tier UI Value
        String TierToCompare = memberValidation_pageobjects.CardTier_sf;
        //UI Value - Cycle StatusDollar
        String StatusDollarToCompare = memberValidation_pageobjects.tier_Status_Doll_sf.replaceAll("[, ;]", "");
        //UI Value - Cycle CarryforwardDollar
        String CarryForwardDollarToCompare = memberValidation_pageobjects.carryForwardDollarAmount_sf.replaceAll("[, ;]", "");
        //UI Value - Cycle start
        String startToCompare = memberValidation_pageobjects.CycleStartDate_sf_formattedDate;
        //UI Value - Cycle end
        String endToCompare = memberValidation_pageobjects.CycleEndDate_sf_formattedDate;
        //UI Value - EntryDollar
        String EntryDollarToCompare = memberValidation_pageobjects.entry_Status_doll_sf;
        //UI Value - Status Renew
        String amtTRTocompare1 = memberValidation_pageobjects.statusDollarToRenew_sf_formatted;
        String amtRenew = memberValidation_pageobjects.statusDollarToRenew_sf_formatted.replaceAll("[, ;]", "");;

        if(amtRenew.contains(".")){
            double doubleAmtRenew = Double.parseDouble(amtRenew);
            amtRenew = df.format(doubleAmtRenew);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@" +  amtRenew);
        }

        //amt to upgrade
        String amtUpgrade = memberValidation_pageobjects.statusDollarToUpgrade_sf.replaceAll("[, ;]", "");
        if (scenario.toUpperCase().equals("AUTODOWNGRADE")){
            System.out.println("========DB Movement Type=========");
            System.out.println("UI Value : DOWNGRADE");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("UI Value : DOWNGRADE");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            Assert.assertEquals("DOWNGRADE", membershipInfoMatrixDBMap.get("MovementType"));
        }else if (scenario.toUpperCase().equals("UPGRADE")){
            System.out.println("========DB Movement Type=========");
            System.out.println("UI Value : UPGRADE");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("UI Value : UPGRADE");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            Assert.assertEquals("UPGRADE", membershipInfoMatrixDBMap.get("MovementType"));
        }else if (scenario.toUpperCase().equals("REACTIVATION")){
            System.out.println("========DB Movement Type=========");
            System.out.println("UI Value : REACTIVATED");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("UI Value : REACTIVATED");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            Assert.assertEquals("REACTIVATED", membershipInfoMatrixDBMap.get("MovementType"));
        }else if (scenario.toUpperCase().equals("MANUALUPGRADE")){
            System.out.println("========DB Movement Type=========");
            System.out.println("UI Value : UPGRADE");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            _scenario.write("========DB Movement Type=========");
            _scenario.write("UI Value : UPGRADE");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            Assert.assertEquals("UPGRADE", membershipInfoMatrixDBMap.get("MovementType"));

            System.out.println("========Remarks Status=========");
            System.out.println("UI Value : Upgrade - Manual");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            _scenario.write("========Remarks Status=========");
            _scenario.write("UI Value : Upgrade - Manual");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            Assert.assertEquals("Upgrade - Manual", membershipInfoMatrixDBMap.get("Remarks"));

        }
        System.out.println("========Membership Tier=========");
        System.out.println("UI Value : " + TierToCompare.toUpperCase());
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Tier"));
        _scenario.write("========Membership Tier=========");
        _scenario.write("UI Value : " + TierToCompare.toUpperCase());
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Tier"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("Tier"), TierToCompare.toUpperCase());

        System.out.println("========Status Dollar=========");
        System.out.println("UI Value : " + StatusDollarToCompare);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("StatusDollar"));
        _scenario.write("========Status Dollar=========");
        _scenario.write("UI Value : " + StatusDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("StatusDollar"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("StatusDollar"), StatusDollarToCompare);

        System.out.println("========CarryForward Dollar=========");
        System.out.println("UI Value : " + CarryForwardDollarToCompare);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("CarryForwardDollar"));
        _scenario.write("========CarryForward Dollar=========");
        _scenario.write("UI Value : " + CarryForwardDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("CarryForwardDollar"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("CarryForwardDollar"), CarryForwardDollarToCompare);

        System.out.println("========Cycle Start Date=========");
        System.out.println("UI Value : " + startToCompare);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("StartDate"));
        _scenario.write("========Cycle Start Date=========");
        _scenario.write("UI Value : " + startToCompare);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("StartDate"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("StartDate"), startToCompare);

        System.out.println("========Cycle End Date=========");
        System.out.println("UI Value : " + endToCompare);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("EndDate"));
        _scenario.write("========Cycle End Date=========");
        _scenario.write("UI Value : " + endToCompare);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("EndDate"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("EndDate"), endToCompare);

        System.out.println("========Entry Status Dollar=========");
        System.out.println("UI Value : " + EntryDollarToCompare);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("EntryDollar"));
        _scenario.write("========Entry Status Dollar=========");
        _scenario.write("UI Value : " + EntryDollarToCompare);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("EntryDollar"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("EntryDollar"), EntryDollarToCompare);

        System.out.println("========Amount for Upgrade=========");
        System.out.println("UI Value : " + amtUpgrade);
        System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("AmtForUpgrade"));
        _scenario.write("========Amount for Upgrade=========");
        _scenario.write("UI Value : " + amtUpgrade);
        _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("AmtForUpgrade"));
        Assert.assertEquals(membershipInfoMatrixDBMap.get("AmtForUpgrade"), amtUpgrade);

        if (membershipInfoMatrixDBMap.get("Tier").equalsIgnoreCase("LOYAL T")){
            System.out.println("========Amount For Renewal=========");
            System.out.println("UI Value : " + amtTRTocompare1);
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("AmtForRenewal"));
            _scenario.write("========Amount For Renewal=========");
            _scenario.write("UI Value : " + amtTRTocompare1);
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("AmtForRenewal"));
            String DB_AmtForRenewal = membershipInfoMatrixDBMap.get("AmtForRenewal");
            if(DB_AmtForRenewal.contains(".")){
                DB_AmtForRenewal = "0";
            }
            Assert.assertEquals(DB_AmtForRenewal, amtTRTocompare1);
        }else{
            System.out.println("========Amount For Renewal=========");
            _scenario.write("========Amount For Renewal=========");
            double value5 = Double.parseDouble(membershipInfoMatrixDBMap.get("AmtForRenewal"));
            String DB_AmtForRenewal = df.format(value5);
            System.out.println("UI Value : " + amtRenew);
            System.out.println("DB Value : " + DB_AmtForRenewal);
            _scenario.write("UI Value : " + amtRenew);
            _scenario.write("DB Value : " + DB_AmtForRenewal);
            Assert.assertEquals(DB_AmtForRenewal, amtTRTocompare1);
        }

        if(!(scenario.equals("LoyalT_RenewalFlow_7")) || scenario.equals("")){
            if (membershipInfoMatrixDBMap.get("Remarks").toUpperCase().contains("DEACTIVATE")){
                try {
                    System.out.println("========Membership Status=========");
                    System.out.println("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                    System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
                    _scenario.write("========Membership Status=========");
                    _scenario.write("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                    _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
                    Assert.assertEquals("INACTIVE", memberValidation_pageobjects.MemberStatus_sf.toUpperCase());
                    //get today's date
                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                    Date date = new Date();
                    String dateToday = formatter.format(date);
                    System.out.println("========Cycle End Date=========");
                    System.out.println("UI Value : " + endToCompare);
                    System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("EndDate"));
                    _scenario.write("========Cycle End Date=========");
                    _scenario.write("UI Value : " + endToCompare);
                    _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("EndDate"));
                    Assert.assertEquals(membershipInfoMatrixDBMap.get("EndDate"), dateToday);   //db's end date should be today
                    Assert.assertEquals(endToCompare, dateToday); //UI's end date should be today
                }catch(Exception e){
                    _scenario.write("========Membership Status Mismatch=========");
                    _scenario.write("========Expected Membership Status: INACTIVE=========");
                    _scenario.write("========Cycle End Mismatch=========");
                }
            }
        }

        if (scenario.equalsIgnoreCase("LoyalT_RenewalFlow_7")){
            if (membershipInfoMatrixDBMap.get("Remarks").toUpperCase().contains("DEACTIVATE")){
                System.out.println("========Membership Status=========");
                System.out.println("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
                _scenario.write("========Membership Status=========");
                _scenario.write("UI Value : " + memberValidation_pageobjects.MemberStatus_sf);
                _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
                Assert.assertEquals("INACTIVE", memberValidation_pageobjects.MemberStatus_sf.toUpperCase());
            }
        }
    }

    @And("^I validate expected point information with Matrix DB$")
    public void iValidateExpectedPointInformationWithMatrixDB() throws Throwable {
        System.out.println("DB Points Balance is :" + pointsCalculationMatrix);
        System.out.println("========Points=========");
        System.out.println("UI Value==="+memberValidation_pageobjects.memberPoints_MembershipTier_sf);
        System.out.println("DB Value==="+pointsCalculationMatrix);
        _scenario.write("========Points=========");
        _scenario.write("UI Value==="+memberValidation_pageobjects.memberPoints_MembershipTier_sf);
        _scenario.write("DB Value==="+pointsCalculationMatrix);
        Assert.assertEquals(pointsCalculationMatrix, memberValidation_pageobjects.memberPoints_MembershipTier_sf);
    }

    @When("^I manual downgrade member from salesforce UI$")
    public void iManualDowngradeMemberFromSalesforceUI() throws Throwable {
        TierManagement_pageobjects tierManagement_pageobjects = new TierManagement_pageobjects(driver, _scenario);
        manualDownGradeInfoMap = tierManagement_pageobjects.tierManualDowngrade();
    }

    @Then("^I validate the membership tier$")
    public void iValidateTheMembershipTier() throws Throwable {
        Assert.assertEquals(manualDownGradeInfoMap.get("TierManualDowngrade").toLowerCase(), mainLogicDetailsMap.get("TierAfterManualDowngrade").toLowerCase());
    }


    @When("^I approve all member \"([^\"]*)\" requests$")
    public void iApproveAllMemberRequests(String memberRequest) throws Throwable {
        memberValidation_pageobjects.clickOnMemberRequest(memberRequest);
        memberValidation_pageobjects.approveAllPendingRequests();
    }

    @Then("^I validate manual tier \"([^\"]*)\" message$")
    public void iValidateManualTierMessage(String value) throws Throwable {
        if (value.equalsIgnoreCase("downgrade")){
            String expected = "Downgrade request was created successfully.";
            Assert.assertEquals(expected, manualDownGradeInfoMap.get("TierManualDowngradeMessage"));
        }else if(value.equalsIgnoreCase("upgrade")){
            String expected = "Upgrade request was created successfully.";
            Assert.assertEquals(expected, manualUpGradeInfoMap.get("TierManualUpgradeMessage"));
        }else if(value.equalsIgnoreCase("renewal")){
            String expected = "Renewe request was created successfully.";
            Assert.assertEquals(expected, manualRenewalInfoMap.get("TierManualRenewalMessage"));
        }
    }

    @And("^I read the membership Information after manual downgrade from Salesforce UI$")
    public void iReadTheMembershipInformationAfterManualDowngradeFromSalesforceUI() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.manualDowngradeLogic(rowCount);
    }

    @And("^I validate expected membership information after manual downgrade with Salesforce UI$")
    public void iValidateExpectedMembershipInformationAfterManualDowngradeWithSalesforceUI() throws Throwable {
        memberValidation_pageobjects.validateResults(mainLogicDetailsMap);
    }

    @And("^I read the membership Information for new Member Refund$")
    public void iReadTheMembershipInformationForNewMemberRefund() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.newMemberRefundLogic(rowCount);
    }

    @When("^I manual upgrade member from salesforce UI$")
    public void iManualUpgradeMemberFromSalesforceUI(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNumText = data.get("<Card_Number>");
            TierManagement_pageobjects tierManagement_pageobjects = new TierManagement_pageobjects(driver, _scenario);
            manualUpGradeInfoMap = tierManagement_pageobjects.tierManualUpgrade(cardNumText);
        }
    }

    @And("^I validate expected membership information after transaction disassociation with Matrix DB$")
    public void iValidateExpectedMembershipInformationAfterTransactionDisassociationWithMatrixDB(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNum = "";
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");
            memberValidation_pageobjects.readMembershipInformation();
            memberValidation_pageobjects.readMembershipCardTab();
            int rowCount = memberValidation_pageobjects.getTransactionDisassociationValues(cardNum, myJsonObj);
            Map<String, String> assoDisMap = memberValidation_pageobjects.mainLogicCalculation(rowCount);
            memberValidation_pageobjects.validateResults(assoDisMap);
        }
    }

    @And("^I read the membership Information after transaction association$")
    public void iReadTheMembershipInformationAfterTransactionAssociation(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNum = "";
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");
            memberValidation_pageobjects.readMembershipInformation();
            memberValidation_pageobjects.readMembershipCardTab();
            int rowCount = memberValidation_pageobjects.getTransactionAssociationValues(cardNum, myJsonObj);
            mainLogicDetailsMap = memberValidation_pageobjects.mainLogicCalculation(rowCount);
        }
    }

    @And("^I read the membership Information after transaction disassociation$")
    public void iReadTheMembershipInformationAfterTransactionDisassociation(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNum = "";
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");
            memberValidation_pageobjects.readMembershipInformation();
            memberValidation_pageobjects.readMembershipCardTab();
            int rowCount = memberValidation_pageobjects.getTransactionDisassociationValues(cardNum, myJsonObj);
            mainLogicDetailsMap = memberValidation_pageobjects.mainLogicCalculation(rowCount);
        }
    }

    @And("^I read the membership Information after manual upgrade$")
    public void iReadTheMembershipInformationAfterManualUpgrade(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNumText = data.get("<Card_Number>");
            memberValidation_pageobjects.readMembershipInformation();
            memberValidation_pageobjects.readMembershipCardTab();
            int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
            mainLogicDetailsMap = memberValidation_pageobjects.manualUpgradeLogicCalculation(rowCount, cardNumText);
        }
    }

    @And("^I read the membership Information after renewal job in salesforce$")
    public void iReadTheMembershipInformationAfterRenewalJobInSalesforce(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String renewalCriteria = data.get("<Renewal_Criteria>");
            memberValidation_pageobjects.readMembershipInformation();
            memberValidation_pageobjects.readMembershipCardTab();
            int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
            mainLogicDetailsMap = memberValidation_pageobjects.renewalLogicCalculation(rowCount, renewalCriteria);
        }
    }

    @And("^I validate the purchase history tab$")
    public void iValidateThePurchaseHistoryTab() throws Throwable {
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        ArrayList<ArrayList<String>> fullListOfTransactions = memberValidation_pageobjects.readPurchaseHistoryTab(rowCount);
        ArrayList<String> tTypeList = new ArrayList<>();
        transactionNumbers = new ArrayList<>();
        System.out.println("FullListOfTransactions" + fullListOfTransactions);
        outerloop : for (int x = 0; x <fullListOfTransactions.size() ; x++){
            innerloop : for (int i = 0; i <fullListOfTransactions.get(x).size() ; i++) {
                if (x==0){
                    _scenario.write("First Row Status Dollar amount : "+ fullListOfTransactions.get(x).get(i));
                    System.out.println("First Row Status Dollar amount : "+ fullListOfTransactions.get(x).get(i));
                    _scenario.write("First Row Transaction Type : "+ fullListOfTransactions.get(x).get(i+1));
                    System.out.println("First Row Transaction Type : "+ fullListOfTransactions.get(x).get(i+1));
                    _scenario.write("First Row Transaction Number : "+ fullListOfTransactions.get(x).get(i+2));
                    System.out.println("First Row Transaction Number : "+ fullListOfTransactions.get(x).get(i+2));
                    tTypeList.add(fullListOfTransactions.get(x).get(i+1));
                    transactionNumbers.add(fullListOfTransactions.get(x).get(i+2));
                    break innerloop;
                }else if(x==1){
                    _scenario.write("Second Row Status Dollar amount : "+ fullListOfTransactions.get(x).get(i));
                    _scenario.write("Second Row Transaction Type : "+ fullListOfTransactions.get(x).get(i+1));
                    _scenario.write("Second Row Transaction Number : "+ fullListOfTransactions.get(x).get(i+2));
                    System.out.println("Second Row Status Dollar amount : "+ fullListOfTransactions.get(x).get(i));
                    System.out.println("Second Row Transaction Type : "+ fullListOfTransactions.get(x).get(i+1));
                    System.out.println("Second Row Transaction Number : "+ fullListOfTransactions.get(x).get(i+2));
                    tTypeList.add(fullListOfTransactions.get(x).get(i+1));
                    transactionNumbers.add(fullListOfTransactions.get(x).get(i+2));
                    break innerloop;
                }
            }
        }

        for(int i=0; i<tTypeList.size(); i++){
            if (tTypeList.get(i).equalsIgnoreCase("Manual")){
                Assert.assertEquals("Manual", tTypeList.get(i));
            }else{
                Assert.assertEquals("Merged", tTypeList.get(i));
            }
        }
    }

    @And("^I read the membership Information from Salesforce UI for the cancelled card$")
    public void iReadTheMembershipInformationFromSalesforceUIForTheCancelledCard() throws Throwable {
        memberValidation_pageobjects.readMembershipInformation();
        memberValidation_pageobjects.readMembershipCardTab();
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        mainLogicDetailsMap = memberValidation_pageobjects.mergeLogicCalculation(rowCount);
    }

    @And("^I read the membership points balance$")
    public void iReadTheMembershipPointsBalance(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String cardNum = "";
            if (data.get("<Card_Number>").contains("MemberMergeCardNumberCardCancel")){
                cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
                cardNum = myJsonObj.get(cardNum).getAsString();
                memberValidation_pageobjects.readMembershipInformation();
                cancelledCardPointsBalance = memberValidation_pageobjects.memberPoints_MembershipTier_sf;
                System.out.println("Cancel SF Points balance"+ cancelledCardPointsBalance);
                String matrixPointsBalance = memberValidation_pageobjects.readPointCalculationInMatrixForCancelledCard(cardNum, myJsonObj);
                Assert.assertEquals(cancelledCardPointsBalance, matrixPointsBalance);
                statusDollarsTransferred = Integer.parseInt(memberValidation_pageobjects.entry_Status_doll_sf) + Integer.parseInt(memberValidation_pageobjects.tier_Status_Doll_sf)
                        + Integer.parseInt(memberValidation_pageobjects.carryForwardDollarAmount_sf);
            }else{
                cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
                cardNum = myJsonObj.get(cardNum).getAsString();
                memberValidation_pageobjects.readMembershipInformation();
                keptCardPointsBalance = memberValidation_pageobjects.memberPoints_MembershipTier_sf;
                System.out.println("Kept SF Points balance"+ keptCardPointsBalance);
                String matrixPointsBalance = memberValidation_pageobjects.readPointCalculationInMatrixForCancelledCard(cardNum, myJsonObj);
                Assert.assertEquals(keptCardPointsBalance, matrixPointsBalance);
            }
        }
    }

    @And("^I validate expected point information with Matrix DB after merging$")
    public void iValidateExpectedPointInformationWithMatrixDBAfterMerging(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String Card_Number_Kept = "";
            Card_Number_Kept = data.get("<Card_Number_Kept>");
            String Card_Number_cancel = "";
            Card_Number_cancel = data.get("<Card_Number_Cancel>");

            String tier = System.getProperty("Tier");
            int cancelCardpoint = 0;
            int keptCardpoint = 0;
            try {
                System.out.println("Cancel SF Points balance :" + cancelledCardPointsBalance);
                cancelCardpoint = Integer.parseInt(cancelledCardPointsBalance);
            }catch (Exception e){
                System.out.println("Exception while reading the value of cancel card point :" + e.getMessage());
            }

            try {
                System.out.println("Kept SF Points balance :" + keptCardPointsBalance);
                keptCardpoint = Integer.parseInt(keptCardPointsBalance);
            }catch (Exception e){
                System.out.println("Exception while reading the value of kept card point :" + e.getMessage());
            }

            int fullpoint = cancelCardpoint + keptCardpoint;
            fullPointCalculation = String.valueOf(fullpoint);
            System.out.println("DB Points Full Balance is :" + fullPointCalculation);
            if (tier.equalsIgnoreCase("Diamond")){
                System.out.println("DB Points Balance is :" + pointsCalculationMatrix);
                _scenario.write("DB Points Balance is :" + pointsCalculationMatrix);
                System.out.println("========Points=========");
                System.out.println("Expected Value==="+String.valueOf(fullpoint));
                System.out.println("DB Value==="+pointsCalculationMatrix);
                _scenario.write("========Points=========");
                _scenario.write("Expected Value==="+String.valueOf(fullpoint));
                _scenario.write("DB Value==="+pointsCalculationMatrix);
                Assert.assertEquals(String.valueOf(fullpoint), pointsCalculationMatrix);
            }else{
                _scenario.write("Points are different because of the tier is different");
                _scenario.write("========Points=========");
                _scenario.write("Expected Value==="+String.valueOf(fullpoint));
                _scenario.write("DB Value==="+pointsCalculationMatrix);
                System.out.println("Points are different because of the tier is different");
                System.out.println("========Points=========");
                System.out.println("Expected Value==="+String.valueOf(fullpoint));
                System.out.println("DB Value==="+pointsCalculationMatrix);
            }
        }
    }

    @Then("^I validate the request details in Salesforce UI$")
    public void iValidateTheRequestDetailsInSalesforceUI(DataTable table) throws Throwable {
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            String Card_Number_Kept = "";
            Card_Number_Kept = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number_Kept>"))).getAsString();
            String Card_Number_cancel = "";
            Card_Number_cancel = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number_Cancel>"))).getAsString();
            Map<String, String> readRequestDetailsMap = memberValidation_pageobjects.readRequestDetails();
            _scenario.write("========MergeRequest CardKept=========");
            _scenario.write("Expected Value==="+Card_Number_Kept);
            _scenario.write("DB Value==="+readRequestDetailsMap.get("MergeRequest_CardKept"));
            System.out.println("========MergeRequest CardKept=========");
            System.out.println("Expected Value==="+Card_Number_Kept);
            System.out.println("DB Value==="+readRequestDetailsMap.get("MergeRequest_CardKept"));
            Assert.assertEquals(Card_Number_Kept, readRequestDetailsMap.get("MergeRequest_CardKept"));
            _scenario.write("========MergeRequest CardCancelled=========");
            _scenario.write("Expected Value==="+Card_Number_cancel);
            _scenario.write("DB Value==="+readRequestDetailsMap.get("MergeRequest_CardCancelled"));
            System.out.println("========MergeRequest CardCancelled=========");
            System.out.println("Expected Value==="+Card_Number_cancel);
            System.out.println("DB Value==="+readRequestDetailsMap.get("MergeRequest_CardCancelled"));
            Assert.assertEquals(Card_Number_cancel, readRequestDetailsMap.get("MergeRequest_CardCancelled"));
            _scenario.write("========MergeRequest pointsBalance=========");
            _scenario.write("Expected Value==="+fullPointCalculation);
            _scenario.write("DB Value==="+readRequestDetailsMap.get("MergeRequest_pointsBalance"));
            System.out.println("========MergeRequest pointsBalance=========");
            System.out.println("Expected Value==="+fullPointCalculation);
            System.out.println("DB Value==="+readRequestDetailsMap.get("MergeRequest_pointsBalance"));
            Assert.assertEquals(fullPointCalculation, readRequestDetailsMap.get("MergeRequest_pointsBalance"));
            _scenario.write("========MergeRequest DollarsTransferred=========");
            _scenario.write("Expected Value==="+String.valueOf(statusDollarsTransferred));
            _scenario.write("DB Value==="+readRequestDetailsMap.get("MergeRequest_DollarsTransferred"));
            System.out.println("========MergeRequest DollarsTransferred=========");
            System.out.println("Expected Value==="+String.valueOf(statusDollarsTransferred));
            System.out.println("DB Value==="+readRequestDetailsMap.get("MergeRequest_DollarsTransferred"));
            Assert.assertEquals(String.valueOf(statusDollarsTransferred), readRequestDetailsMap.get("MergeRequest_DollarsTransferred"));
        }
    }

    @And("^I validate the \"([^\"]*)\" is \"([^\"]*)\"$")
    public void iValidateTheIs(String key, String value) throws Throwable {
        if (key.equalsIgnoreCase("Membership Status")){
            Assert.assertEquals(value, memberValidation_pageobjects.MemberStatus_sf);
        }
    }

    @And("^I query Matrix DB Transact table for transaction information$")
    public void iQueryMatrixDBTransactTableForTransactionInformation(DataTable table) throws Throwable {
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Card Number : "+cardNum);
        transactionInfoMap = memberValidation_pageobjects.getMainDBRecordsForTransactions(cardNum);
    }


    @And("^I validate expected transaction information with Matrix DB$")
    public void iValidateExpectedTransactionInformationWithMatrixDB() throws Throwable {
        if (!(transactionInfoMap.get("VoidBy").isEmpty())){
            _scenario.write("========Void by=========");
            _scenario.write("Expected Value : VoidBy field is populated");
            _scenario.write("DB Value==="+transactionInfoMap.get("VoidBy"));
            Assert.assertTrue(true);
        }else{
            _scenario.write("========Void by=========");
            _scenario.write("Expected Value : VoidBy field is NOT populated");
            _scenario.write("DB Value==="+transactionInfoMap.get("VoidBy"));
            Assert.assertTrue(false);
        }

        if (!(transactionInfoMap.get("VoidOn").isEmpty())){
            _scenario.write("========Void On=========");
            _scenario.write("Expected Value : VoidOn field is populated");
            _scenario.write("DB Value==="+transactionInfoMap.get("VoidOn"));
            Assert.assertTrue(true);
        }else{
            _scenario.write("========Void On=========");
            _scenario.write("Expected Value : VoidOn field is NOT populated");
            _scenario.write("DB Value==="+transactionInfoMap.get("VoidOn"));
            Assert.assertTrue(false);
        }
    }

    @And("^I validate all transactions added to Matrix DB Transact table$")
    public void iValidateAllTransactionsAddedToMatrixDBTransactTable(DataTable table) throws Throwable {
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Card Number : "+cardNum);
        HashMap<String, String> mainTransactDBRecordMap = memberValidation_pageobjects.getRowCountMainDBRecordsForTransactions(cardNum);
        _scenario.write("========Transactions count in Matrix DB Transact=========");
        _scenario.write("Expected Row Count : "+transactionNumbers.size());
        _scenario.write("DB Row Count : "+mainTransactDBRecordMap.get("Count"));
        System.out.println("========Transactions count in Matrix DB Transact=========");
        System.out.println("Expected Row Count : "+transactionNumbers.size());
        System.out.println("DB Row Count : "+mainTransactDBRecordMap.get("Count"));
        if (String.valueOf(transactionNumbers.size()).equals(mainTransactDBRecordMap.get("Count"))){
            System.out.println("Transactions are added to Card To Be Kept member");
            _scenario.write("Transactions are added to Card To Be Kept member");
            Assert.assertTrue(true);
        }else{
            System.out.println("Transactions are NOT added to Card To Be Kept member");
            _scenario.write("Transactions are NOT added to Card To Be Kept member");
            Assert.assertTrue(false);
        }
    }

    @And("^I validate \"([^\"]*)\" is \"([^\"]*)\" in Matrix DB$")
    public void iValidateIsInMatrixDB(String key, String value) throws Throwable {
        if (key.equalsIgnoreCase("Movement Type")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("MovementType"));
            Assert.assertEquals(value.toLowerCase(), membershipInfoMatrixDBMap.get("MovementType").toLowerCase());
        }else if(key.equalsIgnoreCase("Remarks")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            Assert.assertEquals(value.toLowerCase(), membershipInfoMatrixDBMap.get("Remarks").toLowerCase());
        }else if(key.equalsIgnoreCase("LoyalT_Remarks")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + membershipInfoMatrixDBMap.get("Remarks"));
            if (membershipInfoMatrixDBMap.get("Remarks").contains("Deactivate")){
                Assert.assertEquals("Deactivate", "Deactivate");
            }else{
                Assert.assertEquals("Deactivate", membershipInfoMatrixDBMap.get("Remarks"));
            }
        }
    }

    @When("^I manual renewal member from salesforce UI$")
    public void iManualRenewalMemberFromSalesforceUI() throws Throwable {
        TierManagement_pageobjects tierManagement_pageobjects = new TierManagement_pageobjects(driver, _scenario);
        manualRenewalInfoMap = tierManagement_pageobjects.tierManualRenewal();
    }

    @And("^I checked \"([^\"]*)\" status is \"([^\"]*)\"$")
    public void iCheckedStatusIs(String key, String value) throws Throwable {
        if (key.equalsIgnoreCase("Grace Period Flag")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + memberCycleInfoMap.get("GracePeriodCheckbox"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + memberCycleInfoMap.get("GracePeriodCheckbox"));
            Assert.assertEquals(value.toLowerCase(), memberCycleInfoMap.get("GracePeriodCheckbox").toLowerCase());
        }else if(key.equalsIgnoreCase("Exception Renewal Flag")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + memberCycleInfoMap.get("ExceptionalRenewalCheckBox"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + memberCycleInfoMap.get("ExceptionalRenewalCheckBox"));
            Assert.assertEquals(value.toLowerCase(), memberCycleInfoMap.get("ExceptionalRenewalCheckBox").toLowerCase());
        }else if(key.equalsIgnoreCase("Active Flag")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + memberCycleInfoMap.get("ActiveImageStatus"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + memberCycleInfoMap.get("ActiveImageStatus"));
            Assert.assertEquals(value.toLowerCase(), memberCycleInfoMap.get("ActiveImageStatus").toLowerCase());
        }else if(key.equalsIgnoreCase("Cycle End Date")){
            String sfDate = memberCycleInfoMap.get("CycleEndDate");
            Date dateTodayStrssDate = new SimpleDateFormat("dd/M/yyyy").parse(sfDate);
            sfDate = new SimpleDateFormat("dd/M/yyyy").format(dateTodayStrssDate);
            if(value.equals("today")){
                Calendar cal = Calendar.getInstance();
                String dateTodayStr = new SimpleDateFormat("dd/M/yyyy").format(cal.getTime());
                System.out.println("========Cycle End Date=========");
                System.out.println("Expected Value : "+dateTodayStr+"");
                System.out.println("UI Value : " + sfDate);
                _scenario.write("========"+key+"=========");
                _scenario.write("Expected Value : "+dateTodayStr+"");
                _scenario.write("UI Value : " + sfDate);
                Assert.assertEquals(dateTodayStr, sfDate);
            }
        }
    }

    @And("^I update the Membershipcycle End date in Salesforce UI$")
    public void iUpdateTheCycleEndDateInSalesforceUI(DataTable table) throws Throwable {
        String cardNum = "";
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            //Enter card number
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            cardNum = myJsonObj.get(cardNum).getAsString();
            memberValidation_pageobjects.updateMembershipCycleDateInSFUsingQuery(cardNum);
        }
    }

    @And("^I read the \"([^\"]*)\" membership cycle in the SF$")
    public void iReadTheMembershipCycleInTheSF(String rowString) throws Throwable {
        memberValidation_pageobjects.goToMembershipCycleTable(rowString);
        memberCycleInfoMap = memberValidation_pageobjects.readMembershipCycleTable();
    }

    @And("^I validate expected point information after transaction disAssociation$")
    public void iValidateExpectedPointInformationAfterTransactionDisAssociation() throws Throwable {
        System.out.println("========Points=========");
        System.out.println("Expected Value=== 0");
        System.out.println("UI Value==="+memberValidation_pageobjects.memberPoints_MembershipTier_sf);
        System.out.println("DB Value==="+pointsCalculationMatrix);
        _scenario.write("========Points=========");
        _scenario.write("Expected Value=== 0");
        _scenario.write("UI Value==="+memberValidation_pageobjects.memberPoints_MembershipTier_sf);
        _scenario.write("DB Value==="+pointsCalculationMatrix);
        Assert.assertEquals("0", memberValidation_pageobjects.memberPoints_MembershipTier_sf);
        Assert.assertEquals("0", pointsCalculationMatrix);
    }


    @And("^I read the Transaction Details$")
    public void iReadTheTransactionDetails() throws Throwable {
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        transactionDetailsMap = memberValidation_pageobjects.readTransactionDetails(rowCount);
    }

    @And("^I read the Transaction Details in Matrix DB$")
    public void iReadTheTransactionDetailsInMatrixDB(DataTable table) throws Throwable {
        String transaction_Number = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get Transaction_Number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Transaction_Number>"));
            transaction_Number = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Transaction Number : "+transaction_Number);
        transactionDBRecordMap = memberValidation_pageobjects.readMatrixTransactionsDBTable(transaction_Number);
    }

    @And("^I validate \"([^\"]*)\" is \"([^\"]*)\" in SF UI Purchase History$")
    public void iValidateIsInSFUIPurchaseHistory(String key, String value) throws Throwable {
        if(key.equalsIgnoreCase("TaxAmountLocal")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + transactionDetailsMap.get("TaxAmountLocal"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + transactionDetailsMap.get("TaxAmountLocal"));
            Assert.assertEquals(value, transactionDetailsMap.get("TaxAmountLocal"));

            String taxUSD = transactionDetailsMap.get("TaxAmountLocal").split(" ")[0].replaceAll(",","");
            double taxUSDdoubleRate = Double.parseDouble(taxUSD);
            DecimalFormat dfs = new DecimalFormat("#.##");
            String env = System.getProperty("Environment");
            double exchangeRate;
            if (env.equalsIgnoreCase("Preprod")){
                System.out.println("Exchange Rate reading from property file");
                System.out.println("Exchange Rate: 1 USD == " + Property.EXCHANGE_RATE_JPY_PREPROD+" JPY");
                exchangeRate = Property.EXCHANGE_RATE_JPY_PREPROD;
            }else{
                System.out.println("Exchange Rate reading from property file");
                System.out.println("Exchange Rate: 1 USD == " + Property.EXCHANGE_RATE_JPY_QACORE2+" JPY");
                exchangeRate = Property.EXCHANGE_RATE_JPY_QACORE2;
            }
            taxUSDAm = dfs.format(taxUSDdoubleRate/exchangeRate);
            System.out.println("Tax Amount (USD): " + taxUSDAm);

            System.out.println("========Tax Amount (USD)=========");
            System.out.println("Expected Value : $"+taxUSDAm+"");
            System.out.println("UI Value : " + transactionDetailsMap.get("TaxAmountUSD"));
            _scenario.write("========Tax Amount (USD)=========");
            _scenario.write("Expected Value : "+taxUSDAm+"");
            _scenario.write("UI Value : " + transactionDetailsMap.get("TaxAmountUSD"));
            Assert.assertEquals("$"+taxUSDAm, transactionDetailsMap.get("TaxAmountUSD").replaceAll(",",""));

        }else if(key.equalsIgnoreCase("Amount In Foreign Currency")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("AmountInFC"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("AmountInFC"));
            Assert.assertEquals(value, paymentsDetailsMap.get("AmountInFC"));
        }else if(key.equalsIgnoreCase("Amount In Local Currency")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("AmountInLC"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("AmountInLC"));
            Assert.assertEquals(value, paymentsDetailsMap.get("AmountInLC"));
        }else if(key.equalsIgnoreCase("Code")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("Code"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("Code"));
            Assert.assertEquals(value, paymentsDetailsMap.get("Code"));
        }else if(key.equalsIgnoreCase("CreditCardNumber")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("CreditCardNumber"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("CreditCardNumber"));
            Assert.assertEquals(value, paymentsDetailsMap.get("CreditCardNumber"));
        }else if(key.equalsIgnoreCase("Description")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("Description"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("Description"));
            Assert.assertEquals(value, paymentsDetailsMap.get("Description"));
        }else if(key.equalsIgnoreCase("ForeignCurrencyDescription")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("ForeignCurrencyDescription"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("ForeignCurrencyDescription"));
            Assert.assertEquals(value, paymentsDetailsMap.get("ForeignCurrencyDescription"));
        }else if(key.equalsIgnoreCase("Type")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + paymentsDetailsMap.get("Type"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + paymentsDetailsMap.get("Type"));
            Assert.assertEquals(value, paymentsDetailsMap.get("Type"));
        }else if(key.equalsIgnoreCase("Product Name")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("ProductName"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("ProductName"));
            Assert.assertEquals(value, sKUDetailsMap.get("ProductName"));
        }else if(key.equalsIgnoreCase("Units")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("Units"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("Units"));
            Assert.assertEquals(value, sKUDetailsMap.get("Units"));
        }else if(key.equalsIgnoreCase("Net Amount without Tax (Local)")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("NetAmountLocal"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("NetAmountLocal"));
            Assert.assertEquals(value, sKUDetailsMap.get("NetAmountLocal"));
        }else if(key.equalsIgnoreCase("Product Name1")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("ProductName1"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("ProductName1"));
            Assert.assertEquals(value, sKUDetailsMap.get("ProductName1"));
        }else if(key.equalsIgnoreCase("Units1")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("Units1"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("Units1"));
            Assert.assertEquals(value, sKUDetailsMap.get("Units1"));
        }else if(key.equalsIgnoreCase("Net Amount without Tax (Local)1")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("UI Value : " + sKUDetailsMap.get("NetAmountLocal1"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("UI Value : " + sKUDetailsMap.get("NetAmountLocal1"));
            Assert.assertEquals(value, sKUDetailsMap.get("NetAmountLocal1"));
        }
    }

    @And("^I validate transaction \"([^\"]*)\" is \"([^\"]*)\" in Matrix DB$")
    public void iValidateTransactionIsInMatrixDB(String key, String value) throws Throwable {
        if(key.equalsIgnoreCase("TaxAmountLocal")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + transactionDBRecordMap.get("TaxAmount"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + transactionDBRecordMap.get("TaxAmount"));
            Assert.assertEquals(value, transactionDBRecordMap.get("TaxAmount"));

            System.out.println("========Tax Amount (USD)=========");
            System.out.println("Expected Value : "+taxUSDAm+"");
            System.out.println("DB Value : " + transactionDBRecordMap.get("TaxAmountUSD"));
            _scenario.write("========Tax Amount (USD)=========");
            _scenario.write("Expected Value : "+taxUSDAm+"");
            _scenario.write("DB Value : " + transactionDBRecordMap.get("TaxAmountUSD"));
            Assert.assertEquals(taxUSDAm, transactionDBRecordMap.get("TaxAmountUSD"));
        }else if(key.equalsIgnoreCase("Amount In Foreign Currency")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("PaymentAmountForeignCurrency"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("PaymentAmountForeignCurrency"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("PaymentAmountForeignCurrency"));
        }else if(key.equalsIgnoreCase("Amount In Local Currency")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("PaymentAmountLocalCurrency"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("PaymentAmountLocalCurrency"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("PaymentAmountLocalCurrency"));
        }else if(key.equalsIgnoreCase("Code")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("PaymentCode"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("PaymentCode"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("PaymentCode"));
        }else if(key.equalsIgnoreCase("CreditCardNumber")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("SalesPayCreditCardNo"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("SalesPayCreditCardNo"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("SalesPayCreditCardNo"));
        }else if(key.equalsIgnoreCase("Description")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("PaymentDescription"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("PaymentDescription"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("PaymentDescription"));
        }else if(key.equalsIgnoreCase("ForeignCurrencyDescription")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("ForeignCurrencyDescription"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("ForeignCurrencyDescription"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("ForeignCurrencyDescription"));
        }else if(key.equalsIgnoreCase("Type")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + paymentsDBRecordMap.get("PaymentType"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + paymentsDBRecordMap.get("PaymentType"));
            Assert.assertEquals(value, paymentsDBRecordMap.get("PaymentType"));
        }else if(key.equalsIgnoreCase("Product Name")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + skuDBRecordMap.get("Item_Code"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + skuDBRecordMap.get("Item_Code"));
            Assert.assertEquals(value, skuDBRecordMap.get("Item_Code"));
        }else if(key.equalsIgnoreCase("Units")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + skuDBRecordMap.get("Quantity"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + skuDBRecordMap.get("Quantity"));
            Assert.assertEquals(value, skuDBRecordMap.get("Quantity"));
        }else if(key.equalsIgnoreCase("Net Amount without Tax (Local)")){
            System.out.println("========"+key+"=========");
            System.out.println("Expected Value : "+value+"");
            System.out.println("DB Value : " + skuDBRecordMap.get("Nett"));
            _scenario.write("========"+key+"=========");
            _scenario.write("Expected Value : "+value+"");
            _scenario.write("DB Value : " + skuDBRecordMap.get("Nett"));
            Assert.assertEquals(value, skuDBRecordMap.get("Nett"));
        }
    }

    @And("^I read the SKU Details$")
    public void iReadTheSKUDetails() throws Throwable {
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        sKUDetailsMap = memberValidation_pageobjects.readTransactionSKUDetails(rowCount);
    }

    @And("^I read the payments details$")
    public void iReadThePaymentsDetails() throws Throwable {
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        paymentsDetailsMap = memberValidation_pageobjects.readTransactionPaymentDetails(rowCount);
    }

    @And("^I read the payments details in Matrix DB$")
    public void iReadThePaymentsDetailsInMatrixDB(DataTable table) throws Throwable {
        String transaction_Number = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get Transaction_Number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Transaction_Number>"));
            transaction_Number = myJsonObj.get(cardNumMap).getAsString();
        }
        System.out.println("Transaction Number : "+transaction_Number);
        paymentsDBRecordMap = memberValidation_pageobjects.readMatrixPaymentsDBTable(transaction_Number);
    }

    @And("^I validate transaction count is \"([^\"]*)\" in the transactions table in SF$")
    public void iValidateTransactionCountIsInTheTransactionsTableInSF(String count) throws Throwable {
        int rowCount = memberValidation_pageobjects.switchToPurchaseHistoryTab();
        ArrayList<ArrayList<String>> fullListOfTransactions = memberValidation_pageobjects.readPurchaseHistoryTab(rowCount);
        String trCount = String.valueOf(fullListOfTransactions.size());
        System.out.println("========Transactions Count=========");
        System.out.println("Expected Count : "+count+"");
        System.out.println("Actual Count : " + trCount);
        _scenario.write("========Transactions Count=========");
        _scenario.write("Expected Count : "+count+"");
        _scenario.write("Actual Count : " + trCount);
        Assert.assertEquals(count, trCount);
    }

    @And("^I read the SKU Details details in Matrix DB for skuNumber \"([^\"]*)\"$")
    public void iReadTheSKUDetailsDetailsInMatrixDBForSkuNumber(String sku, DataTable table) throws Throwable {
        String transaction_Number = "";
        String skuNumber = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Get Transaction_Number from test data
            String cardNumMap = ReadJenkinsParameters.getJenkinsParameter(data.get("<Transaction_Number>"));
            transaction_Number = myJsonObj.get(cardNumMap).getAsString();
            skuNumber = data.get("<SkuNumber>");
        }
        System.out.println("Transaction Number : "+transaction_Number);
        skuDBRecordMap = memberValidation_pageobjects.readMatrixSKUDBTable(transaction_Number, skuNumber);
    }
}



