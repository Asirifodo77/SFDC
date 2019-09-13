package utilities;

import cucumber.api.Scenario;
import org.testng.annotations.Test;
import page_objects.Cobrand.Cobrand_pageObjects;
import property.Property;
import step_definitions.Hooks;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Date {
    @Test
    public void testdate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Today ==== "+dtf.format(now));


        String date = "7/9/2018";
        String[] dateParts = date.split("/");
        String day = dateParts[0];
        if(day.toCharArray().length<2) {
            day="0"+day;
        }

        String month = dateParts[1];
        if(month.toCharArray().length<2) {
            month="0"+month;
        }

        String year = dateParts[2];
        System.out.println(day+"/"+month+"/"+year);
        //System.out.println(year+"-"+month+"-"+day);
    }

    @Test
    public void testJsonWrite() {

    }

    @Test
    public void testIntConversion(){
        String MinimumValueExp = "520000514922";
        String MaximumValueExp = "520000514925";
        Long minimumValInt = Long.parseLong(MinimumValueExp);
        Long maximumValInt = Long.parseLong(MaximumValueExp);

        System.out.println("The count is : "+(maximumValInt-minimumValInt)+1);
    }

    @Test
    public void getMonthValue() throws ParseException {
        String monthName = "February"; // German for march
        java.util.Date date = new SimpleDateFormat("MMMM").parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int monthIntVal = cal.get(Calendar.MONTH)+1;
        String monthValString = "";
        if(monthIntVal<10) {
            monthValString ="0"+String.valueOf(monthIntVal);
        }else {
            monthValString=String.valueOf(monthIntVal);
        }
        System.out.println(monthValString);
    }

    @Test
    public void testLastdateMonth() throws ParseException {
        dateOperations dateOpr = new dateOperations();
        System.out.println(dateOpr.getLastDateOfMonth("March"));
    }

    @Test
    public void getAccessToken() throws Exception {
        getSFaccessToken accessToken = new getSFaccessToken();
        String token = accessToken.getSFaccessToken();
        System.out.println("Access token "+token);
    }

    @Test
    public void testEndDateUpdate() throws FileNotFoundException {
        Scenario _scenario;
        _scenario = Hooks._scenario;
        updateCycleEndDate updateDate = new updateCycleEndDate(_scenario);
        updateDate.updateCycleEndDateInSF("520000562173","2019-01-31");
    }

    @Test
    public void readTestDataFromExcel() throws Exception {
        readExcelData excel = new readExcelData();
        HashMap<String,String> excelDataSet;
//        excelDataSet = excel.getColumData("ExcelTestData","Sheet1",0);
//        System.out.println("Size of excelTestDataSet : "+ excelDataSet.size());
//        for (int i=0;i<excelDataSet.size();i++) {
//            //memberData = (HashMap<String, String>) excelDataSet.get(i);
//            System.out.println("Column Data : "+excelDataSet.get(i));
//        }
        int colCount =excel.getColumnCount("ExcelTestData","Sheet1");
        System.out.println("Number of Members "+(colCount-1));
        for (int i=1;i<colCount;i++) {
            excelDataSet = excel.getKeyValuesForGivenMember("ExcelTestData", "Sheet1", i);

            for (Map.Entry<String, String> entry : excelDataSet.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
        //HashMap<Integer,HashMap> memData = new HashMap<>();
        //for(int i=0; i<excelDataSet.size();i++){
           // System.out.println("Member "+i+" TitleCode : "+excelDataSet.get(i));
            //System.out.println("Member "+i+" TitleCode : "+excelDataSet.get());
        //}
        //System.out.println(excelDataSet);

        //HashMap h1 = (HashMap) excelDataSet.get(0);
        //System.out.println("Member 1 EmailAddressText  ==== "+ h1.get("EmailAddressText").toString());

        //HashMap h2 = (HashMap) excelDataSet.get(1);
        //System.out.println("Member 1 EmailAddressText  ==== "+ h2.get("EmailAddressText").toString());
        //HashMap<String,String> h1= (HashMap<String, String>) excelDataSet.get(1);

//        System.out.println("TitleCode : "+excelDataSet.get("TitleCode"));
//        System.out.println("GenderCode : "+excelDataSet.get("GenderCode"));
//        System.out.println("EmailAddressText : "+excelDataSet.get("EmailAddressText"));
    }

    @Test
    public void testIsCardRangeExist() throws Exception {
        getSFaccessToken accessToken = new getSFaccessToken();
        getDataFromSF sf = new getDataFromSF();

        System.out.println("Card Range Exists ? - "+sf.isCardRangeExist(accessToken.getSFaccessToken(),Property.INVALID_CARD_RANGE_MAXIMUM_VALUE, Property.INVALID_CARD_RANGE_MINIMUM_VALUE,Property.INVALID_CARD_RANGE_STORE_LOCATION));
    }

    @Test
    public void testIsCardExist() throws Exception {
        getSFaccessToken accessToken = new getSFaccessToken();
        getDataFromSF sf = new getDataFromSF();

        System.out.println("Card Exisit ? - "+sf.isCardExist(accessToken.getSFaccessToken(),Property.INVALID_CARD_NUMBER));
    }

    @Test
    public void testDateConversion() throws Exception {
        String rawDate ="31 Mar 2021";

        //day
        String day = rawDate.substring(0,2).trim();
        System.out.println("day : "+day);

        //month
        String month = rawDate.substring(3,6).trim();
        System.out.println("Month : "+month);
        dateOperations dateOpr = new dateOperations();
        System.out.println("Int value of Month : "+dateOpr.getIntValueOfMonth(month));

        String year = rawDate.substring(7,11);
        System.out.println("Year : "+year);

    }

    @Test
    public void testdouble() throws Exception {
        String price = "$515.97";
        System.out.println(price.replace("$","").trim());
    }

}
