package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import property.Property;

public class getExcelTestData
{

    public static XSSFSheet ExcelWSheet;

    public static XSSFWorkbook ExcelWBook;

    public static XSSFCell Cell;

    public static XSSFRow Row;

    public static String filepath= Property.EXCEL_TEST_DATA_FILE_PATH;
    public static String extension=Property.EXCEL_FILE_EXTENSION;

    public int getColumnCount(String filename, String sheetname) throws Exception {


        int columns = 0;
        try {
            excelSetup(filename, sheetname);
            columns = ExcelWSheet.getRow(0).getLastCellNum();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columns;
    }

    public int getRowCount(String filename, String sheetname) throws Exception {


        int rows = 0;
        try {
            excelSetup(filename, sheetname);
            rows = ExcelWSheet.getLastRowNum();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    public List<String> getColumnValues(String filename, String sheetname, int colNo ) throws Exception {
        List<String> colValues = new ArrayList<>();
        try {
            excelSetup(filename,sheetname);
        }catch (Exception e) { e.printStackTrace(); }
        int rowCount = getRowCount(filename,sheetname);
        //System.out.println("Row count "+rowCount);
        for(int i=0;i<rowCount;i++){
            //System.out.println("Data == "+ ExcelWSheet.getRow(i+1).getCell(colNo).getStringCellValue());
            colValues.add(i,ExcelWSheet.getRow(i+1).getCell(colNo).getStringCellValue());
        }

        return colValues;
    }


    public String readFromExcel(String filename, String sheetname, int row, int column) throws Exception{

        try {
            excelSetup(filename,sheetname);
        }catch (Exception e) { e.printStackTrace(); }

        String data = ExcelWSheet.getRow(row).getCell(column).getStringCellValue();

        return data;
    }


    public void excelSetup(String filename, String sheetname) throws Exception{

        try {
            FileInputStream ExcelFile = new FileInputStream(filepath+filename+extension);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetname);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
