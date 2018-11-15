package com.testAuto;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class issueTests extends TestBase {
    static IssuePage steps;

    @BeforeTest
    public static void initBrowser(){
        steps = new IssuePage(browser);
    }

//    @Test(dependsOnMethods = "testCreateTask", groups = {"TaskPage"})
    @Test(dependsOnGroups = "createTask", groups = {"TaskPage"})

    public void testOpenTask() {

        browser.get(MainPage.newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameTask));
    }

    @Test(dependsOnMethods = "testOpenTask", groups = {"TaskPage"})
//    @Test(dependsOnGroups = "createTask", groups = {"TaskPage"})

    public void uploadAttach() throws InterruptedException {

        steps.uploadAttach();
        Thread.sleep(3000);

        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(dependsOnMethods = "uploadAttach", groups = {"TaskPage"})
//    @Test(dependsOnGroups = "createTask", groups = {"TaskPage"})

    public void downloadAttach() throws InterruptedException {

        steps.downloadAttach();
        Assert.assertTrue(IssuePage.f.exists());
    }

    @Test(dependsOnMethods = "downloadAttach", groups = {"TaskPage"})
//    @Test(dependsOnGroups = "createTask", groups = {"TaskPage"})

    public void deleteTest() {
        steps.f.delete();

        Assert.assertFalse(IssuePage.f.exists());
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
