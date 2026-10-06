package stepdefinitions;

import com.automation.framework.pages.HomePage;
import com.automation.framework.pages.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class HomeSteps {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;

    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
    }
    @Given("User is on the SauceDemo login page")
    public void userIsOnTheSauceDemoLoginPage() {

        String title = driver.getTitle();

        System.out.println("Page Title: " + title);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucedemo"),
                "User is not on SauceDemo login page"
        );
    }

    @When("User enters username {string}")
    public void userEntersUsername(String username) {

        loginPage.enterUsername(username);
    }

    @And("User enters password {string}")
    public void userEntersPassword(String password) {

        loginPage.enterPassword(password);
    }

    @And("User clicks on the Login button")
    public void userClicksOnTheLoginButton() {

        loginPage.clickLogin();
    }

    @Then("User should be successfully logged in")
    public void userShouldBeSuccessfullyLoggedIn() {

        Assert.assertTrue(
                loginPage.isProductsPageDisplayed(),
                "Login was not successful"
        );
    }



    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("testing");
        }
    }

}
