package utilities;

import com.google.gson.JsonObject;
import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import page_objects.DSAcreateMember_pageObjects;
import page_objects.Login_pageobjects;
import page_objects.MemberValidation_pageobjects;
import page_objects.SearchMember_pageobjects;
import step_definitions.Hooks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;
import static step_definitions.API_Steps.jsonObject;

public class checkCancelledMember {

    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public String POSTrequestBody;
    public DSAcreateMember_pageObjects member;
    public DSAcreateMember_pageObjects memberInfo;
    public SoftAssert softAssert;
    public Assert hardAssert;
    public String environment;
    public Login_pageobjects login;
    public SearchMember_pageobjects searchMember;
    public MemberValidation_pageobjects sf;
    public TakeScreenshot screenshot;
    public getDataFromSF sfData;
    public String ReturnStatus="";
    public String ReturnMessage="";

    public checkCancelledMember() throws FileNotFoundException {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        environment= System.getProperty("Environment");
        member = new DSAcreateMember_pageObjects(_scenario);
        login = new Login_pageobjects(driver,_scenario);
        searchMember = new SearchMember_pageobjects(driver,_scenario);
        sf = new MemberValidation_pageobjects(driver,_scenario);
        softAssert = new SoftAssert();
        screenshot = new TakeScreenshot(driver,_scenario);
        sfData = new getDataFromSF();
    }

    public boolean isMemberCancelledOrBlackListed(String cardNumber) throws IOException, ParseException {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_GetMember_External");  // Preprod_GetMember_External    // Preprod_ETR_External
            System.out.println("-------Preprod ---------");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_GetMember_External");  // QACore2_GetMember_External   // QACore2_ETR_External
            System.out.println("-------QaCore2 ---------");
        }

        String endpointURL = data.readTestData("POS_GetMember_Endpoint_URL");
        String authorization = data.readTestData("POS_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        String requestBody="{\n" +
                "  \"CRM\" : {\n" +
                "  \"crm:CRM\" : {\n" +
                "    \"crm:Customer\" : [ {\n" +
                "      \"cus:MemberInfo\" : {\n" +
                "        \"cus:SourceSystem\" : \"Portal\"\n" +
                "      }\n" +
                "    } ],\n" +
                "    \"crm:MemberCard\" : [ {\n" +
                "      \"mc:CardInfo\" : {\n" +
                "        \"mc:CardNo\" : \"+"+cardNumber+"+\"\n" +
                "      }\n" +
                "    } ],\n" +
                "    \"crm:loyaltyService\" : {\n" +
                "      \"lts:common\" : {\n" +
                "        \"lts:POS\" : {\n" +
                "          \"pos:OutletCode\" : \"70-207\",\n" +
                "          \"pos:CashierID\" : \"0\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "}";

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization) //"Authorization", authorization
                    .body(requestBody)
                    //.auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            System.out.println("Status code : "+ statusCode);
            _scenario.write("Status code : "+ statusCode);

        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }


        //IF STATUS IS 200 OK , then extract values from the response
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {

            //Converting the response body into a String buffer
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody().asInputStream()));
            String inputLine;
            StringBuffer resp = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                resp.append(inputLine);
            }
            br.close();

            //parse the response into a JSON object

            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(resp.toString());
            jsonObject = (JSONObject) object;

            JSONObject crmCRM = null;
            JSONArray crmCustomer = null;

            try {
                //Navigating through the json nodes
                JSONObject crm = (JSONObject) jsonObject.get("CRM");
                crmCRM = (JSONObject) crm.get("crm:CRM");

               // crmCustomer = (JSONArray) crmCRM.get("crm:Customer");

            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - " + e);
                _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - " + e);
                fail();

            }


            // ############################ Start of crm:loyaltyService block #####################
            JSONObject respInfo = null;
            try {
                JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
                respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                fail();
            }

            //getting the lts:ReturnStatus
            try {
                ReturnStatus = respInfo.get("lts:ReturnStatus").toString();
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            } catch (Exception e) {
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            }

            //getting the lts:ReturnMessage
            try {
                ReturnMessage = respInfo.get("lts:ReturnMessage").toString();
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            } catch (Exception e) {
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            }

            //############################ End of crm:loyaltyService block #########################


        } else  if (statusCode!=HttpStatus.SC_OK){
            System.out.println("POST request Error. Returned the status code "+ statusCode);
            _scenario.write("POST request Error. Returned the status code "+ statusCode);

        } else if (response.getBody().toString().isEmpty() || response.getBody().toString() ==""){
            System.out.println("The response body is null or its empty");
            _scenario.write("The response body is null or its empty");
        }

        if(ReturnMessage.equalsIgnoreCase("CANCELLED MEMBER") || ReturnMessage.equalsIgnoreCase("BLACKLISTED MEMBER")) {
            return true;
        } else {
            return false;
        }

    }
}
