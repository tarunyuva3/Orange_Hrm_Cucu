package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LeavePage;
import pages.LoginPage;

public class Scenario6 {

    private final LeavePage leavePage = new LeavePage(base.DriverFactory.getDriver());
    private LoginPage lp = new LoginPage(base.DriverFactory.getDriver());

    @When("the user navigates to Leave and clicks Apply options")
    public void theUserNavigatesToLeaveAndClicksApplyOptions() {
        leavePage.navigateToLeaveMenu();
        leavePage.clickApplySubMenu();
    }

    @And("selects leave type as {string}")
    public void selectsLeaveTypeAs(String leaveType) {
        leavePage.selectLeaveType(leaveType);
    }

    @And("picks start date {string} and end date {string}")
    public void picksStartDateAndEndDate(String startDate, String endDate) {
        leavePage.selectLeaveDates(startDate,endDate);
    }

    @And("adds a leave comment {string}")
    public void addsALeaveComment(String commentText) {
        leavePage.enterLeaveComments(commentText);
    }

    @And("clicks the apply leave button")
    public void clicksTheApplyLeaveButton() {
        leavePage.clickApplySubmitButton();
    }

    @Then("the leave request should be successfully created")
    public void theLeaveRequestShouldBeSuccessfullyCreated() {
        String targetToastTitle = leavePage.getToastTitleText();
        String targetToastMessage = leavePage.getToastMessageText();

        boolean isSuccess = targetToastTitle.equalsIgnoreCase("Success") ||
                targetToastMessage.toLowerCase().contains("successfully saved") ||
                targetToastMessage.toLowerCase().contains("applied");

        Assertions.assertTrue(isSuccess, "Validation Failure: The leave submission confirmation alert did not verify a positive outcome! Found: " + targetToastTitle + " - " + targetToastMessage);
    }

    @Given("login as created user")
    public void loginAsCreatedUser()
    {
        lp.enterUsername("Ravrayudu");
        lp.enterPassword("Ravireddy123");
        lp.clickLogin();
    }
}