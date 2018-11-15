package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class loginTests extends TestBase {
    static LoginPage steps;

    @BeforeTest
    public static void initBrowser(){
        steps = new LoginPage(browser);
    }

    @Test(priority = -1, groups = {"login"})
    public void loginFailed() throws InterruptedException {
        steps.loginPass(false);
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
    }

    @Test(groups = {"login"})
    public void testLogin() throws InterruptedException {

        steps.loginPass(true);
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);
    }

    @AfterMethod
    public static void reportResults(ITestResult testResult) {
        String info= "";
        info += testResult.getMethod().getMethodName();
        info += ": ";
        info += testResult.isSuccess() ? "passed" : "failed";

        System.out.println(info);
    }


}
