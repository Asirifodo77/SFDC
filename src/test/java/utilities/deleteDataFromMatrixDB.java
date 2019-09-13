package utilities;

import cucumber.api.Scenario;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class deleteDataFromMatrixDB {
    public Scenario _scenario;
    public createDbConnection createDbConnection;

    public deleteDataFromMatrixDB(Scenario _scenario) {
        this._scenario=_scenario;
    }

    public void deleteCardRangeromMatrix (String environment,String minimumValue, String maximumValue) throws FileNotFoundException, SQLException, ClassNotFoundException {
        System.out.println(" ====================================== INSIDE DELETE CARD RANGE IN MATRIX FUNCTION ============================================");


                //querry db and check if record exists
                createDbConnection dbConnection = new createDbConnection();
                String readQuery = "select CardNo from MatrixTpReward.dbo.Card where CardNo between '"+minimumValue+"' and '"+maximumValue+"' and MembershipStatusCode='INACTIVE';";
                String deleteQquery = "Delete from MatrixTpReward.dbo.Card where CardNo between '"+minimumValue+"' and '"+maximumValue+"' and MembershipStatusCode='INACTIVE';";
                ResultSet result = dbConnection.queryDB(environment, readQuery);

                //if the records available
                if (result!=null) {
                    // go ahead and delete the record
                    try {
                        dbConnection.deleteRow(environment, deleteQquery);
                    } catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
                        System.out.println(e.getLocalizedMessage());
                        _scenario.write(e.getLocalizedMessage());
                    }

                    //check if record has been deleted
                    ResultSet verifyResult = null;
                    try {
                        verifyResult = dbConnection.queryDB(environment, readQuery);
                    } catch (ClassNotFoundException e) {
                        System.out.println(e.getLocalizedMessage());
                        _scenario.write(e.getLocalizedMessage());
                    }

                    if (verifyResult!=null && verifyResult.next()) {
                        System.out.println("FAIL TO DELETE Existing Card Range data");
                        _scenario.write("FAIL TO DELETE Existing Card Range data");
                    } else {
                        System.out.println("Successfully DELETED Existing Card Range data From : "+minimumValue+" to : "+maximumValue );
                        _scenario.write("Successfully DELETED Existing Card Range data From : "+minimumValue+" to : "+maximumValue);
                    }

                } else {
                    System.out.println("No Existing Card Range data found in the Matrix ");
                    _scenario.write("No Existing Card Range data found in the Matrix ");
                }

         //end of else
    }

    public void deleteCardRangeFromStaging(String environment,String minimumValue, String maximumValue) throws ClassNotFoundException, SQLException {

        System.out.println(" ====================================== INSIDE DELETE CARD RANGE IN STAGING FUNCTION ============================================");


        //querry db and check if record exists
        createDbConnection dbConnection = new createDbConnection();
        String readQuery = "select * from dfsstaging.dbo.PreloadCards where CRR_MIN_NUM='"+minimumValue+"' and CRR_MAX_NUM='"+maximumValue+"';";
        String deleteQquery = "Delete from dfsstaging.dbo.PreloadCards where CRR_MIN_NUM='"+minimumValue+"' and CRR_MAX_NUM='"+maximumValue+"';";


        ResultSet result = null;
        try {
            result = dbConnection.queryDB(environment, readQuery);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            _scenario.write(e.getLocalizedMessage());
        }

        //if the records available
        if (result!=null) {
            // go ahead and delete the record
            try {
                dbConnection.deleteRow(environment, deleteQquery);
            } catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
                System.out.println(e.getLocalizedMessage());
                _scenario.write(e.getLocalizedMessage());
            }

            //check if record has been deleted
            ResultSet verifyResult = null;
            try {
                verifyResult = dbConnection.queryDB(environment, readQuery);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
                _scenario.write(e.getLocalizedMessage());
            }

            if (verifyResult!=null && verifyResult.next()) {
                System.out.println("FAIL TO DELETE Existing Records in PreloadCards table");
                _scenario.write("FAIL TO DELETE Existing Records in PreloadCards table");
            } else {
                System.out.println("Successfully DELETED Existing PreloadCards table data From : "+minimumValue+" to : "+maximumValue );
                _scenario.write("Successfully DELETED Existing PreloadCards table data From : "+minimumValue+" to : "+maximumValue);
            }

        } else {
            System.out.println("No Existing PreloadCards table data found in the Staging ");
            _scenario.write("No Existing PreloadCards table data found in the Staging ");
        }

        //end of else
    }

}
