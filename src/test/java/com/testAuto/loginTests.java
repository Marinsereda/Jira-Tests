package com.testAuto;

import com.testrail.APIException;
import com.testrail.Testrail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class loginTests extends TestBase {
    static LoginPage steps;
    static int projectID = 6;
    static Integer v = 7;
    static String projID = "6";
    static String runPrefix = "JiraMF";


    @BeforeTest
    public static void initBrowser() {
        steps = new LoginPage(browser);
        testTrail = new Testrail("https://hillel5.testrail.io/");
        testTrail.loginAPI(TestData.usernameTrail, TestData.passwordTrail);
    }

    @Test(priority = -1, description = "1. LoginFailed")
    public void loginFailed() throws InterruptedException {
        steps.loginPass(false);
        Assert.assertTrue(browser.findElements(By.cssSelector("div#usernameerror")).size() > 0);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
    }

    @Test(description = ("2. LoginSuccess"))
    public void testLogin() throws InterruptedException {

        steps.loginPass(true);
        Assert.assertTrue(browser.findElements(By.cssSelector("#header-details-user-fullname")).size() > 0);

    }

    @AfterMethod
    public static void reportResults(ITestResult testResult) {
        String info = "";
        info += testResult.getMethod().getMethodName();
        info += ": ";
        info += testResult.isSuccess() ? "passed" : "failed";

        System.out.println(info);
    }

    static Testrail testTrail = new Testrail("https://hillel5.testrail.io/");
    static String runName = "FominaAPI";
//    static String projectUrl = "https://hillel5.testrail.io/index.php?/projects/overview/6";

    static HashMap<Integer, Integer> testRes = new HashMap<>();


//static Integer resultSt;


// @AfterMethod
//    public static void addCases2 (ITestResult testResult) throws IOException, APIException {
//     String info = testResult.getMethod().getDescription();
//     System.out.println(info);
//
//     int caseID = Integer.parseInt(info.substring(0, info.indexOf(".")));
//     System.out.println(caseID);
//
//     testRes.put(caseID, testResult.getStatus());
//     System.out.println(testRes);
//
////     testTrail.addCases(v,info);
//
//
//
// }
//
// @AfterClass
//    public static void runProcess() throws IOException, APIException {
//
//     testTrail.addRun(runName, projectID);
//
//     for (int testID: testRes.keySet()){
//         testTrail.setResults2(testID, testRes.get(testID));
//
//     }
//private HashMap<Integer, Integer> testResults = new HashMap<>();
//
//    @BeforeTest//(groups = "TestRailReport")
//    public void prepareTestRailRun()  {
//        String baseURL = "https://hillel5.testrail.io/";
//        System.out.println("Reporting to " + baseURL);
//
//        testTrail = new Testrail(baseURL);
//        testTrail.loginAPI(TestData.usernameTrail, TestData.passwordTrail);
////        testTrail.startRun(6, runPrefix + " MF - " + Helper.timeStamp());
//    }
//
////    @AfterMethod//(groups = "TestRailReport")
////    protected void reportResult(ITestResult testResult)  {
////        String testDescription = testResult.getMethod().getDescription();
////        try {
////            int caseId = Integer.parseInt(testDescription.substring(0, testDescription.indexOf(".")));
////
////            testResults.put(caseId, testResult.getStatus());
////            // trReport.setResult(caseId, testResult.getStatus());
////        } catch (IndexOutOfBoundsException | NumberFormatException e) {
////            System.out.println(testDescription + " - Case ID missing; not reporting to TestRail.");
////        }
////        testTrail.addCases(6, testResult.getMethod().getDescription());
//        //addCases
////    }
//
//    @AfterTest//(groups = "TestRailReport")
//    public void closeTestRailRun() throws Exception {
//        for (Integer testId : testResults.keySet())
//            try {
////                testTrail.setResult(testId, testResults.get(testId));
//                testTrail.setResults2(testId, testResults.get(testId));
//            } catch (APIException e) {
//                System.out.println("Can't report tp TestRail. Saving to file...");
//            }
////        testTrail.endRun();
//    }
//
}



