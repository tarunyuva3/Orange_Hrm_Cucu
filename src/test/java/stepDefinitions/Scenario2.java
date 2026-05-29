package stepDefinitions;

import hooks.Hooks;
import org.junit.jupiter.api.Assertions; // Keep only the JUnit 5 import
import pages.PimPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class Scenario2 {

    private PimPage pimPage;

    public Scenario2() {
        this.pimPage = new PimPage(base.DriverFactory.getDriver());
    }

    @And("clicks on the PIM menu item")
    public void clicksOnThePIMMenuItem() {
        pimPage.clickPim();
    }

    @And("navigates to PIM and click on add employee")
    public void navigatesToPIMAndClickOnAddEmployee() {
        pimPage.navigateToAddEmployee();
    }

    @And("user enters the employee name {string} and {string}")
    public void userEntersTheEmployeeNameAnd(String fname, String lname) {
        pimPage.enterEmployeeName(fname, lname);
    }

    @And("click on toggle button")
    public void clickOnToggleButton() {
        pimPage.enableCreateLoginDetails();
    }

    @And("user fills username {string} and password {string}")
    public void userFillsUsernameAndPassword(String user, String pass) {
        pimPage.fillLoginDetails(user, pass);
    }

    @And("click on save button")
    public void clickOnSaveButton() {
        pimPage.save();
    }

    @Then("profile details should be displayed")
    public void profileDetailsShouldBeDisplayed() {
        boolean isDisplayed = pimPage.personalDetailsDisplayed();

        // FIXED: Placed the boolean condition first, and moved the error message to the end
        Assertions.assertTrue(isDisplayed, "Personal Details section failed to render after saving the employee!");
    }
}