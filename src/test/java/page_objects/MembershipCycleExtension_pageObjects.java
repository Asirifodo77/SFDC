package page_objects;

import commonLibs.implementation.*;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import property.Property;
import utilities.TakeScreenshot;
import utilities.createDbConnection;
import utilities.readTestData;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class MembershipCycleExtension_pageObjects {
    public WebDriver driver;
    public Scenario _scenario;
    public selectBoxControls selectBox;
    public textBoxControls textBox;
    public checkBoxControls checkBox;
    public CommonElements comElement;
    public Login_pageobjects login_pageobjects;
    public TakeScreenshot screenshot;
    public SoftAssert softAssert;
    public JavaScriptExecutor javaScriptExecutor;
    public readTestData testData;

    public MembershipCycleExtension_pageObjects(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this._scenario=scenario;
        PageFactory.initElements(driver, this);
        selectBox = new selectBoxControls(driver, _scenario);
        textBox= new textBoxControls(_scenario);
        comElement = new CommonElements(driver,_scenario);
        checkBox = new checkBoxControls(_scenario);
        login_pageobjects = new Login_pageobjects(driver, _scenario);
        screenshot = new TakeScreenshot(driver,_scenario);
        softAssert = new SoftAssert();
        javaScriptExecutor = new JavaScriptExecutor(driver);
        testData = new readTestData();
    }

    //Elements
    @FindBy(xpath = "//span[@class='title' and text()='Tier Management']")
    public WebElement tierManagementTab;

    @FindBy(xpath = "//*[contains(text(),'More')]//ancestor::ul//li[5]//div[@class='uiMenu']")
    public WebElement moreTab;

    @FindBy(xpath = "//label[text()='Manual Membership Cycle Extension']")
    public WebElement membershipCycleExtensionLink;

    @FindBy(xpath ="//a[text()='Tier Management' and @title='Tier Management']")
    public WebElement tierManagementTabUnderMore;

    @FindBy(xpath = "//span[text()='New Cycle End Date (Year & Month)']//ancestor::div[@class='slds-col slds-p-top_xx-small slds-p-left_xx-small slds-size--1-of-3']//select[@class='form-select year-select slds-truncate select uiInput uiInputSelect uiInput--default uiInput--select']")
    public WebElement newCycleEndYearDopdown;

    @FindBy(xpath = "//span[text()='New Cycle End Date (Year & Month)']//ancestor::div[@class='slds-col slds-p-top_xx-small slds-p-left_xx-small slds-size--1-of-3']//select[@class='form-select month-select select uiInput uiInputSelect uiInput--default uiInput--select']")
    public WebElement newCycleEndMonthDropdown;

    @FindBy(xpath = "//span[contains(text(),'Remarks ')]//ancestor::div[@class='slds-col slds-p-top_xx-small slds-p-left_xx-small slds-size--1-of-3']//textarea[@class='form-textArea textarea']")
    public WebElement remarksTextarea;

    @FindBy(xpath ="//span[contains(text(),'Reason')]//ancestor::div[@class='slds-col slds-p-top_xx-small slds-p-left_xx-small slds-size--1-of-3']//select[@class='form-select select']")
    public WebElement reasonDropdown;

    @FindBy(xpath = "//button[@class='slds-button slds-button_brand' and text()='Next']")
    public WebElement nextBtn;

    @FindBy(xpath = "//dt[text()='New cycle end date:']//following-sibling::dd[1]")
    public WebElement newCycleEndDateLabel;

    @FindBy(xpath = "//dt[text()='Reason:']//following-sibling::dd[1]")
    public WebElement reasonLabel;

    @FindBy(xpath = "//dt[text()='Remarks:']//following-sibling::dd[1]")
    public WebElement remarksLabel;

    @FindBy(xpath = "//button[text()='Finish' and @class='slds-button slds-button_brand']")
    public WebElement finishBtn;




    // Common methods
    public boolean isManualCycleExtensionPopupPresent() {
        try {
            if(comElement.isElementVisible(nextBtn)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Manual Cycle Extension Popup is not present");
            return false;
        }
    }

    public void setCycleEndYear(String year) {
        try {
            selectBox.selectByVisibleText(newCycleEndYearDopdown,year);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void setCycleEndMonth(String month) {
        try {
            selectBox.selectByVisibleText(newCycleEndMonthDropdown,month);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void setRemark(String remark) {
        try {
            textBox.setText(remarksTextarea,remark);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void setReason(String reason){
        try {
            selectBox.selectByVisibleText(reasonDropdown,reason);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void clickNext() {
        try {
            Thread.sleep(3000);
            comElement.click(nextBtn);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public String getnewCycleEndDateLabelValue(){
        try {
            return comElement.getText(newCycleEndDateLabel);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }

    public String getReasonsLabelValue(){
        try {
            return comElement.getText(reasonLabel);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }

    public String getRemarkLabelValue(){
        try {
            return comElement.getText(remarksLabel);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }

    public void clickFinish() throws Exception {
        //waiting for element to be clickable
        try {
            Thread.sleep(5000);
            comElement.waitForElementToBeClickable(driver,finishBtn);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find element -"+finishBtn);
            throw e;
        } catch (Exception e) {
            System.out.println("Exception found when waiting for element to be clickable");
            throw e;
        }

        //click on the button
        try {
            comElement.click(finishBtn);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to find element -"+finishBtn);
            throw e;
        }catch (Exception e) {
            System.out.println("Exception found when waiting for element to be clickable");
            throw e;
        }
    }

    public void clickMembershipCycleExtensionLink() {
        //see if default element is present
        try {
            Thread.sleep(10000);

            if(comElement.isElementVisible(tierManagementTab)){  // Tier Management is visible
                //Scrolling down until element is found
                Thread.sleep(5000);
                System.out.println("'Tier Management' tab is visible");


                try {
                    comElement.click(tierManagementTab);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                }

                //Scrolling to the element first
                try {
                    //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", membershipCycleExtensionLink);
                    javaScriptExecutor.scrollDown(0,100);
                    Thread.sleep(5000);
                    System.out.println("===== Scrolling done ======");
                } catch (Exception e) {
                    System.out.println("Exception occurred while scrolling down - "+e.getMessage());
                }

                //checking if element is clickable
                if(comElement.isElementClickable(membershipCycleExtensionLink)) {
                    System.out.println("membershipCycleExtensionLink is clickable");
                } else {
                    screenshot.takeScreenshot();
                    System.out.println("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                    _scenario.write("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                    Assert.fail("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                }


                try {

                    comElement.click(membershipCycleExtensionLink);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                }

            } else if (comElement.isElementVisible(moreTab)){  //More tab is visible
                System.out.println("'More' Tab is visible");
                //click on more tab
                try {
                    comElement.click(moreTab);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on 'More' Tab - "+e.getMessage());
                }


                try {
                    Thread.sleep(3000);
                    comElement.moveMouseAndClick(tierManagementTabUnderMore);
                } catch (Exception e) {
                    System.out.println("TEST FAILED!. Unable to click on tier management under 'More' Tab - "+e.getMessage());
                    _scenario.write("TEST FAILED!. Unable to click on tier management under 'More' Tab - "+e.getMessage());
                    Assert.fail("TEST FAILED!. Unable to click on tier management under 'More' Tab - "+e.getMessage());
                }

                //waiting for 3 seconds
                Thread.sleep(3000);

                //check again if tier management tab is there
                if(comElement.isElementVisible(tierManagementTab)) {

                    try {
                        comElement.click(tierManagementTab);
                    } catch (Exception e) {
                        System.out.println("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                        _scenario.write("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                        Assert.fail("TEST FAILED!. Unable to click on tier management tab - "+e.getMessage());
                    }

                    //Scrolling to the element first
                    try {
                        //javaScriptExecutor.executeJavaScript("arguments[0].scrollIntoView();", membershipCycleExtensionLink);
                        javaScriptExecutor.scrollDown(0,100);
                        Thread.sleep(5000);
                        System.out.println("===== Scrolling done ======");
                    } catch (Exception e) {
                        System.out.println("Exception occurred while scrolling down - "+e.getMessage());
                    }

                    //checking if element is clickable
                    if(comElement.isElementClickable(membershipCycleExtensionLink)) {
                        System.out.println("membershipCycleExtensionLink is clickable");
                    } else {
                        screenshot.takeScreenshot();
                        System.out.println("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                        _scenario.write("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                        Assert.fail("TEST FAILED!. Transaction was Not associated correctly. Therefore Membership Cycle Extension link IS DISABLED for this member.");
                    }

                    try {
                        comElement.click(membershipCycleExtensionLink);
                    } catch (Exception e) {
                        System.out.println("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                        _scenario.write("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                        Assert.fail("TEST FAILED!. Unable to click on membership Cycle Extension link - "+e.getMessage());
                    }
                } else {
                    System.out.println("TEST FAILED!. Unable to Locate  tier management tab ");
                    _scenario.write("TEST FAILED!. Unable to Locate  tier management tab ");
                    Assert.fail("TEST FAILED!. Unable to Locate  tier management tab ");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCycleEndDateFromMatrix(String environment,String memberID) throws SQLException, InterruptedException, ClassNotFoundException {
        String query = "Select EndDate from MatrixTpReward.dfs.DFS_MembershipCycle WITH (NOLOCK) where MemberID in ('"+memberID+"');";
        System.out.println(query);


        createDbConnection db = new createDbConnection();
        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {

            //querying for the first time
            resultSet = db.queryDB(environment, query);
            int retryCount=0;
            while (!resultSet.next() && retryCount< Property.RETRY_COUNT_FOR_GETTING_CYCLE_END_DATE_FROM_MATRIX){
                System.out.println("~~~~~~ Result is still empty. Retrying in another 30 seconds ~~~~~~");
                Thread.sleep(30000);
                resultSet = db.queryDB(environment, query);
                retryCount++;
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Exception occurred during reading result set from database - "+e.getMessage());
            throw e;
        }

        int columnCount = 0;
        try {
            rsmd = resultSet.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("Exception occurred during reading column count from database - "+e.getMessage());
            throw e;
        }

        System.out.println("column count : "+columnCount);

        HashMap<String, String> dataset = new HashMap<>();

        //getting the resultset again
        resultSet = db.queryDB(environment, query);

        //String variable to hold and return the cycle end date
        String cycleEndDate=null;

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {

                    if(resultSet.getString(i)!=null) {
                        cycleEndDate = resultSet.getString(i);
                    } else {
                        cycleEndDate= "";
                    }
                    // printing out the data collected from Matrix
                    System.out.println("Cycle end date - "+cycleEndDate);


                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from staging");
            // _scenario.write("Successfully collected data from staging");

        } else {
            System.out.println("Result set is empty");
            //  _scenario.write("Result set is empty");
        }

        return cycleEndDate;


    }

}
