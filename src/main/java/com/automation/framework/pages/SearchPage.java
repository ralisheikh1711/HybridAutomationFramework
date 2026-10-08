package com.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage {
        private WebDriver driver;
        private WebDriverWait wait;

        // URL
        private final String HOME_URL =
                "https://modern-store.competethemes.com/";

        // Search box
        private By searchBox = By.cssSelector("input[type='search']");

        // Search button
        private By searchButton = By.cssSelector("button[type='submit'], input[type='submit']");

        // Product links/cards
        private By productLinks = By.cssSelector(".woocommerce-loop-product__link");

        // Add to Cart button
        private By addToCartButton = By.cssSelector("button.single_add_to_cart_button, " + ".single_add_to_cart_button"
        );

        // Cart
        private By cartIcon = By.cssSelector(".cart-contents, a[href*='cart']");

        // Cart item
        private By cartItems = By.cssSelector(".woocommerce-cart-form__cart-item");


        public SearchPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }


        public void openHomePage() {
            driver.get(HOME_URL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        }


        public void searchProduct(String productName) {

            WebElement search = wait.until(
                    ExpectedConditions.elementToBeClickable(searchBox)
            );

            search.clear();
            search.sendKeys(productName);

            WebElement button = wait.until(
                    ExpectedConditions.elementToBeClickable(searchButton)
            );

            button.click();
        }


        public boolean areJeansProductsDisplayed() {

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    productLinks
            ));

            List<WebElement> products =
                    driver.findElements(productLinks);

            for (WebElement product : products) {

                String productName =
                        product.getText().toLowerCase();

                if (productName.contains("jean")) {
                    return true;
                }
            }

            return false;
        }


        public void selectJeansProduct() {

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    productLinks
            ));

            List<WebElement> products =
                    driver.findElements(productLinks);

            for (WebElement product : products) {

                if (product.getText()
                        .toLowerCase()
                        .contains("jean")) {

                    wait.until(
                            ExpectedConditions.elementToBeClickable(product)
                    ).click();

                    return;
                }
            }

            throw new RuntimeException(
                    "No jeans product was found"
            );
        }


        public void clickAddToCart() {

            WebElement addToCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            addToCartButton
                    )
            );

            addToCart.click();
        }


        public boolean isProductAddedToCart() {

            // WooCommerce normally updates the cart counter
            // after adding a product.

            try {
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                cartIcon
                        )
                );

                return true;

            } catch (Exception e) {
                return false;
            }
        }


        public void openCart() {

            WebElement cart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            cartIcon
                    )
            );

            cart.click();
        }


        public boolean isCartItemDisplayed() {

            try {
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                cartItems
                        )
                );

                return true;

            } catch (Exception e) {
                return false;
            }
        }
    }

