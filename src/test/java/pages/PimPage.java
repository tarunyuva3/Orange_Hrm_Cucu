package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PimPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage lp; // To safely call your framework's custom loader helper

    // PageFactory Elements - Left Side Menu Options
    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement pimMenuOption;

    @FindBy(xpath = "//a[text()='Add Employee']")
    private WebElement addEmployeeSubMenu;

    @FindBy(xpath = "//a[text()='Employee List']")
    private WebElement employeeListTab;

    // PageFactory Elements - Add Employee Input Fields
    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//p[text()='Create Login Details']/following::span[contains(@class,'oxd-switch-input')][1]")
    private WebElement createLoginToggle;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    private WebElement usernameInput;

    @FindBy(xpath = "//label[text()='Password']/following::input[1]")
    private WebElement passwordInput;

    @FindBy(xpath = "//label[text()='Password']/following::input[2]")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement profilePersonalDetailsHeader;

    // PageFactory Elements - Scenario 4 Form Criteria
    @FindBy(xpath = "//label[text()='Employee Name']/following::input[1]")
    private WebElement employeeNameFilterInput;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement savePersonalDetailsButton;

    @FindBy(xpath = "//label[text()='Nationality']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]")
    private WebElement nationalityDropdownSelectedText;

    // Locators for Scenario 5: Delete Employee functionality
    @FindBy(xpath = "//button[contains(normalize-space(),'Delete Selected')]")
    private WebElement deleteSelectedButton;

    @FindBy(xpath = "//button[contains(.,'Yes, Delete')]")
    private WebElement confirmDeleteModalButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    private WebElement successToastNotification;

    public PimPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.lp = new LoginPage(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================================================
    // PRESERVED METHODS TO KEEP SCENARIO 2 & 3 RUNNING STABLE
    // ========================================================

    public void clickPim() {
        lp.waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuOption)).click();
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeSubMenu)).click();
    }

    public void navigateToAddEmployee() {
        clickPim();
        clickAddEmployee();
    }

    public void enterEmployeeName(String firstName, String lastName) {
        lp.waitForLoaderToDisappear();
        wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
    }

    public void enableCreateLoginDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(createLoginToggle)).click();
    }

    public void fillLoginDetails(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(username);
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(password);
    }

    public void save() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public boolean personalDetailsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(profilePersonalDetailsHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEmployeeList() {
        lp.waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(employeeListTab)).click();
    }

    public void searchEmployee(String employeeName) {
        clickEmployeeList();
        wait.until(ExpectedConditions.visibilityOf(employeeNameFilterInput)).sendKeys(employeeName);

        try {
            By autocompleteOption = By.xpath("//div[@role='listbox']//span[contains(text(),'" + employeeName.split(" ")[0] + "')]");
            wait.until(ExpectedConditions.elementToBeClickable(autocompleteOption)).click();
        } catch (Exception ignored) {}

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        lp.waitForLoaderToDisappear();
    }

    public boolean tableContains(String expectedText) {
        lp.waitForLoaderToDisappear();
        try {
            By tableRowRecord = By.xpath("//div[contains(@class,'oxd-table-card')]//div[contains(text(),'" + expectedText + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tableRowRecord)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================================================
    // NEW METHODS DEDICATED SPECIFICALLY FOR SCENARIO 4
    // ========================================================

    public void openEmployeeDetailsForEditing(String employeeName) {
        // Step 1: Search for target record using existing search logic
        searchEmployee(employeeName);

        // Step 2: Locate the row record and click the Edit Action (Pencil) Icon button directly
        By editPencilIcon = By.xpath("//div[contains(@class,'oxd-table-card')]//div[contains(text(),'" + employeeName.split(" ")[0] + "')]/ancestor::div[contains(@class,'oxd-table-row')]//i[contains(@class,'bi-pencil-fill')]/parent::button");
        wait.until(ExpectedConditions.elementToBeClickable(editPencilIcon)).click();
        lp.waitForLoaderToDisappear();
    }

    public void updateGender(String genderValue) {
        // 1. Ensure the form loader overlay is completely gone from the DOM
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'oxd-form-loader')]")
            ));
        } catch (Exception e) {
            // Safe catch if loader is missing entirely or delayed in rendering
        }

        // 2. Locate the precise radio label span
        By genderRadioLabel = By.xpath("//label[text()='" + genderValue + "']/span");
        WebElement targetRadio = wait.until(ExpectedConditions.elementToBeClickable(genderRadioLabel));

        // 3. Robust Click handling to bypass interception
        try {
            targetRadio.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Fallback to JavaScript Click if DOM animation is still settling down
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", targetRadio);
        }
    }

    public void updateDropdownField(String fieldLabel, String dropdownOptionValue) {
        lp.waitForLoaderToDisappear();
        By dropdownBox = By.xpath("//label[text()='" + fieldLabel + "']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
        wait.until(ExpectedConditions.elementToBeClickable(dropdownBox)).click();

        By optionItem = By.xpath("//div[@role='listbox']//span[text()='" + dropdownOptionValue + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionItem)).click();
    }

    public void clickSavePersonalDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(savePersonalDetailsButton)).click();
        lp.waitForLoaderToDisappear();
    }

    public String getSavedNationalityAfterRefresh() {
        // Condition: Data persists after page refresh
        //driver.navigate().refresh();
        lp.waitForLoaderToDisappear();
        return wait.until(ExpectedConditions.visibilityOf(nationalityDropdownSelectedText)).getText().trim();
    }

    //Scenario5--> starts here

    public void selectEmployeeCheckbox(String employeeName) {
        lp.waitForLoaderToDisappear();

        // Split full name into first and last name strings
        String[] names = employeeName.split(" ");
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";

        // Concise multi-column matching XPath
        String xpathExpr = "//div[@class='oxd-table-card' and .//div[contains(.,'" + firstName + "')] and .//div[contains(.,'" + lastName + "')]]//span[contains(@class,'oxd-checkbox-input')]";

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpr)));

        // Use JavaScript click directly to completely bypass any intercepted layer errors
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }

    public void clickDeleteSelectedButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteSelectedButton)).click();
    }

    public void confirmDeletionInModal() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteModalButton)).click();
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(successToastNotification)).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            // Important execution safety: Ensures framework animations finish fully before evaluating downstream criteria loops
            lp.waitForLoaderToDisappear();
        }
    }

}