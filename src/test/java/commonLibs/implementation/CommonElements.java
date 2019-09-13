package commonLibs.implementation;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import commonLibs.contracts.ICommonElements;
import property.Property;

import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CommonElements implements ICommonElements {

    private WebDriver driver;
    private Scenario _scenario;

    public CommonElements(WebDriver driver, Scenario _scenario) {
        this.driver=driver;
        this._scenario=_scenario;

    }

    @Override
    public String getText(WebElement element) {
        String elementText="";

        try {
            elementText = element.getText();

        } catch (NullPointerException ne) {
            System.out.println("The text of element is Null (Description - "+ne+")");

            //setting the value to blank
            elementText="";
            throw ne;

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            throw en;

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            throw we;

        }catch (Exception e) {
            System.out.println("Exception occurred (Description - "+e+")");
            throw e;
        }
        return elementText;
    }

    @Override
    public String getExtractedText(String branchAddressExtractedText) throws Exception {
        return branchAddressExtractedText.trim();

    }

    @Override
    public WebElement getElementByXpath(WebDriver driver, String xpathExpression) throws  Exception {

        WebElement result=driver.findElement(By.xpath(xpathExpression));
        return result;

    }

    public List<WebElement> getElementsByTagName(WebElement parentElement, String tagName) throws Exception {
        List<WebElement> elements = parentElement.findElements(By.tagName(tagName));
        return elements;
    }

    @Override
    public List<WebElement> getElementsByXpath(WebDriver driver, String xpathExpression) throws Exception {
        List<WebElement> results = driver.findElements(By.xpath(xpathExpression));

        return results;
    }

    @Override
    public void waitForElementToBeClickable(WebDriver driver, WebElement element) throws Exception {
        try {
            WebDriverWait wait=new WebDriverWait(driver, Property.TIMEOUT_IN_SECONDS);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException te) {
            System.out.println("waiting to be clickable has timed out. However continuing with the rest of the operations");
        } catch (NoSuchElementException e) {
            System.out.println("UNABLE TO LOCATE ELEMENT (NO ELEMENT FOUND) - "+e.getMessage());
            throw e;
        } catch (WebDriverException we) {
            System.out.println("UNABLE TO LOCATE ELEMENT DUE (WEB DRIVER EXCEPTION) - "+we.getMessage());
            throw we;
        } catch (Exception ge) {
            System.out.println("UNABLE TO LOCATE ELEMENT DUE - "+ge.getMessage());
            throw ge;
        }
    }

    @Override
    public void click(WebElement element) throws Exception {
        try {
            //waitForElementToBeClickable(driver, element);
            element.click();

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            throw en;

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            throw we;
        } catch (Exception e) {
            System.out.println("Exception occurred (Description - "+e+")");
            throw e;
        }
    }

    @Override
    public String getAttribute(WebElement element, String attribute) {
        String elementValue="";

        try {
            elementValue = element.getAttribute(attribute);
        } catch (NullPointerException ne) {
            System.out.println("The text of element is Null (Description - "+ne+")");
            elementValue="";

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");

        } catch (WebDriverException we) {
            //System.out.println("Webdriver Exception occurred (Description - "+we+")");
            System.out.println("========= EXCEPTION COUGHT ON GET ATTRIBUTE LEVEL ==========");
            throw we;
        }

        return elementValue;
    }

    @Override
    public String getCssValue(WebElement element, String cssPropertyName) throws Exception {
        return element.getCssValue(cssPropertyName);
    }

    @Override
    public boolean isElementEnabled(WebElement element) throws Exception {
        try {
            return element.isEnabled();
        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            return  false;
        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            return false;
        }
    }

    @Override
    public boolean isElementVisible(WebElement element) throws Exception {
        try {
            return element.isDisplayed();
        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            return  false;
        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            return false;
        }
    }

    @Override
    public boolean isElementSelected(WebElement element) throws Exception {
        return element.isSelected();
    }

    @Override
    public WebElement waitForElement(WebElement element) throws Exception {
        try {
            return waitElementToBeClickable(element);
        } catch (WebDriverException e) {
            String msg = "Element not found or not visible : ";
            _scenario.write("Element not found or not visible - Waiting for element to be present - : "+Property.TIMEOUT_IN_SECONDS+" SECONDS");
            _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            System.out.println("Element not found or not visible - Waiting for element to be present - : "+Property.TIMEOUT_IN_SECONDS+" SECONDS");
            throw new WebDriverException(msg+e.getMessage());
        }
    }

    @Override
    public WebElement waitElementToBeClickable(WebElement element) throws WebDriverException {
        Wait wait = new FluentWait(driver)
                .withTimeout(Property.TIMEOUT_IN_SECONDS, SECONDS)
                .pollingEvery(Property.TIMEOUT_IN_SECONDS, MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        WebElement elementFound = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
        return elementFound;
    }

    @Override
    public boolean isElementClickable(WebElement element) throws Exception {
        try {
            if(element.getAttribute("class").contains("inactive")) {
                System.out.println("The element's 'class' value contains 'Inactive' phrase. Actual value - "+ element.getAttribute("class"));
                return false;
            } else {
                System.out.println("The element's 'class' value  does not contain 'Inactive' phrase. Actual value - "+ element.getAttribute("class"));
                return true;
            }

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            return  false;
        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            return false;
        }
    }

    public void moveMouseAndClick(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().release().perform();
    }


}
