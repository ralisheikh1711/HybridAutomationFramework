package stepdefinitions;
import com.automation.framework.pages.CartPage;
import com.automation.framework.pages.SearchPage;
import hooks.Hooks;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CartSteps {

    private WebDriver driver;
    private SearchPage searchPage;
    private CartPage cartPage;

    // No driver initialization in the constructor.
    public CartSteps() {
    }

    @Before(order = 1)
    public void initializePages() {
        this.driver = Hooks.getDriver();
        this.searchPage = new SearchPage(driver);
        this.cartPage = new CartPage(driver);
    }


    @Given("the user opens the ModernStore home page")
        public void openHomePage() {
            searchPage.openHomePage();
        }

        @When("the user searches for {string}")
        public void searchForProduct(String productName) {
            searchPage.searchProduct(productName);
        }

        @When("the user selects a jeans product")
        public void selectJeansProduct() {
            assertTrue(
                    "No jeans product was displayed in search results",
                    searchPage.areJeansProductsDisplayed()
            );

            searchPage.selectJeansProduct();
        }

        @When("the user adds the product to the cart")
        public void addProductToCart() {
            searchPage.clickAddToCart();
        }

        @When("the user opens the cart")
        public void openCart() {
            cartPage.openCart();
        }

        @Then("the cart page should be displayed")
        public void verifyCartPage() {
            assertTrue(
                    "Cart page was not displayed",
                    cartPage.isCartPageDisplayed()
            );
        }

        @Then("the product should be displayed in the cart")
        public void verifyProductInCart() {
            assertTrue(
                    "Product was not found in the cart",
                    cartPage.isProductDisplayedInCart()
            );
        }

        @When("the user changes the product quantity to {int}")
        public void changeQuantity(int quantity) {
            cartPage.setQuantity(quantity);
        }

        @When("the user updates the cart")
        public void updateCart() {
            cartPage.updateCart();
        }

    @Then("the product quantity should be {int}")
    public void verifyQuantity(int expectedQuantity) {

        String actualQuantity = cartPage.getQuantity();

        System.out.println("Expected quantity: " + expectedQuantity);
        System.out.println("Actual quantity: " + actualQuantity);

        assertEquals(
                actualQuantity,
                String.valueOf(expectedQuantity),
                "Cart quantity did not match"
        );
    }

        @When("the user applies coupon {string}")
        public void applyCoupon(String couponCode) {
            cartPage.applyCoupon(couponCode);
        }

        @Then("the coupon should be applied successfully")
        public void verifyCoupon() {
            assertTrue(
                    "Coupon was not applied. Store message: "
                            + cartPage.getCouponErrorMessage(),
                    cartPage.isCouponApplied()
            );
        }

        @When("the user proceeds to checkout")
        public void proceedToCheckout() {
            cartPage.proceedToCheckout();
        }

        @Then("the checkout page should be displayed")
        public void verifyCheckoutPage() {
            assertTrue(
                    "Checkout page was not displayed",
                    cartPage.isCheckoutPageDisplayed()
            );
        }
    }

