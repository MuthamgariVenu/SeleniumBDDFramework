package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) return null;

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir")
                + "/reports/screenshots/" + screenshotName + "_" + timestamp + ".png";

        try {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();

            // ✅ Set viewport to full page
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("width", 1920);
            metrics.put("height", 6000); // capture long pages
            metrics.put("deviceScaleFactor", 1);
            metrics.put("mobile", false);
            ((ChromeDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);

            // ✅ Capture full page screenshot
            Map<String, Object> params = new HashMap<>();
            Object result = ((ChromeDriver) driver).executeCdpCommand("Page.captureScreenshot", params);
            String base64Screenshot = ((Map<?, ?>) result).get("data").toString();
            byte[] imageBytes = Base64.getDecoder().decode(base64Screenshot);

            try (FileOutputStream fos = new FileOutputStream(new File(screenshotPath))) {
                fos.write(imageBytes);
            }

            System.out.println("📸 Full-page screenshot captured: " + screenshotPath);
            return screenshotPath;

        } catch (Exception e) {
            // Fallback normal screenshot
            try {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File dest = new File(screenshotPath);
                org.apache.commons.io.FileUtils.copyFile(src, dest);
                System.out.println("📸 Fallback screenshot captured: " + screenshotPath);
                return screenshotPath;
            } catch (IOException io) {
                io.printStackTrace();
                return null;
            }
        }
    }
}
