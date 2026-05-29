package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    private WebElement dashboardHeader;

    @FindBy(xpath = "//span[contains(@class,'oxd-userdropdown-tab')]")
    private WebElement profileDropdown;

    // Left Side Navigation Menu Item Link
    @FindBy(xpath = "//span[text()='Dashboard']")
    private WebElement dashboardMenuLink;

    // Orange circular stopwatch widget action button inside 'Time at Work' card
    @FindBy(xpath = "//div[contains(@class,'orangehrm-attendance-card-bar')]//button[contains(@class,'oxd-icon-button')]")
    private WebElement clockShortcutButton;

    // Dynamic text status card subtitle location (Punched In / Punched Out text node)
    @FindBy(xpath = "//p[contains(@class,'orangehrm-attendance-card-state')]")
    private WebElement attendanceStatusStateLabel;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutMenuOption;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public boolean isDashboardDisplayed() {
        try {
            boolean isHeaderVisible = wait.until(ExpectedConditions.visibilityOf(dashboardHeader)).isDisplayed();
            boolean isDropdownVisible = wait.until(ExpectedConditions.visibilityOf(profileDropdown)).isDisplayed();
            return isHeaderVisible && isDropdownVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToDashboardMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(dashboardMenuLink)).click();
    }

    public void clickClockShortcutWidget() {
        wait.until(ExpectedConditions.elementToBeClickable(clockShortcutButton)).click();
    }

    public boolean isCurrentStatusPunchedOut() {
        try {
            String statusText = wait.until(ExpectedConditions.visibilityOf(attendanceStatusStateLabel)).getText();
            return statusText.trim().equalsIgnoreCase("Punched Out");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickProfileDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(profileDropdown)).click();
    }

    public void clickLogoutOption() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutMenuOption)).click();
    }
}