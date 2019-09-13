package commonLibs.implementation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import commonLibs.contracts.IFrameHandling;

public class FrameControls implements IFrameHandling {

    private WebDriver driver;

    public FrameControls(WebDriver driver) {
        this.driver=driver;
    }

    @Override
    public void switchToFrame(String frameID) throws Exception {
        driver.switchTo().frame(frameID);
    }

    @Override
    public void switchToFrame(WebElement element) throws Exception {
        driver.switchTo().frame(element);
    }

    @Override
    public void switchToFrame(int index) throws Exception {
        driver.switchTo().frame(index);
    }

    @Override
    public void switchToDefaultContent() throws Exception {
        driver.switchTo().defaultContent();
    }
}
