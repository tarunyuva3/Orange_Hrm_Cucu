package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AdminPage {
    private static final Logger log = LogManager.getLogger(AdminPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage lp;

    // Left Side Menu Link
    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuOption;

    // Records Section Add Button
    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addUserButton;

    // Add User Form Dropdowns
    @FindBy(xpath = "//label[text()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement userRoleDropdown;

    @FindBy(xpath = "//label[text()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement statusDropdown;

    // Add User Form Input Fields
    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement usernameInput;

    @FindBy(xpath = "//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//label[text()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input[@type='password']")
    private WebElement confirmPasswordInput;

    // Action Buttons
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-form-actions')]//button[@type='submit']")
    private WebElement searchButton;

    // ========================================================
    // SCENARIO 10: JOB TITLES FIELD LOCATORS
    // ========================================================
    @FindBy(xpath = "//span[normalize-space()='Job']")
    private WebElement jobDropdownMenu;

    @FindBy(xpath = "//a[text()='Job Titles']")
    private WebElement jobTitlesSubMenuOption;

    @FindBy(xpath = "//label[text()='Job Title']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement jobTitleInputField;

    @FindBy(xpath = "//label[text()='Job Description']/ancestor::div[contains(@class,'oxd-input-group')]//textarea")
    private WebElement jobDescriptionTextArea;

    @FindBy(xpath = "//label[text()='Note']/ancestor::div[contains(@class,'oxd-input-group')]//textarea")
    private WebElement jobNoteTextArea;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadHiddenInput;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    private WebElement successToastNotification;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.lp = new LoginPage(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuOption)).click();
        lp.waitForLoaderToDisappear();
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
        lp.waitForLoaderToDisappear();
    }

    public void selectUserRole(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        WebElement roleSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='listbox']//span[text()='" + role + "']")));
        roleSelection.click();
    }

    public void selectEmployeeName(String employeeName) {
        WebElement empInput = wait.until(ExpectedConditions.elementToBeClickable(employeeNameInput));
        empInput.click();

        // Clean any existing text before searching
        empInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        // Type the name to trigger the suggestions overlay
        empInput.sendKeys(employeeName);

        // Wait for the drop-down list box panel to be present on screen
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='listbox']")));

        // Matches the exact design used in PimPage.java search (Splitting by space for the first token name keyword)
        String firstToken = employeeName.split(" ")[0];
        By autocompleteOption = By.xpath("//div[@role='listbox']//span[contains(text(),'" + firstToken + "')]");

        try {
            WebElement optionItem = wait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
            optionItem.click();
        } catch (Exception fallback) {
            // Safety Backup: If span selection fails, look up the option element row wrapper containing the match segment
            WebElement fallbackItem = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='listbox']//div[@role='option' and contains(.,'" + firstToken + "')]")));
            fallbackItem.click();
        }
    }

    public void selectStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        WebElement statusSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='listbox']//span[text()='" + status + "']")));
        statusSelection.click();
    }

    public void enterUsername(String username) {
        WebElement userField = wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        userField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        userField.sendKeys(username);
    }

    public void enterPasswordAndConfirm(String password) {
        WebElement passField = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        passField.sendKeys(password);

        WebElement confirmField = wait.until(ExpectedConditions.elementToBeClickable(confirmPasswordInput));
        confirmField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        confirmField.sendKeys(password);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        lp.waitForLoaderToDisappear();
    }

    public void searchForCreatedUser(String username) {
        WebElement searchUserField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'oxd-table-filter')]//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input")));
        searchUserField.clear();
        searchUserField.sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        lp.waitForLoaderToDisappear();
    }

    public boolean isUserPresentInTable(String username) {
        try {
            String cellXpath = "//div[@class='oxd-table-body']//div[@role='row']//div[text()='" + username + "']";
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cellXpath))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================================================
    // SCENARIO 10: NEW JOB TITLES ACTION METHODS
    // ========================================================
    public void navigateToJobTitlesMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuOption)).click();
        lp.waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(jobDropdownMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(jobTitlesSubMenuOption)).click();
        lp.waitForLoaderToDisappear();
    }

    public void enterJobTitleDetails(String title, String description, String note) {
        wait.until(ExpectedConditions.visibilityOf(jobTitleInputField)).sendKeys(title);
        jobDescriptionTextArea.sendKeys(description);
        jobNoteTextArea.sendKeys(note);
    }

    public void uploadJobSpecificationFile(String path) {
        fileUploadHiddenInput.sendKeys(path);
    }

    public boolean isSuccessToastDisplayed() {
        try {
            WebElement toast = wait.until(ExpectedConditions.visibilityOf(successToastNotification));
            String toastText = toast.getText().toLowerCase();
            return toastText.contains("success") || toastText.contains("saved");
        } catch (Exception e) {
            return false;
        } finally {
            lp.waitForLoaderToDisappear();
        }
    }

    public boolean isJobTitleAndDescriptionPresentInGrid(String expectedTitle, String expectedDesc) {
        lp.waitForLoaderToDisappear();
        try {
            String dynamicRowExpression = "//div[@role='row' and .//div[text()='" + expectedTitle + "'] and .//div[contains(.,'" + expectedDesc + "')]]";
            WebElement verifiedRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dynamicRowExpression)));

            if (verifiedRow.isDisplayed()) {
                log.info("[Validation Success] Successfully located target Job Title: '{}' with Description: '{}' inside the records grid.", expectedTitle, expectedDesc);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("[ERROR] Failed to locate Job Title record row matching Title: '{}' and Description: '{}'. Error: {}", expectedTitle, expectedDesc, e.getMessage());
            return false;
        }
    }
}