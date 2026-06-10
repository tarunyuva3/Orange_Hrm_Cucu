package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.LoginPage;
import pages.PimPage;

public class Scenario3 {
    private static final Logger log = LogManager.getLogger(Scenario3.class);

    private final LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());
    private final PimPage pimPage = new PimPage(base.DriverFactory.getDriver());

    @When("login as admin user")
    public void loginAsAdminUser() {
        log.info("Scenario : Logging into OrangeHRM application with administrative privileges");
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
    }

    @And("the user searches employee {string} from Employee List")
    public void theUserSearchesEmployeeFromEmployeeList(String employeeName) {
        log.info("Scenario : Initializing PIM module search routine for target: {}", employeeName);
        pimPage.clickPim();
        pimPage.searchEmployee(employeeName);
    }

    @Then("matching employee rows for {string} should be displayed")
    public void matchingEmployeeRowsForShouldBeDisplayed(String employeeName) {
        log.info("Scenario : Confirming structural search visibility match criteria for: {}", employeeName);
        String matchKey = employeeName.split(" ")[0];
        boolean isMatched = pimPage.tableContains(matchKey);

        Assertions.assertTrue(isMatched, "Validation failed: Target search records for '" + employeeName + "' not visible inside structural rows!");
    }
}