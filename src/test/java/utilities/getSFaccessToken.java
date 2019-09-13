package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class getSFaccessToken {
    public String accessToken;
    public String URL;
    public readTestData testData;
    public String client_id;
    public String client_secret;
    public String username;
    public String password;

    public String getSFaccessToken() throws Exception {

        testData = new readTestData();
        // getting the URL for access token generation
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")){
            URL=testData.readTestData("Preprod_URL1");
            client_id=testData.readTestData("Preprod_client_id");
            client_secret=testData.readTestData("Preprod_client_secret");
            username=testData.readTestData("Preprod");
            password=testData.readTestData("SFDCAdmin_Pwd");
        } else if (System.getProperty("Environment").equalsIgnoreCase("QACore2")) {
            URL = testData.readTestData("QACore2_URL1");
            client_id=testData.readTestData("QACore2_client_id");
            client_secret=testData.readTestData("QACore2_client_secret");
            username=testData.readTestData("QACore2");
            password=testData.readTestData("SFDCAdmin_Pwd");
        }

        String grant_type="password";



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

        return accessToken;
    }

}
