package utilities;

import cucumber.api.Scenario;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import property.Property;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class getDataFromSF {
    public String URL;
    public String accessToken;
    public String baseURL;
    public Scenario _scenario;
    public String client_id;
    public String client_secret;
    public String username;
    public String password;
    public readTestData testData;
    public String Update_Date_c;
    public String Creation_Date__c;
    public String Card_Pickup_Method__c;
    public String Division_code__c;
    public String Staff_Number__c;
    public String Update_User_ID__c;
    public String Data_Source_Enrollment__c;
    public String Sign_Up_Tier__c;
    public String Start_Date__c;
    public String isDummy__c;
    public String Store_Name__c;
    public String Member_Id_Matrix__c;

    public getDataFromSF() throws FileNotFoundException {
        testData = new readTestData();
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")){
            baseURL = testData.readTestData("Preprod_SF_BaseURL");
            client_id=testData.readTestData("Preprod_client_id");
            client_secret=testData.readTestData("Preprod_client_secret");
            username=testData.readTestData("Preprod");
            password=testData.readTestData("SFDCAdmin_Pwd");

        } else if (System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = testData.readTestData("QACore2_SF_BaseURL");
            client_id=testData.readTestData("QACore2_client_id");
            client_secret=testData.readTestData("QACore2_client_secret");
            username=testData.readTestData("QACore2");
            password=testData.readTestData("SFDCAdmin_Pwd");
        }

    }

    public String getQueryForDSAMember(String cardNumber){
        String query = "query/?q=SELECT Membership_Card_Number__c, " +
                "Sign_Up_Tier__c, " +
                "Name, " +
                "Enrollment_Location__c, " +
                "Registration_Location__c, " +
                "Division_code__c, " +
                "Card_Pickup_Method__c, " +
                "Staff_Number__c," +
                "Creation_Date__c," +
                "Update_Date__c," +
                "Update_User_ID__c," +
                "CreatedById," +
                "Data_Source_Enrollment__c " +
                "from Contact where Membership_Card_Number__c = '"+cardNumber+"'";
        return query;
    }

    public String getQueryForETRMember(String cardNumber) {
        String query = "query/?q=SELECT Membership_Card_Number__c, " +
                "Sign_Up_Tier__c, " +
                "Name, " +
                "Enrollment_Location__c, " +
                "Registration_Location__c, " +
                "Division_code__c, " +
                "Card_Pickup_Method__c, " +
                "Staff_Number__c," +
                "Creation_Date__c," +
                "Update_Date__c," +
                "Update_User_ID__c," +
                "CreatedById," +
                "Data_Source_Enrollment__c " +
                "from Contact where Membership_Card_Number__c = '"+cardNumber+"'";
        return query;
    }

    public String getQueryForPOSMember(String cardNumber){
        String query = "query/?q=SELECT Membership_Card_Number__c, " +
                "Sign_Up_Tier__c, " +
                "Name, " +
                "Enrollment_Location__c, " +
                "Division_code__c, " +
                "Card_Pickup_Method__c, " +
                "Staff_Number__c," +
                "Creation_Date__c," +
                "Update_Date__c," +
                "Update_User_ID__c," +
                "CreatedById," +
                "Data_Source_Enrollment__c " +
                "from Contact where Membership_Card_Number__c  = '"+cardNumber+"'";
        return query;
    }

    public String getQueryForPOSsearchMember(String cardNumber) {

        String query = "query/?q=Select " +
                "enrollment_date_ui__c " +
                "From Contact where Membership_Card_Number__c='"+cardNumber+"'";
        return query;
    }

    public String getQueryForDummyMemberFlag(String cardNumber){
        String query = "query/?q=SELECT isDummy__c from Contact where Membership_Card_Number__c = '"+cardNumber+"'";
        return query;
    }

    public String getQueryForStoreLocationName(String divisionId, String sellingLocationId) {
        return "query/?q=Select Store_Name__c From Selling_Location__c Where Division_ID__c = '"+divisionId+"' and Selling_Location_ID__c = '"+sellingLocationId+"'";
    }

    public void querySFDataToGetStoreLocationName(String accessToken, String divisionId, String sellingLocationId){
        System.out.println("=================== INSIDE GET STORE LOCATION NAME FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForStoreLocationName(divisionId,sellingLocationId);
        String token = accessToken.trim();


        Response response=
                null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/json" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
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

        if (response.getStatusCode()==HttpStatus.SC_OK) {
            //Getting Store_Name__c
            try {
                Store_Name__c = response.jsonPath().getString("records.Store_Name__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Store_Name__c="";
            }
            if (Store_Name__c.isEmpty() || Store_Name__c==null || Store_Name__c=="") {
                System.out.println("Store Location Name is NULL OR EMPTY. Actual value - "+Store_Name__c);
                Store_Name__c="";
            }else {
                System.out.println(" Store Location Name is - "+Store_Name__c);
            }

        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }
    }

    public void querySFDataforDSA(String accessToken, String cardNumber){


        System.out.println("=================== INSIDE GET SF DATA FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForDSAMember(cardNumber);
        String token = accessToken.trim();


        Response response=
                null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
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

        if (response.getStatusCode()==HttpStatus.SC_OK) {
            //Getting RegistrationDivisionCode
            try {
                Division_code__c = response.jsonPath().getString("records.Division_code__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Division_code__c="";
            }
            if (Division_code__c.isEmpty() || Division_code__c==null || Division_code__c=="") {
                System.out.println("RegistrationDivisionCode is NULL OR EMPTY. Actual value - "+Division_code__c);
                Division_code__c="";
            }else {
                System.out.println(" RegistrationDivisionCode is - "+Division_code__c);
            }


            //Getting CardTire
            try {
                Sign_Up_Tier__c= response.jsonPath().getString("records.Sign_Up_Tier__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Sign_Up_Tier__c="";
            }
            if (Sign_Up_Tier__c.isEmpty() || Sign_Up_Tier__c==null || Sign_Up_Tier__c=="") {
                System.out.println("CardTire is NULL OR EMPTY. Actual value - "+Sign_Up_Tier__c);
                Sign_Up_Tier__c="";
            }else {
                System.out.println(" CardTire is - "+Sign_Up_Tier__c);
            }



            // IGNORING THIS FIELD FOR NOW AS IT RETURNS NULL. SHOULD BE INPLEMENTED LATER
            // String Registration_Location__c  = response.jsonPath().getString("records.Registration_Location__c").trim();


            try {
                Card_Pickup_Method__c  = response.jsonPath().getString("records.Card_Pickup_Method__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Card_Pickup_Method__c="";
            }
            if (Card_Pickup_Method__c.isEmpty() || Card_Pickup_Method__c==null || Card_Pickup_Method__c=="") {
                System.out.println("Card_Pickup_Method__c is NULL OR EMPTY. Actual value - "+Card_Pickup_Method__c);
                Card_Pickup_Method__c="";
            } else {
                System.out.println("Card_Pickup_Method__c is - "+Card_Pickup_Method__c);
            }

            //getting Staff_Number__c
            try {
                Staff_Number__c = response.jsonPath().getString("records.Staff_Number__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Staff_Number__c="";
            }
            if (Staff_Number__c.isEmpty() || Staff_Number__c==null || Staff_Number__c=="") {
                System.out.println("Staff_Number__c is NULL OR EMPTY. Actual value - "+Staff_Number__c);
                Staff_Number__c="";
            } else {
                System.out.println("Staff_Number__c is - "+Staff_Number__c);
            }

            //getting Creation_Date__c
            String Creation_Date = null;
            try {
                Creation_Date = response.jsonPath().getString("records.Creation_Date__c").replaceAll("\\[", "").replaceAll("\\]","").trim();
            } catch (Exception e) {
                Creation_Date__c="";
            }
            if (Creation_Date.isEmpty() || Creation_Date==null || Creation_Date=="") {
                System.out.println("Creation_Date__c is NULL OR EMPTY. Actual value - "+Creation_Date);
                Creation_Date__c="";
            } else {
                Creation_Date__c = Creation_Date.substring(0,10);
                System.out.println("Creation_Date__c after trimming - "+Creation_Date__c);

            }

            // getting Update_Date__c
            String Update_Date = null;
            try {
                Update_Date = response.jsonPath().getString("records.Update_Date__c").replaceAll("\\[", "").replaceAll("\\]","").trim();
            } catch (Exception e) {
                Update_Date_c ="";
            }
            if (Update_Date.isEmpty() || Update_Date==null || Update_Date=="") {
                System.out.println("Update_Date_c is NULL OR EMPTY. Actual value - "+Update_Date);
                Update_Date_c ="";
            } else {
                Update_Date_c = Update_Date.substring(0,10);
                System.out.println("Update_Date_c after trimming - "+Update_Date_c);
            }


            //Getting Update_User_ID__c
            try {
                Update_User_ID__c = response.jsonPath().getString("records.Update_User_ID__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Update_User_ID__c="";
            }
            if (Update_User_ID__c.isEmpty() || Update_User_ID__c==null || Update_User_ID__c=="") {
                System.out.println("Update_User_ID__c is NULL OR EMPTY. Actual value - "+Update_User_ID__c);
                Update_User_ID__c="";
            } else {
                System.out.println("Update_User_ID__c is - "+Update_User_ID__c);
            }

            //Getting Data_Source_Enrollment__c
            try {
                Data_Source_Enrollment__c = response.jsonPath().getString("records.Data_Source_Enrollment__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Data_Source_Enrollment__c="";
            }
            if (Data_Source_Enrollment__c.isEmpty() || Data_Source_Enrollment__c==null || Data_Source_Enrollment__c=="") {
                System.out.println("Data_Source_Enrollment__c is NULL OR EMPTY. Actual value - "+Data_Source_Enrollment__c);
                Data_Source_Enrollment__c="";
            } else {
                System.out.println("Data_Source_Enrollment__c is - "+Data_Source_Enrollment__c);
            }
        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }


    }

    public void querySFdataforETR(String accessToken, String cardNumber) {
        System.out.println("=================== INSIDE GET SF DATA FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForDSAMember(cardNumber);
        String token = accessToken.trim();


        Response response= null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
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

        if (response.getStatusCode()==HttpStatus.SC_OK) {
            //Getting RegistrationDivisionCode
            try {
                Division_code__c = response.jsonPath().getString("records.Division_code__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Division_code__c="";
            }
            if (Division_code__c.isEmpty() || Division_code__c==null || Division_code__c=="") {
                System.out.println("RegistrationDivisionCode is NULL OR EMPTY. Actual value - "+Division_code__c);
                Division_code__c="";
            }else {
                System.out.println(" RegistrationDivisionCode is - "+Division_code__c);
            }


            //Getting CardTire
            try {
                Sign_Up_Tier__c= response.jsonPath().getString("records.Sign_Up_Tier__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Sign_Up_Tier__c="";
            }
            if (Sign_Up_Tier__c.isEmpty() || Sign_Up_Tier__c==null || Sign_Up_Tier__c=="") {
                System.out.println("CardTire is NULL OR EMPTY. Actual value - "+Sign_Up_Tier__c);
                Sign_Up_Tier__c="";
            }else {
                System.out.println(" CardTire is - "+Sign_Up_Tier__c);
            }



            // IGNORING THIS FIELD FOR NOW AS IT RETURNS NULL. SHOULD BE INPLEMENTED LATER
            // String Registration_Location__c  = response.jsonPath().getString("records.Registration_Location__c").trim();


            try {
                Card_Pickup_Method__c  = response.jsonPath().getString("records.Card_Pickup_Method__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Card_Pickup_Method__c="";
            }
            if (Card_Pickup_Method__c.isEmpty() || Card_Pickup_Method__c==null || Card_Pickup_Method__c=="") {
                System.out.println("Card_Pickup_Method__c is NULL OR EMPTY. Actual value - "+Card_Pickup_Method__c);
                Card_Pickup_Method__c="";
            } else {
                System.out.println("Card_Pickup_Method__c is - "+Card_Pickup_Method__c);
            }

            //getting Staff_Number__c
            try {
                Staff_Number__c = response.jsonPath().getString("records.Staff_Number__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Staff_Number__c="";
            }
            if (Staff_Number__c.isEmpty() || Staff_Number__c==null || Staff_Number__c=="") {
                System.out.println("Staff_Number__c is NULL OR EMPTY. Actual value - "+Staff_Number__c);
                Staff_Number__c="";
            } else {
                System.out.println("Staff_Number__c is - "+Staff_Number__c);
            }

            //getting Creation_Date__c
            String Creation_Date = null;
            try {
                Creation_Date = response.jsonPath().getString("records.Creation_Date__c").replaceAll("\\[", "").replaceAll("\\]","").trim();
            } catch (Exception e) {
                Creation_Date__c="";
            }
            if (Creation_Date.isEmpty() || Creation_Date==null || Creation_Date=="") {
                System.out.println("Creation_Date__c is NULL OR EMPTY. Actual value - "+Creation_Date);
                Creation_Date__c="";
            } else {
                Creation_Date__c = Creation_Date.substring(0,10);
                System.out.println("Creation_Date__c after trimming - "+Creation_Date__c);

            }

            // getting Update_Date__c
            String Update_Date = null;
            try {
                Update_Date = response.jsonPath().getString("records.Update_Date__c").replaceAll("\\[", "").replaceAll("\\]","").trim();
            } catch (Exception e) {
                Update_Date ="";
            }
            if (Update_Date.isEmpty() || Update_Date==null || Update_Date=="") {
                System.out.println("Update_Date_c is NULL OR EMPTY. Actual value - "+Update_Date);
                Update_Date_c ="";
            } else {
                Update_Date_c = Update_Date.substring(0,10);
                System.out.println("Update_Date_c after trimming - "+Update_Date_c);
            }


            //Getting Update_User_ID__c
            try {
                Update_User_ID__c = response.jsonPath().getString("records.Update_User_ID__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Update_User_ID__c="";
            }
            if (Update_User_ID__c.isEmpty() || Update_User_ID__c==null || Update_User_ID__c=="") {
                System.out.println("Update_User_ID__c is NULL OR EMPTY. Actual value - "+Update_User_ID__c);
                Update_User_ID__c="";
            } else {
                System.out.println("Update_User_ID__c is - "+Update_User_ID__c);
            }

            //Getting Data_Source_Enrollment__c
            try {
                Data_Source_Enrollment__c = response.jsonPath().getString("records.Data_Source_Enrollment__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Data_Source_Enrollment__c="";
            }
            if (Data_Source_Enrollment__c.isEmpty() || Data_Source_Enrollment__c==null || Data_Source_Enrollment__c=="") {
                System.out.println("Data_Source_Enrollment__c is NULL OR EMPTY. Actual value - "+Data_Source_Enrollment__c);
                Data_Source_Enrollment__c="";
            } else {
                System.out.println("Data_Source_Enrollment__c is - "+Data_Source_Enrollment__c);
            }
        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }


    }

    public void querySFdataforPOS(String accessToken, String cardNumber){
        System.out.println("=================== INSIDE GET SF DATA FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForPOSMember(cardNumber);
        String token = accessToken.trim();


        Response response= null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() );
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode());
            Assert.fail();
        }

        if (response.getStatusCode()==HttpStatus.SC_OK) {


            //GETTING Card_Pickup_Method__c
            try {
                Card_Pickup_Method__c  = response.jsonPath().getString("records.Card_Pickup_Method__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Card_Pickup_Method__c="";
            }
            if (Card_Pickup_Method__c.isEmpty() || Card_Pickup_Method__c==null || Card_Pickup_Method__c=="") {
                System.out.println("Card_Pickup_Method__c is NULL OR EMPTY. Actual value - "+Card_Pickup_Method__c);
                Card_Pickup_Method__c="";
            } else {
                System.out.println("Card_Pickup_Method__c is - "+Card_Pickup_Method__c);
            }

            //Getting CardTire
            try {
                Sign_Up_Tier__c= response.jsonPath().getString("records.Sign_Up_Tier__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Sign_Up_Tier__c="";
            }
            if (Sign_Up_Tier__c.isEmpty() || Sign_Up_Tier__c==null || Sign_Up_Tier__c=="") {
                System.out.println("CardTire is NULL OR EMPTY. Actual value - "+Sign_Up_Tier__c);
                Sign_Up_Tier__c="";
            }else {
                System.out.println(" CardTire is - "+Sign_Up_Tier__c);
            }

            //Getting RegistrationDivisionCode
            try {
                Division_code__c = response.jsonPath().getString("records.Division_code__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Division_code__c="";
            }
            if (Division_code__c.isEmpty() || Division_code__c==null || Division_code__c=="") {
                System.out.println("RegistrationDivisionCode is NULL OR EMPTY. Actual value - "+Division_code__c);
                Division_code__c="";
            }else {
                System.out.println(" RegistrationDivisionCode is - "+Division_code__c);
            }

            //getting Staff_Number__c
            try {
                Staff_Number__c = response.jsonPath().getString("records.Staff_Number__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Staff_Number__c="";
            }
            if (Staff_Number__c.isEmpty() || Staff_Number__c==null || Staff_Number__c=="") {
                System.out.println("Staff_Number__c is NULL OR EMPTY. Actual value - "+Staff_Number__c);
                Staff_Number__c="";
            } else {
                System.out.println("Staff_Number__c is - "+Staff_Number__c);
            }

        //Getting Data_Source_Enrollment__c
            try {
                Data_Source_Enrollment__c = response.jsonPath().getString("records.Data_Source_Enrollment__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Data_Source_Enrollment__c="";
            }
            if (Data_Source_Enrollment__c.isEmpty() || Data_Source_Enrollment__c==null || Data_Source_Enrollment__c=="") {
                System.out.println("Data_Source_Enrollment__c is NULL OR EMPTY. Actual value - "+Data_Source_Enrollment__c);
                Data_Source_Enrollment__c="";
            } else {
                System.out.println("Data_Source_Enrollment__c is - "+Data_Source_Enrollment__c);
            }
        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }


    }

    public void querySFdataforPOSsearch(String accessToken, String cardNumber) {
        System.out.println("=================== INSIDE GET SF DATA FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForPOSsearchMember(cardNumber);
        String token = accessToken.trim();


        Response response= null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() );
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode());
            Assert.fail();
        }

        if (response.getStatusCode()==HttpStatus.SC_OK) {


            //GETTING Start_Date__c
            try {
                Start_Date__c  = response.jsonPath().getString("records.Enrollment_Date_Ui__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Start_Date__c="";
            }
            if (Start_Date__c.isEmpty() || Start_Date__c==null || Start_Date__c=="") {
                System.out.println("Enrollment_Date_Ui__c is NULL OR EMPTY. Actual value - "+Start_Date__c);
                Start_Date__c="";
            } else {
                System.out.println("Enrollment_Date_Ui__c is - "+Start_Date__c);
            }
        }
    }

    public void querySFdataForDummyMmeber(String accessToken,String cardNumber) {
        System.out.println("=================== INSIDE GET SF DATA FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForDummyMemberFlag(cardNumber);
        String token = accessToken.trim();


        Response response= null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() );
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode());
            Assert.fail();
        }

        if (response.getStatusCode()==HttpStatus.SC_OK) {


            //GETTING Start_Date__c
            try {
                isDummy__c  = response.jsonPath().getString("records.isDummy__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                isDummy__c="";
            }
            if (isDummy__c.isEmpty() || isDummy__c==null || isDummy__c=="") {
                System.out.println("Start_Date__c is NULL OR EMPTY. Actual value - "+isDummy__c);
                isDummy__c="";
            } else {
                System.out.println("Start_Date__c is - "+isDummy__c);
            }
        }
    }

    public String getQueryForGetMemberIDFromSF(String cardNumber) {
        return "query/?q=SELECT Member_Id_Matrix__c from Contact where Membership_Card_Number__c IN ('"+cardNumber+"')";
    }

    public String getMemberIDFromSF(String accessToken, String cardNumber) {

        System.out.println("=================== INSIDE GET MEMBER ID FROM SF FUNCTION =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForGetMemberIDFromSF(cardNumber);
        String token = accessToken.trim();


        Response response=
                null;
        try {
            String URL = baseURL+querryURL;
            System.out.println("url is: "+URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded" , "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail("TEST FAILED INTENTIONALLY !. POST call to Salesforce was not successful");
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful with status code "+ response.getStatusCode() );
        }

        if (response.getStatusCode()==HttpStatus.SC_OK) {
            //Getting Member_Id_Matrix__c
            try {
                Member_Id_Matrix__c = response.jsonPath().getString("records.Member_Id_Matrix__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                Member_Id_Matrix__c="";
            }
            if (Member_Id_Matrix__c.isEmpty() || Member_Id_Matrix__c==null || Member_Id_Matrix__c=="") {
                System.out.println("Member Id (Matrix) is NULL OR EMPTY. Actual value - "+Member_Id_Matrix__c);
                Member_Id_Matrix__c=null;
            }else {
                System.out.println(" Member Id (Matrix) is - "+Member_Id_Matrix__c);
            }


        } else {
            System.out.println("Request was Unsuccessful with Status code - "+response.getStatusCode());
            System.out.println("Response - "+response.asString());
        }

        return Member_Id_Matrix__c;
    }

    public String getQueryForMessageInCustomLogsSF(String cardNumber, String errorClass) {
        String query ="query/?q=SELECT Message__c " +
                "from Apex_Custom_Log__c WHERE " +
                "Card_Number__c='"+cardNumber+"' " +
                "AND Store_Name__c='OKINAWA AIRPORT' " +
                "AND Class__c='"+errorClass+"' order by CreatedDate desc LIMIT 1";

        return query;
    }

    public String getQueryForMessageInCustomLogsSFforATPfailure(String cardNumber, String errorClass) {
        String query ="query/?q=SELECT Message__c " +
                "from Apex_Custom_Log__c WHERE " +
                "Card_Number__c='"+cardNumber+"' " +
                "AND Store_Name__c='OKINAWA AIRPORT' " +
                "AND Operation__c='Transaction Rejection' " +
                "AND Class__c='"+errorClass+"' order by CreatedDate desc LIMIT 1";

        return query;
    }

    public String getMessageFromCustomLogsInSFForATP(String accessToken, String cardNumber, String errorClass) {
        System.out.println("=================== GETTING RECORD FROM CUSTOM LOGS IN SALESFORCE FOR ATP =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForMessageInCustomLogsSFforATPfailure(cardNumber, errorClass);
        String token = accessToken.trim();


        Response response =
                null;
        try {
            String URL = baseURL + querryURL;
            System.out.println("url is: " + URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail("TEST FAILED INTENTIONALLY !. POST call to Salesforce was not successful");
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful with status code " + response.getStatusCode());
        }

        String messageInCustomLogs = null;
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            //Getting Member_Id_Matrix__c
            try {
                messageInCustomLogs = response.jsonPath().getString("records.Message__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                System.out.println("Unable to fetch the Message from custom logs due to an exception - " + e.getMessage());
                throw e;
            }
            if (messageInCustomLogs.isEmpty() || messageInCustomLogs.equalsIgnoreCase("null")) {
                System.out.println("message In Custom Logs is NULL OR EMPTY. Actual value - " + messageInCustomLogs);
                messageInCustomLogs = "";
            } else {
                System.out.println(" message In Custom Logs (Salesforce) is - " + messageInCustomLogs);
            }


        } else {
            System.out.println("Request was Unsuccessful with Status code - " + response.getStatusCode());
            System.out.println("Response - " + response.asString());
        }

        return messageInCustomLogs;
    }

    public String getMessageFromCustomLogsInSF(String accessToken, String cardNumber, String errorClass) throws Exception {

        System.out.println("=================== GETTING RECORD FROM CUSTOM LOGS IN SALESFORCE =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForMessageInCustomLogsSF(cardNumber, errorClass);
        String token = accessToken.trim();


        Response response =
                null;
        try {
            String URL = baseURL + querryURL;
            System.out.println("url is: " + URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail("TEST FAILED INTENTIONALLY !. POST call to Salesforce was not successful");
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful with status code " + response.getStatusCode());
        }

        String messageInCustomLogs = null;
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            //Getting Member_Id_Matrix__c
            try {
                messageInCustomLogs = response.jsonPath().getString("records.Message__c").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                System.out.println("Unable to fetch the Message from custom logs due to an exception - " + e.getMessage());
                throw e;
            }
            if (messageInCustomLogs.isEmpty() || messageInCustomLogs.equalsIgnoreCase("null")) {
                System.out.println("message In Custom Logs is NULL OR EMPTY. Actual value - " + messageInCustomLogs);
                messageInCustomLogs = "";
            } else {
                System.out.println(" message In Custom Logs (Salesforce) is - " + messageInCustomLogs);
            }


        } else {
            System.out.println("Request was Unsuccessful with Status code - " + response.getStatusCode());
            System.out.println("Response - " + response.asString());
        }

        return messageInCustomLogs;

    }

    public boolean isCardRangeExist(String accessToken, String maximumVal, String minimumVal, String storeLocation) {
        System.out.println("=================== CHECKING IF CARD RANGE EXISTS IN SALESFORCE =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForIsCardRangeExist(maximumVal,minimumVal,storeLocation);
        String token = accessToken.trim();


        Response response =
                null;
        try {
            String URL = baseURL + querryURL;
            System.out.println("url is: " + URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail("TEST FAILED INTENTIONALLY !. POST call to Salesforce was not successful");
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful with status code " + response.getStatusCode());
        }

        String cardRangeId = null;
        boolean cardRangeExist = false;

        if (response.getStatusCode() == HttpStatus.SC_OK) {
            //Getting Member_Id_Matrix__c
            try {
                cardRangeId = response.jsonPath().getString("records").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                System.out.println("Unable to fetch records from Card_range object in SF due to an exception - " + e.getMessage());
                throw e;
            }
            if (cardRangeId.isEmpty() || cardRangeId.equalsIgnoreCase("null")) {
                //System.out.println("Id In Card_range is NULL OR EMPTY. The card range "+minimumVal+" - "+maximumVal+" Does not exist in Salesforce as expected!");
                cardRangeExist=false;
            } else {
                //System.out.println(" Id In Card_range object in Salesforce is - " + cardRangeId);
                cardRangeExist=true;
            }


        } else {
            System.out.println("Request was Unsuccessful with Status code - " + response.getStatusCode());
            System.out.println("Response - " + response.asString());
        }

        return cardRangeExist;
    }

    public String getQueryForIsCardRangeExist(String maximumValue, String minimumValue, String storeLocation) {
        String query ="query/?q=SELECT Id From Card_Range__c WHERE Maximum_Value__c ='"+maximumValue+"' AND Minimal_Value__c ='"+minimumValue+"' AND Store_Location__c='"+storeLocation+"'";
        return query;
    }

    public boolean isCardExist(String accessToken, String cardNumber) {
        System.out.println("=================== CHECKING IF CARD NUMBER EXISTS IN SALESFORCE =========================================");
        // defining the rest of the URL with querry parameters
        String querryURL = getQueryForIsCardExist(cardNumber);
        String token = accessToken.trim();


        Response response =
                null;
        try {
            String URL = baseURL + querryURL;
            System.out.println("url is: " + URL);
            response = given()
                    .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", token)
                    .get(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            System.out.println("Response code is : " + response.getStatusCode());
            System.out.println(response.asString());
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail("TEST FAILED INTENTIONALLY !. POST call to Salesforce was not successful");
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code" + response.getStatusCode() + " The actual status is - " + e);
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful with status code " + response.getStatusCode());
        }

        String cardDetails = null;
        boolean cardExist = false;

        if (response.getStatusCode() == HttpStatus.SC_OK) {
            //Getting Member_Id_Matrix__c
            try {
                cardDetails = response.jsonPath().getString("records").replaceAll("\\p{P}", "").trim();
            } catch (Exception e) {
                System.out.println("Unable to fetch records from Card object in SF due to an exception - " + e.getMessage());
                throw e;
            }
            if (cardDetails.isEmpty() || cardDetails.equalsIgnoreCase("null")) {
                //System.out.println("Details for The card - "+cardNumber+" Does not exist in Salesforce as expected!");
                cardExist=false;
            } else {
               //System.out.println("Card Exist in SF !.  Details for The card in Salesforce is - " + cardDetails);
                cardExist=true;
            }


        } else {
            System.out.println("Request was Unsuccessful with Status code - " + response.getStatusCode());
            System.out.println("Response - " + response.asString());
        }

        return cardExist;
    }

    public String getQueryForIsCardExist(String cardNumber) {
        String query="query/?q=SELECT Membership_Card_Number__c, Status__c from Membership_Card__c where Membership_Card_Number__c = '"+cardNumber+"'";
        return query;
    }

}
