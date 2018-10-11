//import org.junit.Assert;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExampleTest {

    private static WebDriver browser = new ChromeDriver();
    static String nameTask = "Check create process";

    @BeforeMethod
    static void loginPass() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser.get("http://jira.hillel.it:8080");
        browser.findElement(By.cssSelector("[id=\"login-form-username\"]")).sendKeys("autorob");
        browser.findElement(By.cssSelector("[id=\"login-form-password\"]")).sendKeys("forautotests");
        browser.findElement(By.cssSelector("input[value=\"Log In\"]")).click();
        Thread.sleep(6000);

        Assert.assertTrue(browser.findElements(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).size() > 0);
    }


    @Test
    public static void createTask() throws InterruptedException {
        browser.findElement(By.cssSelector("a[href=\"/secure/CreateIssue!default.jspa\"]")).click();
        Thread.sleep(3000);
        browser.findElement(By.cssSelector(".jira-dialog-content #summary")).sendKeys(nameTask);
        browser.findElement(By.cssSelector(".jira-dialog-content #create-issue-submit")).click();
        Thread.sleep(3000);
        browser.findElement(By.cssSelector("#quickSearchInput")).sendKeys(nameTask);
        Thread.sleep(7000);
//     находим массив эл-тов по данному селектору и выбираем с индексом (0)
        browser.findElements(By.cssSelector(".quick-search-view-all")).get(0).click();

        Thread.sleep(2000);

        Assert.assertTrue(browser.findElement(By.cssSelector("td.summary")).getText().equals(nameTask));
    }
}
