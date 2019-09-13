package step_definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.FileReader;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Hooks {
    public static WebDriver driver;
    public static JsonObject myJsonObj;
    public static Scenario _scenario;

    //MalformedURLException
    @Before
    public void initializeSetup(Scenario scenario) throws Exception {
        System.out.println(Instant.now().toString() + " Initializing the setup");
        //FirefoxOptions options = new FirefoxOptions();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        //set Proxy
        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setProxyAutoconfigUrl("http://pac.zscaler.net/dfs.com/proxy.pac");
        options.setProxy(proxy);
        //DesiredCapabilities cap = new DesiredCapabilities();
        //cap.setCapability(CapabilityType.PROXY, proxy);
        //options.addArguments("disable-infobars");
        URL hubURL = new URL("http://127.0.0.1:4444/wd/hub");
        driver = new RemoteWebDriver(hubURL,options);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(new FileReader("src//test//resources//test_data//Test_data"));
        myJsonObj = (JsonObject) obj;
        _scenario = scenario;
    }

    @After
    public void cleanupSetup() {
        _scenario.write("Finished Scenario");
        System.out.println("RESULTS : "+_scenario.getStatus());
        try{
            driver.quit();
            if (_scenario.isFailed()){
                _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            }
            System.out.println(Instant.now().toString() + " Cleanup setup was executed");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
//        //screenshot for failure
//        boolean hasQuit = driver.toString().contains("null");
//        if(!hasQuit){
//            if (_scenario.isFailed()){
//                _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
//            }
//            driver.quit();
//        }

    }
}