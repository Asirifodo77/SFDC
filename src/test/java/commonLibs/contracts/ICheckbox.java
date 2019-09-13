package commonLibs.contracts;

import org.openqa.selenium.WebElement;

public interface ICheckbox {

    public void changeCheckboxStatus (WebElement element, boolean status) throws Exception;

    public Boolean getCheckboxStatus(WebElement element) throws Exception;

}
