package base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;   // CHANGE: Imported EdgeDriver to support Microsoft Edge natively
import org.openqa.selenium.edge.EdgeOptions;    // CHANGE: Imported EdgeOptions to configure Microsoft Edge properties
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void initDriver() {
        if (tlDriver.get() != null) {
            return;
        }

        ConfigReader.loadConfig();

        String execution = ConfigReader.getProperty("Execution");

        if (execution == null) {
            throw new RuntimeException("The key 'Execution' is missing in config.properties!");
        }
        execution = execution.trim();

        // CHANGE: Added dynamic browser parameter capturing from system environment flags (like -Dbrowser=edge)
        String browser = System.getProperty("browser");

        // CHANGE: Fallback layer 1 - if no maven/system flag is passed, check inside config.properties
        if (browser == null) {
            browser = ConfigReader.getProperty("browser");
        }

        // CHANGE: Fallback layer 2 - if browser isn't configured anywhere, default safely to chrome
        if (browser == null) {
            browser = "chrome";
        }

        // CHANGE: Cleaned input by stripping whitespaces and converting string to lower case
        browser = browser.trim().toLowerCase();

        // CHANGE: Restructured initialization block into a dynamic switch-conditional structure based on the targeted browser
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.password_manager_leak_detection", false);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");

            try {
                if ("remote".equalsIgnoreCase(execution)) {
                    String remoteUrl = ConfigReader.getProperty("remote.url");
                    if (remoteUrl == null) {
                        throw new RuntimeException("The key 'remote.url' is missing in config.properties for remote execution!");
                    }
                    tlDriver.set(new RemoteWebDriver(new URL(remoteUrl.trim()), options));
                } else {
                    tlDriver.set(new ChromeDriver(options));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // CHANGE: Added entirely new execution branch to handle Microsoft Edge setup
        } else if (browser.equals("edge")) {
            EdgeOptions options = new EdgeOptions();

            // CHANGE: Synced optimized page load strategy for Edge
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            // CHANGE: Added specific arguments to bypass corporate network policies and launch cleanly
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");

            try {
                if ("remote".equalsIgnoreCase(execution)) {
                    String remoteUrl = ConfigReader.getProperty("remote.url");
                    if (remoteUrl == null) {
                        throw new RuntimeException("The key 'remote.url' is missing in config.properties for remote execution!");
                    }
                    tlDriver.set(new RemoteWebDriver(new URL(remoteUrl.trim()), options));
                } else {
                    tlDriver.set(new EdgeDriver(options)); // CHANGE: Instantiates Edge driver safely on the current executing thread
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // CHANGE: Added an exception message block if an unrecognized browser target is passed
            throw new RuntimeException("Unsupported browser framework target configuration requested: " + browser);
        }

        // Shared timeout and initialization parameters applied globally across all threads
        if (tlDriver.get() != null) {
            tlDriver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            tlDriver.get().manage().window().maximize();
        }
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                tlDriver.remove();
            }
        }
    }
}
































//package base;
//
//import org.openqa.selenium.PageLoadStrategy;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//import java.net.URL;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DriverFactory {
//    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
//
//    public static WebDriver getDriver() {
//        return tlDriver.get();
//    }
//
//    public static void initDriver() {
//        if (tlDriver.get() != null) {
//            return;
//        }
//
//        ConfigReader.loadConfig();
//
//        String execution = ConfigReader.getProperty("Execution");
//
//        if (execution == null) {
//            throw new RuntimeException("The key 'Execution' is missing in config.properties!");
//        }
//        execution = execution.trim();
//
//        ChromeOptions options = new ChromeOptions();
//
//        // 1. Optimize how Selenium waits for page loading
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//
//        // 2. Prevent Password Manager & credentials leak popups
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("profile.password_manager_leak_detection", false);
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//        options.setExperimentalOption("prefs", prefs);
//
//        // 3. Clean Automation Banner Removal Rules (Safe syntax for both IDEs)
//        //options.setExperimentalOption("excludeSwitches", java.util.Collections.singletonList("enable-automation"));
//        //options.addArguments("--disable-blink-features=AutomationControlled");
//        options.addArguments("--remote-allow-origins=*");
//
//        // 4. ESSENTIAL FOR CORPORATE MACHINES: Allow Chrome to execute under strict policies
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-gpu");
//
//        try {
//            if ("remote".equalsIgnoreCase(execution)) {
//                String remoteUrl = ConfigReader.getProperty("remote.url");
//                if (remoteUrl == null) {
//                    throw new RuntimeException("The key 'remote.url' is missing in config.properties for remote execution!");
//                }
//                tlDriver.set(new RemoteWebDriver(new URL(remoteUrl.trim()), options));
//            } else {
//                tlDriver.set(new ChromeDriver(options));
//            }
//            tlDriver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//            tlDriver.get().manage().window().maximize();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void quitDriver() {
//        WebDriver driver = tlDriver.get();
//        if (driver != null) {
//            try {
//                driver.quit();
//            } finally {
//                tlDriver.remove();
//            }
//        }
//    }
//}