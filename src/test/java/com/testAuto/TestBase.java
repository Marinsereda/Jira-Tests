package com.testAuto;

import com.testrail.APIException;
import com.testrail.Testrail;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver browser;
    static String downloadFilepath;
    static Testrail testTrail ;
    private HashMap<Integer, Integer> testResults = new HashMap<>();

    @BeforeTest(alwaysRun = true)

    public static void openBrowser()  {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        downloadFilepath = System.getProperty("user.dir") + "\\downloaded";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath); // - config for changing download folder
        browser = new ChromeDriver(new ChromeOptions().addArguments("--incognito").setExperimentalOption("prefs", chromePrefs));


        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

//        chromePrefs.put("disable-popup-blocking", true);//add fromJS
//        chromePrefs.put("browser.helperApps.neverAsk.saveToDisk", "image/jpg");//add fromJS
//        chromePrefs.put("plugins.always_open_jpg_externally", true);//add fromJS
//        chromePrefs.put("jpgjs.disabled", true);//add fromJS

//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("prefs", chromePrefs);
//        options.addArguments("--incognito");
//        DesiredCapabilities cap = DesiredCapabilities.chrome();
//        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        cap.setCapability(ChromeOptions.CAPABILITY, options);
//        browser = new ChromeDriver(options);

    }


    @AfterTest(alwaysRun = true)
    public  void closeBrowser(){
        browser.quit();
    }


    //@Parameters({"testrailUsername, testrailPassword"})
    @BeforeTest(groups = "TestRailReport")
    public  void loginAPI() {
        testTrail = new Testrail("https://hillel5.testrail.io/");
        testTrail.loginAPI(TestData.usernameTrail, TestData.passwordTrail);

    }

//    @AfterMethod (groups = "TestRailReport")
//    public void addCasesTestRail(ITestResult testResult) throws IOException, APIException {
//        testTrail.addCases(1, testResult.getMethod().getDescription());//succes with hard code on sectionID
//    }
    String runPrefix = "JiraMF";

//    @Parameters({ "testRailRunPrefix" })
    @BeforeTest(groups = "TestRailReport")
    public void prepareTestRailRun() throws IOException, APIException {
        loginAPI();
        testTrail.addRun(runPrefix + " " + Helper.timeStamp(), 6 );//succes with hard code on projectID

    }

    @AfterMethod(groups = "TestRailReport")
    protected void reportResult(ITestResult testResult) throws IOException, APIException {
        String testDescription = testResult.getMethod().getDescription();
        testTrail.addCases(1, testDescription);
        try {
            int caseId = Integer.parseInt(testDescription.substring(0, testDescription.indexOf(".")));

            testResults.put(caseId, testResult.getStatus());
            // trReport.setResult(caseId, testResult.getStatus());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(testDescription + " - Case ID missing; not reporting to TestRail.");
        }
    }

    @AfterTest(groups = "TestRailReport")
    public void closeTestRailRun() throws Exception {
        for (Integer testId : testResults.keySet())
            try {
                testTrail.setResults(testId, testResults.get(testId));
            } catch (APIException e) {
                System.out.println("Can't report tp TestRail. Saving to file...");
            }
//        testTrail.endRun();
    }


//    @Parameters({ "testRailProjectId", "testRailRunPrefix" })
//    @BeforeTest(groups = "TestRailReport")
//    public void prepareTestRailRun(String projectId, String runPrefix) throws Exception {
//        String baseURL = "https://hillel5.testrail.io/";
//        System.out.println("Reporting to " + baseURL);
//
//        testTrail = new Testrail(baseURL);
//        testTrail.loginAPI(TestData.usernameTrail, TestData.passwordTrail);
//        testTrail.startRun(Integer.parseInt(projectId), runPrefix + " Robert Auto - " + Helper.timeStamp());
//    }
//
//    @AfterMethod(groups = "TestRailReport")
//    protected void reportResult(ITestResult testResult) throws Exception {
//        String testDescription = testResult.getMethod().getDescription();
//        try {
//            int caseId = Integer.parseInt(testDescription.substring(0, testDescription.indexOf(".")));
//
//            testResults.put(caseId, testResult.getStatus());
//            // trReport.setResult(caseId, testResult.getStatus());
//        } catch (IndexOutOfBoundsException | NumberFormatException e) {
//            System.out.println(testDescription + " - Case ID missing; not reporting to TestRail.");
//        }
//    }
//
//    @AfterTest(groups = "TestRailReport")
//    public void closeTestRailRun() throws Exception {
//        for (Integer testId : testResults.keySet())
//            try {
//                testTrail.setResult(testId, testResults.get(testId));
//            } catch (APIException e) {
//                System.out.println("Can't report tp TestRail. Saving to file...");
//            }
//        testTrail.endRun();
//    }
}
