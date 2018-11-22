package com.testrail;

import com.testAuto.TestData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Testrail {
//     String baseUrl = "https://hillel5.testrail.io/";
     private APIClient client;
     private Long runID;
     private String idRun;

     public Testrail(String baseUrl){
         client = new APIClient(baseUrl);
     }

     public void loginAPI(String username, String password){
         client.setUser(username);
         client.setPassword(password);
     }

    Integer runID1;

     public void addRun (String runName, Integer projectID) throws IOException, APIException {
         HashMap <String, String> data = new HashMap<>();
         data.put("name", runName );
//         JSONObject r = (JSONObject) client.sendPost(String.format("add_run/id", projectID), data);
         JSONObject r = (JSONObject) client.sendPost( "add_run/6" , data);
         this.runID = (long) r.get("id");
         runID1= (Integer) r.get("id");

         System.out.println(this.runID);
     }

//     public void getTestsID(Integer runID) throws IOException, APIException {
////         JSONObject t = (JSONObject) client.sendGet(String.format("get_tests/id", runID1));
//         JSONObject t = (JSONObject) client.sendGet(String.format("get_tests/id", runID));
//         System.out.println(t.get("id"));
//     }

     public void closeRun () throws IOException, APIException {
         client.sendPost(String.format("close_run/id", this.runID), new HashMap<>());
     }

Object caseID;

     public void addCases(Integer projectID, String nameCase) throws IOException, APIException {
         HashMap <String, String> dataCase = new HashMap<>();
         dataCase.put("title", nameCase);
//         JSONObject c = (JSONObject) client.sendPost(String.format("add_case/id", projectID), dataCase);
         JSONObject c = (JSONObject) client.sendPost( "add_case/15", dataCase);
//         JSONObject c = (JSONObject) client.sendPost("add_case/15", dataCase);
//         caseID=c.get("id");
//         System.out.println(Integer.parseInt((String) caseID));


     }

     public void setResults (Integer caseID, Integer testResult) throws IOException, APIException {
         HashMap <String, Integer> data = new HashMap<>();
         data.put("case_id",caseID);
         data.put("status_id", convertResults(testResult));
         JSONObject s = (JSONObject) client.sendPost(String.format("add_results_for_cases/id/id", this.runID,caseID), data);
     }

     public Integer convertResults (Integer testResult) {
         switch (testResult) {
             case 1:
                 return 1; //Success
             case 2:
                 return 5; //Failure
             case 3:
                 return 2; //blocked
             default:
                 return 4; //retest
         }
     }

     public void setResults2(Integer caseID, Integer testResult) throws IOException, APIException {
         objResult = new JSONObject();

         JSONObject objTests = new JSONObject();

         objTests.put("case_id",caseID);
         objTests.put("status_id",convertResults(testResult));

         JSONArray list = new JSONArray();
         list.add(objTests);

         objResult.put("results", list);

         System.out.println(objResult);

         JSONObject c = (JSONObject) client.sendPost(String.format("add_results_for_cases/128"),objResult);
//         JSONObject c = (JSONObject) client.sendPost(String.format("add_results_for_cases/id", this.runID),objMain);


         if (c.get("id") == null)
             throw new APIException("No such test case!");
     }

     static JSONObject objResult;

     public JSONObject resultList(Integer caseID, Integer testResult) {
//         JSONObject objMain = new JSONObject();
         objResult = new JSONObject();

         JSONObject objTests = new JSONObject();

         objTests.put("case_id", caseID);
         objTests.put("status_id", convertResults(testResult));

         JSONArray list = new JSONArray();
         list.add(objTests);

         objResult.put("results", list);
//         try (FileWriter file = new FileWriter("f:\\test.json")) {
//
//             file.write(objResult.toJSONString());
//             file.flush();
//
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
         System.out.println(objResult);
         return objResult;


     }



//     String projectUrl = "https://hillel5.testrail.io/index.php?/projects/overview/6";
//
     public void getProjectsID () throws IOException, APIException {
         JSONArray g = (JSONArray) client.sendGet("get_sections/6");
         System.out.println(g);
         Integer prID = Integer.parseInt(String.valueOf(g));

     }








//Robert version
    public void startRun(Integer projectID, String runName) throws Exception {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", runName);
//        JSONObject r = (JSONObject) client.sendPost(String.format("add_run/id", projectID), data);
        JSONObject r = (JSONObject) client.sendPost( "add_run/6" , data);

        this.runID = (Long) r.get("id");
        this.idRun = (this.runID.toString());

//        this.runID = Long.valueOf(Integer.parseInt (String.valueOf((Long) r.get("id"))));
        System.out.println(this.runID+ " + " + this.idRun);
    }

    public void setResult(Integer caseID, Integer testNGResult) throws Exception {
        HashMap<String, Integer> data = new HashMap<>();
        data.put("status_id", convertResults(testNGResult));
//        JSONObject r = (JSONObject) client.sendPost(String.format("add_result_for_case/id/id", this.runID, caseID),
        JSONObject r = (JSONObject) client.sendPost(String.format("add_result_for_case/128/id", caseID),
                data);

        if (r.get("id") == null)
            throw new APIException("No such test case!");
    }

    public void endRun() throws Exception {
        JSONObject en = (JSONObject)client.sendPost(String.format("close_run/127"), new HashMap<>());//passed
//        JSONObject en = (JSONObject)client.sendPost(String.format("close_run/id", this.idRun), new HashMap<>());
    }

}
