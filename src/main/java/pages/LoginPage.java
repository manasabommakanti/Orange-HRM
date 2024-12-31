package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;

    // Locators
    private By usernameField = By.xpath("//input[@name=\"username\"]");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[contains(@class, 'orangehrm-login-button')]");
    private By logoutDropdown = By.xpath("//div[contains(@class, 'oxd-topbar-header-userarea')]");
    private By logoutLink = By.xpath("//li/a[normalize-space(text())='Logout']");
  


    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
        usernameElement.clear();
        usernameElement.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement logoutDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(logoutDropdown));
        logoutDropdownElement.click();

        WebElement logoutLinkElement = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLinkElement.click();
    }
}
