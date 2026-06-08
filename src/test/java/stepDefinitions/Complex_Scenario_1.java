package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LeavePage;

public class Complex_Scenario_1 {

    // Initializing LeavePage with the framework's driver factory pattern
    private final LeavePage leavePage = new LeavePage(base.DriverFactory.getDriver());

    @When("the user navigates to Leave and clicks My Leave options")
    public void theUserNavigatesToLeaveAndClicksMyLeaveOptions() {
        leavePage.navigateToLeaveMenu();
        leavePage.clickMyLeaveSubMenu();
    }

    @And("clicks on the leave details button for the record matching employee name {string}")
    public void clicksOnTheLeaveDetailsButtonForTheRecordMatchingEmployeeName(String employeeName) {
        // Runs your clean row verification and clicks the action button safely
        leavePage.verifyEmployeeRowAndClickDetails(employeeName);
        leavePage.selectViewLeaveDetailsFromMenu();
    }

    @Then("the detailed leave request overlay panel should load successfully")
    public void theDetailedLeaveRequestOverlayPanelShouldLoadSuccessfully() {
        boolean isPanelDisplayed = leavePage.isLeaveDetailsPanelVisible();
        Assertions.assertTrue(isPanelDisplayed, "Validation Error: The detailed leave request view context page did not load successfully after clicking dropdown details option link!");
    }
}