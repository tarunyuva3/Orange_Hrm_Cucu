package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import pages.LoginPage;
import pages.PimPage;

public class Scenario11 {

    private final WebDriver driver = base.DriverFactory.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    private final LoginPage loginPage = new LoginPage(driver);
    private final PimPage pimPage = new PimPage(driver);
    private final Actions actions = new Actions(driver);

    @When("the user navigates to the PIM employee list table")
    public void navigateToPimEmployeeTable() {
        pimPage.clickPim();
        pimPage.clickEmployeeList();
    }

    @And("sorts the employee records by Id column in ascending order")
    public void sortEmployeeRecordsById() {
        pimPage.sortIdColumnAscending();
    }

    @And("cycles through all available pagination pages with a safe load delay")
    public void cycleThroughAllPaginationPages() {
        int totalPages = pimPage.getPaginationPageCount();
        System.out.println("Total pagination elements discovered in table grid: " + totalPages);

        // Loops through each page button index directly
        for (int i = 0; i < totalPages; i++) {
            pimPage.clickPageByIndex(i);
            System.out.println("Navigated over onto PIM records list page index: " + (i + 1));

            try {
                actions.scrollByAmount(0, 400).perform();
            } catch (Exception ignored) {
                // Fallback catch if scrolling boundaries fluctuate
            }

            // Standard execution hold using Thread.sleep matching your required 2 seconds window
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Then("the user should navigate back to the first page successfully")
    public void returnToFirstPaginationPage() {
        // Automatically paths back onto page 1 if multiple pages exist
        if (pimPage.getPaginationPageCount() > 1) {
            System.out.println("Final pagination index processed. Direct routing reset focus back onto Page 1...");
            pimPage.clickPageByIndex(0);

            try {
                actions.scrollByAmount(0, 400).perform();
            } catch (Exception ignored) {
                // Fallback catch if scrolling boundaries fluctuate
            }

            // Direct 2-second synchronization pause following the page reset
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Skipping pagination loop cycle; table structure consists of only a single page.");
        }
    }
}