package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.AdminPage;

public class Scenario10 {

    private final AdminPage adminPage = new AdminPage(base.DriverFactory.getDriver());

    @When("the user navigates to Admin Job module and selects Job Titles option")
    public void theUserNavigatesToAdminJobModuleAndSelectsJobTitlesOption() {
        adminPage.navigateToJobTitlesMenu();
    }

    @And("clicks the Add Job Title configuration button")
    public void clicksTheAddJobTitleConfigurationButton() {
        adminPage.clickAddButton();
    }

    @And("fills out job configuration details with title {string}, description {string}, note {string}, and specs file path {string}")
    public void fillsOutJobConfigurationDetails(String jobTitle, String jobDescription, String jobNote, String filePath) {
        adminPage.enterJobTitleDetails(jobTitle, jobDescription, jobNote);
        adminPage.uploadJobSpecificationFile(filePath);

        // Capture the file name text returned by our fixed page method
//        String uploadedName = adminPage.getAttachedFileName();
//
//        // Positive validation: Verifies that the string on the screen matches our expected file text
//        Assertions.assertTrue(uploadedName.contains("Scenario List"),
//                "Validation Failure: Expected to find 'Scenario List' but found '" + uploadedName + "' instead!");
    }

    @And("clicks the Save Job Title button")
    public void clicksTheSaveJobTitleButton() {
        adminPage.clickSave();
    }

    @Then("a success toast alert notification should appear validating the operation")
    public void aSuccessToastAlertNotificationShouldAppearValidatingTheOperation() {
        boolean toastVisible = adminPage.isSuccessToastDisplayed();
        Assertions.assertTrue(toastVisible, "Validation Error: The confirmation toast popup message text did not return an explicit success confirmation status!");
    }

    @And("the user should be redirected back onto the Job Titles grid list verifying that {string} and description {string} match accurately")
    public void theUserShouldBeRedirectedBackOntoTheJobTitlesGridListVerifyingRecord(String expectedTitle, String expectedDescription) {
        boolean isRecordVerified = adminPage.isJobTitleAndDescriptionPresentInGrid(expectedTitle, expectedDescription);
        Assertions.assertTrue(isRecordVerified, "Validation Error: The Job record row metrics matching '" + expectedTitle + "' were missing inside the grid list columns layout details context!");
    }
}