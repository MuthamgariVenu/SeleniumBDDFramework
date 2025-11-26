package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {
        WebDriver driver = null;

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();

            // 🧠 Detect if running inside headless Linux (e.g., Jenkins container)
            String os = System.getProperty("os.name").toLowerCase();
            boolean isLinux = os.contains("linux");

            if (isLinux) {
                System.out.println("🐧 Linux detected → Running Chrome in headless mode");
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--remote-allow-origins=*");
            } else {
                System.out.println("🖥 Windows/Mac → Running Chrome normally");
            }

            driver = new ChromeDriver(options);
        }

        return driver;
    }
}
