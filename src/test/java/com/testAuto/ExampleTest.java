package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleTest extends BrowserFactory {

    public WebDriver browser;
    static FunctionJira steps;

    @BeforeClass
    public void init() {
        browser = BrowserFactory.getBrowser("ChromeWithOptions");
        steps = new FunctionJira(browser);
//        steps=PageFactory.initElements(browser, FunctionJira.class);
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        browser.manage().window().maximize();
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

    @Test(dependsOnMethods = "testLogin", priority = 3, groups = {"createTask"})

    public void testCreateTask() throws InterruptedException {
        Thread.sleep(3000);

        steps.createTask();

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
                FunctionJira.linkNewIssues.size() != 0);
    }

    @Test(dependsOnMethods = "testCreateTask", groups = {"TaskPage"})

    public void testOpenTask() {

        browser.get(FunctionJira.newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameTask));
    }

    @Test(dependsOnMethods = "testOpenTask", groups = {"TaskPage"})

    public void uploadAttach() throws InterruptedException {

        steps.uploadAttach();
        Thread.sleep(3000);

        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(dependsOnMethods = "uploadAttach", groups = {"TaskPage"})

    public void downloadAttach() throws InterruptedException {

        steps.downloadAttach();
        Assert.assertTrue(FunctionJira.f.exists());
    }

    @Test(dependsOnMethods = "downloadAttach", groups = {"TaskPage"})

    public void deleteTest() {
        steps.f.delete();

        Assert.assertFalse(FunctionJira.f.exists());
    }

    @Test(dependsOnMethods = "testLogin", priority = 10, groups = {"AdminMenu"})
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



