package utils;

import org.openqa.selenium.WebElement;

public interface ICommonActions {

    void click(WebElement element);

    void sendKeys(WebElement element, String value);

    String getText(WebElement element);
}
