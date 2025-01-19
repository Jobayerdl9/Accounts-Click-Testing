import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginAutomationTest {

    public static void main(String[] args) {

        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10); // Explicit Wait

        try {
            // Navigate to Login Page
            driver.get("https://hachnat.stage-carelogix.de/sing-in");
            driver.manage().window().maximize();

            // Test Case 1: Valid Login
            System.out.println("Running Test Case: Valid Login");
            login(driver, "jobayerbhaijan", "Carelogix24!");
            WebElement dashboardElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dashboard-id")));
            System.out.println("Valid Login Test Passed: " + dashboardElement.isDisplayed());
            driver.navigate().refresh(); // Verify session persistence

            // Logout before next test
            logout(driver);

            // Test Case 2: Invalid Password
            System.out.println("Running Test Case: Invalid Password");
            login(driver, "jobayerbhaijan", "WrongPassword123!");
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Invalid password')]")));
            System.out.println("Invalid Password Test Passed: " + errorMessage.isDisplayed());

            // Test Case 3: Unregistered Email
            System.out.println("Running Test Case: Unregistered Email");
            login(driver, "unregistered@example.com", "SomePassword!");
            errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Account not found')]")));
            System.out.println("Unregistered Email Test Passed: " + errorMessage.isDisplayed());

            // Test Case 4: Empty Fields
            System.out.println("Running Test Case: Empty Fields");
            login(driver, "", "");
            errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Required fields cannot be empty')]")));
            System.out.println("Empty Fields Test Passed: " + errorMessage.isDisplayed());

        } catch (Exception e) {
            System.out.println("Test failed due to: " + e.getMessage());
        } finally {
            // Close Browser
            driver.quit();
        }
    }

    // Helper Method: Perform Login
    private static void login(WebDriver driver, String username, String password) {
        // Locate username, password fields and login button
        WebElement usernameField = driver.findElement(By.id("email")); // Replace with the actual ID for username field
        WebElement passwordField = driver.findElement(By.id("password")); // Replace with the actual ID for password field
        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/form/button"));

        // Enter credentials and click login
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();
    }

    // Helper Method: Perform Logout
    private static void logout(WebDriver driver) {
        WebElement logoutButton = driver.findElement(By.id("logout-id")); // Replace with the actual ID for logout button
        logoutButton.click();
    }
}
