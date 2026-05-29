package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.PimPage;

public class Scenario5 {

    private final PimPage pimPage = new PimPage(base.DriverFactory.getDriver());

    @When("the user selects the checkbox for employee {string}")
    public void theUserSelectsTheCheckboxForEmployee(String employeeName) {
        // Steps to locate and click the checkbox matching the employee name row
        pimPage.selectEmployeeCheckbox(employeeName);
    }

    @And("clicks the delete selected button")
    public void clicksTheDeleteSelectedButton() {
        // Clicks the top toolbar trash/delete button
        pimPage.clickDeleteSelectedButton();
    }

    @And("confirms the deletion in the modal popup")
    public void confirmsTheDeletionInTheModalPopup() {
        // Clicks the confirm button inside the OrangeHRM validation alert modal
        pimPage.confirmDeletionInModal();
    }

    @Then("a success message notification should be displayed")
    public void aSuccessMessageNotificationShouldBeDisplayed() {
        // Verifies the visible confirmation alert/toast banner popup window
        boolean isToastVisible = pimPage.isSuccessToastDisplayed();

        // FIXED: Condition first, custom error message at the end
        Assertions.assertTrue(isToastVisible, "The success toast alert message did not appear after deleting the record!");
    }

    @And("the employee record {string} should no longer exist in the table")
    public void theEmployeeRecordShouldNoLongerExistInTheTable(String employeeName) {
        // Double check by parsing the row components to confirm the key is dropped
        String matchKey = employeeName.split(" ")[0];
        boolean isRecordPresent = pimPage.tableContains(matchKey);

        // FIXED: Condition first, custom error message at the end
        Assertions.assertFalse(isRecordPresent, "Validation Error: The deleted employee record is still showing inside the web table elements container!");
    }
}