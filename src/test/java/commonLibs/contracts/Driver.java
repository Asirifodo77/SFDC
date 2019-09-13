package commonLibs.contracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface Driver {

    public void openBrowserAndGetURL(String url) throws Exception;

    public String getTitle() throws Exception;

    public String getCurrentURL() throws Exception;

    public String getPageSource() throws Exception;

    public void navigateToURL(String url) throws Exception;

    public void navigateToForward() throws Exception;

    public void navigateToBackward() throws Exception;

    public void refresh() throws Exception;

    public void closeBrowser() throws Exception;

    public void closeAllBrowsers() throws Exception;

    public void waitForElementPresent(WebDriver driver, WebElement element) throws Exception;

}
