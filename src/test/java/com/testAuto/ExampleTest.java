package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExampleTest extends TestBase{
    static FunctionJira steps = PageFactory.initElements(browser, FunctionJira.class);


//    FunctionJira functionJira = new FunctionJira(browser);

    @Test(priority = -1)
    public  void loginFailed () throws InterruptedException, NullPointerException {
        steps.loginPass(false);
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
        }

    @Test
    public void testLogin() throws InterruptedException {

        steps.loginPass(true);
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);
    }

//    @Test(dependsOnMethods = "testLogin",priority = 3)
//
//    public void testCreateTask() throws InterruptedException {
//       Thread.sleep(3000);
//
//        functionJira.createTask();
//
//        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
//                FunctionJira.linkNewIssues.size() != 0);
//    }
//
//    @Test(dependsOnMethods = "testCreateTask")
//
//    public void testOpenTask() {
//
//        browser.get(FunctionJira.newIssuePath);
//        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameTask));
//    }
//
//    @Test(dependsOnMethods = "testOpenTask")
//
//    public void uploadAttach() throws InterruptedException {
//
//        functionJira.uploadAttach();
//        Thread.sleep(3000);
//
//        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
//    }
//
//    @Test(dependsOnMethods = "uploadAttach")
//
//    public void downloadAttach() throws InterruptedException {
//
//        functionJira.downloadAttach();
//            Assert.assertTrue(FunctionJira.f.exists());
//        }
//
//        @Test (dependsOnMethods = "downloadAttach")
//
//        public void deleteTest()  {
//            functionJira.f.delete();
//
//            Assert.assertFalse(FunctionJira.f.exists());
//        }
//
//    @Test(dependsOnMethods = "testLogin", priority = 10)
//    public void createUser() throws InterruptedException {
//        c.adminUserCreate();
//
//        Assert.assertTrue(browser.findElements(By.cssSelector(".user-created-flag-single")).size()>0);
//    }

    }




