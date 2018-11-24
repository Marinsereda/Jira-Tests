package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public WebDriver browser;

    Helper(WebDriver currentBrowser) {
        browser = currentBrowser;
    }



    public WebElement findAndFill(By selector, String value) {
        WebElement element = browser.findElement(selector);
        element.sendKeys(value);
        return element;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }

//    public void adminUserCreate() throws InterruptedException {
//        Thread.sleep(1500);
//        browser.findElement(By.cssSelector(".aui-nav #system-admin-menu")).click();
//        browser.findElement(By.cssSelector("#admin_users_menu")).click();
//        browser.findElement(By.cssSelector("input#login-form-authenticatePassword")).sendKeys("forautotests\n");
//        browser.findElement(By.cssSelector("#create_user")).click();
//        browser.findElement(By.cssSelector("#user-create-email")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-fullname")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-username")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-submit")).click();
//
//    }



    }




