package com.testAuto;

import org.openqa.selenium.By;

public class FunctionJira extends TestBase{
    public static void loginPass(boolean correctPass) throws InterruptedException {
        String pass = (correctPass? TestData.password: TestData.passwordWrong)+ "\n";

        browser.get(TestData.siteLink);
        c.findAndFill(By.cssSelector("#login-form-username"), TestData.login);
        Thread.sleep(500);
        c.findAndFill(By.cssSelector("#login-form-password"), pass );
    }





}
