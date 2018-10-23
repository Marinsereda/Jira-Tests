package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleTest {

    private static WebDriver browser;
    static Code c;

    static String nameTask = "Check create process";
    static String numberTask;
    static String newIssuePath;
    static String downloadFilepath;
    static String fileName2;
    static String fileName = "forTest.jpg";



    public static void loginPass() {

        browser.get("http://jira.hillel.it:8080");
        c.findAndFill(By.cssSelector("#login-form-username"), "autorob");
        c.findAndFill(By.cssSelector("#login-form-password"), "forautotests\n");
    }


    @BeforeTest

    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        downloadFilepath = System.getProperty("user.dir") + "\\downloaded";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        browser = new ChromeDriver(cap);

        c = new Code(browser);

        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }




    @Test (priority = -1)
    public static void loginFailed ()  {
        loginPass();
        Assert.assertFalse(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

    }

    @Test (priority = 1)
    public static void testLogin()  {

        Assert.assertTrue(browser.findElements(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).size() > 0);
    }

    @Test (priority = 2)

    public static void testCreateTask() {

        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        c.findAndFill(By.cssSelector(".jira-dialog-content #summary"), nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0&&
                linkNewIssues.size()!=0);
    }

    @Test (priority = 3)

    public static void testOpenTask() {

        browser.get(newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(nameTask));

    }


    @Test (priority = 4)

    public static void uploadAttach () throws InterruptedException {

        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(downloadFilepath+"\\"+fileName);
        Thread.sleep(4000);
        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(priority = 5)
    public static void downloadAttach () throws InterruptedException {

        browser.findElement(By.cssSelector("div.attachment-thumb")).click();
        Thread.sleep(2000);
        browser.findElement(By.cssSelector("#cp-control-panel-download")).click();
        String fileTitle = browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title");
        browser.findElement(By.cssSelector("#cp-control-panel-close")).click();
        Thread.sleep(2000);

        fileName2 = downloadFilepath + "\\"+ fileTitle;
        Assert.assertTrue(fileName2.contains(fileName));


    }

    @AfterTest
    public static void closeBrowser(){
        browser.quit();
    }

}