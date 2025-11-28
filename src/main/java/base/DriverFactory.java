package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver(String browserName) {

        System.out.println("🧠 Launching browser: " + browserName);

        // Read headless setting from config.properties
        String headlessValue = ConfigReader.initProperties().getProperty("headless", "false");
        boolean isHeadless = headlessValue.equalsIgnoreCase("true");

        if (browserName.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            if (isHeadless) {
                // Jenkins-friendly headless mode
                System.out.println("🚀 Running in HEADLESS mode (Jenkins)");
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            } else {
                // Local mode
                System.out.println("🖥️ Running in NORMAL mode (Local)");
            }

            tlDriver.set(new ChromeDriver(options));
        }

        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static synchronized void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
            System.out.println("🧹 Driver closed and removed from ThreadLocal");
        }
    }
}
