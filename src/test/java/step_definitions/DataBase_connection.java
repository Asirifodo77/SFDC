package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilities.createDbConnection;

import java.sql.*;
import java.util.HashMap;


public class DataBase_connection {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    //WebDriver wDriver = new FirefoxDriver();

    public DataBase_connection() {
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;
        System.out.println ("Transaction_Search constructor");
    }
    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    String tier = "";
    String AmtForUpgrade = "";
    String EntryDollar = "";
    String start = "";
    String end = "";
    String StatusDollar = "";
    String AmtForRenewal = "";
    String MovementType = "";
    String Remarks = "";
    String CarryForwardDollar = "";
    String point = "";
    String FirstName = "";
    String LastName = "";
    String Salutation = "";
    String NativeSalutationCode = "";
    String NativeSalutation = "";
    String Email = "";
    String FirstNameNative = "";
    String LastNameNative = "";
    String MobileAreaCode = "";
    String MobileNo = "";
    String SpokenLanguageCode = "";
    String Country = "";
    String AgeRange = "";
    String IssuedCardTier = "";
    String CardPickupMethod = "";
    String IsContactable = "";
    String RegistrationLocationID = "";
    String CustomerRegistrationDatetime = "";
    String CardStatus = "";
    String Gender = "";
    String IsInvalidEmail = "";
    String IsEmptyEmail = "";
    String RegistrationDivisionCode = "";
    String EnglishCountry = "";
    String ValidMobileNo1 = "";
    String SourceSystem = "";
    String IsEmailOptOut = "";
    String IsOptOutMobile1 = "";
    String IsOptOutMobile2 = "";
    String IsOptOutWorkPhone = "";
    String IsOptOutAddressEnglish = "";
    String IsOptOutAddressOthers = "";
    String IsProcessed = "";
    String ErrorMessage = "";
    String stgQueryResultStatus = "";
    String AutoID = "";



    public void DbConnection(DataTable table,String memberqueyselection, String Query, String cycleID) throws Throwable {

        System.out.println("Executing query");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String env = System.getProperty("Environment");
        System.out.println("------" + env + "------");
        if (env.equalsIgnoreCase("Preprod")){
            conn = DriverManager.getConnection("jdbc:sqlserver://" +myJsonObj.get("DB_DFSDM").getAsString()+"", myJsonObj.get("DB_DFSDM_Username").getAsString(), myJsonObj.get("DB_DFSDM_Pwd").getAsString());
            System.out.println("Execution Environment: Preprod");
        } else if (env.equalsIgnoreCase("QACore2")){
            conn = DriverManager.getConnection("jdbc:sqlserver://" +myJsonObj.get("DB_QACore2").getAsString()+"", myJsonObj.get("DB_QACore2_Username").getAsString(), myJsonObj.get("DB_QACore2_Pwd").getAsString());
            System.out.println("Execution Environment: QACore2");
        }

        try {
            // Execute a query
            stmt = conn.createStatement();
            //resultSet = stmt.executeQuery("select * from MatrixTpReward.dbo.card where CardNo = '520000561224' and  MembershipStatusCode = 'ACTIVE'");
            resultSet = stmt.executeQuery(Query);
            if (!resultSet.isBeforeFirst() ) {
                System.out.println("There is no record set in the Stagging Table.");
                resultSet=null;
            }
            switch(memberqueyselection) {
                case "points":
                    while (resultSet.next()) {
                        System.out.println("The Results are: \n" +
                                "Point : " + resultSet.getString(1) + "\n");
                        point = resultSet.getString(1);
                        break;
                    }
                    break;
                case "Membership":
                    while (resultSet.next()) {
                        System.out.println("The Results are: \n" +
                                "Tier : " + resultSet.getString(1) + "\n" +
                                "Status Dollar : " + resultSet.getString(2) + "\n" +
                                "Carry Forward Dollar : " + resultSet.getString(3) + "\n" +
                                "Entry Dollar : " + resultSet.getString(4) + "\n" +
                                "Cycle Start Date : " + resultSet.getString(5) + "\n" +
                                "Cycle End Date : " + resultSet.getString(6) + "\n" +
                                "Amt For Upgrade : " + resultSet.getString(7) + "\n" +
                                "Amt For Renewal : " + resultSet.getString(8) + "\n" +
                                "Movement Type : " + resultSet.getString(9) + "\n" +
                                "Remarks : " + resultSet.getString(10) + "\n");
                        tier = resultSet.getString(1);
                        StatusDollar = resultSet.getString(2);
                        CarryForwardDollar = resultSet.getString(3);
                        EntryDollar = resultSet.getString(4);
                        start = resultSet.getString(5);
                        end = resultSet.getString(6);
                        AmtForUpgrade = resultSet.getString(7);
                        AmtForRenewal = resultSet.getString(8);
                        MovementType = resultSet.getString(9);
                        Remarks = resultSet.getString(10);
                        break;
                    }
                    break;
                case "API":
                    while (resultSet.next()) {
                        System.out.println("The Results are: \n" +
                                "FirstName : " + resultSet.getString(1) + "\n" +
                                "LastName : " + resultSet.getString(2) + "\n" +
                                "Salutation : " + resultSet.getString(3) + "\n" +
                                "NativeSalutationCode : " + resultSet.getString(4) + "\n" +
                                "Email : " + resultSet.getString(5) + "\n" +
                                "FirstNameNative : " + resultSet.getString(6) + "\n" +
                                "LastNameNative : " + resultSet.getString(7) + "\n" +
                                "MobileAreaCode : " + resultSet.getString(8) + "\n" +
                                "MobileNo : " + resultSet.getString(9) + "\n" +
                                "SpokenLanguageCode : " + resultSet.getString(10) + "\n" +
                                "Country : " + resultSet.getString(11) + "\n" +
                                "AgeRange : " + resultSet.getString(12) + "\n" +
                                "IssuedCardTier : " + resultSet.getString(13) + "\n" +
                                "CardPickupMethod : " + resultSet.getString(14) + "\n" +
                                "IsContactable : " + resultSet.getString(15) + "\n" +
                                "RegistrationLocationID : " + resultSet.getString(16) + "\n" +
                                "CustomerRegistrationDatetime : " + resultSet.getString(17) + "\n" +
                                "CardStatus : " + resultSet.getString(18) + "\n" +
                                "Gender : " + resultSet.getString(19) + "\n" +
                                "IsInvalidEmail : " + resultSet.getString(20) + "\n" +
                                "IsEmptyEmail : " + resultSet.getString(21) + "\n" +
                                "RegistrationDivisionCode : " + resultSet.getString(22) + "\n" +
                                "EnglishCountry : " + resultSet.getString(23) + "\n" +
                                "ValidMobileNo1 : " + resultSet.getString(24) + "\n" +
                                "SourceSystem : " + resultSet.getString(25) + "\n" +
                                "IsEmailOptOut : " + resultSet.getString(26) + "\n" +
                                "IsOptOutMobile1 : " + resultSet.getString(27) + "\n" +
                                "IsOptOutMobile2 : " + resultSet.getString(28) + "\n" +
                                "IsOptOutWorkPhone : " + resultSet.getString(29) + "\n" +
                                "IsOptOutAddressEnglish : " + resultSet.getString(30) + "\n" +
                                "IsOptOutAddressOthers : " + resultSet.getString(31) + "\n" );
                        FirstName = resultSet.getString(1);
                        LastName = resultSet.getString(2);
                        Salutation = resultSet.getString(3);
                        NativeSalutationCode = resultSet.getString(4);
                        Email = resultSet.getString(5);
                        FirstNameNative = resultSet.getString(6);
                        LastNameNative = resultSet.getString(7);
                        MobileAreaCode = resultSet.getString(8);
                        MobileNo = resultSet.getString(9);
                        SpokenLanguageCode = resultSet.getString(10);
                        Country = resultSet.getString(11);
                        AgeRange = resultSet.getString(12);
                        IssuedCardTier = resultSet.getString(13);
                        CardPickupMethod = resultSet.getString(14);
                        IsContactable = resultSet.getString(15);
                        RegistrationLocationID = resultSet.getString(16);
                        CustomerRegistrationDatetime = resultSet.getString(17);
                        CardStatus = resultSet.getString(18);
                        Gender = resultSet.getString(19);
                        IsInvalidEmail = resultSet.getString(20);
                        IsEmptyEmail = resultSet.getString(21);
                        RegistrationDivisionCode = resultSet.getString(22);
                        EnglishCountry = resultSet.getString(23);
                        ValidMobileNo1 = resultSet.getString(24);
                        SourceSystem = resultSet.getString(25);
                        IsEmailOptOut = resultSet.getString(26);
                        IsOptOutMobile1 = resultSet.getString(27);
                        IsOptOutMobile2 = resultSet.getString(28);
                        IsOptOutWorkPhone = resultSet.getString(29);
                        IsOptOutAddressEnglish = resultSet.getString(30);
                        IsOptOutAddressOthers = resultSet.getString(31);
                        break;
                    }
                    break;
                case "nativeSalutation":
                    while (resultSet.next()) {
                        System.out.println("The Results are: \n" +
                                "Native Salutation : " + resultSet.getString(1) + "\n");
                        NativeSalutation = resultSet.getString(1);
                        break;
                    }
                    break;
                case "cycleID":
                     if(resultSet!=null){
                         while (resultSet.next()) {
                             stgQueryResultStatus = "Yes_Record";
                             System.out.println("The Results are: \n" +
                                     "IsProcessed : " + resultSet.getString(1) + "\n" +
                                     "ErrorMessage : " + resultSet.getString(2) + "\n");
                             IsProcessed = resultSet.getString(1);
                             ErrorMessage = resultSet.getString(2);
                             AutoID = resultSet.getString(3);
                             if(ErrorMessage == null){
                                 ErrorMessage = "";
                             }else{
                                 ErrorMessage ="error"; //There is an error
                                 _scenario.write("Flow was intentionally stopped because there was error in processing transaction in Matrix Stagging table.");
                                 _scenario.write("Error message :" +resultSet.getString(2));
                                 System.out.println("Flow was intentionally stopped because there was error in processing transaction in Matrix Stagging table.");
                                 System.out.println("Error message :" +resultSet.getString(2));
                                 Assert.fail();
                             }
                             if(IsProcessed ==  null){
                                 IsProcessed = ""; // still running
                             }
                             break;
                         }
                     }else{
                         stgQueryResultStatus = "NO_Record";
                         if (!AutoID.isEmpty()){
                             boolean dfsHistoryTableStatus = checkingHistoryStaggingTable(cycleID);
                             if (dfsHistoryTableStatus==true){
                                 System.out.println("DFSSTAGING.dbo.History_CustomerTier table IsProcessed status is 1");
                             }
                         }else{
                             System.out.println("AUTO ID is empty because there is no record in the Stagging table.");
                         }
                     }
                    break;
            }

        }catch (SQLException e){
            System.out.println("Exception in Database_Connection :- "+e);
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkingHistoryStaggingTable(String cycleID) throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("Getting DFSSTAGING.dbo.History_CustomerTier table status");
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        if (tier==null){
            tier = "Not_Added";
        }
        createDbConnection db = new createDbConnection();
        boolean queryComplete1 = false;
        while (!queryComplete1) {
            String isProcessedQuery = "select TOP(1) isProcessed, ErrorMessage, MemberID, processedDateTime, addedOn from DFSSTAGING.dbo.History_CustomerTier where DFSCycleAutoID='"+cycleID+"' and TableAutoID = '"+AutoID+"' order by addedOn desc;";
            System.out.println("Getting DFSSTAGING.dbo.History_CustomerTier status - Query : select TOP(1) isProcessed, ErrorMessage, MemberID, processedDateTime, addedOn from DFSSTAGING.dbo.History_CustomerTier where DFSCycleAutoID='"+cycleID+"' and TableAutoID ='"+AutoID+"' order by addedOn desc;");
            HashMap<String, String> isProcessedMAP = db.getDatabaseTableRecords(env, isProcessedQuery);
            String isProcessedVal = isProcessedMAP.get("isProcessed");
            String errorMessage = isProcessedMAP.get("ErrorMessage");
            System.out.println("Initial isProcessed value is :" + isProcessedVal);
            System.out.println("Query processed");
            System.out.println("++++++++++++++++++++++++++ "+ isProcessedVal);

            if ((isProcessedVal).equals("0") || errorMessage!=null) {
                System.out.println("Error");
                _scenario.write("Flow was intentionally stopped because there was error in processing transaction in Matrix DFSSTAGING.dbo.History_CustomerTier table.");
                _scenario.write("Error message :" +errorMessage);
                System.out.println("Flow was intentionally stopped because there was error in processing transaction in Matrix DFSSTAGING.dbo.History_CustomerTier table.");
                System.out.println("Error message :" +errorMessage);
                IsProcessed = isProcessedVal;
                ErrorMessage = errorMessage;
                Assert.fail();
                break;
            } else if (isProcessedVal.equals("1")){
                System.out.println("The Results in history table: \n" +
                        "IsProcessed : " + isProcessedVal + "\n" +
                        "ErrorMessage : " + errorMessage + "\n");
                IsProcessed = isProcessedVal;
                ErrorMessage = errorMessage;
                queryComplete1 = true;

            } else if ((isProcessedVal).equals("2")) {
                System.out.println("Status = 2");
                Thread.sleep(30000);
                System.out.println("30 seconds passed");
                System.out.println("The Results in history table \n" +
                        "IsProcessed : " + isProcessedVal + "\n" +
                        "ErrorMessage : " + errorMessage + "\n");
                IsProcessed = isProcessedVal;
                ErrorMessage = errorMessage;
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }

            }  else if (isProcessedVal.isEmpty()) {
                System.out.println("Status = empty");
                Thread.sleep(30000);
                System.out.println("30 seconds passed");
                System.out.println("The Results in history table \n" +
                        "IsProcessed : " + isProcessedVal + "\n" +
                        "ErrorMessage : " + errorMessage + "\n");
                IsProcessed = isProcessedVal;
                ErrorMessage = errorMessage;
                queryComplete1 = false;
                if (tier.equalsIgnoreCase("All")) {
                    driver.navigate().refresh();
                }
            }else if(isProcessedVal == null){
                System.out.println("The Results in history table \n" +
                        "IsProcessed : " + isProcessedVal + "\n" +
                        "ErrorMessage : " + errorMessage + "\n");
                queryComplete1 = true;
            }
        }

        return queryComplete1;
    }

}
