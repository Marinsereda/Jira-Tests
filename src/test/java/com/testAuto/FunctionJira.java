package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;
import java.util.List;


public class FunctionJira {


    static String numberTask;
    static String newIssuePath;
    static List<WebElement> linkNewIssues ;
    static String uploadFileHref;
    static String  fileTitle;
    static File f;
    static String fileDownloadPAth;

    private  WebDriver browser;
    private  Code c;

    FunctionJira(WebDriver browser) {
        this.browser = browser;
        this.c = new Code(browser);
        PageFactory.initElements(browser, this);
    }

    By usernameInput = By.cssSelector("#login-form-username");
    By passwordInput = By.cssSelector("#login-form-password");
    By summaryInput = By.cssSelector(".jira-dialog-content #summary");

    @FindBy(css = "a#create_link")
    WebElement createIssueButton;

    @FindBy(css = ".jira-dialog-content #create-issue-submit")
    WebElement createSumButton;

    @FindBy(css = ".issue-created-key")
    WebElement createIssueKey;

    @FindBy(css = "input.issue-drop-zone__file")
    WebElement inputFile;

    @FindBy(css = ".attachment-title")
    WebElement attachTitle;


    public void loginPass(boolean correctPass) throws InterruptedException {
        String pass = (correctPass? TestData.password: TestData.passwordWrong)+ "\n";

        browser.get(TestData.siteLink);
//        c.findAndFill(By.cssSelector("#login-form-username"), TestData.login);
        c.findAndFill(usernameInput, TestData.login);
        Thread.sleep(500);
        c.findAndFill(passwordInput, pass );
    }




    public  void createTask() {

//        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
//        c.findAndFill(By.cssSelector(".jira-dialog-content #summary"), TestData.nameTask);
//        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();

        createIssueButton.click();
        c.findAndFill(summaryInput, TestData.nameTask);
        createSumButton.click();

        numberTask = createIssueKey.getAttribute("data-issue-key");
        linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");

//        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
    }


    public void uploadAttach(){
//        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(TestData.uploadFolder + "\\" + TestData.fileName);
        inputFile.sendKeys(TestData.uploadFolder + "\\" + TestData.fileName);

        uploadFileHref = new FluentWait<>(browser).
                withTimeout(Duration.ofSeconds(3)).
                pollingEvery(Duration.ofSeconds(1)).
                ignoring(Exception.class)
                .until(browser -> attachTitle.getAttribute("href"));

//        uploadFileHref = browser.findElement(By.cssSelector(".attachment-title")).getAttribute("href");
//        fileTitle= browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title");
        fileTitle= attachTitle.getAttribute("title");
    }

    public  void downloadAttach() throws InterruptedException {
        browser.get(uploadFileHref);
        Thread.sleep(2000);
       fileTitle = FunctionJira.fileTitle.substring(0, FunctionJira.fileTitle.indexOf(".zip") + 4);
        fileDownloadPAth = TestBase.downloadFilepath + "\\" + fileTitle;
        f = new File(fileDownloadPAth);

    }

    public void adminUserCreate() throws InterruptedException {
        Thread.sleep(1500);
//        browser.findElement(By.cssSelector(".aui-nav #system-admin-menu")).click();
//        browser.findElement(By.cssSelector("#admin_users_menu")).click();
//        browser.findElement(By.cssSelector("input#login-form-authenticatePassword")).sendKeys("forautotests\n");
//        browser.findElement(By.cssSelector("#create_user")).click();
//        browser.findElement(By.cssSelector("#user-create-email")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-fullname")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-username")).sendKeys(TestData.email);
//        browser.findElement(By.cssSelector("#user-create-submit")).click();

        adminMenu.click();
        adminUsers.click();
        adminLog.sendKeys("forautotests\n");
        createUser.click();
        userEmail.sendKeys(TestData.email);
        userFullname.sendKeys(TestData.email);
        userName.sendKeys(TestData.email);
        userSubmit.click();


    }
    @FindBy(css=".aui-nav #system-admin-menu")
    WebElement adminMenu;
    @FindBy(css="admin_users_menu")
    WebElement adminUsers;
     @FindBy(css="input#login-form-authenticatePassword")
    WebElement adminLog;
    @FindBy(css="#create_user")
    WebElement createUser;
    @FindBy(css="#user-create-email")
    WebElement userEmail;
    @FindBy(css="#user-create-fullname")
    WebElement userFullname;
    @FindBy(css="#user-create-username")
    WebElement userName;
    @FindBy(css="#user-create-submit")
    WebElement userSubmit;








}
