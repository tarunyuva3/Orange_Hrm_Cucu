package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import pages.LoginPage;

public class Scenario14 {

    private final WebDriver driver = base.DriverFactory.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    private final LoginPage loginPage = new LoginPage(driver); // Reusing your framework's loader sync

    @When("the user clicks the {string} module link in the sidebar menu")
    public void theUserClicksTheModuleLinkInTheSidebarMenu(String moduleName) {
        loginPage.waitForLoaderToDisappear();
        By sidebarOption = By.xpath("//span[contains(@class,'oxd-main-menu-item--name') and text()='" + moduleName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(sidebarOption)).click();
    }

    @Then("the Admin page should load successfully with the updated URL containing {string}")
    public void verifyAdminPageRouting(String expectedUrlSnippet) {
        loginPage.waitForLoaderToDisappear();
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrlSnippet),
                "Validation Error: Admin page URL did not update correctly!");

        boolean isHeaderVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Admin']"))).isDisplayed();
        Assertions.assertTrue(isHeaderVisible, "Validation Error: Admin module header title is missing!");
    }

    @Then("the PIM page should load successfully with the updated URL containing {string}")
    public void verifyPimPageRouting(String expectedUrlSnippet) {
        loginPage.waitForLoaderToDisappear();
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrlSnippet),
                "Validation Error: PIM page URL did not update correctly!");

        boolean isHeaderVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='PIM']"))).isDisplayed();
        Assertions.assertTrue(isHeaderVisible, "Validation Error: PIM module header title is missing!");
    }

    @Then("the Leave page should load successfully with the updated URL containing {string}")
    public void verifyLeavePageRouting(String expectedUrlSnippet) {
        loginPage.waitForLoaderToDisappear();
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrlSnippet),
                "Validation Error: Leave page URL did not update correctly!");

        boolean isHeaderVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Leave']"))).isDisplayed();
        Assertions.assertTrue(isHeaderVisible, "Validation Error: Leave module header title is missing!");
    }

    @Then("the Time page should load successfully with the updated URL containing {string}")
    public void verifyTimePageRouting(String expectedUrlSnippet) {
        loginPage.waitForLoaderToDisappear();
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrlSnippet),
                "Validation Error: Time page URL did not update correctly!");

        boolean isHeaderVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Time']"))).isDisplayed();
        Assertions.assertTrue(isHeaderVisible, "Validation Error: Time module header title is missing!");
    }
}