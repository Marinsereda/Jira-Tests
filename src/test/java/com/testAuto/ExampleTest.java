package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExampleTest extends TestBase{

    @Test(priority = -1)
    public static void loginFailed () throws InterruptedException {
        FunctionJira.loginPass(false);
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
        }

    @Test
    public static void testLogin() throws InterruptedException {

        FunctionJira.loginPass(true);
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);
    }

    @Test(dependsOnMethods = "testLogin",priority = 3)

    public static void testCreateTask() throws InterruptedException {
       Thread.sleep(3000);

        FunctionJira.createTask();

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
                FunctionJira.linkNewIssues.size() != 0);
    }

    @Test(dependsOnMethods = "testCreateTask")

    public static void testOpenTask() {

        browser.get(FunctionJira.newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameTask));
    }

    @Test(dependsOnMethods = "testOpenTask")

    public static void uploadAttach() throws InterruptedException {

        FunctionJira.uploadAttach();
        Thread.sleep(3000);

        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(dependsOnMethods = "uploadAttach")

    public static void downloadAttach() throws InterruptedException {

        FunctionJira.downloadAttach();
            Assert.assertTrue(FunctionJira.f.exists());
        }

        @Test (dependsOnMethods = "downloadAttach")

        public static void deleteTest()  {
            FunctionJira.f.delete();

            Assert.assertFalse(FunctionJira.f.exists());
        }

    @Test(dependsOnMethods = "testLogin", priority = 10)
    public static void createUser() throws InterruptedException {
        c.adminUserCreate();

        Assert.assertTrue(browser.findElements(By.cssSelector(".user-created-flag-single")).size()>0);
    }

    }




