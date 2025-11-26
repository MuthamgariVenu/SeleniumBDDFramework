package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestManager {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // ✅ Create a new test in Extent Report
    public static void createTest(String testName) {
        ExtentTest extentTest = ExtentManager.getInstance().createTest(testName);
        test.set(extentTest);
    }

    // ✅ Get current test (thread-safe)
    public static ExtentTest getTest() {
        return test.get();
    }

    // ✅ Log PASS
    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
    }

    // ✅ Log FAIL
    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
    }

    // ✅ Log INFO
    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
    }

    // ✅ Log WARNING
    public static void logWarning(String message) {
        getTest().log(Status.WARNING, message);
    }

    // ✅ Attach screenshot to report
    public static void logScreenshot(String path) {
        try {
            getTest().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Log fail with screenshot (shortcut)
    public static void logFailWithScreenshot(String message, String path) {
        logFail(message);
        logScreenshot(path);
    }
}
