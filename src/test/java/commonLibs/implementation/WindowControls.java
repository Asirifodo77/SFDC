package commonLibs.implementation;

import org.openqa.selenium.WebDriver;
import commonLibs.contracts.IwindowHandeling;

import java.util.Set;

public class WindowControls implements IwindowHandeling {

    private WebDriver driver;
    public WindowControls(WebDriver driver) {
        this.driver=driver;
    }

    @Override
    public void switchToChildWindow() throws Exception {
        String childWindow=driver.getWindowHandles().toArray()[1].toString();
        driver.switchTo().window(childWindow);
    }

    @Override
    public void switchToAnyWindow(String windowhandle) throws Exception {
        driver.switchTo().window(windowhandle);
    }

    @Override
    public void switchToAnyWindow(int childwindowindex) throws Exception {
        String childWindow=driver.getWindowHandles().toArray()[childwindowindex].toString();
        driver.switchTo().window(childWindow);
    }

    @Override
    public String getWindowHandle() throws Exception {
        return driver.getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() throws Exception {
        return driver.getWindowHandles();
    }
}
