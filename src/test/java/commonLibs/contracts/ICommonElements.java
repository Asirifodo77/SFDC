package commonLibs.contracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ICommonElements {

    public String getText(WebElement element) throws Exception;

    public String getExtractedText(String text) throws Exception;

    public void click(WebElement element) throws Exception;

    public String getAttribute(WebElement element, String attribute) throws Exception;

    public String getCssValue(WebElement element, String cssPropertyName) throws Exception;

    public boolean isElementEnabled(WebElement element) throws Exception;

    public boolean isElementVisible(WebElement element) throws Exception;

    public boolean isElementSelected(WebElement element) throws Exception;

    public WebElement getElementByXpath(WebDriver driver, String xpathExpression) throws Exception;

    public List<WebElement> getElementsByXpath(WebDriver driver, String xpathExpression) throws Exception;

    public void waitForElementToBeClickable(WebDriver driver, WebElement element) throws Exception;

    public WebElement waitForElement(WebElement element) throws Exception;

    public WebElement waitElementToBeClickable(WebElement element) throws WebDriverException;

    public boolean isElementClickable(WebElement element) throws Exception;
}
