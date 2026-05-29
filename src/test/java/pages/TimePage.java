package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TimePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Time input field element location
    @FindBy(xpath = "//label[text()='Time']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement timeInputField;

    // Green dynamic submit action buttons (handles structural inner text variations cleanly)
    @FindBy(xpath = "//button[contains(@class,'oxd-button') and (normalize-space()='In' or normalize-space()='Out')]")
    private WebElement punchSubmitButton;

    // Toast notification tracker element
    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    private WebElement successToastNotification;

    // Dynamic application loading screen element locator
    private final By formLoaderLocator = By.className("oxd-form-loader");

    public TimePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Safely synchronizes state by ensuring the orange page spinner clears completely
     */
    public void waitForFormLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoaderLocator));
        } catch (Exception ignored) {
            // Fallthrough buffer adjustment if element lifecycle completed instantly
        }
    }

    public void updatePunchTimeField(String targetTimeStr) {
        // Wait for page transition spinner layout layer to disappear first
        waitForFormLoaderToDisappear();

        WebElement timeInput = wait.until(ExpectedConditions.visibilityOf(timeInputField));
        wait.until(ExpectedConditions.elementToBeClickable(timeInput));

        // Ensure focus path adjustments match input element bounds safely
        try {
            timeInput.click();
        } catch (Exception intercepted) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", timeInput);
        }

        // Clear existing characters safely using BACK_SPACE string lengths
        String currentText = timeInput.getAttribute("value");
        if (currentText != null) {
            for (int i = 0; i < currentText.length() + 2; i++) {
                timeInput.sendKeys(Keys.BACK_SPACE);
            }
        }

        // Secondary fallback clear selection
        timeInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        // Type value cleanly and dispatch formatting change updates via TAB key focus
        timeInput.sendKeys(targetTimeStr);
        timeInput.sendKeys(Keys.TAB);
    }

    public void clickPunchActionButton() {
        // Double check loader safety buffer before trying submit operations
        waitForFormLoaderToDisappear();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(punchSubmitButton));
        try {
            btn.click();
        } catch (Exception intercepted) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        // Synchronize state and let page updates finalize via the toast notification lifecycle bounds
        try {
            wait.until(ExpectedConditions.visibilityOf(successToastNotification));
            wait.until(ExpectedConditions.invisibilityOf(successToastNotification));
        } catch (Exception ignored) {
            // Fallthrough buffer adjustments if service requests match faster than refresh loops
        }
    }
}