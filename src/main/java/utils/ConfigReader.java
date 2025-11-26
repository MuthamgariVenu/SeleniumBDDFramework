package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    // Initialize config.properties file
    public static Properties initProperties() {
        if (prop == null) {
            prop = new Properties();
            try {
                FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
                        + "/src/main/resources/config.properties");
                prop.load(ip);
                System.out.println("✅ Config file loaded successfully");
            } catch (IOException e) {
                System.out.println("❌ Error loading config file: " + e.getMessage());
            }
        }
        return prop;
    }

    // Get value from config.properties
    public static String getProperty(String key) {
        if (prop == null) {
            initProperties();
        }
        return prop.getProperty(key);
    }

    // 🚀 Get environment-specific value dynamically
    public static String getEnvSpecificValue(String key) {
        if (prop == null) {
            initProperties();
        }

        // Check if Jenkins or command-line provided "env" parameter
        String envFromSystem = System.getProperty("env");
        String activeEnv = (envFromSystem != null) ? envFromSystem : prop.getProperty("env");

        if (activeEnv == null) {
            activeEnv = prop.getProperty("environment", "qa"); // fallback if key is "environment"
        }

        activeEnv = activeEnv.trim().toLowerCase();
        String fullKey = activeEnv + "." + key;

        String value = prop.getProperty(fullKey);
        if (value == null) {
            System.out.println("⚠️ No environment-specific key found for: " + fullKey);
        } else {
            System.out.println("🌐 Using " + activeEnv.toUpperCase() + " value for " + key + ": " + value);
        }
        return value;
    }
}
