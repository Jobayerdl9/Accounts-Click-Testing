import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegistrationTests {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set up ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    // Helper function to wait for page load
    private void waitForPageLoad() {
        try {
            Thread.sleep(5000); // Wait for 5 seconds for page load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1. TC006: Verify valid registration
    @Test
    public void testValidRegistration() {
        driver.get("URL_OF_REGISTRATION_PAGE");

        driver.findElement(By.xpath("XPATH_FOR_NAME_FIELD")).sendKeys("Test User");
        driver.findElement(By.xpath("XPATH_FOR_EMAIL_FIELD")).sendKeys("test@valid.com");
        driver.findElement(By.xpath("XPATH_FOR_PASSWORD_FIELD")).sendKeys("Test@1234");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button")).click();

        waitForPageLoad();

        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Registration failed, user not redirected to login page");
    }

    // 2. TC007: Verify duplicate email registration
    @Test
    public void testDuplicateEmailRegistration() {
        driver.get("URL_OF_REGISTRATION_PAGE");

        driver.findElement(By.xpath("XPATH_FOR_NAME_FIELD")).sendKeys("Test User");
        driver.findElement(By.xpath("XPATH_FOR_EMAIL_FIELD")).sendKeys("jobayerbhaijan");
        driver.findElement(By.xpath("XPATH_FOR_PASSWORD_FIELD")).sendKeys("Test@1234");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button")).click();

        waitForPageLoad();

        String errorMessage = driver.findElement(By.xpath("XPATH_FOR_ERROR_MESSAGE")).getText();
        Assert.assertTrue(errorMessage.contains("Email already in use"), "Error message for duplicate email not shown");
    }

    // 3. TC008: Verify weak password prompt
    @Test
    public void testWeakPasswordPrompt() {
        driver.get("URL_OF_REGISTRATION_PAGE");

        driver.findElement(By.xpath("XPATH_FOR_NAME_FIELD")).sendKeys("Test User");
        driver.findElement(By.xpath("XPATH_FOR_EMAIL_FIELD")).sendKeys("test@valid.com");
        driver.findElement(By.xpath("XPATH_FOR_PASSWORD_FIELD")).sendKeys("12345");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button")).click();

        waitForPageLoad();

        String errorMessage = driver.findElement(By.xpath("XPATH_FOR_ERROR_MESSAGE")).getText();
        Assert.assertTrue(errorMessage.contains("Password must contain at least 8 characters, one uppercase letter, and one number"), "Weak password error message not shown");
    }

    // 4. TC009: Verify incomplete form submission
    @Test
    public void testIncompleteFormSubmission() {
        driver.get("URL_OF_REGISTRATION_PAGE");

        driver.findElement(By.xpath("XPATH_FOR_NAME_FIELD")).sendKeys("");
        driver.findElement(By.xpath("XPATH_FOR_EMAIL_FIELD")).sendKeys("");
        driver.findElement(By.xpath("XPATH_FOR_PASSWORD_FIELD")).sendKeys("");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button")).click();

        waitForPageLoad();

        String errorMessage = driver.findElement(By.xpath("XPATH_FOR_ERROR_MESSAGE")).getText();
        Assert.assertTrue(errorMessage.contains("This field is required"), "Validation error for missing fields not shown");
    }

    // 5. TC010: Verify invalid email format rejection
    @Test
    public void testInvalidEmailFormat() {
        driver.get("URL_OF_REGISTRATION_PAGE");

        driver.findElement(By.xpath("XPATH_FOR_NAME_FIELD")).sendKeys("Test User");
        driver.findElement(By.xpath("XPATH_FOR_EMAIL_FIELD")).sendKeys("invalid-email");
        driver.findElement(By.xpath("XPATH_FOR_PASSWORD_FIELD")).sendKeys("Test@1234");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button")).click();

        waitForPageLoad();

        String errorMessage = driver.findElement(By.xpath("XPATH_FOR_ERROR_MESSAGE")).getText();
        Assert.assertTrue(errorMessage.contains("Invalid email format"), "Invalid email format error message not shown");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after tests
        if (driver != null) {
            driver.quit();
        }
    }
}
