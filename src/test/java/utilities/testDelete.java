package utilities;

import cucumber.api.Scenario;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class testDelete {
    public String cardNumber ="520000561201";     // Original = 520000561215
    public String accessToken;
    public String memberID;
    public List<String> transactionID = new ArrayList<>();
    public List<String> transactionLineItems = new ArrayList<>();
    public List<String> transactionNumber = new ArrayList<>();
    public ResultSet resultSet;
    public Scenario _scenario;

   // public String accessToken="Bearer 00D5D00000013rE!ARUAQAa5bZSuQr01ZlzZgbPVokwRB8PdSWGA_3rmv7iJj5Vi5punmt4YWxdXTV3kaXQpUNWEAiWpmQvpO.zbNzaLjAJC3iW2";
    public DataCleanup dc = new DataCleanup(_scenario);

    public testDelete() throws Exception {
    }

    @Test
    public void testDeleteTransactionLineItem() throws Exception {
        transactionLineItems.add(0,"02i5D000000LUYfQAO");

        DataCleanup dc = new DataCleanup(_scenario);
        dc.deleteTransactionLineItem(transactionLineItems,accessToken );
    }

    @Test
    public void testDeleteTransaction () throws Exception {
        //getting the access token.
        accessToken = dc.getSFaccessToken().trim();

        //printing out the accessToken
        System.out.println(accessToken);

        //getting member ID by passing the card number and access token
        memberID = dc.getMemberID(cardNumber , accessToken);

        //get Transaction ID by passing the Member ID(contactId) to SF
        transactionID = dc.getTransactionID(memberID, accessToken);

       // dc.deleteTransactionItem(transactionID, accessToken);

        dc.deleteMember(memberID, accessToken);

        transactionNumber = dc.getTransactionNumber(memberID,accessToken);

        //reading data from DB
        createDbConnection dbConnection=new createDbConnection();
        String querry ="Select * from  [DFSStaging].dbo.SalesLine where TransactionNumber='4511111111144'";
        resultSet = dbConnection.queryDB("Preprod",querry);
        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for (int n = 1; n <= columnsNumber; n++) {
                //if (n > 1) {
                    System.out.println(", ");
                    String colValues = resultSet.getString(n);
                    System.out.println(rsmd.getColumnName(n) + " : " + colValues);
               // }
            }
        }
    }

}
