package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.DashboardPage;
import pages.LoginPage;

public class Scenario15 {

    private final DashboardPage dashboardPage = new DashboardPage(base.DriverFactory.getDriver());
    private final LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());

    @When("the user clicks the profile icon dropdown menu")
    public void theUserClicksTheProfileIconDropdownMenu() {
        dashboardPage.clickProfileDropdown();
    }

    @And("selects the Logout option")
    public void selectsTheLogoutOption() {
        dashboardPage.clickLogoutOption();
    }

    @Then("the user should be redirected back to the login page successfully")
    public void theUserShouldBeRedirectedBackToTheLoginPageSuccessfully() {
        // Validate by both the application URL pattern and login interface element visibility
        String currentUrl = loginPage.getPageUrl();
        boolean isLoginFormDisplayed = loginPage.isLoginFieldVisible();

        Assertions.assertTrue(currentUrl.contains("login"),
                "Validation Error: The current URL does not point to the login landing destination page!");
        Assertions.assertTrue(isLoginFormDisplayed,
                "Validation Error: The username input field wrapper structure is not rendered on the viewport!");
    }
}