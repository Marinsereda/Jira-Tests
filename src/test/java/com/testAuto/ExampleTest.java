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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleTest {

    private static WebDriver browser;
    static String nameTask = "Check create process";
    static String numberTask;
    static String newIssuePath;
    static String downloadFilepath;
    static String fileName2;
    static String fileName = "C:\\projects\\com.example\\HelloMaven2\\FilesForTests\\forTest.jpg";



    public static void loginPass() throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//        Thread.sleep(3000);
        browser.get("http://jira.hillel.it:8080");
        findAndFill(By.cssSelector("#login-form-username"), "autorob");
        findAndFill(By.cssSelector("#login-form-password"), "forautotests\n");
//        findAndFill(By.cssSelector("#login-form-password"), "forautotests");
//        browser.findElement(By.cssSelector("[id=\"login\"]")).click();
//        Thread.sleep(1000);

    }

    public static WebElement findAndFill(By selector, String value) {
        WebElement element = browser.findElement(selector);
        element.sendKeys(value);
        return element;
    }

    public static void createTask() throws InterruptedException {
        Thread.sleep(4000);
        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        findAndFill(By.cssSelector(".jira-dialog-content #summary"), nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");


    }

    public static void openTask() throws InterruptedException {
        Thread.sleep(3000);
        findAndFill(By.cssSelector("#quickSearchInput"), numberTask);
        Thread.sleep(3000);

        browser.findElements(By.cssSelector(".quick-search-view-all")).get(0).click();
        Thread.sleep(3000);

        browser.findElement(By.cssSelector(".summary .issue-link")).click();
    }




    @BeforeTest

    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        downloadFilepath = System.getProperty("user.dir") + "\\downloaded";
//        "C:\\projects\\com.example\\HelloMaven2\\downloaded";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        browser = new ChromeDriver(cap);

//        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }




//    @Test
//    public static void loginFailed () throws InterruptedException {
//        loginPass();
//        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);
//
//    }

    @Test (priority = 1)
    public static void testLogin() throws InterruptedException {
        loginPass();
//        browser.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        Assert.assertTrue(browser.findElements(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).size() > 0);
    }

    @Test (priority = 2)

    public static void testCreateTask() throws InterruptedException {
        loginPass();
        Thread.sleep(4000);
        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        findAndFill(By.cssSelector(".jira-dialog-content #summary"), nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");
//        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));

//        Assert.assertTrue(linkNewIssues.size() != 0);

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0&& linkNewIssues.size()!=0);
//        newIssuePath = linkNewIssues.get(0).getAttribute("href");
    }

    @Test (priority = 3)

    public static void testOpenTask() throws InterruptedException {
        loginPass();
        Thread.sleep(5000);

        createTask();
        Thread.sleep(5000);

        browser.get(newIssuePath);
        Thread.sleep(3000);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(nameTask));


    }



    @Test (priority = 4)

    public static void uploadAttach () throws InterruptedException {

        loginPass();
        Thread.sleep(5000);

        createTask();
        Thread.sleep(5000);

        browser.get(newIssuePath);
        Thread.sleep(3000);

        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(fileName);
        Thread.sleep(4000);
        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);


    }

    @Test(priority = 5)
    public static void downloadAttach () throws InterruptedException {
        uploadAttach();

        browser.findElement(By.cssSelector("div.attachment-thumb")).click();
        Thread.sleep(2000);
        browser.findElement(By.cssSelector("#cp-control-panel-download")).click();
        String fileTitle = browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title");
        browser.findElement(By.cssSelector("#cp-control-panel-close")).click();
        Thread.sleep(2000);

        fileName2 = downloadFilepath + "\\"+ fileTitle;

        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(fileName2);
//        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(downloadFilepath + "\\" + fileTitle);
//        proverka  - naiti papku zagruzki i priverit nalichie faila s nazvaniem c caita

//Assert.assertTrue(browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title").contains(fileName));

    }

    @AfterTest
    public static void closeBrowser(){
        browser.quit();
    }








////variant robert
//    @Test
//    public static void createIssue() throws InterruptedException {
//        browser.findElement(By.cssSelector("a#create_link")).click();
//        Thread.sleep(8000);
////        findAndFill(By.cssSelector("input#project-field"), "General Robert QA (GQR)").click();
////        Thread.sleep(5000);
//
//        findAndFill(By.cssSelector("input#summary"), nameTask).submit(); // Add current timestamp to the Summary
//        Thread.sleep(3000);
//        linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
//
//        if (linkNewIssues.size() != 0) {
//            System.out.println("Create Issue Passed");
//            newIssuePath = linkNewIssues.get(0).getAttribute("href");
//        } else {
//            System.out.println("Create Issue Failed");
//        }
//
////        newIssuePath = linkNewIssues.get(0).getAttribute("href");
//    }
//    @Test
//    public static void openIssue() throws InterruptedException {
//        openBrowser();
//        loginPass();
//        Thread.sleep(3000);
//
//        browser.get(newIssuePath);
//
//        if (browser.getTitle().contains(nameTask)) {
//            System.out.println("Open Issue Passed");
//        } else {
//            System.out.println("Open Issue Failed");
//        }
//    }
////    newIssuePath  не перезаписывается
////    end



}