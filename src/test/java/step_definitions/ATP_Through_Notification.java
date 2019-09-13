package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import okhttp3.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import property.Property;
import utilities.ReadJenkinsParameters;
import utilities.createDbConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ATP_Through_Notification {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String jsonData1;
    public DataBase_connection db;
    public String access_token;
    public String cycleIDProcessed = null;
    public int count = 0;

    public ATP_Through_Notification() throws MalformedURLException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("inside login constructor");
    }


    @And("^I Login to workbench to perform Auto Transaction posting$")
    public void I_Login_to_workbench_to_perform_Auto_Transaction_posting(DataTable table) throws IOException, JSONException, InterruptedException {
        String env = System.getProperty("Environment");
        String tier= System.getProperty("Tier");
        System.out.println("------" + env + "------");

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String id = "";
        String secret = "";
        String URL1 = "";
        String URL2 = "";
        if (env.equalsIgnoreCase("Preprod")){
            id = myJsonObj.get("Preprod_client_id").getAsString();
            secret = myJsonObj.get("Preprod_client_secret").getAsString();
            URL1 = myJsonObj.get("Preprod_URL1").getAsString();
            URL2 = myJsonObj.get("Preprod_URL2").getAsString();

            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){

            id = myJsonObj.get("QACore2_client_id").getAsString();
            secret = myJsonObj.get("QACore2_client_secret").getAsString();
            URL1 = myJsonObj.get("QACore2_URL1").getAsString();
            URL2 = myJsonObj.get("QACore2_URL2").getAsString();

            System.out.println("The Execution Environment is:QACORE2");
        }



        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=" + id + "&client_secret=" + secret + "&username=" + myJsonObj.get(env).getAsString() + "&password=" + myJsonObj.get("SFDCAdmin_Pwd").getAsString() + "");
        Request request = new Request.Builder()
            .url(URL1)

            .post(body)
            .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        JSONObject jsonObj = new JSONObject(jsonData);

        access_token = jsonObj.getString("access_token");
        System.out.println("The status code recieved: " + access_token);
        MediaType mediaType1 = MediaType.parse("application/json");
        String TotalRecords = "";
        String Start_timestamp = "";
        String End_timestamp = "";

        String Notification = "";
        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            //Enter card number
            Notification = data.get("<Notification>");
            cardNum = data.get("<Card_Number>");
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" + Notification + "------");
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");

            if (Notification.equalsIgnoreCase("POST")) {
                TotalRecords = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<TotalRecords>"))).getAsString();
                Start_timestamp = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<ATPStartTimestamp>"))).getAsString();
                End_timestamp = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<ATPEndTimestamp>"))).getAsString();
            } else if (Notification.equalsIgnoreCase("Refund")) {
                TotalRecords = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<TotalRecords>"))).getAsString();
                Start_timestamp = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<ATPStartTimestamp>"))).getAsString();
                End_timestamp = myJsonObj.get(ReadJenkinsParameters.getJenkinsParameter(data.get("<ATPEndTimestamp>"))).getAsString();
            }
        }

        System.out.println("------" + TotalRecords + "------");
        System.out.println("------" + Start_timestamp + "------");
        System.out.println("------" + End_timestamp + "------");
        RequestBody body1 = RequestBody.create(mediaType1, "{\"Datatype__c\":\"Transaction Posting\",\"Additional_information__c\":\"{\\\"totalRecordCount\\\":\\\"" + TotalRecords + "\\\"}\",\"Start_timestamp__c\":\"" + Start_timestamp + "\",\"End_timestamp__c\":\"" + End_timestamp + "\"}");


        Request request1 = new Request.Builder()
                .url(URL2)
                .post(body1)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + access_token + "")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response1 = client.newCall(request1).execute();
        jsonData1 = response1.body().string();
        Thread.sleep(30000);

        if (tier==null){
            tier = "Not_Added";
        }
        if (!(tier.equalsIgnoreCase("All"))){
            driver.close();
        }
    }

    @And("^I validate the success message$")
    public void I_validate_the_success_message() {
        System.out.println("JSON DATA: " + jsonData1);
        JSONObject jsonObj1 = new JSONObject(jsonData1);
        System.out.println("The status code recieved: " + jsonObj1.getBoolean("success"));
    }

    @And("^I Validate Main Table$")
    public void I_Validate_main_Table(DataTable table) throws Throwable {
        String env = System.getProperty("Environment");
        String tier= System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        String cardNum = "";
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            //Get card number from test data
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
        }
        DataBase_connection db = new DataBase_connection();
        System.out.println("Card_Number : "+cardNum);
        boolean queryComplete = false;
        while (!queryComplete) {
            Thread.sleep(30000);
            System.out.println(Instant.now().toString() + " sF_login_page");
            String dbquery1 = "Membership";
              /*  db.DbConnection(null, dbquery, "select Tier,StatusDollar,CarryForwardDollar,EntryDollar,StartDate,EndDate,AmtForUpgrade,AmtForRenewal \n" +
                        "from MatrixTpReward.dfs.DFS_MembershipCycle where MemberID in (select MemberID from MatrixTpReward.dbo.card where CardNo like '" + myJsonObj.get(cardNum).getAsString() + "') order by AmtForUpgrade;\n");*/
            //db.DbConnection(null, dbquery1, "select Tier,StatusDollar,CarryForwardDollar,EntryDollar,StartDate,EndDate,AmtForUpgrade,AmtForRenewal, MovementType, Remarks \n" +
                    //"from MatrixTpReward.dfs.DFS_MembershipCycle where MemberID in (select MemberID from MatrixTpReward.dbo.card where CardNo like '" + myJsonObj.get(cardNum).getAsString() + "') order by StartDate desc;\n");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            System.out.println("------" + timeStamp + "------");
            if (!db.tier.isEmpty()) {
                queryComplete = true;
            }
        }
        assertTrue(queryComplete);
    }


    @Given("^browser is initiated$")
    public void browserIsInitiated() throws Throwable {
       // new SearchMember_steps();
        System.out.println("Inside the Background step");

    }

    @And("^I Validate Stagging Table$")
    public void iValidateStaggingTable(DataTable table) throws Throwable {
        Thread.sleep(40000);
        String cycleID = null;
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        System.out.println("------" + env + "------");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String id = "";
        String secret = "";
        String URL1 = "";

        if (env.equalsIgnoreCase("Preprod")){
            id = myJsonObj.get("Preprod_client_id").getAsString();
            secret = myJsonObj.get("Preprod_client_secret").getAsString();
            URL1 = myJsonObj.get("Preprod_URL1").getAsString();

            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){

            id = myJsonObj.get("QACore2_client_id").getAsString();
            secret = myJsonObj.get("QACore2_client_secret").getAsString();
            URL1 = myJsonObj.get("QACore2_URL1").getAsString();

            System.out.println("The Execution Environment is:QACORE2");
        }

        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=" + id + "&client_secret=" + secret + "&username=" + myJsonObj.get(env).getAsString() + "&password=" + myJsonObj.get("SFDCAdmin_Pwd").getAsString() + "");
        Request request = new Request.Builder()
                .url(URL1)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        JSONObject jsonObj = new JSONObject(jsonData);

        access_token = jsonObj.getString("access_token");
        System.out.println("The status code recieved: " + access_token);
        MediaType mediaType1 = MediaType.parse("application/json");

        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");
        }

        String url = "";
        if (env.equalsIgnoreCase("Preprod")){
            url = "https://dfscrm--preprod.cs76.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = "https://dfscrm--qacore2.cs31.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: QACORE2");
        }
        Request request2 = new Request.Builder()
                .url( url + "?q=SELECT Cycle_Matrix_Id__c , Id FROM Membership_Cycle__c WHERE Member__r.Membership_Card_Number__c ='" + myJsonObj.get(cardNum).getAsString() + "'ORDER BY Cycle_Matrix_Id__c desc LIMIT 1")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token + "")
                .addHeader("x-http-method-override", "DELETE")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response3 = client.newCall(request2).execute();

        String jsonData2 = response3.body().string();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + jsonData2);
        JSONObject Jobject2 = new JSONObject(jsonData2);
        JSONArray Jarray = Jobject2.getJSONArray("records");
        //String cycleID = "";
        for (int i = 0; i < Jarray.length(); i++) {
            JSONObject object = Jarray.getJSONObject(i);
            System.out.println(object.get("Cycle_Matrix_Id__c"));
            cycleID = object.get("Cycle_Matrix_Id__c").toString();
            System.out.println(cycleID);
        }

        Thread.sleep(40000);
        System.out.println("Access Token: " + access_token);
        System.out.println("Cycle ID : "+ cycleID);

        String dbquery = "cycleID";
        DataBase_connection db = new DataBase_connection();

        boolean queryComplete1 = false;
        while (!queryComplete1) {
            // driver.navigate().refresh();
            db.DbConnection(null, dbquery, "select isProcessed, ErrorMessage \n" +
                    "from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='" + cycleID + "'order by AddedOn desc;\n", cycleID);
            System.out.println("Query processed");
            System.out.println("++++++++++++++++++++++++++"+ db.IsProcessed);

            if ((db.IsProcessed).equals("0") || db.ErrorMessage.equals("error")) {
                System.out.println("Error");
                break;
            } else if (db.IsProcessed.equals("1")){
                System.out.println("Status = 1");
                queryComplete1 = true;

            } else if ((db.IsProcessed).equals("2")) {
                System.out.println("Status = 2");
                Thread.sleep(30000);
                System.out.println("30 seconds passed");
                queryComplete1 = false;
                if (tier=="All"){
                    driver.navigate().refresh();
                }
            }  else if (db.IsProcessed.isEmpty()) {
                System.out.println("Status = empty");
                Thread.sleep(30000);
                System.out.println("30 seconds passed");
                queryComplete1 = false;
                if (tier=="All"){
                    driver.navigate().refresh();
                }
            }
        }

    }

    @And("^I query Salesforce DB to retrieve membership CycleID$")
    public void iQuerySalesforceDBToRetrieveMembershipCycleID(DataTable table) throws Throwable {
        Thread.sleep(40000);
        String env = System.getProperty("Environment");
        String cycleID = null;
        System.out.println("------" + env + "------");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String id = "";
        String secret = "";
        String URL1 = "";

        if (env.equalsIgnoreCase("Preprod")){
            id = myJsonObj.get("Preprod_client_id").getAsString();
            secret = myJsonObj.get("Preprod_client_secret").getAsString();
            URL1 = myJsonObj.get("Preprod_URL1").getAsString();

            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){

            id = myJsonObj.get("QACore2_client_id").getAsString();
            secret = myJsonObj.get("QACore2_client_secret").getAsString();
            URL1 = myJsonObj.get("QACore2_URL1").getAsString();

            System.out.println("The Execution Environment is:QACORE2");
        }

        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=" + id + "&client_secret=" + secret + "&username=" + myJsonObj.get(env).getAsString() + "&password=" + myJsonObj.get("SFDCAdmin_Pwd").getAsString() + "");
        Request request = new Request.Builder()
                .url(URL1)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        JSONObject jsonObj = new JSONObject(jsonData);

        access_token = jsonObj.getString("access_token");
        System.out.println("The status code recieved: " + access_token);
        MediaType mediaType1 = MediaType.parse("application/json");

        String cardNum = "";
        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
            cardNum = ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>"));
            System.out.println("------" +  myJsonObj.get(cardNum).getAsString() + "------");
        }

        String url = "";
        if (env.equalsIgnoreCase("Preprod")){
            url = "https://dfscrm--preprod.cs76.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = "https://dfscrm--qacore2.cs31.my.salesforce.com/services/data/v43.0/query/";
            System.out.println("The Execution Environment is: QACORE2");
        }

         while(Property.MAXIMUM_TIMEOUT_COUNT != count && cycleID==null){
             Request request2 = new Request.Builder()
                     .url( url + "?q=SELECT Cycle_Matrix_Id__c , Id FROM Membership_Cycle__c WHERE Member__r.Membership_Card_Number__c ='" + myJsonObj.get(cardNum).getAsString() + "'ORDER BY Cycle_Matrix_Id__c desc LIMIT 1")
                     .get()
                     .addHeader("content-type", "application/json")
                     .addHeader("authorization", "Bearer " + access_token + "")
                     .addHeader("x-http-method-override", "DELETE")
                     .addHeader("cache-control", "no-cache")
                     .build();

             Response response3 = client.newCall(request2).execute();

             String jsonData2 = response3.body().string();
             System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + jsonData2);
             JSONObject Jobject2 = new JSONObject(jsonData2);
             JSONArray Jarray = Jobject2.getJSONArray("records");
             //String cycleID = "";
             for (int i = 0; i < Jarray.length(); i++) {
                 JSONObject object = Jarray.getJSONObject(i);
                 System.out.println(object.get("Cycle_Matrix_Id__c"));
                 cycleIDProcessed = object.get("Cycle_Matrix_Id__c").toString();
                 if (cycleIDProcessed != null){
                     cycleID = cycleIDProcessed;
                 }
                 System.out.println(cycleIDProcessed);
             }

             System.out.println("Access Token: " + access_token);
             System.out.println("Cycle ID : "+ cycleIDProcessed);

             if (cycleIDProcessed==null){
                 System.out.println("Cycle ID is null waiting for 1 minute and retrying");
                 Thread.sleep(60000);
                 count++;
             }
         }

        if (cycleIDProcessed==null){
            _scenario.write("Cycle ID is : "+ cycleIDProcessed);
            _scenario.write("Test is failed due to CycleID cannot be found in the DB or User has entered invalid Auto Transaction posting. Tried for :- " + (Property.MAXIMUM_TIMEOUT_COUNT)+ " minutes");
            Assert.fail("Test is failed due to CycleID cannot be found in the DB or User has entered invalid Auto Transaction posting.");
        }
    }


    @And("^I query Matrix DB Stagging table with CycleID and wait for it to be processed$")
    public void iQueryMatrixDBStaggingTableWithCycleIDAndWaitForItToBeProcessed() throws Throwable {
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        String dbquery = "cycleID";
        DataBase_connection db = new DataBase_connection();

        boolean queryComplete1 = false;
        while (!queryComplete1) {
            // driver.navigate().refresh();
            db.DbConnection(null, dbquery, "select isProcessed, ErrorMessage, AutoID \n" +
                    "from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='" + cycleIDProcessed + "'order by AddedOn desc;\n", cycleIDProcessed);
            System.out.println("Query processed");
            System.out.println("++++++++++++++++++++++++++ " + db.IsProcessed);

            if(db.stgQueryResultStatus.equalsIgnoreCase("NO_Record") && !(db.IsProcessed.equals("1"))){
                int counntDFS = 1;
                boolean queryComplete2 = false;
                while (!queryComplete2) {
                    db.DbConnection(null, dbquery, "select isProcessed, ErrorMessage, AutoID \n" +
                            "from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='" + cycleIDProcessed + "'order by AddedOn desc;\n", cycleIDProcessed);
                    System.out.println("\n");
                    System.out.println("Query processed");
                    System.out.println("++++++++++++++++++++++++++ " + db.IsProcessed);
                    if (counntDFS==41){
                        _scenario.write("Script intentionally failed because Sales postings to Salesforce did not reach Matrix. Script waited for 10 minutes");
                        System.out.println("Script intentionally failed because Sales postings to Salesforce did not reach Matrix. Script waited for 10 minutes");
                        Assert.fail();
                    }else{
                        if(db.stgQueryResultStatus.equalsIgnoreCase("NO_Record") && !(db.IsProcessed.equals("1"))){
                            System.out.println("Stagging result set is null waiting for 15 Seconds and retrying");
                            Thread.sleep(15000);
                            counntDFS++;
                            queryComplete2 = false;
                        }else{
                            queryComplete2 = true;
                        }
                    }
                }
            }

            if ((db.IsProcessed).equals("0") || db.ErrorMessage.equals("error")) {
                System.out.println("Error");
                break;

            } else if (db.IsProcessed.equals("1")) {
                System.out.println("Status = 1");
                queryComplete1 = true;

            } else if ((db.IsProcessed).equals("2")) {
                System.out.println("Status = 2");
                Thread.sleep(15000);
                System.out.println("15 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            } else if (db.IsProcessed.isEmpty()) {
                System.out.println("Status = empty");
                Thread.sleep(15000);
                System.out.println("15 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            }
        }
    }

    @And("^I query Matrix DB Stagging table with CycleID and wait for it to be processed for \"([^\"]*)\" request$")
    public void iQueryMatrixDBStaggingTableWithCycleIDAndWaitForItToBeProcessedForRequest(int requestCount) throws Throwable {
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }

        if(requestCount>=2){
            createDbConnection dbInt = new createDbConnection();
            int counntDFS = 1;
            boolean queryComplete2 = false;
            while (!queryComplete2) {
                System.out.println("====Reading Matrix Database " + env);
                String queryDBDetails = "select COUNT(*) AS Count from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='"+cycleIDProcessed+"';";
                System.out.println("Getting Main DB records from the Matrix DB - Query : select COUNT(*) AS Count from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='"+cycleIDProcessed+"';");
                HashMap<String, String> mainDBRecordMap = dbInt.getDatabaseTableRecordsForProcessedValueChecks(env, queryDBDetails);
                if (counntDFS==41){
                    _scenario.write("Script intentionally failed because request did not reach Matrix. Script waited for 10 minutes");
                    System.out.println("Script intentionally failed because request did not reach Matrix. Script waited for 10 minutes");
                    Assert.fail();
                }else{
                    if(Integer.parseInt(mainDBRecordMap.get("Count")) != requestCount){
                        System.out.println("Matrix data row count : " +mainDBRecordMap.get("Count"));
                        System.out.println("Stagging result set is not added to matrix waiting for 15 Seconds and retrying");
                        Thread.sleep(15000);
                        counntDFS++;
                        queryComplete2 = false;
                    }else{
                        queryComplete2 = true;
                    }
                }
            }
        }

        String dbquery = "cycleID";
        DataBase_connection db = new DataBase_connection();

        boolean queryComplete1 = false;
        while (!queryComplete1) {
            // driver.navigate().refresh();
            db.DbConnection(null, dbquery, "select isProcessed, ErrorMessage, AutoID \n" +
                    "from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='" + cycleIDProcessed + "'order by AddedOn desc;\n", cycleIDProcessed);
            System.out.println("Query processed");
            System.out.println("++++++++++++++++++++++++++ " + db.IsProcessed);

            if(db.stgQueryResultStatus.equalsIgnoreCase("NO_Record") && !(db.IsProcessed.equals("1"))){
                int counntDFS = 1;
                boolean queryComplete2 = false;
                while (!queryComplete2) {
                    db.DbConnection(null, dbquery, "select isProcessed, ErrorMessage, AutoID \n" +
                            "from DFSSTAGING.DBO.CustomerTier where DFSCycleAutoID='" + cycleIDProcessed + "'order by AddedOn desc;\n", cycleIDProcessed);
                    System.out.println("\n");
                    System.out.println("Query processed");
                    System.out.println("++++++++++++++++++++++++++ " + db.IsProcessed);
                    if (counntDFS==41){
                        _scenario.write("Script intentionally failed because Sales postings to Salesforce did not reach Matrix. Script waited for 10 minutes");
                        System.out.println("Script intentionally failed because Sales postings to Salesforce did not reach Matrix. Script waited for 10 minutes");
                        Assert.fail();
                    }else{
                        if(db.stgQueryResultStatus.equalsIgnoreCase("NO_Record") && !(db.IsProcessed.equals("1"))){
                            System.out.println("Stagging result set is null waiting for 15 Seconds and retrying");
                            Thread.sleep(15000);
                            counntDFS++;
                            queryComplete2 = false;
                        }else{
                            queryComplete2 = true;
                        }
                    }
                }
            }

            if ((db.IsProcessed).equals("0") || db.ErrorMessage.equals("error")) {
                System.out.println("Error");
                break;

            } else if (db.IsProcessed.equals("1")) {
                System.out.println("Status = 1");
                queryComplete1 = true;

            } else if ((db.IsProcessed).equals("2")) {
                System.out.println("Status = 2");
                Thread.sleep(15000);
                System.out.println("15 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            } else if (db.IsProcessed.isEmpty()) {
                System.out.println("Status = empty");
                Thread.sleep(15000);
                System.out.println("15 seconds passed");
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            }
        }
    }
}

