package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver(String browserName) {

        System.out.println("🧠 Launching browser: " + browserName);

        // Read config
        String runMode = ConfigReader.initProperties().getProperty("runMode", "LOCAL");
        String headlessValue = ConfigReader.initProperties().getProperty("headless", "false");
        boolean isHeadless = headlessValue.equalsIgnoreCase("true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        if (isHeadless) {
            System.out.println("🚀 Running in HEADLESS mode");
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        try {
            if (runMode.equalsIgnoreCase("GRID")) {

                // 🔥 RUNNING ON SELENIUM GRID (Jenkins)
                System.out.println("🌐 Running on Selenium GRID");

                String gridUrl = ConfigReader.initProperties().getProperty(
                        "gridUrl",
                        "http://selenium-hub:4444/wd/hub"
                );

                tlDriver.set(new RemoteWebDriver(new URL(gridUrl), options));
            } else {

                // 🖥️ RUNNING LOCALLY
                System.out.println("🖥️ Running locally (ChromeDriver)");

                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver(options));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Invalid GRID URL");
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
