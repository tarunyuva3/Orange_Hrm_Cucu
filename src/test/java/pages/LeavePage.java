package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeavePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage lp; // Framework custom loader validation helper

    // Navigation Menu Item Options
    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenuOption;

    @FindBy(xpath = "//a[text()='Assign Leave']")
    private WebElement assignLeaveSubMenu;

    // Leave Assignment Form Elements
    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeSearchInput;

    @FindBy(xpath = "//label[text()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement leaveTypeDropdown;

    @FindBy(xpath = "//label[text()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement fromDateInput;

    @FindBy(xpath = "//label[text()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement toDateInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement assignButton;

    // NEW LOCATORS: Targeting the explicit inner text structural details of the toast popup
    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    private WebElement toastContainer;

    @FindBy(xpath = "//p[contains(@class,'oxd-text--toast-title')]")
    private WebElement toastTitle;

    @FindBy(xpath = "//p[contains(@class,'oxd-text--toast-message')]")
    private WebElement toastMessage;

    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message') and text()='Required']")
    private WebElement fieldRequiredValidationError;

    @FindBy(xpath = "//div[contains(@class,'orangehrm-modal-footer')]//button[contains(@class,'oxd-button--secondary')]")
    private WebElement noleavesapprovebutton;

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.lp = new LoginPage(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToAssignLeave() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenuOption)).click();
        lp.waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(assignLeaveSubMenu)).click();
        lp.waitForLoaderToDisappear();
    }

    public void searchAndSelectEmployee(String employeeName) {
        if (employeeName == null || employeeName.isEmpty()) return;

        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(employeeSearchInput));
        searchField.click();
        searchField.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
        searchField.sendKeys(employeeName);

        String firstNameKeyword = employeeName.split(" ")[0];
        By autocompleteOption = By.xpath("//div[@role='listbox']//span[contains(text(),'" + firstNameKeyword + "')]");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(autocompleteOption)).click();
        } catch (Exception ignored) {}
    }

    public void selectLeaveType(String typeOption) {
        if (typeOption == null || typeOption.equals("-- Select --") || typeOption.isEmpty()) return;

        lp.waitForLoaderToDisappear();
        for (int retry = 0; retry < 3; retry++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDropdown)).click();
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (retry == 2) throw e;
            }
        }

        By optionLocator = By.xpath("//div[@role='listbox']//span[contains(text(),'" + typeOption + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    public void selectLeaveDates(String fromDate, String toDate) {
        if (fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) return;

        WebElement fromElem = wait.until(ExpectedConditions.visibilityOf(fromDateInput));
        fromElem.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
        fromElem.sendKeys(fromDate);

        WebElement toElem = wait.until(ExpectedConditions.visibilityOf(toDateInput));
        toElem.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
        toElem.sendKeys(toDate);
        toElem.sendKeys(org.openqa.selenium.Keys.TAB);
    }

    public void clickAssignButton() {
        wait.until(ExpectedConditions.elementToBeClickable(assignButton)).click();

        try {
            WebElement okBtn = wait.until(ExpectedConditions.visibilityOf(noleavesapprovebutton));
            try {
                wait.until(ExpectedConditions.elementToBeClickable(okBtn)).click();
            } catch (Exception intercepted) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
            }
        } catch (Exception e) {
            // Fallthrough bypass if confirmation dialogue does not trigger
        }
    }

    // NEW METHOD: Safely reads the actual message text displayed within the toast popup frame
    public String getToastMessageText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(toastContainer));
            return wait.until(ExpectedConditions.visibilityOf(toastMessage)).getText().trim();
        } catch (Exception e) {
            return "";
        } finally {
            lp.waitForLoaderToDisappear();
        }
    }

    // NEW METHOD: Safely reads the toast header title block (e.g., "Success" or "Warning")
    public String getToastTitleText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(toastContainer));
            return wait.until(ExpectedConditions.visibilityOf(toastTitle)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isRequiredFieldValidationErrorVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(fieldRequiredValidationError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}