package com.testAuto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

    public static WebDriver getBrowser(String browserName) {
        WebDriver browser = null;

        switch (browserName) {

//            case "Firefox":
//                driver = drivers.get("Firefox");
//                if (driver == null) {
//                    System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
//                    driver = new FirefoxDriver();
//                    drivers.put("Firefox", driver);
//                }
//                break;

            case "Chrome":
                browser = drivers.get("Chrome");
                if (browser == null) {
                    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                    browser = new ChromeDriver();
                    drivers.put("Chrome", browser);
                }
                break;
            case "ChromeWithOptions":
                browser = drivers.get("ChromeWithOptions");
                if (browser == null) {
                    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                    String downloadFilepath = System.getProperty("user.dir") + "/downloaded";
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("download.default_directory", downloadFilepath);
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", chromePrefs);
                    DesiredCapabilities cap = DesiredCapabilities.chrome();
                    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    browser = new ChromeDriver(cap);
                    drivers.put("ChromeWithOptions", browser);
                }
                break;

        }
        return browser;
    }

    public static void deleteAllDrivers() {
        for (String key : drivers.keySet()) {
            drivers.get(key).close();
            drivers.get(key).quit();
        }

    }
}
