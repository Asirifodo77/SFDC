package utilities;

import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class updateCycleEndDate {
    public String baseURL;
    public String BaseURLforUpdateSF;
    public Scenario _scenario;
    public readTestData data;
    public String cycleid;
    public getSFaccessToken token;
    public String Environment;

    //CONSTRUCTOR
    public updateCycleEndDate(Scenario _scenario) throws FileNotFoundException {
        this._scenario=_scenario;
        data = new readTestData();
        token = new getSFaccessToken();
        Environment = System.getProperty("Environment");

        if(Environment.equalsIgnoreCase("Preprod")){
            baseURL = data.readTestData("Preprod_SF_BaseURL");
            BaseURLforUpdateSF = data.readTestData("Preprod_Update_SF_BaseURL");

        } else if (Environment.equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_SF_BaseURL");
            BaseURLforUpdateSF =data.readTestData("QACore2_Update_SF_BaseURL");

        }
    }


    public void updateCycleEndDateInSF(String cardNo, String newDate) {
        String accessToken=null;
        try {
             accessToken = token.getSFaccessToken();
        } catch (Exception e) {
            System.out.println("Unable to generate access token. "+e.getMessage());
        }

        //getting the cycle id
        String cycleId=null;

        try {
            cycleId = getCycleId(cardNo,accessToken);
            System.out.println("Obtained cycle Id "+cycleId+" successfully");
        } catch (NullPointerException e) {
            System.out.println("Unable to proceed through with updating the cycle end date because there are more than 1 cycle ids present for the card number "+cardNo +" .This function only expects a card number to have a single cycle id");
            _scenario.write("Unable to proceed through with updating the cycle end date because there are more than 1 cycle ids present for the card number "+cardNo +" .This function only expects a card number to have a single cycle id");
            Assert.fail("Unable to proceed through with updating the cycle end date because there are more than 1 cycle ids present for the card number "+cardNo +" .This function only expects a card number to have a single cycle id");
        }

        //updating the SF field with API
        try {
            updateEndDate(cycleId,newDate,accessToken);
        } catch (Exception e) {
            System.out.println("Unable to update the end date due to an exception - "+e.getMessage());
            _scenario.write("Unable to update the end date due to an exception - "+e.getMessage());
            Assert.fail("Unable to update the end date due to an exception - "+e.getMessage());
        }

    }

    public String getCycleId(String cardNo, String accessToken) {
        System.out.println("=================== INSIDE GET CYCLE ID FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL ="";
        querryURL = "query/?q=SELECT Id from Membership_Cycle__c where Member__r.Membership_Card_Number__c IN ('"+cardNo+"')";

        String token = accessToken.trim();

        String URL = baseURL+querryURL;
        System.out.println("url is: "+URL);
        Response response=
                given()
                        .headers("Content-Type", "application/json" , "Authorization", token)
                        .get(URL)
                        .then()
                        .contentType(ContentType.JSON)
                        .extract().response();

        int statusCode = response.getStatusCode();
        System.out.println("Response code is : " + statusCode);
        System.out.println(response.asString());

        //Getting the number of records response has
        List<String> nodes = response.jsonPath().getList("records");
        System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " CYCLE ID NUMBERS");

        // Check if status code is 200 and transaction id count is not zero
        if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {
            if(nodes.size()>1) {
                //there are more than one cycle ids in the response
                System.out.println("There are more than one Cycle id for the card number "+cardNo);
                System.out.println(" ============= EXITING THE FUNCTION ================");
                return null;

            } else {
                cycleid= response.jsonPath().getString("records.Id").replaceAll("\\p{P}", "").trim();
                System.out.println("Cycle ID : "+ cycleid);
            }


        } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
            System.out.println("Exiting the function because there are NO Cycle Ids found for Card Number "+ cardNo);
        }
        else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
            System.out.println("Status Code : " + statusCode);
            System.out.println("Getting Cycle ID failed");
            return null;
        }
        return cycleid;
    }

    public void updateEndDate(String cycleId, String newDate, String accessToken) {

        System.out.println("=================== INSIDE UPDATE END DATE FUNCTION =========================================");

        Response response=null;

        String querryURL ="";
        querryURL = "sobjects/Membership_Cycle__c/"+cycleId;

        String requestBody=null;
        requestBody ="{\"End_Date__c\" : \""+newDate+"T23:59:59\",\"End_Date_GMT__c\": \""+newDate+"\"}";

        String token = accessToken.trim();

        String URL = BaseURLforUpdateSF+querryURL;
        System.out.println("url is: "+URL);

        try {
             response=
                    given()
                            .headers("Content-Type", "application/json" , "Authorization", token)
                            .body(requestBody)
                            .when()
                            .patch(URL)
                            .then()
                            //.contentType(ContentType.values("application/json"))
                            .extract().response();

            int statusCode = response.getStatusCode();
            System.out.println("Response code is : " + statusCode);

        } catch (Exception e) {
            System.out.println("The test was failed since PATCH call to Salesforce was not successful");
            _scenario.write("The test was failed since PATCH call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT);
            System.out.println("End date is updated successfully !");
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

    }
}
