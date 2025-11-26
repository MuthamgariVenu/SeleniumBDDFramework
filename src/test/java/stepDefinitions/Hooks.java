package stepDefinitions;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.Status;
import base.BaseClass;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        try {
            // Initialize browser and launch application
            BaseClass.initializeBrowser();
            driver = BaseClass.getDriver();

            // Start a new Extent test node for this scenario
            ExtentTestManager.createTest(scenario.getName());
            ExtentTestManager.getTest().log(Status.INFO, "🚀 Starting test execution: " + scenario.getName());

        } catch (Exception e) {
            ExtentTestManager.createTest(scenario.getName());
            ExtentTestManager.getTest().log(Status.FAIL, "❌ Setup failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                // Capture screenshot on failure
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, scenario.getName());

                ExtentTestManager.getTest().log(Status.FAIL, "❌ Test Failed: " + scenario.getName());
                ExtentTestManager.getTest().log(Status.FAIL, "Failure Reason: " + scenario.getStatus());

                if (screenshotPath != null) {
                    ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
                }

            } else {
                ExtentTestManager.getTest().log(Status.PASS, "✅ Test Passed: " + scenario.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                // Flush Extent Report (write all logs to file)
                ExtentManager.flush();
                System.out.println("📄 Extent Report generated successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Quit browser cleanly via BaseClass
            try {
                BaseClass.quitDriver();
                System.out.println("🧹 Browser closed successfully and driver reset.");
            } catch (Exception e) {
                System.err.println("⚠️ Error while closing browser: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
