package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import base.BaseClass;
import pages.LoginPage;
import dataProviders.DataProviders;

public class LoginTest extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifyLogin(String testCaseID, String username, String password, String expectedResult) {

        // ✅ Get driver instance from BaseClass
        WebDriver driver = BaseClass.getDriver();

        // Open URL
        driver.get("https://practicetestautomation.com/practice-test-login/");
        System.out.println("🌐 Navigated to Practice Test Login page");

        // Page Object
        LoginPage login = new LoginPage(driver);

        // Perform login steps
        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLoginButton();

        // Validate login result
        boolean result = login.isLoginSuccessful();

        Assert.assertEquals(result, Boolean.parseBoolean(expectedResult),
                "❌ Login result mismatch for test case: " + testCaseID);

        System.out.println("✅ Test Case " + testCaseID + " executed successfully.");
    }
}
