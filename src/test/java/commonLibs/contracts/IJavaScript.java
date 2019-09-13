package commonLibs.contracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IJavaScript {

    public void executeJavaScript(String scriptToExecute , WebElement element) throws Exception;
    public void scrollDown (int x, int y) throws Exception;
    public String executeJavaScriptWithReturnValue (String scriptToExecute) throws Exception;
}
