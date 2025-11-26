package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import base.BaseClass;
import pages.LoginPage;
import utils.ExcelUtils;   // ✅ Add this line

public class LoginSteps extends BaseClass {

    WebDriver driver;
    LoginPage loginPage;

    @Given("user launches the practice test login page")
    public void user_launches_the_practice_test_login_page() {
        BaseClass.initializeBrowser();
        driver = BaseClass.getDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        loginPage = new LoginPage(driver);
        System.out.println("🌐 Practice Test Login page opened successfully");
    }

    @When("user logs in with Excel data")
    public void user_logs_in_with_excel_data() throws Exception {
        String excelPath = System.getProperty("user.dir") + "/src/test/java/testData/LoginData.xlsx";
        String sheetName = "Sheet1";

        Object[][] data = ExcelUtils.getTestData(excelPath, sheetName);

        for (Object[] row : data) {
            String testCaseID = row[0].toString();
            String username = row[1].toString();
            String password = row[2].toString();
            String expectedResult = row[3].toString();

            System.out.println("🔹 Running: " + testCaseID + " | Username: " + username);

            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            boolean actual = loginPage.isLoginSuccessful();
            boolean expected = Boolean.parseBoolean(expectedResult);

            if (expected)
                Assert.assertTrue(actual, "❌ Login failed for valid user.");
            else
                Assert.assertFalse(actual, "❌ Login passed for invalid user.");

            driver.navigate().back();
        }
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        boolean success = loginPage.isLoginSuccessful();
        Assert.assertTrue(success, "❌ Expected login success but failed.");
        System.out.println("✅ User logged in successfully!");
    }
}
