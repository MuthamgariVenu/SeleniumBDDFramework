package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonActions implements ICommonActions {

    private WebDriver driver;
    private WaitHelper waitHelper;

    public CommonActions(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver, 10); // explicit wait 10 sec
    }

    @Override
    public void click(WebElement element) {
        try {
            waitHelper.waitForClickable(element);
            element.click();
            System.out.println("✅ Clicked on element: " + element);
        } catch (Exception e) {
            System.out.println("❌ Click failed: " + e.getMessage());
        }
    }

    @Override
    public void sendKeys(WebElement element, String value) {
        try {
            waitHelper.waitForVisibility(element);
            element.clear();
            element.sendKeys(value);
            System.out.println("✅ Entered text: " + value);
        } catch (Exception e) {
            System.out.println("❌ SendKeys failed: " + e.getMessage());
        }
    }

    @Override
    public String getText(WebElement element) {
        waitHelper.waitForVisibility(element);
        return element.getText();
    }
}
