package utilities;

import java.util.*;

public class readExcelData  {

    private List columnNamesList;

    public List<String> getData(String fileName, String sheetName) throws Exception {

        getExcelTestData dataObject = new getExcelTestData();
        int rowCount = 0;
        int colCount = 0;
        try {
            rowCount=dataObject.getRowCount(fileName,sheetName);
            colCount=dataObject.getColumnCount(fileName,sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Object[][] data = new Object[rowCount][colCount];
        //Object[] memberData = new Object[colCount-1];  // this will hold hashmap data for each member
        HashMap<Integer,String> memberRowData = new HashMap<>();
        HashMap<String,String> memberColumnData = new HashMap<>();  // stores member n, column i data

        // =========== reading the list of field names from 0th column and store it in an array
        String[] fieldName = new String[rowCount];
        String[][] memberValues= new String[colCount][rowCount];
        System.out.println("Row count "+rowCount);
        System.out.println("Column count "+colCount);

        for (int x=0;x<rowCount;x++){  // loping through and storing them in the array
            //System.out.println(dataObject.readFromExcel(fileName,sheetName,x+1,0));
            fieldName[x] = dataObject.readFromExcel(fileName,sheetName,x+1,0);
        }

        // ======= reading the nth member's value set and stor it in 2d string array
        for(int y=0; y<colCount-1; y++){
            for (int z=0; z<rowCount; z++){
                //System.out.println(dataObject.readFromExcel(fileName,sheetName,z+1,y+1));
                memberValues[y][z]=dataObject.readFromExcel(fileName,sheetName,z+1,y+1);
                System.out.println("row "+z+" col "+y+" : "+ memberValues[y][z]);
            }
        }

        //ArrayList<HashMap<String,String>> objArray = new ArrayList<>();
        Object[][] array=new Object[colCount][rowCount];
        List<String> allData = null;
        HashMap<String,String> tempArray = new HashMap<>();
        ArrayList<String> fieldNamesList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();
        Hashtable<String,String> columnMapping;
        ArrayList<String[]> tableValues;
        Object obj[][][] = new Object[colCount-1][fieldName.length][array.length]; // members // field name // value
        for (int i=0 ; i<colCount-1;i++){  // for each member, store the key value pairs
            for (int j=0;j<rowCount;j++){
                array[i][j]=memberValues[i][j];
                //System.out.println("Data Set for member "+i+" : "+array[i][j]);
                tempArray.put(fieldName[j], (String) array[i][j]);
                //System.out.println("Member "+i+" "+fieldName[j]+" : "+tempArray.get(fieldName[j]));
                fieldNamesList.add(j,fieldName[j]);
                valueList.add(j,memberValues[i][j]);
                obj[i][j][0]=fieldName[j];
                System.out.println("Field names "+obj[i][j][0]);
                obj[i][i][j]=array[i][j];
                System.out.println("Values "+obj[i][i][j]);


            }


//            System.out.println("Member "+i+" "+obj[0][0][0].toString()+" : "+obj[0][0][1].toString());
//            System.out.println("Member "+i+" "+obj[0][1][0].toString()+" : "+obj[0][1][1].toString());
//            System.out.println("Member "+i+" "+obj[0][2][0].toString()+" : "+obj[0][2][1].toString());

        }
        System.out.println("##### Member  "+obj[0][2][1].toString());





        //System.out.println("All data == : "+ allData.get(0));
        //System.out.println("MEMEBER DATA AFTER STORING out of loop : "+memberColumnData);
//        for (int l=0;l<memberRowData.size();l++) {
//            memberColumnData.put(l,memberRowData.get());
//        }

        //System.out.println("Member 1 data == " + memberColumnData.get(0));
        //System.out.println("Member 2 data == " +memberColumnData.get(1));

        return allData;
    }

    public int getColumnCount(String fileName, String sheetName)throws Exception {
        getExcelTestData dataObject = new getExcelTestData();
        return dataObject.getColumnCount(fileName,sheetName);
    }

    public List getInputDataList(String fileName, String sheetName) throws Exception {
        getExcelTestData dataObject = new getExcelTestData();
        int rowCount = 0;
        int colCount = 0;
        try {
            rowCount = dataObject.getRowCount(fileName, sheetName);
            colCount = dataObject.getColumnCount(fileName, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List rowList = new ArrayList();
        columnNamesList = getInputDataColumnNames(fileName, sheetName, colCount);
        for (int rowNum = 0; rowNum <= rowCount; rowNum++) {

            Map inputDataMap = new LinkedHashMap();
            for (int colNum = 0; colNum <= colCount; colNum++) {   //inputDataMap = new LinkedHashMap();
                String key = (String) columnNamesList.get(colNum);
                String value = dataObject.readFromExcel(fileName,sheetName,colNum,rowNum);
                inputDataMap.put(key, value);
            }
            if (inputDataMap != null) {
                rowList.add(inputDataMap);
            }
        }
        return rowList;
    }

    public List getInputDataColumnNames(String fileName, String sheetName, int colCount) throws Exception {
        getExcelTestData dataObject = new getExcelTestData();
        List<String> columnNames = new ArrayList();
        //int colCount = excel.getColumnCount(inputDataSheetName);
        for (int i=0; i<colCount;i++) {
        columnNames.add(dataObject.readFromExcel(fileName,sheetName,i,1));
            }
        return columnNames;
}

    public List<String> getColumData(String fileName, String sheetName, int colNumber ) throws Exception {
        getExcelTestData dataObject = new getExcelTestData();
        return dataObject.getColumnValues(fileName,sheetName,colNumber);
    }

    public List<String> getFirstColumnData(String fileName, String sheetName) throws Exception {
        getExcelTestData dataObject = new getExcelTestData();
        return dataObject.getColumnValues(fileName,sheetName,0);
    }

    public HashMap<String,String> getKeyValuesForGivenMember(String fileName, String sheetName, int memberPosition) throws Exception {
        HashMap<String,String> keyValueData = new HashMap<>();
        int rowCount=getFirstColumnData(fileName,sheetName).size();
        List<String> fieldNames=new ArrayList<>();
        List<String> valueList = new ArrayList<>();

        fieldNames = getFirstColumnData(fileName,sheetName);
        valueList = getColumData(fileName,sheetName,memberPosition);
        for (int i=0;i<rowCount;i++){
            keyValueData.put(fieldNames.get(i),valueList.get(i));
        }
//        for (Map.Entry<String, String> entry : keyValueData.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//        }
        return keyValueData;
    }

}
