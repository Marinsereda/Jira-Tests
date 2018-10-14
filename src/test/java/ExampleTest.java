//import org.junit.Assert;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExampleTest {

    private static WebDriver browser;
    static String nameTask = "Check create process";
    static String numberTask;

    public static void openBrowser() {
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    public static void loginPass() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser.get("http://jira.hillel.it:8080");
        findAndFill(By.cssSelector("#login-form-username"), "autorob");
        findAndFill(By.cssSelector("#login-form-password"), "forautotests\n");
    }

    public static WebElement findAndFill(By selector, String value) {
        WebElement element = browser.findElement(selector);
        element.sendKeys(value);
        return element;
    }

    public static void createTask() throws InterruptedException {
        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        Thread.sleep(3000);
        findAndFill(By.cssSelector(".jira-dialog-content #summary"), nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        Thread.sleep(2000);
        numberTask = browser.findElement(By.cssSelector(".issue-created-key")).getAttribute("data-issue-key");
    }

    public static void openTask() throws InterruptedException {
        createTask();
        Thread.sleep(3000);
        findAndFill(By.cssSelector("#quickSearchInput"), numberTask);
        Thread.sleep(3000);
        browser.findElements(By.cssSelector(".quick-search-view-all")).get(0).click();
        Thread.sleep(3000);
        browser.findElement(By.cssSelector(".summary .issue-link")).click();
    }


    @BeforeTest

    public static void testLogin() throws InterruptedException {
        openBrowser();
        loginPass();
        Thread.sleep(6000);
        Assert.assertTrue(browser.findElements(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).size() > 0);
    }

    @AfterTest
    public static void closeBrowser(){
        browser.quit();
    }

    @Test

    public static void testCreateTask() throws InterruptedException {
        createTask();
        Thread.sleep(3000);
        Assert.assertTrue(browser.findElements(By.cssSelector(".aui-message-success")).size() > 0);
    }

    @Test

    public static void testOpenTask() throws InterruptedException {
        openTask();
        Thread.sleep(3000);
        Assert.assertTrue(browser.findElement(By.cssSelector("#summary-val")).getText().contains(nameTask));


    }


}