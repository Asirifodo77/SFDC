package page_objects.Cobrand;

import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import page_objects.DSAcreateMember_pageObjects;
import property.Property;
import utilities.*;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Cobrand_pageObjects {
    public Scenario _scenario;
    public readTestData data;
    public getSFaccessToken token;
    public JSONObject MemberInfoJsonObj;
    public JSONObject jsonObject;
    public String createCobrandMmeberReturnMessage;
    public String FileRef;
    public String FirstName;
    public String LastName;
    public String JobId;
    public String JobName;
    public String JobStatus;
    public String baseURL;
    public String client_id;
    public String client_secret;
    public String username;
    public String password;
    public DSAcreateMember_pageObjects dataConverter;
    public String NonPurchaseFlag;
    public String CycleId;
    public String MovementType;
    public String Remarks;
    public String CardNo;




    //constructor
    public Cobrand_pageObjects(Scenario _scenario) throws FileNotFoundException {
        this._scenario=_scenario;
        data = new readTestData();
        token = new getSFaccessToken();
        dataConverter = new DSAcreateMember_pageObjects(_scenario);


        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")){
            baseURL = data.readTestData("Preprod_SF_BaseURL");
            client_id=data.readTestData("Preprod_client_id");
            client_secret=data.readTestData("Preprod_client_secret");
            username=data.readTestData("Preprod");
            password=data.readTestData("SFDCAdmin_Pwd");

        } else if (System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_SF_BaseURL");
            client_id=data.readTestData("QACore2_client_id");
            client_secret=data.readTestData("QACore2_client_secret");
            username=data.readTestData("QACore2");
            password=data.readTestData("SFDCAdmin_Pwd");
        }
    }

    public String readRequestFromCreateCobrandMmeberJsonFile(String FilePathTag) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(FilePathTag));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();

        } catch (FileNotFoundException FileNotFound) {
            System.out.println("Unable to find the Json file : "+FileNotFound);
            _scenario.write("Unable to find the Json file : "+FileNotFound);
            Assert.fail();

        } catch(Exception e) {
            System.out.println("Exception occurred during the reading of Json file - "+e);
            _scenario.write("Exception occurred during the reading of Json file - "+e);
            Assert.fail();
        }
        if(result.isEmpty() || result=="") {
            System.out.println("The Json file does not contain a valid request body.");
            _scenario.write("The Json file does not contain a valid request body.");
            Assert.fail();
        }
        else {
            System.out.println("Reading Json request body is Successful !");
            _scenario.write("Reading Json request body is Successful !");
        }
        return result;
    }

    public void createCobrandMember(String requestBody) throws FileNotFoundException {
        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_Cobrand_BaseURL");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_Cobrand_BaseURL");
        }

        String endpointURL = data.readTestData("Cobrand_Endpoint_URL");
        String accessToken = null;
        try {
            accessToken = token.getSFaccessToken();
        } catch (Exception e) {
            System.out.println("Unable to generate access token. Exception - "+e.getMessage());
        }

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", accessToken)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE RESPONSE IN CUCUMBER REPORT
            _scenario.write("=============== The API Response for Cobrand create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("=============== End of API Response body ==========================");

            System.out.println("Status code : "+ statusCode);

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

        //EVEN IF STATUS IS 200 OK , STILL VALIDATE IF RETURN MESSAGE IS - SUCCESS
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {
            try {
                createCobrandMmeberReturnMessage = response.jsonPath().getString("Message");
                System.out.println("Return message from Cobrand create member response - "+createCobrandMmeberReturnMessage);
            } catch (Exception e) {
                System.out.println("Unable to read the Message returned from Cobrand create member response.");
                _scenario.write("Unable to read the Message returned from Cobrand create member response.");
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read the Message returned from Cobrand create member response.");
            }
        } else {
            System.out.println("Cobrand create member response body is empty.");
            _scenario.write("Cobrand create member response body is empty.");
            Assert.fail("TEST FAILED INTENTIONALLY !. Cobrand create member response body is empty. Unable to proceed ahead");
        }

        try {
            Assert.assertEquals(createCobrandMmeberReturnMessage.toLowerCase(), Property.EXPECTED_COBRAND_CREATE_MEMBER_RETURN_MESSAGE.toLowerCase());
            System.out.println("Cobrand Member created Successfully");
        } catch (AssertionError e) {
            System.out.println("Cobrand create member response does not contain Success message. Actual message - "+createCobrandMmeberReturnMessage);
            _scenario.write("Cobrand create member response does not contain Success message. Actual message - "+createCobrandMmeberReturnMessage);
            Assert.fail("TEST FAILED INTENTIONALLY !. Cobrand create member response does not contain Success message. Actual message - "+createCobrandMmeberReturnMessage);
        }

    }

    public void readCobrandJsonNodes(JSONObject jsonObject) {
        JSONArray memberList = null;
        try {
            memberList = (JSONArray) jsonObject.get("Members");
        } catch (Exception e) {
            System.out.println("There is no 'Member' data in the json file. Unable to proceed through");
            Assert.fail("TEST FAILED INTENTIONALLY !. There is no 'Member' data in the json file. Unable to proceed through");
        }

        for(int i=0;i<memberList.size();i++) {
            JSONObject Member =   (JSONObject) memberList.get(i);

            //READING FILEREF VALUE
            try {
                FileRef = Member.get("FileRef").toString();
            } catch (Exception e) {
                System.out.println("Unable to read the 'FileRef' value from the Json file. Exception - "+e.getMessage());
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read the 'FileRef' value from the Json file. Exception - "+e.getMessage());
            }

            //READING FIREST NAME VALUE
            try {
                FirstName = Member.get("FirstName").toString();
            } catch (Exception e) {
                System.out.println("Unable to read the 'FirstName' value from the Json file. Exception - "+e.getMessage());
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read the 'FirstName' value from the Json file. Exception - "+e.getMessage());
            }

            //READING LAST NAME VALUE
            try {
                LastName = Member.get("LastName").toString();
            } catch (Exception e) {
                System.out.println("Unable to read the 'LastName' value from the Json file. Exception - "+e.getMessage());
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read the 'LastName' value from the Json file. Exception - "+e.getMessage());
            }

        }
    }

    public void getElementValuesFromCobrandJsonFile(String env) {
        try {

            readTestData testData = new readTestData();

            JSONParser jsonParser = new JSONParser();

            File file = new File(testData.readTestData(getCobrandCreateMemberJsonPath(env)));

            Object object = jsonParser.parse(new FileReader(file));

            jsonObject = (JSONObject) object;
            readCobrandJsonNodes(jsonObject);
            // parseJson(jsonObject);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCobrandCreateMemberJsonPath(String env) {
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath= "Cobrand_CreateMember_RequestBody_Filepath_Preprod";
        } else if (env.equalsIgnoreCase("QACore2")) {
            filepath = "Cobrand_CreateMember_RequestBody_Filepath_QACore2";
        }
        return filepath;
    }

    public void getJobDetails(String FileRef) throws Exception {
        System.out.println("======================= INSIDE GET JOB ID FUNCTION  ===============================");

        // List<String> jobIdNumber = new ArrayList<>();

        if (FileRef.isEmpty() || FileRef=="" || FileRef==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN FILE REF IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Id, Name, Status__c FROM Job__c WHERE File_Name__c = '"+FileRef+"' ORDER BY CreatedDate DESC LIMIT 1";
            //String token
            String accessToken=null;
            try {
                accessToken = token.getSFaccessToken();
            } catch (Exception e) {
                System.out.println("Unable to generate access token. Exception - "+e.getMessage());
            }

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", accessToken)
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


                try {
                    JobId = response.jsonPath().getString("records.Id").replaceAll("\\p{P}", "").trim();
                    System.out.println("Job ID : "+ JobId);
                } catch (Exception e) {
                    System.out.println("Unable to read Job Id from response. "+e.getMessage());
                }

                try {
                    JobName = response.jsonPath().getString("records.Name").replaceAll("\\p{P}", "").trim();
                    System.out.println("Job Name : "+ JobName);
                } catch (Exception e) {
                    System.out.println("Unable to read Job Name from response. "+e.getMessage());
                }

                try {
                    JobStatus = response.jsonPath().getString("records.Status__c").replaceAll("\\p{P}", "").trim();
                    System.out.println("Job Status : "+ JobStatus);
                } catch (Exception e) {
                    System.out.println("Unable to read Job Status from response. "+e.getMessage());
                }


            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are NO Jobs found for FileRef "+ FileRef);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Job details failed");
            }

        }
    }

    public String getNonPurchaseFlagFromSF(String cardNo) {
        System.out.println("======================= INSIDE GET NON PURCHASE FLAG FUNCTION  ===============================");

        // List<String> jobIdNumber = new ArrayList<>();

        if (cardNo.isEmpty() || cardNo=="" || cardNo==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN CARD NUMBER IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Non_Purchase__c from Contact where Membership_Card_Number__c ='"+cardNo+"'";
            //String token
            String accessToken=null;
            try {
                accessToken = token.getSFaccessToken();
            } catch (Exception e) {
                System.out.println("Unable to generate access token. Exception - "+e.getMessage());
            }

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", accessToken)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();


            List<String> nodes = response.jsonPath().getList("records");


            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));


                try {
                    NonPurchaseFlag = response.jsonPath().getString("records.Non_Purchase__c").replaceAll("\\p{P}", "").trim();
                } catch (Exception e) {
                    System.out.println("Unable to read Non Purchase Flag value from response. "+e.getMessage());
                }



            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are NO Non Purchase Flag records found for card number "+ cardNo);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Non Purchase Flag failed");
            }

        }
        return NonPurchaseFlag;
    }

    public void getMembershipCycleDetailsFromSF(String cardNo) {
        System.out.println("======================= INSIDE GET MEMBERSHIP CYCLE DETAILS FUNCTION  ===============================");

        // List<String> jobIdNumber = new ArrayList<>();

        if (cardNo.isEmpty() || cardNo=="" || cardNo==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN CARD NUMBER IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Id, Movement_Type__c, Remarks__c from Membership_Cycle__c where Member__r.Membership_Card_Number__c ='"+cardNo+"'";
            //String token
            String accessToken=null;
            try {
                accessToken = token.getSFaccessToken();
            } catch (Exception e) {
                System.out.println("Unable to generate access token. Exception - "+e.getMessage());
            }

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", accessToken)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();


            List<String> nodes = response.jsonPath().getList("records");


            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));


                try {
                    CycleId = response.jsonPath().getString("records.Id").replaceAll("\\p{P}", "").trim();
                } catch (Exception e) {
                    System.out.println("Unable to read Cycle Id from response. "+e.getMessage());
                }

                try {
                    MovementType = response.jsonPath().getString("records.Movement_Type__c").replaceAll("\\p{P}", "").trim();
                } catch (Exception e) {
                    System.out.println("Unable to read Movement Type from response. "+e.getMessage());
                }

                try {
                    Remarks = response.jsonPath().getString("records.Remarks__c").replaceAll("\\p{P}", "").trim();
                } catch (Exception e) {
                    System.out.println("Unable to read Remarks from response. "+e.getMessage());
                }



            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are NO Cycle ID found for Card Number "+ cardNo);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Cycle Id details failed");
            }

        }

    }

    public String getCardNoUsingFirstAndLastNameFromSF(String firstName, String lastName) {
        System.out.println("======================= INSIDE GET CARD NUMBER USING FIRST NAME AND LAST NAME FUNCTION  ===============================");

        String Name = firstName.trim()+" "+lastName.trim();
        // List<String> jobIdNumber = new ArrayList<>();

        if (Name.isEmpty() || Name=="" || Name==null) {
            System.out.println("EXITING THE FUNCTION BECAUSE GIVEN FIRST NAME AND LAST NAME IS EMPTY");
        } else {

            Response response = null;
            int statusCode = 0;

            // defining the rest of the URL with querry parameters

            String querryURL = "query/?q=SELECT Membership_Card_Number__c from Contact where Name Like '"+Name+"'";
            //String token
            String accessToken=null;
            try {
                accessToken = token.getSFaccessToken();
            } catch (Exception e) {
                System.out.println("Unable to generate access token. Exception - "+e.getMessage());
            }

            String URL = baseURL + querryURL;
            System.out.println(URL);
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", accessToken)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();


            List<String> nodes = response.jsonPath().getList("records");


            // Check if status code is 200 and transaction id count is not zero
            if (statusCode == HttpStatus.SC_OK && nodes.size()!=0) {

                //System.out.println(response.jsonPath().getString("records[0].Id").replaceAll("\\p{P}",""));


                try {
                    CardNo = response.jsonPath().getString("records.Membership_Card_Number__c").replaceAll("\\p{P}", "").trim();
                } catch (Exception e) {
                    System.out.println("Unable to read Card Number from response. "+e.getMessage());
                }



            } else if (statusCode == HttpStatus.SC_OK && nodes.size()==0){ //if status code is OK but no transaction numbers are found
                System.out.println("Exiting the function because there are card number records found for given First and Last names : "+ Name);
            }
            else if (statusCode != HttpStatus.SC_OK && nodes.size()!=0) { //if both status code is not OK and no records found
                System.out.println("Status Code : " + statusCode);
                System.out.println("Getting Card Number failed");
            }

        }
        return CardNo;
    }

    public String getCobrandIndicatorQuery(String memberId) {
        return "select GenericStrAry11 from MatrixTpReward.dbo.Member with(nolock) where ID in ('"+memberId+"')";
    }

    public String getMovementTypeandRemarkQuery(String memberId) {
        return "select TOP(1) MovementType,Remarks from DFSStaging.dbo.CustomerTier with(nolock) where  MemberID in ('"+memberId+"') order by DFSCycleAutoID desc";
    }

    public HashMap<String, String> readCobrandDataFromMatrix(String environment, String query) throws SQLException {


        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                        if(rsmd.getColumnName(i).equalsIgnoreCase("NativeSalutation")) {
                            nativeSalutationConvertedValue = dataConverter.convertNativeSalutation(environment, resultSet.getString(i).trim());
                            //updating the value in dataset
                            dataset.put(rsmd.getColumnName(i),nativeSalutationConvertedValue);
                        } else {
                            dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                        }

                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));


                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return dataset;


    }

    public void showErrorMessageWhenMultipleSearchResultsFound(Cobrand_pageObjects cobrand, testDataCleanup dataCleanup, TakeScreenshot screenshot){
        System.out.println("ERROR : The search must be having multiple search results for the member with First Name : "+cobrand.FirstName+" and Last Name : "+cobrand.LastName);
        System.out.println("FAILING THE TEST INTENTIONALLY !. There should be an unique Cobrand member created with the First Name : "+cobrand.FirstName+" and Last Name : "+cobrand.LastName);
        _scenario.write("ERROR : The search must be having multiple search results for the member with First Name : "+cobrand.FirstName+" and Last Name : "+cobrand.LastName);
        _scenario.write("FAILING THE TEST INTENTIONALLY !. There should be an unique Cobrand member created with the First Name : "+cobrand.FirstName+" and Last Name : "+cobrand.LastName);

        cobrand.cleanupDatOnAssertionFailure(cobrand,dataCleanup);

        screenshot.takeScreenshot();
        Assert.fail("FAILING THE TEST INTENTIONALLY !. There should be an unique Cobrand member created with the First Name : "+cobrand.FirstName+" and Last Name : "+cobrand.LastName);
    }

    public void cleanupDatOnAssertionFailure(Cobrand_pageObjects cobrand, testDataCleanup dataCleanup) {
        String CardNumber = cobrand.getCardNoUsingFirstAndLastNameFromSF(cobrand.FirstName,cobrand.LastName);
        System.out.println("==============STARTING DATA CLEAN UP AFTER THE TEST FAILURE ================");
        try {
            dataCleanup.cleanupCobrandMemberData(_scenario,CardNumber,cobrand.FileRef);
        } catch (Exception e) {
            System.out.println("Unable to clean up data after the assertion failure due to the exception - "+e.getMessage());
        }
        System.out.println("============== END OF DATA CLEANUP AFTER TEST FAILURE ===============");
    }

}
