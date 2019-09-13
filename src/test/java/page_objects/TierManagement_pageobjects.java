package page_objects;

import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import commonLibs.implementation.CommonElements;
import commonLibs.implementation.selectBoxControls;
import commonLibs.implementation.textBoxControls;
import utilities.TakeScreenshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TierManagement_pageobjects {

    public WebDriver driver;
    public Scenario _scenario;
    public textBoxControls textBoxObj;
    public CommonElements commonElements;
    public selectBoxControls comboBoxObj;
    public TakeScreenshot screenshot;

    @FindBy(how= How.CSS, using = "fieldset.slds-form-element > .slds-form-element__control.slds-m-bottom_small:nth-of-type(1) > .iconPointer > label.iconPointer")
    public WebElement transactionAssociationLink_link;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Transaction Association')]/ancestor::ul//*[contains(text(),'More')]")
    public WebElement more_link;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul")
    public WebElement tierManagementMAIN_link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Manual Downgrade']")
    public WebElement manualDowngrade_Link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Manual Renewal']")
    public WebElement manualRenewal_Link;

    @FindBy(how= How.XPATH, using = "//label[@class='iconPointer active-action-label' and text()='Manual Upgrade']")
    public WebElement manualUpgrade_Link;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'New Tier')]/ancestor::label/../select/option[2]")
    public WebElement newTier_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'New Tier')]/ancestor::label/../select")
    public WebElement newTierUpgrade_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Current Membership Tier')]/ancestor::dl/dd[3]")
    public WebElement currentMembershipTierManualDown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Reason')]/ancestor::label/../select")
    public WebElement reason_dropdown;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Remarks')]/ancestor::label/../textarea")
    public WebElement remarks_textarea;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Next']")
    public WebElement next_Button;

    @FindBy(how= How.XPATH, using = "//button[@class='slds-button slds-button_brand' and text()='Finish']")
    public WebElement finish_Button;

    @FindBy(how= How.XPATH, using = "//div[@class='slds-align-middle slds-hyphenate']//span[@class='toastMessage forceActionsText']")
    public WebElement validationMessageText;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'Current Membership Tier')]/ancestor::dl/dd[3]")
    public WebElement currentMembershipTierManualDown_FinishScreen;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'New Tier')]/ancestor::dl/dd[1]")
    public WebElement newTier_FinishScreen;

    public TierManagement_pageobjects(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this._scenario=scenario;
        PageFactory.initElements(driver, this);
        textBoxObj = new textBoxControls(_scenario);
        comboBoxObj = new selectBoxControls(driver, _scenario);
        commonElements = new CommonElements(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
    }

    public Map<String, String> manualDowngradeLogic(int rowCount) throws ParseException {

        Map<String, String> manualDowngradeDataMap = new HashMap<>();

        List<String> value = new ArrayList<String>();
        List<String> value1 = new ArrayList<String>();

        for (int i=1; i<rowCount+1; i++){
            String PurchaceH_StatusDolla = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(7) > div:nth-child(1)")).getText();
            value.add(PurchaceH_StatusDolla.replaceAll("[, ;]", ""));
            System.out.println(value);

        }
        for (int i=1; i<rowCount+1; i++){
            String PurchaceH_StatusDolla1 = driver.findElement(By.cssSelector("tr.slds-hint-parent:nth-child(" + i + ") > td:nth-child(8) > div:nth-child(1)")).getText();
            value1.add(PurchaceH_StatusDolla1);
            System.out.println(value1);

        }


        String [] transactionHistory = value.toArray(new String[value.size()]);;
        String [] transactionMemberCycle = value1.toArray(new String[value.size()]);

        String manualDowngradeTier =  "";
        String entryStatusDollar = "";
        String cycleStartDate = "";
        String cycleEndDate = "";
        String firstPurchaseDate = "";
        String enrollmentDate = "";

        String currentTier= "";
        int tierStatusDollar = 0;
        int carryForwardDollar = 0;
        int currentStatusDollar = 0;

        //int totalPoints = 0;
        System.out.println("Array size: " + transactionHistory.length);


        for(int i = 0; i < transactionHistory.length; i++){							//Loop through purchase history transactions
            int currentTransactionAmt = Integer.parseInt(transactionHistory[i]);  //current transaction line amount
            System.out.println("-------------------------------");
            System.out.println("Current Transaction line: " + currentTransactionAmt);
            System.out.println("Current Transaction line date : " + transactionMemberCycle[i]);

            String[] cycleSplitStr = transactionMemberCycle[i].split("\\s+");
            String DateStr1 = cycleSplitStr[0];
            Date cycleDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr1);
            String firstDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate1);

            String DateStr2 = cycleSplitStr[2];
            Date cycleDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(DateStr2);
            String secondDateStr = new SimpleDateFormat("dd MMM yyyy").format(cycleDate2);

            if(i == 0){  														//if first transaction
                currentTier = "LOYAL T";					//Set the first transaction date
                carryForwardDollar += currentTransactionAmt; 					//CF = transaction amount
                entryStatusDollar = "0";											//Initialize entryStatusDollar to 0 first

                firstPurchaseDate = firstDateStr;
                enrollmentDate = firstDateStr;
                cycleStartDate = firstDateStr;
                cycleEndDate = secondDateStr;

                System.out.println("Enrollment Date: " + enrollmentDate);
                System.out.println("First Transaction Date: " + firstPurchaseDate);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            }
            //else if(firstTransactionDate.equals(transactionMemberCycle[i])){		// if the second line of transaction falls on the same date
            //	carryForwardDollar += currentTransactionAmt;					// add the transaction amount to CF$
            else{
                tierStatusDollar += currentTransactionAmt;   					//add to tierStatusDollar
            }


            System.out.println("CF$ : " + carryForwardDollar);
            System.out.println("TS$ : " + tierStatusDollar);
            System.out.println("Current Tier: " + currentTier);
            System.out.println("Entry Status Dollars: " + entryStatusDollar);



            if((tierStatusDollar + carryForwardDollar) >= 5000 && currentTier.equals("LOYAL T")){ //upgrade to Jade

                System.out.println("Upgrading to JADE Tier...");
                currentTier = "JADE";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 5000;
                tierStatusDollar = 0;

                if(i == 0){ 		//if first transaction
                    entryStatusDollar = "5000";
                }else{
                    entryStatusDollar = "0"; 	//if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if((tierStatusDollar + carryForwardDollar) >= 15000 && currentTier.equals("JADE")){ //upgrade to prestige ruby

                System.out.println("Upgrading to PRESTIGE RUBY Tier...");
                currentTier = "PRESTIGE RUBY";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 15000;
                tierStatusDollar = 0;

                if(i == 0){ 		//if first transaction
                    entryStatusDollar = "20000";
                }else{
                    entryStatusDollar = "0"; 	//if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            if((tierStatusDollar + carryForwardDollar) >= 60000 && currentTier.equals("PRESTIGE RUBY")){

                System.out.println("Upgrading to PRESTIGE DIAMOND Tier...");
                currentTier = "PRESTIGE DIAMOND";
                carryForwardDollar = (tierStatusDollar + carryForwardDollar) - 60000;
                tierStatusDollar = 0;

                if(i == 0){ 		//if first transaction
                    entryStatusDollar = "80000";
                }else{
                    entryStatusDollar = "0"; 	//if not first transaction and there is upgrade, reset entryStatusDollar to 0
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);
            }

            //For refunds
            if(currentTransactionAmt < 0 && currentTier.equals("JADE") && (tierStatusDollar) < 0){			//Jade Tier has auto-downgrade
                carryForwardDollar = carryForwardDollar +  tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0; //reset tierStatusDollar to 0
                if(carryForwardDollar < 0){ //CF also -ve
                    //Perform Downgrade for Jade
                    System.out.println("Downgrading to LOYAL T Tier...");
                    currentTier = "LOYAL T";
                    carryForwardDollar = carryForwardDollar + 5000;
                    //downgrade will reset entryStatusDollar to 0
                    entryStatusDollar = "0";
                    //downgrade will reset cycleEndDate and cycleStartDate
                    cycleStartDate = firstDateStr;
                    cycleEndDate = secondDateStr;

                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
                System.out.println("Cycle Start Date: " + cycleStartDate);
                System.out.println("Cycle End Date: " + cycleEndDate);

            }else if(currentTransactionAmt < 0 && (tierStatusDollar) < 0){    		//Other tiers will not be auto-downgraded
                System.out.println("Deducting balance from CarryForwardDollar");
                carryForwardDollar = carryForwardDollar +  tierStatusDollar; //deduct the refund balance from CF
                tierStatusDollar = 0;
                if(carryForwardDollar < 0 ){
                    tierStatusDollar = carryForwardDollar;		//carryForwardDollar will not be -ve
                    carryForwardDollar = 0;
                }
                int tempTotalStatusDollar = 0;
                int tempEntryStatusDollar = 0;
                if( currentTier.equals("LOYAL T") ){
                    tempEntryStatusDollar = 0;
                } else if( currentTier.equals("JADE") ){
                    tempEntryStatusDollar = 5000;
                } else if( currentTier.equals("PRESTIGE RUBY") ){
                    tempEntryStatusDollar = 20000;
                } else if( currentTier.equals("PRESTIGE DIAMOND") ){
                    tempEntryStatusDollar = 80000;
                }

                tempTotalStatusDollar = carryForwardDollar  + tempEntryStatusDollar + tierStatusDollar;
                System.out.println("tempTotalStatusDollar -- " + tempTotalStatusDollar);
                if(tempTotalStatusDollar < 5000){
                    manualDowngradeTier =  "LOYAL T";
                }else if(tempTotalStatusDollar >= 5000 && tempTotalStatusDollar < 20000){
                    manualDowngradeTier =  "JADE";
                }else if(tempTotalStatusDollar >= 20000 && tempTotalStatusDollar < 80000){
                    manualDowngradeTier =  "PRESTIGE RUBY";
                }else if(tempTotalStatusDollar >= 80000){
                    manualDowngradeTier =  "PRESTIGE DIAMOND";
                }

                System.out.println("CF$ : " + carryForwardDollar);
                System.out.println("TS$ : " + tierStatusDollar);
                System.out.println("Current Tier: " + currentTier);
                System.out.println("Entry Status Dollars: " + entryStatusDollar);
            }

        }


        String statusDollarToUpgrade = "";

        //Status Dollar Required to Upgrade

        if(currentTier.equals("LOYAL T")){

            statusDollarToUpgrade = (5000 - (carryForwardDollar + tierStatusDollar)) + "";  // convert to string to compare with SF UI and dB

        }else if(currentTier.equals("JADE")){

            statusDollarToUpgrade = (15000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }else if(currentTier.equals("PRESTIGE RUBY")){

            statusDollarToUpgrade = (60000 - (carryForwardDollar + tierStatusDollar)) + ""; // convert to string to compare with SF UI and dB

        }

        String statusDollarToRenew = "";
        int result = 0;

        switch(currentTier) {
            case "LOYAL T" :
                statusDollarToRenew = "Any Amount/0";
                break;

            case "JADE" :
                result = 2500 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0){
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE RUBY" :
                result = 10000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0){
                    statusDollarToRenew = Integer.toString(result);
                }
                break;

            case "PRESTIGE DIAMOND" :
                result = 40000 - tierStatusDollar;
                if (result < 0) {
                    statusDollarToRenew = "0";
                } else if (result > 0){
                    statusDollarToRenew = Integer.toString(result);
                }

                break;
            default :
                System.out.println("Purchase History Entry Status dollar is mismatch with membership information");
        }

        //Results
        currentStatusDollar = (carryForwardDollar + tierStatusDollar);
        System.out.println("===================RESULTS================");
        System.out.println("Membership Tier: " + currentTier);
        System.out.println("Carry Forward $: " + carryForwardDollar);
        System.out.println("Tier Status $: " + tierStatusDollar);
        System.out.println("Status Dollar Required to Upgrade: " + statusDollarToUpgrade);
        //System.out.println("Total Points Balance: " +  totalPoints);
        System.out.println("Current Status $: " + currentStatusDollar);
        System.out.println("Entry Status Dollars: " + entryStatusDollar);
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("First Transaction Date: " + firstPurchaseDate);
        System.out.println("Status Dollar Required to Renew:"+statusDollarToRenew);
        System.out.println("Tier after manual Downgrade : " + manualDowngradeTier);

        manualDowngradeDataMap.put("MembershipTier", currentTier);
        manualDowngradeDataMap.put("TierAfterManualDowngrade", manualDowngradeTier);

        return manualDowngradeDataMap;

    }

    public HashMap<String, String> tierManualDowngrade() throws Exception {
        HashMap<String, String> downGradeMap = new HashMap<>();
        System.out.println("Downgrading Member");
        _scenario.write("=====Downgrading Member=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        Thread.sleep(4000);
        commonElements.click(more_link);
        Thread.sleep(10000);
        screenshot.takeScreenshot();
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Tier Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        System.out.println("Clicking Manual Downgrade link");
        Thread.sleep(4000);
        commonElements.click(manualDowngrade_Link);
        commonElements.waitForElement(next_Button);
        screenshot.takeScreenshot();
        String currentTier = currentMembershipTierManualDown.getText();
        _scenario.write("Current Membership Tier : " + currentTier);
        String selectedTier = newTier_dropdown.getText();
        _scenario.write("New tier : " + selectedTier);
        comboBoxObj.selectByValue(reason_dropdown,"System issue");
        textBoxObj.setText(remarks_textarea,"Member Manual Downgrade");
        System.out.println("Clicking Next button");
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        screenshot.takeScreenshot();
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        downGradeMap.put("TierManualDowngrade", selectedTier);
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        downGradeMap.put("TierManualDowngradeMessage", commonElements.getText(validationMessageText));
        return downGradeMap;
    }

    public HashMap<String, String> tierManualUpgrade(String cardNumText) throws Exception {
        HashMap<String, String> upGradeMap = new HashMap<>();
        System.out.println("Upgrading Member");
        _scenario.write("=====Upgrading Member=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        Thread.sleep(4000);
        commonElements.click(more_link);
        Thread.sleep(10000);
        screenshot.takeScreenshot();
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Tier Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        System.out.println("Clicking Manual Upgrade link");
        Thread.sleep(4000);
        commonElements.click(manualUpgrade_Link);
        commonElements.waitForElement(next_Button);
        screenshot.takeScreenshot();
        String currentTier = currentMembershipTierManualDown.getText();
        _scenario.write("Current Membership Tier : " + currentTier);

        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
            _scenario.write("Script intentionally failed because no tier is added");
            Assert.fail("Script intentionally failed because no tier is added");
        }

        String tierSelected = null;
        if (!(tier.equalsIgnoreCase("All"))){
            String tierToBeUpgrade = tier.split("TO")[1];
            if (tierToBeUpgrade.equalsIgnoreCase("Jade")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Jade");
                tierSelected = "Jade";
            }else if (tierToBeUpgrade.equalsIgnoreCase("Ruby")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Prestige Ruby");
                tierSelected = "Prestige Ruby";
            }else if (tierToBeUpgrade.equalsIgnoreCase("Diamond")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Prestige Diamond");
                tierSelected = "Prestige Diamond";
            }
            _scenario.write("New Tier Selected: " + tierSelected);
            Thread.sleep(4000);
        }else{
            String tierFromAll = cardNumText.split("_")[1];
            String tierToBeUpgrade = tierFromAll.split("TO")[1];
            if (tierToBeUpgrade.equalsIgnoreCase("Jade")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Jade");
                tierSelected = "Jade";
            }else if (tierToBeUpgrade.equalsIgnoreCase("Ruby")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Prestige Ruby");
                tierSelected = "Prestige Ruby";
            }else if (tierToBeUpgrade.equalsIgnoreCase("Diamond")){
                comboBoxObj.selectByValue(newTierUpgrade_dropdown,"Prestige Diamond");
                tierSelected = "Prestige Diamond";
            }
            _scenario.write("New Tier Selected: " + tierSelected);
            Thread.sleep(4000);
        }
        comboBoxObj.selectByValue(reason_dropdown,"System issue");
        Thread.sleep(4000);
        textBoxObj.setText(remarks_textarea,"Member Manual Upgrade");
        System.out.println("Clicking Next button");
        Thread.sleep(6000);
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        screenshot.takeScreenshot();
        Thread.sleep(6000);
        String currentMembershipTierFinish = currentMembershipTierManualDown_FinishScreen.getText();
        _scenario.write("Current Membership Tier - Finish Screen : " + currentMembershipTierFinish);
        String newMembershipTierFinish = newTier_FinishScreen.getText();
        _scenario.write("New Membership Tier - Finish Screen : " + newMembershipTierFinish);
        Assert.assertEquals(currentTier, currentMembershipTierFinish);
        Assert.assertEquals(tierSelected, newMembershipTierFinish);
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        upGradeMap.put("TierManualUpgradeMessage", commonElements.getText(validationMessageText));
        return upGradeMap;
    }

    public HashMap<String, String> tierManualRenewal() throws Exception {
        HashMap<String, String> renewalGradeMap = new HashMap<>();
        System.out.println("Renewal Member");
        _scenario.write("=====Renewal Member=====");
        commonElements.waitForElement(transactionAssociationLink_link);
        Thread.sleep(4000);
        commonElements.click(more_link);
        Thread.sleep(10000);
        screenshot.takeScreenshot();
        List<WebElement> liElememtsList = driver.findElements(By.xpath("//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li"));
        for (int i=1; i<=liElememtsList.size(); i++){
            String xpathLi = "//*[contains(text(),'Transaction Association')]/ancestor::ul/li[5]/div/div[2]/div/ul/li["+i+"]";
            String elementName = driver.findElement(By.xpath(xpathLi)).getText();
            if (elementName.equalsIgnoreCase("Tier Management")){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpathLi)));
                driver.findElement(By.xpath(xpathLi)).click();
                break;
            }
        }
        screenshot.takeScreenshot();
        System.out.println("Clicking Renewal link");
        Thread.sleep(4000);
        commonElements.click(manualRenewal_Link);
        commonElements.waitForElement(next_Button);
        screenshot.takeScreenshot();
        Thread.sleep(8000);
        comboBoxObj.selectByValue(reason_dropdown,"System issue");
        Thread.sleep(4000);
        textBoxObj.setText(remarks_textarea,"Member Manual Renewal");
        System.out.println("Clicking Next button");
        Thread.sleep(4000);
        commonElements.click(next_Button);
        commonElements.waitForElement(finish_Button);
        Thread.sleep(4000);
        screenshot.takeScreenshot();
        commonElements.click(finish_Button);
        screenshot.takeScreenshot();
        commonElements.waitForElement(validationMessageText);
        screenshot.takeScreenshot();
        renewalGradeMap.put("TierManualRenewalMessage", commonElements.getText(validationMessageText));
        return renewalGradeMap;
    }


}
