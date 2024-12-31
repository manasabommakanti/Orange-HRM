package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    WebDriver driver;

    // Locators
    private By menuOptions = By.cssSelector(".oxd-main-menu-item");
    private By adminMenu = By.xpath("//span[text()='Admin']");
    private By usernameSearchField = By.xpath("//form[@class='oxd-form']//input[1]");
    private By roleDropdown = By.xpath("//label[text()='User Role']/following::div[1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[1]");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resultRows = By.xpath("//div[@class='oxd-table-body']/div");

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public int getMenuCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuOptions));
        return options.size();
    }


    public void clickAdminMenu() {
        driver.findElement(adminMenu).click();
    }

    public void searchByUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameElement = wait.until(driver -> driver.findElement(usernameSearchField));
        usernameElement.clear();
        usernameElement.sendKeys(username);
        driver.findElement(searchButton).click();
    }

    public void searchByRole(String role) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(roleDropdown));
        dropdown.click();
        System.out.println("Role dropdown opened successfully.");

        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='option']")));

        System.out.println("Available options in the dropdown:");
        
        for (WebElement option : options) {
            String optionText = option.getText();
            System.out.println(optionText);

            if (optionText.equals(role)) {
                option.click();
                System.out.println("Clicked on option:" + role);
                break;
            }
        }
        
        driver.findElement(searchButton).click();
    }

    public void searchByStatus(String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(statusDropdown));
        dropdown.click();
        System.out.println("Status dropdown opened successfully.");
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='option']")));

        System.out.println("Available options in the dropdown:");
        
        for (WebElement option : options) {
            String optionText = option.getText();
            System.out.println(optionText);

            if (optionText.equals(status)) {
                option.click();
                System.out.println("Clicked on option:" + status);
                break;
            }
        }
        

        driver.findElement(searchButton).click();
    }

    public int getResultCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultRows));
        return results.size();
    }

    public void refreshPage() {
    	driver.navigate().refresh();
    }
    

}
