package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.Properties;
import utils.ConfigReader;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportName = "ExtentReport_" + timestamp + ".html";
            String reportPath = System.getProperty("user.dir") + "/reports/" + reportName;

            File reportDir = new File(System.getProperty("user.dir") + "/reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            // Load config
            Properties prop = ConfigReader.initProperties();
            String themeValue = prop.getProperty("theme", "light").toLowerCase();
            String reportLogo = prop.getProperty("reportLogo", "");

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            // ✅ Dynamic Theme
            if (themeValue.equals("dark")) {
                spark.config().setTheme(Theme.DARK);
            } else {
                spark.config().setTheme(Theme.STANDARD);
            }

            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Selenium BDD Framework Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // ✅ Add System Info
            String browser = prop.getProperty("browser", "Chrome");
            String environment = prop.getProperty("environment", "QA");
            String projectName = prop.getProperty("project", "Flipkart Automation");

            extent.setSystemInfo("Project", projectName);
            extent.setSystemInfo("Tester", "Venu M");
            extent.setSystemInfo("Environment", environment);
            extent.setSystemInfo("Browser", browser);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));

            // ✅ Add logo (HTML injection)
            if (!reportLogo.isEmpty()) {
                extent.setSystemInfo("Logo", "<img src='" + reportLogo + "' width='80' height='80'/>");
            }

            System.out.println("📄 Extent Report created: " + reportPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
