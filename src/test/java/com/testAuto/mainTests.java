package com.testAuto;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class mainTests extends TestBase{
    static MainPage steps;

    @BeforeTest
    public static void initBrowser(){
        steps = new MainPage(browser);
    }

//    @Test(dependsOnMethods = "testLogin", priority = 3, groups = {"createTask"})
@Test(dependsOnGroups = "login", priority = 3, groups = {"createTask"})

    public void testCreateTask() throws InterruptedException {
        Thread.sleep(3000);

        steps.createTask();

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
                MainPage.linkNewIssues.size() != 0);
    }


//    @Test(dependsOnMethods = "testLogin", priority = 10, groups = {"AdminMenu"})
    @Test(dependsOnGroups = "login", priority = 10, groups = {"AdminMenu"})
    public void createUser() throws InterruptedException {
        steps.adminUserCreate();

        Assert.assertTrue(browser.findElements(By.cssSelector(".user-created-flag-single")).size() > 0);
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
