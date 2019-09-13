package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import property.Property;
import utilities.ReadJenkinsParameters;

import java.io.IOException;
import java.util.Map;

public class Renewal_steps {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String access_token;
    public String cycleID =null;
    public String jsonData1;
    public int count = 0;

    public Renewal_steps() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println("inside login constructor");
    }

    @And("^I query member Cycle ID and run renewal job on SF$")
    public void runRenewalJob(DataTable table) throws IOException, JSONException, InterruptedException  {
        String env = System.getProperty("Environment");
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
            url = myJsonObj.get("Preprod_SF_BaseURL").getAsString();
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = myJsonObj.get("QACore2_SF_BaseURL").getAsString();
            System.out.println("The Execution Environment is: QACORE2");
        }


        Request request2 = new Request.Builder()
                .url( url + "query/?q=SELECT Cycle_Matrix_Id__c , Id FROM Membership_Cycle__c WHERE Member__r.Membership_Card_Number__c ='" + myJsonObj.get(cardNum).getAsString() + "'ORDER BY Cycle_Matrix_Id__c desc LIMIT 1")
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
            System.out.println(object.get("Id"));
            cycleID = object.get("Id").toString();
            System.out.println(cycleID);
        }


        Thread.sleep(40000);
        System.out.println("Access Token: " + access_token);
        System.out.println("Cycle ID : "+ cycleID);


        //Run renewal job for different environment

        url = "";
        if (env.equalsIgnoreCase("Preprod")){
            url = myJsonObj.get("Preprod_Renewal_End_Point").getAsString();
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = myJsonObj.get("QACore2_Renewal_End_Point").getAsString();
            System.out.println("The Execution Environment is: QACORE2");
        }

        //"{\"cycleIds\":[{\"cycleId\" : \"a0s5D0000015TIXQA2\"}]}
        RequestBody body1 = RequestBody.create(mediaType1, "{\"cycleIds\":[{ \"cycleId\" : \""  + cycleID + "\"}]}");
        Request request1 = new Request.Builder()
                .url(url)
                .post(body1)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + access_token + "")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response1 = client.newCall(request1).execute();
        jsonData1 = response1.body().string();
        Thread.sleep(30000);
    }

    @And("^I query Salesforce DB to retrieve membership CycleID to run renewal job$")
    public void iQuerySalesforceDBToRetrieveMembershipCycleIDToRunRenewalJob(DataTable table) throws Throwable {
        String env = System.getProperty("Environment");
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
            url = myJsonObj.get("Preprod_SF_BaseURL").getAsString();
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = myJsonObj.get("QACore2_SF_BaseURL").getAsString();
            System.out.println("The Execution Environment is: QACORE2");
        }

        while(Property.MAXIMUM_TIMEOUT_COUNT != count && cycleID==null){
            Request request2 = new Request.Builder()
                    .url( url + "query/?q=SELECT Cycle_Matrix_Id__c , Id FROM Membership_Cycle__c WHERE Member__r.Membership_Card_Number__c ='" + myJsonObj.get(cardNum).getAsString() + "'ORDER BY Cycle_Matrix_Id__c desc LIMIT 1")
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
                System.out.println(object.get("Id"));
                cycleID = object.get("Id").toString();
                System.out.println(cycleID);
            }

            System.out.println("Access Token: " + access_token);
            System.out.println("Cycle ID : "+ cycleID);

            if (cycleID==null){
                System.out.println("Cycle ID is null waiting for 1 minute and retrying");
                Thread.sleep(60000);
                count++;
            }
        }

        if (cycleID==null){
            _scenario.write("Cycle ID is : "+ cycleID);
            _scenario.write("Test is failed due to CycleID cannot be found. Tried for :- " + (Property.MAXIMUM_TIMEOUT_COUNT)+ " minutes");
            Assert.fail();
        }
    }

    @And("^I run renewal job on SF$")
    public void iRunRenewalJobOnSF() throws Throwable {
        //Run renewal job for different environment
        String env = System.getProperty("Environment");
        String url = "";
        if (env.equalsIgnoreCase("Preprod")){
            url = myJsonObj.get("Preprod_Renewal_End_Point").getAsString();
            System.out.println("The Execution Environment is: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            url = myJsonObj.get("QACore2_Renewal_End_Point").getAsString();
            System.out.println("The Execution Environment is: QACORE2");
        }
        MediaType mediaType1 = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        //"{\"cycleIds\":[{\"cycleId\" : \"a0s5D0000015TIXQA2\"}]}
        RequestBody body1 = RequestBody.create(mediaType1, "{\"cycleIds\":[{ \"cycleId\" : \""  + cycleID + "\"}]}");
        Request request1 = new Request.Builder()
                .url(url)
                .post(body1)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + access_token + "")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response1 = client.newCall(request1).execute();
        jsonData1 = response1.body().string();
        Thread.sleep(180000);
    }
}
