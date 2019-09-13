package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dateOperations {

    public String getIntValueOfMonth(String monthName) throws ParseException {

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
        return monthValString;
    }

    public String getLastDateOfMonth(String monthName) throws ParseException {
        java.util.Date date = new SimpleDateFormat("MMMM").parse(monthName);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDate = calendar.getActualMaximum(Calendar.DATE);
        String LastDateString = "";

        if(lastDate<9) {
            LastDateString = "0"+String.valueOf(lastDate);
        } else {
            LastDateString=String.valueOf(lastDate);
        }

        System.out.println("Last Date: " + LastDateString);
        return LastDateString;

    }
    public String convertSimpleDateIntoNumberDate(String rawDate) throws Exception {
        //day
        String day = rawDate.substring(0,2).trim();

        //month
        String month = null;
        try {
            month = getIntValueOfMonth(rawDate.substring(3,6).trim());
        } catch (ParseException e) {
            throw new Exception("Unable to convert the Text month value to Int due to exception - "+ e.getMessage());
        }
        //year
        String year = rawDate.substring(7,11);

        return year+"-"+month+"-"+day;
    }

}
