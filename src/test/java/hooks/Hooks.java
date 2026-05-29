package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private static final Logger log = LogManager.getLogger(Hooks.class);

    //public static WebDriver driver;

    @Before
    public void setup() {
        log.info("-------------------- STARTING SCENARIO SETUP --------------------");

        // 1. Initialize a completely fresh browser session safely
        DriverFactory.initDriver();
        //driver = DriverFactory.getDriver();
        DriverFactory.getDriver().get("https://opensource-demo.orangehrmlive.com/");
    }

    @After
    public void tearDownClose() {
        // 2. Delegate the cleanup entirely to DriverFactory to safely clear ThreadLocal reference
        if (DriverFactory.getDriver() != null) {
            log.info("-------------------- TEARING DOWN SCENARIO --------------------");
            DriverFactory.quitDriver();
            //driver = null; // Reset local static pointer reference
        }
    }
}