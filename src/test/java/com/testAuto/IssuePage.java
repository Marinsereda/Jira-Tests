package com.testAuto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class IssuePage {
    private WebDriver browser;
    private  Code c;

    static String uploadFileHref;
    static String  fileTitle;
    static File f;
    static String fileDownloadPAth;


    IssuePage(WebDriver browser) {
        this.browser = browser;
        this.c = new Code(browser);
        PageFactory.initElements(browser, this);
    }
    @FindBy(css = "input.issue-drop-zone__file")
    WebElement inputFile;

    @FindBy(css = ".attachment-title")
    WebElement attachTitle;

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
        fileTitle = IssuePage.fileTitle.substring(0, IssuePage.fileTitle.indexOf(".zip") + 4);
        fileDownloadPAth = TestBase.downloadFilepath + "\\" + fileTitle;
        f = new File(fileDownloadPAth);

    }

}
