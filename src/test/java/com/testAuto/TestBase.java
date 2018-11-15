package com.testAuto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver browser;
    static Code c;
    static String downloadFilepath;


    @BeforeTest

    public static void openBrowser()  {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        downloadFilepath = System.getProperty("user.dir") + "\\downloaded";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("disable-popup-blocking", true);//add fromJS
        chromePrefs.put("browser.helperApps.neverAsk.saveToDisk", "image/jpg");//add fromJS
        chromePrefs.put("plugins.always_open_jpg_externally", true);//add fromJS
        chromePrefs.put("jpgjs.disabled", true);//add fromJS

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
//        DesiredCapabilities cap = DesiredCapabilities.chrome();
//        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        cap.setCapability(ChromeOptions.CAPABILITY, options);
        browser = new ChromeDriver(options);



        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
//        c = new Code(browser);
    }




    @AfterTest
    public  void closeBrowser(){
        browser.quit();
    }
}
