package utilities;

import cucumber.api.Scenario;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import property.Property;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static step_definitions.API_Steps.jsonObject;

public class validateAPIreturnMessage {

    public String ReturnStatus="";
    public String ReturnMessage="";

    public void validateAPIReturnMessage(Scenario _scenario, Response response) throws ParseException, IOException {

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

        //Navigating through the json nodes
        JSONObject crmCRM = null;


        //Trying to read CRM-->crm:Customer block
        try {
            JSONObject crm = (JSONObject) jsonObject.get("CRM");
            crmCRM = (JSONObject) crm.get("crm:CRM");

        } catch (Exception e) {
            System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
            _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
            fail();
        }

        // Validating the Return message from Loyalty service block to see if response is returning success message

        //################# Start of LoyaltyServices block ###############
        JSONObject respInfo = null;
        try {
            JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
            respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
        } catch (Exception e) {
            System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
            _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
            fail();
        }

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
        //################ End of LoyaltyServices Block ###################

        //validate if Return message is SUCCESS
        try {
            assertTrue((ReturnMessage.equalsIgnoreCase(Property.API_EXPECTED_SUCCESS_MESSAGE) ),"The API response is : "+ ReturnMessage);
            System.out.println("The actual return message is : "+ReturnMessage);

        } catch (AssertionError e) {
            System.out.println("The test was failed since the API response did not return a success message. Actual message - "+ReturnMessage);
            _scenario.write("The test was failed since the API response did not return a success message. Actual message - "+ReturnMessage);
            fail();
        }
    }
public String getAPIReturnMessage(Scenario _scenario, Response response) throws IOException, ParseException {
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

    //Navigating through the json nodes
    JSONObject crmCRM = null;


    //Trying to read CRM-->crm:Customer block
    try {
        JSONObject crm = (JSONObject) jsonObject.get("CRM");
        crmCRM = (JSONObject) crm.get("crm:CRM");

    } catch (Exception e) {
        System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
        _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
        fail();
    }

    // Validating the Return message from Loyalty service block to see if response is returning success message

    //################# Start of LoyaltyServices block ###############
    JSONObject respInfo = null;
    try {
        JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
        respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
    } catch (Exception e) {
        System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
        _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
        fail();
    }

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
   return ReturnMessage;
}
}


