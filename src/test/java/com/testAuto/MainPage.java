package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    static String numberIssue;
    static String newIssuePath;
    static List<WebElement> linkNewIssues ;

    private WebDriver browser;
    private Helper helper;

    MainPage(WebDriver browser) {
        this.browser = browser;
        this.helper = new Helper(browser);
        PageFactory.initElements(browser, this);
    }

    By summaryInput = By.cssSelector(".jira-dialog-content #summary");

    @FindBy(css = "a#create_link")
    WebElement createIssueButton;

    @FindBy(css = ".jira-dialog-content #create-issue-submit")
    WebElement createSumButton;

    @FindBy(css = ".issue-created-key")
    WebElement createIssueKey;

    @FindBy(css=".aui-nav #system-admin-menu")
    WebElement adminMenu;

    @FindBy(css="#admin_users_menu")
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



    public  void createIssue() {


        createIssueButton.click();
        helper.findAndFill(summaryInput, TestData.nameIssue);
        createSumButton.click();

        numberIssue = createIssueKey.getAttribute("data-issue-key");
        linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");

    }

    public void adminUserCreate() throws InterruptedException {
        Thread.sleep(1500);

        adminMenu.click();
        adminUsers.click();
        adminLog.sendKeys("forautotests\n");
        createUser.click();
        userEmail.sendKeys(TestData.email);
        userFullname.sendKeys(TestData.email);
        userName.sendKeys(TestData.email);
        userSubmit.click();

    }

}
