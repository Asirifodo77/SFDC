package utilities;

import property.Property;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class AppendCycleID {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        /*String t = "2018/11/56 - 2020/11/30 Prestige Diamond";//asso
        String ghghg = "2018/11/30 - 2020/12/22 Prestige Diamond";//disasso

        String ghghghghghg = ghghg.split(" - ")[0];
        String ghghghghghgsss = t.split(" - ")[1];
        StringBuffer sb = new StringBuffer(ghghghghghg).append(" - ").append(ghghghghghgsss);
        String jj = sb.toString();

        System.out.println("array is :" +jj);
        System.out.println("array is :" +jj);
        System.out.println("array is :" +jj);

        List<String> listArrai  = new ArrayList<>();
        listArrai.add("AAAAA");

        listArrai.remove(0);
        listArrai.add("ZZZZZZ");
        listArrai.add("BBXXXXXBBB");
        System.out.println("array is :" +listArrai);*/
        /*String env = "Preprod";
        createDbConnection db = new createDbConnection();
        String isProcessedQuery = "select TOP(1) isProcessed, errorMessage, AutoID, ProcessedDateTime from DFSSTAGING.DBO.CustomerTier;";
        System.out.println("Getting isProcessed status - Query : select isProcessed, errorMessage, AutoID, ProcessedDateTime from DFSSTAGING.DBO.CustomerTier;order by AddedOn desc");
        HashMap<String, String> isProcessedMAP = db.getDatabaseTableRecords(env, isProcessedQuery);
        String isProcessedVal = isProcessedMAP.get("isProcessed");
        String errorMessage = isProcessedMAP.get("errorMessage");
        System.out.println("Initial isProcessed value is :" + isProcessedVal);
        System.out.println("Query processed");
        System.out.println("++++++++++++++++++++++++++ "+ isProcessedVal);
        System.out.println("ispro "  +isProcessedVal);
        System.out.println("errorMessage :" +errorMessage);

        String ProcessedDateTime = isProcessedMAP.get("ProcessedDateTime");
        System.out.println("ProcessedDateTime :"+ProcessedDateTime);

        if (!(ProcessedDateTime.isEmpty())){
            System.out.println("Not Empty :"+ProcessedDateTime);
        }else{
            System.out.println("Empty :"+ProcessedDateTime);
        }
*/

        String cycleStartDate = "";
        String cycleEndDate=  "";
        /*Calendar cal = Calendar.getInstance();
        cycleStartDate = "01 Jul 2016";
        Date date = new SimpleDateFormat("dd MMM yyyy").parse(cycleStartDate);
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.YEAR, 2);
        cycleEndDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
        System.out.println("Cycle Start Date: " + cycleStartDate);
        System.out.println("Cycle End Date: " + cycleEndDate);*/

       /* Calendar cal = Calendar.getInstance();
        String dateTodayStr = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        dateTodayStr = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        System.out.println("Cycle End Date: " + dateTodayStr);
        String month = dateTodayStr.split("-")[1];
        System.out.println("month: " + month);

        cal.add(Calendar.MONTH, -2);
        dateTodayStr = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        System.out.println("Cycle End Date2: " + dateTodayStr);
        String month2 = dateTodayStr.split("-")[1];
        System.out.println("month2: " + month2);*/

        /*Calendar cal2 = Calendar.getInstance();
        String dateTodayStr2 = new SimpleDateFormat("dd-MMM-yyyy").format(cal2.getTime());
        cal2.add(Calendar.MONTH, -2);
        dateTodayStr2 = new SimpleDateFormat("dd-MMM-yyyy").format(cal2.getTime());
        System.out.println("Cycle End Date222: " + dateTodayStr2);
        String month2 = dateTodayStr2.split("-")[1];
        System.out.println("month222: " + month2);*/

        double EXCHANGE_RATE_JPY_PREPROD=111.85;
        /*System.out.println("Cycle End Date222: " + (9018880.80/EXCHANGE_RATE_JPY_PREPROD));
        DecimalFormat df = new DecimalFormat("#.##");
        String asas = df.format(9018880.80/EXCHANGE_RATE_JPY_PREPROD);
        System.out.println("Cycle End Date222: " + asas);*/

        /*String taxUSD = "9,018,880.80 JPY".split(" ")[0].replaceAll(",","");
        double o = Double.parseDouble(taxUSD);
        DecimalFormat dfs = new DecimalFormat("#.##");
        String asass = dfs.format(o/EXCHANGE_RATE_JPY_PREPROD);
        System.out.println("Cycle ssssssss2: " + "$"+asass);*/

        /*String taxUSD = "1,677,790.00 JPY".split(" ")[0].replaceAll(",","");
        double taxUSDdoubleRate = Double.parseDouble(taxUSD);
        DecimalFormat dfs = new DecimalFormat("#.##");
        String env = System.getProperty("Environment");
        double exchangeRate = 111.85;
        //exchangeRate = Property.EXCHANGE_RATE_JPY_PREPROD;
        String taxUSDAm = dfs.format(taxUSDdoubleRate/exchangeRate);
        System.out.println("Tax Amount (USD): " + taxUSDAm);*/

        /*String t = "0";
        String hh = "45254";

        int fullpoint = Integer.parseInt(t) + Integer.parseInt(hh);
        String fullPointCalculation = String.valueOf(fullpoint);
        System.out.println("fullPointCalculation: " + fullPointCalculation);*/

        String ght = "10/4/2019";
        Date dateTodayStrssDate = new SimpleDateFormat("dd/M/yyyy").parse(ght);
        String dateTodayStrss = new SimpleDateFormat("dd/M/yyyy").format(dateTodayStrssDate);
        System.out.println("fullPointCalculation: " + dateTodayStrss);
        Calendar cal = Calendar.getInstance();
        String dateTodayStr = new SimpleDateFormat("dd/M/yyyy").format(cal.getTime());

    }
}
