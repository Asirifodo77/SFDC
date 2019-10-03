package com.millenniumit.ccp.utilities.ui;

import com.millenniumit.ccp.utilities.data.BusinessObjectBase;
import com.millenniumit.ccp.utilities.data.BusinessObjectFactory;
import com.millenniumit.ccp.utilities.logs.Log;
import com.millenniumit.ccp.utilities.support.CCPChronometer;
import com.millenniumit.ccp.utilities.support.TestSupporter;
import com.millenniumit.ccp.utilities.ui.dso.ConfirmationPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.ReferenceDataPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.account.AccountPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.account.add.AddClearingAccountPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.ccp.add.AddCentralCounterpartyPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.contract.add.AddContractPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.currencies.CurrenciesPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.currencies.add.AddTradeCurrencyPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.firm.add.AddFirmPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.firmrole.add.AddCMFRoleDetailsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.firmrole.add.AddTMFRoleDetailsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.instruments.add.AddEquityInstrumentsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.instruments.add.AddFutureInstrumentsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.instruments.add.AddOptionsInstrumentsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.instruments.edit.EditEquityInstrumentsPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.legalentity.add.AddLeiCodePage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.notificationdictionary.NotificationDictionaryPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.tables.Tables;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.tables.add.*;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.timezone.AddTimeZoneLevel2PopupPage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.timezone.AddTimeZonePage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.tradesource.add.AddTradeSourcePage;
import com.millenniumit.ccp.utilities.ui.dso.referencedata.entities.tradingvenue.add.AddPrimaryTradingVenuePage;
import com.millenniumit.ccp.utilities.ui.selenium.customelements.MTable;
import com.millenniumit.ccp.utilities.ui.selenium.support.ui.Camera;
import com.millenniumit.ccp.utilities.ui.selenium.support.ui.UiUtilities;
import org.apache.commons.beanutils.PropertyUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.millenniumit.ccp.utilities.support.CommonProperties.SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS;
import static com.millenniumit.ccp.utilities.ui.selenium.uilocators.IDs.*;
import static com.millenniumit.ccp.utilities.ui.selenium.uilocators.XPaths.REFERENCE_DATA_ENTITY_SAVE_BUTTON_INDEX_LAST_XPATH;


public class PageBase extends UiUtilities{

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'v-csslayout-footer')]/div[2]/div")
    private WebElement systemClockDiv;

    private String message;
    ArrayList<String> menulist = new ArrayList<>();
    public PageBase() {
    }

    public void refreshPage() throws Exception {

            // if(!webDriver.findElement(By.id(HOME_PAGE_USER_INFO_BUTTON_ID)).isEnabled()) {
            webDriver.get(webDriver.getCurrentUrl());
            Camera.takeSnapShot("After_page_refresh", null);waitForElementLocatedByIdCustom(HOME_PAGE_USER_INFO_BUTTON_ID);
            Log.info("Page Refreshed...!", null);

    }

    protected void waitUntilTableRowsDisplayed(int expectedRowCount, MTable table, int tableIndex) throws Exception {
        int count = 0;
        String callerMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
        String buttonXpath = "//div[@id='" + table.getParentDivId() + "']/../preceding-sibling::div//*[contains(@class,'v-button-reset-filter')]/..";
        while (true) {
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
            if(callerMethod.equals("filterTable")) {
                Log.info("Checking for reset button visible...");
                if ((!webDriver.findElements(By.xpath(buttonXpath)).isEmpty()) && (table.getTableRowCount() >= expectedRowCount)) {
                    Log.info("Reset button found");
                    break;
                }
            }
            else {
                Log.info("Checking for table row count against expected count...");
                if (table.getTableRowCount() >= expectedRowCount) {
                    Log.info("Expected row count found...");
                    break;
                }
            }
            count++;
            if (count > 60) {
                String msg = "Rows not found or not visible in table: " + table.getParentDivId() + " expected row count: " + expectedRowCount;
                Log.error(msg, new RuntimeException(msg));
                String imageLocation = Camera.takeSnapShot("RuntimeException", null);
                throw new RuntimeException(msg + ". screenshot taken at: " + imageLocation);
            }
            Log.info("Waiting until row displayed. Cycle " + count + " finished");
        }
    }

    public ArrayList<String> getValuesFromCheckBoxList(WebElement addRemoveColumnsComboBox) throws Exception{

        ArrayList<String> valuesList = new ArrayList<>();
        waitForElement(getParentElement(addRemoveColumnsComboBox));
        clickElement(getParentElement(addRemoveColumnsComboBox));

        waitForElementLocatedByIdCustom("null.btnApply");
        List<WebElement> elementList = webDriver.findElements(By.xpath("//div[@class='popupContent']/div/div[2]/div/span/label"));
        for (WebElement element : elementList) {

            String value = element.getText();
            if (value.equals("")) {
                scrollToElement(element);
                value = element.getText();
            }
            valuesList.add(value);

        }

        return valuesList;


    }

    public WebElement getPlusButtonXpathByFieldID(String id)throws Exception{
        String path="//div[@id='"+id+"']/../div[@id='CreateNewInst']";
        WebElement xpath=webDriver.findElement(By.xpath(path));
        return xpath;
    }


    public String confirmCsd(String SpainId){

        String csdIDXPath = "//*[@id='CSD']/input";
        String csdID ="";

        csdID = webDriver.findElement(By.xpath(csdIDXPath)).getAttribute("value");
        return csdID;
    }


    public void navigateBackOrCloseWindow() throws Exception {

        String navigationLinkXpath = "(//div[contains(@class, 'v-csslayout-BreadcrumbNavigation')])[last()]/div[last()-1]/span/span[2]";
        String closeButtonXpath = "(//*[contains(@id,'PopupWindow')]/div/div/div[2]/div[2])[last()]";
        int count =0;
        while(true){

            if (!webDriver.findElements(By.xpath(navigationLinkXpath)).isEmpty()){
                WebElement element = waitForElementLocatedByXPath(navigationLinkXpath);
                clickElement(webDriver.findElement(By.xpath(navigationLinkXpath)));
                break;
            }

            else if (!webDriver.findElements(By.xpath(closeButtonXpath)).isEmpty()){
                WebElement element = waitForElementLocatedByXPath(closeButtonXpath);
                clickElement(webDriver.findElement(By.xpath(closeButtonXpath)));
                TestSupporter.implicitWait(1000);
                if(!webDriver.findElements(By.id(CONFIRM_DIALOG_WINDOW_YES_BUTTON_ID)).isEmpty()) {
                    WebElement yesButtonElement = waitForElementLocatedByIdCustom(CONFIRM_DIALOG_WINDOW_YES_BUTTON_ID);
                    clickElement(yesButtonElement);
                }
                break;
            }

            if(count > 150){
                throw new RuntimeException("Window closing timeout...!");
            }
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
            count++;
        }

        String callerClass = TestSupporter.getCallerClassCanonicalName();

        if (callerClass.equals(AddClearingAccountPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Clearing Account:General:Account Name");
        else if (callerClass.equals(AccountPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("GuiSimpleDefinitionSelectorPopup.DefinitionDropdown");
        else if (callerClass.equals(AddActivityControlPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Activity Control:General:Table ID");
        else if (callerClass.equals(AddCentralCounterpartyPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP:General:CCP Name");
        else if (callerClass.equals(AddContractPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Contract:General:Contract ID");
        else if (callerClass.equals(AddTradeCurrencyPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:NORMAL:General:Currency ID");
        else if (callerClass.equals(CurrenciesPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Currencies:General:Table ID");
        else if (callerClass.equals(AddFirmPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:FIRMS:General:Firm Name");
        else if (callerClass.equals(AddCMFRoleDetailsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CMF:General:CMF Role Details ID");
        else if (callerClass.equals(AddTMFRoleDetailsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CMF:General:CMF Role Details ID");
        else if (callerClass.equals(AddFutureInstrumentsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Future:General:Symbol");
        else if (callerClass.equals(AddEquityInstrumentsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Equity:General:Symbol");
        else if (callerClass.equals(EditEquityInstrumentsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_EDIT:Equity:General:Symbol");
        else if (callerClass.equals(AddOptionsInstrumentsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Option:General:Symbol");
        else if (callerClass.equals(AddLeiCodePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Legal Entity:General:Legal Entity ID");
        else if (callerClass.equals(AddTimeZonePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:ZONE:Time Zone Properties:Zone ID");
        else if (callerClass.equals(AddTimeZoneLevel2PopupPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:ZONE:Time Zone Properties:Zone ID");
        else if (callerClass.equals(AddTradeSourcePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Trade Source:General:Trade Source ID");
        else if (callerClass.equals(AddPrimaryTradingVenuePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Trading Venue:General:Trading Venue ID");
        else if (callerClass.equals(AddTransactionGroupPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("TRG.trnsGroupName");
        else if (callerClass.equals(AddAccountCategorySelectionPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Acc Cat Selection:General:Table ID");
        else if (callerClass.equals(AddAccountCategorySelectionTableEntryPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Acc Cat Selection:Trading:Account Category");
        else if (callerClass.equals(NotificationDictionaryPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("common.GuiEntityViewTableControl.Notification_DictionarymainLayout.btnFilter");
        else if (callerClass.equals(AddAccountRuleEntryPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Account Rules:Trading:Account Category");
        else if (callerClass.equals(AddAccountRulesPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Account Rules:General:Table ID");
        else if (callerClass.equals(AddAllowedCMFEntryPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Allowed CMF List:General:Rank");
        else if (callerClass.equals(AddAllowedCMFListPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Allowed CMF List:General:Table ID");
        else if (callerClass.equals(AddAllowZeroPricesPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Allow Zero Prices:General:Table ID");
        else if (callerClass.equals(AddAlternativeMemberCodePage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Member Codes:General:Table ID");
        else if (callerClass.equals(AddBICCodesPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:CCP BIC Codes:General:Table ID");
        else if (callerClass.equals(AddTradeSourceBICCodePage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("UI_ACTION_ADD:Trade Source BIC:General:Table ID");
        else if (callerClass.equals(AddCalendarTablePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Calendar:General:Table ID");
        else if (callerClass.equals(AddCCPGrossExposureLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Grss Expo Lmt:General:Table ID");
        else if (callerClass.equals(AddCCPGrossExposureLimitTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Grss Expo Lmt:Trading:Account Category");
        else if (callerClass.equals(AddCCPNetExposureLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Net Expo Limit:General:Table ID");
        else if (callerClass.equals(AddCCPNetExposureLimitTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Net Expo Limit:Trading:Account Category");
        else if (callerClass.equals(AddCCPPriceTolerancePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Price Tole Lmt:General:Table ID");
        else if (callerClass.equals(AddCCPPriceToleranceTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Price Tole Lmt:Trading:Account Category");
        else if (callerClass.equals(AddCCPQuantityLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Quantity Limit:General:Table ID");
        else if (callerClass.equals(AddCCPQuantityLimitTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Quantity Limit:Trading:Account Category");
        else if (callerClass.equals(AddCheckDecimalPointsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Chck Prce Dcml Pnt:General:Rank");
        else if (callerClass.equals(AddCheckPriceDecimalPointsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Chck Prce Dcml pnt:General:Table ID");
        else if (callerClass.equals(AddClearingCycleSelectionPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Chck Prce Dcml pnt:General:Table ID");
        else if (callerClass.equals(AddClearingCycleSelectionTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Clring Cycle Slctn:General:Clearing Cycle");
        else if (callerClass.equals(AddConsiderationLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Cnsdrtn Limit:General:Table ID");
        else if (callerClass.equals(AddConsiderationLimitTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:CCP Cnsdrtn Limit:Trading:Account Category");
        else if (callerClass.equals(AddExerciseAtTheMoneyPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Exercse At The Mny:General:Table ID");
        else if (callerClass.equals(AddFirmAllowedTradeSourceEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Firm Alwd Trde Src:Trading:Trade Source");
        else if (callerClass.equals(AddFirmAllowedTradeSourcesPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Firm Alwd Trde Src:General:Table ID");
        else if (callerClass.equals(AddCCPFractionalQuantitiesAllowedPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Quantities Allowed:General:Table ID");
        else if (callerClass.equals(AddInstrumentAllowedTradeSourcePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Inst Allwd Trd Src:General:Table ID");
        else if (callerClass.equals(AddMDRefPriceTablePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:MD Ref Price Table:General:Table ID");
        else if (callerClass.equals(AddMemberConsiderationLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Mmbr Cnsdrtn Limit:General:Table ID");
        else if (callerClass.equals(AddMemeberCosiderationEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Mmbr Cnsdrtn Limit:Trading:Account Category");
        else if (callerClass.equals(AddMemberGrossExposureLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Mmbr Grss Expo Lmt:General:Table ID");
        else if (callerClass.equals(AddMemberNetExposureLimitPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Mmbr Grss Expo Lmt:General:Table ID");
        else if (callerClass.equals(AddNewMDRefPriceTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:MD Ref Price Table:Position:Price");
        else if (callerClass.equals(AddPermittedTMFsAndActivitiesPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Prmted TMFs n Acts:General:Table ID");
        else if (callerClass.equals(AddPermittedTMFsAndActivitiesEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Prmted TMFs n Acts:General:Permitted Activities");
        else if (callerClass.equals(AddPositionKeyDefinitionTablePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Clr Pos Key Def:General:Table ID");
        else if (callerClass.equals(AddPositionKeySelectionTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Clr Pos Key Select:Position:Transaction Group");
        else if (callerClass.equals(AddPositionKeySelectionTablePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Clr Pos Key Select:General:Table ID");
        else if (callerClass.equals(AddPostingOrGivupPriorityPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:P G Rule Priority:General:Table ID");
        else if (callerClass.equals(AddPriceDecimalPointsPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Price Decimal Pnts:General:Table ID");
        else if (callerClass.equals(AddPriceDecimalPointsEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Price Decimal Pnts:General:Rank");
        else if (callerClass.equals(AddStalePriceCheckWindowPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Stale Prc Chk Win:General:Table ID");
        else if (callerClass.equals(AddSystematicPositionGiveupPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Systmtc Pstng Gvup:General:Table ID");
        else if (callerClass.equals(AddSystematicPositionGiveupEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Systmtc Pstng Gvup:Conditional Data:Rank");
        else if (callerClass.equals(AddTaxRatesPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Tax Rates:General:Table ID");
        else if (callerClass.equals(AddTradeAcceptancePeriodPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Trade Accept Perio:General:Table ID");
        else if (callerClass.equals(AddUnderlyingTablePage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Underlying Table:General:Table ID");
        else if (callerClass.equals(AddUnderlyingTableEntryPage.class.getCanonicalName()))
            waitForElementLocateByIdWithSpaces("UI_ACTION_ADD:Underlying Table:Execution Report:Symbol");
        else if (callerClass.equals(Tables.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("common.GuiEntityViewTableControl.btnFilter");
        else if(callerClass.equals(ReferenceDataPage.class.getCanonicalName()))
            waitForElementLocatedByIdCustom("categorySearchTextField");

    }

    public String getSuccessFailDivMessage() {

        String verificationPopupXPath = "//div[2]/div/div/h1";
        String successFailDivMessageXPath = "(//*[contains(@id,'PopupWindow')]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/div[2]/div/div/div[1]/div)[last()]";
        String message = "";
        int count =0;
        while (true) {
            if (webDriver.findElements(By.xpath(successFailDivMessageXPath)).size() != 0) {
                message = webDriver.findElement(By.xpath(successFailDivMessageXPath)).getText();
                return message;
            } else if (webDriver.findElements(By.xpath(verificationPopupXPath)).size() != 0) {
                message = webDriver.findElement(By.xpath(verificationPopupXPath)).getText();
                return message;
            }

            count++;
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
            if(count > 150){
                throw new RuntimeException("Success/Fail message not shown");
            }

        }
    }

    public String getSuccessFailWizardDivMessage(){
        String successFailDivMessageXPath = "(//*[contains(@role,'treeitem')]/div/div)[last()]";
        String message = "";
        int count =0;
        while (true) {
            if (webDriver.findElements(By.xpath(successFailDivMessageXPath)).size() != 0) {
                message = webDriver.findElement(By.xpath(successFailDivMessageXPath)).getText();
                return message;
            }
            count++;
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
            if(count > 150){
                throw new RuntimeException("Success/Fail message not shown");
            }

        }
    }

    public String generateWizAccID(String accountid){

        String wizardFirmIDPath = "wizardCreationPopup.wizardNameField";
        String FinancialIDXPath = "*//div[@id='validationTree']/div/div[2]/div[2]/div/div/div/span";
        String MarginAccXPath = "*//div[@id='validationTree']/div/div[3]/div[2]/div/div/div/span";
        String SecuritiesAccIDXpath = "*//div[@id='validationTree']/div/div[4]/div[2]/div/div/div/span";
        String SettlementAccXPath = "*//div[@id='validationTree']/div/div[6]/div[2]/div/div/div/span";
        String TMFSettlementAccXPath = "*//div[@id='validationTree']/div/div[5]/div[2]/div/div/div/span";
        String ClearingAccXPath = "*//div[@id='validationTree']/div/div[7]/div[2]/div/div/div/span";
        String TMFClearingAccXPath = "*//div[@id='validationTree']/div/div[6]/div[2]/div/div/div/span";
        String financialID = "";
        String marginID= "";
        String secAccID = "";
        String settlementAccID = "";
        String clearingAccID ="";
        String tmfsettlementAccID ="";
        String tmfclearingAccID ="";
        String wizardFirmID = "";
        String tmfwizardFirmID = "";



        int count =0;
        while (true) {
            if (webDriver.findElements(By.xpath(FinancialIDXPath)).size() != 0 && accountid.equals("FinancialAccID") ) {
                financialID = webDriver.findElement(By.xpath(FinancialIDXPath)).getText();
                return financialID;
            }else if(webDriver.findElements(By.xpath(MarginAccXPath)).size() != 0 && accountid.equals("MarginAccID")) {
                marginID = webDriver.findElement(By.xpath(MarginAccXPath)).getText();
                return marginID;
            }else if(webDriver.findElements(By.xpath(SecuritiesAccIDXpath)).size() != 0 && accountid.equals("SecAccID")) {
                secAccID = webDriver.findElement(By.xpath(SecuritiesAccIDXpath)).getText();
                return secAccID;
            }else if(webDriver.findElements(By.xpath(SettlementAccXPath)).size() != 0 && accountid.equals("SetAccID")) {
                settlementAccID = webDriver.findElement(By.xpath(SettlementAccXPath)).getText();
                return settlementAccID;
            }else if(webDriver.findElements(By.xpath(TMFSettlementAccXPath)).size() != 0 && accountid.equals("TMFSetAccID")) {
                tmfsettlementAccID = webDriver.findElement(By.xpath(TMFSettlementAccXPath)).getText();
                return tmfsettlementAccID;
            }else if(webDriver.findElements(By.xpath(ClearingAccXPath)).size() != 0 && accountid.equals("ClrAccID")) {
                clearingAccID = webDriver.findElement(By.xpath(ClearingAccXPath)).getText();
                return clearingAccID;
            }else if(webDriver.findElements(By.xpath(TMFClearingAccXPath)).size() != 0 && accountid.equals("TMFClrAccID")) {
                tmfclearingAccID = webDriver.findElement(By.xpath(TMFClearingAccXPath)).getText();
                return tmfclearingAccID;
            }else if(webDriver.findElements(By.id(wizardFirmIDPath)).size() != 0 && accountid.equals("WizardFirmID")) {
                wizardFirmID = webDriver.findElement(By.id(wizardFirmIDPath)).getAttribute("value");
                return wizardFirmID;
            }else if(webDriver.findElements(By.id(wizardFirmIDPath)).size() != 0 && accountid.equals("TMFWizardFirmID")) {
                tmfwizardFirmID = webDriver.findElement(By.id(wizardFirmIDPath)).getAttribute("value");
                return tmfwizardFirmID;
            }

            count++;
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
            if(count > 150){
                throw new RuntimeException("Success/Fail message not shown");
            }

        }
    }


    protected boolean isElementMarkedAsMandatory(WebElement mandatoryElement) {
        String text = "";
        try {
            text = mandatoryElement.findElement(By.xpath("preceding-sibling::*")).findElement(By.xpath("//*[@class='v-required-field-indicator']")).getText();
        } catch (NoSuchElementException | ElementNotVisibleException e) {
            Log.error("Exception occurred", e);
        }
        return text.equals("*");
    }

    protected String getElementToolTip(WebElement element) throws Exception {
        getActions().moveToElement(element).build().perform();
        waitForElementLocatedByXPath("//div[@class='v-tooltip']/div/div/div/div/div");
        return webDriver.findElement(By.xpath("//div[@class='v-tooltip']/div/div/div/div/div")).getText();
    }



    public void clickTableContextMenuLink(String menuItem) throws Exception {
        waitForElementLocatedByXPath("(//div[@class='popupContent'])[last()]/div/table");
        String xpath = "(//div[@class='popupContent'])[last()]/div/table/tbody/tr/td[text() = '"+ menuItem + "']";
        WebElement contextMenuItem = waitForElementLocatedByXPath(xpath);
        //getActions().moveToElement(contextMenuItem,5,5).click().build().perform();
        clickElement(contextMenuItem);
    }



    public boolean checkRightClickMenuNotAvailable(String menuItem) throws Exception {
        waitForElementLocatedByXPath("(//div[@class='popupContent'])[last()]/div/table");
        String xpath = "(//div[@class='popupContent'])[last()]/div/table/tbody/tr[?]/td";

        for (int index = 1; index <6; index++) {
            String actualPath = xpath.replace("?", String.valueOf(index));
            String text = webDriver.findElement(By.xpath(actualPath)).getText();
            menulist.add(text);
        }
        if (!menulist.contains(menuItem)) {
            return true;
        }

        else return false;


    }

    public ArrayList<String> getContextMenuStringList() throws Exception {
        ArrayList<String> valueList = new ArrayList<>();
        waitForElementLocatedByXPath("(//div[@class='popupContent'])[last()]/div/table");
        String xpath = "(//div[@class='popupContent'])[last()]/div/table/tbody/tr[?]/td";
        int index = 1;
        while (true) {
            String actualPath = xpath.replace("?", String.valueOf(index));
            if (!webDriver.findElements(By.xpath(actualPath)).isEmpty()) {
                WebElement textElement = webDriver.findElement(By.xpath(actualPath));
                valueList.add(textElement.getText());
            } else {
                break;
            }
            index++;
        }
        return valueList;
    }

    protected boolean isRadioButtonSelected(String RadioButtonLabelText) throws Exception {
        WebElement element = webDriver.findElement(By.xpath("//*[text()='" + RadioButtonLabelText + "']"))
                .findElement(By.xpath("preceding-sibling::*"));
        if (!element.isDisplayed()) {
            scrollToElement(element);
        }
        String attr = element.getAttribute("checked");
        if (attr == null) {
            return false;
        } else {
            return Boolean.valueOf(attr);
        }
    }
    protected void setFocusToElement(WebElement element) {
        getActions().moveToElement(element).build().perform();
    }

    protected void closeTab(WebElement element){

        clickElement(element);
        TestSupporter.implicitWait(1000);
        WebElement closeButton = element.findElement(By.xpath(".//*[contains(@class,'v-slot-tabButtonClose')]"));
        clickElement(closeButton);

        try {
            Thread.sleep(2000); // todo need to find a proper solution to goto link not displayed after closing tab
        } catch (InterruptedException e) {
            Log.error("Exception occurred", e);
        }
    }

    protected boolean isElementDisabled(WebElement element) throws Exception{

        waitForElement(element);
        String attr = element.getAttribute("aria-disabled");
        if (attr != null) {
            return Boolean.valueOf(attr);
        } else {
            return false;
        }

    }

    public boolean isElementDisabled(String fieldID){
        WebElement element = webDriver.findElement(By.id(fieldID));
        return !element.isEnabled();
    }

    public boolean isMenuItemEnabledInContextMenu(String menuItem) throws Exception {
        waitForElementLocatedByXPath("(//div[@class='popupContent'])[last()]/div/table");
        String xpath = "(//div[@class='popupContent'])[last()]/div/table/tbody/tr/td";
        List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
        for(int i = 0; i < elementList.size(); i++) {
            if(menuItem.equals(elementList.get(i).getText())){
                if(elementList.get(i).isEnabled()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMenuItemExistInContextMenu(String menuItem) throws Exception {
        waitForElementLocatedByXPath("(//div[@class='popupContent'])[last()]/div/table");
        String xpath = "(//div[@class='popupContent'])[last()]/div/table/tbody/tr";
        List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
        for(int i = 0; i < elementList.size(); i++) {
            if(menuItem.equals(elementList.get(i).getText()))
                return true;
        }
        return false;
    }

    public Map<String,String> getInputElementValues() throws Exception {

        Map<String,String> valuesMap = new HashMap<>();

        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field: fields){

            field.setAccessible(true);
            Object fieldValue = field.get(this);
            String value = null;

            Log.info("getting value from: " + field.getName());
            if(field.getName().endsWith("TextBox")){
                WebElement element = (WebElement) fieldValue;
                scrollToElement(element);
                value = element.getAttribute("value");
                Log.info("value found: " + value);
                valuesMap.put(field.getName(),value);
            } else if (field.getName().endsWith("DropDown")){
                WebElement element = (WebElement) fieldValue;
                scrollToElement(element);
                value = getVaadinComboBoxElement(element).getSelectedValueFromDropDown();
                Log.info("value found: " + value);
                valuesMap.put(field.getName(),value);
            } else if(field.getName().endsWith("Calendar")){
                WebElement element = (WebElement) fieldValue;
                scrollToElement(element);
                value = getVaadinCalendarElement(element).getCurrentSelectedDate();
                Log.info("value found: " + value);
                valuesMap.put(field.getName(),value);
            }

        }

        return valuesMap;
    }




    public <T extends BusinessObjectBase> T getBusinessObjectWithCurrentValues(Class<T> type) throws Exception{

        Map<String,String> currentValues = getInputElementValues();
        T bso = BusinessObjectFactory.initBusinessObject(type);
        bso.setAllFieldsToNull();

        Field[] fields = type.getDeclaredFields();

        for(String property : currentValues.keySet()){
            boolean isMatched = false;

            for(Field field : fields){

                //Log.debug("getting value for: " + field.getName());
                Class fieldType = PropertyUtils.getPropertyType(bso, field.getName());

                String regex = field.getName()+"(.*)";
                if(property.matches(regex)){

                    if (fieldType.getName().equals("java.lang.String")) {

                        bso.setValueForVariableString(field.getName(),currentValues.get(property));
                        isMatched = true;

                    } else if(fieldType.getName().equals("java.util.Calendar")){

                        if(!currentValues.get(property).isEmpty()){
                            Calendar calendar = CCPChronometer.getCcpTestSystemCalendar();
                            calendar.setTime(CCPChronometer.getDateFromString("dd/MM/yyyy",currentValues.get(property)));
                            bso.setValueForVariableCalendar(field.getName(),calendar);
                            isMatched = true;
                        }
                    }
                }


            }
            if(!isMatched){
//                throw new RuntimeException("No matching entry found for: " + property + " in BSO class");
                Log.error("No matching entry found for: " + property + " in BSO class", null);
            }
        }

        return bso;

    }

    public String getCCPSystemTime() {
        return systemClockDiv.getText();
    }

    private WebElement getWebElementFromRefDataFormsByLabelText(String labelText) throws Exception {
        WebElement webElement = null;
        webElement = webDriver.findElement(By.xpath("(//div[text()='" + labelText + "'])[last()]"))
                .findElement(By.xpath("..")).findElement(By.xpath("following-sibling::td"));
        return webElement;
    }

    private WebElement getWebElementFromWizardFormsByLabelText(String labelText) throws Exception {
        return getWebElementFromRefDataFormsByLabelText(labelText);
    }

    protected void enterDataInRefDataFormByVisibleLabelText(String labelText, String value) throws Exception {
        WebElement parentElement = getWebElementFromRefDataFormsByLabelText(labelText);
        if(!parentElement.findElements(By.xpath(".//*[contains(@class,'v-filterselect')]")).isEmpty()) {
            getVaadinComboBoxElement(parentElement.findElement(By.xpath("div"))).selectDropDown(value);
        } else if(!parentElement.findElements(By.xpath(".//input")).isEmpty()) {
            typeTextInTextBox(parentElement.findElement(By.xpath(".//input")), value, true);
        }
    }

    protected void enterDataInWizardFormByVisibleLabelText(String labelText, String value) throws Exception {
        WebElement parentElement = getWebElementFromWizardFormsByLabelText(labelText);
        if(!parentElement.findElements(By.xpath(".//*[contains(@class,'v-filterselect')]")).isEmpty()) {
            getVaadinComboBoxElement(parentElement.findElement(By.xpath("div"))).selectDropDown(value);
        } else if(!parentElement.findElements(By.xpath(".//input")).isEmpty()) {
            typeTextInTextBox(parentElement.findElement(By.xpath(".//input")), value, true);
        }
    }

    protected void clickCreateNewInstanceInWizardFormByVisibleLabelText(String labelText) throws Exception {
        WebElement parentElement = getWebElementFromWizardFormsByLabelText(labelText);
        clickElement(parentElement.findElement(By.xpath(".//div[@id='CreateNewInst']")));
    }

    protected void clickNavigateToInstanceInWizardFormByVisibleLabelText(String labelText) throws Exception {
        WebElement parentElement = getWebElementFromWizardFormsByLabelText(labelText);
        clickElement(parentElement.findElement(By.xpath(".//div[@id='NavigateToInst']")));
    }

    public ConfirmationPage clickRefDataSaveButton() throws Exception {

        waitForElementLocatedByXPath(REFERENCE_DATA_ENTITY_SAVE_BUTTON_INDEX_LAST_XPATH);
        TestSupporter.implicitWait(600);
        WebElement saveButton = webDriver.findElement(By.xpath(REFERENCE_DATA_ENTITY_SAVE_BUTTON_INDEX_LAST_XPATH));
        TestSupporter.implicitWait(2000);
        waitForElement(saveButton);
        clickElement(saveButton);
        int count=1;
        while (true) {
            if (!webDriver.findElements(By.xpath("(//*[contains(@id,'PopupWindow')]/div/div/div[3]/div/div/div/div/div/" +
                    "div/div/div/div/div/div/div/div/div[1]/div/div/div[2]/div/div/div[1]/div)[last()]")).isEmpty()) {
                return null;
            } else if (!webDriver.findElements(By.xpath("//input[contains(@class,'commenttextfield')]")).isEmpty()) {
                return UiPageFactory.initPage(webDriver,ConfirmationPage.class);
            }
            count++;
            if(count > 30){
                String imageLocation = Camera.takeSnapShot(null, null);
                this.navigateBackOrCloseWindow();
                throw new RuntimeException("Reference data did not save as expected...!" + " screenshot taken at: " + imageLocation);
            }
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
        }

    }

    public ConfirmationPage clickRefDataSave() throws Exception {

        waitForElementLocatedByIdCustom(REFERENCE_DATA_ENTITY_PAGE_SAVE_BUTTON_ID);

        WebElement saveButton = webDriver.findElement(By.id(REFERENCE_DATA_ENTITY_PAGE_SAVE_BUTTON_ID));
        waitForElement(saveButton);
        clickElement(saveButton);
        int count=1;
        while (true) {
            if (!webDriver.findElements(By.xpath("(//*[contains(@id,'PopupWindow')]/div/div/div[3]/div/div/div/div/div/" +
                    "div/div/div/div/div/div/div/div/div[1]/div/div/div[2]/div/div/div[1]/div)[last()]")).isEmpty()) {
                return null;
            } else if (!webDriver.findElements(By.xpath("//input[contains(@class,'commenttextfield')]")).isEmpty()) {
                return UiPageFactory.initPage(webDriver,ConfirmationPage.class);
            }
            count++;
            if(count > 30){
                String imageLocation = Camera.takeSnapShot(null, null);
                this.navigateBackOrCloseWindow();
                throw new RuntimeException("Reference data did not saved as expected...!" + " screenshot taken at: " + imageLocation);
            }
            TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
        }

    }

    protected void setDropDownValueWithoutEdit(final String dropDown, final String value) throws Exception {
        Field field = this.getClass().getDeclaredField(dropDown+"DropDown");
        field.setAccessible(true);
//        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
        WebElement element = (WebElement) field.get(this);
        scrollToElement(element);
        getVaadinComboBoxElement(element).selectDropDown(value);
    }

    protected boolean isElementMarkedAsLocked(WebElement element) {
        boolean isLocked;
        isLocked = Boolean.valueOf(element.getAttribute("readonly"));
        return isLocked;
    }

    public ConfirmationPage clickRefDataSave1() throws Exception {

        waitForElementLocatedByIdCustom(REFERENCE_DATA_ENTITY_PAGE_SAVE_BUTTON_ID);

        WebElement saveButton = webDriver.findElement(By.id(REFERENCE_DATA_ENTITY_PAGE_SAVE_BUTTON_ID));
        clickElement(saveButton);
        return UiPageFactory.initPage(webDriver,ConfirmationPage.class);
    }

    public String getSuccessFailAddTransactionGroupMessage(){
        String successFailAddTransactionGroupMessageXpath = "(//*[contains(@id,'GuiEntityInstDetailViewComponent.Header.infoLayout')]/div/div[1])[last()]";
        String message = "";
        int count = 0;
        while (true){
            if (webDriver.findElements(By.xpath(successFailAddTransactionGroupMessageXpath)).size()!=0){
                message = webDriver.findElement(By.xpath(successFailAddTransactionGroupMessageXpath)).getText();
                return message;
            }
            else
                return null;
        }
    }

    //Use this method to close all popup windows.
    public void closePopupWindows() throws Exception {

        String closeButtonXpath = "(//*[contains(@id,'PopupWindow')]/div/div/div[2]/div[2])[last()]";
        int count = 0;
        while (!webDriver.findElements(By.xpath(closeButtonXpath)).isEmpty() == true) {

            waitForElementLocatedByXPath(closeButtonXpath);
            clickElement(webDriver.findElement(By.xpath(closeButtonXpath)));
            TestSupporter.implicitWait(1000);
            if (!webDriver.findElements(By.id(CONFIRM_DIALOG_WINDOW_YES_BUTTON_ID)).isEmpty()) {
                WebElement yesButtonElement = waitForElementLocatedByIdCustom(CONFIRM_DIALOG_WINDOW_YES_BUTTON_ID);
                clickElement(yesButtonElement);
            }
        }
    }

    public String getConfirmationWindowMessage(String message) throws Exception {
        WebElement webElement = waitForElementLocatedByIdCustom(CONFIRM_DIALOG_MESSAGE_ID);
        return webElement.getText();
    }

}
