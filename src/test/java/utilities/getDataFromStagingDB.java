package utilities;

import cucumber.api.Scenario;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class getDataFromStagingDB {
    public Scenario _scenario;
    public createDbConnection createDbConnection;


    public getDataFromStagingDB(Scenario _scenario) {
    this._scenario=_scenario;


    }

    public HashMap<String, String> readIsProcessedFromStagingDB (String environment, String minimumValue, String maximumValue) throws ClassNotFoundException, SQLException, InterruptedException {

        String query = "select isProcessed,ErrorMessage from dfsstaging.dbo.PreloadCards where CRR_MIN_NUM='"+minimumValue+"' and CRR_MAX_NUM='"+maximumValue+"';";
        System.out.println(query);


        createDbConnection db = new createDbConnection();
        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {

            //querying for the first time
            resultSet = db.queryDB(environment, query);
            int retryCount=0;
            while (!resultSet.next() && retryCount<5){
                System.out.println("~~~~~~ Result is still empty. Retrying in another 30 seconds ~~~~~~");
                Thread.sleep(30000);
                resultSet = db.queryDB(environment, query);
                retryCount++;
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Exception occurred during reading result set from database - "+e.getMessage());
            throw e;
        }

        int columnCount = 0;
        try {
            rsmd = resultSet.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("Exception occurred during reading column count from database - "+e.getMessage());
            throw e;
        }

        System.out.println("column count : "+columnCount);

        HashMap<String, String> dataset = new HashMap<>();

        //getting the resultset again
        resultSet = db.queryDB(environment, query);

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                            dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                    _scenario.write(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from staging");
           // _scenario.write("Successfully collected data from staging");

        } else {
            System.out.println("Result set is empty");
          //  _scenario.write("Result set is empty");
        }

        return dataset;


    }

    public ArrayList readNewCardRangeCountFromMatrixDB (String environment, String minimumValue, String maximumValue) throws ClassNotFoundException, SQLException, InterruptedException {

        String query = "select CardNo from MatrixTpReward.dbo.Card where CardNo between '"+ minimumValue+"' and '"+maximumValue+"' and MembershipStatusCode='INACTIVE';";


        createDbConnection db = new createDbConnection();
        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);

            int retryCount=0;
            while (!resultSet.next()  && retryCount<5){
                System.out.println("Result has records ? - "+(resultSet.next()));
                System.out.println("~~~~~~ Result is still empty. Retrying in another 30 seconds ~~~~~~");
                Thread.sleep(30000);
                resultSet = db.queryDB(environment, query);
                retryCount++;
            }
            //filling the resultset again
            resultSet = db.queryDB(environment, query);

        } catch (ClassNotFoundException | InterruptedException e) {
            System.out.println("Exception occurred during reading result set from database - "+e.getMessage());
            throw e;
        }

        int columnCount = 0;
        try {
            rsmd = resultSet.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("Exception occurred during reading column count from database - "+e.getMessage());
            throw e;
        }

        ArrayList rowDataList = new ArrayList();

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                HashMap<String, String> dataset = new HashMap<>();

                for(int i=1; i<=columnCount;i++) {

                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                        dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                    _scenario.write(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop
                    rowDataList.add(dataset);
            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from staging");
            _scenario.write("Successfully collected data from staging");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return rowDataList;

    }

}
