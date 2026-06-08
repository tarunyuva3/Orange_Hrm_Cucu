package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.LeavePage;

public class Scenario7_and_13 {

    private final LeavePage leavePage = new LeavePage(base.DriverFactory.getDriver());

    @When("the user navigates to Leave and clicks Assign Leave options")
    public void theUserNavigatesToLeaveAndClicksAssignLeaveOptions() {
        leavePage.navigateToAssignLeave();
    }

    @And("handles assignment form input with employee name {string}, leave type {string}, start date {string}, and end date {string}")
    public void handlesAssignmentFormInput(String fullEmployee, String leaveType, String startDate, String endDate) {
        leavePage.searchAndSelectEmployee(fullEmployee);
        leavePage.selectLeaveType(leaveType);
        leavePage.selectLeaveDates(startDate, endDate);
    }

    @And("clicks the Assign button")
    public void clicksTheAssignButton() {
        leavePage.clickAssignButton();
    }

    @Then("the assignment form should conclude with status validation result {string}")
    public void theAssignmentFormShouldConcludeWithStatusValidationResult(String expectedResult) {
        if (expectedResult.equalsIgnoreCase("Success")) {

            // Capture the actual runtime values from the UI toast alert notification structure
            String actualTitle = leavePage.getToastTitleText();
            String actualMessage = leavePage.getToastMessageText();

            // Guard against warning exceptions (like the "Failed to Submit" alert banner)
            if (actualTitle.equalsIgnoreCase("Warning") || actualMessage.toLowerCase().contains("failed")) {
                Assertions.fail("Form Submission Failed at application runtime! System UI Toast Message: ["
                        + actualTitle + " - " + actualMessage + "]");
            }

            // Assert that the popup is an authentic success execution confirm message wrapper
            Assertions.assertEquals("Success", actualTitle, "Validation Error: The notification title was not 'Success'!");
            Assertions.assertTrue(actualMessage.toLowerCase().contains("saved") || actualMessage.toLowerCase().contains("success"),
                    "Validation Error: Expected successful text strings but instead found: " + actualMessage);

        } else if (expectedResult.equalsIgnoreCase("Required Validation")) {
            boolean isValidationErrorVisible = leavePage.isRequiredFieldValidationErrorVisible();
            Assertions.assertTrue(isValidationErrorVisible, "Validation Error: The expected 'Required' field warning messages were absent!");
        }
    }
}