package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.WaitHelper;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Locators
    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(id = "password")
    WebElement passwordField;

    @FindBy(id = "submit")
    WebElement loginButton;

    @FindBy(xpath = "//h1[contains(text(),'Logged In Successfully')]")
    WebElement successMessage;

    @FindBy(id = "error")  // Error message element (for invalid login)
    WebElement errorMessage;

    // ✅ Actions
    public void enterUsername(String username) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", usernameField);
        WaitHelper.waitForElementVisible(driver, usernameField, 10);
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passwordField);
        WaitHelper.waitForElementVisible(driver, passwordField, 10);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    // ✅ Validation Methods

    // Checks if login success message appears
    public boolean isLoginSuccessful() {
        try {
            WebElement successMsg = driver.findElement(By.xpath("//h1[text()='Logged In Successfully']"));
            return successMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // Checks if error message appears for invalid credentials
    public boolean isErrorDisplayed() {
        try {
            WebElement errorMsg = driver.findElement(By.id("error"));
            return errorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    }

