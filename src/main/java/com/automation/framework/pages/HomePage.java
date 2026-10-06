package com.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    //Locators
    By logoname= By.xpath("//div[@class='app_logo']");
    By hamburgermenu=By.xpath("//button[@id='react-burger-menu-btn']");
    By dynamiccatalog=By.xpath("//a[@id='dynamic_catalog_sidebar_link']");
    By lazyload=By.xpath("//a[@id='dynamic_catalog_lazy_load_link']");


    //constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
    }

    //verify the logo Name on the home page
    public boolean isLogoNameDisplayed() {
        return driver.findElement(logoname).isDisplayed();
    }
    //click on hamburger menu on the home page
    public void clickOnHamburgerMenu(){
        driver.findElement(hamburgermenu).click();
    }

    //click on the Dynamic Catalog
    public void clickOnDynamicCatalog(){
        driver.findElement(dynamiccatalog).click();
    }

    //click on the Lazy load
    public void clickOnLazyLoad(){
        driver.findElement(lazyload).click();
    }

}
