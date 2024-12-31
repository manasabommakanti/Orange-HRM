package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ExcelReader;

import java.io.IOException;
import java.util.List;
import utils.ScreenshotUtil;

public class LoginTests {
    WebDriver driver;
    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Set up Extent Reports with ExtentSparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/LoginTestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        loginPage = new LoginPage(driver);
    }
    
    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedResult) throws IOException, InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Start a new Extent Report test
        test = extent.createTest("Login Test - User Name :  " + username + " - Password :" + password);

        // Perform login actions
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        // Capture a screenshot before clicking the login button
        String preSubmissionScreenshotPath = ScreenshotUtil.takeScreenshot(driver, "BeforeLogin_" + username);
        test.info("Captured screenshot before login submission.",
                MediaEntityBuilder.createScreenCaptureFromPath(preSubmissionScreenshotPath).build());

        // Click the login button
        loginPage.clickLogin();

        // Wait to ensure the page updates after login
        Thread.sleep(2000);

        // Capture a screenshot immediately after login attempt
        String postSubmissionScreenshotPath = ScreenshotUtil.takeScreenshot(driver, "AfterLogin_" + username);

        // Validate the result of the login attempt
        if (expectedResult.equals("Pass")) {
            try {
                // Validate login success by checking if the Dashboard is displayed
                boolean isDashboardDisplayed = driver.getCurrentUrl().contains("dashboard");
                Assert.assertTrue(isDashboardDisplayed, "Login failed for valid credentials.");
                
                test.pass("Login successful.",
                        MediaEntityBuilder.createScreenCaptureFromPath(preSubmissionScreenshotPath).build());

                Thread.sleep(2000);

                // Capture screenshot after logout
                String logoutScreenshotPath = ScreenshotUtil.takeScreenshot(driver, "Logout_" + username);
                test.pass("Captured screenshot before logout successful.", MediaEntityBuilder.createScreenCaptureFromPath(logoutScreenshotPath).build());

                // Perform logout action
                loginPage.logout();

            } catch (AssertionError e) {
                // Handle unexpected login failure
                test.fail("Login failed for valid credentials.",
                        MediaEntityBuilder.createScreenCaptureFromPath(postSubmissionScreenshotPath).build());
                throw e; // Ensure TestNG registers this as a failed test
            }
        } else {
            try {
                // Validate login failure by checking for the presence of an error message
                boolean isErrorMessageDisplayed = driver.getPageSource().contains("Invalid credentials");
                Assert.assertTrue(isErrorMessageDisplayed, "Login passed unexpectedly for invalid credentials.");

                test.fail("Login failed as expected.",
                        MediaEntityBuilder.createScreenCaptureFromPath(postSubmissionScreenshotPath).build());
            } catch (AssertionError e) {
                // Handle unexpected login success
                test.fail("Unexpected success for invalid credentials.",
                        MediaEntityBuilder.createScreenCaptureFromPath(postSubmissionScreenshotPath).build());
                throw e; // Ensure TestNG registers this as a failed test
            }
        }

        // Capture a final screenshot after test completion
        ScreenshotUtil.takeScreenshot(driver, "FinalState_" + username);
    }



    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        String filePath = "src/test/resources/LoginTestData.xlsx";
        List<String[]> data = ExcelReader.readExcel(filePath, "Sheet1");
        return data.toArray(new Object[0][0]);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
