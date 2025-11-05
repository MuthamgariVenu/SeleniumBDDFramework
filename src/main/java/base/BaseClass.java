package base;

import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class BaseClass {

    private static WebDriver driver;
    private static Properties prop;

    /**
     * Initialize browser, timeouts, and application URL.
     * Reads all values from config.properties.
     */
    public static void initializeBrowser() {
        try {
            // Load configuration
            prop = ConfigReader.initProperties();

            // Get values safely with defaults
            String browser = prop.getProperty("browser", "chrome");
            String appUrl = prop.getProperty("url", "https://www.flipkart.com");
            String timeoutValue = prop.getProperty("timeout");

            int timeout = 10; // default fallback
            if (timeoutValue != null && !timeoutValue.isEmpty()) {
                timeout = Integer.parseInt(timeoutValue);
            }

            // Initialize WebDriver
            driver = DriverFactory.initDriver(browser);

            // Apply implicit wait and launch URL
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            driver.get(appUrl);

            // Console logs for quick debugging
            System.out.println("✅ Browser launched successfully: " + browser);
            System.out.println("🌐 Navigated to URL: " + appUrl);
            System.out.println("⏱ Timeout set to: " + timeout + " seconds");

        } catch (Exception e) {
            System.err.println("❌ Browser initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get current WebDriver instance.
     */
    public static WebDriver getDriver() {
        driver = DriverFactory.getDriver();
        return driver;
    }
}
