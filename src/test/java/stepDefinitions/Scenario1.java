package stepDefinitions;

import hooks.Hooks;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.DashboardPage;
import pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scenario1
{
    // Reference to the LoginPage class
    private LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());
    private DashboardPage dp = new DashboardPage(base.DriverFactory.getDriver());
    private static final Logger log = LogManager.getLogger(Scenario1.class);

    // =========================================================
    // SCENARIO 1: POSITIVE LOGIN FLOW
    // =========================================================

    @Given("the user is navigated to OrangeHrm login page")
    public void theUserIsNavigatedToOrangeHrmLoginPage() {
        String actualTitle = loginPage.getPageTitle();
        // JUnit 5 message parameter moved to the end
        Assertions.assertEquals("OrangeHRM", actualTitle, "Login page title did not match!");
    }

    @When("the user enters username {string}")
    public void theUserEntersUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("the Dashboard page should load successfully")
    public void theDashboardPageShouldLoadSuccessfully() {
        boolean isDashboardLoaded = dp.isDashboardDisplayed();
        // JUnit 5 message parameter moved to the end
        Assertions.assertTrue(isDashboardLoaded, "Dashboard failed to load after login!");
        log.info("The Url of the page is " + loginPage.getPageUrl());
        Assertions.assertEquals("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index", loginPage.getPageUrl());
    }

    // =========================================================
    // SCENARIO 1.1: NEGATIVE LOGIN FLOW (SCENARIO OUTLINE)
    // =========================================================

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessage();
        log.info("Captured Error Message from UI: " + actualErrorMessage);

        // FIXED: Switched from Assert to Assertions and moved the message parameter to the end
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message did not match!");
    }
}