package utilities;

import cucumber.api.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import property.Property;

public class TakeScreenshot {
    public WebDriver driver;
    public Scenario _scenario;

    public TakeScreenshot(WebDriver driver,Scenario _scenario ) {
        this.driver=driver;
        this._scenario=_scenario;
    }

    public void takeScreenshot() {
        try {
            _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), Property.SCREENSHOT_IMAGE_TYPE);
        } catch (WebDriverException e) {
            System.out.println("Unable to capture the screenshot due to following exception - "+e);
            _scenario.write("Unable to capture the screenshot due to following exception - "+e);
        }
    }

}
