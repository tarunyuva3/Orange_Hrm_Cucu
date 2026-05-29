package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.AdminPage;

public class Scenario9 {

    private final AdminPage adminPage = new AdminPage(base.DriverFactory.getDriver());

    @When("the user navigates to Admin User Management module")
    public void theUserNavigatesToAdminUserManagementModule() {
        adminPage.navigateToAdmin();
    }

    @And("clicks the Add User button")
    public void clicksTheAddUserButton() {
        adminPage.clickAddButton();
    }

    @And("fills out user details with role {string}, employee name {string}, status {string}, username {string}, and password {string}")
    public void fillsOutUserDetailsWithRoleEmployeeNameStatusUsernameAndPassword(String role, String employeeName, String status, String username, String password) {
        adminPage.selectUserRole(role);
        adminPage.selectEmployeeName(employeeName);
        adminPage.selectStatus(status);
        adminPage.enterUsername(username);
        adminPage.enterPasswordAndConfirm(password);
    }

    @And("clicks the save configuration button")
    public void clicksTheSaveConfigurationButton() {
        adminPage.clickSave();
    }

    @Then("the user searches for username {string} in the system users table")
    public void theUserSearchesForUsernameInTheSystemUsersTable(String username) {
        adminPage.searchForCreatedUser(username);
    }

    @And("verifies that the user appears successfully in the data records row")
    public void verifiesThatTheUserAppearsSuccessfullyInTheDataRecordsRow() {
        boolean isFound = adminPage.isUserPresentInTable("RaviRayudu");
        Assertions.assertTrue(isFound, "Validation Error: The newly configured user 'RaviRayudu' was not found inside the System Users data rows!");
    }
}