
package com.automation.framework.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // UPDATED: Store the quantity requested by the test
    private String expectedQuantity;

    // Cart page elements
    private final By cartItems =
            By.cssSelector(".woocommerce-cart-form__cart-item");

    private final By quantityInputs =
            By.cssSelector("input.qty");

    private final By updateCartButton =
            By.cssSelector("button[name='update_cart']");

    private final By couponInput =
            By.name("coupon_code");

    private final By applyCouponButton =
            By.cssSelector("button[name='apply_coupon']");

    private final By couponSuccessMessage =
            By.cssSelector(".woocommerce-message");

    private final By couponErrorMessage =
            By.cssSelector(".woocommerce-error");

    private final By checkoutButton =
            By.cssSelector("a.checkout-button");

    private final By cartNotice =
            By.cssSelector(".woocommerce-message");

    // UPDATED: WooCommerce cart update loading indicators
    private final By cartLoadingOverlay =
            By.cssSelector(
                    ".woocommerce-cart-form .blockUI, " +
                            ".woocommerce-cart-form .blockOverlay"
            );

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    public void openCart() {
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                ".cart-contents, a[href*='cart']"
                        )
                )
        );

        cartLink.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(
                        cartItems),
                ExpectedConditions.urlContains("cart")
        ));
    }

    public boolean isCartPageDisplayed() {
        return driver.getCurrentUrl().contains("cart")
                || !driver.findElements(cartItems).isEmpty();
    }

    public boolean isProductDisplayedInCart() {
        return !driver.findElements(cartItems).isEmpty();
    }

    // UPDATED METHOD
    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1");
        }

        List<WebElement> quantities = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        quantityInputs)
        );

        if (quantities.isEmpty()) {
            throw new IllegalStateException(
                    "No product quantity field was found");
        }

        // Remember the quantity expected after updating the cart
        this.expectedQuantity = String.valueOf(quantity);

        WebElement quantityInput = quantities.get(0);

        quantityInput.clear();
        quantityInput.sendKeys(expectedQuantity);

        // Confirm the input contains the requested value
        wait.until(
                ExpectedConditions.attributeToBe(
                        quantityInputs,
                        "value",
                        expectedQuantity
                )
        );
    }

    // UPDATED METHOD
    public void updateCart() {
        if (expectedQuantity == null) {
            throw new IllegalStateException(
                    "Call setQuantity() before updateCart()");
        }

        WebElement updateButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        updateCartButton)
        );

        updateButton.click();

        // Wait until WooCommerce finishes its cart update.
        // Some themes do not display a loading overlay,
        // so this condition is followed by a value check.
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    cartLoadingOverlay
            ));
        } catch (org.openqa.selenium.TimeoutException e) {
            throw new AssertionError(
                    "Cart loading overlay did not disappear.", e
            );
        }

        // Wait for the quantity input to reflect the requested value.
        wait.until(
                ExpectedConditions.attributeToBe(
                        quantityInputs,
                        "value",
                        expectedQuantity
                )
        );
    }

    // UPDATED METHOD
    public String getQuantity() {
        WebElement quantityInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        quantityInputs)
        );

        return quantityInput.getAttribute("value");
    }

    public void applyCoupon(String couponCode) {

        WebElement couponField = wait.until(
                ExpectedConditions.elementToBeClickable(couponInput)
        );

        couponField.clear();
        couponField.sendKeys(couponCode);

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement applyButton = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                applyCouponButton
                        )
                );

                applyButton.click();

                // Wait for WooCommerce to report the coupon result.
                wait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(
                                couponSuccessMessage
                        ),
                        ExpectedConditions.visibilityOfElementLocated(
                                couponErrorMessage
                        )
                ));

                return;

            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (attempt == 2) {
                    throw e;
                }
            }
        }
    }

    public boolean isCouponApplied() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(
                        couponSuccessMessage),
                ExpectedConditions.visibilityOfElementLocated(
                        couponErrorMessage)
        ));

        return driver.findElements(couponErrorMessage).stream()
                .noneMatch(WebElement::isDisplayed)
                && driver.findElements(couponSuccessMessage).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    public String getCouponErrorMessage() {
        List<WebElement> errors =
                driver.findElements(couponErrorMessage);

        if (errors.isEmpty()) {
            return "";
        }

        return errors.get(0).getText();
    }

    public void proceedToCheckout() {
        WebElement checkout = wait.until(
                ExpectedConditions.elementToBeClickable(
                        checkoutButton)
        );

        checkout.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout"),
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                "form.checkout, #customer_details"
                        )
                )
        ));
    }

    public boolean isCheckoutPageDisplayed() {
        return driver.getCurrentUrl().contains("checkout")
                || !driver.findElements(
                By.cssSelector(
                        "form.checkout, #customer_details"
                )
        ).isEmpty();
    }
}