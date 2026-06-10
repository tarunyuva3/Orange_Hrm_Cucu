package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions; // Native JUnit 5 Assertions
import pages.PimPage;

public class Scenario4 {

    private final PimPage pimPage = new PimPage(base.DriverFactory.getDriver());

    @And("the user opens employee {string} and updates gender {string} nationality {string} and marital status {string}")
    public void theUserOpensEmployeeAndUpdatesGenderNationalityAndMaritalStatus(String employeeName, String gender, String nationality, String maritalStatus) {
        // 1. Search for record -> Click Pencil Edit Icon
        pimPage.clickPim();
        pimPage.openEmployeeDetailsForEditing(employeeName);

        // 2. Adjust target fields
        pimPage.updateGender(gender);
        pimPage.updateDropdownField("Nationality", nationality);
        pimPage.updateDropdownField("Marital Status", maritalStatus);

        // 3. Commit changes
        pimPage.clickSavePersonalDetails();
    }

    @Then("employee nationality should persist as {string}")
    public void employeeNationalityShouldPersistAs(String expectedNationality) {
        // 4. Reload page to verify persistence state criteria
        String actualNationality = pimPage.getSavedNationalityAfterRefresh();

        Assertions.assertEquals(expectedNationality, actualNationality, "The nationality details failed to persist inside application after page refresh sequence!");
    }
}