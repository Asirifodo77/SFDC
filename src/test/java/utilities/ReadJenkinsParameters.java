package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadJenkinsParameters {

    public static String month = "";
    public static String month_renew7 = "";

    public static String getJenkinsParameter(String testDataString){
        String jenkinsString = null;
        String env = System.getProperty("Environment");
        String tier = System.getProperty("Tier");
        String suit = System.getProperty("Suit");
        if (tier==null){
            tier = "Not_Added";
        }
        if (suit==null){
            suit = "Not_Added";
        }

        Calendar cal = Calendar.getInstance();
        String dateTodayStr = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        dateTodayStr = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        month = dateTodayStr.split("-")[1];

        Calendar cal2 = Calendar.getInstance();
        String dateTodayStr2 = new SimpleDateFormat("dd-MMM-yyyy").format(cal2.getTime());
        cal2.add(Calendar.MONTH, -2);
        dateTodayStr2 = new SimpleDateFormat("dd-MMM-yyyy").format(cal2.getTime());
        month_renew7 = dateTodayStr2.split("-")[1];

        try{
            if (suit.equalsIgnoreCase("Regression") || suit.equalsIgnoreCase("Not_Added")){
                if (tier.equalsIgnoreCase("All")) {
                    if (testDataString.contains("MemberRenewalCardFlow5") || testDataString.contains("MemberRenewalCardFlow8") || testDataString.contains("MemberRenewalCardFlow9") || testDataString.contains("MemberRenewalCardFlow10")|| testDataString.contains("MemberRenewalCardFlow11") || testDataString.contains("MemberRenewalCardFlow12")){
                        jenkinsString = new StringBuffer(testDataString).append("_" + env).append("_"+month).toString();
                    }else if(testDataString.contains("MemberRenewalCardFlow7")){
                        jenkinsString = new StringBuffer(testDataString).append("_" + env).append("_"+month_renew7).toString();
                    }else{
                        jenkinsString = new StringBuffer(testDataString).append("_" + env).toString();
                    }
                }else {
                    if(testDataString.equalsIgnoreCase("Advance_Member_search_Partial_Card_Number")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+env).toString();
                    }else if (testDataString.contains("MemberRenewalCardFlow5") || testDataString.contains("MemberRenewalCardFlow8") || testDataString.contains("MemberRenewalCardFlow9") || testDataString.contains("MemberRenewalCardFlow10")|| testDataString.contains("MemberRenewalCardFlow11")|| testDataString.contains("MemberRenewalCardFlow12")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_" + env).append("_"+month).toString();
                    }else if(testDataString.contains("MemberRenewalCardFlow7")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_" + env).append("_"+month_renew7).toString();
                    }else{
                        if (!(tier.equalsIgnoreCase("Not_Added"))){
                            jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                        }else{
                            jenkinsString = new StringBuffer(testDataString).append("_"+env).toString();
                        }
                    }
                }
            }else{
                if (testDataString.equalsIgnoreCase("Advance_Member_search_Partial_Card_Number")){
                    jenkinsString = new StringBuffer(testDataString).append("_"+env).toString();
                }else if (testDataString.equalsIgnoreCase("ATP_Auto_Downgrade_Card_Number") || testDataString.contains("Auto_Downgrade_Start_timestamp") || testDataString.contains("Auto_Downgrade_End_timestamp") || testDataString.contains("Auto_Downgrade_TotalRecords")){
                    jenkinsString = new StringBuffer(testDataString).append("_"+env).toString();
                }else if (testDataString.equalsIgnoreCase("Member_Card_Management_Cancel") || testDataString.equalsIgnoreCase("Cancel_Start_timestamp") || testDataString.equalsIgnoreCase("Cancel_End_timestamp") || testDataString.equalsIgnoreCase("Cancel_TotalRecords")){
                    if (tier.equalsIgnoreCase("LoyalT") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else if (testDataString.equalsIgnoreCase("Member_Transaction_Association_Disassociation_Division") || testDataString.equalsIgnoreCase("Member_Transaction_Association_Disassociation_TransactionDate")) {
                    jenkinsString = testDataString;
                }else if(testDataString.equalsIgnoreCase("ATP_Reactivation_Card_Number") || testDataString.contains("Reactivation_Start_timestamp") || testDataString.contains("Reactivation_End_timestamp") || testDataString.contains("Reactivation_TotalRecords")){
                    jenkinsString = new StringBuffer(testDataString).append("_" + env).toString();
                }else if (testDataString.equalsIgnoreCase("Member_Card_Management_SuspendBlacklist_Card") || testDataString.equalsIgnoreCase("SuspendBlacklist_TotalRecords") || testDataString.equalsIgnoreCase("SuspendBlacklist_Start_timestamp") || testDataString.equalsIgnoreCase("SuspendBlacklist_End_timestamp")) {
                    if (tier.equalsIgnoreCase("LoyalT") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else if (testDataString.equalsIgnoreCase("Member_Card_Management_SuspendResume_Card") || testDataString.equalsIgnoreCase("SuspendResume_TotalRecords") || testDataString.equalsIgnoreCase("SuspendResume_Start_timestamp") || testDataString.equalsIgnoreCase("SuspendResume_End_timestamp")) {
                    if (tier.equalsIgnoreCase("Jade") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else if (testDataString.equalsIgnoreCase("Member_Card_Management_Replace_OldCard") || testDataString.equalsIgnoreCase("Member_Card_Management_Replace_NewCard") || testDataString.equalsIgnoreCase("Replace_TotalRecords") || testDataString.equalsIgnoreCase("Replace_Start_timestamp") || testDataString.equalsIgnoreCase("Replace_End_timestamp")) {
                    if (tier.equalsIgnoreCase("LoyalT") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else if (testDataString.equalsIgnoreCase("Member_Card_Management_Blacklist_Card") || testDataString.equalsIgnoreCase("Blacklist_TotalRecords") || testDataString.equalsIgnoreCase("Blacklist_Start_timestamp") || testDataString.equalsIgnoreCase("Blacklist_End_timestamp")) {
                    if (tier.equalsIgnoreCase("Jade") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else if (testDataString.equalsIgnoreCase("MemberMergeCardNumberCardKept") || testDataString.equalsIgnoreCase("MemberMergeCardNumberCardCancel") || testDataString.equalsIgnoreCase("MemberMergeCardNumberCardCancel_TransactionDate") || testDataString.equalsIgnoreCase("MemberMergeCardNumberCardCancel_TransactionID") || testDataString.equalsIgnoreCase("MemberMergeCardNumberCardKept_TransactionDate") || testDataString.equalsIgnoreCase("MemberMergeCardNumberCardKept_TransactionID")) {
                    if (tier.equalsIgnoreCase("Ruby") || tier.equalsIgnoreCase("Diamond")){
                        jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                    }else{
                        jenkinsString = "Data for selected tier : "+tier+" is not available in TestData file. Please add relevant data and retry.";
                    }
                }else{
                    jenkinsString = new StringBuffer(testDataString).append("_"+tier).append("_"+env).toString();
                }
            }

        }catch (Exception e){
            System.out.println("Environment : " +env+", Tier : "+tier+", Suit : "+suit);
            e.printStackTrace();
        }
        System.out.println("Test Data Reading value : " + jenkinsString);
        return jenkinsString;
    }


}
