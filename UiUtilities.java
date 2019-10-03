package com.millenniumit.ccp.utilities.ui.selenium.support.ui;
/**
 * This class should not have any hardcoded element locators.
 * This should contain only low level capabilities which can used in a page as well as in a custom element
 */

import com.millenniumit.ccp.utilities.logs.Log;
import com.millenniumit.ccp.utilities.support.TestSupporter;
import com.millenniumit.ccp.utilities.ui.selenium.customelements.CardLayout;
import com.millenniumit.ccp.utilities.ui.selenium.customelements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static com.millenniumit.ccp.utilities.support.CommonProperties.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class UiUtilities {

    private int maxRetryCount = 150;

    protected WebDriver webDriver;

    protected MTable getMTableElement(WebElement parentDiv) {
        return new MTable(webDriver, parentDiv);
    }


    protected VaadinComboBox getVaadinComboBoxElement(WebElement parentDiv) {
        return new VaadinComboBox(webDriver, parentDiv);
    }

    protected VaadinCalendar getVaadinCalendarElement(WebElement parentDiv) {
        return new VaadinCalendar(webDriver, parentDiv);
    }

    protected VaadinCheckBox getVaadinCheckBoxElement(WebElement checkBoxContainer, String checkBoxLabelText) {
        return new VaadinCheckBox(webDriver, checkBoxContainer, checkBoxLabelText);
    }

    protected VaadinCheckBox getVaadinCheckBoxElement(String checkBoxLabelText) {
        return new VaadinCheckBox(webDriver, checkBoxLabelText);
    }

    protected CardLayout getCardLayout(WebElement parentContainerDiv) {
        return new CardLayout(webDriver, parentContainerDiv);
    }

    protected VaadinSpinBox getVaadinSpinBox(WebElement parentContainerDiv) {
        return new VaadinSpinBox(webDriver, parentContainerDiv);
    }

    /*protected ReportDataGrid getReportDataGridElement(WebElement parentDiv) {
        return new ReportDataGrid(webDriver, parentDiv);
    }*/

    protected WebElement waitForElementLocatedByXPath(final String xpath) throws Exception {
        try {
            return waitElementToBeClickable(By.xpath(xpath));
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible for xpath: " + xpath;
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected WebElement waitForElementLocatedByXPathAndTextVisible(final String xpath, String text) throws Exception {
        try {
            if (waitElementForExpectedText(By.xpath(xpath), text)) {
                return waitElementToBeClickable(By.xpath(xpath));
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible for xpath: " + xpath + "and text: " + text;
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected WebElement waitForElement(WebElement element) throws Exception {
        try {
            return waitElementToBeClickable(element);
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible";
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected WebElement waitForElementLocatedByIdCustom(final String id) throws Exception {
        try {
            return waitElementToBeClickable(By.id(id));
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible for id: " + id;
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected WebElement waitForElementLocatedBy(By locator) throws Exception {
        try {
            return waitElementToBeClickable(locator);
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible for : " + locator.toString();
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected WebElement waitForElementLocatedBy(WebElement parentElement, By locator) throws Exception {
        try {
            return waitElementToBeClickable(parentElement, locator);
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible for : " + locator.toString();
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected void waitForElementLocateByIdWithSpaces(String id) throws Exception {

        id = "//*[contains(@id,'" + id + "')]";
        waitForElementLocatedByXPath(id);
    }

    protected boolean waitForElementToDisappearByXpath(String xPath) throws Exception {
        try {
            return waitElementToBeDisappear(By.xpath(xPath));
        } catch (WebDriverException e) {
            String msg = "Element not disappeared for xpath: " + xPath;
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    protected boolean waitForElementToDisappearByID(String id) throws Exception {
        try {
            return waitElementToBeDisappear(By.id(id));
        } catch (WebDriverException e) {
            String msg = "Element not disappeared for id: " + id;
            Log.error(msg, new WebDriverException(msg));
            String imageLocation = Camera.takeSnapShot("WebDriverException", null);
            throw new WebDriverException(msg + ". screenshot taken at: " + imageLocation, e);
        }
    }

    //    @Deprecated
//    protected void waitForElementLocatedByXPath(final String xpath,
//                                                WebDriver webDriver) throws Exception {
//        new WebDriverWait(webDriver, 30)
//                .until(new ExpectedCondition<WebElement>() {
//                    public WebElement apply(WebDriver d) {
//                        return d.findElement(By.xpath(xpath));
//                    }
//                });
//    }
//
//    @Deprecated
//    protected void waitForElementLocatedById(final String id) throws Exception {
//        new WebDriverWait(webDriver, 30)
//                .until(new ExpectedCondition<WebElement>() {
//                    public WebElement apply(WebDriver d) {
//                        return d.findElement(By.id(id));
//                    }
//                });
//    }

    protected void selectItemFromFilteredList(String xpathToItemTable, String valueToBeSelected) throws Exception {
        int index = 1;
        int retryCount  = 0;
        TestSupporter.implicitWait(700);
        while (true) {
            try{
                WebElement item = waitForElementLocatedByXPath(xpathToItemTable.replace("?", String.valueOf(index)));
                if (item.getText().equals(valueToBeSelected)) {
                    clickElement(item);
                    break;
                }
            } catch (Exception e){
                Log.info(valueToBeSelected + "element not in filtered table list",e);
            }
            TestSupporter.implicitWait(500);
            if(retryCount > 60 ){
                throw new RuntimeException(valueToBeSelected + " not found from the filtered list within timeout.");
            }
            retryCount++;
            index++;

        }
    }

    protected void selectItemFromDropDownList(String xpathToItemTable, String valueToBeSelected) throws Exception {
        int index = 1;
        while (true) {
            Log.debug("Dropdown item index: " + index);
            WebElement item = waitForElementLocatedByXPath(xpathToItemTable.replace("?", String.valueOf(index)));
            if (item.getText().equals(valueToBeSelected)) {
                xpathToItemTable = xpathToItemTable.replace("?",String.valueOf(index));
                TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                waitForElementsLocatedByXPath(1, xpathToItemTable);
                clickElementByXPath(xpathToItemTable);
                TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                waitForElementToDisappearByXpath(xpathToItemTable);
                break;
            }
            index++;
        }
    }

    protected void clickElement(WebElement element) {
        int retryCounts = 1;
        while (true) {
            try {
                getActions().moveToElement(element).build().perform();
                getActions().click(element).build().perform();
                //Log.debug("Element clicked...!", null);
                break;
            } catch (WebDriverException e) {
                Log.info("Ignoring exception: " + e.getMessage());
                if (e.getMessage().contains("Element is not clickable at point") || e.getMessage().contains("element is not attached to the page document")) {
                    if (retryCounts >= maxRetryCount) {
                        String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                        throw new WebDriverException("Failed to click element." + " screenshot taken at: " + imageLocation, e);
                    } else {
                        TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                        retryCounts++;
                        continue;
                    }
                } else {
                    String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                    throw new WebDriverException("Failed to click element." + " screenshot taken at: " + imageLocation, e);
                }
            }
        }
    }

    protected void clickElementByXPath(String xpath) {
        int retryCounts = 1;
        while (true) {
            try {
                WebElement element = webDriver.findElement(By.xpath(xpath));
                getActions().moveToElement(element).build().perform();
                TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                getActions().click(element).build().perform();
                //Log.debug("Element clicked...!", null);
                break;
            } catch (WebDriverException e) {
                Log.info("Ignoring exception: " + e.getMessage());
                if (e.getMessage().contains("Element is not clickable at point") || e.getMessage().contains("element is not attached to the page document")) {
                    if (retryCounts >= maxRetryCount) {
                        String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                        throw new WebDriverException("Failed to click element by xpath " + xpath + "." + " screenshot taken at: " + imageLocation, e);
                    } else {
                        TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                        retryCounts++;
                        continue;
                    }
                } else {
                    String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                    throw new WebDriverException("Failed to click element by xpath " + xpath + "." + " screenshot taken at: " + imageLocation, e);
                }
            }
        }
    }

    protected void clickElement(WebElement element, Point clickablePoint) {
        int retryCounts = 1;
        while (true) {
            try {
                getActions().moveToElement(element, clickablePoint.getX(), clickablePoint.getY()).build().perform();
                TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                getActions().moveToElement(element, 0, 0).click().build().perform();
                //Log.debug("Element clicked...!", null);
                break;
            } catch (WebDriverException e) {
                Log.info("Ignoring exception: " + e.getMessage());
                if (e.getMessage().contains("Element is not clickable at point") || e.getMessage().contains("element is not attached to the page document")) {
                    if (retryCounts >= maxRetryCount) {
                        String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                        throw new WebDriverException("Failed to click element." + " screenshot taken at: " + imageLocation, e);
                    } else {
                        TestSupporter.implicitWait(SELENIUM_ELEMENT_WAITING_TIME_OUT_MILISECONDS);
                        retryCounts++;
                        continue;
                    }
                } else {
                    String imageLocation = Camera.takeSnapShot("WebDriverException", null);
                    throw new WebDriverException("Failed to click element." + " screenshot taken at: " + imageLocation, e);
                }
            }
        }
    }

    /**
     * Set value to a text field. If command line argument "local" is present, clip board functionality will be skipped.
     *
     * @param textBox
     * @param val
     * @param clean
     * @throws Exception
     */
    protected void typeTextInTextBox(WebElement textBox, String val, boolean clean) throws Exception {
        String isLocal = System.getProperty("local");
        if (isLocal != null && val != null && val.length() < 200) {
            typeTextInTextBox(textBox, val, clean, 5);
        } else {
            typeTextInTextBoxWithClipBoard(textBox, val, clean);
        }
    }


    private void typeTextInTextBox(WebElement textBox, String val, boolean clean, int count) throws Exception {
        if (val != null) {
            Log.info("TextBox value to be entered: " + val);
            if (val.isEmpty()) {
                Log.info("TextBox value cleared. Value to be set is empty");
                textBox.clear();
                return;
            }

            String currentValue = textBox.getAttribute("value");
            Log.info("TextBox Current Value: " + currentValue);
            if (!currentValue.equals(val)) {
                textBox.clear();
                // Send character by character as Chrome driver misses some letters when string is sent in full
                for (int i = 0; i < val.length(); i++) {
                    char c = val.charAt(i);
                    String s = new StringBuilder().append(c).toString();
                    textBox.sendKeys(s);
                }
                TestSupporter.implicitWait(200);
            }

            // Recheck whether value is correctly set
            if (!textBox.getAttribute("value").equals(val)) {
                if (count > 0) {
                    //Reattempt till maximum number of attempts exceed
                    TestSupporter.implicitWait(1000);
                    typeTextInTextBox(textBox, val, clean, --count);
                } else {
                    // Fallback to clip board if attempting through plain send keys does not work
                    typeTextInTextBoxWithClipBoard(textBox, val, clean);
                }
            }
        }
    }

    private void typeTextInTextBoxWithClipBoard(WebElement textBox, String val, boolean clean) throws Exception {
        if (val != null) {
            StringSelection selection = new StringSelection(val);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            Log.info("TextBox value to be pasted: " + val);

            if (val.isEmpty()) {
                Log.info("TextBox value cleared. Value to be set is empty");
                textBox.clear();
                return;
            }

            String currentValue = textBox.getAttribute("value");
            Log.info("TextBox Current Value: " + currentValue);
            if (!currentValue.equals(val)) {
                if (clean) {
                    Log.info("TextBox cleaned and new value pasted");
                    textBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    textBox.sendKeys(Keys.chord(Keys.CONTROL, "v"));
                } else {
                    Log.info("TextBox value pasted without clearing");
                    textBox.sendKeys(Keys.chord(Keys.CONTROL, "v"));
                }
                TestSupporter.implicitWait(500);
            }
        }
    }

    protected void scrollToElement(WebElement element) {
        try {
            /*if (!element.isDisplayed()) {*/
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            /*}*/

        } catch (Exception e) {
            Log.error("Exception occurred", e);
        }
    }

    protected void scrollToElement(By locator) {
        try {
            if (!webDriver.findElement(locator).isDisplayed()) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                WebElement element = webDriver.findElement(locator);
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            }

        } catch (Exception e) {
            Log.error("Exception occurred", e);
        }
    }

    protected void scrollToElementByAction(WebElement element) {
        if (!element.isDisplayed()) {
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element);
            actions.perform();
        }
    }

    /**
     * Focus web element
     *
     * @param element the WebElement
     */
    protected void setFocusToElement(WebElement element) {
        getActions().moveToElement(element).build().perform();
    }

    protected WebElement getParentElement(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    protected WebElement getPrecedingSiblingElement(WebElement webElement) {
        return webElement.findElement(By.xpath("preceding-sibling::*"));
    }

    protected Actions getActions() {
        return new Actions(webDriver);
    }

    public void deleteAllCookies() {
        webDriver.manage().deleteAllCookies();
    }

    protected boolean isElementPresentByIDvalue(String elementid) throws Exception{
        try{
            webDriver.findElement(By.id(elementid));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    protected boolean isElementPresentByXPATHvalue(String elementxpath) throws Exception{
        try{
            webDriver.findElement(By.xpath(elementxpath));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    private WebElement waitElementToBeClickable(By locator) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }

    private WebElement waitElementToBeClickable(WebElement element) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement elementFound = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
        return elementFound;
    }

    private WebElement waitElementToBeClickable(WebElement parentElement, By locator) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(parentElement
                .findElement(locator)));
        return element;
    }

    private boolean waitElementForExpectedText(By locator, String expectedText) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(ExpectedConditions.textToBe(locator, expectedText));
    }

    private boolean waitElementToBeDisappear(By locator) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait until text of the given attribute contains one from the given value list
     *
     * @param locator
     * @param attribute
     * @param expectedContainingTextList
     * @return
     * @throws WebDriverException
     */
    protected boolean waitElementToAttributeTextContainsEitherOne(By locator, String attribute
            , String... expectedContainingTextList) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(new ExpectedCondition<Boolean>() {

            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                String logMessage = "Waiting element for attribute: '" + attribute + "' contains text: ";
                for (String value : expectedContainingTextList) {
                    logMessage += "'" + value + "'" + " or ";
                    if (webDriver.findElement(locator).getAttribute(attribute).contains(value)) {
                        Log.info("Found element for attribute: '" + attribute + "' contains text: '" + value + "'");
                        return true;
                    }
                }
                Log.info(logMessage.substring(0, logMessage.lastIndexOf("or ")).trim());
                return false;
            }
        });
    }

    /**
     * Wait until one or more text appear in a given attribute of the element
     *
     * @param element
     * @param attribute
     * @param expectedContainingTextList
     * @return
     * @throws WebDriverException
     */
    protected boolean waitElementToAttributeTextContainsEitherOne(WebElement element, String attribute
            , String... expectedContainingTextList) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_EXTENDED_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(500, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(new ExpectedCondition<Boolean>() {

            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                String logMessage = "Waiting element for attribute: '" + attribute + "' contains text: ";
                for (String value : expectedContainingTextList) {
                    logMessage += "'" + value + "'" + " or ";
                    if (element.getAttribute(attribute).contains(value)) {
                        Log.info("Found element for attribute: '" + attribute + "' contains text: '" + value + "'");
                        return true;
                    }
                }
                Log.info(logMessage.substring(0, logMessage.lastIndexOf("or ")).trim());
                return false;
            }
        });
    }

    public boolean waitForAttributeChanged(WebElement element, String attr, String initialValue) {
        WebDriverWait wait = new WebDriverWait(this.webDriver, 5);

        wait.until(new ExpectedCondition<Boolean>() {
            private WebElement element;
            private String attr;
            private String initialValue;

            private ExpectedCondition<Boolean> init(WebElement element, String attr, String initialValue ) {
                this.element = element;
                this.attr = attr;
                this.initialValue = initialValue;
                return this;
            }

            public Boolean apply(WebDriver webDriver) {
                //WebElement button = webDriver.findElement(element);
                String enabled = element.getAttribute(this.attr);
                if(enabled.equals(this.initialValue))
                    return false;
                else
                    return true;
            }
        }.init(element, attr, initialValue));
        return false;
    }

    protected boolean waitElementToAttributeTextNotContainsEitherOne(By locator, String attribute
            , String... expectedContainingTextList) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(new ExpectedCondition<Boolean>() {

            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                String logMessage = "Waiting element for attribute: '" + attribute + "' contains text: ";
                for (String value : expectedContainingTextList) {
                    logMessage += "'" + value + "'" + " or ";
                    if (!webDriver.findElement(locator).getAttribute(attribute).contains(value)) {
                        Log.info("Found element for attribute: '" + attribute + "' contains text: '" + value + "'");
                        return true;
                    }
                }
                Log.info(logMessage.substring(0, logMessage.lastIndexOf("or ")).trim());
                return false;
            }
        });
    }

    protected boolean waitElementToAttributeTextNotContainsEitherOne(WebElement element, String attribute
            , String... expectedContainingTextList) throws WebDriverException {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(SELENIUM_ELEMENT_LOCATING_TIME_OUT_SECONDS, SECONDS)
                .pollingEvery(100, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return (boolean) wait.until(new ExpectedCondition<Boolean>() {

            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                String logMessage = "Waiting element for attribute: '" + attribute + "' contains text: ";
                for (String value : expectedContainingTextList) {
                    logMessage += "'" + value + "'" + " or ";
                    if (!element.getAttribute(attribute).contains(value)) {
                        Log.info("Found element for attribute: '" + attribute + "' contains text: '" + value + "'");
                        TestSupporter.implicitWait(500);
                        return true;
                    }
                }
                Log.info(logMessage.substring(0, logMessage.lastIndexOf("or ")).trim());
                return false;
            }
        });
    }

    protected void waitForElementsLocatedByXPath(int expectedElementCount, String xpath) {
        int index = 0;
        while (true) {
            if(webDriver.findElements(By.xpath(xpath)).size() == expectedElementCount) {
                break;
            }
            index++;
            if(index>maxRetryCount) {
                Camera.takeSnapShot("RunTimeException", null);
                throw new RuntimeException("Elements not found with the given xpath: " + xpath);
            }
            TestSupporter.implicitWait(200);
        }
    }

}
