package utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class DataCleanup {

    public JsonObject myJsonObj;
    public static String jsonAsString;
    public String URL;
    public String accessToken;
    public String baseURL;
    public Scenario _scenario;
    public String client_id;
    public String client_secret;
    public String username;
    public String password;
    public String JobId;

    //constructor class
    public DataCleanup(Scenario _scenario) throws FileNotFoundException, Exception {

        //passing the _scenario object into the DataCleanup class file so that it can accessed by any method inside this class
        this._scenario=_scenario;
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")){
            baseURL = readTestData("Preprod_SF_BaseURL");
            client_id=readTestData("Preprod_client_id");
            client_secret=readTestData("Preprod_client_secret");
            username=readTestData("Preprod");
            password=readTestData("SFDCAdmin_Pwd");

        } else if (System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = readTestData("QACore2_SF_BaseURL");
            client_id=readTestData("QACore2_client_id");
            client_secret=readTestData("QACore2_client_secret");
            username=readTestData("QACore2");
            password=readTestData("SFDCAdmin_Pwd");
        }
    }

    //---------------------


    public void DataCleanup(Scenario _scenario) throws Exception {

         accessToken = getSFaccessToken();
         System.out.println(accessToken);

        //Initiate get request and get the accessToken

    }


    //---------------------


    public String readTestData(String field) throws FileNotFoundException {

        JsonParser parser = new JsonParser();
        Object obj = parser.parse(new FileReader("src//test//resources//test_data//Test_data"));
        myJsonObj = (JsonObject) obj;
        String fieldValue = myJsonObj.get(field.trim()).getAsString();
        return fieldValue;
    }

    public String getSFaccessToken() throws Exception {

        // getting the URL for access token generation
        URL=readTestData("Preprod_URL1");

        String grant_type="password";


        try {
            Response response=
                    given()
                            .queryParam("grant_type", grant_type)
                            .queryParam("client_id", client_id)
                            .queryParam("client_secret", client_secret)
                            .queryParam("username" , username)
                            .queryParam("password" , password)
                            .post(URL)
                            .then()
                            .contentType(ContentType.JSON)
                            .extract().response();

            System.out.println("Get accessToken status == "+response.getStatusCode());

            //getting access token from the json object
            String token = response.jsonPath().getString("access_token");
            String token_type = response.jsonPath().getString("token_type");
            accessToken = token_type+" "+token;
            //System.out.println(accessToken);
        } catch (Exception e) {
            System.out.println("An exception occurred when generating access token - "+e.getMessage());
            throw e;
        }

        return accessToken;
    }


    public String getMemberID(String Membership_Card_Number, String accessToken) throws Exception {

        System.out.println("=================== INSIDE GET MEMBER ID FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL ="";
        querryURL = "query/?q=SELECT Id from Contact where Membership_Card_Number__c='"+Membership_Card_Number+"'";


        String token = accessToken.trim();

        String memberID ="";
        String URL = baseURL+querryURL;
        System.out.println("url is: "+URL);
        Response response=
                given()
                        .headers("Content-Type", "application/json" , "Authorization", token)
                        .get(URL)
                        .then()
                        .contentType(ContentType.JSON)
                        .extract().response();

        System.out.println("Response code is : " + response.getStatusCode());
        System.out.println(response.asString());

        //if no Members are found
            if(response.jsonPath().getString("totalSize").equalsIgnoreCase("0")){
                return memberID;
            } else {
                memberID = response.jsonPath().getString("records.Id");
                memberID = memberID.replaceAll("\\p{P}", "");
                System.out.println("Found Member id: "+ memberID);
                return memberID;
            }

    }

    public List<String> getRequestID( String Membership_Card_Number, String accessToken) {
        System.out.println("=================== INSIDE GET REQUEST ID FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL ="";
        querryURL = "query/?q=SELECT Id,Original_Card_Number__c,Membership_Number__c,New_Card_Number__c from Case Where Contact.Membership_Card_Number__c='"+Membership_Card_Number+"'";

        List<String> requestID = new ArrayList<>();
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
        System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " REQUEST ID NUMBERS");

        // Check if status code is 200 and transaction id count is not zero
        if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

            //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));

            for (int i = 0; i < nodes.size(); i++) {
                String path = "records[" + i + "].Id";
                requestID.add(i, response.jsonPath().getString(path).replaceAll("\\p{P}", ""));
                System.out.println("Request ID : "+ requestID.get(i));
            }
            return requestID;

        } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
            System.out.println("Exiting the function because there are NO Request Ids found for Card Number "+ Membership_Card_Number);
        }
        else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
            System.out.println("Status Code : " + statusCode);
            System.out.println("Getting Request ID failed");
            return null;
        }
        return requestID;
    }

    public List<String> getTransactionID(String MemberID, String accessToken) throws Exception {

        System.out.println("======================= INSIDE GET TRANSACTION ID FUNCTION ===============================");

        //defining variable for transaction ID
        List<String> transactionID = new ArrayList<>();

        if (MemberID.isEmpty() || MemberID=="" || MemberID==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE THERE ARE NO MEMBER ASSOCIATED WITH THE CARD NUMBER");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT id from Asset where ContactId ='" + MemberID + "'";
            String token = accessToken.trim();

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            List<String> nodes = response.jsonPath().getList("records");
            System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " TRANSACTION IDs");

            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));

                for (int i = 0; i < nodes.size(); i++) {
                    String path = "records[" + i + "].Id";
                    transactionID.add(i, response.jsonPath().getString(path).replaceAll("\\p{P}", ""));
                    System.out.println("Transaction Id: "+transactionID.get(i));
                }
                //return transactionID;

            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){
                System.out.println("Exiting the function beacause there are NO transaction IDs found for Member "+ MemberID);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) {
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Transaction ID failed");
              //  return transactionID;
            }

        }
        return transactionID;
    }

    public List<String> getTransactionLineItemID(List<String> transactionID, String accessToken) throws Exception {

        System.out.println("================================== INSIDE GET TRANSACTION LINE ITEM ID FUNCTION ================================= ");
        //defining variable for transaction line items
        List<String> transactionLineItems = new ArrayList<>();

        //
        System.out.println("===== SIZE OF TRANSACTION IDs passed by the variable ==== "+transactionID.size());



        // creating the variable to store the response and status code
        Response response = null;
        int statusCode = 0;

        if(transactionID.size()<= 0 || transactionID.isEmpty() || transactionID==null) {
            System.out.println("NO TRANSACTION IDs ARE FOUND. EXITING THE FUNCTION");
        } else {

            // defining the rest of the URL with querry parameters


            String querryURL = "";
            //checking if there are multiple transaction ids passed. if so, changing the querry accordingly
            if (transactionID.size() > 1) {
                // set querry with "IN" keyword in SQL
                String valueSet = "";

                for (int countOfTransIDs = 0; countOfTransIDs < transactionID.size(); countOfTransIDs++) {
                    if (countOfTransIDs != transactionID.size() - 1) {
                        valueSet = valueSet + "'" + transactionID.get(countOfTransIDs) + "',";
                    } else {
                        valueSet = valueSet + "'" + transactionID.get(countOfTransIDs) + "'";
                    }
                }

                System.out.println("VALUE SET ============= " + valueSet);
                querryURL = "query/?q=SELECT id from Asset where ParentId in (" + valueSet + ")";

            } else {
                // set querry with "=" keyword in SQL
                querryURL = "query/?q=SELECT id from Asset where ParentId='" + transactionID.get(0) + "'";
            }

            String token = accessToken.trim();

            String URL = baseURL + querryURL;
            System.out.println(URL);

            //sending the request and storing the response.
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            //getting the status code
            statusCode = response.getStatusCode();


            // Check if status code is 200
            if (statusCode == HttpStatus.SC_OK) {

                //counting number of transaction line items returned
                List<String> nodes = response.jsonPath().getList("records");
                System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " TRANSACTION LINE ITEMS IDs");

                //looping through and adding each transaction line item Ids to Array list
                for (int i = 0; i < nodes.size(); i++) {
                    String path = "records[" + i + "].Id";
                    transactionLineItems.add(i, response.jsonPath().getString(path).replaceAll("\\p{P}", ""));
                    System.out.println("Transaction Line Item : "+ transactionLineItems.get(i));
                }

                return transactionLineItems;

            } else if (statusCode == HttpStatus.SC_NOT_FOUND){
                System.out.println("====== Status Code " + statusCode);
                System.out.println("record is not found or it has already been deleted");
                return null;
            } else {
                System.out.println("====== Status Code " + statusCode);
                System.out.println("Getting Transaction line items FAILED");
                return null;
            }

        }
            return transactionLineItems;

    }

    public void deleteTransactionLineItem(List<String> transactionLineItems, String accessToken) throws Exception{

        System.out.println("=================================== INSIDE DELETE TRANSACTION LINE ITEM FUNCTION ========================================");

        //Checking if trnasaction line items are not 0
        if(transactionLineItems.size()<=0 || transactionLineItems.isEmpty() || transactionLineItems==null) {
            System.out.println("THERE ARE NO TRANSACTION LINE ITEM IDs TO BE DELETED. ABORTING THE DELETION !");
            _scenario.write("There are no Transaction Line Items to be deleted.");
            return;
        }
        System.out.println("===== PREPARING TO DELETE ==== "+transactionLineItems.size()+" TRANSACTION LINE ITEMS");


            // creating the variable to store the response and status code
            Response response = null;
            int statusCode = 0;


            // defining the rest of the URL with querry parameters


            String querryURL="";
            String token = accessToken.trim();

            //Looping through all transaction line item ids and deleting one at a time
            for (int recordCount=0; recordCount<transactionLineItems.size();recordCount++ ){
                try {

                querryURL = "sobjects/Asset/"+transactionLineItems.get(recordCount);

                String URL = baseURL + querryURL;
                System.out.println("The DELETE url is : "+URL);

                //sending the request and storing the response.
                response = given()
                        .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                        .delete(URL)
                        .then()
                        //.contentType(ContentType.fromContentType("application/json;charset=UTF-8"))
                        .extract().response();

                //getting the status code
                statusCode = response.getStatusCode();
                    System.out.println("status code returned is "+statusCode);

                    // Check if status code is 200
                    if (statusCode == HttpStatus.SC_NO_CONTENT) {

                        System.out.println(" -- THE TRANSACTION LINE ITEM ID " + transactionLineItems.get(recordCount) + " HAS BEEN DELETED SUCCESSFULLY");
                        _scenario.write(" -- The Transaction line Item "+ transactionLineItems.get(recordCount) + " has been deleted successfully");

                    } else {
                        System.out.println("====== Status Code " + statusCode);
                        System.out.println("====== Deleting Transaction line item id "+transactionLineItems.get(recordCount)+" failed");
                        _scenario.write("====== Deleting Transaction line item " +transactionLineItems.get(recordCount)+" failed");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void deleteRequest(List<String> requestID, String accessToken) {
        System.out.println("=================================== INSIDE DELETE REQUEST FUNCTION ========================================");

        //Checking if trnasaction line items are not 0
        if(requestID.size()<=0 || requestID.isEmpty() || requestID==null) {
            System.out.println("THERE ARE NO REQUEST IDs TO BE DELETED. ABORTING THE DELETION !");
            _scenario.write("There are no Request Id's to be deleted.");
            return;
        }
        System.out.println("===== PREPARING TO DELETE ==== "+requestID.size()+" REQUESTS ITEMS");


        // creating the variable to store the response and status code
        Response response = null;
        int statusCode = 0;


        // defining the rest of the URL with querry parameters


        String querryURL="";
        String token = accessToken.trim();

        //Looping through all transaction line item ids and deleting one at a time
        for (int recordCount=0; recordCount<requestID.size();recordCount++ ){
            try {

                querryURL = "sobjects/Case/"+requestID.get(recordCount);

                String URL = baseURL + querryURL;
                System.out.println("The DELETE url is : "+URL);

                //sending the request and storing the response.
                response = given()
                        .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                        .delete(URL)
                        .then()
                        //.contentType(ContentType.fromContentType("application/json;charset=UTF-8"))
                        .extract().response();

                //getting the status code
                statusCode = response.getStatusCode();
                System.out.println("status code returned is "+statusCode);

                // Check if status code is 200
                if (statusCode == HttpStatus.SC_NO_CONTENT) {

                    System.out.println(" -- THE REQUEST ID " + requestID.get(recordCount) + " HAS BEEN DELETED SUCCESSFULLY");
                    _scenario.write(" -- The Request ID "+ requestID.get(recordCount) + " has been deleted successfully");

                } else {
                    System.out.println("====== Status Code " + statusCode);
                    System.out.println("====== Deleting REQUEST ID  "+requestID.get(recordCount)+" failed");
                    _scenario.write("====== Deleting Request ID " +requestID.get(recordCount)+" failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } //End of for loop
    }

    public void deleteTransactionItem(List<String> transactionID, String accessToken) throws Exception{

        System.out.println("=================================== INSIDE DELETE TRANSACTION ITEM FUNCTION ========================================");

        //Checking if trnasaction line items are not 0
        if(transactionID.size()<=0 || transactionID.isEmpty() || transactionID==null) {
            System.out.println("THERE ARE NO TRANSACTION ITEM IDs TO BE DELETED. ABORTING THE DELETION !");
            _scenario.write("There are no Transaction Items to be deleted.");
            return;
        }
        System.out.println("===== PREPARING TO DELETE ==== "+transactionID.size()+" TRANSACTION ITEMS");


        // creating the variable to store the response and status code
        Response response = null;
        int statusCode = 0;


        // defining the rest of the URL with querry parameters


        String querryURL="";
        String token = accessToken.trim();

        //Looping through all transaction line item ids and deleting one at a time
        for (int recordCount=0; recordCount<transactionID.size();recordCount++ ){
            try {

                querryURL = "sobjects/Asset/"+transactionID.get(recordCount);

                String URL = baseURL + querryURL;
                System.out.println("The DELETE url is : "+URL);

                //sending the request and storing the response.
                response = given()
                        .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                        .delete(URL)
                        .then()
                        //.contentType(ContentType.fromContentType("application/json;charset=UTF-8"))
                        .extract().response();

                //getting the status code
                statusCode = response.getStatusCode();
                System.out.println("status code returned is "+statusCode);

                // Check if status code is 200
                if (statusCode == HttpStatus.SC_NO_CONTENT) {

                    System.out.println(" -- THE TRANSACTION ITEM ID " + transactionID.get(recordCount) + " HAS BEEN DELETED SUCCESSFULLY");
                    _scenario.write(" -- The Transaction Item "+ transactionID.get(recordCount) + " has been deleted successfully");


                } else {
                    System.out.println("====== Status Code " + statusCode);
                    System.out.println("====== Deleting Transaction item id "+transactionID.get(recordCount)+" failed");
                    _scenario.write("====== Deleting Transaction item id " +transactionID.get(recordCount)+" failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    public void deleteMember(String memberID, String accessToken) throws Exception{

        System.out.println("=================================== INSIDE DELETE MEMBER FUNCTION ========================================");

        //Checking if member id has a value
        if(memberID.isEmpty() || memberID=="" || memberID==null) {
            System.out.println("THERE ARE NO MEMBERS IDs TO BE DELETED. ABORTING THE DELETION !");
            _scenario.write("There are no members to be deleted.");
            return;
        }
        System.out.println("===== PREPARING TO DELETE MEMBER ==== "+memberID);


        // creating the variable to store the response and status code
        Response response = null;
        int statusCode = 0;


        // defining the rest of the URL with querry parameters


        String querryURL="";
        String token = accessToken.trim();

        //Looping through all transaction line item ids and deleting one at a time
        //for (int recordCount=0; recordCount<transactionID.size();recordCount++ ){
            try {

                querryURL = "sobjects/Contact/"+memberID;

                String URL = baseURL + querryURL;
                System.out.println("The DELETE url is : "+URL);

                //sending the request and storing the response.
                response = given()
                        .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                        .delete(URL)
                        .then()
                        //.contentType(ContentType.fromContentType("application/json;charset=UTF-8"))
                        .extract().response();

                //getting the status code
                statusCode = response.getStatusCode();
                System.out.println("status code returned is "+statusCode);

                // Check if status code is 200
                if (statusCode == HttpStatus.SC_NO_CONTENT) {

                    System.out.println(" -- THE MEMBER ID " + memberID + " HAS BEEN DELETED SUCCESSFULLY");
                    _scenario.write(" -- The Member" + memberID + " has been deleted successfully");

                } else {
                    System.out.println("====== Status Code " + statusCode);
                    System.out.println("====== Deleting Member id "+ memberID+" failed");
                    _scenario.write("====== Deleting Member "+ memberID+" failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        //}


    }

    public void deleteJob(String JobID, String accessToken) {
        System.out.println("=================================== INSIDE DELETE JOB FUNCTION ========================================");

        //Checking if member id has a value
        if(JobID.isEmpty() || JobID=="" || JobID==null) {
            System.out.println("THERE ARE NO JOBS TO BE DELETED. ABORTING THE DELETION !");
            _scenario.write("There are no jobs to be deleted.");
            return;
        }
        System.out.println("===== PREPARING TO DELETE JOB ==== "+JobID);


        // creating the variable to store the response and status code
        Response response = null;
        int statusCode = 0;


        // defining the rest of the URL with querry parameters


        String querryURL="";
        String token = accessToken.trim();

        //Looping through all transaction line item ids and deleting one at a time
        //for (int recordCount=0; recordCount<transactionID.size();recordCount++ ){
        try {

            querryURL = "sobjects/Job__c/"+JobID;

            String URL = baseURL + querryURL;
            System.out.println("The DELETE url is : "+URL);

            //sending the request and storing the response.
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .delete(URL)
                    .then()
                    //.contentType(ContentType.fromContentType("application/json;charset=UTF-8"))
                    .extract().response();

            //getting the status code
            statusCode = response.getStatusCode();
            System.out.println("status code returned is "+statusCode);

            // Check if status code is 200
            if (statusCode == HttpStatus.SC_NO_CONTENT) {

                System.out.println(" -- THE JOB ID " + JobID + " HAS BEEN DELETED SUCCESSFULLY");
                _scenario.write(" -- The JOB" + JobID + " has been deleted successfully");

            } else {
                System.out.println("====== Status Code " + statusCode);
                System.out.println("====== Deleting Job id "+ JobID +" failed");
                _scenario.write("====== Deleting Job "+ JobID +" failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJobID(String FileRef, String accessToken) {
        System.out.println("======================= INSIDE GET JOB ID FUNCTION  ===============================");

       // List<String> jobIdNumber = new ArrayList<>();

        if (FileRef.isEmpty() || FileRef=="" || FileRef==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN FILE REF IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Id, Name, Status__c FROM Job__c WHERE File_Name__c = '"+FileRef+"' ORDER BY CreatedDate DESC LIMIT 1";
            String token = accessToken.trim();

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();


            List<String> nodes = response.jsonPath().getList("records");
            System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " JOB IDs");


            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));


                    String path = "records.Id";
                    JobId = response.jsonPath().getString(path).replaceAll("\\p{P}", "").trim();
                    System.out.println("Job ID : "+ JobId);

                return JobId;

            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are NO Jobs found for FileRef "+ FileRef);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Job id failed");
                return null;
            }

        }
        return JobId;
    }

    public List<String> getTransactionNumber(String MemberID, String accessToken){
        System.out.println("======================= INSIDE GET TRANSACTION NUMBER FUNCTION (TO QUERY STAGING TABLE) ===============================");

        List<String> transactionNumber = new ArrayList<>();

        if (MemberID.isEmpty() || MemberID=="" || MemberID==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN MEMBER ID IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Name from Asset where ContactId ='" + MemberID + "'";
            String token = accessToken.trim();

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();


            List<String> nodes = response.jsonPath().getList("records");
            System.out.println(" -- THE RESPONSE HAS " + nodes.size() + " TRANSACTION NUMBERS");


            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));

                for (int i = 0; i < nodes.size(); i++) {
                    String path = "records[" + i + "].Name";
                    transactionNumber.add(i, response.jsonPath().getString(path).replaceAll("\\p{P}", ""));
                    System.out.println("Transaction Number : "+ transactionNumber.get(i));
                }
                return transactionNumber;

            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are NO transaction Numbers found for Member "+ MemberID);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Transaction Number failed");
                return null;
            }

        }
        return transactionNumber;

    }

    public void deleteSalesLineInStaging (List<String> transactionNumber ,String environment) throws FileNotFoundException, SQLException, ClassNotFoundException {
        System.out.println(" ====================================== INSIDE DELETE SALES LINE IN STAGING FUNCTION ============================================");

        if (transactionNumber.isEmpty() || transactionNumber == null) {
            System.out.println("NO SALES LINE RECORDS ARE AVAILABLE TO DELETE. EXITING THE FUNCTION");
            _scenario.write("No Sales Line records are available to be deleted.");
        } else {
            //get the number of transaction numbers to be deleted and loop through that
            for (int recordCount = 0; recordCount < transactionNumber.size(); recordCount++) {

                //querry db and check if record exists
                createDbConnection dbConnection = new createDbConnection();
                String readQuery = "Select * from  [DFSStaging].dbo.SalesLine where TransactionNumber='" + transactionNumber.get(recordCount) + "'";
                String deleteQquery = "delete from [DFSStaging].dbo.SalesLine where TransactionNumber='" + transactionNumber.get(recordCount) + "'";
                ResultSet result = dbConnection.queryDB(environment, readQuery);

                //if the records available
                if (result.next()) {
                    // go ahead and delete the record
                    dbConnection.deleteRow(environment, deleteQquery);

                    //check if record has been deleted
                    ResultSet verifyResult = dbConnection.queryDB(environment, readQuery);

                    if (verifyResult.next()) {
                        System.out.println("FAIL TO DELETE Sales Line with Transaction Number : " + transactionNumber.get(recordCount));
                        _scenario.write("FAIL TO DELETE Sales Line with Transaction Number : " + transactionNumber.get(recordCount));
                    } else {
                        System.out.println("Successfully DELETE Sales Line with Transaction Number : " + transactionNumber.get(recordCount));
                        _scenario.write("Successfully DELETE Sales Line with Transaction Number : " + transactionNumber.get(recordCount));
                    }

                } else {
                    System.out.println("No Sales Line record found in Database for Transaction Number : " + transactionNumber.get(recordCount));
                    _scenario.write("No Sales Line record found in Database for Transaction Number : " + transactionNumber.get(recordCount));
                }
            }  // end of for loop
    } //end of else
    }

    public void deleteSalesHeaderInStaging(List<String> transactionNumber ,String environment) throws FileNotFoundException, SQLException, ClassNotFoundException {

            System.out.println(" ====================================== INSIDE DELETE SALES HEADER IN STAGING FUNCTION============================================");

        if (transactionNumber.isEmpty() || transactionNumber == null) {
            System.out.println("NO SALES HEADER RECORDS ARE AVAILABLE TO DELETE. EXITING THE FUNCTION");
            _scenario.write("No Sales Header records are available to be deleted.");
        } else {
            //get the number of transaction numbers to be deleted and loop through that
            for (int recordCount = 0; recordCount < transactionNumber.size(); recordCount++) {

                //querry db and check if record exists
                createDbConnection dbConnection = new createDbConnection();

                String readQuery = "Select * from  [DFSStaging].dbo.SalesHeader where TransactionNumber='" + transactionNumber.get(recordCount) + "'";
                String deleteQquery = "delete from [DFSStaging].dbo.SalesHeader where TransactionNumber='" + transactionNumber.get(recordCount) + "'";

                // search for the record to be deleted
                ResultSet result = dbConnection.queryDB(environment, readQuery);

                //if records are available
                if (result.next()) {
                    // go ahead and delete the record
                    dbConnection.deleteRow(environment, deleteQquery);

                    //check if record has been deleted
                    ResultSet verifyResult = dbConnection.queryDB(environment, readQuery);

                    if (verifyResult.next()) {
                        System.out.println("FAIL TO DELETE Sales Header with Transaction Number : " + transactionNumber.get(recordCount));
                        _scenario.write("FAIL TO DELETE Sales Header with Transaction Number : " + transactionNumber.get(recordCount));
                    } else {
                        System.out.println("Successfully DELETED Sales Header with Transaction Number : " + transactionNumber.get(recordCount));
                        _scenario.write("Successfully DELETED Sales Header with Transaction Number : " + transactionNumber.get(recordCount));
                    }

                } else {
                    System.out.println("No Sales Header record found in Database for Transaction Number: " + transactionNumber.get(recordCount));
                    _scenario.write("No Sales Header record found in Database for Transaction Number : " + transactionNumber.get(recordCount));
                }
            }  // end of for loop
        } // end of else
        }

        public void deleteSalesPaymentInStaging(List<String> receiptNumber ,String environment) throws FileNotFoundException, SQLException, ClassNotFoundException {

            System.out.println(" ====================================== INSIDE DELETE SALES PAYMENT IN STAGING FUNCTION============================================");

            if (receiptNumber.isEmpty() || receiptNumber == null) {
                System.out.println("NO SALES PAYMENT RECORDS ARE AVAILABLE TO DELETE. EXITING THE FUNCTION");
                _scenario.write("No Sales Payment records are available to be deleted.");
            } else {
                //get the number of transaction numbers to be deleted and loop through that
                for (int recordCount = 0; recordCount < receiptNumber.size(); recordCount++) {

                    //querry db and check if record exists
                    createDbConnection dbConnection = new createDbConnection();

                    String readQuery = "Select * from  [DFSStaging].dbo.SalesPayment where ReceiptNumber='" + receiptNumber.get(recordCount) + "'";
                    String deleteQquery = "delete from [DFSStaging].dbo.SalesPayment where ReceiptNumber='" + receiptNumber.get(recordCount) + "'";

                    // search for the record to be deleted
                    ResultSet result = dbConnection.queryDB(environment, readQuery);

                    //if records are available
                    if (result.next()) {
                        // go ahead and delete the record
                        dbConnection.deleteRow(environment, deleteQquery);

                        //check if record has been deleted
                        ResultSet verifyResult = dbConnection.queryDB(environment, readQuery);

                        if (verifyResult.next()) {
                            System.out.println("FAIL TO DELETE Sales Payment with Transaction Number : " + receiptNumber.get(recordCount));
                            _scenario.write("FAIL TO DELETE Sales Payment with Transaction Number : " + receiptNumber.get(recordCount));
                        } else {
                            System.out.println("Successfully DELETED Sales Payment with Transaction Number : " + receiptNumber.get(recordCount));
                            _scenario.write("Successfully DELETED Sales Payment with Transaction Number : " + receiptNumber.get(recordCount));
                        }

                    } else {
                        System.out.println("No Sales Payment record found in Database for Transaction Number: " + receiptNumber.get(recordCount));
                        _scenario.write("No Sales Payment record found in Database for Transaction Number : " + receiptNumber.get(recordCount));
                    }
                }  // end of for loop
            } // end of else

        }

        public void deleteDataInMatrix(String cardNumber, String environment) throws FileNotFoundException, SQLException, ClassNotFoundException {
            System.out.println(" ====================================== INSIDE DELETE MATRIX DATA FUNCTION============================================");

            if (cardNumber.isEmpty() || cardNumber == null) {
                System.out.println("NO SALES CARD NUMBERS ARE AVAILABLE TO INITIATE DELETE JOB. EXITING THE FUNCTION");
                _scenario.write("No Sales Card Number available to initiate deletion stored procedure");
            } else {
                //get the number of transaction numbers to be deleted and loop through that


                    //querry db and check if record exists
                    createDbConnection dbConnection = new createDbConnection();

                   // String readQuery = "Select * from  [DFSStaging].dbo.SalesPayment where TransactionNumber='" + receiptNumber.get(recordCount) + "'";
                    String matrixDeleteQquery = "EXEC [MatrixTpReward].dbo.Deletion_AutomationScript '"+cardNumber+"'";


                ResultSet result = null;
                try {
                    result = dbConnection.queryDB(environment,matrixDeleteQquery);
                } catch (Exception e) {
                    System.out.println("Test stopped. Unable to delete data in Matrix due to-"+e.getMessage());
                    _scenario.write("Test stopped. Unable to delete data in Matrix due to-"+e.getMessage());
                    Assert.fail("Test stopped. Unable to delete data in Matrix due to-"+e.getMessage());
                }

                if(result==null){
                        System.out.println("Stored procedure executed and records were deleted successfully");
                        _scenario.write("Stored procedure executed and records were deleted successfully");
                        System.out.println("Printing Result set -- ");
                        System.out.println(result);
                    }else {
                        System.out.println("Stored Procedure execution failed");
                        _scenario.write("Stored Procedure execution failed");
                        System.out.println("Printing Result set -- ");
                        System.out.println(result);
                    }

                    // search for the record to be deleted


            } // end of else
        }

}
