package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class ExampleTest extends TestBase{

//    public static WebDriver browser;
//    static Code c;

    static String numberTask;
    static String newIssuePath;
    static String uploadFileHref;
    static String  fileTitle;
    static File f;
    static String fileDownloadPAth;




    public static void loginPass(boolean correctPass) throws InterruptedException {
        String pass = (correctPass? TestData.password: TestData.passwordWrong)+ "\n";

        browser.get(TestData.siteLink);
        c.findAndFill(By.cssSelector("#login-form-username"), TestData.login);
        Thread.sleep(500);
        c.findAndFill(By.cssSelector("#login-form-password"), pass );
    }


    @Test(priority = -1)
    public static void loginFailed () throws InterruptedException {
        loginPass(false);
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));

    }

    @Test
    public static void testLogin() throws InterruptedException {

        loginPass(true);
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);
    }

    @Test(dependsOnMethods = "testLogin")

    public static void testCreateTask() {

        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        c.findAndFill(By.cssSelector(".jira-dialog-content #summary"), TestData.nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
                linkNewIssues.size() != 0);
    }

    @Test(dependsOnMethods = "testCreateTask")

    public static void testOpenTask() {

        browser.get(newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameTask));

    }


    @Test(dependsOnMethods = "testOpenTask")

    public static void uploadAttach() throws InterruptedException {

        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(TestData.uploadFolder + "\\" + TestData.fileName);
        Thread.sleep(3000);
        uploadFileHref = browser.findElement(By.cssSelector(".attachment-title")).getAttribute("href");
        fileTitle= browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title");

        System.out.println(uploadFileHref + " " + fileTitle);

        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(dependsOnMethods = "uploadAttach")

    public static void downloadAttach() throws InterruptedException {

            browser.get(uploadFileHref);
            Thread.sleep(2000);

            fileTitle = fileTitle.substring(0, fileTitle.indexOf(".zip") + 4);
            fileDownloadPAth = downloadFilepath + "\\" + fileTitle;
            f = new File(fileDownloadPAth);

            Assert.assertTrue(f.exists());
        }


        @Test (dependsOnMethods = "downloadAttach")

        public static void deleteTest()  {
            f.delete();

            Assert.assertFalse(f.exists());
        }



    @Test(dependsOnMethods = "testLogin", priority = 10)
    public static void createUser() throws InterruptedException {
        c.adminUserCreate();


        Assert.assertTrue(browser.findElements(By.cssSelector(".user-created-flag-single")).size()>0);
    }

    }




