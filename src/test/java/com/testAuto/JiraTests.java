package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class JiraTests extends TestBase {

    public WebDriver browser;
//    static FunctionJira steps;
    static LoginPage loginSteps;
    static IssuePage issuePageSteps;
    static MainPage mainPageSteps;


    @BeforeClass(alwaysRun = true)
    public void init() {
//        browser = BrowserFactory.getBrowser("ChromeWithOptions");
//        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        browser.manage().window().maximize();
//        steps = new FunctionJira(browser);

        //add browser to other objects of classes
        browser= TestBase.browser;
        loginSteps = new LoginPage(browser);
        issuePageSteps = new IssuePage(browser);
        mainPageSteps = new MainPage(browser);

    }


    @Test(priority = -1, groups = {"login"}, description = "1. LoginFailed")
    public void loginFailed() throws InterruptedException {
        loginSteps.loginPass(false);//change object, add description
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
    }

//    @Test(groups = {"login"},description = ("2. LoginSuccess"))
    @Test(description = ("2. LoginSuccess"))
    public void testLogin() throws InterruptedException {

        loginSteps.loginPass(true);//change object, add description
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);
    }


    @Test(dependsOnMethods = "testLogin", priority = 3, groups = {"IssuePage"},description = ("3. CreateIssue"))

    public void testCreateIssue() throws InterruptedException {
        Thread.sleep(3000);

        mainPageSteps.createIssue();//change object, add description

        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0 &&
                mainPageSteps.linkNewIssues.size() != 0);
    }

    @Test(dependsOnMethods = "testCreateIssue", groups = {"IssuePage"},description = ("4. OpenIssue"))

    public void testOpenIssue() {

        browser.get(mainPageSteps.newIssuePath);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(TestData.nameIssue));
    }

    @Test(dependsOnMethods = "testOpenIssue", groups = {"IssuePage"},description = ("4. UploadAttach"))

    public void uploadAttach() throws InterruptedException {

        issuePageSteps.uploadAttach();
        Thread.sleep(3000);

        Assert.assertTrue(browser.findElements(By.cssSelector("div.attachment-thumb")).size() > 0);
    }

    @Test(dependsOnMethods = "uploadAttach", groups = {"IssuePage"}, description = ("5. DownloadAttach"))

    public void downloadAttach() throws InterruptedException {

        issuePageSteps.downloadAttach();
        Assert.assertTrue(issuePageSteps.f.exists());
    }

    @Test(dependsOnMethods = "downloadAttach", groups = {"IssuePage"}, description = ("6. DeleteAttach"))

    public void deleteTest() {
       issuePageSteps.f.delete();// think over how to delete without a test

        Assert.assertFalse(issuePageSteps.f.exists());
    }

    @Test(dependsOnMethods = "testLogin", priority = 10, groups = {"AdminMenu"}, description = ("7. CreateUser"))
    public void createUser() throws InterruptedException {
        mainPageSteps.adminUserCreate();

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



