package utilities;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class createDbConnection {

    public Connection conn = null;
    public PreparedStatement stmt = null;


    public ResultSet queryDB(String environment, String Query) throws ClassNotFoundException {
        //connecting to database only
        System.out.println("================= INSIDE QUERY DB FUNCTION ========================");
        readTestData testData = new readTestData();
        ResultSet resultSet=null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try {
            //String env = System.getProperty("Environment");
            //System.out.println("------" + env + "------");
            if (environment.equalsIgnoreCase("Preprod")){
                conn = DriverManager.getConnection("jdbc:sqlserver://" +testData.readTestData("DB_DFSDM")+"" , testData.readTestData("DB_DFSDM_Username"), testData.readTestData("DB_DFSDM_Pwd"));
                System.out.println("Execution Environment: Preprod");
            } else if (environment.equalsIgnoreCase("QACore2")){
                conn = DriverManager.getConnection("jdbc:sqlserver://" +testData.readTestData("DB_QACore2")+"", testData.readTestData("DB_QACore2_Username"), testData.readTestData("DB_QACore2_Pwd"));
                System.out.println("Execution Environment: QACore2");
            }
            // Execute a query
            stmt = conn.prepareStatement(Query);
            try {
                resultSet = stmt.executeQuery();
            } catch (SQLServerException e) {
                System.out.println(e.getMessage());
                throw e;
            }
            if(resultSet==null){
                System.out.println("No records available. My SQL resultset :- Returned recordset is Null");
            }
        } catch (SQLException eSQL) {
            System.out.println("No records available. My SQL resultset -  " + eSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void deleteRow(String environment, String Query) throws ClassNotFoundException, FileNotFoundException, SQLException {
        //connecting to database only
        System.out.println("================= INSIDE DELETE ROW FUNCTION ========================");
        readTestData testData = new readTestData();


        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        if (environment.equalsIgnoreCase("Preprod")){

            conn = DriverManager.getConnection("jdbc:sqlserver://" +testData.readTestData("DB_DFSDM")+"" , testData.readTestData("DB_DFSDM_Username"), testData.readTestData("DB_DFSDM_Pwd"));
            System.out.println("Execution Environment: Preprod");
        } else if (environment.equalsIgnoreCase("QACore2")){
            conn = DriverManager.getConnection("jdbc:sqlserver://" +testData.readTestData("DB_QACore2")+"", testData.readTestData("DB_QACore2_Username"), testData.readTestData("DB_QACore2_Pwd"));
            System.out.println("Execution Environment: QACore2");
        }

        try {
            // Execute delete query
             PreparedStatement statement = conn.prepareStatement(Query);
             statement.executeUpdate();
             System.out.println("Successfully deleted the row");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getDatabaseTableRecords(String environment, String query) throws ClassNotFoundException, SQLException, InterruptedException {
        createDbConnection db = new createDbConnection();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        System.out.println("Time Now :- "+sdf.format(date));
        System.out.println("DB reads >>>>>> Adding 3 minutes of waiting time before querying from Matrix DB");
        Thread.sleep(180000);
        java.util.Date dateNew = new Date();
        System.out.println("Time After Waiting :- " +sdf.format(dateNew));
        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in DB result set : " + e.getMessage());
            e.printStackTrace();
        }
        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();
        if (resultSet!=null && columnCount>0) {
            System.out.println("---printing out the data collected from Matrix");
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    if(resultSet.getString(i)!=null) {
                        dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop

            } // end of while
            //Message when colleted data successfully
            System.out.println("---Successfully collected data from Matrix");
        } else {
            System.out.println("Result set is empty");
        }
        return dataset;
    }

    public HashMap<String, String> getDatabaseTableRecordsForProcessedValueChecks(String environment, String query) throws ClassNotFoundException, SQLException, InterruptedException {
        createDbConnection db = new createDbConnection();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        System.out.println("Time Now :- "+sdf.format(date));
        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in DB result set : " + e.getMessage());
            e.printStackTrace();
        }
        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();
        if (resultSet!=null && columnCount>0) {
            System.out.println("---printing out the data collected from Matrix");
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    if(resultSet.getString(i)!=null) {
                        dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop

            } // end of while
            //Message when colleted data successfully
            System.out.println("---Successfully collected data from Matrix");
        } else {
            System.out.println("Result set is empty");
        }
        return dataset;
    }

}
