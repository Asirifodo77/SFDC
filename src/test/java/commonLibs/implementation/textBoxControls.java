package commonLibs.implementation;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import cucumber.api.Scenario;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class textBoxControls {
    private Scenario _scenario;

    //constructor class
    public textBoxControls(Scenario _scenario) {
        this._scenario=_scenario;
    }

    public String getText(WebElement element) throws ElementNotFoundException, WebDriverException {

        String elementVal="";

        try {
            elementVal  = element.getAttribute("value").trim();

        } catch (NullPointerException ne) {
            System.out.println("The text of element is Null (Description - "+ne+")");

            //setting the value to blank
            elementVal="";

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            throw en;

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            throw we;
        }

        return elementVal;
    }

    public void hitEnterKey(WebElement element) {
        try {
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void setText(WebElement element, String val){
        //check if passed value is Not null
        if(val!=null){
            try {

                clearTextBox(element);

            } catch (Exception e) {
                System.out.println("Unable to clear the textbox. (Description - "+e+")");
                throw  e;
            }

            try {
                element.sendKeys(val);

            } catch (Exception e) {
                System.out.println("Unable to set text in the text box (Description -"+e+")");
                throw e;
            }

        } else { //when the passed value is Null
            System.out.println("The provided text value is Null.");
        }
    }




    public void clearTextBox(WebElement element){
        try{
            element.clear();

        } catch (NullPointerException ne) {
            System.out.println("The text of element is Null (Description - "+ne+")");

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");

        } catch (Exception e){
            System.out.println("Unable to clean the text box value. ( Description -"+e+")");

        }
    }
}
