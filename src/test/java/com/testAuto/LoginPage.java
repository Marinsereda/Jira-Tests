package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage  {
    private WebDriver browser;
    private  Code c;

    LoginPage(WebDriver browser) {
        this.browser = browser;
        this.c = new Code(browser);
        PageFactory.initElements(browser, this);
    }

    By usernameInput = By.cssSelector("#login-form-username");
    By passwordInput = By.cssSelector("#login-form-password");
    By summaryInput = By.cssSelector(".jira-dialog-content #summary");


    public void loginPass(boolean correctPass) throws InterruptedException {
        String pass = (correctPass? TestData.password: TestData.passwordWrong)+ "\n";

        browser.get(TestData.siteLink);
        c.findAndFill(usernameInput, TestData.login);
        Thread.sleep(500);
        c.findAndFill(passwordInput, pass );
    }









}
