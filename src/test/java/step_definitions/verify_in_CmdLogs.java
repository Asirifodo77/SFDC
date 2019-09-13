package step_definitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import utilities.ReadJenkinsParameters;
import utilities.getSFaccessToken;
import utilities.readTestData;
import utilities.validateMemberIDinSFandMatrix;

import java.io.FileNotFoundException;
import java.util.Map;

public class verify_in_CmdLogs {
    public Scenario _scenario;
    public String environment;
    public utilities.validateMemberIDinSFandMatrix validateMemberIDinSFandMatrix;
    public getSFaccessToken accessToken;
    public String token;
    public readTestData testData;

    public verify_in_CmdLogs() throws FileNotFoundException {
        _scenario = Hooks._scenario;
        environment = System.getProperty("Environment");
        accessToken = new getSFaccessToken();
        validateMemberIDinSFandMatrix = new validateMemberIDinSFandMatrix(_scenario);
        testData = new readTestData();
    }

    @Then("^I validate memberID in Salesforce and Matrix$")
    public void iValidateMemberIDInSalesforceAndMatrix(DataTable table) throws Throwable {
        //getting the access token
        token = accessToken.getSFaccessToken();

        for (Map<String,String> data : table.asMaps(String.class, String.class)) {
           String cardNumber = testData.readTestData(ReadJenkinsParameters.getJenkinsParameter(data.get("<Card_Number>")));
           validateMemberIDinSFandMatrix.validateMemberID(token, cardNumber,environment);
        }

    }
}
