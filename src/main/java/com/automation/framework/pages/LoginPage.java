package com.automation.framework.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class LoginPage {
    WebDriver driver;

        // Locators
        By usernameField = By.id("user-name");
        By passwordField = By.id("password");
        By loginButton = By.id("login-button");
        By productsTitle = By.xpath("//span[text()='Products']");

        // Constructor
        public LoginPage(WebDriver driver) {
            this.driver = driver;
        }

        // Enter username
        public void enterUsername(String username) {
            driver.findElement(usernameField).sendKeys(username);
        }

        // Enter password
        public void enterPassword(String password) {
            driver.findElement(passwordField).sendKeys(password);
        }

        // Click Login
        public void clickLogin() {
            driver.findElement(loginButton).click();
        }

        // Verify successful login
        public boolean isProductsPageDisplayed() {
            return driver.findElement(productsTitle).isDisplayed();
        }
    }

