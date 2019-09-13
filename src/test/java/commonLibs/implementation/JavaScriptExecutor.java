package commonLibs.implementation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import commonLibs.contracts.IJavaScript;

public class JavaScriptExecutor implements IJavaScript {

    private WebDriver driver;

    public JavaScriptExecutor(WebDriver driver) {

        this.driver=driver;
    }

    public JavascriptExecutor getJSEngine(){
        JavascriptExecutor jsEngine = (JavascriptExecutor) driver;
        return jsEngine;
    }

    @Override
    public void executeJavaScript(String scriptToExecute, WebElement element) throws Exception {
        getJSEngine().executeScript(scriptToExecute , element);
    }

    public void scrollToElement(WebElement element) throws Exception {
        executeJavaScript("arguments[0].scrollIntoView();",element);
    }

    @Override
    public void scrollDown(int x, int y) throws Exception {
        String scriptToExecute=String.format("window.scrollBy(%d,%d)",x,y);
        getJSEngine().executeScript(scriptToExecute);
    }

    @Override
    public String executeJavaScriptWithReturnValue(String scriptToExecute) throws Exception {
        return getJSEngine().executeScript(scriptToExecute).toString();
    }

    public void clickElement(WebElement element) throws Exception {
        getJSEngine().executeScript("arguments[0].click();",element);
    }

    public void changeCheckBoxStatus(WebElement element, boolean status) throws Exception {
        try {
            if ((element.isSelected() && !status) || (!element.isSelected() && status)){
                clickElement(element);
            }
        } catch (WebDriverException e) {
            throw e;
        }
    }
}
