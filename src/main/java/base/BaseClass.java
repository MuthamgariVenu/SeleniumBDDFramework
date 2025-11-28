package base;

import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class BaseClass {

    private static WebDriver driver;
    private static Properties prop;

    /**
     * Initialize browser only if not already open.
     * Reads all values from config.properties.
     */
    public static void initializeBrowser() {
        try {
            if (driver == null) {  // ✅ Prevents reopening browsers repeatedly
                prop = ConfigReader.initProperties();

                // Get configuration values
                String browser = prop.getProperty("browser", "chrome");
                String appUrl = prop.getProperty("url", "https://practicetestautomation.com/practice-test-login/");
                String timeoutValue = prop.getProperty("timeout", "10");

                int timeout = Integer.parseInt(timeoutValue);

                // Initialize WebDriver
                driver = DriverFactory.initDriver(browser);

                // Setup browser session
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
                driver.manage().window().maximize();
                driver.get(appUrl);

                System.out.println("✅ Browser launched successfully: " + browser);
                System.out.println("🌐 Navigated to URL: " + appUrl);
                System.out.println("⏱ Timeout set to: " + timeout + " seconds");
            } else {
                System.out.println("ℹ️ Browser already initialized. Reusing existing driver.");
            }
        } catch (Exception e) {
            System.err.println("❌ Browser initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get current WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Quit browser and reset driver to null.
     */
    public static void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
                System.out.println("🧹 Browser closed and driver reset successfully.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error while closing browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
