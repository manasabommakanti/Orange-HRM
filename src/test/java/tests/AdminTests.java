package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AdminPage;
import pages.LoginPage;

public class AdminTests {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);

        // Perform login before starting tests
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
    }

    @Test(priority = 1)
    public void testMenuCount() {
        int menuCount = adminPage.getMenuCount();
        System.out.println("Menu count: " + menuCount);
        Assert.assertEquals(menuCount, 12, "Menu count should be 12.");
        adminPage.clickAdminMenu();
    }

    @Test(priority = 2)
    public void testSearchByUsername() {
        adminPage.searchByUsername("Admin");
        int resultCount = adminPage.getResultCount();
        System.out.println("Total Records Found:" + resultCount);
        adminPage.refreshPage();
    }

    @Test(priority = 3)
    public void testSearchByRole() {
        adminPage.searchByRole("Admin");
        int resultCount = adminPage.getResultCount();
        System.out.println("Results found: " + resultCount);
        adminPage.refreshPage();
    }

    @Test(priority = 4)
    public void testSearchByStatus() {
        adminPage.searchByStatus("Enabled");
        int resultCount = adminPage.getResultCount();
        System.out.println("Results found: " + resultCount);
        adminPage.refreshPage();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
