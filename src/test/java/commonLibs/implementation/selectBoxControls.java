package commonLibs.implementation;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class selectBoxControls {
    private WebDriver driver;
    private Scenario _scenario;

    public selectBoxControls(WebDriver driver, Scenario _scenario) {
        this.driver=driver;
        this._scenario=_scenario;
    }

    public String getDefaultText(WebElement element) throws Exception {

        String firstSelectedOption="";

        Select select = null;
        try {
            select = new Select(element);
        } catch (NullPointerException ne) {
            System.out.println("The select/dropdown returns Null (Description - "+ne+")");

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
        } catch (Exception e) {
            System.out.println("Unable to select element (Description - "+e+")");
        }

        try {
            if (select != null) {
                firstSelectedOption = select.getFirstSelectedOption().getText().trim();
            }

            //converting the field value to empty if its --None--
            if(firstSelectedOption.equalsIgnoreCase("--None--")){
                firstSelectedOption="";
            }
        } catch (NullPointerException ne) {
            System.out.println("The select/dropdown returns Null (Description - "+ne+")");
            firstSelectedOption="";

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
        } catch (Exception e) {
            System.out.println("Unable to select element (Description - "+e+")");
        }

        return firstSelectedOption;
    }

    public List<String> getAllSelectedOptions(WebElement element) throws Exception{
        System.out.println("Inside selected options ======");
        List<String> allOptionsText = new ArrayList<>();

        Select select = null;
        try {
            select = new Select(element);
        } catch (NullPointerException ne) {
            System.out.println("The select/dropdown returns Null (Description - "+ne+")");

        } catch (ElementNotFoundException en) {
            System.out.println("The Element is not found (Description - "+en+")");

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
        } catch (Exception e) {
            System.out.println("Unable to select element (Description - "+e+")");
        }


        List<WebElement> option = null;
        try {

            System.out.println("===== Before getting all options");
            if (select != null) {
                System.out.println("===== Inside checking - Select box is not null");
                option = select.getOptions();
                System.out.println("===== After getting all options");
            }

        } catch (NullPointerException ne) {
            System.out.println("The select/dropdown returns Null (Description - "+ne+")");
        }catch (Exception e) {
            System.out.println("Unable to select element (Description - "+e+")");
        }

        try {

            if(option.size() == 0) {
                    System.out.println("No items found in the select box");
                } else {
                    System.out.println("Option count : " + option.size());
                    for (int n = 0; n < option.size(); n++) {
                        allOptionsText.add(n, option.get(n).getText());
                        System.out.println("Result of select option ==== : " + option.get(n).getText());
                    }
                }

        } catch (Exception e) {
            System.out.println("Unable to get all selected options from the select/dropdown (Description - "+e+")");
        }
        return allOptionsText;

    }

    public void selectByVisibleText(WebElement element, String text){
        System.out.println("select box -> Select By Text");
        Select select = new Select(element);
        try {
            select.selectByVisibleText(text);
        }catch (Exception e){
            String msg = "Element not found or not visible";
            throw new WebDriverException(e.getMessage());
        }
    }

    public void selectByValue(WebElement element, String text) {
        Select select = null;
        try {
            select = new Select(element);
        } catch (ElementNotFoundException en){
            System.out.println("Unable to locate Element-"+ en.getLocalizedMessage());
            throw en;
        }

        try {
            select.selectByValue(text);
        } catch (Exception e){
            System.out.println("Unable to select "+text+ " from the dropdown");
            throw e;
        }
    }

    public void selectByIndex(WebElement element, int index){
        System.out.println("select box -> Select By Index");
        Select select = new Select(element);
        try {
            select.selectByIndex(index);
        }catch (Exception e){
            String msg = "Element not found or not visible";
            throw new WebDriverException(e.getMessage());
        }
    }

    public boolean isSelectHasMultipleOptions(List<WebElement> elementList){
        if(elementList.size()==0 || elementList.isEmpty() || elementList==null){
            //false when there are no elements
            System.out.println("No default multiple values selected");
            return false;

        } else {
            System.out.println(elementList.size() +" default multiple values selected");
            return true;
        }
    }

}
