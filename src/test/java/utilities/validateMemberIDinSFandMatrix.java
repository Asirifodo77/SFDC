package utilities;

import cucumber.api.Scenario;
import org.testng.Assert;
import page_objects.DSAcreateMember_pageObjects;
import property.Property;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class validateMemberIDinSFandMatrix {
    public Scenario _scenario;
    public getDataFromSF sfData;
    public DSAcreateMember_pageObjects matrix;

    public validateMemberIDinSFandMatrix(Scenario _scenario) throws FileNotFoundException {
        this._scenario=_scenario;
        sfData = new getDataFromSF();
        matrix = new DSAcreateMember_pageObjects(_scenario);
    }

    public void validateMemberID(String token, String cardNumber, String environment) throws InterruptedException {

        //Validate if there is no error in CmdLogs in Matrix
        String ReturnMessageFromCmdLogs=null;


            try {
                System.out.println(" ~~~~~ Waiting for 3 minutes before querying CMD logs table ~~~~~~");
                Thread.sleep(180000);
                System.out.println(" ~~~~~ Continuing to query CMD logs table after 3 minutes wait ~~~~~~");

                ReturnMessageFromCmdLogs = matrix.getReturnMessageFromCmdLogsInMatrix(environment,cardNumber);

                int retryCount =0;
                while (ReturnMessageFromCmdLogs.isEmpty() && retryCount<10){
                    System.out.println("CMD log message is empty. Retrying in 30 seconds");
                    Thread.sleep(30000);
                    ReturnMessageFromCmdLogs = matrix.getReturnMessageFromCmdLogsInMatrix(environment,cardNumber);
                    retryCount++;
                }
            } catch (SQLException e) {
                System.out.println("Unable to get data from Matrix Custom Log. "+e.getMessage());
                _scenario.write("Unable to get data from Matrix Custom Log. "+e.getMessage());
                Assert.fail("Unable to get data from Matrix Custom Log. "+e.getMessage());
            } catch (NullPointerException ne) {
                System.out.println("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
                _scenario.write("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
                Assert.fail("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
            }



        try {
            Assert.assertEquals(ReturnMessageFromCmdLogs.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+" .The record is successfully processed in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
            _scenario.write("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
            Assert.fail("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
        }



        //get member ID from SF //get member ID from Matrix //validate both IDs
        Thread.sleep(10000);
        String memberIDsf = null;
        String memberIDmatrix = null;

        //reading the member id from SF
        try {
            memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
        } catch (Exception e) {
            System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
        }


        //reading member id from Matrix

        try {
            memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
        } catch (SQLException e) {
            System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
        }

        int retryCount = 0;
        while ((memberIDsf==null || memberIDmatrix==null) && retryCount < Property.RETRY_COUNT_FOR_GETTING_MEMBERID_FROM_SF_AND_MATRIX) {

            if(memberIDsf==null && memberIDmatrix==null) {  //when both are null
                System.out.println("Member ID in SF and Matrix are Null. Retrying again in 10 seconds");
                Thread.sleep(10000);
                // READING BOTH ENDS AFTER 10 SECONDS //
                //reading the member id from SF
                try {
                    memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
                } catch (Exception e) {
                    System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                }

                //reading member id from Matrix

                try {
                    memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
                } catch (SQLException e) {
                    System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                }
            } else { //when at least one of the values are null

                System.out.println("=============================");
                System.out.println("MemberID (Salesforce) : "+memberIDsf+" MemberID (Matrix) : "+memberIDmatrix);
                System.out.println("=============================");

                if(memberIDsf==null){ //if only SF side is null
                    //reading the member id from SF
                    try {
                        System.out.println("Member ID from Salesforce is Null. Retrying again in 10 seconds");
                        Thread.sleep(10000);
                        memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
                    } catch (Exception e) {
                        System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    }
                }

                if(memberIDmatrix==null){  //if only Matrix side is null
                    //reading member id from Matrix
                    try {
                        System.out.println("Member ID from Matrix is Null. Retrying again in 10 seconds");
                        Thread.sleep(10000);
                        memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
                    } catch (SQLException e) {
                        System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    }
                }
            }
            retryCount++;

        } // end of while loop

        if(!(retryCount < Property.RETRY_COUNT_FOR_GETTING_MEMBERID_FROM_SF_AND_MATRIX) && (memberIDsf==null || memberIDmatrix==null) ) {
            //when the loop has ended after reaching maximum retry count and still at least one of the value is null
            if(memberIDsf==null && memberIDmatrix==null) {
                System.out.println("Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
                _scenario.write("Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
            } else {

                System.out.println("=============================");
                System.out.println("MemberID (Salesforce) : "+memberIDsf+" MemberID (Matrix) : "+memberIDmatrix);
                System.out.println("=============================");

                if(memberIDsf==null ) {
                    System.out.println("Unable to get member ID from Salesforce after retrying for 5 minutes");
                    _scenario.write("Unable to get member ID from Salesforce after retrying for 5 minutes");
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from Salesforce after retrying for 5 minutes");
                } else  {
                    System.out.println("Unable to get member ID from Matrix after retrying for 5 minutes");
                    _scenario.write("Unable to get member ID from Matrix after retrying for 5 minutes");
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from Matrix after retrying for 5 minutes");
                }
            }

        } else { //if loop has ended with values from both end
            try {
                Assert.assertEquals(memberIDsf, memberIDmatrix);
                System.out.println("Member ID (SF) and Member ID (Matrix) Matches");
                _scenario.write("Member ID (SF) and Member ID (Matrix) Matches");
            } catch (AssertionError e) {
                System.out.println("Test failed intentionally due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" MemberID from Matrix : "+memberIDmatrix);
                _scenario.write("Test failed intentionally due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" AND MemberID from Matrix : "+memberIDmatrix);
                fail("TEST FAILED INTENTIONALLY due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" MemberID from Matrix : "+memberIDmatrix);
            }
        }

    }

    // ##################################################################################################
    public void validateMemberIDAfterUpdate(String token, String cardNumber, String environment) throws InterruptedException {

        //Validate if there is no error in CmdLogs in Matrix
        String ReturnMessageFromCmdLogs=null;


        try {
            System.out.println(" ~~~~~ Waiting for 3 minutes before querying CMD logs table ~~~~~~");
            Thread.sleep(180000);
            System.out.println(" ~~~~~ Continuing to query CMD logs table after 3 minutes wait ~~~~~~");

            ReturnMessageFromCmdLogs = matrix.getReturnMessageFromCmdLogsInMatrixAfterUpdate(environment,cardNumber);

            int retryCount =0;
            while (ReturnMessageFromCmdLogs.isEmpty() && retryCount<10){
                System.out.println("CMD log message is empty. Retrying in 30 seconds");
                Thread.sleep(30000);
                ReturnMessageFromCmdLogs = matrix.getReturnMessageFromCmdLogsInMatrixAfterUpdate(environment,cardNumber);
                retryCount++;
            }
        } catch (SQLException e) {
            System.out.println("Unable to get data from Matrix Custom Log. "+e.getMessage());
            _scenario.write("Unable to get data from Matrix Custom Log. "+e.getMessage());
            Assert.fail("Unable to get data from Matrix Custom Log. "+e.getMessage());
        } catch (NullPointerException ne) {
            System.out.println("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
            _scenario.write("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
            Assert.fail("TEST FAILED INTENTIONALLY ! CMD log's'Return Message' is Null after retrying for 5 minutes.");
        }



        try {
            Assert.assertEquals(ReturnMessageFromCmdLogs.toLowerCase(), "SUCCESS".toLowerCase());
            System.out.println("The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+" .The record is successfully processed in Matrix");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
            _scenario.write("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
            Assert.fail("TEST FAILED INTENTIONALLY ! The Cmd Log Return message is - "+ReturnMessageFromCmdLogs+ " .The record is not processed in Matrix ");
        }



        //get member ID from SF //get member ID from Matrix //validate both IDs
        Thread.sleep(10000);
        String memberIDsf = null;
        String memberIDmatrix = null;

        //reading the member id from SF
        try {
            memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
        } catch (Exception e) {
            System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
        }


        //reading member id from Matrix

        try {
            memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
        } catch (SQLException e) {
            System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
        }

        int retryCount = 0;
        while ((memberIDsf==null || memberIDmatrix==null) && retryCount < Property.RETRY_COUNT_FOR_GETTING_MEMBERID_FROM_SF_AND_MATRIX) {

            if(memberIDsf==null && memberIDmatrix==null) {  //when both are null
                System.out.println("Member ID in SF and Matrix are Null. Retrying again in 10 seconds");
                Thread.sleep(10000);
                // READING BOTH ENDS AFTER 10 SECONDS //
                //reading the member id from SF
                try {
                    memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
                } catch (Exception e) {
                    System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                }

                //reading member id from Matrix

                try {
                    memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
                } catch (SQLException e) {
                    System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                }
            } else { //when at least one of the values are null

                System.out.println("=============================");
                System.out.println("MemberID (Salesforce) : "+memberIDsf+" MemberID (Matrix) : "+memberIDmatrix);
                System.out.println("=============================");

                if(memberIDsf==null){ //if only SF side is null
                    //reading the member id from SF
                    try {
                        System.out.println("Member ID from Salesforce is Null. Retrying again in 10 seconds");
                        Thread.sleep(10000);
                        memberIDsf = sfData.getMemberIDFromSF(token,cardNumber);
                    } catch (Exception e) {
                        System.out.println("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        _scenario.write("Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Salesforce for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    }
                }

                if(memberIDmatrix==null){  //if only Matrix side is null
                    //reading member id from Matrix
                    try {
                        System.out.println("Member ID from Matrix is Null. Retrying again in 10 seconds");
                        Thread.sleep(10000);
                        memberIDmatrix = matrix.getMemberIDFromMatrix(environment,cardNumber);
                    } catch (SQLException e) {
                        System.out.println("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        _scenario.write("Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                        Assert.fail("TEST FAILED INTENTIONALLY !. Unable to read MemberID from Matrix for the Card Number : "+cardNumber+" Due to error - "+e.getMessage());
                    }
                }
            }
            retryCount++;

        } // end of while loop

        if(!(retryCount < Property.RETRY_COUNT_FOR_GETTING_MEMBERID_FROM_SF_AND_MATRIX) && (memberIDsf==null || memberIDmatrix==null) ) {
            //when the loop has ended after reaching maximum retry count and still at least one of the value is null
            if(memberIDsf==null && memberIDmatrix==null) {
                System.out.println("Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
                _scenario.write("Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from both Salesforce and Matrix after retrying for 5 minutes");
            } else {

                System.out.println("=============================");
                System.out.println("MemberID (Salesforce) : "+memberIDsf+" MemberID (Matrix) : "+memberIDmatrix);
                System.out.println("=============================");

                if(memberIDsf==null ) {
                    System.out.println("Unable to get member ID from Salesforce after retrying for 5 minutes");
                    _scenario.write("Unable to get member ID from Salesforce after retrying for 5 minutes");
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from Salesforce after retrying for 5 minutes");
                } else  {
                    System.out.println("Unable to get member ID from Matrix after retrying for 5 minutes");
                    _scenario.write("Unable to get member ID from Matrix after retrying for 5 minutes");
                    Assert.fail("TEST FAILED INTENTIONALLY !. Unable to get member ID from Matrix after retrying for 5 minutes");
                }
            }

        } else { //if loop has ended with values from both end
            try {
                Assert.assertEquals(memberIDsf, memberIDmatrix);
                System.out.println("Member ID (SF) and Member ID (Matrix) Matches");
                _scenario.write("Member ID (SF) and Member ID (Matrix) Matches");
            } catch (AssertionError e) {
                System.out.println("Test failed intentionally due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" MemberID from Matrix : "+memberIDmatrix);
                _scenario.write("Test failed intentionally due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" AND MemberID from Matrix : "+memberIDmatrix);
                fail("TEST FAILED INTENTIONALLY due to Member ID discrepancy between Salesforce and Matrix. MemberID from Salesforce : "+memberIDsf+" MemberID from Matrix : "+memberIDmatrix);
            }
        }

    }
// ####################################################################################################33


}





