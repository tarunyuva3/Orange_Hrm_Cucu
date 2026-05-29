package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.DashboardPage;
import pages.TimePage;

public class Scenario8 {

    private final DashboardPage dashboardPage = new DashboardPage(base.DriverFactory.getDriver());
    private final TimePage timePage = new TimePage(base.DriverFactory.getDriver());

    @When("the user accesses Punch module from the Dashboard clock widget shortcut")
    public void userAccessesPunchModuleFromDashboardClockWidgetShortcut() {
        dashboardPage.clickClockShortcutWidget();
    }

    @And("enters Punch In time configuration value {string} and clicks login")
    public void entersPunchInTimeConfigurationValueAndClicksLogin(String loginTime) {
        timePage.updatePunchTimeField(loginTime);
        timePage.clickPunchActionButton();
    }

    @And("enters Punch Out time configuration value {string} and clicks logout")
    public void entersPunchOutTimeConfigurationValueAndClicksLogout(String logoutTime) {
        timePage.updatePunchTimeField(logoutTime);
        timePage.clickPunchActionButton();
    }

    @And("returns back onto the Dashboard module view panel")
    public void returnsBackOntoTheDashboardModuleViewPanel() {
        dashboardPage.navigateToDashboardMenu();
    }

    @Then("the dashboard time at work status badge must display as {string}")
    public void dashboardTimeAtWorkStatusBadgeMustDisplayAs(String expectedStatus) {
        boolean isPunchedOut = dashboardPage.isCurrentStatusPunchedOut();

        // FIXED: Condition evaluated first, custom error message moved to the end
        Assertions.assertTrue(isPunchedOut, "Validation Failure: Expected dashboard state component element to represent: "
                + expectedStatus + ", but it is still showing as Punched In!");

        System.out.println("Automation Step Validation Confirmation Status Verified: Page reports " + expectedStatus);
    }
}