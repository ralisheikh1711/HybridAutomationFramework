package com.automation.framework.driver;

import com.automation.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public final class DriverFactory {

    private DriverFactory() {

    }

    public static WebDriver createDriver() {

        String browser = ConfigReader.getBrowser();

        return switch (browser.toLowerCase()) {

            case "chrome" -> createChromeDriver();

            case "firefox" -> createFirefoxDriver();

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };
    }

    private static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {

        FirefoxOptions options = new FirefoxOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("-headless");
        }

        return new FirefoxDriver(options);
    }
}
