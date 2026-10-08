package stepdefinitions;

import com.automation.framework.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;

public class SearchAndAddToCartStep {

        private WebDriver driver;
        private SearchPage searchPage;


        @Given("I am on the Modern Store home page")
        public void i_am_on_the_modern_store_home_page() {

            driver = new ChromeDriver();

            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            searchPage =
                    new SearchPage(driver);

            searchPage.openHomePage();
        }


        @When("I search for {string}")
        public void i_search_for(String productName) {

            searchPage.searchProduct(productName);
        }


        @Then("I should see jeans related products")
        public void i_should_see_jeans_related_products() {

            boolean productsDisplayed =
                    searchPage.areJeansProductsDisplayed();

            Assert.assertTrue(
                    productsDisplayed
            );

        }


        @When("I select a jeans product")
        public void i_select_a_jeans_product() {

            searchPage.selectJeansProduct();
        }


        @And("I click on the Add to Cart button")
        public void i_click_on_the_add_to_cart_button() {

            searchPage.clickAddToCart();
        }


        @Then("the product should be added to the cart")
        public void the_product_should_be_added_to_the_cart() {

            boolean added =
                    searchPage.isProductAddedToCart();

            Assert.assertTrue(
                    added);

        }
    }

