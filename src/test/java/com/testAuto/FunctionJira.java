package com.testAuto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;
import java.util.List;


public class FunctionJira extends TestBase{
    static String numberTask;
    static String newIssuePath;
    static List<WebElement> linkNewIssues ;
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


    public static void createTask() {

        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        c.findAndFill(By.cssSelector(".jira-dialog-content #summary"), TestData.nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();

        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
        linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));
        newIssuePath = linkNewIssues.get(0).getAttribute("href");

    }
    public static void uploadAttach(){
        browser.findElement(By.cssSelector("input.issue-drop-zone__file")).sendKeys(TestData.uploadFolder + "\\" + TestData.fileName);

        uploadFileHref = new FluentWait<>(browser).withTimeout(Duration.ofSeconds(3)).pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class)
                .until(browser -> browser.findElement(By.cssSelector(".attachment-title")).getAttribute("href"));

//        uploadFileHref = browser.findElement(By.cssSelector(".attachment-title")).getAttribute("href");
        fileTitle= browser.findElement(By.cssSelector(".attachment-title")).getAttribute("title");


    }

    public static void downloadAttach() throws InterruptedException {
        browser.get(uploadFileHref);
        Thread.sleep(2000);
       fileTitle = FunctionJira.fileTitle.substring(0, FunctionJira.fileTitle.indexOf(".zip") + 4);
        fileDownloadPAth = downloadFilepath + "\\" + fileTitle;
        f = new File(fileDownloadPAth);

    }





}
